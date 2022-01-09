/*
 * Class that creates the edges
 */
package GraphCreator;

public class Edge {

	Node first;
	Node second;
	String label;
	
	public Edge(Node newfirst, Node newsecond, String newLabel) { //input first node, second node, and label
		first = newfirst;
		second = newsecond;
		label = newLabel;
	}
	
	public Node getOtherEnd(Node n) { //returns the other node
		if (first.equals(n)) {
			return second;
		}
		else if (second.equals(n)) {
			return first;
		}
		else {
			return null;
		}
	}
	
	//getters and setters for all variables
	
	public Node getFirst() {
		return first;
	}
	public void setFirst(Node first) {
		this.first = first;
	}
	public Node getSecond() {
		return second;
	}
	public void setSecond(Node second) {
		this.second = second;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
}
