import java.util.ArrayList;

public class SimpleSolver {
    private final Maze initialMaze;
    private Node[][] nodes;



    public SimpleSolver(Maze inMaze){
        initialMaze = inMaze;
        nodes = inMaze.getNodeArray();

        mazeDFS(nodes, inMaze);

    }

    private Node[][] mazeDFS(Node[][] nodes, Maze inMaze) {
        // TODO: Colter's logic
        ArrayList<Integer> directions;



        return null;
    }


    public Node[][] logicUpdate(Node [][] in, Node [] Base){
        Node[][] tarray = in;

        for(int i =0;i<Base.length;i++){
            Node temp = Base[i];
            temp = findEnd(temp);
            if(temp.getFlow() !=0){
                System.out.println(temp.getValue()+" flow complete, logical update end node = base node");
            }
            int only = ifOnly(temp,in);         // return direction of next flow
            if(only==0){                    //up
                in[temp.getX()+1][temp.getY()].setValue(temp.getValue());
            }
        }

        return tarray;
    }

    public Node findEnd(Node temp){
        int flow = temp.getFlow();   // tell which way flow

        if(flow == 1){
            while(temp.getNext() != null){
                temp = temp.getNext();
            }
        }
        else if(flow == 2){
            while(temp.getLast() != null){
                temp = temp.getLast();
            }
        }
        else {
            System.out.println("Non base node in findEnd");
        }
        return temp;
    }

    public Node [] checkNeighborsFor(Node inNode, Node [][] maze){                                               //can be used to find empty spaces or partner

        Node temp [] = new Node [3];

        if( 0 <= inNode.getX() + 1 && inNode.getX() + 1 < maze.length){ // add right node to array
            temp[1]= maze[inNode.getX()+1][inNode.getY()];
        }else if( 0 <= inNode.getX() - 1 && inNode.getX() - 1 < maze.length ){ // add left node
            temp[3]= maze[inNode.getX()-1][inNode.getY()];
        }else if( 0 <= inNode.getY() + 1 && inNode.getY() + 1 < maze.length ){ // down
            temp[2]= maze[inNode.getX()][inNode.getY()+1];
        }else if( 0 <= inNode.getY() - 1 && inNode.getY() - 1 < maze.length ){ // up
            temp[0]= maze[inNode.getX()][inNode.getY()-1];
        }
        return temp;

    }

    public int ifOnly(Node temp, Node [][] maze){                  // checks if there is only one spot to go
        Node [] neighbors = checkNeighborsFor(temp, maze);
        int spots =0;     // keep track of open spots
        int spot = 5;
        for(int i=0; i<3;i++){
            if(neighbors[i]!= null && neighbors[i].getValue() == '_'){
                spots++;
                spot = i;
            }
        }
        if(spots>1){
            return spot;  // too many spots, no constraints
        }
        else{
            return spot;
        }

    }

    public Node[][] update(Node[][] in, Node origonal, Node change){




        return null;
    }

}
