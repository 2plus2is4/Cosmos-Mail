package Controllers;

import Classes.Account;
import Classes.MyApp;
import Interfaces.IFolder;
import Interfaces.IMail;
import Interfaces.ISort;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class MailsController {

    @FXML
    VBox foldersBox;
    @FXML
    Label name;
    @FXML
    Button signoutButton;
    @FXML
    ImageView refresh;
    @FXML
    Button compose;
    @FXML
    Button newf;
    @FXML
    Button prev;
    @FXML
    Button nxt;
    @FXML
    HBox mail0;
    @FXML
    HBox mail1;
    @FXML
    HBox mail2;
    @FXML
    HBox mail3;
    @FXML
    HBox mail4;
    @FXML
    HBox mail5;
    @FXML
    HBox mail6;
    @FXML
    HBox mail7;
    @FXML
    HBox mail8;
    @FXML
    HBox mail9;
    @FXML
    VBox newfolderBox;
    @FXML
    Button addfolder;
    @FXML
    TextField newfoldername;


    private Stage myStage;
    private MyApp app;
    private Account currentAccount;
    private IFolder currentFolder;
    private IMail[] currentPage;
    private int pageIterator = 0;
    private int pages = 0;
    private HBox[] mailview = new HBox[10];

    public void setup(Stage myStage, MyApp app, IFolder folder, Account account) {
        setMailView();
        this.myStage = myStage;
        this.app = app;
        this.currentAccount = account;
        this.currentFolder = folder;
        try{
            app.setViewingOptions(currentFolder, null, new ISort(currentFolder.getFolderList(), "date"));
//            this.currentPage = app.page(currentFolder, pageIterator+1);
            //TODO read all folders
            readFolders();
            readFilters();
            //TODO fill pages
            fillmails();
        }catch (Exception exception){
            System.out.println("new Account");
        }


        name.setText(account.getName());

    }

    private void setMailView() {
        mailview[0] = mail0;
        mailview[1] = mail1;
        mailview[2] = mail2;
        mailview[3] = mail3;
        mailview[4] = mail4;
        mailview[5] = mail5;
        mailview[6] = mail6;
        mailview[7] = mail7;
        mailview[8] = mail8;
        mailview[9] = mail9;

        for (HBox h : mailview) {
            ((Label) h.getChildren().get(1)).setText("");
            ((Label) h.getChildren().get(2)).setText("");
            h.getChildren().get(3).setVisible(false);
            h.getChildren().get(4).setVisible(false);
            h.getChildren().get(5).setVisible(false);
            ((Label) h.getChildren().get(6)).setText("");
            h.setVisible(false);
        }
    }

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
                setup(myStage, app, currentFolder, currentAccount);
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
                setup(myStage, app, currentFolder, currentAccount);
            });
            this.foldersBox.getChildren().add(temp);
        }
    }

    private void fillmails() {
//        IMail[] mails = app.listEmails(pageIterator);
        this.currentPage = app.page(currentFolder, pageIterator+1);

        prev.setDisable(pageIterator == 0);
        nxt.setDisable(currentPage.length < 10);
        String date;
        for (int i = 0; i < currentPage.length; i++) {
            if(currentPage[i]!=null) {
                mailview[i].setVisible(true);
                System.out.println(((Label) this.mailview[i].getChildren().get(1)).getText());
                ((Label) this.mailview[i].getChildren().get(1)).setText(currentPage[i].getSender());
                ((Label) this.mailview[i].getChildren().get(2)).setText(currentPage[i].getSubject());
                if (currentPage[i].getAttach() != null && currentPage[i].getAttach().size() != 0) {
                    this.mailview[i].getChildren().get(3).setVisible(true);
                }

                if (currentPage[i].isStar()) {
                    this.mailview[i].getChildren().get(4).setVisible(true);
                }

                if (currentPage[i].getPriority() > 1) {
                    this.mailview[i].getChildren().get(5).setVisible(true);
                }

                date = (new SimpleDateFormat("dd-MM")).format(currentPage[i].getDate());
                ((Label) this.mailview[i].getChildren().get(6)).setText(date);
            }
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
                setup(myStage, app, currentFolder, currentAccount);
            });
            this.foldersBox.getChildren().add(temp);
        }
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

    public void ref(MouseEvent event) {
        setup(myStage, app, currentFolder, currentAccount);
    }

    @SuppressWarnings("Duplicates")
    public void compoze(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../FXMLs/compose.fxml"));
        Parent root = fxmlLoader.load();
        myStage.setTitle("Cosmos Mail");
        myStage.setScene(new Scene(root, 879.0D, 564.0D));
        myStage.setResizable(false);
        ComposeController composeController = fxmlLoader.getController();
        composeController.setup(myStage, app, currentFolder, currentAccount,currentPage,pageIterator);
        myStage.show();
    }

    public void newfolder(ActionEvent event) {
        this.newfolderBox.setVisible(true);
        this.addfolder.setDisable(true);
    }

    @SuppressWarnings("Duplicates")
    public void openFolder(ActionEvent event) {
        String target = ((Button) event.getSource()).getText().toLowerCase();
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
            case "starred":
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
        setup(myStage,app,currentFolder,currentAccount);
    }

    public void open(MouseEvent event) throws IOException {
        String target = ((HBox) event.getSource()).getId();
        System.out.println(target);
        int x = Character.getNumericValue(target.charAt(4));
        IMail currentMail = currentPage[x];

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../FXMLs/openmail.fxml"));
        Parent root = fxmlLoader.load();
        myStage.setTitle("Cosmos Mail");
        myStage.setScene(new Scene(root, 879.0D, 564.0D));
        myStage.setResizable(false);
        OpenMailController openMailController = fxmlLoader.getController();
        openMailController.setup(myStage, app, currentFolder, currentAccount,currentPage,pageIterator,currentMail);
        myStage.show();
    }

    public void hover(MouseEvent event) {
        ((HBox)event.getSource()).setStyle("-fx-background-color: #e0e0e0;");
    }

    public void unhover(MouseEvent event) {
        ((HBox)event.getSource()).setStyle("-fx-background-color: rgba(0, 0, 0, 0)");
    }

    public void prevpage(ActionEvent event) {
        pageIterator--;
        fillmails();
    }

    public void nxtpage(ActionEvent event) {
        pageIterator++;
        fillmails();
    }

    public void srch(MouseEvent event) {
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

}
