// Specifies the necessary components of a 
// Cipher-related object, such as the Vigenere
// or Ceasar cipher objects.

package lib;

public interface Cipher {
	
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
