package com.example.demo;

import com.example.demo.domain.Message;
import com.example.demo.domain.Utilizator;
import com.example.demo.domain.validators.FriendshipRequestValidator;
import com.example.demo.domain.validators.MessageValidator;
import com.example.demo.domain.validators.UtilizatorValidator;
import com.example.demo.domain.validators.ValidatorPrietnie;
import com.example.demo.repository.Repository;
import com.example.demo.repository.database.FriendshipRequestDB;
import com.example.demo.repository.database.MessageDB;
import com.example.demo.repository.database.PrietenieDB;
import com.example.demo.repository.database.UtilizatorDB;
import com.example.demo.service.Service1;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        var userRepo = new UtilizatorDB("jdbc:postgresql://localhost:5432/retea_socializare", "postgres", "0609", new UtilizatorValidator());
        var friendshipRepo = new PrietenieDB("jdbc:postgresql://localhost:5432/retea_socializare", "postgres", "0609", new ValidatorPrietnie(), (UtilizatorDB)userRepo);
        var friendrequestRepo=new FriendshipRequestDB("jdbc:postgresql://localhost:5432/retea_socializare", "postgres", "0609", new FriendshipRequestValidator(), (UtilizatorDB)userRepo);
        var messageRepo=new MessageDB("jdbc:postgresql://localhost:5432/retea_socializare", "postgres", "0609", new MessageValidator(), (UtilizatorDB)userRepo);
        Service1 srv = new Service1(userRepo, friendshipRepo,friendrequestRepo,messageRepo);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("start.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        StartController SC=(StartController) fxmlLoader.getController();
        SC.setService(srv);
        SC.setStage(stage);
        stage.setTitle("Meow");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();


    }

    public static void main(String[] args) {
        launch();
    }

}