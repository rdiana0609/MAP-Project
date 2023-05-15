package com.example.demo;

import com.example.demo.service.Service1;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class StartController {
    public Button registerStartButton;
    public Button loginStartButton;
    Service1 service;
    private Stage stg;

    @FXML
    void initialize(){}

    public void login(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("sign-up.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        LoginController LC=(LoginController) fxmlLoader.getController();
        LC.setService(service);

        Stage stage=new Stage();
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        LC.setStage(stage);
        //this.stg.close();



    }

    public void registerstart(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("register.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        RegisterController RC=(RegisterController) fxmlLoader.getController();
        RC.setService(service);

        Stage stage=new Stage();
        stage.setTitle("Register");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        RC.setStage(stage);
        RC.setService(service);
       // this.stg.close();


    }
    public void setService(Service1 srv){
        this.service=srv;
    }

    public void setStage(Stage stage) {
        this.stg=stage;
    }
}
