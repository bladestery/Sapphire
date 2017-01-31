// 
// Decompiled by Procyon v0.5.30
// 

package org.apache.harmony.rmi.transport;

import org.apache.harmony.rmi.common.CreateThreadAction;
import java.net.InetAddress;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.rmi.RemoteException;
import java.rmi.ConnectIOException;
import java.rmi.ConnectException;
import java.rmi.UnknownHostException;
import org.apache.harmony.rmi.internal.nls.Messages;
import java.net.Socket;
import java.io.IOException;
import java.net.ServerSocket;
import org.apache.harmony.rmi.common.GetLongPropAction;
import org.apache.harmony.rmi.common.GetBooleanPropAction;
import java.security.PrivilegedAction;
import java.security.AccessController;
import org.apache.harmony.rmi.common.GetStringPropAction;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.RMIClientSocketFactory;

public class Endpoint
{
    public static final int NULL_CSF = 0;
    public static final int NONNULL_CSF = 1;
    private final String host;
    private int port;
    private RMIClientSocketFactory csf;
    private RMIServerSocketFactory ssf;
    private static String localHost;
    private static boolean isLocalHostIdentified;
    private static String localHostPropVal;
    private static boolean useLocalHostName;
    private static int localHostNameTimeout;
    
    static {
        Endpoint.localHost = getLocalHost();
        Endpoint.isLocalHostIdentified = false;
        Endpoint.localHostPropVal = AccessController.doPrivileged((PrivilegedAction<String>)new GetStringPropAction("java.rmi.server.hostname"));
        Endpoint.useLocalHostName = AccessController.doPrivileged((PrivilegedAction<Boolean>)new GetBooleanPropAction("java.rmi.server.useLocalHostname"));
        Endpoint.localHostNameTimeout = (int)(Object)AccessController.doPrivileged((PrivilegedAction<Long>)new GetLongPropAction("harmony.rmi.transport.tcp.localHostNameTimeOut", 10000L));
    }
    
    public Endpoint(final int port, final RMIClientSocketFactory csf, final RMIServerSocketFactory ssf) {
        this.host = getLocalHost();
        this.port = port;
        this.csf = csf;
        this.ssf = ssf;
    }
    
    public Endpoint(final String host, final int port, final RMIClientSocketFactory csf, final RMIServerSocketFactory ssf) {
        this.host = host;
        this.port = port;
        this.csf = csf;
        this.ssf = ssf;
    }
    
    public static Endpoint createTemplate(final Endpoint endpoint) {
        return new Endpoint(null, endpoint.port, endpoint.csf, endpoint.ssf);
    }
    
    public ServerSocket createServerSocket() throws IOException {
        final ServerSocket serverSocket = DefaultRMISocketFactory.getNonNullServerFactory(this.ssf).createServerSocket(this.port);
        if (this.port == 0) {
            this.port = serverSocket.getLocalPort();
        }
        return serverSocket;
    }
    
    public Socket createSocket() throws RemoteException {
        Socket socket;
        try {
            socket = DefaultRMISocketFactory.getNonNullClientFactory(this.csf).createSocket(this.host, this.port);
        }
        catch (java.net.UnknownHostException ex) {
            throw new UnknownHostException(Messages.getString("rmi.80", (Object)this.toString()), ex);
        }
        catch (java.net.ConnectException ex2) {
            throw new ConnectException(Messages.getString("rmi.80", (Object)this.toString()), ex2);
        }
        catch (IOException ex3) {
            throw new ConnectIOException(Messages.getString("rmi.80", (Object)this.toString()), ex3);
        }
        return socket;
    }
    
    public RMIClientSocketFactory getClientSocketFactory() {
        return this.csf;
    }
    
    public RMIServerSocketFactory getServerSocketFactory() {
        return this.ssf;
    }
    
    public int getPort() {
        return this.port;
    }
    
    public String getHost() {
        return this.host;
    }
    
    public boolean equals(final Object o) {
        if (!(o instanceof Endpoint)) {
            return false;
        }
        final Endpoint endpoint = (Endpoint)o;
        if (this.port != endpoint.port) {
            return false;
        }
        Label_0064: {
            if (this.host == null) {
                if (this.host == endpoint.host) {
                    break Label_0064;
                }
            }
            else if (this.host.equals(endpoint.host)) {
                break Label_0064;
            }
            return false;
        }
        Label_0101: {
            if (this.csf == null) {
                if (this.csf == endpoint.csf) {
                    break Label_0101;
                }
            }
            else if (this.csf.equals(endpoint.csf)) {
                break Label_0101;
            }
            return false;
        }
        if (this.ssf == null) {
            if (this.ssf == endpoint.ssf) {
                return true;
            }
        }
        else if (this.ssf.equals(endpoint.ssf)) {
            return true;
        }
        return false;
    }
    
    public int hashCode() {
        return this.port;
    }
    
    public void writeExternal(final ObjectOutput objectOutput, final boolean b) throws IOException {
        if (b) {
            if (this.csf == null) {
                objectOutput.writeByte(0);
            }
            else {
                objectOutput.writeByte(1);
            }
        }
        objectOutput.writeUTF(this.host);
        objectOutput.writeInt(this.port);
        if (b && this.csf != null) {
            objectOutput.writeObject(this.csf);
        }
    }
    
    public static Endpoint readExternal(final ObjectInput objectInput, final boolean b) throws IOException, ClassNotFoundException {
        int unsignedByte = 0;
        if (b) {
            unsignedByte = objectInput.readUnsignedByte();
        }
        final String utf = objectInput.readUTF();
        final int int1 = objectInput.readInt();
        RMIClientSocketFactory rmiClientSocketFactory = null;
        if (b && unsignedByte == 1) {
            rmiClientSocketFactory = (RMIClientSocketFactory)objectInput.readObject();
        }
        return new Endpoint(utf, int1, rmiClientSocketFactory, null);
    }
    
    public String toString() {
        String s = "[" + this.host + ":" + this.port;
        if (this.csf != null) {
            s = String.valueOf(s) + ", csf: " + this.csf;
        }
        if (this.ssf != null) {
            s = String.valueOf(s) + ", ssf: " + this.ssf;
        }
        return String.valueOf(s) + "]";
    }
    
    private static synchronized String getLocalHost() {
        if (Endpoint.isLocalHostIdentified) {
            return Endpoint.localHost;
        }
        if (Endpoint.localHostPropVal != null) {
            Endpoint.isLocalHostIdentified = true;
            return Endpoint.localHost = Endpoint.localHostPropVal;
        }
        try {
            final InetAddress localHost = InetAddress.getLocalHost();
            localHost.getAddress();
            if (Endpoint.useLocalHostName) {
                Endpoint.localHost = getFQDN(localHost);
            }
            else {
                Endpoint.localHost = localHost.getHostAddress();
            }
            Endpoint.isLocalHostIdentified = true;
        }
        catch (Exception ex) {
            Endpoint.localHost = null;
        }
        return Endpoint.localHost;
    }
    
    private static String getFQDN(final InetAddress inetAddress) throws java.net.UnknownHostException {
        final String hostName = inetAddress.getHostName();
        if (hostName.indexOf(46) >= 0) {
            return hostName;
        }
        final String hostAddress = inetAddress.getHostAddress();
        final FQDNGetter fqdnGetter = new FQDNGetter(hostAddress);
        final Thread thread = AccessController.doPrivileged((PrivilegedAction<Thread>)new CreateThreadAction((Runnable)fqdnGetter, "FQDN getter.", true));
        try {
            synchronized (fqdnGetter) {
                thread.start();
                fqdnGetter.wait(Endpoint.localHostNameTimeout);
            }
            // monitorexit(fqdnGetter)
        }
        catch (InterruptedException ex) {}
        final String access$0 = fqdnGetter.getFQDN();
        if (access$0 == null || access$0.indexOf(46) < 0) {
            return hostAddress;
        }
        return access$0;
    }
    
    private static class FQDNGetter implements Runnable
    {
        private String addr;
        private String name;
        
        FQDNGetter(final String addr) {
            this.addr = addr;
        }
        
        public void run() {
            try {
                this.name = InetAddress.getByName(this.addr).getHostName();
            }
            catch (java.net.UnknownHostException ex) {
                synchronized (this) {
                    this.notify();
                }
            }
            finally {
                synchronized (this) {
                    this.notify();
                }
            }
            synchronized (this) {
                this.notify();
            }
        }
        
        private String getFQDN() {
            return this.name;
        }
    }
}
