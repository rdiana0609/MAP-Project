package com.example.demo;

import com.example.demo.domain.Utilizator;
import com.example.demo.service.Service1;
import javafx.application.Preloader;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    public Button BackButton;
    Service1 service;
    public Button LogInButton;
    public TextField usernameTxt;
    public TextField emailTxt;
    private Stage stage;
    private Stage stg;

    public void LogInAction(ActionEvent actionEvent) throws IOException {

        String email = emailTxt.getText();
        var found=service.getUserByEmail(email);
        if(found==null)
        {Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);

            VBox vbox = new VBox(new Text("The user does not exist in database!"));
            vbox.setAlignment(Pos.CENTER);
            vbox.setPadding(new Insets(30));

            dialogStage.setScene(new Scene(vbox));
            dialogStage.show();
        }
        else
        {FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("loggedin.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Logged_inController LC= fxmlLoader.getController();

        Stage stage=new Stage();
        stage.setTitle("Loggedin");
        stage.setScene(scene);
        stage.setResizable(false);
        LC.setService(this.service);
        LC.setUser(found);
        stage.show();

       }
        System.out.println(service.getAllFriendships());


    }

    public void setService(Service1 service) {
        this.service=service;
    }
    public void setStage(Stage stage){
        this.stage=stage;
    }

    public void backToStart(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("start.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        StartController SC=(StartController) fxmlLoader.getController();

        stage.setTitle("Meow");
        stage.setScene(scene);
        stage.setResizable(false);
        SC.setService(service);
        stage.show();
        stg.close();
    }


}
