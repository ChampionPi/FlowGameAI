public class Maze {

    private Node[][] nodeMaze;
    private Node[] baseNodes;
    private Node[] emptyNodes;
    private Node temp;
    private int mazeDim;
    private int baseNodesSize = 0;
    private int emptyNodesSize = 0;      //will be physical number counted starting at 1

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



    public Maze(char[][] textMaze) {
        mazeDim = textMaze[0].length;
        baseNodes = new Node[(mazeDim*mazeDim)];
        emptyNodes  = new Node[(mazeDim*mazeDim)];
        nodeMaze = new Node[mazeDim][mazeDim];
        for (int x = 0; x < mazeDim; x++) {
            for (int y = 0; y < mazeDim; y++) {
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

    public void solveMaze(){                                                                                            //will solve our maze
        for(int x = 0; x < (baseNodesSize/2)-1; x++){
            int secondNodeIndex = x + 1;
            while(baseNodes[x].getValue() != baseNodes[secondNodeIndex].getValue()){
                secondNodeIndex++;
            }
            travelFromTo(baseNodes[x], baseNodes[secondNodeIndex]);
        }

        //TODO Solve the maze
    }

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

    public void dumbSolve(){

    }

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

    private Node checkNeighborsFor(Node inNode, char inSearchFor){                                                      //can be used to find empty spaces or partner
        if( 0 <= inNode.getX() + 1 && inNode.getX() + 1 < mazeDim && inSearchFor == nodeMaze[inNode.getX() + 1][inNode.getY()].getValue()){
            return nodeMaze[inNode.getX()+1][inNode.getY()];
        }else if( 0 <= inNode.getX() - 1 && inNode.getX() - 1 < mazeDim && inSearchFor == nodeMaze[inNode.getX() - 1][inNode.getY()].getValue()){
            return nodeMaze[inNode.getX()-1][inNode.getY()];
        }else if( 0 <= inNode.getY() + 1 && inNode.getY() + 1 < mazeDim && inSearchFor == nodeMaze[inNode.getX()][inNode.getY() + 1].getValue()){
            return nodeMaze[inNode.getX()][inNode.getY()+1];
        }else if( 0 <= inNode.getY() - 1 && inNode.getY() - 1 < mazeDim && inSearchFor == nodeMaze[inNode.getX()][inNode.getY() - 1].getValue()){
            return nodeMaze[inNode.getX()][inNode.getY()-1];
        }else{
            return null;
        }
    }

    private Node checkUpNeighborFor(Node inNode, char inSearchFor){                                                      //can be used to find empty spaces or partner
        if( 0 <= inNode.getY() - 1 && inNode.getY() - 1 < mazeDim && inSearchFor == nodeMaze[inNode.getX()][inNode.getY() - 1].getValue()){
            return nodeMaze[inNode.getX()][inNode.getY()-1];
        }else{
            return null;
        }
    }

    private Node checkDownNeighborFor(Node inNode, char inSearchFor){
        if( 0 <= inNode.getY() + 1 && inNode.getY() + 1 < mazeDim && inSearchFor == nodeMaze[inNode.getX()][inNode.getY() + 1].getValue()){
            return nodeMaze[inNode.getX()][inNode.getY()+1];
        }else{
            return null;
        }
    }

    private Node checkLeftNeighborFor(Node inNode, char inSearchFor){
        if( 0 <= inNode.getX() - 1 && inNode.getX() - 1 < mazeDim && inSearchFor == nodeMaze[inNode.getX() - 1][inNode.getY()].getValue()){
            return nodeMaze[inNode.getX()-1][inNode.getY()];
        }else{
            return null;
        }
    }

    private Node checkRightNeighborFor(Node inNode, char inSearchFor){
        if( 0 <= inNode.getX() + 1 && inNode.getX() + 1 < mazeDim && inSearchFor == nodeMaze[inNode.getX() + 1][inNode.getY()].getValue()){
            return nodeMaze[inNode.getX()+1][inNode.getY()];
        }else{
            return null;
        }
    }

    private Node undo(Node inNode){
        Node temp = inNode;
        if(!inNode.getBase()) {
            inNode.setValue('_');
        } else {
            return null;
        }
        temp = checkNeighborsFor(temp, temp.getValue());
        return temp;
    }


    public void printMaze() {                       //will print out the maze, printing each nodes value which is a char
        for (int x = 0; x < mazeDim; x++) {
            for (int y = 0; y < mazeDim; y++) {
                System.out.print(nodeMaze[x][y].getValue() + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

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