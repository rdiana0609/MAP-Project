package com.example.demo;

import com.example.demo.domain.Message;
import com.example.demo.domain.Utilizator;
import com.example.demo.service.Service1;
import com.example.demo.utils.events.observer.Observer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class MessagesController implements Observer {
    public TableView<Message> tabelMessages;
    public TableColumn fromcolumn;
    public TableColumn datecollumn;
    public Label labeltext;
    public TableColumn textColum;
    private Service1 service;
    private Utilizator owner;
    public ObservableList<Message> modelMessages= FXCollections.observableArrayList();

    @FXML
    void initialize(){
        tabelMessages.setItems(modelMessages);
        fromcolumn.setCellValueFactory(new PropertyValueFactory<>("senderName"));
       datecollumn.setCellValueFactory(new PropertyValueFactory<>("sentAt"));
       textColum.setCellValueFactory(new PropertyValueFactory<>("text"));
       getMessage();
       //labeltext.setText(msg.getText());



    }
    public void setUser(Utilizator stage) {
        System.out.println(owner);
        this.owner=stage;
        modelMessages.setAll(this.service.getAllMessagesForSomeone(stage.getId()));


    }

    public void setService(Service1 service) {
        this.service = service;
        service.addObserver(this);

    }
    public void getMessage(){
        tabelMessages.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if(tabelMessages.getSelectionModel().getSelectedItem() != null)
                {
                    TableView.TableViewSelectionModel selectionModel = tabelMessages.getSelectionModel();
                    ObservableList selectedCells = selectionModel.getSelectedCells();
                    TablePosition tablePosition = (TablePosition) selectedCells.get(0);
                    Object val =tablePosition.getTableColumn().getCellData(newValue);
                   labeltext.setText(val.toString());
                }
            }
        });
    }


    @Override
    public void update() {
        modelMessages.setAll(this.service.getAllMessagesForSomeone(owner.getId())); tabelMessages.setItems(modelMessages);
        fromcolumn.setCellValueFactory(new PropertyValueFactory<>("senderName"));
        datecollumn.setCellValueFactory(new PropertyValueFactory<>("sentAt"));
        textColum.setCellValueFactory(new PropertyValueFactory<>("text"));
        getMessage();
    }
}
