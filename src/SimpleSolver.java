import java.util.*;
import java.lang.Math;



public class SimpleSolver {
    private final Maze initialMaze;
    private Node[][] nodes;
    private final Node blank = new Node(-1,-1,'#');
    private ArrayList<HashMap<Node, Integer>> directionArray = new ArrayList<>();
    private ArrayList<ArrayList<Node>> nodeArrayList = new ArrayList<>();

    public SimpleSolver(Maze inMaze){
        initialMaze = inMaze;
        nodes = inMaze.getNodeMaze();

//        newDFS(inMaze.getNodeMaze(), inMaze);
//        mazeDFS(nodes, inMaze);
//
        Node[][] in = inMaze.getNodeMaze();
        Node[] Base = inMaze.getBaseNodes();
        Node [] sBase=singleBase(Base);     // key for forward checking 3d array color spot

        printBase(Base);
        printMaze(in);
//
//        in = logicUpdate(in,Base);
//        System.out.println("Logic Maze");
//        inMaze.printColorMaze();
//
//        System.out.println("Reset Maze");
//        inMaze.reset();
//        inMaze.printColorMaze();



        if(forwardCheck(in,Base)){
            System.out.println("did not die");
        }

        //in = logicUpdate(in,Base);

        printMaze(in);

    }

    public boolean forwardCheck(Node [][]in , Node[] Base){
        Node [] sBase=singleBase(Base);
        printBase(sBase);

        boolean[][][] option = new boolean[in[1].length][in[1].length][sBase.length+1];
        // option is possibilities for each node, first spot indicates if given color

        option = checkoption(option,in, sBase);



        // check each end/ base node that's not null to see if it can be reached from other end/ base node
        boolean possible = false;
        for(int i=0; i< Base.length;i++){
            if(Base[i]!=null){
                Node temp = findEnd(Base[i],Base[i],in);
                Node []neighbors = checkNeighborsFor(temp,in);
                int index = Index(Base[i].getValue(),sBase);

                if(neighbors[0]!=null && option[neighbors[0].getX()][neighbors[0].getY()][index]){
                    possible = true;
                }
                if(neighbors[1]!=null && option[neighbors[1].getX()][neighbors[1].getY()][index]){
                    possible = true;
                }
                if(neighbors[2]!=null && option[neighbors[2].getX()][neighbors[2].getY()][index]){
                    possible = true;
                }
                if(neighbors[3]!=null && option[neighbors[3].getX()][neighbors[3].getY()][index]){
                    possible = true;
                }
            }
        }
        return possible;

    }

    private int Index(char c, Node []sBase){

        for(int i=0;i<sBase.length;i++){
            if(sBase[i].getValue()==c){
                return i;
            }
        }
        return -1;
    }

    private boolean[][][] checkoption(boolean[][][] option, Node [][] in, Node [] sBase){
        for(int i=0;i<in[1].length;i++){  // sets alredy given values
            for( int j=0; j<in[1].length;j++){
                if(in[i][j].getValue() != '_'){
                    option[i][j][0] = true;
                    option[i][j][Index(in[i][j].getValue(),sBase)]= true;
                }
            }
        }

        // look at sBase, on of each particular base node, add all option tracks from that node,
        // back in forward check see if any surrounding node for other base/ end nodes can be that color

        for(int j=0; j<sBase.length;j++){ // j is index for sBase
            Node end = findEnd(sBase[j],sBase[j],in);
            boolean [][] bo = new boolean [in[1].length][in[1].length];
            boolean [][] temp = searchRecur(in, end,bo);
            System.out.println("option at "+j);
            printBo(temp);
            for(int k =0; k<option.length;k++){
                for(int l=0; l<option.length;l++){
                    option[k][l][j] = temp[k][l];

                }
            }


        }
        return option;
    }

    private boolean[][] searchRecur(Node [][] in, Node start, boolean[][] bo){  // send in end node
        Node neighbors[] = checkNeighborsFor(start,in);

        if(neighbors[0]!= null && neighbors[0].getValue()=='_' && !bo[neighbors[0].getX()][neighbors[0].getY()]){
            Node temp = neighbors[0];
            bo[temp.getX()][temp.getY()]= true;
            searchRecur(in,temp,bo);
        }
        if(neighbors[1]!= null && neighbors[1].getValue()=='_'&& !bo[neighbors[1].getX()][neighbors[1].getY()]){
            Node temp = neighbors[1];
            bo[temp.getX()][temp.getY()]= true;
            searchRecur(in,temp,bo);
        }
        if(neighbors[2]!= null && neighbors[2].getValue()=='_'&& !bo[neighbors[2].getX()][neighbors[2].getY()]){
            Node temp = neighbors[2];
            bo[temp.getX()][temp.getY()]= true;
            searchRecur(in,temp,bo);
        }
        if(neighbors[3]!= null && neighbors[3].getValue()=='_'&& !bo[neighbors[3].getX()][neighbors[3].getY()]){
            Node temp = neighbors[3];
            bo[temp.getX()][temp.getY()]= true;
            searchRecur(in,temp,bo);
        }
        return bo;
    }

    private Node[] singleBase(Node[] Base){
        int count =0;
        for(int i=0;i<Base.length;i++){
            if(Base[i] != null){
                count++;
            }
        }

        Node [] sBase = new Node[count/2];
        count = 0;

        for(int j=0; j<Base.length;j++){
            if(Base[j]!= null){
                boolean add = true;
                for(int k=0;k<sBase.length;k++){
                    if(Base[j] != null & sBase[k]!= null) {
                        if( Base[j].getValue()==sBase[k].getValue()) {
                            add = false;
                        }
                    }
                }
                if(add){
                    sBase[count] =Base[j];
                    count++;
                }
            }
        }
        return sBase;
    }

    private Node[][] newDFS(Node[][] nodeMaze, Maze inMaze) {
        Node[] baseNodes = inMaze.getFirstBaseNodes();

        for (Node base : baseNodes) {
            System.out.println("Base Node " + base.getValue() + " at X:" + base.getX() + " Y:" + base.getY());
//
//            if(findEnd(base,base,nodeMaze) != blank) { // if it isn't connected already
//                recursiveSearch(nodeMaze,base);
//
//            }

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
                if (neighbor.getValue() == base.getValue() && connectedBase(nodeMaze, currentNode, neighbor) != base) {
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

        boolean foundPath = false;
        // for each base node
        while (!foundPath) {
            Node [] firstBaseNodes = inMaze.getFirstBaseNodes();
            for (Node currentNode : firstBaseNodes) {

                Node baseNode = currentNode;
                currentNode = findEnd(currentNode, currentNode, nodeMaze); // find the end of the path from the base

                if (currentNode != blank) {
                    foundPath = pathDFS(nodeMaze, currentNode, baseNode);
//                    System.out.println("found path if true :"+foundPath);
//                    inMaze.printColorMaze();
                    if (foundPath == false) {
//                        inMaze.printColorMaze();
//                        inMaze.reset();
                    }
                    else {
                        System.out.println("Found Path from node X:" + currentNode.getX() + " Y:" + currentNode.getY());
                        inMaze.printColorMaze();
                    }

//                    inMaze.printColorMaze();
//                    System.out.println("logic update:");
                    logicUpdate(nodeMaze, inMaze.getBaseNodes());
//                    inMaze.printColorMaze();


                }
                else {
                    foundPath = true; // if the current node is connected to the other base
                    System.out.printf("Node is connected from X:%d, Y:%d\n", baseNode.getX(), baseNode.getY());
                    inMaze.printColorMaze();
                }

            }
            if (!foundPath) {
                System.out.println("Cant find path on Maze");
                inMaze.printColorMaze();
                inMaze.reset();
                nodeMaze = inMaze.getNodeMaze();
                System.out.println("Reverted Maze");
                inMaze.printColorMaze();
            }
        }

        //System.out.println("I think this means its working");
        return nodeMaze;
    }

    private boolean pathDFS(Node[][] nodeMaze, Node currentNode, Node baseNode) {
//        printMaze(nodeMaze);
        ArrayList<Node> pathNodes = new ArrayList<>();
        Node[] neighbors = checkNeighborsFor(currentNode, nodeMaze);

        neighbors = shuffle(neighbors);

        currentNode.setValue(baseNode.getValue());

//        printMaze(nodeMaze);
        for (Node neighbor : neighbors) { // for each node around current
            if (neighbor != null) { // if the neighbor isn't null

                // if the neighbor is the same color as the base and not connected to the base
                if (neighbor.getValue() == baseNode.getValue() && connectedBase(nodeMaze,currentNode, neighbor) != baseNode) {
                    pathNodes.add(currentNode);
                    return true;
                }

            }

        }

        for (Node neighbor : neighbors) { // for each node around current
            if (neighbor != null) { // if the neighbor isn't null
                if (neighbor.getValue() == '_' && doesntZigzag(nodeMaze, neighbor, baseNode)) { // and is blank
                    currentNode.setValue(baseNode.getValue());

                    boolean returnMe = pathDFS(nodeMaze, neighbor, baseNode);
                    if (returnMe == true) return returnMe;
                    else currentNode.setValue('_');
                }
            }
        }

        currentNode.setValue('_');
//        System.out.println("Bad News Bears in SimpleSolver pathDFS");
        return false;
    }

    // makes makes sure that adding a node wont create a zigzag or loop
    private boolean doesntZigzag(Node[][] nodeMaze, Node currentNode, Node baseNode) {
        Node[] neighbors = checkNeighborsFor(currentNode, nodeMaze);
        int count = 0;
        for (Node node : neighbors) {

            // if the neighbor isnt null, has the same color as the current node, and is a base node that isnt connected
            if (node != null && node.getValue() == baseNode.getValue() && (!node.getBase() || node == baseNode)) count++;
            if (count >= 2) return false;
        }
        return true;
    }

    // shuffles order of neighbor nodes
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
    private Node connectedBase(Node[][] nodeMaze, Node currentNode, Node neighborNode) {

        if (currentNode.getBase()) return currentNode;
        if (neighborNode.getBase()) return neighborNode;
        // while the neighbor node isn't a base node
        while (!neighborNode.getBase()) {
            Node[] neighbors = checkNeighborsFor(neighborNode,nodeMaze); // find neighbors
            for (Node node : neighbors) { // for each neighbor
                if (node != null) {
                    if (node.getValue() == currentNode.getValue() && node != currentNode) { // if the neighbor is the same color and not the last node
                        currentNode = neighborNode;
                        neighborNode = node;
                        break;
                    }
                }
            }
        }

        return neighborNode;
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
//            System.out.println(" something wrong in findEnd");
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

    public void printBo(boolean[][] bo){
        System.out.println();
        for(int i=0; i<bo[1].length;i++){
            for(int j=0;j<bo[1].length;j++){
                if(bo[i][j]){
                    System.out.print(" 1");
                }
                else{
                    System.out.print(" 0");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

}
