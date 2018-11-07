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

    }

    public void initialize_constraints(Node[][] nodeMaze) {

    }

}
