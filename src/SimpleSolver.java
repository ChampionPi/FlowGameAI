import java.awt.image.AreaAveragingScaleFilter;
import java.lang.reflect.Array;
import java.util.*;
import java.lang.Math;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

public class SimpleSolver {
    private final Maze initialMaze;
    private Node[][] nodes;
    private final Node blank = new Node(-1,-1,'#');
    private ArrayList<HashMap<Node, Integer>> directionArray = new ArrayList<>();
    private ArrayList<ArrayList<Node>> nodeArrayList = new ArrayList<>();
    private double finishTime;      //used to track timing
    private double startTime;

    //startTime = System.currentTimeMillis();
    //finishTime = System.currentTimeMillis();

    public SimpleSolver(Maze inMaze){
        startTime = System.currentTimeMillis();
        initialMaze = inMaze;
        nodes = inMaze.getNodeMaze();

//        newDFS(inMaze.getNodeMaze(), inMaze);
//        mazeDFS(nodes, inMaze);
        logicUpdate(inMaze.getNodeMaze(),inMaze.getBaseNodes());
        pathDFS(inMaze.getNodeMaze(), inMaze.getBaseNodes()[0], inMaze.getBaseNodes()[0]);
        finishTime = System.currentTimeMillis();
        System.out.println("The time in milliseconds to solve the maze is " + (finishTime - startTime));
        inMaze.printColorMaze();

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

    private Node[][] newDFS(Node[][] nodeMaze, Maze inMaze) {
        Node[] baseNodes = inMaze.getBaseNodes();

        for (Node base : baseNodes) {

            if(findEnd(base,base,nodeMaze) != blank) { // if it isn't connected already
                recursiveSearch(nodeMaze,base);

            }

        }

        return null;
    }

    private void recursiveSearch(Node[][] nodeMaze, Node base) {
        Node currentNode = findEnd(base,base,nodeMaze); // find the node at the end of it's path

        HashMap<Node, Integer> directionMap = new HashMap<>(); // create a hash map for directions from each node
        ArrayList<Node> nodeList = new ArrayList<>(); // arraylist to keep track of order of mappings

        directionMap.put(currentNode, 0); // add current node with a direction of 0 (up)
        nodeList.add(currentNode);

        directionArray.add(directionMap); // add the hash map to the direction array to store across method calls
        nodeArrayList.add(nodeList);


        boolean hasFoundOtherBase = false;
        while (!hasFoundOtherBase) {
            currentNode = nodeList.get(directionMap.size());
            Node neighbor = neighborInDirection(nodeMaze, currentNode, directionMap.get(currentNode));
            if (neighbor != null) {

                // if the neighbor is the same color and connected to the other base
                if (neighbor.getValue() == base.getValue() && connectedBase(nodeMaze,neighbor) != base) {
                    currentNode.setValue(base.getValue());
                    hasFoundOtherBase = true;
                }

                // else if the neighbor is blank
                else if (neighbor.getValue() == '_') {
                    currentNode.setValue(base.getValue());

                }
            }
        }
    }

    private Node[][] mazeDFS(Node[][] nodeMaze, Maze inMaze) {
        nodeMaze = logicUpdate(nodeMaze, inMaze.getBaseNodes());
//        inMaze.printColorMaze();

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
                    pathDFS(nodeMaze, currentNode, baseNode);
//                    printMaze(nodeMaze);
                }
                baseNodeMap.replace(baseNode.getValue(), 2);
            }
            else System.out.println("A base node exists 3 or more times, fault.");
        }

        //System.out.println("I think this means its working");
        return nodeMaze;
    }

    private int pathDFS(Node[][] nodeMaze, Node currentNode, Node baseNode) {
        printMaze(nodeMaze);
        ArrayList<Node> pathNodes = new ArrayList<>();
        Node[] neighbors = checkNeighborsFor(currentNode, nodeMaze);

        neighbors = shuffle(neighbors);

        currentNode.setValue(baseNode.getValue());

        for (Node neighbor : neighbors) { // for each node around current
            if (neighbor != null) { // if the neighbor isn't null

                // if the neighbor is the same color as the base and not connected to the base
                if (neighbor.getValue() == baseNode.getValue() && connectedBase(nodeMaze,neighbor) != baseNode) {
                    pathNodes.add(currentNode);
                    return 0;
                }

            }

        }

        for (Node neighbor : neighbors) { // for each node around current
            if (neighbor != null) { // if the neighbor isn't null
                if (neighbor.getValue() == '_') { // and is blank
                    int returnMe = pathDFS(nodeMaze, neighbor, baseNode);
                    if (returnMe == 0) return returnMe;
                }
            }
        }

        currentNode.setValue('_');
        System.out.println("Bad News Bears");
        return 1;
    }

    private Node[] shuffle(Node[] neighbors) {
        ArrayList<Node> oldNeighbors = new ArrayList<>();

        for (Node node : neighbors) oldNeighbors.add(node);

        Node[] newNeighbors = new Node[neighbors.length];

        int firstNeighbor = (int) (Math.random()*4);
        int secondNeighbor = (int) (Math.random()*3);
        int thirdNeighbor = (int) (Math.random()*2);

        newNeighbors[0] = oldNeighbors.get(firstNeighbor);
        oldNeighbors.remove(newNeighbors[0]);

        newNeighbors[1] = oldNeighbors.get(secondNeighbor);
        oldNeighbors.remove(newNeighbors[1]);

        newNeighbors[2] = oldNeighbors.get(thirdNeighbor);
        oldNeighbors.remove(newNeighbors[2]);

        newNeighbors[3] = oldNeighbors.get(0);
        oldNeighbors.remove(newNeighbors[3]);

        return newNeighbors;
    }

    // method to return the node in a given direction
    private Node neighborInDirection(Node[][] nodeMaze, Node currentNode, int direction) {
        switch (direction) {
            case 0:
                if (currentNode.getX() > 0) return nodeMaze[currentNode.getX()-1][currentNode.getY()];
                else return null;
            case 1:
                if (currentNode.getY() < nodeMaze.length - 1) return nodeMaze[currentNode.getX()][currentNode.getY()+1];
                else return null;
            case 2:
                if (currentNode.getX() < nodeMaze.length -1) return nodeMaze[currentNode.getX()+1][currentNode.getY()];
                else return null;
            case 3:
                if (currentNode.getY() > 0) return nodeMaze[currentNode.getX()][currentNode.getY()-1];
                else return null;
            default: return null;
        }
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
