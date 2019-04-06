package Classes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private static Stage primaryStage;

    public Main() {
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void setPrimaryStage(Stage primaryStage) {
        primaryStage = primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        setPrimaryStage(primaryStage);
        Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("FXMLs/theFXML.fxml"));
        primaryStage.setTitle("Cosmos Mail");
        primaryStage.setScene(new Scene(root, 844.0D, 550.0D));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
