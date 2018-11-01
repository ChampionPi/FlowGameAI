public class Maze {

    private Node[][] nodeMaze;          //holds the maze with each node containing all relevant information for that spot
    private Node[] baseNodes;           //holds all nodes that had values when the maze was imported
    private Node[] emptyNodes;          //holds all nodes that are empty when the maze in imported
    private Node temp;                  //general temp node to pass info around
    private int mazeDim;                //for 5x5 maze holds the number 5
    private int baseNodesSize = 0;      //numbers of basenodes, naturally counted starting at 1
    private int emptyNodesSize = 0;     //will be physical number counted starting at 1

    //used to color output
    private String ANSI_RESET = "\u001B[0m";
    private String ANSI_BLACK = "\u001B[30m";
    private String ANSI_RED = "\u001B[31m";
    private String ANSI_GREEN = "\u001B[32m";
    private String ANSI_YELLOW = "\u001B[33m";
    private String ANSI_BLUE = "\u001B[34m";
    private String ANSI_PURPLE = "\u001B[35m";
    private String ANSI_CYAN = "\u001B[36m";
    private String ANSI_WHITE = "\u001B[37m";


    //imports the maze and sets a lot of values
    public Maze(char[][] textMaze) {
        mazeDim = textMaze[0].length;
        baseNodes = new Node[(mazeDim*mazeDim)];
        emptyNodes  = new Node[(mazeDim*mazeDim)];
        nodeMaze = new Node[mazeDim][mazeDim];
        for (int y = 0; y < mazeDim; y++) {
            for (int x = 0; x < mazeDim; x++) {
                Node temp = new Node(x, y, textMaze[x][y]);
                nodeMaze[x][y] = temp;
                if (temp.getBase()) {
                    baseNodes[baseNodesSize] = temp;
                    baseNodesSize++;
                } else {
                    emptyNodes[emptyNodesSize] = temp;
                    emptyNodesSize++;
                }
            }
        }
    }

        //TODO Solve the maze
    }
    //theoretically you give it two nodes and it travels from one to other
    private void travelFromTo(Node inFirstNode, Node inSecondNode){
        int travelX = inFirstNode.getX() - inSecondNode.getX();
        int travelY = inFirstNode.getY() - inSecondNode.getY();
        if(Math.abs(travelX)>Math.abs(travelY)){
            checkUpDown(travelX, inFirstNode);
            checkLeftRight(travelY, inFirstNode);
        }else{
            checkLeftRight(travelY, inFirstNode);
            checkUpDown(travelX, inFirstNode);
        }
    }
    //will solve maze without forward checking and other things
    public void dumbSolve(){

    }
    //takes in the first nodes x value - the x value of the second node to determine which direction to go then tries to travel from the node passed into it until x difference = 0
    private void checkLeftRight(int inTravel, Node inStartNode){
        while(inTravel != 0) {
            if (inTravel > 0) {
                checkLeftNeighborFor(inStartNode, '_');
                inTravel -= 1;
            } else if (inTravel < 0) {
                checkRightNeighborFor(inStartNode, '_');
                inTravel += 1;
            } else {
                return;
            }
            if(temp != null) {
                temp.setValue(inStartNode.getValue());
            }
        }
    }
    //takes in the first nodes y value - the y value of the second node to determine which direction to go then tries to travel from the node passed into it until y difference = 0
    private void checkUpDown(int inTravel, Node inStartNode){
        while(inTravel != 0) {
            if (inTravel > 0) {
                temp = checkUpNeighborFor(inStartNode, '_');
                inTravel -= 1;
            } else if (inTravel < 0) {
                temp = checkDownNeighborFor(inStartNode, '_');
                inTravel += 1;
            } else {
                return;
            }
            if(temp != null) {
                temp.setValue(inStartNode.getValue());
            }
        }
    }

    private Node [] checkNeighborsFor(Node inNode){                                               //can be used to find empty spaces or partner

        Node temp [] = new Node [3];

        if( 0 <= inNode.getX() + 1 && inNode.getX() + 1 < mazeDim){ // add right node to array
            temp[1]= nodeMaze[inNode.getX()+1][inNode.getY()];
        }else if( 0 <= inNode.getX() - 1 && inNode.getX() - 1 < mazeDim ){ // add left node
            temp[3]= nodeMaze[inNode.getX()-1][inNode.getY()];
        }else if( 0 <= inNode.getY() + 1 && inNode.getY() + 1 < mazeDim ){ // down
            temp[2]= nodeMaze[inNode.getX()][inNode.getY()+1];
        }else if( 0 <= inNode.getY() - 1 && inNode.getY() - 1 < mazeDim ){ // up
            temp[0]= nodeMaze[inNode.getX()][inNode.getY()-1];
        }
        return temp;

    }

    private Node isNeighbor(char C, Node temp) {// ? method that looks for specific char in checkNeighborFor
        Node tarray[];
        tarray = checkNeighborsFor(temp);

        for(int i=0;i< tarray.length;i++){
            if(tarray[i].getValue()== C){
                return tarray[i];
            }
        }
        return null;
    }
    //just checks the node above the current node for the char value passed in
    private Node checkUpNeighborFor(Node inNode, char inSearchFor){                                                      //can be used to find empty spaces or partner
        if( 0 <= inNode.getY() - 1 && inNode.getY() - 1 < mazeDim && inSearchFor == nodeMaze[inNode.getX()][inNode.getY() - 1].getValue()){
            return nodeMaze[inNode.getX()][inNode.getY()-1];
        }else{
            return null;
        }
    }
    //just checks the node below the current node for the char value passed in
    private Node checkDownNeighborFor(Node inNode, char inSearchFor){
        if( 0 <= inNode.getY() + 1 && inNode.getY() + 1 < mazeDim && inSearchFor == nodeMaze[inNode.getX()][inNode.getY() + 1].getValue()){
            return nodeMaze[inNode.getX()][inNode.getY()+1];
        }else{
            return null;
        }
    }
    //just checks the node to the left of the current node for the char value passed in
    private Node checkLeftNeighborFor(Node inNode, char inSearchFor){
        if( 0 <= inNode.getX() - 1 && inNode.getX() - 1 < mazeDim && inSearchFor == nodeMaze[inNode.getX() - 1][inNode.getY()].getValue()){
            return nodeMaze[inNode.getX()-1][inNode.getY()];
        }else{
            return null;
        }
    }
    //just checks the node to the right og the current node for the char value passed in
    private Node checkRightNeighborFor(Node inNode, char inSearchFor){
        if( 0 <= inNode.getX() + 1 && inNode.getX() + 1 < mazeDim && inSearchFor == nodeMaze[inNode.getX() + 1][inNode.getY()].getValue()){
            return nodeMaze[inNode.getX()+1][inNode.getY()];
        }else{
            return null;
        }
    }
    //totally honest not sure how this was supposed to work or whit it does


    /*private Node undo(Node inNode){
        Node temp = inNode;
        if(!inNode.getBase()) {
            inNode.setValue('_');
        } else {
            return null;
        }
        temp = isNeighbor(temp, temp.getValue());
        return temp;
    }*/


    public void printMaze() {                       //will print out the maze, printing each nodes value which is a char
        for (int x = 0; x < mazeDim; x++) {
            for (int y = 0; y < mazeDim; y++) {
                System.out.print(nodeMaze[x][y].getValue() + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    //prints out a beautiful rainbow maze
    public void printColorMaze() {
        for (int x = 0; x < mazeDim; x++) {
            for (int y = 0; y < mazeDim; y++) {
                if(nodeMaze[x][y].getValue() == 'A') {
                    System.out.print(ANSI_CYAN + nodeMaze[x][y].getValue() + ANSI_RESET + " ");
                }else if(nodeMaze[x][y].getValue() == 'B'){
                    System.out.print(ANSI_BLACK + nodeMaze[x][y].getValue() + ANSI_RESET + " ");
                }else if(nodeMaze[x][y].getValue() == 'D'){
                    System.out.print(ANSI_BLUE + nodeMaze[x][y].getValue() + ANSI_RESET + " ");
                }else if(nodeMaze[x][y].getValue() == 'G'){
                    System.out.print(ANSI_GREEN + nodeMaze[x][y].getValue() + ANSI_RESET + " ");
                }else if(nodeMaze[x][y].getValue() == 'K'){
                    System.out.print(ANSI_CYAN + nodeMaze[x][y].getValue() + ANSI_RESET + " ");
                }else if(nodeMaze[x][y].getValue() == 'O'){
                    System.out.print(ANSI_BLUE + nodeMaze[x][y].getValue() + ANSI_RESET + " ");
                }else if(nodeMaze[x][y].getValue() == 'P') {
                    System.out.print(ANSI_PURPLE + nodeMaze[x][y].getValue() + ANSI_RESET + " ");
                }else if(nodeMaze[x][y].getValue() == 'Q'){
                    System.out.print(ANSI_GREEN + nodeMaze[x][y].getValue() + ANSI_RESET + " ");
                }else if(nodeMaze[x][y].getValue() == 'R'){
                        System.out.print(ANSI_RED + nodeMaze[x][y].getValue() + ANSI_RESET + " ");
                }else if(nodeMaze[x][y].getValue() == 'W'){
                    System.out.print(ANSI_WHITE + nodeMaze[x][y].getValue() + ANSI_RESET + " ");
                }else if(nodeMaze[x][y].getValue() == 'Y'){
                    System.out.print(ANSI_YELLOW + nodeMaze[x][y].getValue() + ANSI_RESET + " ");
                }else{
                    System.out.print(nodeMaze[x][y].getValue() + " ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public void printColorBaseNodes(){                   //prints all none empty nodes in color
        for (int x = 0; x < baseNodesSize; x++) {
            if(baseNodes[x].getValue() == 'A') {
                System.out.print(ANSI_CYAN + baseNodes[x].getValue() + ANSI_RESET + " ");
            }else if(baseNodes[x].getValue() == 'B'){
                System.out.print(ANSI_BLACK + baseNodes[x].getValue() + ANSI_RESET + " ");
            }else if(baseNodes[x].getValue() == 'D'){
                System.out.print(ANSI_BLUE + baseNodes[x].getValue() + ANSI_RESET + " ");
            }else if(baseNodes[x].getValue() == 'G'){
                System.out.print(ANSI_GREEN + baseNodes[x].getValue() + ANSI_RESET + " ");
            }else if(baseNodes[x].getValue() == 'K'){
                System.out.print(ANSI_CYAN + baseNodes[x].getValue() + ANSI_RESET + " ");
            }else if(baseNodes[x].getValue() == 'O'){
                System.out.print(ANSI_BLUE + baseNodes[x].getValue() + ANSI_RESET + " ");
            }else if(baseNodes[x].getValue() == 'P') {
                System.out.print(ANSI_PURPLE + baseNodes[x].getValue() + ANSI_RESET + " ");
            }else if(baseNodes[x].getValue() == 'Q'){
                System.out.print(ANSI_GREEN + baseNodes[x].getValue() + ANSI_RESET + " ");
            }else if(baseNodes[x].getValue() == 'R'){
                System.out.print(ANSI_RED + baseNodes[x].getValue() + ANSI_RESET + " ");
            }else if(baseNodes[x].getValue() == 'W'){
                System.out.print(ANSI_WHITE + baseNodes[x].getValue() + ANSI_RESET + " ");
            }else if(baseNodes[x].getValue() == 'Y'){
                System.out.print(ANSI_YELLOW + baseNodes[x].getValue() + ANSI_RESET + " ");
            }
        }
        System.out.println();
    }

    public void printBaseNodes(){                   //prints all none empty nodes without color
        for (Node baseNode : baseNodes) {
                System.out.print(baseNode.getValue() + " ");
        }
        System.out.println();
    }




}