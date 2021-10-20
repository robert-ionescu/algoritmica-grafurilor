import java.awt.*;

public class Arc
{
	private Point start;
	private Point end;
	
	public Arc(Point start, Point end)
	{
		this.start = start;
		this.end = end;
	}
	
	public void drawUndirectedArc(Graphics g)
	{
		if (start != null)
		{
            g.setColor(Color.RED);
            g.drawLine(start.x, start.y, end.x, end.y);
        }
	}
	public void drawDirectedArc(Graphics g)
	{
		int dx = end.x - start.x, dy = end.y - start.y;
		double D = Math.sqrt(dx*dx + dy*dy);
		double xm = D - 10, xn = xm, ym = 10, yn = -10, x;
		double sin = dy / D, cos = dx / D;

		x = xm*cos - ym*sin + start.x;
		ym = xm*sin + ym*cos + start.y;
		xm = x;

		x = xn*cos - yn*sin + start.x;
		yn = xn*sin + yn*cos + start.y;
		xn = x;

		int[] xpoints = {end.x, (int) xm, (int) xn};
		int[] ypoints = {end.y, (int) ym, (int) yn};

		g.drawLine(start.x, start.y, end.x, end.y);
		g.fillPolygon(xpoints, ypoints, 3);
	}
}
