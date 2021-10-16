import javax.swing.*;

public class Graf
{
    public int adjacencyMatrix[][];
    public int nodeNumbers;
    public Graf(int nodeNumbers){
        this.nodeNumbers = nodeNumbers;
        adjacencyMatrix = new int [nodeNumbers][nodeNumbers];
    }
	private static void initUI() {
        JFrame f = new JFrame("Algoritmica Grafurilor");
        //sa se inchida aplicatia atunci cand inchid fereastra
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //imi creez ob MyPanel
        f.add(new MyPanel());

        //setez dimensiunea ferestrei
        f.setSize(500, 500);
        //fac fereastra vizibila
        f.setVisible(true);
    }

	
	public static void main(String[] args)
	{
		//pornesc firul de executie grafic
		//fie prin implementarea interfetei Runnable, fie printr-un ob al clasei Thread
		SwingUtilities.invokeLater(new Runnable() //new Thread()
		{
            public void run() 
            {
            	initUI(); 
            }
        });
	}	
}
