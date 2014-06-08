import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class src extends PApplet {

private int 
    squareSize = 80,
    rows = 8,
    cols = 8;
private Node root;
private Thread build;

public void setup()
{
    size(squareSize * rows, squareSize * cols);
    root = new Node();
    frameRate(60);
    noLoop();
    redraw();
}

public void draw()
{
    build = new Thread(new RunBuild(root));
    System.gc();
    build.start();

    drawBG();
    drawPieces();
}

public void drawBG()
{
    background(0, 150, 0);
    for (int count = 1; count <= rows - 1; count++)
	    line(count * width / rows, height, 
		 count * width / rows, 0);
    for (int count = 1; count <= cols - 1; count++)
	    line(0,     count * height / cols, 
		 width, count * height / cols);
}

public void drawPieces()
{
   for (int row = 0; row < rows; row++)
	{for (int col = 0; col < cols; col++)
		{
		    if (root.check(row, col) == -1)
			fill(255);
		    else if (root.check(row, col) == 1)
			fill(0);
		    else
			continue;
		    ellipse(squareSize * row + squareSize / 2, 
			    squareSize * col + squareSize / 2, 
			    squareSize - 10, 
			    squareSize - 10);
		}
	}
}

public void mouseClicked()
{
    loop();
    int row = mouseX / squareSize;
    int col = mouseY / squareSize;
    round(new Move(row, col));
    if (root.getPlayer() == 1)
	{
	    round(null);
	}
    noLoop();
}

public void round(Move move)
{
    try 
	{
	    build.join(500);
	} 
    catch (InterruptedException e) {}
    build.interrupt();
    if (move == null)
	{
	    root = root.best();
	}
    else if (root.find(move) != null)
	{
	    root = root.find(move);
	    System.gc();
	}
}

public void memStatus()
{
    long
	T = Runtime.getRuntime().totalMemory(),
	F = Runtime.getRuntime().freeMemory();

    System.out.printf("T - F: %d bytes%n",
		      T - F);
}

class RunBuild implements Runnable
{

    private Node temp;

    public RunBuild(Node temp)
    {
	this.temp = temp;
    }

    public void run()
    {
	while (!Thread.interrupted())
	    {
		System.out.println("started");
		Thread thing = new Thread(temp); 
		thing.start();
		try
		    {
			thing.join(0);
		    }
		catch (InterruptedException e)
		    {
			thing.interrupt();
			return;
		    }
		thing.interrupt();
	    }
    }
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "src" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
