// 
// Decompiled by Procyon v0.5.30
// 

package org.apache.harmony.rmi.transport.tcp;

import java.net.SocketTimeoutException;
import java.net.SocketAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.io.IOException;
import java.net.Socket;
import java.io.Serializable;
import java.rmi.server.RMISocketFactory;

public class DirectRMISocketFactory extends RMISocketFactory implements Serializable
{
    private static final long serialVersionUID = -779073015476675531L;
    
    public Socket createSocket(final String s, final int n) throws IOException {
	if (s.startsWith("localhost", 0)) {
	    return new Socket("::ffff:127.0.0.1", n);
	} else {
	    return new Socket(s, n);
	}
    }
    
    public ServerSocket createServerSocket(final int n) throws IOException {
        return new ServerSocket(n);
    }
    
    public Socket createSocket(final String s, final int n, final int n2) throws IOException {
        Socket socket = new Socket();
        try {
	    if (s.startsWith("localhost", 0))
		socket.connect(new InetSocketAddress("::ffff:127.0.0.1:", n), n2);
	    else
		socket.connect(new InetSocketAddress(s, n), n2);
        }
        catch (SocketTimeoutException ex) {
            socket = null;
        }
        return socket;
    }
}
