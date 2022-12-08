package telran.net;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.*;
public class TcpServer implements Runnable {
   private static final int DEFAULT_N_THREADS = 5;
private ServerSocket serverSocket;
   private int port;
   private ApplProtocol protocol;
   private ExecutorService executor; 
   public TcpServer(int port, ApplProtocol protocol, int nThreads) throws Exception{
	   this.port = port;
	   this.protocol = protocol;
	   executor = Executors.newFixedThreadPool(nThreads);
	   serverSocket = new ServerSocket(port);
   }
   public TcpServer(int port, ApplProtocol protocol) throws Exception {
	   this(port, protocol, DEFAULT_N_THREADS);
   }
	@Override
	public void run() {
		System.out.println("Server is listening on the port " + port);
		try {
			while(true) {
				Socket socket = serverSocket.accept();
				TcpClientServer clientServer = new TcpClientServer(socket, protocol);
				executor.execute(clientServer);
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		

	}

}
