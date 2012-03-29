// An encapsulation of a String or array
// of characters that is intended to be sent
// to another person, i.e. through an instant
// messaging protocol. 

// When an instance of this object is constructed, 
// the time which it was constructed will be embedded 
// into the object.

package cipher;

public class Message {
	
	// Attributes
	
	private char[] text; 		// The message is stored as an
								// array of characters.
	private Timestamp time;		// Associated time when the
								// message was created.
	private String sender;		// Message sender's name is stored.
	
	// Constructor
	
	public Message() {
		time = new Timestamp();
	}
	
	public Message(String te) {
		text = te.toCharArray();
		time = new Timestamp();
	}
	
	public Message(char[] te) {
		text = te;
		time = new Timestamp();
	}
	
	public Message(String te, String name) {
		text = te.toCharArray();
		sender = name;
		time = new Timestamp();
	}
	
	public Message(char[] te, String name) {
		text = te;
		sender = name;
		time = new Timestamp();
	}
	
	public Message(String te, String name, Timestamp t) {
		text = te.toCharArray();
		sender = name;
		time = t;
	}
	
	public Message(char[] te, String name, Timestamp t) {
		text = te;
		sender = name;
		time = t;
	}
	
	// Get, set methods.
	
	public Timestamp getTime() {
		return time;
	}
	
	public void setTime(Timestamp t) {
		time = t;
	}
	
	public String getMessage() {
		// Returns a String.
		return new String(text);
	}
	
	public char[] getCharArray() {
		// Get the character array rather than
		// the String representation of the message.
		return text;
	}
	
	public void setMessage(String m) {
		// If message changed, 
		// time done has changed, so
		// new Timestamp made.
		text = m.toCharArray();
		time = new Timestamp();
	}
	
	public void setMessage(char[] m) {
		text = m;
		time = new Timestamp();
	}
	
	public String getSender() {
		return sender;
	}
	
	public void setSender(String n) {
		if (n.contains(">")) {
			System.out.println("Error: Invalid character '>' present in username.");
			return;
		}
		sender = n;
	}
	
	// Handles conversion of a Message object
	// into a raw String in order to be sent
	// across a network. Will include header
	// information, etc. that can be able to
	// be used in order to reconstruct the object.
	
	public String toTransmitString() {
		return time.toUnformatted() + "<" + sender + ">" + getMessage();
	}
	
	public void fromTransmitString(String t) {
		time = new Timestamp(t.substring(0,14));
		sender = t.substring(15, t.indexOf(">"));
		text = t.substring(t.indexOf(">")+1,t.length()).toCharArray();
	}
	
	// Returns the string/message.
	
	public String toLabeledMessage() {
		// Will show sender.
		return getSender() + " (" + time.toOnlyHour() + "): " + getMessage();
	}
	
	public String toString() {
		return getMessage();
	}
	
}
