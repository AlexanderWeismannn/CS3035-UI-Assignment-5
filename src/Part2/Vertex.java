package Part2;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;



public class Vertex {
    private int ID;
    private int radius;
    private int x;
    private int y;
    private boolean isClicked;
    private boolean isConnected;
    private Color color;

    Vertex( int ID, int x, int y, int radius, Color color){
        this.ID = ID;
        this.x = x;
        this.y = y;
        this.radius = radius;
        //default both to false as the mouse will be unclicked when added / and will have no connections
        this.isClicked = false;
        this.isConnected = false;
        this.color = color;
    }


    //getter and setters for the X and Y coordinate
    int getX(){
        return x;
    }

    int getY(){
        return y;
    }

    void setX(int x){
        this.x = x;
    }

    void setY(int y){
        this.y = y;
    }


    //getter and setter for the ID
    int getID(){
        return ID;
    }

    void setID(int ID){
        this.ID = ID;
    }


    // get the position of the vertex in terms of a 2D point in space
    Point2D getPosition(){
        Point2D newPoint = new Point2D(x,y);
        return newPoint;
    }

    //check if the circle object contains the bounds of x and y
    boolean isContained(int x, int y){
        Circle circle = new Circle(x,y,radius);
        return circle.contains(this.x, this.y);
    }


    //get the clicked value and set it to True or False
    boolean getIsClicked(){
        return isClicked;
    }

    void setClickedTrue(){
        this.isClicked = true;
    }

    void setClickedFalse(){
        this.isClicked = false;
    }


    //getter and setter for the radius of the vertex
    int getRadius(){
        return radius;
    }

    void setRadius(){
        this.radius = radius;
    }

    // get the connected value and set it to True or False
    boolean getIsConnected(){
        return isConnected;
    }

    void setConnectedTrue(){
        this.isConnected = true;
    }

    void setConnectedfalse(){
        this.isConnected = false;
    }

    void setColor(Color color){
        this.color = color;
    }

    Color getColor(){
        return color;
    }


}