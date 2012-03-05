// An encapsulation of either a set of
// or a single String representing the
// key for a Vigenere or similar cipher.

// If passed to one of those cipher objects,
// depending on the implementation, this object
// can have its index incremented (i.e. change
// the current String represented within the object)
// without affecting the cipher so the next encoding
// uses a new key.

// Keys are stored in an ArrayList and is done so
// in a way to hide the complexity -- even when
// values are grabbed they are grabbed as either a
// String array or a single String. This
// method of implementation allows for keys to be
// added or removed with ease.

// See the KeyDemo.java source for an example of
// implementation.

package lib;
import java.io.*;
import java.util.Arrays;
import java.util.Random;
import java.util.ArrayList;

public class Key {
	
	// Attributes
	
	private ArrayList<String> keyset;	// The set of all strings representing keys.
	private int keyindex;				// Specifies which key string is being used.
	private final int SIZE = 1024;		// Specifies length of each key in the keyset.
	
	// Constructor
	
	public Key() {
	}
	
	public Key(String key) {
		// If a single key is passed in.
		keyset = new ArrayList<String>();
		keyset.add(key);
	}
	
	public Key(String[] keys) {
		keyset = new ArrayList<String>(Arrays.asList(keys));
	}
	
	public Key(String[] keys, int k) {
		keyset = new ArrayList<String>(Arrays.asList(keys));
		keyindex = k;
	}
	
	public Key(ArrayList<String> keys) {
		keyset = keys;
	}
	
	public Key(ArrayList<String> keys, int k) {
		keyset = keys;
		keyindex = k;
	}
	
	// Get, set methods.
	
	public String[] getKeyset() {
		// Returns the full array of
		// keys as a String array.

		return keyset.toArray(new String[keyset.size()]);
	}
	
	public String getKey() {
		// Returns the key currently
		// associated to the set index of
		// the key array.
		
		return keyset.get(keyindex);
	}
	
	public int getIndex() {
		// Gets the current set index.
		
		return keyindex;
	}
	
	public int getLength() {
		// Gets length of the keyset
		// (i.e. how many keys are stored).
		
		return keyset.size();
	}
	
	public void setKeyset(ArrayList<String> k) {
		keyset = k;
	}
	
	public void setKeyset(String[] k) {
		// Sets the key array.
		
		keyset = new ArrayList<String>(Arrays.asList(k));
	}
	
	public void setKey(String k) {
		// Sets the key at the current
		// index.
		
		keyset.set(keyindex, k);
	}
	
	public boolean setKey(String k, int i) {
		// Sets the key at a given index.
		
		if (i < getLength()) keyset.set(i,k);
		else return false;
		return true;
	}
	
	public boolean setIndex(int i) {
		// Error checking is done to
		// ensure index set is possible
		// (within bounds of array).
		
		if (i < getLength()) keyindex = i;
		else return false;
		return true;
	}
	
	// Add/Remove methods.
	
	public boolean removeKey(int i) {
		// Removes a key at an index
		// if possible.
		
		if (i < getLength()) {
			keyset.remove(i);
			if (keyindex == i) keyindex--;
			if (keyindex < 0) keyindex = 0;
			return true;
		}
		else return false;
	}
	
	public int addKey(String s) {
		// Adds a key and returns
		// the index of this new
		// element.
		
		keyset.add(s);
		return getLength()-1;
	}
	
	// Other methods.
	
	public boolean hasKey(String k) {
		// Returns true if the given key
		// is contained in the keyset.
		
		return keyset.contains(k);
	}
	
	public boolean incrementIndex() {
		// Increments index by 1 if possible.
		
		if (keyindex + 1 < getLength()) keyindex++;
		else return false;
		return true;
	}
	
	public boolean decrementIndex() {
		// Decreases index by 1 if possible.
		
		if (keyindex - 1 > 0) keyindex--;
		else return false;
		return true;
	}
	
	public int randomIndex() {
		// Sets the key index to a random value 
		// within the confines of the array. No
		// checking is done here to whether or not
		// it has been used already (expected to be 
		// done by the implementer).
		
		// Index is returned.
		
		Random rng = new Random();
		int i = rng.nextInt(getLength());
		setIndex(i);
		return i;
	}
	
	// String processing, file I/O.
	
	private void splitString(String str) {
		// Helper function. Splits a large
		// string into chunks of SIZE (constant).
		// It then assigns this split string to
		// the keyset attribute (key array).
		
		int len = str.length()/SIZE;
		ArrayList<String> set = new ArrayList<String>();
		for (int i = 0; i < len; i++)
			set.add(str.substring(i*SIZE,(i*SIZE)+SIZE));
		keyset = set;
		
	}
	
	public void generate(int len) {
		// Generates a random key of characters,
		// and assigns it to the key array.
		
		// Length specified is how many chunks of
		// SIZE are generated.
		
		if (len <= 0) return;
		
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
			while ((r = br.readLine()) != null) read += r;
			
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
			
			// Will overwrite the file if already present.
			
			file.createNewFile();
			output = new BufferedWriter(new FileWriter(file));
		
			for (int i = 0; i < getLength(); i++) output.write(keyset.get(i) + "\n");
			
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
