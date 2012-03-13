// Specifies the necessary components of a 
// Cipher-related object, such as the Vigenere
// or Ceasar cipher objects.

package cipher;

public interface Cipher {
	
	// Static attributes: values associated with the
	// Unicode character set / ASCII representation
	// of characters.
	
	public final static int UPPERCASE = 65; 	// ASCII value for uppercase A.
	public final static int LOWERCASE = 97; 	// ASCII value for lowercase A.
	public final static int UNICODE = 65535;	// Number of characters in Unicode character set (for char primitive type).
	
	// Encoding methods.
	
	public abstract CodedMessage encode(String plaintext);
	public abstract CodedMessage encode(Message plaintext);
	public abstract CodedMessage encodeAlpha(String plaintext);
	public abstract CodedMessage encodeAlpha(Message plaintext);
	
	// Decoding methods.
	
	public abstract Message decode(String ciphertext);
	public abstract Message decode(CodedMessage ciphertext);
	public abstract Message decodeAlpha(String ciphertext);
	public abstract Message decodeAlpha(CodedMessage ciphertext);
	
}
