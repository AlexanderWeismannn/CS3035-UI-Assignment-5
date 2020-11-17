package Part2;


import javafx.collections.ListChangeListener;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.*;


public class GraphView extends Pane {


    private Line selectedEdge;
    static final Color DEFAULT_VERTEX_COLOR = Color.LIGHTBLUE;
    static final Color SELECTED_VERTEX_COLOR = Color.ORANGE;


    GraphView(){
        //initilialize the background color and set no selected edge
        this.setStyle("-fx-background-color: #d3d3d3");
        this.selectedEdge = null;
        Main.graphModel.getVertexSimpleListProperty().addListener((ListChangeListener<Vertex>) e -> draw());
            // finish draw methods
    }

    boolean tryDrawingEdge(int x1, int y1, int x2, int y2){
        selectedEdge = drawEdge(x1,y1,x2,y2);
        Vertex v1 = Main.graphModel.getVertexAtPos(x1,y1);
        Vertex v2 = Main.graphModel.getVertexAtPos(x2,y2);

        if(v2 != null && v1 != v2){
            v2.setClickedTrue();
            v2.setConnectedTrue();
            Main.graphModel.addEdges(x1,y1,x2,y2);
            return true;
        }
        return false;
    }


    Line drawEdge(int x1, int y1, int x2, int y2){
        Line edge = new Line(x1,y1,x2,y2);
        Vertex v2 = Main.graphModel.getVertexAtPos(x2,y2);
        if( v2 == null){
            edge.setStrokeWidth(4);
        }else{
            edge.setStrokeWidth(1);
        }

        edge.setFill(Color.BLACK);
        edge.setStroke(Color.BLACK);
        return edge;
    }


    void resetUsedEdges(){
        this.selectedEdge = null;
    }


    @Override
    public void layoutChildren(){
        draw();
    }


    void draw(){
        this.getChildren().clear();
        if(selectedEdge != null){
            int x1 = (int) selectedEdge.getStartX();
            int y1 = (int) selectedEdge.getStartY();
            int x2 = (int) selectedEdge.getEndX();
            int y2 = (int) selectedEdge.getEndY();
            this.getChildren().add(drawEdge(x1,y1,x2,y2));
        }

        Set<Map.Entry<String,Edge>> edgeEntries = Main.graphModel.getStoredEdges().entrySet();
        for(Object o: edgeEntries){
            Map.Entry entry = (Map.Entry) o;
            Edge e = (Edge) entry.getValue();
            Point2D start = e.getStart();
            Point2D end = e.getEnd();

            int x1 = (int) start.getX();
            int y1 = (int) start.getY();
            int x2 = (int) end.getX();
            int y2 = (int) end.getY();

            Vertex v1 = Main.graphModel.getVertexAtPos(x1,y1);
            Vertex v2 = Main.graphModel.getVertexAtPos(x2,y2);

            if(v1 != null && v2 != null){
                this.getChildren().add(drawEdge(x1,y1,x2,y2));
            }

        }
        for(Vertex v : Main.graphModel.getVertexSimpleListProperty()){
            Circle c = new Circle(v.getPosition().getX(), v.getPosition().getY(), v.getRadius());

            if(v.getIsClicked()){
                c.setFill(SELECTED_VERTEX_COLOR);
                c.setStroke(Color.BLACK);
                c.setStrokeWidth(2);
                //add stroke type and line join
                c.setStrokeType(StrokeType.OUTSIDE);
                c.setStrokeLineJoin(StrokeLineJoin.ROUND);
            }else{
                c.setFill(DEFAULT_VERTEX_COLOR);
                c.setStroke(Color.BLACK);
                c.setStrokeWidth(2);
                //add stroke type and line join
                c.setStrokeType(StrokeType.OUTSIDE);
                c.setStrokeLineJoin(StrokeLineJoin.ROUND);
            }

            if(v.getIsConnected()){
                c.setStroke(Color.BLACK);
                c.setStrokeWidth(3);
                //add stroke type and line join
                c.setStrokeType(StrokeType.OUTSIDE);
                c.setStrokeLineJoin(StrokeLineJoin.ROUND);
            }


            //adding the circle and text
            Text text = new Text();
            text.setText(Integer.toString(v.getID()));
            text.setFont(new Font("times",18));
            text.setTextAlignment(TextAlignment.CENTER);

            StackPane stack = new StackPane();
            stack.setLayoutX(v.getPosition().getX());
            stack.setLayoutY(v.getPosition().getY());
            stack.getChildren().addAll(c,text);

            stack.toBack();

            this.getChildren().add(stack);


        }
    }
}
