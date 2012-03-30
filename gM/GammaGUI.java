package gM;

import net.*;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTextArea;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import cipher.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;

public class GammaGUI implements ActionListener {
  
	// File choosing prompt.
	private final JFileChooser fc = new JFileChooser();
	
	// Number of 1-24 char (1 message) keys to generate in one keyfile.
	private final int RANDOMKEYFILELENGTH = 50; 
	
	// GUI Elements.
	private JFrame frmGamm;
	private JTextField textField;
	private GammaToolBar toolBar;
	private JScrollPane scrollPane;
	private JTextArea textArea;
	private JTextArea eTextArea;
	
	// Encryption Options, Cipher Elements.
	private boolean encrypt = false;
	private String encryptionType = "None";
	private static Caesar caesar;
	private static Vigenere vigenere;
	private static Key key;
	private Message yourMessage;
	
	// Networking Elements.
	private String userName = "User";
	private int yourPort = 1199; // Default: 1199
	private String theirIP;
	private int theirPort;
	private Server server;
	private Client client;
	private Thread listener;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
			
				try {
					GammaGUI window = new GammaGUI();
					window.frmGamm.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GammaGUI() throws IOException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() throws IOException {
		frmGamm = new JFrame();
		frmGamm.setTitle("\u03B3Messenger 2012");
		frmGamm.setResizable(false);
		frmGamm.setBounds(100, 100, 310, 300);
		frmGamm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmGamm.getContentPane().setLayout(new MigLayout("", "[442px,grow][]", "[16px][grow][20px,grow][][][][][][][][]"));
		
		toolBar = new GammaToolBar(this);
		toolBar.setFloatable(false);
		frmGamm.getContentPane().add(toolBar, "cell 0 0 2 1,growx,aligny top");
		
		//Initialize your Message/Key objects.
		yourMessage = new Message();
		
		//Checks for typed messages, sends to text area on receiving enter.
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			
			public void keyTyped(KeyEvent e) {
				if(e.getKeyChar()==KeyEvent.VK_ENTER){
					// Display and send message.
					yourMessage.setMessage(textField.getText());
					yourMessage.setSender(userName);
					textField.setText("");
					CodedMessage outMessage = sendMessage(yourMessage);
					if (outMessage != null) display(yourMessage, outMessage);
					else display(yourMessage.toLabeledMessage() + "\n");
				}	
			}

		});
		
		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		frmGamm.getContentPane().add(scrollPane, "cell 0 1 1 9,grow");
		
		//Create standard text area
		eTextArea = new JTextArea();
		eTextArea.setLineWrap(true);
		eTextArea.setEditable(false);
		scrollPane.setViewportView(eTextArea);
		frmGamm.getContentPane().add(textField, "cell 0 10,growx,aligny top");
		textField.setColumns(10);
		//eTextArea.setVisible(encrypt);
		
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		frmGamm.getContentPane().add(textField, "cell 0 10,growx,aligny top");
		textField.setColumns(10);
		//textArea.setVisible(!encrypt);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
	    String cmd = e.getActionCommand();
	    if("Open Keyfile".equals(cmd)) { //first button clicked
	    	key = new Key();
	        try {
				openKeyFile(e);
			} catch (IOException e1) {
				display("I/O File error. Try again.\n");
			}
	        display("Keyfile Loaded.\n");
	    }
	    
	    else if("Generate Keyfile".equals(cmd)) { //second button clicked
	    	key = new Key();
	    	key.generate(RANDOMKEYFILELENGTH);
	    	saveKeyFile(e);
	    }
	    
	    else if("Save Conversation".equals(cmd)) { //third button clicked	    	
	    	saveConvo(e);
	    	display("Conversation Saved\n");
	    }
	    
	    else if("Connect".equals(cmd)) { //fourth button clicked
	    	try {
				connectMenu();
			} catch (UnknownHostException e1) {
				display("Unknown host. Cannot connect.\n");
			} catch (IOException e1) {
				display("A problem occurred trying to connect. Try again.\n");
			}
	    	System.out.println("Connection Establised");
	    }
	    
	    else if("Encryption Options".equals(cmd)) { //fifth button clicked
		    encryptionMenu();
		    display("Encryption Type is Now: " + encryptionType + "\n");
		}
	    
	    else if("General Options".equals(cmd)) { //sixth button clicked
	    	optionsMenu();
		    display("Username is: " + userName + " port: " + yourPort + "\n");
		}
	    
	    else if("Un-Encrypted view".equals(cmd)||"Encrypted view".equals(cmd)) { //seventh button clicked
		    
	    	toolBar.changeView(encrypt);
	    	toolBar.repaint();
		    
		    if(encrypt){
		    	String text = eTextArea.getText();
		    	eTextArea = new JTextArea(text);
				eTextArea.setLineWrap(true);
				eTextArea.setEditable(false);
				scrollPane.setViewportView(eTextArea);
				frmGamm.getContentPane().add(textField, "cell 0 10,growx,aligny top");
				textField.setColumns(10);
		    }
		    else {
		    	
		    	String text = textArea.getText();
		    	textArea = new JTextArea(text);
				textArea.setLineWrap(true);
				textArea.setEditable(false);
				scrollPane.setViewportView(textArea);
				frmGamm.getContentPane().add(textField, "cell 0 10,growx,aligny top");
				textField.setColumns(10);
		    }
		    	encrypt = !encrypt;
		}
	    
	    else if("Exit Program".equals(cmd)) { //eight button clicked
		    System.out.println("Exit Program");
		    System.exit(0);
		}
	}

	private void connectMenu() throws UnknownHostException, IOException {
	
		String[] options = { "Establish New Conversation", "Connect to Existing Conversation" };
		int connectType = JOptionPane.showOptionDialog(null, "Do you want to start a new conversation (server)" +
				" or join an existing conversation (client)?", "Connection Options",
		JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
		null, options, options[0]);
		
		// Close current connections.
		
		if (server != null || client != null) {
			if (server != null) server.close();
			else if (client != null) client.close();
			display("Current connection closed.\n");
		}
		
		// Server
		if(connectType == 0) {
			
			display("Conversation started: Port = " + yourPort + "\n");
			server = new Server(yourPort, this);
			listener = new Thread(server);
			listener.start();
		}
		// Client
		else {
			theirIP = JOptionPane.showInputDialog("Enter the IP address you wish to connect to.");
			theirPort = Integer.valueOf(JOptionPane.showInputDialog("Enter the Port Number to connect to."));
			display("Connected to " + theirIP + ":" + theirPort + "\n");
			client = new Client(theirIP, yourPort, this);
			listener = new Thread(client);
			listener.start();
		}
			
	}

	private void optionsMenu() {
		// TODO Auto-generated method stub
		 userName = JOptionPane.showInputDialog(null, "What is your user name?");
		 yourPort = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Port Number (E.g. 32450"));
	}

	private void encryptionMenu() {
		
		String[] possibleValues = { "None", "One Time Pad", "Classic Vigenere", "Caesar"};
		encryptionType = (String)JOptionPane.showInputDialog(null,"Choose Encryption Type:", "Input",
				JOptionPane.INFORMATION_MESSAGE, null,possibleValues, possibleValues[0]);
		
		
		//Checks if encryption is type caesar, asks for shift, sets shift.
		if(encryptionType.equals("Caesar")) {
			caesar = new Caesar();
			caesar.setShift(Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Caesar shift (Number <= 25)")));
		}
		else if(encryptionType.equals("Classic Vigenere") || encryptionType.equals("One Time Pad"))
			if(key == null) {
				display("You need to open or generate a key to select this encyption type.\n");
				encryptionType = "None";
			}
			else vigenere = new Vigenere(key);
	}

	private void openKeyFile(ActionEvent e) throws IOException {
		// TODO Auto-generated method stub
		int rValue = fc.showOpenDialog(toolBar.getComponent(1));
		if (rValue == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			key.openFile(file);
		}
		
	}
	
	private void saveConvo(ActionEvent e) {
		// TODO Auto-generated method stub
		
		int rValue = fc.showSaveDialog(toolBar.getComponent(3));
		if (rValue == JFileChooser.APPROVE_OPTION) {
			
			File file = fc.getSelectedFile();
			
			try {
				PrintWriter output = new PrintWriter(file);
				output.println(textArea.getText());
				output.close();
			} 
			catch (IOException err) { 
				display("Could not save to file.\n");
			}
			
		}
	}
	
	private void saveKeyFile(ActionEvent e) {
		// TODO Auto-generated method stub
		
		int rValue = fc.showSaveDialog(toolBar.getComponent(3));
		if (rValue == JFileChooser.APPROVE_OPTION) {
			
			File file = fc.getSelectedFile();
			try {
				BufferedWriter out = new BufferedWriter(new FileWriter(file));
				out.write(key.toString());
				out.close();
				display("Keyfile Generated.\n");
			} 
			catch (IOException err) { 
				display("Could not save to file.\n");
			}
			
		}
	}

	public void display(CodedMessage s) {
		eTextArea.append(s.toLabeledMessage()+"\n");//Always append text to encrypted window
		
		// "None", "One Time Pad", "Classic Vigenere", "Caesar"
		if(encryptionType.equals("None")){
			textArea.append(s.toLabeledMessage());
		}
		else if(encryptionType.equals("One Time Pad")) {
			key.setIndex(s.getKey());
			textArea.append(vigenere.decode(s).toLabeledMessage());
		}
		else if(encryptionType.equals("Classic Vigenere")){
			key.setIndex(s.getKey());
			textArea.append(vigenere.decodeAlpha(s).toLabeledMessage());
		}
		else if(encryptionType.equals("Caesar")) {
			textArea.append(caesar.decode(s).toLabeledMessage());
		}
		
		textArea.append("\n");
	}
	
	public void display(Message m, CodedMessage c) {
		// If both already present (i.e. on message
		// sending) and decoding not necessary.
		
		eTextArea.append(c.toLabeledMessage() + "\n");
		textArea.append(m.toLabeledMessage() + "\n");
		
	}
	
	public CodedMessage sendMessage(Message s) {
		
		if (server == null && client == null) return null;
		
		CodedMessage encrypted = new CodedMessage();
		
		// "None", "One Time Pad", "Classic Vigenere", "Caesar"
		if(encryptionType.equals("None")){
			encrypted = new CodedMessage(s.getMessage(), s.getSender());
		}
		else if(encryptionType.equals("One Time Pad")) {
			encrypted = vigenere.encode(s);
			key.incrementIndex();
		}		
		else if(encryptionType.equals("Classic Vigenere")){
			key.setIndex(0); // by default, sets key to first in keyset.
			encrypted = vigenere.encodeAlpha(s);
		}
		else if(encryptionType.equals("Caesar")) {
			encrypted = caesar.encode(s);
		}
		
		try {
			if (server != null) server.sendMessage(encrypted.toTransmitString());
			else if (client != null) client.sendMessage(encrypted.toTransmitString());
		} catch (IOException err) { display("Could not send message.\n"); }
		
		return encrypted;
		
	}
	
	//Used for Error/Info text to screen.
	public void display(String s) {
		textArea.append(s);
		eTextArea.append(s);
	}
	
	public boolean isEncrypt() {
		return encrypt;
	}

	public void setEncrypt(boolean encrypt) {
		this.encrypt = encrypt;
	}
}
