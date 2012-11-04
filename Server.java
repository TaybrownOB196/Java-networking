import java.io.*;//importing io classes
import java.net.*;//importing networking classes
class Server {
    public static void main(String argc[]) {
	    try {
	                ServerSocket senderSocket = new ServerSocket(2000); // socket for sending data to client
			System.out.println("Waiting for a client...");
		        Socket client = senderSocket.accept();//wait for client to connect,which receives data from server
			System.out.println("Client connected....\nStarting chat.....");
			PrintWriter outStream = new PrintWriter(client.getOutputStream());//get client's output stream to write
			BufferedReader outBuffer = new BufferedReader(new InputStreamReader(System.in));//buffer to read from keyboard
			String line = "Ready to chat";
			ClientReceiver clientReceiver = new ClientReceiver(client); 
			do {
			    System.out.println("me: "+line+"\n");
				outStream.println(line);
				outStream.flush();
			}
			while((line =  outBuffer.readLine())!= "stop");
			outBuffer.close();
			outStream.close();
			client.close();
			senderSocket.close();
			System.out.println("Server disconnected");
		} catch(Exception e) {
		    System.out.println("Could not start server!");
		}
	}
	class ClientReceiver extends Thread {//Thread which receives  data from client
	        Socket clientReceiver; 
		BufferedReader inBuffer;//buffer to accept data from client
		String clientInput = "Starting reception from client...";//string storing client input
	        ClientReceiver(Socket clientSocket) {//constructor
	            super("ClientReceiver Thread");//initialising thread name
		    clientReceiver = clientSocket;
		    start();//start the thread
		}
		public void run() {//thread operations
		    try {
			inBuffer = new BufferedReader(new InputStreamReader(clientReceiver.getInputStream()));//initialise buffer with client's input stream
		        do {
		            System.out.println("Client: "+clientInput);
			} while((clientInput = inBuffer.readLine())!="stop");
			System.out.println("Client disconnected");
		    } catch(Exception e) {
		        System.out.println("Could not initialise client!");
		   }
		}
       }
}