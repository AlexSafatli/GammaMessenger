// A class dedicated to encoding and decoding
// a Message into a CodedMessage, or vice versa,
// respectively using both a classical and 
// modern implementation of the Caesar Cipher.

package lib;

public class Caesar implements Cipher {
	
	// Attributes
	
	private int shift=3;	// While the conventional Caesar cipher
							// employs a shift of 3, this class allows
							// for a Shift Cipher of any variant shift
							// amount, if specified.
	
	// Constructor
	
	public Caesar() {
		
	}
	
	public Caesar(int s) {
		
		shift = s;
	}
	
	// Get, set methods.
	
	
	public void setShift(int s) {
		shift = s;
	}
	
	public int getShift() {
		return shift;
	}
	
	// Encoding methods.
	
	public CodedMessage encode(String plaintext) {
		return new CodedMessage(cipher(plaintext.toCharArray()));
	}
	
	public CodedMessage encode(Message plaintext) {
		return new CodedMessage(cipher(plaintext.getCharArray()));
	}
	
	public CodedMessage encodeAlpha(String plaintext) {
		return new CodedMessage(cipherAlpha(plaintext.toCharArray()));
	}
	
	public CodedMessage encodeAlpha(Message plaintext) {
		return new CodedMessage(cipherAlpha(plaintext.getCharArray()));
	}
	
	private char[] cipher(char[] plaintext) {
		// The char primitive data type in Java contains
		// all 65535 characters of the Unicode character set.
		// Therefore, a modulus is done in the off-chance that
		// the addition of two characters takes us out of those
		// bounds.

		char[] ciphertext = new char[plaintext.length];
		
		for (int i = 0; i < plaintext.length; i++)
			ciphertext[i] = (char)((plaintext[i] + shift)%UNICODE);
		
		return ciphertext;
		
	}
	
	private char[] cipherAlpha(char[] plaintext) {
		// Performs the encoding by ignoring all non-alphabet
		// characters and leaving them as they are, and then encoding
		// all alphabet characters modulo 26 (i.e. wraps them around
		// the alphabet); this is the conventional Caesar cipher.
		
		char[] ciphertext = new char[plaintext.length];
		
		for (int i = 0; i < plaintext.length; i++) {
			if (Character.isLetter(plaintext[i])) {
				int result = ((Character.getNumericValue(plaintext[i])-10)
						+ shift)%26;
				result += (Character.isUpperCase(plaintext[i])) ? UPPERCASE : LOWERCASE;
				ciphertext[i] = (char)(result);
			}
			else ciphertext[i] = plaintext[i];
		}
		
		return ciphertext;
		
	}
	
	// Decoding methods.
	
	public Message decode(String plaintext) {
		return new Message(decipher(plaintext.toCharArray()));
	}
	
	public Message decode(CodedMessage plaintext) {
		return new Message(decipher(plaintext.getMessage().toCharArray()));
	}
	
	public Message decodeAlpha(String plaintext) {
		return new Message(decipherAlpha(plaintext.toCharArray()));
	}
	
	public Message decodeAlpha(CodedMessage plaintext) {
		return new Message(decipherAlpha(plaintext.getMessage().toCharArray()));
	}
	
	private char[] decipher(char[] ciphertext) {
		// The char primitive data type in Java contains
		// all 65535 characters of the Unicode character set.
		// Therefore, checks are done to ensure a wrapping around
		// when subtracting.

		char[] plaintext = new char[ciphertext.length];
		
		for (int i = 0; i < ciphertext.length; i++) {
			int result = (ciphertext[i] - shift);
			result = (result < 0) ? UNICODE+result : result;
			plaintext[i] = (char)(result);
		}
		
		return plaintext;
		
	}
	
	private char[] decipherAlpha(char[] ciphertext) {
		// Performs the decoding by ignoring all non-alphabet
		// characters and leaving them as they are, and then decoding
		// all alphabet characters modulo 26 (i.e. wraps them around
		// the alphabet); this is the conventional Vigenere cipher.
		
		char[] plaintext = new char[ciphertext.length];
		
		for (int i = 0; i < ciphertext.length; i++) {
			if (Character.isLetter(ciphertext[i])) {
				int result = ((Character.getNumericValue(ciphertext[i])-10)
						- shift)%26;
				result = (result<0) ? 26+result : result;
				result += (Character.isUpperCase(ciphertext[i])) ? UPPERCASE : LOWERCASE;
				plaintext[i] = (char)(result);
			}
			else plaintext[i] = ciphertext[i];
		}
		
		return plaintext;
		
	}
	
}
