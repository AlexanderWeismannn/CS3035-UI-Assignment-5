package Part1;


import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;

import java.util.*;

public class GraphModel {

    private SimpleListProperty<Vertex> vertexSimpleListProperty;
    //Using a has map to access the library of edges associated with a vertex
    private HashMap<String, Edge> storedEdges;
    //As per graph theory i will try and use an adjacency matrix to store whether vertices are adjacent or not
    private ArrayList<ArrayList<Integer>> vertexAdjacencyMatrix;
    private int vertRadius;
    private int numOfVertices;

    GraphModel(int vertRadius){
        ArrayList<Vertex> list = new ArrayList<>();
        ObservableList<Vertex> observableList = FXCollections.observableList(list);
        vertexSimpleListProperty = new SimpleListProperty<>(observableList);

        this.storedEdges = new HashMap<>();
        this.vertexAdjacencyMatrix = new ArrayList<>();
        this.vertRadius = vertRadius;
        //default to 1
        this.numOfVertices = 1;
    }

    //getter for the vertex list
    SimpleListProperty<Vertex> getVertexSimpleListProperty(){
        return vertexSimpleListProperty;
    }

    //add
    void addVertexToList(int x, int y){
       Vertex newVert = new Vertex(numOfVertices++, x,y, vertRadius);
       addVertexToMatrix();
       vertexSimpleListProperty.add(newVert);
    }

    //delete
    void deletedVertAtPos(int x, int y){
        Vertex vertToDelete = getVertexAtPos(x,y);
        if(vertToDelete != null){
            removeEdges(vertToDelete);
            unMarkAdjMatrix(vertToDelete);
            vertexSimpleListProperty.remove(vertToDelete);
            numOfVertices--;
            numOfVertices = Math.min(getMaxID(),numOfVertices) + 1;
        }

    }

    int getMaxID(){
        int maxVal = 0;
        for( Vertex v : vertexSimpleListProperty){
            if(v.getID() > maxVal){
                maxVal = v.getID();
            }
        }
        return maxVal;
    }

    private void addVertexToMatrix(){
        for(int i = 0; i < numOfVertices - 2; i++){
            ArrayList<Integer> col = vertexAdjacencyMatrix.get(i);
            for(int j = 0; j < (numOfVertices - col.size()) - 1; j++){
                col.add(0);
            }
        }
        for(int i = numOfVertices - 2; i < vertexAdjacencyMatrix.size(); i++){
            vertexAdjacencyMatrix.get(i).clear();
        }


        ArrayList<Integer> vertColumn = new ArrayList<>();
        for(int i = 0; i < numOfVertices; i++){
            vertColumn.add(0);
        }
        //row
        vertexAdjacencyMatrix.add(vertColumn);
        for(int i = vertexAdjacencyMatrix.size() - 1; i >= 0; i--){
            ArrayList<Integer> row = vertexAdjacencyMatrix.get(i);
            if(row.size() == 0){
                vertexAdjacencyMatrix.remove(row);
            }
        }
        //for each column
        for(ArrayList<Integer> row: vertexAdjacencyMatrix){
            for(int i = row.size() - 1; i >= 0; i--){
                if(i > numOfVertices - 2){
                    row.remove(i);
                }
            }

        }

    }

    Vertex getVertexAtPos(int x, int y){
        Vertex vert = null;
        for(Vertex v : vertexSimpleListProperty){
            if(v.isContained(x,y)){
                vert = v;
            }
        }
        return vert;
    }

    HashMap<String,Edge> getStoredEdges(){
        return storedEdges;
    }

    String genEdgeID(Vertex v1, Vertex v2){
        return v1.getID() + "-" + v2.getID();
    }


    void addEdges(int x1, int y1, int x2, int y2){
        Vertex v1 = getVertexAtPos(x1,y1);
        Vertex v2 = getVertexAtPos(x2,y2);

        if(v2 != null && v1 != v2){
            Point2D v2Pos = v2.getPosition();
            x2 = (int) v2Pos.getX();
            y2 = (int) v2Pos.getY();
            String edgeID = genEdgeID(v1,v2);
            String reversedEdgeID = genEdgeID(v2,v1);

            if(!storedEdges.containsKey(edgeID) && !storedEdges.containsKey(reversedEdgeID)){
                markAdjMatrix(v1,v2);
                Edge e = new Edge(x1,y1,x2,y2,v1,v2);
                //store the edge
                storedEdges.put(edgeID,e);
            }
        }
    }

    void markAdjMatrix(Vertex v1, Vertex v2) {
        int v1IDX = v1.getID() -1;
        int v2IDX = v2.getID() -1;
        vertexAdjacencyMatrix.get(v1IDX).set(v2IDX,1);
        vertexAdjacencyMatrix.get(v2IDX).set(v1IDX,1);
    }

    void unMarkAdjMatrix(Vertex vertToDelete){
        int vIDX = vertToDelete.getID() - 1;
        ArrayList<Integer> clearRow = new ArrayList<>();
        for(ArrayList<Integer> matrix : vertexAdjacencyMatrix){
            matrix.set(vIDX,0);
            clearRow.add(0);

        }
    }

    void removeEdges(Vertex vertexToDelete){
        int vIDx = vertexToDelete.getID() - 1;
        ArrayList<Integer> row = vertexAdjacencyMatrix.get(vIDx);
        for(int i = 0; i < row.size(); i++){
            if(row.get(i) == 1){
                String id = (vIDx + 1) + "-" + (i+1);
                String reverseID = (i + 1) + "-" + (vIDx + 1);
                storedEdges.remove(id);
                storedEdges.remove(reverseID);
            }
        }

    }


    void updateEdgePositions(Vertex movingVert){
        Set<Map.Entry<String,Edge>> edgeEntries = storedEdges.entrySet();
        for(Object o: edgeEntries){
            Map.Entry entry = (Map.Entry) o;
            String key = (String) entry.getKey();
            String[] keys = key.split("-",2);
            Edge e = (Edge) entry.getValue();
            int v1 = Integer.parseInt(keys[0]);
            int v2 = Integer.parseInt(keys[1]);

            if(movingVert.getID() == v1 || movingVert.getID() == v2){
                int indexID = key.indexOf(Integer.toString(movingVert.getID()));
                int indexSpace = key.indexOf("-");

                if( indexID < indexSpace){
                    e.setStart(new Point2D(movingVert.getX(), movingVert.getY()));
                }else{
                    e.setEnd(new Point2D(movingVert.getX(), movingVert.getY()));
                }
            }
        }
    }




}
