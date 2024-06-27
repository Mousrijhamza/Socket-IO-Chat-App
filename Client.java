package ChatApp;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.*;
import java.io.*;
import java.net.Socket;

public class Client extends Application {
    PrintWriter printWriter;
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Client Chat");
        BorderPane borderPane=new BorderPane();
        Label label=new Label("Host :");
        TextField textField=new TextField("localhost");

        Label labelport=new Label("Port :");
        TextField textFieldport=new TextField("1234");

        Button buttonconnecter=new Button("Connecter");
        HBox hbox=new HBox(); hbox.setSpacing(10); hbox.setPadding(new Insets(10));
        hbox.setBackground(new Background(new BackgroundFill(Color.TOMATO, null, null)));
        hbox.getChildren().addAll(label, textField, labelport, textFieldport , buttonconnecter);
        // ----------------------------------------zone chat-------------------------------------------------------
        VBox vbox2=new VBox(); vbox2.setSpacing(10); vbox2.setPadding(new Insets(10));

        ObservableList listModel=FXCollections.observableArrayList();
        ListView listView=new ListView<String>(listModel);

        vbox2.getChildren().addAll(listView);
        borderPane.setCenter(vbox2);

        borderPane.setTop(hbox);
        // ----------------------------------------zone courier-------------------------------------------------------
        TextArea message= new TextArea();
        message.setPrefWidth(700);
        message.setPrefHeight(25);
        Label label1=new Label("message");
        Button buttonSendMessage=new Button("Send");
        HBox hBox1= new HBox(); hBox1.setSpacing(10);hBox1.setPadding(new Insets(15));
        hBox1.getChildren().addAll(message,buttonSendMessage);
        borderPane.setBottom(hBox1);
        //------------------------------------------------------------------------------------------------------------

        Scene scene=new Scene(borderPane,800,500);
        primaryStage.setScene(scene);
        primaryStage.show();

        buttonconnecter.setOnAction((evt)->{
            String host =textField.getText();
            int port = Integer.parseInt(textFieldport.getText());
            try {
                Socket socket=new Socket(host, port);
                InputStream is=socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader bf=new BufferedReader(isr);
                printWriter=new PrintWriter(socket.getOutputStream(), true);
                new Thread(()->{
                     try {
                        while(true){
                            String response= bf.readLine();
                            Platform.runLater(()->{ // executer au sein du thread de l'interface Client
                                listModel.add(response);
                            });
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }).start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
        buttonSendMessage.setOnAction((evt)->{
            String msg2send=message.getText();
            printWriter.println(msg2send);
        });
    }
}
