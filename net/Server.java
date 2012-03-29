package net;
import gM.GammaGUI;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import cipher.CodedMessage;

public class Server implements Runnable {
	
	private DataInputStream streamIn;
	private DataOutputStream streamOut;
	private ServerSocket server;
	private Socket socket;
	private int port;
	private GammaGUI gui;
	
	// Constructors

	public Server(int port, GammaGUI gui) throws UnknownHostException, IOException {

		this.port = port;
		this.gui = gui;
		server = new ServerSocket(port);

	}
	
	// Get methods.
	
	public int getPort() {
		return port;
	}
	
	// Network methods.
	
	public void run() {
		
		try {
			socket = server.accept(); 
			System.out.println("Connection Made:\n" + socket);
			open();
			while (true) {
				String line = "";
				try {
					line = receiveMessage();
					if (!line.equals("")) {
						CodedMessage out = new CodedMessage();
						out.fromTransmitString(line);
						gui.display(out);
					}
	            }
	            catch(IOException ioe) {  break; }
			}
			close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void open() throws IOException {
		// Open input stream.
		streamIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
		// Open output stream.
		streamOut = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
	}
	
	public void close() throws IOException {
		// Close server, connection, etc.
		if (socket != null) socket.close();
		if (streamIn != null) streamIn.close();
		if (streamOut != null) streamOut.close();
	}
	
	public void sendMessage(String msg) throws IOException {
		streamOut.writeUTF(msg);
		streamOut.flush();
	}
	
	public String receiveMessage() throws IOException {
		return streamIn.readUTF();
	}
	
}
