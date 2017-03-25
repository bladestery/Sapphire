package sapphire.policy.offload;

/**
 * Created by bladestery on 15/2/2560.
 */
import android.provider.Settings;

import java.net.InetSocketAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.io.*;

import sapphire.common.AppObject;
import sapphire.common.SapphireObjectNotAvailableException;
import sapphire.kernel.client.KernelClient;
import sapphire.kernel.common.GlobalKernelReferences;
import sapphire.kernel.common.KernelOID;
import sapphire.kernel.common.KernelObjectNotFoundException;
import sapphire.kernel.common.KernelRPC;
import sapphire.kernel.server.KernelServer;
import sapphire.policy.SapphirePolicy;

public class CodeOffload extends SapphirePolicy{

    public static class CodeOffloadClientPolicy extends SapphireClientPolicy {
        static final long serialVersionUID =-4400265452954433951L;
        static private InetSocketAddress host;
        static private InetSocketAddress serverHost;
        static private KernelOID oid;
        private CodeOffloadServerPolicy server;
        private CodeOffloadGroupPolicy group;
        private long latency;
        //private HashMap<String, Long> local;
        //private HashMap<String, Long> offload;
        private AppObject appObject;
        static private KernelClient kernelclient;
        static private KernelServer kernelserver;

        @Override
        public void onCreate(SapphireGroupPolicy group) {
            this.group = (CodeOffloadGroupPolicy) group;
            serverHost = new InetSocketAddress("192.168.1.2", 22345);
            //this.local = new HashMap<String, Long>();
            //this.offload = new HashMap<String, Long>();
            //UpdateLatency();
        }

        public void UpdateLatency() {
            long startTime = System.nanoTime();
            server.DummyFunc();
            this.latency = (System.nanoTime() - startTime);
            System.out.println("[CodeOffload Client] latency to server (ns): " + this.latency);
        }

        @Override
        public SapphireGroupPolicy getGroup() {
            return group;
        }

        @Override
        public SapphireServerPolicy getServer() {
            return server;
        }

        @Override
        public void setServer(SapphireServerPolicy server) {
            this.server = (CodeOffloadServerPolicy) server;
        }

        private void sync() {
            server.syncObject(appObject.getObject());
        }

        @Override
        public Object onRPC(String method, ArrayList<Object> params) throws Exception {
            Object ret = null;
            long startTime, elapsedTime;
            System.out.println(method);
            switch (method) {
                case "public void org.opencv.samples.facedetect.CascadeManager.localize()":
                    /*
                    KernelServer stub = (KernelServer) UnicastRemoteObject.exportObject(GlobalKernelReferences.nodeServer, 0);
                    Registry nsReg = LocateRegistry.createRegistry(22346);
                    nsReg.rebind("SapphireKernelServer", stub);
                    host = GlobalKernelReferences.nodeServer.getLocalHost();
                    //System.out.println(host); //ip of nodeSever on Android
                    GlobalKernelReferences.nodeServer.oms.registerKernelServer(host);
                    System.out.println("Server ready!");
                    */
                    oid = server.getOID();
                    //ArrayList<String> region = group.sapphire_getRegions();
                    //System.out.println(region);

                    //System.out.println(kernelclient);
                    kernelclient = GlobalKernelReferences.nodeServer.getKernelClient();
                    kernelclient.addHost(serverHost);
                    return ret;
                /*
                case "public void org.opencv.samples.facedetect.CascadeManager.initCC(java.lang.String)":
                case "public org.opencv.objdetect.CascadeClassifier org.opencv.samples.facedetect.CascadeManager.getClassifier()":
                case "public boolean org.opencv.samples.facedetect.CascadeManager.empty()":
                    ret = server.onRPC(method, params);
                    return ret;
                */
                case "public void org.opencv.samples.facedetect.CascadeManager.detectMultiScale" +
                        "(org.opencv.core.Mat,org.opencv.core.MatOfRect,double,int,int,org.opencv.core.Size,org.opencv.core.Size)":
                case "public boolean org.opencv.samples.facedetect.CascadeManager.empty()":
                    try {
                        //server processing
                        startTime = System.nanoTime();
                        ret = server.onRPC(method, params);
                        //kernelclient = GlobalKernelReferences.nodeServer.getKernelClient();
                        //kernelserver = kernelclient.getServer(serverHost);
                        //System.out.println(kernelserver);
                        //KernelRPC rpc = new KernelRPC(oid, method, params);
                        //ret = kernelclient.tryMakeKernelRPC(kernelserver, rpc);
                        elapsedTime = (System.nanoTime() - startTime) / (1000000);
                        System.out.println("[CodeOffload] " + method + " server.onRPC (ms): " + elapsedTime);

                        //local processing - flows to the next case statement
                        startTime = System.nanoTime();
                        ret = appObject.invoke(method, params);
                        elapsedTime = (System.nanoTime() - startTime) / (1000000);
                        System.out.println("[CodeOffload appObject] " + method + " (ms): " + elapsedTime);
                    } catch (RemoteException e) {
                        throw new SapphireObjectNotAvailableException("Could not contact Sapphire server.");
                    } catch (KernelObjectNotFoundException e) {
                        throw new SapphireObjectNotAvailableException("Could not find server policy object.");
                    }
                    return ret;
                case "public void org.opencv.objdetect.CascadeClassifier.detectMultiScale" +
                        "(org.opencv.core.Mat,org.opencv.core.MatOfRect,double,int,int,org.opencv.core.Size,org.opencv.core.Size)":
                case "public boolean org.opencv.objdetect.CascadeClassifier.empty()":
                    if (appObject == null) {
                        appObject = server.getAppObject();
                        ret = appObject.invoke(method, params);
                    } else {
                        ret = appObject.invoke(method, params);
                    }
                    //ret = appObject.invoke(method, params);
                    return ret;
                default:
                    break;
            }

            try {
                //server processing
                startTime = System.nanoTime();
                ret = server.onRPC(method, params);
                elapsedTime = (System.nanoTime() - startTime) / (1000000);
                System.out.println("[CodeOffload] " + method + " server.onRPC (ms): " + elapsedTime);

                //local processing
                if (appObject == null) {
                    System.out.println("appObject null");
                    startTime = System.nanoTime();
                    /*
                    CodeOffloadServerPolicy policy = (CodeOffloadServerPolicy) server.replicate();
                    oid = policy.sapphire_getOID();
                    GlobalKernelReferences.nodeServer.moveKernelObjectToServer(host, oid);
                    //policy = (CodeOffloadServerPolicy) GlobalKernelReferences.nodeServer.getObject(oid);
                    appObject = policy.getAppObject();
                    */
                    appObject = server.getAppObject();
                    ret = appObject.invoke(method, params);
                    elapsedTime = (System.nanoTime() - startTime) / (1000000);
                    System.out.println("[CodeOffload appObject] " + method + " (ms): " + elapsedTime);
                } else {
                    System.out.println("appObject exist");
                    startTime = System.nanoTime();
                    ret = appObject.invoke(method, params);
                    elapsedTime = (System.nanoTime() - startTime) / (1000000);
                    System.out.println("[CodeOffload appObject] " + method + " (ms): " + elapsedTime);
                }
            } catch (RemoteException e) {
                throw new SapphireObjectNotAvailableException("Could not contact Sapphire server.");
            } catch (KernelObjectNotFoundException e) {
                throw new SapphireObjectNotAvailableException("Could not find server policy object.");
            }

            return ret;
        }
    }


    public static class CodeOffloadServerPolicy extends SapphireServerPolicy {
        static final long serialVersionUID =-7847659545984906681L;

        private CodeOffloadGroupPolicy group;

        @Override
        public void onCreate(SapphireGroupPolicy group) {
            this.group = (CodeOffloadGroupPolicy) group;
        }

        @Override
        public SapphireGroupPolicy getGroup() {
            return group;
        }

        @Override
        public void onMembershipChange() {
        }

        public AppObject getAppObject() {
            return sapphire_getAppObject();
        }

        public KernelOID getOID() { return sapphire_getOID(); }

        public SapphireServerPolicy replicate() { return sapphire_replicate(); }


        public void syncObject(Serializable object) {
            appObject.setObject(object);
        }

        public void DummyFunc() {
            System.out.println("[CodeOffload] server: DummyFunc()");
            return;
        }

    }

    public static class CodeOffloadGroupPolicy extends SapphireGroupPolicy {
        CodeOffloadServerPolicy server;

        @Override
        public void addServer(SapphireServerPolicy server) {
            this.server = (CodeOffloadServerPolicy) server;
        }

        @Override
        public void onFailure(SapphireServerPolicy server) {

        }

        @Override
        public SapphireServerPolicy onRefRequest() {
            return server;
        }

        @Override
        public ArrayList<SapphireServerPolicy> getServers() {
            return null;
        }

        @Override
        public void onCreate(SapphireServerPolicy server) {
            addServer(server);
        }
    }
}
