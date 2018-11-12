import java.util.*;

public class CSP {


    // 3D array of maze.  [row][column][constraints] where constraints is:
    // [constraints] = [
    //      is_base
    //      color 1
    //      color 2
    //      etc.
    //      ]
    Boolean[][][] constraints;

    public CSP (Node[][] nodeMaze, int maze_leng) {
        int maze_colors = 0;
        int maze_nodes = 0;

        for (Node[] nodes : nodeMaze) {
            for (Node node : nodes) {
                if (node.getBase()) maze_nodes++;
            }

        }

        maze_colors = maze_nodes/2 ;

        constraints = new Boolean[maze_leng][maze_leng][maze_colors];
        initialize_base_constraints(nodeMaze);

    }

    // method that takes in a node maze and initialize
    // whether or not each position is a base node for the maze
    private void initialize_base_constraints(Node[][] nodeMaze) {

        // iterate over the entire maze, row then column.
        // will always update constraint 1 because it is only looking at base
        for (int i = 0; i < nodeMaze.length; i++) {
            for (int j = 0; j < nodeMaze[i].length; j++) {
                constraints[i][j][1] = nodeMaze[i][j].getBase();
            }
        }

    }

    // method to determine possible colors for any position
    // TODO: fill out with search algorithm that determines if a color could possibly have a path in the space
    private void initialize_color_constraints(Node[][] nodeMaze) {
        boolean[][] has_been_visited = new boolean[nodeMaze.length][nodeMaze[0].length]; // 2D array to see if we have check a space yet

        // initialize array as false
        for (int i = 0; i < has_been_visited.length; i++) {
            for (int j = 0; j < has_been_visited[i].length; j++) {
                has_been_visited[i][j] = false;
            }
        }

        // loop over each position in the maze
        for (int i = 0; i < nodeMaze.length; i++) {
            for (int j = 0; j < nodeMaze[i].length; i++) {
                if (!has_been_visited[i][j]) ;  // TODO: visit the nodes here

            }
        }


    }



}
