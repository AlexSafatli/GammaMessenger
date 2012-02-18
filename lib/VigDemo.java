package lib;
import java.util.Scanner;

public class VigDemo {
	
	public static void main(String[] args) {
		
		Scanner k = new Scanner(System.in);
		
		System.out.print("Enter a string: ");
		String msg = k.nextLine();
		
		System.out.print("Enter a key: ");
		String key = k.nextLine();
		
		System.out.print("Enter a shift amount: ");
		int shift = k.nextInt();
		
		Vigenere v = new Vigenere(key);
		Caesar c = new Caesar(shift);
		
		System.out.println("\n[VIGENERE]\n");
		
		CodedMessage s = v.encode(msg);
		System.out.println("Encoded Message: " + s);
		System.out.println("Associated Timestamp: " + s.getTime());
		System.out.println("Decoded Message: " + v.decode(s));
		
		System.out.println("\n[CAESAR] Shift = " + shift + "\n");
		
		s = c.encodeAlpha(msg);
		System.out.println("Encoded Message: " + s);
		System.out.println("Decoded Message: " + c.decodeAlpha(s));
		
	}
	
}
