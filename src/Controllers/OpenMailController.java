package Controllers;

import Classes.Account;
import Classes.MyApp;
import Interfaces.IFolder;
import Interfaces.IMail;
import Interfaces.MyDLinkedList;
import Interfaces.MyLinkedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Date;


public class OpenMailController {

    @FXML
    VBox foldersBox;
    @FXML
    Label name;
    @FXML
    Button signoutButton;
    @FXML
    Button compose;
    @FXML
    Button newf;
    @FXML
    VBox newfolderBox;
    @FXML
    Button addfolder;
    @FXML
    TextField newfoldername;
    @FXML
    Label from2;
    @FXML
    Label subject2;
    @FXML
    Label date2;
    @FXML
    TextArea msg;
    @FXML
    HBox attachmentz;
    @FXML
    ComboBox<String> fltr;
    @FXML
    ImageView drft;
    @FXML
    ImageView move;
    @FXML
    ComboBox<Integer> prior;
    @FXML
    ImageView delete;
    @FXML
    ImageView star;
    @FXML
    VBox moveLabel;
    @FXML
    TextField mvtof;
    @FXML
    Button moooove;
    @FXML
    StackPane str;


    private Stage myStage;
    private MyApp app;
    private Account currentAccount;
    private IFolder currentFolder;
    private IMail[] currentPage;
    private IMail currentMail;
    private int pageIterator = 0;

    public void setup(Stage myStage, MyApp app, IFolder currentFolder, Account currentAccount, IMail[] currentPage, int pageIterator, IMail currentMail) {
        this.myStage = myStage;
        this.app = app;
        this.currentAccount = currentAccount;
        this.currentFolder = currentFolder;
        this.currentMail = currentMail;
        this.pageIterator = pageIterator;
        this.currentPage = currentPage;
        name.setText(currentAccount.getName());

        readFolders();
        readFilters();
        fillFlag();
        fillFilter();

        showMailInfo();
    }

    private void showMailInfo() {
        this.from2.setText(this.currentMail.getSender());
        this.subject2.setText(this.currentMail.getSubject());
        this.date2.setText(this.currentMail.getDate().toString());
        this.msg.setText(this.currentMail.getText());

        if (this.currentMail.isStar()) {
            str.getChildren().get(1).setVisible(false);
        }
        if(this.currentMail.getAttach()!=null)
        for (int i = 0; i < this.currentMail.getAttach().size(); i++) {
            Label label = new Label();
            label.prefHeight(58.0D);
            label.prefWidth(147.0D);
            File f = ((File) currentMail.getAttach().get(i));
            label.setText(" " + f.getName() + " ");
            label.setOnMouseClicked((event) -> this.openFile(f));
            this.attachmentz.getChildren().add(label);
        }
    }

    private void openFile(File f) {
        try {
            Desktop desktop = Desktop.getDesktop();
            desktop.open(f);
        } catch (IOException var3) {
        }
    }

    private void fillFilter() {
        fltr.getItems().add("By Sender");
        fltr.getItems().add("By Subject");
    }

    private void fillFlag() {
        prior.getItems().add(1);
        prior.getItems().add(2);
        prior.getItems().add(3);
        prior.getItems().add(4);
        prior.getItems().add(5);
    }

    @SuppressWarnings("Duplicates")
    public void signout(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../FXMLs/theFXML.fxml"));
        Parent root = fxmlLoader.load();
        myStage.setTitle("Cosmos Mail");
        myStage.setScene(new Scene(root, 844.0D, 550.0D));
        myStage.setResizable(false);
        SignInController signInController = fxmlLoader.getController();
        signInController.setup(myStage);
        myStage.show();
    }

    @SuppressWarnings("Duplicates")
    public void compoze(ActionEvent event) throws IOException {
//        app.signin(this.currentAccount.getEmail(),this.currentAccount.getPassword());
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../FXMLs/compose.fxml"));
        Parent root = fxmlLoader.load();
        myStage.setTitle("Cosmos Mail");
        myStage.setScene(new Scene(root, 879.0D, 564.0D));
        myStage.setResizable(false);
        ComposeController composeController = fxmlLoader.getController();
        composeController.setup(myStage, app, currentFolder, currentAccount, currentPage, pageIterator);
        myStage.show();
    }

    @SuppressWarnings("Duplicates")
    public void newfolder(ActionEvent event) {
        this.newfolderBox.setVisible(true);
        this.addfolder.setDisable(true);
    }

    @SuppressWarnings("Duplicates")
    public void openFolder(ActionEvent event) throws IOException {
        String target = ((Button) event.getSource()).getText();
        switch (target) {
            case "all mails":
                currentFolder = app.allMails;
                break;
            case "inbox":
                currentFolder = app.inbox;
                break;
            case "draft":
                currentFolder = app.draft;
                break;
            case "sent":
                currentFolder = app.sent;
                break;
            case "star":
                currentFolder = app.star;
                break;
            case "trash":
                currentFolder = app.trash;
                break;
            default:
                for (int i = 0; i < app.folders.size(); i++) {
                    if (target.equals(((IFolder) app.folders.get(i)).getFolderName())) {
                        currentFolder = ((IFolder) app.folders.get(i));
                        break;
                    }
                }
                break;

        }
        bk();
    }

    @SuppressWarnings("Duplicates")
    public void bk() throws IOException {
//        app.signin(this.currentAccount.getEmail(),this.currentAccount.getPassword());
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../FXMLs/mails.fxml"));
        Parent root = fxmlLoader.load();
        myStage.setTitle("Cosmos Mail");
        myStage.setScene(new Scene(root, 879.0D, 564.0D));
        myStage.setResizable(false);
        MailsController mailsController = fxmlLoader.getController();
        mailsController.setup(myStage, app, currentFolder, currentAccount);
        myStage.show();
        this.currentPage = app.page(currentFolder, pageIterator+1);
    }

    public void setstar(MouseEvent event) {
        this.currentMail.setStar(!this.currentMail.isStar());
        str.getChildren().get(1).setVisible(!str.getChildren().get(1).isVisible());
        if(currentMail.isStar())
            app.setMailStar(currentMail);
        else
            app.setMailNotStar(currentMail);

    }

    public void flagit(ActionEvent event) {
        this.currentMail.setPriority(prior.getValue());
        app.editePriority(currentMail,prior.getValue());
    }

    @SuppressWarnings("Duplicates")
    public void draft(MouseEvent event) {
        IMail currentMail = new IMail();
        currentMail.setSender(this.currentAccount.getName());
        MyLinkedList rec = new MyLinkedList();
        rec.add(this.from2.getText());
        currentMail.setReciever(rec);
        currentMail.setAttach(this.currentMail.getAttach());
        currentMail.setSubject(this.subject2.getText());
        currentMail.setText(this.msg.getText());
        currentMail.setDate(new Date());
        app.saveToDraft(currentMail);
        System.out.println("drafted successfully");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Mail saved to the draft");
        alert.setContentText("Mail saved to the draft!");
        alert.showAndWait();
    }

    public void filterit(ActionEvent event) {
        MyDLinkedList list = new MyDLinkedList();
        list.add(this.currentMail);
        if (fltr.getValue().equals("from2"))
            app.makeFromFilter(list, this.from2.getText());
        else if (fltr.getValue().equals("subject"))
            app.makeSubFilter(list, this.subject2.getText());
    }

    @SuppressWarnings("Duplicates")
    public void checkfoldername(KeyEvent event) {
        if (this.newfoldername.getText().length() > 0) {
            this.addfolder.setDisable(false);
        } else {
            this.addfolder.setDisable(true);
        }
    }

    @SuppressWarnings("Duplicates")
    public void cancelfolder(ActionEvent event) {
        this.newfolderBox.setVisible(false);
        this.newfoldername.clear();
    }

    @SuppressWarnings("Duplicates")
    public void addfo(ActionEvent event) {
        app.CreatNewFolder(this.newfoldername.getText());
        this.newfolderBox.setVisible(false);
        Button temp = new Button();
        temp.getStyleClass().add("folder");
        temp.setFont(new Font("Uni Sans Heavy Italic", 12.0D));
        temp.setStyle("-fx-border-width: 0;\n" +
                "    -fx-background-color:  #336dcc;\n" +
                "    -fx-border-radius: 0;\n" +
                "    -fx-cursor: HAND;\n" +
                "    -fx-pref-width: 261.0D;\n" +
                "    -fx-pref-height: 45.0D;\n" +
                "    -fx-text-fill: WHITE;");
        System.out.println(temp.getStyle());
        temp.setText(this.newfoldername.getText());
        temp.setOnAction((e) -> {
            currentFolder = (IFolder) app.folders.get(app.folders.getSize() - 1);
            //TODO refresh
//            setup(myStage,app,currentFolder,currentAccount);
        });
        this.foldersBox.getChildren().add(temp);
        this.newfoldername.clear();
    }

    public void mvfld(ActionEvent event) {
        String name = this.mvtof.getText();
        MyLinkedList list = new MyLinkedList();
        list.add(currentMail);
        for (int i = 0; i < app.folders.size(); i++) {
            if (name.equals(((IFolder) app.folders.get(i)).getFolderName())) {
                app.moveEmails(list, ((IFolder) app.folders.get(i)));
                moveLabel.setVisible(false);
                break;
            }
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Folder Name");
        alert.setContentText("Invalid Folder Name!");
        alert.showAndWait();
    }

    public void trsh(MouseEvent event) throws IOException {
        MyLinkedList list = new MyLinkedList();
        list.add(currentMail);
        app.deleteEmails(list);
        bk();
    }

    public void mov(MouseEvent event) {
        moveLabel.setVisible(true);
    }

    @SuppressWarnings("Duplicates")
    private void readFilters() {
        for (int i = 0; i < app.subjectF.size(); i++) {
            Button temp = new Button();
            temp.getStyleClass().add("folder");
            temp.setText(((IFolder) app.subjectF.get(i)).getFolderName());
            temp.setFont(new Font("Uni Sans Heavy Italic", 12.0D));
            temp.setStyle("-fx-border-width: 0;\n" +
                    "    -fx-background-color:  #336dcc;\n" +
                    "    -fx-border-radius: 0;\n" +
                    "    -fx-cursor: HAND;\n" +
                    "    -fx-pref-width: 261.0D;\n" +
                    "    -fx-pref-height: 45.0D;\n" +
                    "    -fx-text-fill: WHITE;");
            int finalI = i;
            temp.setOnAction((e) -> {
                currentFolder = (IFolder) app.subjectF.get(finalI);
                //TODO refresh
//                setup(myStage, app, currentFolder, currentAccount);
            });
            this.foldersBox.getChildren().add(temp);
        }

        for (int i = 0; i < app.fromF.size(); i++) {
            Button temp = new Button();
            temp.getStyleClass().add("folder");
            temp.setText(((IFolder) app.fromF.get(i)).getFolderName());
            temp.setFont(new Font("Uni Sans Heavy Italic", 12.0D));
            temp.setStyle("-fx-border-width: 0;\n" +
                    "    -fx-background-color:  #336dcc;\n" +
                    "    -fx-border-radius: 0;\n" +
                    "    -fx-cursor: HAND;\n" +
                    "    -fx-pref-width: 261.0D;\n" +
                    "    -fx-pref-height: 45.0D;\n" +
                    "    -fx-text-fill: WHITE;");
            int finalI = i;
            temp.setOnAction((e) -> {
                currentFolder = (IFolder) app.fromF.get(finalI);
                //TODO refresh
//                setup(myStage, app, currentFolder, currentAccount);
            });
            this.foldersBox.getChildren().add(temp);
        }
    }

    private void readFolders() {
        for (int i = 0; i < app.folders.size(); i++) {
            Button temp = new Button();
            temp.getStyleClass().add("folder");
            temp.setText(((IFolder) app.folders.get(i)).getFolderName());
            temp.setFont(new Font("Uni Sans Heavy Italic", 12.0D));
            temp.setStyle("-fx-border-width: 0;\n" +
                    "    -fx-background-color:  #336dcc;\n" +
                    "    -fx-border-radius: 0;\n" +
                    "    -fx-cursor: HAND;\n" +
                    "    -fx-pref-width: 261.0D;\n" +
                    "    -fx-pref-height: 45.0D;\n" +
                    "    -fx-text-fill: WHITE;");
            int finalI = i;
            temp.setOnAction((e) -> {
                currentFolder = (IFolder) app.folders.get(finalI);
                //TODO refresh
//                setup(myStage, app, currentFolder, currentAccount);
            });
            this.foldersBox.getChildren().add(temp);
        }
    }
}
