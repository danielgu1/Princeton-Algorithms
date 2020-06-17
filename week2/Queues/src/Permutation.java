import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RandomizedQueue<String> queue = new RandomizedQueue<>();
        int k = StdIn.readInt();
        System.out.println(k);
        String item = StdIn.readString();
        while (!item.equals("stop")) {
            queue.enqueue(item);
            item = StdIn.readString();
        
        for (int i = 0; i < k; i++) {
            StdOut.println(queue.dequeue());
        }
        }
	}
}


