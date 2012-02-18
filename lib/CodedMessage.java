// A Message (see the beginning comment from
// that class for more information) intended
// to store a String/array of characters corres-
// ponding to the ciphertext from a particular
// cipher.

// Furthermore, a "key ID" can be stored within the
// object that represents an integer that can be
// carried along with the object and used in some
// manner by the message's recipient.

package lib;

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
	
	public CodedMessage(String c, int k) {
		
		super(c);
		keyId = k;
		
	}
	
	public CodedMessage(char[] c, int k) {
		
		super(c);
		keyId = k;
		
	}
	
	// Get method. Set method.
	
	public int getKey() {
		return keyId;
	}
	
	public void setKey(int k) {
		keyId = k;
	}
	
}
