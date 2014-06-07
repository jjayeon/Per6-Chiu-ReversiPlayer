private int 
    squareSize = 80,
    rows = 8,
    cols = 8;
private Tree game;
private Thread build;

void setup()
{
    size(squareSize * rows, squareSize * cols);
    game = new Tree(1);
    frameRate(60);
    noLoop();
}

void draw()
{
    build = new Thread(game);
    build.start();

    drawBG();
    drawPieces();
}

void drawBG()
{
    background(0, 150, 0);
    for (int count = 1; count <= rows - 1; count++)
	    line(count * width / rows, height, 
		 count * width / rows, 0);
    for (int count = 1; count <= cols - 1; count++)
	    line(0,     count * height / cols, 
		 width, count * height / cols);
}

void drawPieces()
{
   for (int row = 0; row < rows; row++)
	{for (int col = 0; col < cols; col++)
		{
		    if (game.check(row, col) == -1)
			fill(255);
		    else if (game.check(row, col) == 1)
			fill(0);
		    else
			continue;
		    ellipse(squareSize * row + squareSize / 2, squareSize * col + squareSize / 2, squareSize - 10, squareSize - 10);
		}
	}
}

void mouseClicked()
{
    int row = mouseX / squareSize;
    int col = mouseY / squareSize;
    round(new Move(row, col));
    if (game.AIPlayer() == game.currPlayer())
	{
	    round(null);
	}
}

void round(Move move)
{
    loop();
    try 
	{
	    build.join(500);
	} 
    catch (InterruptedException e) {}
    build.interrupt();
    game.play(move);
    noLoop();
}