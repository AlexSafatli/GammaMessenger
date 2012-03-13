package gM;

import java.awt.Color;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTextArea;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import cipher.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GammaGUI implements ActionListener {
  
	final JFileChooser fc = new JFileChooser();
	final int RANDOMKEYFILELENGTH = 50;//Number of 1-24char(1message) keys to generate in one keyfile
	
	private JFrame frmGamm;
	private JTextField textField;
	private GammaToolBar toolBar;
	private JScrollPane scrollPane;
	private JTextArea textArea;
	private JTextArea eTextArea;
	
	private boolean encrypt = false;
	private String encryptionType = "None";
	private static Caesar caesar;
	private static Vigenere vigenere;
	
	private static Key key;
	private Message yourMessage;
	private String userName = "You";
	
	private String yourIP;
	private int yourPort;
	private String theirIP;
	private int theirPort;
	

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
	public GammaGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
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
					//display message
					yourMessage.setMessage(textField.getText());
					display(userName + ": ");
					display(yourMessage);
					textField.setText("");
					
					//TODO send message function here
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
	        openKeyFile(e);
	        display("\nKeyfile Loaded.\n");
	    }
	    
	    else if("Generate Keyfile".equals(cmd)) { //second button clicked
	    	key = new Key();
	    	key.generate(RANDOMKEYFILELENGTH);
	    	saveKeyFile(e);
	    }
	    
	    else if("Save Conversation".equals(cmd)) { //third button clicked	    	
	    	saveConvo(e);
	    	display("\nConversation Saved\n");
	    }
	    
	    else if("Connect".equals(cmd)) { //fourth button clicked
	    	connectMenu();
	    	System.out.println("Connection Establised");
	    }
	    
	    else if("Encryption Options".equals(cmd)) { //fifth button clicked
		    encryptionMenu();
		    display("\nEncryption Type is Now: " + encryptionType + "\n");
		}
	    
	    else if("General Options".equals(cmd)) { //sixth button clicked
	    	optionsMenu();
		    display("\n Username is: " + userName + " port: " + yourPort);
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

	private void connectMenu() {
		// TODO Auto-generated method stub
		String[] options = { "Establish New Conversation", "Connect to Existing Conversation" };
		int connectType = JOptionPane.showOptionDialog(null, "Do you want to start a new conversation (server)" +
				" or join an existing conversation (client)?", "Connection Options",
		JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
		null, options, options[0]);
		
		//Server
		if(connectType == 0)
			display("Server created: IP XXX Port: " + yourPort);//TODO NETWORK CLASS API HERE
		//Port
		else {
			theirIP = JOptionPane.showInputDialog("Enter the IP address you wish to connect to.");
			theirPort = Integer.valueOf(JOptionPane.showInputDialog("Enter the Port Number to connect to."));
			display("Connecting to " + theirIP + ":" + theirPort);
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
		else if(encryptionType.equals("Classic Vigenere"))
			vigenere = new Vigenere();
		
		else if(encryptionType.equals("One Time Pad")) {
			if(key.equals(null)) {
				display("You need to open or generate a key to select this encyption type.");
				encryptionType = "None";
			}
			else	
			vigenere = new Vigenere(key);
		}
	}

	private void openKeyFile(ActionEvent e) {
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
				BufferedWriter out = new BufferedWriter(new FileWriter(file));
				out.write(textArea.getText());
				out.close();
				} 
				catch (IOException err) 
				{ 
				System.out.println("Exception ");

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
				display("\nKeyfile Generated.\n");
				} 
				catch (IOException err) 
				{ 
				System.out.println("Exception ");

				}
			
		}
	}

	public void display(Message s) {
		textArea.append(s.getMessage()+"\n");//Always append text to unecrypted window
		//"None", "One Time Pad", "Classic Vigenere", "Caesar"
		if(encryptionType.equals("None")){
		eTextArea.append(s.getMessage());//TODO NETWORK SEND HERE
		}
		else if(encryptionType.equals("One Time Pad")||encryptionType.equals("Classic Vigenere")){
			eTextArea.append(vigenere.encode(s).toString());//TODO NETWORK SEND HERE
		}
		else if(encryptionType.equals("Caesar")) {
			eTextArea.append(caesar.encode(s).getMessage());
		}
		
		eTextArea.append("\n");
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
