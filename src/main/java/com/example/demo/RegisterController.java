package com.example.demo;

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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterController {
    public HBox registerStage;
    Service1 service;
    public Button DoneButton;
    public TextField emailRegisterTxt;
    public TextField usernameRegisterTxt;
    private Stage stg;
    String username;
    String email;


    public void SetNewUsername(ActionEvent actionEvent) {
        this.username=usernameRegisterTxt.getText().toString();
    }

    public void setNewEmail(ActionEvent actionEvent) {
        this.email=emailRegisterTxt.getText().toString();
    }

    public void BackToLogin(ActionEvent actionEvent) throws IOException {
        username=usernameRegisterTxt.getText().toString();
        System.out.println(username);
        email=emailRegisterTxt.getText().toString();
        Utilizator user=new Utilizator(username,email);
        if(service.getUserByEmail(email)!=null)
        {Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);

            VBox vbox = new VBox(new Text("email already in db"));
            vbox.setAlignment(Pos.CENTER);
            vbox.setPadding(new Insets(30));

            dialogStage.setScene(new Scene(vbox));
            dialogStage.show();
        }
        else{
            if(user.getEmail().equals("") || user.getUsername().equals("") || user.getUsername()==null ||user.getEmail()==null)
            {Stage dialogStage = new Stage();
                dialogStage.initModality(Modality.WINDOW_MODAL);

                VBox vbox = new VBox(new Text("Date invalide"));
                vbox.setAlignment(Pos.CENTER);
                vbox.setPadding(new Insets(30));

                dialogStage.setScene(new Scene(vbox));
                dialogStage.show();
            }
            else{
                this.service.addUser(user);
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("loggedin.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Logged_inController LC= fxmlLoader.getController();
                LC.setService(service);
                Stage stage=new Stage();
                stage.setTitle("Loggedin");
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
                LC.setUser(user);
                this.stg.close();}}



    }

    public void setService(Service1 service) {
        this.service=service;
    }

    public void setStage(Stage stage) {
        this.stg=stage;
    }
}
