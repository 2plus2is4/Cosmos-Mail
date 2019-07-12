package Classes;

import Controllers.SignInController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../FXMLs/theFXML.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Cosmos Mail");
        primaryStage.setScene(new Scene(root, 844.0D, 550.0D));
        primaryStage.setResizable(false);
        SignInController signInController = fxmlLoader.getController();
        signInController.setup(primaryStage);
        primaryStage.show();


    }

}
