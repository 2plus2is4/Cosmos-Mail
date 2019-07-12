package Controllers;

import Classes.Account;
import Classes.MyApp;
import Interfaces.IContact;
import Interfaces.IFolder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpController {

    @FXML
    TextField nameField;
    @FXML
    TextField mailField;
    @FXML
    TextField passField;
    @FXML
    Button signupButton;
    @FXML
    Hyperlink signinLink;
    @FXML
    Label passwordError;

    private MyApp app;
    private Account currentAccount = new Account();
    private IFolder currentFolder = new IFolder();
    private Stage myStage;


    public void setup(Stage myStage,MyApp app) {
        this.myStage = myStage;
        this.app = app;
    }

    public void signIn(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../FXMLs/theFXML.fxml"));
        Parent root = fxmlLoader.load();
        myStage.setTitle("Cosmos Mail");
        myStage.setScene(new Scene(root, 844.0D, 550.0D));
        myStage.setResizable(false);
        SignInController signInController = fxmlLoader.getController();
        signInController.setup(myStage);
        myStage.show();
    }

    public void signUp(ActionEvent event) throws IOException {
        IContact c = new IContact(this.nameField.getText(), this.passField.getText(), this.mailField.getText());
        if (this.passField.getText().length() < 8) {
            this.passwordError.setVisible(true);
            this.nameField.clear();
            this.mailField.clear();
            this.passField.clear();
        } else if (app.signup(c)) {
            currentAccount = new Account(c.getName(), c.getPassword(), c.getEmail());
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../FXMLs/mails.fxml"));
            Parent root = fxmlLoader.load();
            myStage.setTitle("Cosmos Mail");
            myStage.setScene(new Scene(root, 879.0D, 564.0D));
            myStage.setResizable(false);
            MailsController mailsController = fxmlLoader.getController();
            mailsController.setup(myStage,app,currentFolder,currentAccount);
            myStage.show();
        }
    }
}
