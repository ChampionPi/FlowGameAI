import java.security.Key;
import java.util.*;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

public class SimpleSolver {
    private final Maze initialMaze;
    private Node[][] nodes;
    private final Node blank = new Node(-1,-1,'#');


    public SimpleSolver(Maze inMaze){
        initialMaze = inMaze;
        nodes = inMaze.getNodeMaze();

        mazeDFS(nodes, inMaze);

//        Node[][] in = inMaze.getNodeMaze();
//        Node[] Base = inMaze.getBaseNodes();
//
//        printBase(Base);
//        printMaze(in);
//
//        in = logicUpdate(in,Base);
//        initialMaze.printColorMaze();

        //printMaze(in);

    }

    private Node[][] mazeDFS(Node[][] nodeMaze, Maze inMaze) {
        // TODO: Colter's logic
        nodeMaze = logicUpdate(nodeMaze, inMaze.getBaseNodes());
        //inMaze.printColorMaze();

        HashMap<Character, Integer> baseNodeMap = new HashMap<>();

        // for each base node, add it to a hash map to see if it has been reached twice
        for (Node currentNode : inMaze.getBaseNodes()) {
            if ( currentNode == null ) {
                break; }
            if (!baseNodeMap.containsKey(currentNode.getValue())) {
                baseNodeMap.put(currentNode.getValue(), 1);
            }
            else if (baseNodeMap.get(currentNode.getValue()) == 1) { // when we have found the 2nd instance of a color's base
                Node baseNode = currentNode;
                currentNode = findEnd(currentNode, currentNode, nodeMaze); // find the end of the path from the base
                if (currentNode != blank) {
                    LinkedHashMap<Node, Integer> directionMap = new LinkedHashMap<>(); // hash map of directions to nodes, to keep track of direction paths have traveled
                    directionMap = pathDFS(nodeMaze, currentNode, directionMap, baseNode);
                }
                baseNodeMap.replace(baseNode.getValue(), 2);
            }
            else System.out.println("A base node exists 3 or more times, fault.");
        }

        return null;
    }

    private LinkedHashMap pathDFS(Node[][] nodeMaze, Node currentNode, LinkedHashMap<Node,Integer> directionMap, Node baseNode) {
        directionMap.put(currentNode, 0); // initialize with a zero direction, i.e. up
        Node[] neighbors = checkNeighborsFor(currentNode,nodeMaze);

        for (int i = 0; i < 4; i++) { // for each neighbor
            if (neighbors[i] != null) {
                if (neighbors[i].getValue() == baseNode.getValue()) { // if the neighbor is the same color as the base
                    // if the neighbor connects to the other base node
                    if( connectedBase(nodeMaze, neighbors[i]) != baseNode) {
                        currentNode.setValue(baseNode.getValue());
                        directionMap.replace(currentNode, i);
                        return directionMap;

                    }
                }

                if (neighbors[i].getValue() == '_') { // if the neighbor is blank

                    currentNode.setValue(baseNode.getValue()); // set the current position to the base node color
                    directionMap.replace(currentNode, i); // replace the direction with the direction to the neighbor

                    directionMap = pathDFS(nodeMaze, neighbors[i], directionMap, baseNode);

                }
            }
        }

        currentNode.setValue('_');

        return directionMap;
    }

    // method to return the base node connected to a non base node
    private Node connectedBase(Node[][] nodeMaze, Node startNode) {
        Node currentNode = startNode;
        Node lastNode = currentNode;

        // while the current node isn't a base node
        while (!currentNode.getBase()) {
            Node[] neighbors = checkNeighborsFor(currentNode,nodeMaze); // find neighbors
            for (Node node : neighbors) { // for each neighbor
                if (node != null) {
                    if (node.getValue() == currentNode.getValue() && node != lastNode) { // if the neighbor is the same color and not the last node
                        lastNode = currentNode;
                        currentNode = node; // make that the current node
                    }
                }
            }
        }

        return currentNode;
    }


    // Method to determine if there are any positions on the maze that must be a color,
    // by checking if there is a path that could only have 1 valid extension
    public Node[][] logicUpdate(Node [][] in, Node [] Base){


        //printMaze(in);
        int i=0;

        while(Base[i]!= null){
            Node temp = Base[i];
            temp = findEnd(temp, temp, in);

            //System.out.println("check at "+temp.getValue());
            if(temp.getValue()=='#'){}
            else {
                int only = ifOnly(temp, in);         // return direction of next flow

                if (only == 0) {                    //up  -X
                    in = update(in, temp, in[temp.getX() - 1][temp.getY()]);
                    logicUpdate(in, Base);
                }
                if (only == 1) {                    //Right +Y
                    in = update(in, temp, in[temp.getX()][temp.getY() + 1]);
                    logicUpdate(in, Base);
                }
                if (only == 2) {                    //Down  +X
                    in = update(in, temp, in[temp.getX() + 1][temp.getY()]);
                    logicUpdate(in, Base);
                }
                if (only == 3) {                    //Left -Y
                    in = update(in, temp, in[temp.getX()][temp.getY() - 1]);
                    logicUpdate(in, Base);
                }
            }
            i++;
        }

        return in;
    }

    public Node findEnd(Node temp, Node last, Node [][] in){

        Node [] arr = checkNeighborsFor(temp,in);
        //System.out.println("findEnd temp "+temp.getX()+", "+temp.getY());
        int count = 0;
        Node option= null;


        for(int i =0; i<arr.length;i++){
            if(arr[i]==null){}
            else if(temp.getValue()== arr[i].getValue()){
                count++;
                if( arr[i] != last) {
                    option = arr[i];
                }
            }
        }

        if(temp == last){   // at first base node
            if(count == 0){
                return(temp);
            }
            if(count ==1){
                //System.out.println("found from base "+ option.getValue());
                temp = findEnd(option,temp,in);
            }
            else{
                //System.out.println("base node w multiple same surrounding");
                return null;
            }
        }
        else if(temp.getBase()){    // flow complete******
            return blank;
        }
        else if(count == 1){        // at end of flow
            return temp;
        }
        else if( count ==2){        // not at end
            temp = findEnd(option,temp,in);
        }
        else{
            System.out.println(" something wrong in findEnd");
        }

        return temp;
    }

    public Node [] checkNeighborsFor(Node inNode, Node [][] maze){  //can be used to find empty spaces or partner

        Node temp [] = new Node [4];
        //System.out.println("Check Neighbors of "+inNode.getValue()+ " at  x "+ inNode.getX()+" y "+inNode.getY());

        if( 0 <= inNode.getY() + 1 && inNode.getY() + 1 < maze.length){ // add right node to array
            temp[1]= maze[inNode.getX()][inNode.getY()+1];
        }
        if( 0 <= inNode.getY() - 1 && inNode.getY() - 1 < maze.length ){ // add left node
            temp[3]= maze[inNode.getX()][inNode.getY()-1];
        }
        if( 0 <= inNode.getX() + 1 && inNode.getX() + 1 < maze.length ){ // down
            temp[2]= maze[inNode.getX()+1][inNode.getY()];
        }
        if( 0 <= inNode.getX() - 1 && inNode.getX() - 1 < maze.length ){ // up
            temp[0]= maze[inNode.getX()-1][inNode.getY()];
        }
        return temp;

    }

    public int ifOnly(Node temp, Node [][] maze){ // checks if there is only one spot to go
        Node [] neighbors = checkNeighborsFor(temp, maze);
        //System.out.println("ifOnly neighbors of "+temp.getX()+", "+ temp.getY());
        //printBase(neighbors);

        int spots =0;     // keep track of open spots
        int spot = -1;
        for(int i=0; i<neighbors.length;i++){
            if(neighbors[i] != null && neighbors[i].getValue()=='_'){
                spots ++;
                spot =i;
            }
        }
        if( spots==1){      // only one open spot
            return spot;
        }
        else return -1;

    }

    public Node[][] update(Node[][] in, Node original, Node change){
        int x = change.getX();
        int y = change.getY();

        change.setValue(original.getValue());
        in[x][y] = change;

        //System.out.println(" update at "+x+", "+y+" to "+ change.getValue()+ " complete");
        //printMaze( in);
        return in;
    }

    public void printMaze(Node [][] in) { //will print out the maze, printing each nodes value which is a char
        System.out.println();
        for (int x = 0; x < in.length; x++) {
            for (int y = 0; y < in.length; y++) {
                System.out.print(in[x][y].getValue() + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void printBase(Node[] base){  // prints node array
        for (int i = 0; i < base.length; i++) {
            if(base[i]== null){
                //System.out.print("null, ");
            }
            else {
                //System.out.print(base[i].getValue() + ", ");
            }
        }
        System.out.println();
    }

}
