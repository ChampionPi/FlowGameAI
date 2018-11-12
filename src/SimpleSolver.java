public class SimpleSolver {

    //private Maze maze;



    public SimpleSolver(Maze inMaze){

        //maze = inMaze;




    }


    public Node[][] logicUpdate(Node [][] in, Node [] Base){
        Node[][] tarray = in;

        for(int i =0;i<Base.length;i++){
            Node temp = Base[i];
            temp = findEnd(temp);
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
}
