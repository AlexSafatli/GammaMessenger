package gM;

import java.awt.*;
import java.util.EventListener;

import javax.swing.*;

@SuppressWarnings("serial")
public class ToolBarButton extends JButton implements EventListener {
  private static final Insets margins =
    new Insets(0, 0, 0, 0);

  public ToolBarButton(Icon icon) {
    super(icon);
    setMargin(margins);
    setVerticalTextPosition(BOTTOM);
    setHorizontalTextPosition(CENTER);
  }

  public ToolBarButton(String imageFile) {
	  this(new ImageIcon(ClassLoader.getSystemResource(imageFile)));
  }

  public ToolBarButton(String imageFile, String text) {
    this(new ImageIcon(ClassLoader.getSystemResource(imageFile)));
    setText(text);
  }
  
}
