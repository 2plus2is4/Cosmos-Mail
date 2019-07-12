package Controllers;

import Classes.Account;
import Classes.MyApp;
import Interfaces.IFilter;
import Interfaces.IFolder;
import Interfaces.ISort;
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

public class SignInController {

    @FXML
    TextField mailField;
    @FXML
    TextField passwordField;
    @FXML
    Hyperlink nomailsignup;
    @FXML
    Button signinButton;
    @FXML
    Label errorLabel;

    private Stage myStage;
    private MyApp app = new MyApp();
    private Account currentAccount = new Account();
    private IFolder currentFolder = new IFolder();

    public void setup(Stage primaryStage) {
        this.myStage = primaryStage;
    }

    public void signUp(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../FXMLs/sample.fxml"));
        Parent root = fxmlLoader.load();
        myStage.setTitle("Cosmos Mail");
        myStage.setScene(new Scene(root, 879.0D, 564.0D));
        myStage.setResizable(false);
        SignUpController signUpController = fxmlLoader.getController();
        signUpController.setup(myStage,app);
        myStage.show();
    }

    public void signIn(ActionEvent event) throws IOException {
        String s1 = this.mailField.getText();
        String s2 = this.passwordField.getText();
        if (app.signin(s1, s2)) {
            currentAccount.setEmail(app.accountEmail);
            currentAccount.setPassword(this.passwordField.getText());
            currentAccount.setName(app.accountName);
            currentFolder = app.inbox;
//            app.setViewingOptions(currentFolder, (IFilter)null, new ISort(currentFolder.getFolderList(), "date"));
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../FXMLs/mails.fxml"));
            Parent root = fxmlLoader.load();
            myStage.setTitle("Cosmos Mail");
            myStage.setScene(new Scene(root, 879.0D, 564.0D));
            myStage.setResizable(false);
            MailsController mailsController = fxmlLoader.getController();
            mailsController.setup(myStage,app,currentFolder,currentAccount);
            myStage.show();

        } else {
            this.errorLabel.setVisible(true);
            this.mailField.clear();
            this.passwordField.clear();
        }
    }
}

