import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Vector;

public class MyPanel extends JPanel {
	private int nodeNr = 1;
	private int node_diam = 30;
	private int node_radius = node_diam / 2;
	private Vector<Node> listaNoduri;
	private Vector<Arc> listaArce;
	Point pointStart = null;
	Point pointEnd = null;
	boolean isDragging = false;
	public MyPanel()
	{
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
							node1 = new Node(n.getCoordX(), n.getCoordY());
						}
						if (isInsideNode(pointEnd.x, pointEnd.y, n))
						{
							isInsideNode2= true;
							node2 = new Node(n.getCoordX(), n.getCoordY());
						}

					}
					if (isInsideNode1 && isInsideNode2 && node1 != node2)
					{
						listaArce.add(arc);
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
}
