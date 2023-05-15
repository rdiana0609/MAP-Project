package com.example.demo;

import com.example.demo.domain.FriendRequest;
import com.example.demo.domain.Prietenie;
import com.example.demo.domain.Utilizator;
import com.example.demo.domain.validators.FriendshipRequestValidator;
import com.example.demo.domain.validators.UtilizatorValidator;
import com.example.demo.domain.validators.ValidationException;
import com.example.demo.domain.validators.ValidatorPrietnie;
import com.example.demo.repository.Repository;
import com.example.demo.repository.database.PrietenieDB;
import com.example.demo.repository.database.UtilizatorDB;
import com.example.demo.service.Service1;
import com.example.demo.utils.events.observer.Observer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Logged_inController  {
    public TableView<Utilizator> tableFriends;
    public TableColumn<Utilizator,String> column;
    public ObservableList<Utilizator> modelFriends= FXCollections.observableArrayList();
    public ObservableList<Utilizator> modelFriends2= FXCollections.observableArrayList();
    public ObservableList<Utilizator> modelFriends3= FXCollections.observableArrayList();
    public Label zgarda;
    public TextField searchbar;
    public Button searchbutton;
    public Label numeusercautat;
    public Button sendinviteButton;
    public Button removefriendButton;
    public Button showrequestsButton;
    public TableView tabelfriendshipRequests;
    public TableColumn coltabelrequests;
    public Button acceptButton;
    public Button messageButton;
    public Button sendmsgbutton;
    public Button logoutButton;
    public Button deletefriendButton;
    public Button deleteUser;
    public Button openconvbutton;
    public Button openconvb;
    public Button butonnou;

    private Service1 service;

    private Utilizator owner;
    private Utilizator usercautat;


    @FXML
    void initialize(){

        tableFriends.setItems(modelFriends);
        tabelfriendshipRequests.setItems(modelFriends2);
        System.out.println("modelfr");
        System.out.println(modelFriends2);
        column.setCellValueFactory(new PropertyValueFactory<>("username"));
        coltabelrequests.setCellValueFactory(new PropertyValueFactory<>("username"));
        tableFriends.setVisible(true);
        tabelfriendshipRequests.setVisible(false);
        coltabelrequests.setVisible(false);
        getUserFromTable();
    }

    void populateFriends(){

        modelFriends.setAll(this.service.getFriends(owner));

    }

   /* public void setService(Service1 serv) {
        System.out.println("iybbu");

        populateFriends();
    }*/

    public void setUser(Utilizator stage) {this.owner=stage;zgarda.setText(owner.getUsername());
        System.out.println(owner);
        System.out.println(service.getFriends(owner));
        modelFriends.setAll(this.service.getFriends(owner));
        modelFriends2.setAll(this.service.getInvitesFrom(owner.getId()));
        modelFriends3.setAll((this.service.getRequests(owner.getId())));
        System.out.println("aici:>>>>>>>>>>>>>>>>");
        System.out.println(modelFriends2);

    }

    public void setService(Service1 service) {
        this.service = service;
        System.out.println(service.getAllFriendships());
        //populateFriends();
        System.out.println(owner);
        System.out.println(service.getFriends(owner));

        System.out.println(modelFriends);
        System.out.println(modelFriends);


    }

    public void showRequests(ActionEvent actionEvent) {
        tabelfriendshipRequests.setItems(modelFriends2);
        System.out.println("modelfr");
        System.out.println(modelFriends2);
        coltabelrequests.setCellValueFactory(new PropertyValueFactory<>("username"));

        //this.tableFriends.setVisible(false);
        this.tabelfriendshipRequests.setVisible(true);
      //  column.setVisible(false);
        coltabelrequests.setVisible(true);
        this.getRequestFromTable();
    }

    public void showFriends(ActionEvent actionEvent) {

        column.setVisible(true);
        coltabelrequests.setVisible(false);
        this.tabelfriendshipRequests.setVisible(false);
        this.tableFriends.setVisible(true);
        this.getUserFromTable();
    }


    public void search(ActionEvent actionEvent) {

         this.usercautat=service.getUserByUsername(this.searchbar.getText());
         if(this.usercautat!=null) {
             this.numeusercautat.setText(this.usercautat.getUsername().toString());
             System.out.println(numeusercautat.getText());
             this.numeusercautat.setVisible(true);
             this.sendinviteButton.setVisible(true);
             this.removefriendButton.setVisible(true);
             //this.acceptButton.setVisible(true);
         }
        else {
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);

            VBox vbox = new VBox(new Text("Nu exista userul cautat"));
            vbox.setAlignment(Pos.CENTER);
            vbox.setPadding(new Insets(30));

            dialogStage.setScene(new Scene(vbox));
            dialogStage.show();



        }
    }

    public void removeFriend(ActionEvent actionEvent) {
        this.service.stergePrietenie(usercautat.getEmail(),owner.getEmail());
        this.modelFriends.setAll(service.getFriends(owner));
        this.modelFriends2.setAll(service.getInvitesFrom(owner.getId()));
       // this.modelFriends3.setAll(service.getInvites(owner.getId()));
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);

        VBox vbox = new VBox(new Text("your are friends now:3"));
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(30));

        dialogStage.setScene(new Scene(vbox));
        dialogStage.show();

    }

    public void sendInvite(ActionEvent actionEvent) {
        //getUserFromTable();
        System.out.println(owner.getId().toString());
        System.out.println(usercautat.getId().toString());

        //System.out.println(this.service.sendInvite(owner.getId(),usercautat.getId()));
        //this.numeusercautat.setText("your friendship request was sent:3");
        try { this.service.sendInvite(owner.getId(),usercautat.getId());
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);

            VBox vbox = new VBox(new Text("your friendship request was sent:3"));
            vbox.setAlignment(Pos.CENTER);
            vbox.setPadding(new Insets(30));

            dialogStage.setScene(new Scene(vbox));
            dialogStage.show();

        } catch (ValidationException e) {
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);

            VBox vbox = new VBox(new Text("Nu s-a trimis"));
            vbox.setAlignment(Pos.CENTER);
            vbox.setPadding(new Insets(30));

            dialogStage.setScene(new Scene(vbox));
            dialogStage.show();



        }
    }

    public void showacceptButton(MouseEvent mouseEvent) {

    }

    public void acceptRequest(ActionEvent actionEvent) {
        this.service.acceptInvite(this.owner.getId(),this.usercautat.getId() );
        this.modelFriends.setAll(service.getFriends(owner));
        this.modelFriends2.setAll(service.getInvitesFrom(owner.getId()));
        this.modelFriends3.setAll((Utilizator) service.getInvites(owner.getId()));
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);

        VBox vbox = new VBox(new Text("your are friends now:3"));
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(30));

        dialogStage.setScene(new Scene(vbox));
        dialogStage.show();
    }

    public void openconversation(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("messages.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        MessagesController MC=fxmlLoader.getController();
        Stage stage=new Stage();
        stage.setTitle("Message");
        stage.setScene(scene);
        stage.setResizable(false);
        MC.setService(this.service);
        MC.setUser(this.owner);
        stage.show();

    }

    public void sendMessage(ActionEvent actionEvent) throws IOException {     FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("sendMessage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        SendMessageController SMC=fxmlLoader.getController();
        Stage stage=new Stage();
        stage.setTitle("Message");
        stage.setScene(scene);
        stage.setResizable(false);
        SMC.setService(this.service);
        SMC.setUser(this.owner);
        stage.show();
    }

    public void LogOut(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("start.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        StartController SC=(StartController) fxmlLoader.getController();

        Stage stage =new Stage();
        stage.setTitle("Meow");
        stage.setScene(scene);
        stage.setResizable(false);
        SC.setService(service);
        stage.show();
        
    }
    void setUserCautat(String nume){
        System.out.println("fhnethnbe");
        this.usercautat=service.getUserByUsername(nume);
        this.numeusercautat.setText(nume);
       // this.sendinviteButton.setVisible(true);
        this.removefriendButton.setVisible(true);
        //this.acceptButton.setVisible(true);
    }
    public void getUserFromTable(){
        tableFriends.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if(tableFriends.getSelectionModel().getSelectedItem() != null)
                {
                    TableView.TableViewSelectionModel selectionModel = tableFriends.getSelectionModel();
                    ObservableList selectedCells = selectionModel.getSelectedCells();
                    TablePosition tablePosition = (TablePosition) selectedCells.get(0);
                    Object val =tablePosition.getTableColumn().getCellData(newValue);
                   setUserCautat(val.toString());
                    System.out.println(usercautat);
                }
            }
        });
    }
    void setRequest(String nume){
        setUserCautat(nume);
        this.acceptButton.setVisible(true);
        this.removefriendButton.setVisible(true);

    }
    public void getRequestFromTable(){
        tabelfriendshipRequests.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if(tabelfriendshipRequests.getSelectionModel().getSelectedItem() != null)
                {
                    TableView.TableViewSelectionModel selectionModel = tabelfriendshipRequests.getSelectionModel();
                    ObservableList selectedCells = selectionModel.getSelectedCells();
                    TablePosition tablePosition = (TablePosition) selectedCells.get(0);
                    Object val =tablePosition.getTableColumn().getCellData(newValue);
                    setRequest(val.toString());
                    System.out.println(usercautat);
                }
            }
        });
    }

    public void deleteFriend(ActionEvent actionEvent) {
        service.rejectInvite(usercautat.getId(),owner.getId());
        this.modelFriends.setAll(service.getFriends(owner));
        this.modelFriends2.setAll(service.getInvitesFrom(owner.getId()));
    }

    public void deleteaccount(ActionEvent actionEvent) throws IOException {

    }


    public void OpenConv(ActionEvent actionEvent) {

    }

    public void showYourRequests(ActionEvent actionEvent) {

        tabelfriendshipRequests.setItems(modelFriends3);
        System.out.println("modelfr");
        System.out.println(modelFriends3);
        coltabelrequests.setCellValueFactory(new PropertyValueFactory<>("username"));

        //this.tableFriends.setVisible(false);
        this.tabelfriendshipRequests.setVisible(true);
        //  column.setVisible(false);
        coltabelrequests.setVisible(true);
        this.getRequestFromTable();
        this.acceptButton.setVisible(false);
    }
}
