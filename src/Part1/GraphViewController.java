package Part1;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseButton;

public class GraphViewController {

    private Vertex movingVert;
    private Vertex connectingVert;
    private Vertex connectedVert;
    private boolean shiftPressed;
    private boolean drag;

    GraphViewController(){
        //handling mouse dragged
        Main.graphView.setOnMouseDragged(e -> {
            System.out.println("Mouse DRAGGED\n");
            int posX = (int) e.getX();
            int posY = (int) e.getY();

            if(shiftPressed && !drag && connectingVert != null){
                Point2D vertPos = connectingVert.getPosition();
                connectingVert.setClickedTrue();
                connectingVert.setConnectedTrue();
                connectedVert = Main.graphModel.getVertexAtPos(posX,posY);
                if( Main.graphView.tryDrawingEdge((int) vertPos.getX(),(int) vertPos.getY(),posX, posY)){
                    drag = true;
                    return;
                }
            }else{
                if(movingVert != null){
                    movingVert.setX(posX);
                    movingVert.setY(posY);
                    movingVert.setClickedTrue();
                    Main.graphModel.updateEdgePositions(movingVert);
                }
            }
            Main.graphView.layoutChildren();
        });

        //handling mouse pressed
        Main.graphView.setOnMousePressed(e -> {
            System.out.println("Mouse PRESSED\n");
            int posX = (int) e.getX();
            int posY = (int) e.getY();
            Vertex pressedVert = Main.graphModel.getVertexAtPos(posX,posY);
            if(e.isSecondaryButtonDown() && !e.isPrimaryButtonDown()){
                System.out.println("Right click, DELETE VERTEX");
                Main.graphModel.deletedVertAtPos(posX,posY);
                //else add the edge
            }else if(e.isShiftDown()){
                System.out.println("Shift has been pressed");
                drag = false;
                connectingVert = pressedVert;
                shiftPressed = true;
                //else drag vertex
            }else if(pressedVert != null){
                System.out.println("Moving vertex " + movingVert);
                movingVert = pressedVert;
                //else add node w/ left click
            }else if(e.getButton() == MouseButton.PRIMARY){
                System.out.println("add vertex");
                Main.graphModel.addVertexToList(posX,posY);
            }
        });

        //handling mouse released
        Main.graphView.setOnMouseReleased(e -> {
            System.out.println("Mouse RELEASED");
            if( movingVert != null){
                movingVert.setClickedFalse();
                Main.graphView.layoutChildren();
            }

            if(connectingVert != null){
                connectingVert.setClickedFalse();
                connectingVert.setConnectedfalse();
                Main.graphView.layoutChildren();

                if(connectedVert != null){
                    connectedVert.setClickedFalse();
                    connectedVert.setConnectedfalse();
                }
            }

            movingVert = null;
            connectingVert = null;
            shiftPressed = false;
            drag = true;
            Main.graphView.resetUsedEdges();
        });

    }



}
