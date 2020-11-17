package Part2;


import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

class InteractionModel extends Pane {

    private HBox toolBar;
    private Color selectedColor;
    private boolean creatingVertices;

    InteractionModel() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("toolbar.fxml"));
        this.toolBar = fxmlLoader.load();
        this.getChildren().add(toolBar);
        this.selectedColor = Color.valueOf("#ff3333");
        this.creatingVertices = true;
    }

    Color getSelectedColor() {
        return selectedColor;
    }

    void setSelectedColor(Color color) {
        this.selectedColor = color;
    }

    void redButtonSelected() {
        toolBar.lookup("#redButton").setStyle("-fx-text-fill: white; -fx-base: #ff3333;");
    }

    void greenButtonSelected() {
        toolBar.lookup("#greenButton").setStyle("-fx-text-fill: white; -fx-base: green;");
    }

    void blueButtonSelected() {
        toolBar.lookup("#blueButton").setStyle("-fx-text-fill: white; -fx-base: blue;");
    }

    void unSelectRedButton() {
        toolBar.lookup("#redButton").setStyle("-fx-text-fill: black; -fx-base: #ff3333;");
    }

    void unSelectGreenButton() {
        toolBar.lookup("#greenButton").setStyle("-fx-text-fill: black; -fx-base: green;");
    }

    void unSelectBlueButton() {
        toolBar.lookup("#blueButton").setStyle("-fx-text-fill: black; -fx-base: blue;");
    }

    void setIsCreating() {
        this.creatingVertices = true;
    }

    void setIsDeleting() {
        this.creatingVertices = false;
    }

    boolean isCreatingVertices() {
        return creatingVertices;
    }
}