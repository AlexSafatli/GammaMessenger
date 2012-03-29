package net;
import gM.GammaGUI;

import java.io.*;
import java.net.Socket;

import cipher.CodedMessage;

public class Client implements Runnable {
	
	private DataInputStream streamIn;	// Network stream for input.
	private DataOutputStream streamOut; // Network stream for output.
	private int port;					// Connection port.
	private String server_ip;			// Connection IP.
	private Socket socket;				// Used to establish connection.
	private GammaGUI gui;				// The GUI thread in order to output messages to textarea.
	
	// Constructor	
	
	public Client(String server_ip, int port, GammaGUI gui) {
		this.port = port;
		this.server_ip = server_ip;
		this.gui = gui;
	}
	
	// Get methods.
	
	public String getServerIp() {
		return server_ip;
	}
	
	public int getPort() {
		return port;
	}
	
	// Network methods.
	
	public void run() {
		
		try {
			// Establishes connection to server.
			socket = new Socket(server_ip, port);
			// Open input stream.
			streamIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			// Open output stream.
			streamOut = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
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
		
	
