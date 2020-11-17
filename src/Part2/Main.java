package Part2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    private static final int vertRadius = 25;
    static final GraphModel graphModel = new GraphModel(vertRadius);
    static final GraphView graphView = new GraphView();
    static final GraphViewController graphViewController = new GraphViewController();
    static InteractionModel interactionModel;
    private static Scene scene;


    @Override
    public void start(Stage primaryStage) throws Exception {

        interactionModel = new InteractionModel();

        primaryStage.setTitle("Assignment 5 Pt 2");
        primaryStage.setResizable(false);
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(graphView);
        borderPane.setTop(interactionModel);

        scene = new Scene(borderPane, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    static Scene getScene() {
        return scene;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
