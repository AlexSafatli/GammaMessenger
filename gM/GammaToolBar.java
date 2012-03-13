package gM;
import java.awt.*;
import java.awt.event.ActionEvent;

import javax.swing.*;


public class GammaToolBar extends JToolBar{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String[] imageFiles;
	private String[] toolbarLabels;
	private final int VIEWBUTTONNUM = 6;
	
	public GammaToolBar(GammaGUI gammaGUI) {
		
			String[] imageFiles = 
				{"OpenKeyFileIcon.gif","GenerateKeyIcon.gif","SaveIcon.gif",
				"ConnectIcon.gif","EncryptionIcon.gif","OptionsIcon.gif","UnlockedIcon.gif","ExitIcon.gif"};
			
			String[] toolbarLabels = 
				{"Open Keyfile", "Generate Keyfile", "Save Conversation", 
				"Connect", "Encryption Options","General Options","Un-Encrypted view","Exit Program"};
			
			this.imageFiles = imageFiles;
			this.toolbarLabels = toolbarLabels;
			
		
		/*
		Insets margins = new Insets (0,0,0,0);
		//Creates all the buttons on toolbars. adds actionlisteners.
		for(int i = 0; i < toolbarLabels.length; i++) {
			ToolBarButton button = 
					new ToolBarButton("images/" + imageFiles[i]);
			button.setToolTipText(toolbarLabels[i]);
		    button.setMargin(margins);
		    button.setActionCommand(toolbarLabels[i]);
		    button.addActionListener(gammaGUI);
		    add(button);
		    */
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

	public void setTextLabels(boolean labelsAreEnabled) {
	    Component c;
	    int i = 0;
	    while((c = getComponentAtIndex(i++)) != null) {
	      ToolBarButton button = (ToolBarButton)c;
	      if (labelsAreEnabled)
	        button.setText(button.getToolTipText());
	      else
	        button.setText(null);
	   }
	}
	
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
