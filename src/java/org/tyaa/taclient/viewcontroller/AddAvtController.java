/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tyaa.taclient.viewcontroller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.tools.ValueExtractor;
import org.controlsfx.validation.ValidationMessage;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.tyaa.taclient.Main;
import org.tyaa.taclient.rmi.RMIConnector;
import org.tyaa.taclient.screensframework.ControlledScreen;
import org.tyaa.taclient.screensframework.ScreensController;
import org.tyaa.tradingactivity.entity.Sale;
import org.tyaa.tradingactivity.session.RemoteServiceRemote;

/**
 *
 * @author Администратор
 */
public class AddAvtController implements Initializable, ControlledScreen {
    
    @FXML
    Label wrnMessage;
    
    @FXML
    CustomTextField loginTextField;
    
    @FXML
    CustomTextField passwordTextField;
    
    ScreensController myController;
    RemoteServiceRemote remoteServiceRemote;
    ValidationSupport validationSupport;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        remoteServiceRemote = RMIConnector.getRemoteService();
        ValueExtractor.addObservableValueExtractor(
                c -> c instanceof CustomTextField
                , c -> ((CustomTextField) c).textProperty());
        validationSupport = new ValidationSupport();
        validationSupport.setErrorDecorationEnabled(true);
        validationSupport.registerValidator(
                loginTextField
                , Validator.createEmptyValidator("login is required"));
        validationSupport.registerValidator(
                passwordTextField
                , Validator.createEmptyValidator("password is required"));
    }
    
    public void actionAvthorization(ActionEvent actionEvent){
        List<ValidationMessage> validationMessageList =
                (List<ValidationMessage>) validationSupport
                        .getValidationResult()
                        .getMessages();
        if (validationMessageList.isEmpty()) {
            if(remoteServiceRemote.addAvt(loginTextField.getText(), passwordTextField.getText())){
                myController.setScreen(Main.addSaleID);
                wrnMessage.setText("Authorisation");
                loginTextField.setText("");
                passwordTextField.setText("");
            }
            else{
                wrnMessage.setText("Authorisation Error");
                wrnMessage.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            }
        } else {
            String currentControlIdString;
            for (ValidationMessage validationMessage : validationMessageList) {
                currentControlIdString =
                        ((CustomTextField)validationMessage.getTarget()).getId();
                switch(currentControlIdString){
                    case "loginTextField" : {
                        loginTextField.promptTextProperty()
                                .setValue(validationMessage.getText());
                        //securityNameCTextField.getStyleClass().remove("custom-text-field");
                        //securityNameCTextField.getStyleClass().add("error-c-text-field");
                        loginTextField.setStyle("-fx-border-color:red;");
                        break;
                    }
                    case "passwordTextField" : {
                        passwordTextField.promptTextProperty()
                                .setValue(validationMessage.getText());
                        passwordTextField.setStyle("-fx-border-color:red;");
                        break;
                    }
                }
                System.out.println(((CustomTextField)validationMessage.getTarget()).getId());
            }
        }   
        
    }
    
    public void goToMainScreen(ActionEvent actionEvent){
//        wrnMessage.setText("Authorisation");
//        loginTextField.setText("");
//        passwordTextField.setText("");
        myController.setScreen(Main.mainID);
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }
    
}
