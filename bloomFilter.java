//Christina Burris - 4/23/23

package bloomFilter; //a simple implementation
/*The implementation of the Bloom Filter is the algorithm.
 * See: https://en.wikipedia.org/wiki/Bloom_filter#Algorithm_description
 */

import java.util.BitSet;

public class BloomFilter {
	
	public static void main(String[] args) {
		
		int m = 4096; //size of bit array
		
		//String test of Bloom Filter
		MyBloomFilter stringFilter = new MyBloomFilter(m);
		//add String values
		stringFilter.add("Lavandula angustifolia");
		stringFilter.add("Aloe barbadensis");
		stringFilter.add("Calendula officinalis");		
		stringFilter.add("Matricaria recutita");
		stringFilter.add("Melissa officinalis");
		stringFilter.add("Rosmarinus officinalis");
		stringFilter.add("Thymus vulgaris");
		//List values to screen
		System.out.println("Bloom Filter Test 1 - Strings");
		System.out.println("String values added:");
		System.out.println("Lavandula angustifolia, Aloe barbadensis, Calendula officinalis, Matricaria recutita, Melissa officinalis, Rosmarinus officinalis, Thymus vulgaris");
		System.out.println();
		//Check for an entered value
		System.out.println("Checking for: Aloe barbadensis...");
		if (stringFilter.query("Aloe barbadensis"))
			System.out.println("Aloe barbadensis MIGHT be entered.");
		//"might" because bloom filters can return false positives
		//although that's clearly not the case here
		else
			System.out.println("Aloe barbadensis has definitely NOT been entered.");
		System.out.println();
		//Check for a value that has not been entered
		System.out.println("Checking for: Rafflesia arnoldii...");
		if (stringFilter.query("Rafflesia arnoldii"))
			System.out.println("Rafflesia arnoldii is probably entered.");
		else
			System.out.println("Rafflesia arnoldii has definitely NOT been entered.");
		System.out.println();
		System.out.println();
		
		//Integer Test of Bloom Filter
		MyBloomFilter integerFilter = new MyBloomFilter(m);
		//Add Integer values
		integerFilter.add(Integer.valueOf(42));
		integerFilter.add(Integer.valueOf(24601));
		integerFilter.add(Integer.valueOf(1989));
		integerFilter.add(Integer.valueOf(2001));
		integerFilter.add(Integer.valueOf(2010));
		integerFilter.add(Integer.valueOf(2061));
		integerFilter.add(Integer.valueOf(3001));
		//List values to screen
		System.out.println("Bloom Filter Test 2 - Integers");
		System.out.println("Integer values added:");
		System.out.println("42, 24601, 1989, 2001, 2010, 2061, 3001");
		System.out.println();
		//Check for an entered value
		System.out.println("Checking for 1989...");
		if (integerFilter.query(Integer.valueOf(1989)))
			System.out.println("1989 is probably entered");
		else
			System.out.println("1989 has definitely NOT been entered");
		System.out.println();
		//Check for a value that has not been entered
		System.out.println("Checking for 1701...");
		if (integerFilter.query(Integer.valueOf(1701)))
			System.out.println("1701 is probably entered");
		else 
			System.out.println("1701 has definitely NOT been entered");
				
		
	}//main
	
}//BloomFilter

	
class MyBloomFilter {
	
	int size;
	BitSet bitArray;
	
	public MyBloomFilter() {
		//pro forma default constructor
		//k(# of keys) will always be 3 for this program
		this.size = 4096;
		this.bitArray = new BitSet(this.size);
	}
	
	public MyBloomFilter (int capacity) {
		//k(# of keys) will always be 3 for this program
		this.size = capacity;
		this.bitArray = new BitSet(this.size);
		bitArray.clear();
	}
	
	public void add(Object obj){
		//get keys(hashed indexes) and set all to '1'
		int k1, k2, k3;
		k1 = key1(obj, size);
			this.bitArray.set(k1);
		k2 = key2(obj, size);
			bitArray.set(k2);
		k3 = key3(obj, size);
			bitArray.set(k3);
	}
	
	public boolean query(Object obj){
		//get keys(hashed indexes)
		int k1, k2, k3;
		k1 = key1(obj, size);
		k2 = key2(obj, size);
		k3 = key3(obj, size);
		//check indices & return true if all set to '1'/true
		if (bitArray.get(k1) && bitArray.get(k2) && bitArray.get(k3))
			return true;
		else
		//if any are zero then item was never entered
			return false;
	}
	
	private int key1(Object obj, int m) {  //key1
		  return (obj.hashCode() & 0x7fffffff) % m; 
		  //ensures hash is positive and less than size of bit array
		}
	
	private int key2(Object obj, int m) {  //key2
		  return (obj.hashCode() & 0x7ffffffe) % m; 
		  //ensures hash is positive and less than size of bit array
		}
	
	private int key3(Object obj, int m) {  //key3
		  return (obj.hashCode() & 0x7fffffef) % m; 
		  //ensures hash is positive and less than size of bit array
		}
	
}//MyBloomFilter