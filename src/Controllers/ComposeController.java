package Controllers;

import Classes.Account;
import Classes.MyApp;
import Interfaces.*;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class ComposeController {

    @FXML
    HBox attachments;
    @FXML
    Button compose;
    @FXML
    Button newf;
    @FXML
    VBox foldersBox;
    @FXML
    Button back;
    @FXML
    ImageView drft;
    @FXML
    Button signoutButton;
    @FXML
    TextField to;
    @FXML
    TextField subjekt;
    @FXML
    TextArea mesg;
    @FXML
    ImageView attach;
    @FXML
    VBox newfolderBox;
    @FXML
    Button addfolder;
    @FXML
    TextField newfoldername;
    @FXML
    Label name;

    private Stage myStage;
    private MyApp app;
    private Account currentAccount;
    private IFolder currentFolder;
    private IMail[] currentPage;
    private int pageIterator = 0;
    private int pages = 0;
    private HBox[] mailview = new HBox[10];
    private List<File> files;


    public void setup(Stage myStage, MyApp app, IFolder folder, Account account, IMail[] currentPage, int pageIterator) {
        this.myStage = myStage;
        this.app = app;
        this.currentAccount = account;
        this.currentFolder = folder;
        this.currentPage = currentPage;
        this.pageIterator = pageIterator;
        name.setText(account.getName());

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
    public void newfolder(ActionEvent event) {
        this.newfolderBox.setVisible(true);
        this.addfolder.setDisable(true);
    }

    @SuppressWarnings("Duplicates")
    public void openFolder(ActionEvent event) {

        //TODO add to drafts
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Mail saved to the draft");
        alert.setContentText("Mail saved to the draft!");
        alert.showAndWait();
        draft();

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
    }

    public void openAttachment(MouseEvent event) {
        FileChooser fc = new FileChooser();
        this.files = fc.showOpenMultipleDialog(myStage);
        if (this.files != null) {
            for (File file : this.files) {
                Label t1 = new Label();
                t1.prefHeight(58.0D);
                t1.prefWidth(147.0D);
                t1.setText(" " + file.getName() + " ");
                String index = Integer.toString(this.attachments.getChildren().size());
                t1.setId(index);
                this.attachments.getChildren().add(t1);
                Button t2 = new Button();
                t2.prefHeight(33.0D);
                t2.prefWidth(18.0D);
                t2.setStyle("-fx-background-color: #da2f38;");
                t2.setText("X");
                t2.setTextFill(Paint.valueOf("WHITE"));
                t2.setId(index);
                this.attachments.getChildren().add(t2);
                t2.setOnAction((e) -> {
                    String s1 = ((Button) e.getSource()).getId();
                    for (int ii = 0; ii < this.attachments.getChildren().size(); ii++) {
                        String s2 = this.attachments.getChildren().get(ii).getId();
                        if (s1.equals(s2)) {
                            this.checkattach(((Label) this.attachments.getChildren().get(ii)).getText());
                            this.attachments.getChildren().remove(ii);
                            this.attachments.getChildren().remove(ii);
                            break;
                        }
                    }

                });
            }

            System.out.println();
        }
    }

    private void checkattach(String target) {
        for (int i = 0; i < this.files.size(); ++i) {
            if (this.files.get(i).getName().equals(target)) {
                this.files.remove(i);
                break;
            }
        }
    }

    public void sent(ActionEvent event) throws IOException {
        IMail mail = new IMail();
        mail.setSender(currentAccount.getEmail());
        mail.setAttach(new MyLinkedList());
        mail.setStar(false);
        MyLinkedList r = new MyLinkedList();
        r.add(this.to.getText());
        mail.setReciever(r);
        mail.setDate(new Date());
        mail.setSubject(this.subjekt.getText());
        String a = this.mesg.getText();
        mail.setText(a);
        MyLinkedList l = new MyLinkedList();
        if (this.files != null) {
            for (File file : this.files) {
                l.add(file);
            }
        }

        mail.setAttach(l);
        app.compose(mail);
        System.out.println(this.mesg.getText());


        currentFolder = app.sent;
//        app.setViewingOptions(currentFolder, null, new ISort(currentFolder.getFolderList(), "date2"));
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../FXMLs/mails.fxml"));
        Parent root = fxmlLoader.load();
        myStage.setTitle("Cosmos Mail");
        myStage.setScene(new Scene(root, 879.0D, 564.0D));
        myStage.setResizable(false);
        MailsController mailsController = fxmlLoader.getController();
        mailsController.setup(myStage, app, currentFolder, currentAccount);
        myStage.show();

    }

    public void bak(ActionEvent event) throws IOException {

        //TODO add to drafts
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Mail saved to the draft");
        alert.setContentText("Mail saved to the draft!");
        alert.showAndWait();
        draft();


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../FXMLs/mails.fxml"));
        Parent root = fxmlLoader.load();
        myStage.setTitle("Cosmos Mail");
        myStage.setScene(new Scene(root, 879.0D, 564.0D));
        myStage.setResizable(false);
        MailsController mailsController = fxmlLoader.getController();
        mailsController.setup(myStage, app, currentFolder, currentAccount);
        myStage.show();
    }

    public void draft() {
        System.out.println("drafted successfully");
        IMail currentMail = new IMail();
        currentMail.setSender(this.currentAccount.getName());
        MyLinkedList rec = new MyLinkedList();
        rec.add(this.to.getText());
        currentMail.setReciever(rec);
        currentMail.setAttach(getFiles());
        currentMail.setSubject(this.subjekt.getText());
        currentMail.setText(this.mesg.getText());
        currentMail.setDate(new Date());
        app.saveToDraft(currentMail);
    }

    public void checkfoldername(KeyEvent event) {
        if (this.newfoldername.getText().length() > 0) {
            this.addfolder.setDisable(false);
        } else {
            this.addfolder.setDisable(true);
        }
    }

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

    private MyLinkedList getFiles() {
        MyLinkedList rec = new MyLinkedList();
        for (File file : files) {
            rec.add(file);
        }
        return rec;
    }

}
