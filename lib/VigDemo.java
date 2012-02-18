package lib;
import java.util.Scanner;

public class VigDemo {
	
	public static void main(String[] args) {
		
		Scanner k = new Scanner(System.in);
		
		System.out.print("Enter a string: ");
		String msg = k.nextLine();
		
		System.out.print("[CAESAR] Enter a shift amount: ");
		int shift = k.nextInt();
		
		Key key = new Key();
		key.generate(2);
		Vigenere v = new Vigenere(key);
		Caesar c = new Caesar(shift);
		
		System.out.println("\n[VIGENERE]\n");
		
		System.out.println("--- Encoding 1 ---\n");
		CodedMessage s = v.encode(msg);
		System.out.println("Encoded Message: " + s);
		System.out.println("Associated Timestamp: " + s.getTime());
		System.out.println("This message was encoded at " + s.getTime().getHour()
				+ ":" + s.getTime().getMinute() + " 'o clock.");
		System.out.println("Decoded Message: " + v.decode(s));
		
		System.out.println("\n--- Encoding 2 ---\n");
		key.incrementIndex();
		s = v.encode(msg);
		System.out.println("Encoded Message: " + s);
		System.out.println("Associated Timestamp: " + s.getTime());
		System.out.println("This message was encoded at " + s.getTime().getHour()
				+ ":" + s.getTime().getMinute() + " 'o clock.");
		System.out.println("Decoded Message: " + v.decode(s));
		
		System.out.println("\n[CAESAR] Shift = " + shift + "\n");
		
		s = c.encode(msg);
		System.out.println("Encoded Message: " + s);
		System.out.println("Decoded Message: " + c.decode(s));
		
	}
	
}
