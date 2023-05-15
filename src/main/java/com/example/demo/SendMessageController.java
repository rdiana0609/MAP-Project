package com.example.demo;

import com.example.demo.domain.Message;
import com.example.demo.domain.Utilizator;
import com.example.demo.domain.validators.ValidationException;
import com.example.demo.service.Service1;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class SendMessageController {
    public TextField textlbl;
    public TextField subjectlbl;
    public TextField tolbl;
    public Button sendbtn;
    private Utilizator owner;
    private Service1 service;

    public void sendMessage(ActionEvent actionEvent) {
        String text=textlbl.getText();
        String subject=subjectlbl.getText();
        String numeutilizator=tolbl.getText();

       final Message m=new Message(subject,text, owner,service.getUserByUsername(numeutilizator));
        final Popup popup = new Popup();
        final Stage ownerWindow = new Stage();
        try {service.addMessage(m);
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);

            VBox vbox = new VBox(new Text("Mesaj trimis"));
            vbox.setAlignment(Pos.CENTER);
            vbox.setPadding(new Insets(30));

            dialogStage.setScene(new Scene(vbox));
            dialogStage.show();

            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("messages.fxml"));
            fxmlLoader.load();
            MessagesController MC = (MessagesController) fxmlLoader.getController();
            MC.setService(this.service);
            MC.setUser(service.getUserByUsername(numeutilizator));
            MC.update();

        } catch (ValidationException e) {
            Stage dialogStage = new Stage();
                dialogStage.initModality(Modality.WINDOW_MODAL);

                VBox vbox = new VBox(new Text("Nu s-a trimis,mesaj invalid"));
                vbox.setAlignment(Pos.CENTER);
                vbox.setPadding(new Insets(30));

                dialogStage.setScene(new Scene(vbox));
                dialogStage.show();



    } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setUser(Utilizator stage) {

        System.out.println(owner);
        this.owner=stage;



    }

    public void setService(Service1 service) {
        this.service = service;


    }

}
