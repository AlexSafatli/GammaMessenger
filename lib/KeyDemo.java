// Explore the file I/O operation(s)
// of the Key object.

package lib;
import java.util.Scanner;

public class KeyDemo {
	
	public static void main(String[] args) {
		
		Scanner k = new Scanner(System.in);
		
		System.out.print("Enter a path: ");
		String path = k.nextLine();
		
		Key key = new Key();
		key.generate(5); // 5*1024-length String.
		key.saveFile(path);
		System.out.println("File written.");
		
	}
	
}
