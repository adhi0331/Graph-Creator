/*
 * Class for the queue which is used in the adjacency matrix
 */
package GraphCreator;

import java.util.ArrayList;

public class Queue {
	
	ArrayList<String> queue = new ArrayList<String>();
	
	public void enqueue(String s) { //adds a string to the queue
		queue.add(s);
	}
	
	public String dequeue() { //removes first object in queue
		String s = queue.get(0);
		queue.remove(0);
		return s;
	}
	
	public boolean isEmpty() { //checks whether queue is empty
		return queue.isEmpty();
	}
}
