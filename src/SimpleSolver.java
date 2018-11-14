import java.util.ArrayList;
import java.util.Map;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

public class SimpleSolver {
    private final Maze initialMaze;
    private Node[][] nodes;



    public SimpleSolver(Maze inMaze){
        initialMaze = inMaze;
        nodes = inMaze.getNodeMaze();

        mazeDFS(nodes, inMaze);

        Node[][]in = inMaze.getNodeMaze();
        Node[] Base = inMaze.getBaseNodes();

        printBase(Base);
        printMaze(in);

        in = logicUpdate(in,Base);

        printMaze(in);

    }

    private Node[][] mazeDFS(Node[][] nodeMaze, Maze inMaze) {
        // TODO: Colter's logic

        Map<Node,Integer> directions; // maps nodes to directions, 0 for right

        for (int i = 0; i < nodeMaze.length; i++) {
            for (int j = 0; j < nodeMaze.length; j++) {



            }
        }


        return null;
    }


    public Node[][] logicUpdate(Node [][] in, Node [] Base){


        printMaze(in);
        int i=0;

        while(Base[i]!= null){
            Node temp = Base[i];
            temp = findEnd(temp, temp, in);

            System.out.println("check at "+temp.getValue());
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

    public Node findEnd(Node temp,Node last, Node [][] in){

        Node [] arr = checkNeighborsFor(temp,in);
        System.out.println("findEnd temp "+temp.getX()+", "+temp.getY());
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
                System.out.println("found from base "+ option.getValue());
                temp = findEnd(option,temp,in);
            }
            else{
                System.out.println("base node w multiple same surrounding");
                return null;
            }
        }
        else if(temp.getBase()){    // flow complete******
            Node blank = new Node(-1,-1,'#');
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

    public Node [] checkNeighborsFor(Node inNode, Node [][] maze){                                               //can be used to find empty spaces or partner

        Node temp [] = new Node [4];
        System.out.println("Check Neighbors of "+inNode.getValue()+ " at  x "+ inNode.getX()+" y "+inNode.getY());

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

    public int ifOnly(Node temp, Node [][] maze){                  // checks if there is only one spot to go
        Node [] neighbors = checkNeighborsFor(temp, maze);
        //System.out.println("ifOnly neighbors of "+temp.getX()+", "+ temp.getY());
        printBase(neighbors);

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
        printMaze( in);
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
                System.out.print("null, ");
            }
            else {
                System.out.print(base[i].getValue() + ", ");
            }
        }
        System.out.println();
    }

}
