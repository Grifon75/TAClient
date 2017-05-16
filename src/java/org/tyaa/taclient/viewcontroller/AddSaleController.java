/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tyaa.taclient.viewcontroller;

import org.tyaa.taclient.screensframework.ControlledScreen;
import org.tyaa.taclient.screensframework.ScreensController;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.tools.ValueExtractor;
import org.controlsfx.validation.ValidationMessage;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.tyaa.taclient.Main;
import org.tyaa.taclient.rmi.RMIConnector;
import org.tyaa.tradingactivity.entity.Sale;
import org.tyaa.tradingactivity.session.RemoteServiceRemote;

/**
 * FXML Controller class
 *
 * @author Yurii
 */
public class AddSaleController implements Initializable, ControlledScreen {

    @FXML
    CustomTextField securityNameCTextField;
    
    @FXML
    CustomTextField quantityCTextField;
    
    @FXML
    CustomTextField priceCTextField;
    
    ScreensController myController;
    ValidationSupport validationSupport;
    RemoteServiceRemote remoteServiceRemote;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
//        InitialContext initialContext = null;
//        try {
//            initialContext = new InitialContext();
//        } catch (NamingException ex) {
//            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        try {
//            remoteServiceRemote =
//                    (RemoteServiceRemote) initialContext.lookup("remote_service");
//        } catch (NamingException ex) {
//            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
//        }

        remoteServiceRemote = RMIConnector.getRemoteService();
        
        ValueExtractor.addObservableValueExtractor(
                c -> c instanceof CustomTextField
                , c -> ((CustomTextField) c).textProperty());
        validationSupport = new ValidationSupport();
        validationSupport.setErrorDecorationEnabled(true);
        validationSupport.registerValidator(
                securityNameCTextField
                , Validator.createEmptyValidator("Security name is required"));
        validationSupport.registerValidator(
                quantityCTextField
                , Validator.createEmptyValidator("Quantity is required"));
        validationSupport.registerValidator(
                priceCTextField
                , Validator.createEmptyValidator("Price is required"));
    }    

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }
    
    @FXML
    private void goToMainScreen(ActionEvent event){
       myController.setScreen(Main.mainID);
    }
    
    public void actionAddSale(ActionEvent actionEvent) {
        //securityNameCTextField.getStyleClass().remove("error-c-text-field");
        //securityNameCTextField.getStyleClass().add("custom-text-field");
        List<ValidationMessage> validationMessageList =
                (List<ValidationMessage>) validationSupport
                        .getValidationResult()
                        .getMessages();
        if (validationMessageList.isEmpty()) {
            securityNameCTextField.promptTextProperty()
                                .setValue("security name");
            quantityCTextField.promptTextProperty()
                                .setValue("quantity");
            priceCTextField.promptTextProperty()
                                .setValue("price");
            securityNameCTextField.setStyle("-fx-border-color:lightgray;");
            quantityCTextField.setStyle("-fx-border-color:lightgray;");
            priceCTextField.setStyle("-fx-border-color:lightgray;");
            //System.out.println(securityNameCTextField.getText());
            Sale newSale = new Sale();
            newSale.setPrice((int)(Double.parseDouble(priceCTextField.getText()) * 100));
            newSale.setQuantity(Integer.parseInt(quantityCTextField.getText()));
            newSale.setSecurityName(securityNameCTextField.getText());
            System.out.println(securityNameCTextField.getText());
            //remoteServiceRemote.addSale(newSale, 1, 1);
            //remoteServiceRemote.addSale(newSale);
            remoteServiceRemote.addSale(securityNameCTextField.getText(), (int)(Double.parseDouble(priceCTextField.getText()) * 100), Integer.parseInt(quantityCTextField.getText()), 1, 1);
            securityNameCTextField.textProperty().setValue("");
            quantityCTextField.textProperty().setValue("");
            priceCTextField.textProperty().setValue("");
        } else {
            String currentControlIdString;
            for (ValidationMessage validationMessage : validationMessageList) {
                currentControlIdString =
                        ((CustomTextField)validationMessage.getTarget()).getId();
                switch(currentControlIdString){
                    case "securityNameCTextField" : {
                        securityNameCTextField.promptTextProperty()
                                .setValue(validationMessage.getText());
                        //securityNameCTextField.getStyleClass().remove("custom-text-field");
                        //securityNameCTextField.getStyleClass().add("error-c-text-field");
                        securityNameCTextField.setStyle("-fx-border-color:red;");
                        break;
                    }
                    case "quantityCTextField" : {
                        quantityCTextField.promptTextProperty()
                                .setValue(validationMessage.getText());
                        quantityCTextField.setStyle("-fx-border-color:red;");
                        break;
                    }
                    case "priceCTextField" : {
                        priceCTextField.promptTextProperty()
                                .setValue(validationMessage.getText());
                        priceCTextField.setStyle("-fx-border-color:red;");
                        break;
                    }
                }
                System.out.println(((CustomTextField)validationMessage.getTarget()).getId());
            }
        }
    }
}
