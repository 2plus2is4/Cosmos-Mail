package Controllers;

import Classes.Account;
import Classes.Main;
import Classes.MyApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import Interfaces.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class mainController {
    private static MyApp app = new MyApp();
    private static Account currentAccount = new Account();
    private static IMail currentMail = new IMail();
    private static IFolder currentFolder = new IFolder();
    private static boolean signinscene = false;
    private static boolean signupscene = false;
    private static boolean mailsscene = false;
    private static boolean composescene = false;
    private static boolean openmailscene = false;
    private static int pages;
    private static int thispage = 1;
    private static IMail[] currentpage;
    private static boolean mv;
    private static MyDLinkedList l = new MyDLinkedList();
    private static MyDLinkedList ll = new MyDLinkedList();
    private static boolean hi = false;
    private static IMail dragged;
    @FXML
    Button signinButton;
    @FXML
    PasswordField passwordField;
    @FXML
    TextField mailField;
    @FXML
    CheckBox remembermeBox;
    @FXML
    Hyperlink forgotpassword;
    @FXML
    Hyperlink nomailsignup;
    @FXML
    Label errorLabel;
    @FXML
    TextField nameField;
    @FXML
    TextField mailField1;
    @FXML
    PasswordField passField;
    @FXML
    Button signupButton;
    @FXML
    Hyperlink signinLink;
    @FXML
    Label addressError;
    @FXML
    Label passwordError;
    @FXML
    Button compose = new Button();
    @FXML
    Label name;
    @FXML
    Button signoutButton;
    @FXML
    ImageView background;
    @FXML
    HBox mail;
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
    Button newf;
    @FXML
    TextField newfoldername;
    @FXML
    Button cancelf;
    @FXML
    ImageView refresh;
    @FXML
    TreeView<String> tree;
    @FXML
    VBox foldersBox;
    @FXML
    Button allmail;
    @FXML
    Button inbox;
    @FXML
    Button starred;
    @FXML
    Button sent;
    @FXML
    Button trash;
    @FXML
    Button draft;
    @FXML
    Button nxt;
    @FXML
    Button prev;
    @FXML
    Label pnum;
    @FXML
    ImageView flag;
    @FXML
    ImageView search;
    @FXML
    ComboBox<String> f;
    @FXML
    TextField searchfield;
    @FXML
    Button back;
    @FXML
    TextArea msg;
    @FXML
    Label from;
    @FXML
    Label subject;
    @FXML
    Label date;
    @FXML
    ImageView delete;
    @FXML
    StackPane str;
    @FXML
    ComboBox<String> prior;
    @FXML
    HBox attachmentz;
    @FXML
    ImageView drft;
    @FXML
    ComboBox<String> fltr;
    @FXML
    VBox moveLabel;
    @FXML
    Button moooove;
    @FXML
    TextField mvtof;
    @FXML
    ImageView attach;
    @FXML
    TextField to;
    @FXML
    TextField subjekt;
    @FXML
    TextArea mesg;
    @FXML
    ScrollBar textScroll;
    @FXML
    Button send;
    @FXML
    HBox attachments;
    private Desktop desktop = Desktop.getDesktop();
    private List<File> files;

    public mainController() {
    }

    public void signIn(ActionEvent event) throws IOException {
        String s1 = this.mailField.getText();
        String s2 = this.passwordField.getText();
        if (app.signin(s1, s2)) {
            l = app.folders;
            currentAccount.setEmail(app.accountEmail);
            currentAccount.setPassword(this.passwordField.getText());
            currentAccount.setName(app.accountName);
            mailsscene = true;
            currentFolder = app.inbox;
            app.setViewingOptions(currentFolder, (IFilter)null, new ISort(currentFolder.getFolderList(), "date"));
            pages = (int)((double)(currentFolder.getFolderList().getSize() / 10) + 0.5D);
            this.loadScene("mails", event);
        } else {
            this.errorLabel.setVisible(true);
            this.mailField.clear();
            this.passwordField.clear();
        }

    }

    public void signUp(ActionEvent event) throws IOException {
        this.loadScene("sample", event);
    }

    public void signUp1(ActionEvent event) throws IOException {
        IContact c = new IContact(this.nameField.getText(), this.passField.getText(), this.mailField1.getText());
        if (this.passField.getText().length() < 8) {
            this.passwordError.setVisible(true);
            this.nameField.clear();
            this.mailField1.clear();
            this.passField.clear();
        } else if (app.signup(c)) {
            currentAccount = new Account(c.getName(), c.getPassword(), c.getEmail());
            mailsscene = true;
            hi = true;
            this.loadScene("mails", event);
        }

    }

    public void signIn1(ActionEvent event) throws IOException {
        this.loadScene("theFXML", event);
    }

    public void compoze(ActionEvent e) throws IOException {
        this.loadScene("compose", e);
    }

    public void srch(MouseEvent e) {
        System.out.println((String)this.f.getValue());
        new IFilter((String)this.f.getValue(), this.searchfield.getText());
    }

    public void open(MouseEvent e) throws IOException {
        String target = ((HBox)e.getSource()).getId();
        System.out.println(target);
        currentMail = currentpage[this.getmail(target)];
        openmailscene = true;
        this.loadScene2("openmail", e);
    }

    public void signout(ActionEvent e) throws IOException {
        this.loadScene("theFXML", e);
    }

    public void hover(MouseEvent e) {
        ((HBox)e.getSource()).setStyle("-fx-background-color: #e0e0e0;");
    }

    public void unhover(MouseEvent e) {
        ((HBox)e.getSource()).setStyle("-fx-background-color: rgba(0, 0, 0, 0)");
    }

    public void nxtpage(ActionEvent e) throws IOException {
        ++thispage;
        mailsscene = true;
        this.loadScene("mails", e);
    }

    public void prevpage(ActionEvent e) throws IOException {
        ++thispage;
        mailsscene = true;
        this.loadScene("mails", e);
    }

    public void newfolder() {
        this.newfolderBox.setVisible(true);
        this.addfolder.setDisable(true);
    }

    public void cancelfolder() {
        this.newfolderBox.setVisible(false);
        this.newfoldername.clear();
    }

    public void addfo() {
        app.CreatNewFolder(this.newfoldername.getText());
        this.newfolderBox.setVisible(false);
        Button temp = new Button();
        this.swag(temp, app.folders.getSize());
        this.foldersBox.getChildren().add(temp);
        this.newfoldername.clear();
    }

    public void checkfoldername(KeyEvent e) {
        if (this.newfoldername.getText().length() > 0) {
            this.addfolder.setDisable(false);
        } else {
            this.addfolder.setDisable(true);
        }

    }

    public void ref(MouseEvent e) throws IOException {
        mailsscene = true;
        app.signin(currentAccount.getEmail(), currentAccount.getPassword());
        this.loadScene2("mails", e);
    }

    public void openFolder(ActionEvent e) throws IOException {
        System.out.println(((Button)e.getSource()).getId());
        if (!openmailscene || !mv) {
            if (((Button)e.getSource()).getId().equals("inbox")) {
                currentFolder = app.inbox;
                app.signin(currentAccount.getEmail(), currentAccount.getPassword());
                thispage = 1;
                app.setViewingOptions(currentFolder, (IFilter)null, new ISort(currentFolder.getFolderList(), "date"));
                pages = (int)((double)(currentFolder.getFolderList().getSize() / 10) + 0.5D);
                mailsscene = true;
                this.loadScene("mails", e);
            } else if (((Button)e.getSource()).getId().equals("starred")) {
                currentFolder = app.star;
                app.signin(currentAccount.getEmail(), currentAccount.getPassword());
                thispage = 1;
                app.setViewingOptions(currentFolder, (IFilter)null, new ISort(currentFolder.getFolderList(), "date"));
                pages = (int)((double)(currentFolder.getFolderList().getSize() / 10) + 0.5D);
                mailsscene = true;
                this.loadScene("mails", e);
            } else if (((Button)e.getSource()).getId().equals("allmail")) {
                currentFolder = app.allMails;
                app.signin(currentAccount.getEmail(), currentAccount.getPassword());
                thispage = 1;
                app.setViewingOptions(currentFolder, (IFilter)null, new ISort(currentFolder.getFolderList(), "date"));
                pages = (int)((double)(currentFolder.getFolderList().getSize() / 10) + 0.5D);
                mailsscene = true;
                this.loadScene("mails", e);
            } else if (((Button)e.getSource()).getId().equals("sent")) {
                currentFolder = app.sent;
                app.signin(currentAccount.getEmail(), currentAccount.getPassword());
                thispage = 1;
                app.setViewingOptions(currentFolder, (IFilter)null, new ISort(currentFolder.getFolderList(), "date"));
                pages = (int)((double)(currentFolder.getFolderList().getSize() / 10) + 0.5D);
                mailsscene = true;
                this.loadScene("mails", e);
            } else if (((Button)e.getSource()).getId().equals("trash")) {
                currentFolder = app.trash;
                app.signin(currentAccount.getEmail(), currentAccount.getPassword());
                thispage = 1;
                app.setViewingOptions(currentFolder, (IFilter)null, new ISort(currentFolder.getFolderList(), "date"));
                pages = (int)((double)(currentFolder.getFolderList().getSize() / 10) + 0.5D);
                mailsscene = true;
                this.loadScene("mails", e);
            } else if (((Button)e.getSource()).getId().equals("draft")) {
                currentFolder = app.draft;
                app.signin(currentAccount.getEmail(), currentAccount.getPassword());
                thispage = 1;
                app.setViewingOptions(currentFolder, (IFilter)null, new ISort(currentFolder.getFolderList(), "date"));
                pages = (int)((double)(currentFolder.getFolderList().getSize() / 10) + 0.5D);
                mailsscene = true;
                this.loadScene("mails", e);
            }
        }

    }

    public void moveToFolder(ActionEvent e) throws IOException {
        System.out.println(((Button)e.getSource()).getId());
        if (!openmailscene || !mv) {
            if (((Button)e.getSource()).getId().equals("inbox")) {
                currentFolder = app.inbox;
                app.signin(currentAccount.getEmail(), currentAccount.getPassword());
                thispage = 1;
                app.setViewingOptions(currentFolder, (IFilter)null, new ISort(currentFolder.getFolderList(), "date"));
                pages = (int)((double)(currentFolder.getFolderList().getSize() / 10) + 0.5D);
                mailsscene = true;
                this.loadScene("mails", e);
            } else if (((Button)e.getSource()).getId().equals("starred")) {
                currentFolder = app.star;
                app.signin(currentAccount.getEmail(), currentAccount.getPassword());
                thispage = 1;
                app.setViewingOptions(currentFolder, (IFilter)null, new ISort(currentFolder.getFolderList(), "date"));
                pages = (int)((double)(currentFolder.getFolderList().getSize() / 10) + 0.5D);
                mailsscene = true;
                this.loadScene("mails", e);
            } else if (((Button)e.getSource()).getId().equals("allmail")) {
                currentFolder = app.allMails;
                app.signin(currentAccount.getEmail(), currentAccount.getPassword());
                thispage = 1;
                app.setViewingOptions(currentFolder, (IFilter)null, new ISort(currentFolder.getFolderList(), "date"));
                pages = (int)((double)(currentFolder.getFolderList().getSize() / 10) + 0.5D);
                mailsscene = true;
                this.loadScene("mails", e);
            } else if (((Button)e.getSource()).getId().equals("sent")) {
                currentFolder = app.sent;
                app.signin(currentAccount.getEmail(), currentAccount.getPassword());
                thispage = 1;
                app.setViewingOptions(currentFolder, (IFilter)null, new ISort(currentFolder.getFolderList(), "date"));
                pages = (int)((double)(currentFolder.getFolderList().getSize() / 10) + 0.5D);
                mailsscene = true;
                this.loadScene("mails", e);
            } else if (((Button)e.getSource()).getId().equals("trash")) {
                currentFolder = app.trash;
                app.signin(currentAccount.getEmail(), currentAccount.getPassword());
                thispage = 1;
                app.setViewingOptions(currentFolder, (IFilter)null, new ISort(currentFolder.getFolderList(), "date"));
                pages = (int)((double)(currentFolder.getFolderList().getSize() / 10) + 0.5D);
                mailsscene = true;
                this.loadScene("mails", e);
            } else if (((Button)e.getSource()).getId().equals("draft")) {
                currentFolder = app.draft;
                app.signin(currentAccount.getEmail(), currentAccount.getPassword());
                thispage = 1;
                app.setViewingOptions(currentFolder, (IFilter)null, new ISort(currentFolder.getFolderList(), "date"));
                pages = (int)((double)(currentFolder.getFolderList().getSize() / 10) + 0.5D);
                mailsscene = true;
                this.loadScene("mails", e);
            }
        }

    }

    public void bk(ActionEvent e) throws IOException {
        mailsscene = true;
        app.setViewingOptions(currentFolder, (IFilter)null, new ISort(currentFolder.getFolderList(), "date"));
        pages = (int)((double)(currentFolder.getFolderList().getSize() / 10) + 0.5D);
        app.signin(currentAccount.getEmail(), currentAccount.getPassword());
        this.loadScene("mails", e);
    }

    public void mvfld(ActionEvent e) {
        String s1 = this.mvtof.getText();

        for(int i = 0; i < this.foldersBox.getChildren().size(); ++i) {
            String s2 = ((Node)this.foldersBox.getChildren().get(i)).getId();
            if (s1.equals(s2)) {
                MyLinkedList o = new MyLinkedList();
                o.add(currentMail);
                IFolder f = new IFolder();
                f.setFolderName(s2);
                app.moveEmails(o, f);
            }
        }

        this.moveLabel.setVisible(false);
    }

    public void setstar(MouseEvent e) {
        if (((Node)this.str.getChildren().get(0)).getId().equals("gold")) {
            app.setMailStar(currentMail);
            ((Node)this.str.getChildren().get(0)).toFront();
        } else {
            currentMail.setStar(false);
            ((Node)this.str.getChildren().get(0)).toFront();
        }

    }

    public void trsh(MouseEvent e) throws IOException {
        MyDLinkedList l = new MyDLinkedList();
        l.add(currentMail);
        app.deleteEmails(l);
        mailsscene = true;
        app.setViewingOptions(currentFolder, (IFilter)null, new ISort(currentFolder.getFolderList(), "date"));
        pages = (int)((double)(currentFolder.getFolderList().getSize() / 10) + 0.5D);
        app.signin(currentAccount.getEmail(), currentAccount.getPassword());
        this.loadScene2("mails", e);
    }

    public void draft(MouseEvent e) {
        app.saveToDraft(currentMail);
        app.signin(currentAccount.getEmail(), currentAccount.getPassword());
    }

    public void filter(ActionEvent e) {
        String s = (String)this.fltr.getSelectionModel().getSelectedItem();
        if (s.equals("Sender")) {
            app.makeFromFilter(app.inbox.getFolderList(), currentMail.getSender());
        } else if (s.equals("Subject")) {
            app.makeSubFilter(app.inbox.getFolderList(), currentMail.getSubject());
        }

    }

    public void flagit(ActionEvent e) {
        String s = (String)this.prior.getSelectionModel().getSelectedItem();
        if (s.equals("High")) {
            app.editePriority(currentMail, 5);
        } else if (s.equals("Normal")) {
            app.editePriority(currentMail, 4);
        } else if (s.equals("Low")) {
            app.editePriority(currentMail, 3);
        } else if (s.equals("Lowest")) {
            app.editePriority(currentMail, 2);
        } else if (s.equals("No Flag")) {
            app.editePriority(currentMail, 1);
        }

    }

    public void mov(MouseEvent e) {
        if (!mv) {
            mv = true;
        } else {
            mv = false;
        }

        this.moveLabel.setVisible(mv);
    }

    public void openAttachment(MouseEvent e) {
        FileChooser fc = new FileChooser();
        this.files = fc.showOpenMultipleDialog(Main.getPrimaryStage());
        if (this.files != null) {
            for(int i = 0; i < this.files.size(); ++i) {
                Label t1 = new Label();
                t1.prefHeight(58.0D);
                t1.prefWidth(147.0D);
                t1.setText(" " + ((File)this.files.get(i)).getName() + " ");
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
                t2.setOnAction((event) -> {
                    String s1 = ((Button)event.getSource()).getId();

                    for(int ii = 0; ii < this.attachments.getChildren().size(); ++ii) {
                        String s2 = ((Node)this.attachments.getChildren().get(ii)).getId();
                        if (s1.equals(s2)) {
                            this.checkattach(((Label)this.attachments.getChildren().get(ii)).getText());
                            this.attachments.getChildren().remove(ii);
                            this.attachments.getChildren().remove(ii);
                            break;
                        }
                    }

                });
                this.attachments.getChildren().add(t2);
            }

            System.out.println();
        }

    }

    private void openFile(File file) {
        try {
            this.desktop.open(file);
        } catch (IOException var3) {
            ;
        }

    }

    public void sent(ActionEvent e) throws IOException {
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
            for(int i = 0; i < this.files.size(); ++i) {
                l.add(this.files.get(i));
            }
        }

        mail.setAttach(l);
        app.compose(mail);
        System.out.println(this.mesg.getText());
        mailsscene = true;
        currentFolder = app.sent;
        app.setViewingOptions(currentFolder, (IFilter)null, new ISort(currentFolder.getFolderList(), "date"));
        pages = (int)((double)(currentFolder.getFolderList().getSize() / 10) + 0.5D);
        this.loadScene("mails", e);
    }

    public void checkRe(MouseEvent e) {
        if (openmailController.re) {
            openmailController.re = false;
        }

    }

    public void bak(ActionEvent e) throws IOException {
        mailsscene = true;
        this.loadScene("mails", e);
    }

    public void dltattch(ActionEvent e) {
        ((Node)this.attachments.getChildren().get(Integer.parseInt(((Button)e.getSource()).getId()))).setVisible(false);
        ((Button)e.getSource()).setVisible(false);
    }

    public void initialize() {
        int i;
        Button temp;
        if (mailsscene) {
            for(i = 0; i < app.folders.size(); ++i) {
                temp = new Button();
                temp.setFont(new Font("Uni Sans Heavy Italic", 12.0D));
                temp.setCursor(this.inbox.getCursor());
                temp.setPrefHeight(45.0D);
                temp.setPrefWidth(261.0D);
                temp.setStyle("-fx-border-width: 0; -fx-background-color: #336dcc; -fx-border-radius: 0;");
                temp.setText(((IFolder)app.folders.get(i)).getFolderName());
                temp.setId(((IFolder)app.folders.get(i)).getFolderName());
                temp.setTextFill(Paint.valueOf("WHITE"));
                temp.setOnAction((event) -> {
                    currentFolder = (IFolder)app.folders.get(i);
                    if (!openmailscene || !mv) {
                        mailsscene = true;
                        app.signin(currentAccount.getEmail(), currentAccount.getPassword());

                        try {
                            this.loadScene("mails", event);
                        } catch (IOException var4) {
                            var4.printStackTrace();
                        }
                    }

                });
                temp.setOnDragDropped((event) -> {
                });
                this.foldersBox.getChildren().add(temp);
                this.newfoldername.clear();
            }

            for(i = 0; i < app.fromF.size(); ++i) {
                temp = new Button();
                temp.setFont(new Font("Uni Sans Heavy Italic", 12.0D));
                temp.setCursor(this.inbox.getCursor());
                temp.setPrefHeight(45.0D);
                temp.setPrefWidth(261.0D);
                temp.setStyle("-fx-border-width: 0; -fx-background-color: #336dcc; -fx-border-radius: 0;");
                temp.setText("Filter From: " + ((IFolder)app.fromF.get(i)).getFolderName());
                temp.setId("Filter From: " + ((IFolder)app.fromF.get(i)).getFolderName());
                temp.setTextFill(Paint.valueOf("WHITE"));
                temp.setOnAction((event) -> {
                    currentFolder = (IFolder)app.folders.get(i);
                    if (!openmailscene || !mv) {
                        mailsscene = true;
                        app.signin(currentAccount.getEmail(), currentAccount.getPassword());

                        try {
                            this.loadScene("mails", event);
                        } catch (IOException var4) {
                            var4.printStackTrace();
                        }
                    }

                });
                temp.setOnDragDropped((event) -> {
                });
                this.foldersBox.getChildren().add(temp);
                this.newfoldername.clear();
            }

            for(i = 0; i < app.subjectF.size(); ++i) {
                temp = new Button();
                temp.setFont(new Font("Uni Sans Heavy Italic", 12.0D));
                temp.setCursor(this.inbox.getCursor());
                temp.setPrefHeight(45.0D);
                temp.setPrefWidth(261.0D);
                temp.setStyle("-fx-border-width: 0; -fx-background-color: #336dcc; -fx-border-radius: 0;");
                temp.setText("Filter From: " + ((IFolder)app.subjectF.get(i)).getFolderName());
                temp.setId("Filter From: " + ((IFolder)app.subjectF.get(i)).getFolderName());
                temp.setTextFill(Paint.valueOf("WHITE"));
                temp.setOnAction((event) -> {
                    currentFolder = (IFolder)app.folders.get(i);
                    if (!openmailscene || !mv) {
                        mailsscene = true;
                        app.signin(currentAccount.getEmail(), currentAccount.getPassword());

                        try {
                            this.loadScene("mails", event);
                        } catch (IOException var4) {
                            var4.printStackTrace();
                        }
                    }

                });
                temp.setOnDragDropped((event) -> {
                });
                this.foldersBox.getChildren().add(temp);
                this.newfoldername.clear();
            }

            if (!hi) {
                currentpage = app.page(currentFolder, thispage);
                this.matching();
                if (thispage != 1) {
                    this.prev.setDisable(false);
                }

                if (thispage != pages) {
                    this.nxt.setDisable(false);
                }

                if (this.f != null) {
                    this.f.getItems().addAll(new String[]{"Sender", "Subject"});
                }

                currentpage = app.page(currentFolder, thispage);
            }
        }

        if (openmailscene) {
            for(i = 0; i < app.folders.size(); ++i) {
                temp = new Button();
                temp.setFont(new Font("Uni Sans Heavy Italic", 12.0D));
                temp.setCursor(this.inbox.getCursor());
                temp.setPrefHeight(45.0D);
                temp.setPrefWidth(261.0D);
                temp.setStyle("-fx-border-width: 0; -fx-background-color: #336dcc; -fx-border-radius: 0;");
                temp.setText(((IFolder)app.folders.get(i)).getFolderName());
                temp.setId(((IFolder)app.folders.get(i)).getFolderName());
                temp.setTextFill(Paint.valueOf("WHITE"));
                temp.setOnAction((event) -> {
                    currentFolder = (IFolder)app.folders.get(i);
                    if (!openmailscene || !mv) {
                        mailsscene = true;
                        app.signin(currentAccount.getEmail(), currentAccount.getPassword());

                        try {
                            this.loadScene("mails", event);
                        } catch (IOException var4) {
                            var4.printStackTrace();
                        }
                    }

                });
                temp.setOnDragDropped((event) -> {
                });
                this.foldersBox.getChildren().add(temp);
            }

            for(i = 0; i < app.fromF.size(); ++i) {
                temp = new Button();
                temp.setFont(new Font("Uni Sans Heavy Italic", 12.0D));
                temp.setCursor(this.inbox.getCursor());
                temp.setPrefHeight(45.0D);
                temp.setPrefWidth(261.0D);
                temp.setStyle("-fx-border-width: 0; -fx-background-color: #336dcc; -fx-border-radius: 0;");
                temp.setText("Filter From: " + ((IFolder)app.fromF.get(i)).getFolderName());
                temp.setId("Filter From: " + ((IFolder)app.fromF.get(i)).getFolderName());
                temp.setTextFill(Paint.valueOf("WHITE"));
                temp.setOnAction((event) -> {
                    currentFolder = (IFolder)app.folders.get(i);
                    if (!openmailscene || !mv) {
                        mailsscene = true;
                        app.signin(currentAccount.getEmail(), currentAccount.getPassword());

                        try {
                            this.loadScene("mails", event);
                        } catch (IOException var4) {
                            var4.printStackTrace();
                        }
                    }

                });
                temp.setOnDragDropped((event) -> {
                });
                this.foldersBox.getChildren().add(temp);
                this.newfoldername.clear();
            }

            for(i = 0; i < app.subjectF.size(); ++i) {
                temp = new Button();
                temp.setFont(new Font("Uni Sans Heavy Italic", 12.0D));
                temp.setCursor(this.inbox.getCursor());
                temp.setPrefHeight(45.0D);
                temp.setPrefWidth(261.0D);
                temp.setStyle("-fx-border-width: 0; -fx-background-color: #336dcc; -fx-border-radius: 0;");
                temp.setText("Filter From: " + ((IFolder)app.subjectF.get(i)).getFolderName());
                temp.setId("Filter From: " + ((IFolder)app.subjectF.get(i)).getFolderName());
                temp.setTextFill(Paint.valueOf("WHITE"));
                temp.setOnAction((event) -> {
                    currentFolder = (IFolder)app.folders.get(i);
                    if (!openmailscene || !mv) {
                        mailsscene = true;
                        app.signin(currentAccount.getEmail(), currentAccount.getPassword());

                        try {
                            this.loadScene("mails", event);
                        } catch (IOException var4) {
                            var4.printStackTrace();
                        }
                    }

                });
                temp.setOnDragDropped((event) -> {
                });
                this.foldersBox.getChildren().add(temp);
            }

            this.from.setText(currentMail.getSender());
            this.subject.setText(currentMail.getSubject());
            this.msg.setText(currentMail.getText());
            MyLinkedList l = currentMail.getAttach();
            if (l != null) {
                for(int j = 0; (long)j < l.getSize(); ++j) {
                    Label label = new Label();
                    label.prefHeight(58.0D);
                    label.prefWidth(147.0D);
                    File f = (File)((File)l.get(j));
                    label.setText(" " + f.getName() + " ");
                    label.setOnMouseClicked((event) -> {
                        this.openFile(f);
                    });
                    this.attachmentz.getChildren().add(label);
                }
            }

            if (currentMail.isStar()) {
                ((Node)this.str.getChildren().get(0)).toFront();
            }

            this.prior.getItems().addAll(new String[]{"High", "Normal", "Low", "Lowest", "No Flag"});
            this.fltr.getItems().addAll(new String[]{"Sender", "Subject"});
        }

        if (mailsscene || composescene || openmailscene) {
            openmailscene = false;
            composescene = false;
            mailsscene = false;
            this.name.setText(currentAccount.getName());
        }

    }

    public void swag(Button temp, int index) {
        temp.setFont(new Font("Uni Sans Heavy Italic", 12.0D));
        temp.setCursor(this.inbox.getCursor());
        temp.setPrefHeight(45.0D);
        temp.setPrefWidth(261.0D);
        temp.setStyle("-fx-border-width: 0; -fx-background-color: #336dcc; -fx-border-radius: 0;");
        temp.setText(this.newfoldername.getText());
        temp.setId(this.newfoldername.getText());
        temp.setTextFill(Paint.valueOf("WHITE"));
        temp.setOnAction((event) -> {
            currentFolder = (IFolder)app.folders.get(index);
            if (!openmailscene || !mv) {
                mailsscene = true;
                app.signin(currentAccount.getEmail(), currentAccount.getPassword());

                try {
                    this.loadScene("mails", event);
                } catch (IOException var4) {
                    var4.printStackTrace();
                }
            }

        });
    }

    public void getFolders() {
        MyDLinkedList l = app.folders;

        for(int i = 0; i < l.size(); ++i) {
            Button temp = new Button();
            temp.setFont(new Font("Uni Sans Heavy Italic", 12.0D));
            temp.setCursor(this.inbox.getCursor());
            temp.setPrefHeight(45.0D);
            temp.setPrefWidth(261.0D);
            temp.setStyle("-fx-border-width: 0; -fx-background-color: #336dcc; -fx-border-radius: 0;");
            temp.setText((String)l.get(i));
            temp.setId((String)l.get(i));
            temp.setTextFill(Paint.valueOf("WHITE"));
        }

    }

    public void loadScene(String fxml, ActionEvent e) throws IOException {
        Parent mail = (Parent)FXMLLoader.load(this.getClass().getResource("../FXMLs/" + fxml + ".fxml"));
        Stage primaryStage = (Stage)((Node)e.getSource()).getScene().getWindow();
        primaryStage.setTitle("Cosmos Mail");
        primaryStage.setScene(new Scene(mail, 879.0D, 564.0D));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void loadScene2(String fxml, MouseEvent e) throws IOException {
        Parent mail = (Parent)FXMLLoader.load(this.getClass().getResource("../FXMLs/" + fxml + ".fxml"));
        Stage primaryStage = (Stage)((Node)e.getSource()).getScene().getWindow();
        primaryStage.setTitle("Cosmos Mail");
        primaryStage.setScene(new Scene(mail, 879.0D, 564.0D));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void matching() {
        int i = 0;
        String date;
        if (i < currentpage.length) {
            this.mail.setVisible(true);
            System.out.println(((Label)this.mail.getChildren().get(1)).getText());
            ((Label)this.mail.getChildren().get(1)).setText(currentpage[i].getSender());
            ((Label)this.mail.getChildren().get(2)).setText(currentpage[i].getSubject());
            if (currentpage[i].getAttach() != null && currentpage[i].getAttach().size() != 0) {
                ((ImageView)this.mail.getChildren().get(3)).setVisible(true);
            }

            if (currentpage[i].isStar()) {
                ((ImageView)this.mail.getChildren().get(4)).setVisible(true);
            }

            if (currentpage[i].getPriority() > 1) {
                ((Node)this.mail.getChildren().get(5)).setVisible(true);
            }

            date = (new SimpleDateFormat("dd-MM")).format(currentpage[i].getDate());
            ((Label)this.mail.getChildren().get(6)).setText(date);
            ++i;
        }

        if (i < currentpage.length) {
            this.mail1.setVisible(true);
            System.out.println(((Label)this.mail1.getChildren().get(1)).getText());
            ((Label)this.mail1.getChildren().get(1)).setText(currentpage[i].getSender());
            ((Label)this.mail1.getChildren().get(2)).setText(currentpage[i].getSubject());
            if (currentpage[i].getAttach() != null && currentpage[i].getAttach().size() != 0) {
                ((ImageView)this.mail1.getChildren().get(3)).setVisible(true);
            }

            if (currentpage[i].isStar()) {
                ((ImageView)this.mail1.getChildren().get(4)).setVisible(true);
            }

            if (currentpage[i].getPriority() > 1) {
                ((Node)this.mail1.getChildren().get(5)).setVisible(true);
            }

            date = (new SimpleDateFormat("dd-MM")).format(currentpage[i].getDate());
            ((Label)this.mail1.getChildren().get(6)).setText(date);
            ++i;
        }

        if (i < currentpage.length) {
            this.mail2.setVisible(true);
            System.out.println(((Label)this.mail2.getChildren().get(1)).getText());
            ((Label)this.mail2.getChildren().get(1)).setText(currentpage[i].getSender());
            ((Label)this.mail2.getChildren().get(2)).setText(currentpage[i].getSubject());
            if (currentpage[i].getAttach() != null && currentpage[i].getAttach().size() != 0) {
                ((ImageView)this.mail2.getChildren().get(3)).setVisible(true);
            }

            if (currentpage[i].isStar()) {
                ((ImageView)this.mail2.getChildren().get(4)).setVisible(true);
            }

            if (currentpage[i].getPriority() > 1) {
                ((Node)this.mail2.getChildren().get(5)).setVisible(true);
            }

            date = (new SimpleDateFormat("dd-MM")).format(currentpage[i].getDate());
            ((Label)this.mail2.getChildren().get(6)).setText(date);
            ++i;
        }

        if (i < currentpage.length) {
            this.mail3.setVisible(true);
            System.out.println(((Label)this.mail3.getChildren().get(1)).getText());
            ((Label)this.mail3.getChildren().get(1)).setText(currentpage[i].getSender());
            ((Label)this.mail3.getChildren().get(2)).setText(currentpage[i].getSubject());
            if (currentpage[i].getAttach() != null && currentpage[i].getAttach().size() != 0) {
                ((ImageView)this.mail3.getChildren().get(3)).setVisible(true);
            }

            if (currentpage[i].isStar()) {
                ((ImageView)this.mail3.getChildren().get(4)).setVisible(true);
            }

            if (currentpage[i].getPriority() > 1) {
                ((Node)this.mail3.getChildren().get(5)).setVisible(true);
            }

            date = (new SimpleDateFormat("dd-MM")).format(currentpage[i].getDate());
            ((Label)this.mail3.getChildren().get(6)).setText(date);
            ++i;
        }

        if (i < currentpage.length) {
            this.mail4.setVisible(true);
            System.out.println(((Label)this.mail4.getChildren().get(1)).getText());
            ((Label)this.mail4.getChildren().get(1)).setText(currentpage[i].getSender());
            ((Label)this.mail4.getChildren().get(2)).setText(currentpage[i].getSubject());
            if (currentpage[i].getAttach() != null && currentpage[i].getAttach().size() != 0) {
                ((ImageView)this.mail4.getChildren().get(3)).setVisible(true);
            }

            if (currentpage[i].isStar()) {
                ((ImageView)this.mail4.getChildren().get(4)).setVisible(true);
            }

            if (currentpage[i].getPriority() > 1) {
                ((Node)this.mail4.getChildren().get(5)).setVisible(true);
            }

            date = (new SimpleDateFormat("dd-MM")).format(currentpage[i].getDate());
            ((Label)this.mail4.getChildren().get(6)).setText(date);
            ++i;
        }

        if (i < currentpage.length) {
            this.mail5.setVisible(true);
            System.out.println(((Label)this.mail5.getChildren().get(1)).getText());
            ((Label)this.mail5.getChildren().get(1)).setText(currentpage[i].getSender());
            ((Label)this.mail5.getChildren().get(2)).setText(currentpage[i].getSubject());
            if (currentpage[i].getAttach() != null && currentpage[i].getAttach().size() != 0) {
                ((ImageView)this.mail5.getChildren().get(3)).setVisible(true);
            }

            if (currentpage[i].isStar()) {
                ((ImageView)this.mail5.getChildren().get(4)).setVisible(true);
            }

            if (currentpage[i].getPriority() > 1) {
                ((Node)this.mail5.getChildren().get(5)).setVisible(true);
            }

            date = (new SimpleDateFormat("dd-MM")).format(currentpage[i].getDate());
            ((Label)this.mail5.getChildren().get(6)).setText(date);
            ++i;
        }

        if (i < currentpage.length) {
            this.mail6.setVisible(true);
            System.out.println(((Label)this.mail6.getChildren().get(1)).getText());
            ((Label)this.mail6.getChildren().get(1)).setText(currentpage[i].getSender());
            ((Label)this.mail6.getChildren().get(2)).setText(currentpage[i].getSubject());
            if (currentpage[i].getAttach() != null && currentpage[i].getAttach().size() != 0) {
                ((ImageView)this.mail6.getChildren().get(3)).setVisible(true);
            }

            if (currentpage[i].isStar()) {
                ((ImageView)this.mail6.getChildren().get(4)).setVisible(true);
            }

            if (currentpage[i].getPriority() > 1) {
                ((Node)this.mail6.getChildren().get(5)).setVisible(true);
            }

            date = (new SimpleDateFormat("dd-MM")).format(currentpage[i].getDate());
            ((Label)this.mail6.getChildren().get(6)).setText(date);
            ++i;
        }

        if (i < currentpage.length) {
            this.mail7.setVisible(true);
            System.out.println(((Label)this.mail7.getChildren().get(1)).getText());
            ((Label)this.mail7.getChildren().get(1)).setText(currentpage[i].getSender());
            ((Label)this.mail7.getChildren().get(2)).setText(currentpage[i].getSubject());
            if (currentpage[i].getAttach() != null && currentpage[i].getAttach().size() != 0) {
                ((ImageView)this.mail7.getChildren().get(3)).setVisible(true);
            }

            if (currentpage[i].isStar()) {
                ((ImageView)this.mail7.getChildren().get(4)).setVisible(true);
            }

            if (currentpage[i].getPriority() > 1) {
                ((Node)this.mail7.getChildren().get(5)).setVisible(true);
            }

            date = (new SimpleDateFormat("dd-MM")).format(currentpage[i].getDate());
            ((Label)this.mail7.getChildren().get(6)).setText(date);
            ++i;
        }

        if (i < currentpage.length) {
            this.mail8.setVisible(true);
            System.out.println(((Label)this.mail8.getChildren().get(1)).getText());
            ((Label)this.mail8.getChildren().get(1)).setText(currentpage[i].getSender());
            ((Label)this.mail8.getChildren().get(2)).setText(currentpage[i].getSubject());
            if (currentpage[i].getAttach() != null && currentpage[i].getAttach().size() != 0) {
                ((ImageView)this.mail8.getChildren().get(3)).setVisible(true);
            }

            if (currentpage[i].isStar()) {
                ((ImageView)this.mail8.getChildren().get(4)).setVisible(true);
            }

            if (currentpage[i].getPriority() > 1) {
                ((Node)this.mail8.getChildren().get(5)).setVisible(true);
            }

            date = (new SimpleDateFormat("dd-MM")).format(currentpage[i].getDate());
            ((Label)this.mail8.getChildren().get(6)).setText(date);
            ++i;
        }

        if (i < currentpage.length) {
            this.mail9.setVisible(true);
            System.out.println(((Label)this.mail9.getChildren().get(1)).getText());
            ((Label)this.mail9.getChildren().get(1)).setText(currentpage[i].getSender());
            ((Label)this.mail9.getChildren().get(2)).setText(currentpage[i].getSubject());
            if (currentpage[i].getAttach() != null && currentpage[i].getAttach().size() != 0) {
                ((ImageView)this.mail9.getChildren().get(3)).setVisible(true);
            }

            if (currentpage[i].isStar()) {
                ((ImageView)this.mail9.getChildren().get(4)).setVisible(true);
            }

            if (currentpage[i].getPriority() > 1) {
                ((Node)this.mail9.getChildren().get(5)).setVisible(true);
            }

            date = (new SimpleDateFormat("dd-MM")).format(currentpage[i].getDate());
            ((Label)this.mail9.getChildren().get(6)).setText(date);
            ++i;
        }

    }

    public int getmail(String id) {
        System.out.println(Integer.parseInt(id.substring(id.length() - 1)));
        int x = Integer.parseInt(id.substring(id.length() - 1)) - 1;
        return x;
    }

    public void checkattach(String target) {
        for(int i = 0; i < this.files.size(); ++i) {
            if (((File)this.files.get(i)).getName().equals(target)) {
                this.files.remove(i);
                break;
            }
        }

    }
}

