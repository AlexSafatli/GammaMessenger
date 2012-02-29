// Shows how to use the relevant
// Cipher objects.

package lib;
import java.util.Scanner;

public class VigDemo {
	
	public static void main(String[] args) {
		
		Scanner k = new Scanner(System.in);
		
		// Get input.
		
		System.out.print("Enter a string: ");
		String msg = k.nextLine();
		
		// Generate key.
		
		Key key = new Key();
		key.generate(2);
		
		// Make cipher objects.
		
		Vigenere v = new Vigenere(key);
		Caesar c = new Caesar();
		
		// Output.
		
		System.out.println("\n[VIGENERE]\n");
		System.out.println("--- Encoding 1 ---\n");
		
		CodedMessage s = v.encode(msg); // Encode message (Vigenere).
		String keyShow = key.toString().substring(0,25); // Only show part of key.
		
		System.out.println("Key: " + keyShow + "...\n");
		System.out.println("Encoded Message: " + s);
		System.out.println("Associated Timestamp: " + s.getTime());
		System.out.println("This message was encoded at " + s.getTime().getHour()
				+ " 'o clock.\n");
		System.out.println("Decoded Message: " + v.decode(s));
		
		System.out.println("\n--- Encoding 2 ---\n");
		
		key.incrementIndex(); // Increment the key index.
		s = v.encode(msg); // Encode message again (Vigenere).
		keyShow = key.toString().substring(0,25); // Only show part of (new) key.
		
		System.out.println("Key: " + keyShow + "...\n");
		System.out.println("Encoded Message: " + s);
		System.out.println("Associated Timestamp: " + s.getTime());
		System.out.println("This message was encoded at " + s.getTime().getHour()
				+ " 'o clock.\n");
		System.out.println("Decoded Message: " + v.decode(s));
		
		System.out.println("\n[CAESAR] Shift = " + c.getShift() + "\n");
		
		s = c.encode(msg); // Encode message (Caesar).
		
		System.out.println("Encoded Message: " + s);
		System.out.println("Decoded Message: " + c.decode(s));
		
	}
	
}
