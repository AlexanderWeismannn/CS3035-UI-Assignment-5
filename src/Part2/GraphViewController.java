package Part2;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;


public class GraphViewController {

    private Vertex movingVert;
    private Vertex connectingVert;
    private Vertex connectedVert;
    private boolean shiftPressed;
    private boolean drag;

    GraphViewController() {
        //handling mouse dragged
        Main.graphView.addEventHandler(MouseEvent.ANY, new MouseHandler());
    }

    public class MouseHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent e) {

            if (e.getEventType() == MouseEvent.MOUSE_PRESSED) {
                System.out.println("MOUSE PRESSED");
                int xPos = (int) e.getX();
                int yPos = (int) e.getY();
                Vertex selected = Main.graphModel.getVertexAtPos(xPos, yPos);
                if (e.isSecondaryButtonDown() && !e.isPrimaryButtonDown()) {
                    if (!Main.interactionModel.isCreatingVertices()) {
//                        System.out.println("\t\tRight click\t->\tdelete vertex");
                        Main.graphModel.deletedVertAtPos(xPos, yPos);
                    }
                }
                // add edge
                else if (e.isShiftDown()) {
                    if (Main.interactionModel.isCreatingVertices()) {
                        drag = false;
                        connectingVert = selected;
                        shiftPressed = true;
//                        System.out.println("\t\tshift pressed\t->\tadd edge\n\t\tConnectingVertex: " + connectingVertex);
                    }
                }
                // move vertex
                else if (selected != null) {
                    if (Main.interactionModel.isCreatingVertices()) {
                        movingVert = selected;
                        movingVert.setClickedTrue();
//                        System.out.println("\t\tDrag click\t->\tmove vertex\n\t\tMovingVertex: " + movingVertex);
                        if (!e.isDragDetect()) {
                            if (e.getTarget().getClass() == Text.class) {
                            }
                            if (e.getTarget().getClass() == Circle.class) {
                                Circle c = (Circle) e.getTarget();
                                c.setFill(GraphView.SELECTED_VERTEX_COLOR);
                            } else if (e.getTarget().getClass() == Label.class) {
                                //Label txt = (Label) e.getTarget();
                                //not actually useful for what i want to do
                            }
                        }
                    }

                }
                // add node
                else if (e.getButton() == MouseButton.PRIMARY){
                    if (Main.interactionModel.isCreatingVertices()) {
//                    System.out.println("\t\tLeft click\t->\tadd vertex");
                        Main.graphModel.addVertexToList(xPos, yPos);
                    }
                }
                if (selected != null && Main.interactionModel.isCreatingVertices()) {
                    selected.setClickedTrue();
                }


            }

            if (e.getEventType() == MouseEvent.MOUSE_RELEASED) {
//                System.out.println("MOUSE RELEASED");
                if (movingVert != null) {
                    movingVert.setClickedFalse();
                }
                if (connectingVert != null) {
                    connectingVert.setClickedFalse();
                    connectingVert.setConnectedfalse();
                    if (connectedVert != null) {
                        connectedVert.setClickedFalse();
                        connectedVert.setConnectedfalse();
                    }
                }

                int x = (int) e.getX();
                int y = (int) e.getY();
                Vertex hoveringOver = Main.graphModel.getVertexAtPos(x, y);
                if (hoveringOver != null) {
                    hoveringOver.setClickedFalse();
                }

                movingVert = null;
                connectingVert = null;
                shiftPressed = false;
                drag = true;
                Main.graphView.resetUsedEdges();
                Main.graphView.layoutChildren();
            }

            if (e.getEventType() == MouseEvent.MOUSE_DRAGGED) {
//                System.out.println("MOUSE DRAGGED");
                if (Main.interactionModel.isCreatingVertices()) {
                    int xPos = (int) e.getX();
                    int yPos = (int) e.getY();
                    if (shiftPressed && !drag && connectingVert != null) {
                        Point2D vPos = connectingVert.getPosition();
                        connectingVert.setClickedTrue();
                        connectingVert.setConnectedTrue();
                        connectedVert = Main.graphModel.getVertexAtPos(xPos, yPos);
                        if (Main.graphView.tryDrawingEdge((int) vPos.getX(), (int) vPos.getY(), xPos, yPos)) {
                            drag = true;
                            return;
                        }
                    } else {
                        if (movingVert != null) {
                            int horizontalSpace = (int) (Main.getScene().getWidth() - movingVert.getRadius());
                            int verticalSpace = (int) ((Main.getScene().getHeight() - Main.interactionModel.getHeight())
                                    - movingVert.getRadius());
                            xPos = Math.min(horizontalSpace, Math.max(movingVert.getRadius(), xPos));
                            yPos = Math.min(verticalSpace, Math.max(movingVert.getRadius(), yPos));
                            movingVert.setX(xPos);
                            movingVert.setY(yPos);
                            movingVert.setClickedTrue();
                            Main.graphModel.updateEdgePositions(movingVert);
                        }
                    }
                    Main.graphView.layoutChildren();
                }
            }

            if (e.getEventType() == MouseEvent.MOUSE_MOVED) {
                if (!Main.interactionModel.isCreatingVertices()) {
                    int x = (int) e.getX();
                    int y = (int) e.getY();
                    Vertex v = Main.graphModel.getVertexAtPos(x, y);
                    if (v != null) {
                        Main.getScene().setCursor(Cursor.CROSSHAIR);
                    }
                    else {
                        Main.getScene().setCursor(Cursor.DEFAULT);
                    }
                }
            }
        }
    }
}