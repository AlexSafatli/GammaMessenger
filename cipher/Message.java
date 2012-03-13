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
		text = m.toCharArray();
	}
	
	public void setMessage(char[] m) {
		text = m;
	}
	
	// Handles conversion of a Message object
	// into a raw String in order to be sent
	// across a network. Will include header
	// information, etc. that can be able to
	// be used in order to reconstruct the object.
	
	public String toTransmitString() {
		return time.toUnformatted() + getMessage();
	}
	
	public void fromTransmitString(String t) {
		time = new Timestamp(t.substring(0,14));
		text = t.substring(14,t.length()).toCharArray();
	}
	
	
	// Returns the string.
	
	public String toString() {
		return getMessage();
	}
	
}
