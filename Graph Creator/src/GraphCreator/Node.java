/*
 * Class for the nodes
 */
package GraphCreator;

public class Node {

	int x;
	int y;
	String label;
	boolean highlighted;
	
	public Node(int newx, int newy, String newLabel) { //input an x, y, and label
		x = newx;
		y = newy;
		label = newLabel;
		highlighted = false;
	}
	
	//getters and setters for all variables
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public boolean getHighlighted() {
		return highlighted;
	}
	public void setHighlighted(boolean highlighted) {
		this.highlighted = highlighted;
	}
	
}
