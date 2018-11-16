class Node {
    private int xCoord;
    private int yCoord;
    private int numOptions;
    private char value;
    private boolean base; // If node is a source
    private boolean[] possibleValues;
    //private int flow; // tells which way flow should go 1=next 2=last 0= not base node
    //private Node next= null;
    //private Node last= null;


    public Node (int inX, int inY, char inValue) {
        xCoord = inX;
        yCoord = inY;
        value = inValue;
        base = value != '_';
        //flow = 0;
        base = (value != '_');
    }

    public Node (Node inNode) {
        xCoord = inNode.getX();
        yCoord = inNode.getY();
        value = inNode.getValue();
        base = inNode.getBase();
    }



    public int getX(){
        return xCoord;
    }

    public int getY(){
        return yCoord;
    }

    public char getValue(){
        return value;
    }

    public void setValue(char inValue){
        if(base == false) {
            value = inValue;
        }
    }

    public void instantiatePossibleValues(int inSize){
        possibleValues = new boolean[inSize];
        numOptions =  inSize;
    }

    public void setPossibleValues(boolean[] inValues){
        possibleValues = inValues;
    }

    public Boolean getBase() {
        return base;
    }

//    public void setFlow(int inFlow){
//        flow = inFlow;
//    }
//
//    public int getFlow(){
//        return flow;
//    }

//    public Node getNext(){return next;}
//    public void setNext(Node in){next = in;}
//
//    public Node getLast(){return last;}
//    public void setLast(Node in){last = in;}

}