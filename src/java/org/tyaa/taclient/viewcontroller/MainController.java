/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tyaa.taclient.viewcontroller;

import org.tyaa.taclient.screensframework.ControlledScreen;
import org.tyaa.taclient.screensframework.ScreensController;
import java.net.URL;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.tyaa.taclient.Main;
import org.tyaa.taclient.model.SaleModel;
import org.tyaa.taclient.rmi.RMIConnector;
import org.tyaa.tradingactivity.entity.Sale;
import org.tyaa.tradingactivity.session.RemoteServiceRemote;

/**
 * FXML Controller class
 *
 * @author Yurii
 */
public class MainController implements Initializable, ControlledScreen {

    @FXML
    private TableView mySalesTableView;
    @FXML
    private TableColumn idTableColumn;
    @FXML
    private TableColumn securityNameTableColumn;
    @FXML
    private TableColumn quantityTableColumn;
    @FXML
    private TableColumn priceTableColumn;
    
    ScreensController myController;
    ObservableList<SaleModel> sales;
    RemoteServiceRemote remoteServiceRemote;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        sales = FXCollections.observableArrayList();
        
//        InitialContext initialContext = null;
//        RemoteServiceRemote remoteServiceRemote = null;
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

        List allMySales = remoteServiceRemote.getAllSales();
        for (Object object : allMySales) {
            Sale sale = (Sale)object;
            sales.add(new SaleModel(
                    sale.getId()
                    , sale.getSecurityName()
                    , sale.getQuantity()
                    , sale.getPrice())
            );
        }
//        sales = FXCollections.observableArrayList(
//                new SaleModel(1, "MSFT", 1000, 41.39),
//                new SaleModel(2, "ORCL", 1500, 36.70)
//        );
        //TableColumn idColumn = new TableColumn("id");
        idTableColumn.setCellValueFactory(
                new PropertyValueFactory<SaleModel, String>("id")
        );
        securityNameTableColumn.setCellValueFactory(
                new PropertyValueFactory<SaleModel, String>("securityName")
        );
        quantityTableColumn.setCellValueFactory(
                new PropertyValueFactory<SaleModel, String>("quantity")
        );
        priceTableColumn.setCellValueFactory(
                new PropertyValueFactory<SaleModel, Double>("price")
        );
        NumberFormat doubleToCurrencyFormatter =
                NumberFormat.getCurrencyInstance(new Locale("en", "US"));
        doubleToCurrencyFormatter.setMinimumIntegerDigits(1);
        doubleToCurrencyFormatter.setMinimumFractionDigits(2);
        priceTableColumn.setCellFactory(column ->{
            
            return new TableCell<SaleModel, Double>(){
                @Override
                protected void updateItem(Double item, boolean empty)
                {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText(doubleToCurrencyFormatter.format(item / 100));
                    }
                }
                
            };
        });
        mySalesTableView.setItems(sales);
        //idColumn.setSortable(true);        
    }    

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }
    
    @FXML
    private void goToAddAvtScreen(ActionEvent event){
       myController.setScreen(Main.addAvtID);
    }
    
}
