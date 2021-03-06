// A Message (see the beginning comment from
// that class for more information) intended
// to store a String/array of characters corres-
// ponding to the ciphertext from a particular
// cipher.

// Furthermore, a "key ID" can be stored within the
// object that represents an integer that can be
// carried along with the object and used in some
// manner by the message's recipient.

package cipher;

public class CodedMessage extends Message {
	
	// Attributes
	
		private int keyId; 	// A unique identifier the recipient
							// can use in order to derive a key
							// in order to decrypt this message.
							// For example, an offset.
	
	// Constructor
	
	public CodedMessage() {
	}
	
	public CodedMessage(String c) {
		super(c);
	}
	
	public CodedMessage(char[] c) {
		super(c);
	}
	
	public CodedMessage(String c, String n) {
		super(c, n);
	}
	
	public CodedMessage(char[] c, String n) {
		super(c,n);
	}
	
	public CodedMessage(String c, String n, Timestamp t) {
		super(c,n,t);
	}
	
	public CodedMessage(char[] c, String n, Timestamp t) {
		super(c,n,t);
	}
	
	public CodedMessage(String c, int k) {
		super(c);
		keyId = k;
	}
	
	public CodedMessage(char[] c, int k) {
		super(c);
		keyId = k;
	}
	
	public CodedMessage(String c, String name, int k) {
		super(c, name);
		keyId = k;
	}
	
	public CodedMessage(char[] c, String name, int k) {
		super(c, name);
		keyId = k;
	}
	
	public CodedMessage(String c, String name, Timestamp t, int k) {
		super(c, name, t);
		keyId = k;
	}
	
	public CodedMessage(char[] c, String name, Timestamp t, int k) {
		super(c, name, t);
		keyId = k;
	}
	
	// Get method. Set method.
	
	public int getKey() {
		return keyId;
	}
	
	public void setKey(int k) {
		keyId = k;
	}
	
	// Overwritten methods for
	// handling transmission strings.
	// Adding in of keyId to header.
	
	public String toTransmitString() {
		return "[" + keyId + "]" + 
				getTime().toUnformatted() + "<" + getSender() + ">" + getMessage();
	}
	
	public void fromTransmitString(String t) {
		keyId = Integer.parseInt(t.substring(t.indexOf("[")+1,t.indexOf("]")));
		setTime(new Timestamp(t.substring(t.indexOf("]")+1,t.indexOf("]")+15)));
		setSender(t.substring(t.indexOf("<")+1,t.indexOf(">")));
		setMessage(t.substring(t.indexOf(">")+1,t.length()).toCharArray());
	}
	
}
