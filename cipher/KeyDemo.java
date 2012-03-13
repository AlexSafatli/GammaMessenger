// Explore the file I/O operation(s)
// of the Key object.

package cipher;
import java.util.Scanner;

public class KeyDemo {
	
	public static void main(String[] args) {
		
		Scanner k = new Scanner(System.in);
		
		System.out.print("Write to or open a file? (0/1) ");
		int cmd = k.nextInt();
		
		k.nextLine(); // Correct for line skip.
		System.out.print("Enter a path: ");
		String path = k.nextLine();
		
		Key key = new Key();
		
		switch (cmd) {
		
		case 0:
			key.generate(5); // 5*1024-length String.
			key.saveFile(path);
			System.out.println("File written.");
			break;
			
		case 1:
			key.openFile(path);
			System.out.println("Key:");
			String[] arr = key.getKeyset();
			for (int i = 0; i < arr.length; i++)
				System.out.println(arr[i]);
			break;
			
		}
		
	}
	
}
