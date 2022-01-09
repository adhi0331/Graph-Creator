/*
 * This is the main class for Graph Creator. Graph Creator is an application that allows users to
 * create a graph using nodes and edges. Users can find whether two nodes have a connection and can
 * do the traveling salesman problem.
 * Author: Adhithya Ananthan Regina
 * Period: 1
 * Date: 5/23/21
 */
package GraphCreator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class GraphCreator implements ActionListener, MouseListener {

	JFrame frame = new JFrame();
	GraphPanel panel = new GraphPanel();
	JButton nodeB = new JButton("Node");
	JButton edgeB = new JButton("Edge");
	JTextField labelsTF = new JTextField("A");
	JTextField firstNode = new JTextField("First");
	JTextField secondNode = new JTextField("Second");
	JTextField salesmanStartTF = new JTextField("A");
	JButton connectedB = new JButton("Test Connected");
	JButton salesmanB = new JButton("Shortest Path");
	Container west = new Container();
	Container east = new Container();
	Container south = new Container();
	final int NODE_CREATE = 0;
	final int EDGE_FIRST = 1;
	final int EDGE_SECOND = 2;
 	int state = NODE_CREATE;
 	Node first = null;
 	ArrayList<ArrayList<Node>> completed = new ArrayList<ArrayList<Node>>();
 	int Total = 0;
 	
	public GraphCreator() { //Constructor
		frame.setSize(800, 600);
		frame.setLayout(new BorderLayout());
		frame.add(panel,BorderLayout.CENTER);
		west.setLayout(new GridLayout(3,1)); //West contains node and edge interface
		west.add(nodeB);
		nodeB.addActionListener(this);
		nodeB.setOpaque(true);
		west.add(edgeB);
		edgeB.addActionListener(this);
		edgeB.setOpaque(true);
		west.add(labelsTF);
		frame.add(west, BorderLayout.WEST);
		east.setLayout(new GridLayout(3,1));
		east.add(firstNode); //East contains Node connection interface
		east.add(secondNode);
		east.add(connectedB);
		connectedB.addActionListener(this);
		frame.add(east, BorderLayout.EAST);
		panel.addMouseListener(this);
		south.setLayout(new GridLayout(1,2)); //South contains salesman interface
		south.add(salesmanStartTF);
		south.add(salesmanB);
		salesmanB.addActionListener(this);
		frame.add(south, BorderLayout.SOUTH);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		new GraphCreator();
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) { //One going to be used
		if (state == NODE_CREATE) { //creates Nodes
			panel.addNode(e.getX(), e.getY(), labelsTF.getText());
		}
		else if (state == EDGE_FIRST) { //selects first edge
			Node n = panel.getNode(e.getX(), e.getY());
			if (n != null) {
				first = n;
				state = EDGE_SECOND;
				n.setHighlighted(true);
			}
		}
		else if (state == EDGE_SECOND) { //selects second edge and creates the edge
			Node n = panel.getNode(e.getX(), e.getY());
			if (n != null && !first.equals(n)) {
				String s = labelsTF.getText();
				boolean valid = true;
				for (int a = 0; a < s.length(); a++) {
					if (Character.isDigit(s.charAt(a)) == false) { //ensures that edge label is only digits
						valid = false;
					}
				}
				if (valid == true) {
					first.setHighlighted(false); 
					panel.addEdge(first, n, labelsTF.getText());
					first = null;
					state = EDGE_FIRST;
				}
				else {
					JOptionPane.showMessageDialog(frame, "Can only have digits in edge label.");
				}
			}
		}
		
		frame.repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(nodeB)) { //when node button is selected
			nodeB.setBackground(Color.GREEN);
			edgeB.setBackground(Color.LIGHT_GRAY);
			state = NODE_CREATE;
		}
		
		if (e.getSource().equals(edgeB)){ //when edge button is selected
			edgeB.setBackground(Color.GREEN);
			nodeB.setBackground(Color.LIGHT_GRAY);
			state = EDGE_FIRST;
			panel.stopHighlighting();
			frame.repaint();
		}
		
		if (e.getSource().equals(connectedB)) { //when connected is selected
			if (panel.nodeExists(firstNode.getText()) == false) { //ensures that nodes inputed are on the graph
				JOptionPane.showMessageDialog(frame, "First node is not in you graph.");
			}
			else if (panel.nodeExists(secondNode.getText()) == false){
				JOptionPane.showMessageDialog(frame, "Second node is not in you graph.");
			}
			else {
				Queue queue = new Queue();
				ArrayList<String> connectedList = new ArrayList<String>(); 
				connectedList.add(panel.getNode(firstNode.getText()).getLabel());
				
				ArrayList<String> edges = panel.getConnectedLabels(firstNode.getText());
				for (int a = 0; a < edges.size(); a++) {
					//Add connected nodes
					queue.enqueue(edges.get(a)); //creates matrix that keeps track of node connections
				}
				while (queue.isEmpty() == false) {
					String currentNode = queue.dequeue();
					if (connectedList.contains(currentNode) == false) {
						connectedList.add(currentNode);
					}
					edges = panel.getConnectedLabels(currentNode);
					for (int a = 0; a < edges.size(); a++) {
						if (connectedList.contains(edges.get(a)) == false) {
							queue.enqueue(edges.get(a));
						}
					}
				}
				if (connectedList.contains(secondNode.getText())) { //Displays message
					JOptionPane.showMessageDialog(frame, "Connected!");
				}
				else {
					JOptionPane.showMessageDialog(frame, "Not Connected :(");
				}
			}
		}
		
		if(e.getSource().equals(salesmanB)) { //when salesman is selected
			if (panel.getNode(salesmanStartTF.getText()) != null) {
				travelling(panel.getNode(salesmanStartTF.getText()), new ArrayList<Node>(), 0);
				String message = "";
				if (completed.size() != 0) { //checks whether completed has a path
					for (int paths = 0; paths < completed.size(); paths++) {
						for (int nodes = 0; nodes < completed.get(paths).size(); nodes++) {
							message += completed.get(paths).get(nodes).getLabel() + " "; //adds path to message
						}
						
					}
				}
				JOptionPane.showMessageDialog(frame, "The shortest path is: " + message + " with length" + Total); //shows message dialog
				
				
			}
			else { //if valid starting node is not selected
				JOptionPane.showMessageDialog(frame, "Not a valid starting node!");
			}
		}
		
	}
	
	public void travelling(Node n, ArrayList<Node> path, int total) { //method runs the salesman problem
		if (path.size() == panel.getNodeList().size()) {
			if (Total == 0 || total < Total) { //ensures total is the smallest length and that smallest path is added
				Total = total;
				completed.add(path);
				path.remove(path.size() - 1);
			}
			return;
		}
		else {
			for (int a = 0; a < panel.getEdgeList().size(); a++) {
				Edge e = panel.getEdgeList().get(a);
				if (e.getOtherEnd(n) != null && e.getFirst() == n) { 
					if (path.contains(e.getOtherEnd(n)) == false) {
						path.add(e.getOtherEnd(n));
						travelling(e.getOtherEnd(n), path, total + Integer.parseInt(e.getLabel()));
					}
				}
			}
		}
	}

}
