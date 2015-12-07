/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databas_lab_1;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Anton
 */
public class LoginStage extends Stage{
    private DatabaseCommunication dbCom = null;
    private MediaCenter mc = null;
    private TextField tf_username = new TextField();
    private PasswordField tf_pwd = new PasswordField();
    private Button btn_login = new Button("Login");
    
    public LoginStage(DatabaseCommunication dbCom, MediaCenter mc){
        this.dbCom = dbCom;
        this.mc = mc;
        super.initStyle(StageStyle.UTILITY);
      
        VBox vb = new VBox();
        vb.getChildren().add(new Label("Login"));
        vb.getChildren().add(new Label("Username"));
        vb.getChildren().add(tf_username);
        vb.getChildren().add(new Label("Password"));
        vb.getChildren().add(tf_pwd);
        vb.getChildren().add(btn_login);
        vb.setPadding(new Insets(10,10,10,10));
        
        btn_login.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                Thread t  = new Thread(){
                    public void run(){
                        System.out.println("Attempting to login...");
                        User user = 
                                dbCom.loginWithUser(tf_username.getText(), tf_pwd.getText());
                        javafx.application.Platform.runLater(
                            new Runnable(){
                                public void run(){
                                    if(user == null){
                                        Alert alert = new Alert(AlertType.WARNING);
                                        alert.setTitle("Wrong password/username!");
                                        alert.setContentText("You have entered an incorrect username and/or password!");
                                        alert.show(); 
                                    }
                                    else{
                                        mc.setUser(user);
                                        closeLogin();
                                    }
                                }
                            }
                        );
                    }
                };
                t.start();
            }
        });
        
        Scene scene_Login = new Scene(vb, 300, 300);
        
        super.setScene(scene_Login);
        super.showAndWait();
    }
    
    private void closeLogin(){
        super.close();
    }
}
