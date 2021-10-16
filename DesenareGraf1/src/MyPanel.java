import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Vector;


public class MyPanel extends JPanel {
	Vector <AbstractMap.SimpleEntry<Node,Node>> listaAdiacentaNeorientata = new Vector<AbstractMap.SimpleEntry<Node,Node>>();
	Vector <AbstractMap.SimpleEntry<Node,Node>> listaAdiacentaOrientata = new Vector<AbstractMap.SimpleEntry<Node,Node>>();
	public boolean isUndirectedGraph;
	private int nodeNr = 1;
	private int node_diam = 30;
	private int node_radius = node_diam / 2;
	private Vector<Node> listaNoduri;
	private Vector<Arc> listaArce;
	Point pointStart = null;
	Point pointEnd = null;
	boolean isDragging = false;
	public static AbstractMap<AbstractMap.SimpleEntry<Integer,Integer>, Integer> timesNodesHaveBeenLinked = new HashMap<>();
	public MyPanel()
	{
		//Custom button text
		Object[] options = {"Undirected Graph","Directed Graph"
				};
		int n = JOptionPane.showOptionDialog(JOptionPane.getRootFrame(),
				"Select the type of graph that you'd like to draw",
				"Graph Selection",
				JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				options,
				options[1]);
		if (n == 0)
		{
			isUndirectedGraph = true;
		}
		else{
			isUndirectedGraph = false;
		}
		//System.out.println(n);
		listaNoduri = new Vector<Node>();
		listaArce = new Vector<Arc>();
		// borderul panel-ului
		setBorder(BorderFactory.createLineBorder(Color.black));
		addMouseListener(new MouseAdapter() {
			//evenimentul care se produce la apasarea mousse-ului
			public void mousePressed(MouseEvent e) {
				pointStart = e.getPoint();
			}
			
			//evenimentul care se produce la eliberarea mousse-ului
			public void mouseReleased(MouseEvent e) {
				if (!isDragging) {
					addNode(e.getX(), e.getY());
				}
				else {
					Arc arc = new Arc(pointStart, pointEnd);
					boolean isInsideNode1 = false;
					Node node1 = new Node(Integer.MIN_VALUE, Integer.MIN_VALUE);
					boolean isInsideNode2 = false;
					Node node2 = new Node(Integer.MIN_VALUE, Integer.MIN_VALUE);
					for (Node n: listaNoduri){
						if (isInsideNode(pointStart.x, pointStart.y, n))
						{
							isInsideNode1= true;
							node1 = new Node(n.getCoordX(), n.getCoordY(), n.getNumber());
						}
						if (isInsideNode(pointEnd.x, pointEnd.y, n))
						{
							isInsideNode2= true;
							node2 = new Node(n.getCoordX(), n.getCoordY(), n.getNumber());
						}

					}
					AbstractMap.SimpleEntry<Integer,Integer> p1 = new AbstractMap.SimpleEntry<>(node1.getNumber(),node2.getNumber());
					AbstractMap.SimpleEntry<Integer,Integer> p2 = new AbstractMap.SimpleEntry<>(node2.getNumber(),node1.getNumber());
					if (isInsideNode1 && isInsideNode2 && node1 != node2 && isUndirectedGraph && timesNodesHaveBeenLinked.getOrDefault(p1, 0) == 0 && timesNodesHaveBeenLinked.getOrDefault(p2, 0) == 0)
					{
						System.out.println(timesNodesHaveBeenLinked.getOrDefault(p1, 0));
						System.out.println(timesNodesHaveBeenLinked.getOrDefault(p2, 0));
						AbstractMap.SimpleEntry<Node,Node> entry = new AbstractMap.SimpleEntry<>(node1,node2);
						listaAdiacentaNeorientata.add(entry);
						listaArce.add(arc);
						timesNodesHaveBeenLinked.put(p1, 1);
						timesNodesHaveBeenLinked.put(p2, 1);
					}
					else if (isInsideNode1 && isInsideNode2 && node1 != node2 && !isUndirectedGraph && timesNodesHaveBeenLinked.getOrDefault(p1, 0) < 2 && timesNodesHaveBeenLinked.getOrDefault(p2, 0) <2)
					{
						AbstractMap.SimpleEntry<Node,Node> entry = new AbstractMap.SimpleEntry<>(node1,node2);
						listaAdiacentaOrientata.add(entry);
						listaArce.add(arc);
						var val1 = timesNodesHaveBeenLinked.getOrDefault(p1, 0);
						timesNodesHaveBeenLinked.put(p1, ++val1);
						var val2 = timesNodesHaveBeenLinked.getOrDefault(p2, 0);
						timesNodesHaveBeenLinked.put(p2, ++val2);
					}

				}
				pointStart = null;
				isDragging = false;
			}
		});
		
		addMouseMotionListener(new MouseMotionAdapter() {
			//evenimentul care se produce la drag&drop pe mousse
			public void mouseDragged(MouseEvent e) {
				pointEnd = e.getPoint();
				isDragging = true;
				repaint();
			}
		});
	}

	//metoda care se apeleaza la eliberarea mouse-ului
	private void addNode(int x, int y) {
		boolean nodNeintersectat = true;
		if (listaNoduri.size() > 0) {
			for (Node n : listaNoduri) {
				double distantaDintrePuncte = Math.sqrt(Math.pow(x - n.getCoordX(), 2) + Math.pow(y - n.getCoordY(), 2));
				if (distantaDintrePuncte < node_diam) {
					nodNeintersectat = false;
				}
			}
			if (nodNeintersectat) {
					Node node = new Node(x, y, nodeNr);
					listaNoduri.add(node);
					nodeNr++;
					repaint();
				}
		}
		else {
			Node node = new Node(x, y, nodeNr);
			listaNoduri.add(node);
			nodeNr++;
			repaint();
		}

	}
	boolean isInsideNode(int x, int y, Node n) {
		return(Math.sqrt(Math.pow(Math.abs(x - n.getCoordX()), 2) + Math.pow(Math.abs(y - n.getCoordY()), 2)) <= node_radius);
	}
	//se executa atunci cand apelam repaint()
	protected void paintComponent(Graphics g)
	{

		super.paintComponent(g);//apelez metoda paintComponent din clasa de baza
		g.drawString("This is my Graph!", 10, 20);
		//deseneaza arcele existente in lista
		/*for(int i=0;i<listaArce.size();i++)
		{
			listaArce.elementAt(i).drawArc(g);
		}*/
		for (Arc a : listaArce)
		{
			a.drawArc(g);
		}
		//deseneaza arcul curent; cel care e in curs de desenare
		if (pointStart != null)
		{
			g.setColor(Color.RED);
			g.drawLine(pointStart.x, pointStart.y, pointEnd.x, pointEnd.y);
		}
		//deseneaza lista de noduri
		for(int i=0; i<listaNoduri.size(); i++)
		{
			listaNoduri.elementAt(i).drawNode(g, node_diam);
		}
		/*for (Node nod : listaNoduri)
		{
			nod.drawNode(g, node_diam, node_Diam);
		}*/
	}
	public int[][] CreateUndirectedMatrix()
	{
		int adjacencyMatrix[][] = new int[listaNoduri.size()][listaNoduri.size()];
		for (var perecheNoduri: listaAdiacentaNeorientata)
		{
			adjacencyMatrix[perecheNoduri.getKey().getNumber()][perecheNoduri.getValue().getNumber()] = adjacencyMatrix[perecheNoduri.getValue().getNumber()][perecheNoduri.getKey().getNumber()]
					= 1;
		}
		return adjacencyMatrix;
	}
	public int[][] CreateDirectedMatrix()
	{
		int adjacencyMatrix[][] = new int[listaNoduri.size()][listaNoduri.size()];
		for (var perecheNoduri: listaAdiacentaNeorientata)
		{
			adjacencyMatrix[perecheNoduri.getKey().getNumber()][perecheNoduri.getValue().getNumber()] = 1;
		}
		return adjacencyMatrix;
	}
	public void CreateMatrixFile() throws IOException {
		int adjacencyMatrix[][] = CreateUndirectedMatrix();
			//PrintWriter matrixFile = new PrintWriter("matrix.txt","UTF-8");
			File matrixFile = new File("matrix.txt");
			if (!matrixFile.isFile()) {
				FileWriter fw = new FileWriter(matrixFile);
				fw.write(nodeNr);
				for (int i = 0; i < adjacencyMatrix.length; i++) {
					for (int j = 0; j < adjacencyMatrix[i].length; j++) {
						fw.write(adjacencyMatrix[i][j]);
					}
					fw.write("\n");
				}

			}



		
	}
}
