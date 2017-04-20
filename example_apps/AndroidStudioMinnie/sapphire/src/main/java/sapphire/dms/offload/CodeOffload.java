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
import sapphire.kernel.server.KernelObject;
import sapphire.kernel.server.KernelServer;
import sapphire.policy.SapphirePolicy;

import static sapphire.kernel.common.GlobalKernelReferences.nodeServer;

public class CodeOffload extends SapphirePolicy{

    public static class CodeOffloadClientPolicy extends SapphireClientPolicy {
        static final long serialVersionUID =-4400265452954433951L;
        private boolean init;
        private CodeOffloadServerPolicy server;
        private CodeOffloadGroupPolicy group;
        private long latency;
        private AppObject appObject;

        @Override
        public void onCreate(SapphireGroupPolicy group) {
            this.group = (CodeOffloadGroupPolicy) group;
            //init = false;
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
            /*
            if (!init) {
                KernelServer kernelserver = (KernelServer) UnicastRemoteObject.exportObject(nodeServer, 0);
                Registry nsReg = LocateRegistry.createRegistry(22346);
                nsReg.rebind("SapphireKernelServer", kernelserver);
                GlobalKernelReferences.nodeServer.oms.registerKernelServer(GlobalKernelReferences.nodeServer.getLocalHost());
                System.out.println("Server ready!");
                init = true;
            }
            */
            try {
                //server processing
                startTime = System.nanoTime();
                ret = server.onRPC(method, params);
                elapsedTime = (System.nanoTime() - startTime) / (1000000);
                System.out.println("[CodeOffload] " + method + " server.onRPC (ms): " + elapsedTime);

                //TODO: local processing
                /*
                startTime = System.nanoTime();
                appObject = policy.getAppObject();
                ret = appObject.invoke(method, params);
                elapsedTime = (System.nanoTime() - startTime) / (1000000);
                System.out.println("[CodeOffload appObject] " + method + " (ms): " + elapsedTime);
                */
                /*
                KernelClient kernelclient = GlobalKernelReferences.nodeServer.getKernelClient();
                KernelOID oid = server.getOID();
                System.out.println(oid);
                KernelRPC rpc = new KernelRPC(oid, method, params);
                try {
                    ret = kernelclient.tryMakeKernelRPC(GlobalKernelReferences.nodeServer, rpc);
                } catch (KernelObjectNotFoundException e) {
                    //KernelObject object = new KernelObject(server.getAppObject().getObject());
                    //kernelclient.copyObjectToServer(GlobalKernelReferences.nodeServer.getLocalHost(), oid, object);
                    //GlobalKernelReferences.nodeServer.copyKernelObject(oid,object);
                    //GlobalKernelReferences.nodeServer.moveKernelObjectToServer(GlobalKernelReferences.nodeServer.getLocalHost(), oid);
                    ret = kernelclient.tryMakeKernelRPC(GlobalKernelReferences.nodeServer, rpc);
                }
                */
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
