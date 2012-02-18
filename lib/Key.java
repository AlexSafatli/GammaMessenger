// An encapsulation of either a set of
// or a single String representing the
// key for a Vigenere or similar cipher.

// If passed to one of those cipher objects,
// depending on the implementation, this object
// can have its index incremented (i.e. change
// the current String represented within the object)
// without affecting the cipher so the next encoding
// uses a new key.

package lib;
import java.io.*;
import java.util.Random;

public class Key {
	
	// Attributes
	
	private String[] keyset;			// The set of all strings representing keys.
	private int keyindex;				// Specifies which key string is being used.
	private final int SIZE = 1024;		// Specifies length of each key in the String[].
	
	// Constructor
	
	public Key() {
	}
	
	public Key(String key) {
		keyset = new String[1];
		keyset[0] = key;
	}
	
	public Key(String[] keys) {
		keyset = keys;
	}
	
	public Key(String[] keys, int k) {
		keyset = keys;
		keyindex = k;
	}
	
	// Get, set methods.
	
	public String[] getKeyset() {
		return keyset;
	}
	
	public String getKey() {
		return keyset[keyindex];
	}
	
	public int getIndex() {
		return keyindex;
	}
	
	public void setKeyset(String[] k) {
		keyset = k;
	}
	
	public void setKey(String k) {
		keyset[keyindex] = k;
	}
	
	public void setIndex(int i) {
		keyindex = i;
	}
	
	public boolean incrementIndex() {
		// Increments index by 1 if possible.
		
		if (keyindex + 1 < keyset.length) keyindex++;
		else return false;
		return true;
			
	}
	
	// String processing, file I/O.
	
	public void splitString(String str) {
		int len = str.length()/SIZE;
		String[] set = new String[len];
		for (int i = 0; i < len; i++)
			set[i] = str.substring(i*SIZE,(i*SIZE)+SIZE);
		keyset = set;
	}
	
	public void generate(int len) {
		// Generates a random key of characters,
		// and assigns it to the key array.
		
		Random rng = new Random();
		String rns = "";
		for (int i = 0; i < len*SIZE; i++) 
			rns += (char)(rng.nextInt(26) + 'A');
		splitString(rns);
	}
	
	public void openFile(String path) {
		// Opens a file and reads it in as
		// a string array to be used as the key.
		
		String read = "";
		try {
			// Open file.
			FileInputStream fstream = new FileInputStream(path);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			// Read line-by-line.
			String r;
			while ((r = br.readLine()) != null) {
				read += r;
			}
			// Close stream.
			in.close();
			
		}
		catch (Exception err) {
			System.err.println("Error: " + err.getMessage());
		}
		splitString(read); // Pass the file to be split into a String array.
	}
	
	public void saveFile(String path) {
		// Saves the current keyset to a file.
		
		Writer output = null;
		File file = new File(path);
		
		try {
			output = new BufferedWriter(new FileWriter(file));
		
			for (int i = 0; i < keyset.length; i++) output.write(keyset[i]);
			
			output.close();
		}
		catch (Exception err) {
			System.err.println("Error: " + err.getMessage());
		}
	}
	
	// toString
	
	public String toString() {
		return getKey();
	}
	
}
