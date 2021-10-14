import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Node
{
	private int coordX;
	private int coordY;
	private int number;
	
	public Node(int coordX, int coordY, int number)
	{
		this.coordX = coordX;
		this.coordY = coordY;
		this.number = number;
	}
	public Node(int coordX, int coordY)
	{
		this.coordX = coordX;
		this.coordY = coordY;
	}


	public int getCoordX() {
		return coordX;
	}
	public void setCoordX(int coordX) {
		this.coordX = coordX;
	}
	public int getCoordY() {
		return coordY;
	}
	public void setCoordY(int coordY) {
		this.coordY = coordY;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}

	public void drawNode(Graphics gg, int node_diam)
	{
		Graphics2D g = (Graphics2D) gg;
		int node_radius = node_diam / 2;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
		g.setColor(Color.RED);
		g.setFont(new Font("TimesRoman", Font.BOLD, 15));
        g.fillOval(coordX - node_radius, coordY - node_radius, node_diam, node_diam);
        g.setColor(Color.WHITE);
        //g.drawOval(coordX, coordY, node_diam, node_diam);
		Ellipse2D.Double circle = new Ellipse2D.Double(coordX - node_radius, coordY - node_radius, node_diam, node_diam);
		g.draw(circle);
        if(number < 10)
        	g.drawString(((Integer)number).toString(), coordX -4, coordY+6);
        else
        	g.drawString(((Integer)number).toString(), coordX-9, coordY+6);
	}
}
