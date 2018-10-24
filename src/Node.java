class Node {
    private int xCoord;
    private int yCoord;
    private int numOptions;
    private char value;
    private boolean base;
    private boolean[] possibleValues;

    public Node (int inX, int inY, char inValue) {
        xCoord = inX;
        yCoord = inY;
        value = inValue;
        base = value != '_';
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
        value = inValue;
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
}