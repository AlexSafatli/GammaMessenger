package gM;
import java.awt.*;
import java.awt.event.ActionEvent;

import javax.swing.*;


public class GammaToolBar extends JToolBar{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String[] imageFiles;//Image icon name
	private String[] toolbarLabels;//Label tooltip name
	private final int VIEWBUTTONNUM = 6;// final reference to which component is the switch view button
	
	public GammaToolBar(GammaGUI gammaGUI) {
		
			String[] imageFiles = 
				{"OpenKeyFileIcon.gif","GenerateKeyIcon.gif","SaveIcon.gif",
				"ConnectIcon.gif","EncryptionIcon.gif","OptionsIcon.gif","UnlockedIcon.gif","ExitIcon.gif"};
			
			String[] toolbarLabels = 
				{"Open Keyfile", "Generate Keyfile", "Save Conversation", 
				"Connect", "Encryption Options","General Options","Un-Encrypted view","Exit Program"};
			
			this.imageFiles = imageFiles;
			this.toolbarLabels = toolbarLabels;
			
		
			//Create a series of Jbuttons with the image names, tooltips specified. Add actionlisteners. JButtons
			Insets margins = new Insets (0,0,0,0);
			for(int i = 0; i < toolbarLabels.length; i++) {
				Icon icon = new ImageIcon(ClassLoader.getSystemResource("images/"+imageFiles[i]));
				Action action = new AbstractAction("", icon) {
					public void actionPerformed(ActionEvent evt) {
						// Perform action
					}
				};
				JButton button = new JButton(action);
				button.setToolTipText(toolbarLabels[i]);
				button.setMargin(margins);
				button.setActionCommand(toolbarLabels[i]);
				button.addActionListener(gammaGUI);
				add(button);
			}
		
	}
	//Method changes the icon of the encrypted/un-encrypted view button. Redraws the toolbar.
	public void changeView(boolean encrypt){
		JButton newView = (JButton)this.getComponentAtIndex(VIEWBUTTONNUM);
		if(encrypt){
			newView.setIcon(new ImageIcon(ClassLoader.getSystemResource("images/UnlockedIcon.gif")));
			newView.setToolTipText("Un-Encrypted view");
		}
		else {
			newView.setIcon(new ImageIcon(ClassLoader.getSystemResource("images/LockedIcon.gif")));
			newView.setToolTipText("Encrypted view");
		}
		this.remove(VIEWBUTTONNUM);
		this.add(newView,VIEWBUTTONNUM);
		this.validate();
	}

		    


	
}
