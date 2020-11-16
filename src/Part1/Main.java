package Part1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static final int vertRadius = 25;
    static final GraphModel graphModel = new GraphModel(vertRadius);
    static final GraphView graphView = new GraphView();
    static final GraphViewController graphViewController = new GraphViewController();


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setResizable(false);
        primaryStage.setTitle("Assignment #5 Part 1");
        primaryStage.setScene(new Scene(graphView,500,500));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
