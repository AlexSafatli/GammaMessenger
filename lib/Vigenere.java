// A class dedicated to encoding and decoding
// a Message into a CodedMessage, or vice versa,
// respectively using both a classical and 
// modern implementation of the Vigenere Cipher.

package lib;

public class Vigenere implements Cipher {
	
	// Attributes
	
	private char[] key;
	
	// Constructor
	
	public Vigenere() {
		
	}
	
	public Vigenere(String k) {
		
		key = k.toCharArray();
	}
	
	public Vigenere(char[] k) {
		
		key = k;
		
	}
	
	// Get, set methods.
	
	
	public void setKey(String k) {
		key = k.toCharArray();
	}
	
	public void setKey(char[] k) {
		key = k;
	}
	
	public String getKey() {
		return new String(key);
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
			ciphertext[i] = (char)((plaintext[i] + key[i%key.length])%UNICODE);
		
		return ciphertext;
		
	}
	
	private char[] cipherAlpha(char[] plaintext) {
		// Performs the encoding by ignoring all non-alphabet
		// characters and leaving them as they are, and then encoding
		// all alphabet characters modulo 26 (i.e. wraps them around
		// the alphabet); this is the conventional Vigenere cipher.
		
		char[] ciphertext = new char[plaintext.length];
		
		for (int i = 0; i < plaintext.length; i++) {
			if (Character.isLetter(plaintext[i])) {
				int result = (Character.getNumericValue(plaintext[i])
						+ Character.getNumericValue(key[i%key.length]) - 20)%26;
				result += (Character.isUpperCase(plaintext[i])) ? UPPERCASE : LOWERCASE;
				ciphertext[i] = (char)(result);
			}
			else ciphertext[i] = plaintext[i];
		}
		
		return ciphertext;
		
	}
	
	// Decoding methods.
	
	public Message decode(String ciphertext) {
		return new Message(decipher(ciphertext.toCharArray()));
	}
	
	public Message decode(CodedMessage ciphertext) {
		return new Message(decipher(ciphertext.getMessage().toCharArray()));
	}
	
	public Message decodeAlpha(String ciphertext) {
		return new Message(decipherAlpha(ciphertext.toCharArray()));
	}
	
	public Message decodeAlpha(CodedMessage ciphertext) {
		return new Message(decipherAlpha(ciphertext.getMessage().toCharArray()));
	}
	
	private char[] decipher(char[] ciphertext) {
		// The char primitive data type in Java contains
		// all 65535 characters of the Unicode character set.
		// Therefore, checks are done to ensure a wrapping around
		// when subtracting.

		char[] plaintext = new char[ciphertext.length];
		
		for (int i = 0; i < ciphertext.length; i++) {
			int result = (ciphertext[i] - key[i%key.length]);
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
						- (Character.getNumericValue(key[i%key.length])-10))%26;
				result = (result<0) ? 26+result : result;
				result += (Character.isUpperCase(ciphertext[i])) ? UPPERCASE : LOWERCASE;
				plaintext[i] = (char)(result);
			}
			else plaintext[i] = ciphertext[i];
		}
		
		return plaintext;
		
	}
	
}
