/*package com.example.demo;

import com.example.demo.domain.Message;
import com.example.demo.domain.Utilizator;
import com.example.demo.service.Service1;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class Newchat {
    public ListView listview;
    public ListView listacumesaje;
    public TextField textmesaj;
    public Button sendButton;
    private Service1 service;
    ObservableList<Message> modelMessages;
    private Utilizator owner;

    @FXML
    void initialize(){
        listacumesaje.setItems(modelMessages);
        //fromcolumn.setCellValueFactory(new PropertyValueFactory<>("senderName"));
        //datecollumn.setCellValueFactory(new PropertyValueFactory<>("sentAt"));
       // textColum.setCellValueFactory(new PropertyValueFactory<>("text"));
       // getMessage();
        //labeltext.setText(msg.getText());



    }
    public void setUser(Utilizator stage) {
        System.out.println(owner);
        this.owner=stage;
        modelMessages.setAll(this.service.getAllMessagesForSomeone(stage.getId()));


    }

    public void setService(Service1 service) {
        this.service = service;
        this.service.addObserver(this);


    }
    public void setMessages(){
        ListView<Message> listacumesaje = new ListView<>();
        listacumesaje.setCellFactory(new Callback<ListView<Message>, ListCell<Message>>() {
            @Override
            public ListCell<Message> call(ListView<Message> param) {
                return new ListCell<Message>() {
                    @Override
                    protected void updateItem(Message item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            HBox hbox = new HBox();
                            Label sender = new Label(item.getSenderName());
                            Label date = new Label(item.getSentAt().toString());
                            Label text = new Label(item.getText());
                            hbox.getChildren().addAll(sender, date, text);
                            setGraphic(hbox);
                        }
                    }
                };
            }
        });

        listacumesaje.setItems(modelMessages);
    }
    public void getUser(MouseEvent mouseEvent) {
    }

    public void SendMessahe(ActionEvent actionEvent) {
    }
}
*/