package lib;
import java.util.Scanner;

public class VigDemo {
	
	public static void main(String[] args) {
		
		Scanner k = new Scanner(System.in);
		
		// Get input.
		
		System.out.print("Enter a string: ");
		String msg = k.nextLine();
		
		System.out.print("Enter a shift amount (for the Caesar cipher): ");
		int shift = k.nextInt();
		
		// Generate key.
		
		Key key = new Key();
		key.generate(2);
		
		// Make cipher objects.
		
		Vigenere v = new Vigenere(key);
		Caesar c = new Caesar(shift);
		
		// Output.
		
		System.out.println("\n[VIGENERE]\n");
		System.out.println("--- Encoding 1 ---\n");
		
		CodedMessage s = v.encode(msg); // Encode message (Vigenere).
		
		System.out.println("Key: " + key);
		System.out.println("Encoded Message: " + s);
		System.out.println("Associated Timestamp: " + s.getTime());
		System.out.println("This message was encoded at " + s.getTime().getHour()
				+ ":" + s.getTime().getMinute() + " 'o clock.");
		System.out.println("Decoded Message: " + v.decode(s));
		
		System.out.println("\n--- Encoding 2 ---\n");
		
		key.incrementIndex(); // Increment the key index.
		s = v.encode(msg); // Encode message again (Vigenere).
		
		System.out.println("Key: " + key);
		System.out.println("Encoded Message: " + s);
		System.out.println("Associated Timestamp: " + s.getTime());
		System.out.println("This message was encoded at " + s.getTime().getHour()
				+ ":" + s.getTime().getMinute() + " 'o clock.");
		System.out.println("Decoded Message: " + v.decode(s));
		
		System.out.println("\n[CAESAR] Shift = " + shift + "\n");
		
		s = c.encode(msg); // Encode message (Caesar).
		
		System.out.println("Encoded Message: " + s);
		System.out.println("Decoded Message: " + c.decode(s));
		
	}
	
}
