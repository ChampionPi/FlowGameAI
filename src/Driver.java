import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Driver {
    public static void main(String[] args) throws IOException {
        Maze fiveMaze     = readMazeIn("5x5maze.txt");
//        Maze sevenMaze    = readMazeIn("7x7maze.txt");
//        Maze eightMaze    = readMazeIn("8x8maze.txt");
//        Maze nineMaze     = readMazeIn("9x9maze.txt");
//        Maze tenMaze      = readMazeIn("10x10maze.txt");
//        Maze twelveMaze   = readMazeIn("12x12maze.txt");
//        Maze fourteenMaze = readMazeIn("14x14maze.txt");

        Node node1 = new Node(1,1, '_');
        Node node2 = new Node(node1);
        node2.setValue('b');
        System.out.println("value should be '_': " + node1.getValue());
        System.out.println("value should be 'b': " + node2.getValue());

        fiveMaze.printColorBaseNodes();
        System.out.println();
        fiveMaze.dumbSolve();
        Solver solvefive = new Solver(fiveMaze);
//        fiveMaze.solveMaze();
        fiveMaze.printColorMaze();

//        sevenMaze.printColorBaseNodes();
//        sevenMaze.printColorMaze();
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

    private static Maze readMazeIn(String mazeName) throws IOException {
        ArrayList<String> maze = new ArrayList<>();
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
