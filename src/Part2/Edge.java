package Part2;

import javafx.geometry.Point2D;

public class Edge {

    private Point2D start;
    private Point2D end;
    private Vertex startVertex;
    private Vertex endVertex;

    Edge(int x1, int y1, int x2, int y2, Vertex startVertex, Vertex endVertex){
        this.start = new Point2D(x1,y1);
        this.end = new Point2D(x2,y2);
        this.startVertex = startVertex;
        this.endVertex = endVertex;
    }

    //getter and setter for the start position
    void setStart(Point2D startPos){
        this.start = startPos;
    }

    Point2D getStart(){
        return start;
    }


    //getter and setter for the end position
    void setEnd(Point2D endPos){
        this.end = endPos;
    }

    Point2D getEnd(){
        return end;
    }

    //getters for the two vertex's that the edge is attached to
//    Vertex getStartVertex(){ return startVertex; }
//    Vertex getEndVertex(){ return endVertex; }



}
