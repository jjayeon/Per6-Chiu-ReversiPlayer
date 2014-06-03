import java.util.*;
import java.io.*;

public class Driver
{

    public static void main(String[] args)
    {
	game = new Tree(0);
	try
	    {
		game = new Tree(Integer.parseInt(args[0]));
	    }
	catch (IndexOutOfBoundsException e) {}
	catch (NumberFormatException e) {}

	while (true)
	    {
		System.out.println(game);
		ThreadTree thing = new ThreadTree(game);
		String input = getInput(game.currPlayer() == -1 ? "X" : "O");
		Move coords = getCoords(input);
		try
		    {
			thing.join(500);
		    }
		catch (InterruptedException e)
		    {

		    }
		game.play(coords);
		if (game.currPlayer() == game.AIPlayer())
		    {
			System.out.println(game);
			game.playBest();
		    }
	    }
    }

    private static Tree game;

    private static Move getCoords(String input)
    {
	Scanner s = new Scanner(input);
	return new Move( s.nextInt(), s.nextInt() ); 
    }

    private static String getInput(String prompt)
    {
	System.out.print(prompt + ": > ");
	BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	try
	    {
		return in.readLine();
	    }
	catch (IOException e)
	    {
		return "";
	    }
    }

}