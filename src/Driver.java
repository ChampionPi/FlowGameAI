import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Driver {
    public static void main(String[] args) throws IOException {
        Maze fiveMaze     = readMazeIn("5x5maze.txt");                                                        //Load Mazes
        Maze sevenMaze    = readMazeIn("7x7maze.txt");
//        Maze eightMaze    = readMazeIn("8x8maze.txt");
//        Maze nineMaze     = readMazeIn("9x9maze.txt");
//        Maze tenMaze      = readMazeIn("10x10maze.txt");
//        Maze twelveMaze   = readMazeIn("12x12maze.txt");
//        Maze fourteenMaze = readMazeIn("14x14maze.txt");

//        fiveMaze.printColorBaseNodes();                                                                               //Prints to the console with color
        System.out.println();
        //fiveMaze.dumbSolve();

//        fiveMaze.solveMaze();
        fiveMaze.printColorMaze();
        new SimpleSolver(fiveMaze);
        System.out.println("Final 5x5 Maze");
        fiveMaze.printColorMaze();

        //sevenMaze.printColorBaseNodes();
        sevenMaze.printColorMaze();
        new SimpleSolver((sevenMaze));
        System.out.println("Final 7x7 Maze");
        sevenMaze.printColorMaze();
//
//        eightMaze.printColorBaseNodes();
//        eightMaze.printColorMaze();
//
//        nineMaze.printColorBaseNodes();
//        nineMaze.printColorMaze();
//
//        tenMaze.printColorBaseNodes();
//        tenMaze.printColorMaze();
//
//        twelveMaze.printColorBaseNodes();
//        twelveMaze.printColorMaze();
//
//        fourteenMaze.printColorBaseNodes();
//        fourteenMaze.printColorMaze();

        System.out.println("Program Done");
    }

    private static Maze readMazeIn(String mazeName) throws IOException {                                                //creates a 2 dimensional character array
        ArrayList<String> maze = new ArrayList<>();                                                                     // that holds the maze
        try (BufferedReader br = new BufferedReader(new FileReader(mazeName))) {
            String line = br.readLine();
            while (line != null) {
                maze.add(line);
                line = br.readLine();
            }
        }
        char[][] newMaze = new char[maze.size()][maze.get(0).length()];
        for (int i = 0; i < maze.size(); i++) {
            newMaze[i] = maze.get(i).toCharArray();
        }
        return new Maze(newMaze);
    }
}
