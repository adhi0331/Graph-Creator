/*
 * This is the class for the graph panel which the graph is drawn on
 */
package GraphCreator;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GraphPanel extends JPanel {

	ArrayList<Node> nodeList = new ArrayList<Node>();
	ArrayList<Edge> edgeList = new ArrayList<Edge>();
	int circleRadius = 20;
	
	ArrayList<ArrayList<Boolean>> adjacency = new ArrayList<ArrayList<Boolean>>();
	
	public GraphPanel() {
		super();
	}
	
	public ArrayList<String> getConnectedLabels(String label) { //method that gets the connected labels
		ArrayList<String> toReturn = new ArrayList<String>();
		int b = getIndex(label);
		for (int a = 0; a < adjacency.size(); a++) {
			if (adjacency.get(b).get(a) == true && !nodeList.get(a).getLabel().contentEquals(label)) {
				toReturn.add(nodeList.get(a).getLabel());
			}
		}
		return toReturn;
	}
	
	public void printAdjacency() { //method that prints the adjacency matrix
		System.out.println();
		for (int a = 0; a < adjacency.size(); a++) {
			for (int b = 0; b < adjacency.get(0).size(); b++) {
				System.out.print(adjacency.get(a).get(b) + "\t");
			}
			System.out.println();
		}
	}
	
	public void addNode(int newx, int newy, String newLabel) { //adds a node to the graph
		nodeList.add(new Node(newx, newy, newLabel));
		adjacency.add(new ArrayList<Boolean>());
		for (int i = 0; i < adjacency.size() - 1; i++) {
			adjacency.get(i).add(false);
		}
		for (int i = 0; i < adjacency.size(); i++) {
			adjacency.get(adjacency.size() - 1).add(false);
		}
		printAdjacency();
	}
	
	public Node getNode(int x, int y) { //gets node with x and y input
		for (int i = 0; i < nodeList.size(); i++) {
			Node node = nodeList.get(i);
			double radius = Math.sqrt(Math.pow(x-node.getX(), 2) + Math.pow(y-node.getY(), 2));
			if (radius < circleRadius) {
				return node;
			}
		}
		return null;
	}
	
	public Node getNode(String s) { //gets a node with string input
		for (int a = 0; a < nodeList.size(); a++) {
			Node node = nodeList.get(a);
			if (s.equals(node.getLabel())) {
				return node;
			}
		}
		return null;
	}
	
	public int getIndex(String s) { //gets the index of a node in the node list
		for (int a = 0; a < nodeList.size(); a++) {
			Node node = nodeList.get(a);
			if (s.equals(node.getLabel())) {
				return a;
			}
		}
		return -1;
	}
	
	public ArrayList<Edge> getEdgeList() { //gets edge list
		return edgeList;
	}

	public ArrayList<Node> getNodeList() { //gets node list
		return nodeList;
	}

	public void setNodeList(ArrayList<Node> nodeList) { //sets node list
		this.nodeList = nodeList;
	}

	public void setEdgeList(ArrayList<Edge> edgeList) { //sets edge list
		this.edgeList = edgeList;
	}
	
	public Edge getEdge(Node n) { //gets the edge with a node that is second node
		for (int i = 0; i < edgeList.size(); i++) {
			Edge e = edgeList.get(i);
			if (e.getOtherEnd(n).equals(e.getFirst())) {
				return e;
			}
		}
		return null;
	}

	public boolean nodeExists(String s) { //checks whether node exists
		for (int i = 0; i < nodeList.size(); i++) {
			if (s.equals(nodeList.get(i).getLabel())) {
				return true;
			}
		}
		return false;
	}
	
	public void addEdge(Node first, Node second, String newLabel) { //adds edge to edge list and updates adjacency matrix
		edgeList.add(new Edge(first, second, newLabel));
		int firstIndex = 0;
		int secondIndex = 0;
		for (int i = 0; i < nodeList.size(); i++) {
			if (first.equals(nodeList.get(i))) {
				firstIndex = i;
			}
			if (second.equals(nodeList.get(i))) {
				secondIndex = i;
			}
		}
		adjacency.get(firstIndex).set(secondIndex, true);
		adjacency.get(secondIndex).set(firstIndex, true);
		printAdjacency();
	}
	
	public void paintComponent(Graphics g) { //draws the graph panel
		super.paintComponent(g);
		//draw my stuff
		for (int i = 0; i < nodeList.size(); i++) { //Nodes drawing
			if (nodeList.get(i).getHighlighted() == true) { //highlights node red when selected
				g.setColor(Color.RED);
			}
			else {
				g.setColor(Color.BLACK);
			}
			g.drawOval(nodeList.get(i).getX() - circleRadius, 
					nodeList.get(i).getY() - circleRadius, 40, 40);
			g.drawString(nodeList.get(i).getLabel(), nodeList.get(i).getX(),
					nodeList.get(i).getY());
		}
		
		for (int i = 0; i < edgeList.size(); i++) { //Edges drawing
			g.setColor(Color.BLACK);
			g.drawLine(edgeList.get(i).getFirst().getX(), 
					edgeList.get(i).getFirst().getY(), 
					edgeList.get(i).getSecond().getX(), 
					edgeList.get(i).getSecond().getY());
			int fx = edgeList.get(i).getFirst().getX();
			int fy = edgeList.get(i).getFirst().getY();
			int sx = edgeList.get(i).getSecond().getX();
			int sy = edgeList.get(i).getSecond().getY();
			g.drawString(edgeList.get(i).getLabel(), 
					Math.min(fx, sx) + (Math.abs(sx - fx)/2), 
					Math.min(fy, sy) + (Math.abs(sy - fy)/2));
		}
	}

	public void stopHighlighting() { //stops the highlighting when edge is set
		for (int i = 0; i < nodeList.size(); i++) {
			nodeList.get(i).setHighlighted(false);
		}
		
	}
}
