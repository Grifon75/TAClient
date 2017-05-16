/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tyaa.taclient;

import org.tyaa.taclient.screensframework.ScreensController;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.tyaa.tradingactivity.entity.Sale;
import org.tyaa.tradingactivity.session.RemoteServiceRemote;


/**
 *
 * @author Yurii
 */
public class Main extends Application
{
    public static String mainID = "main";
    public static String mainView = "/org/tyaa/taclient/view/Main.fxml";
    public static String addSaleID = "add_sale";
    public static String addSaleView = "/org/tyaa/taclient/view/AddSale.fxml";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
        
//        InitialContext initialContext = null;
//        RemoteServiceRemote remoteServiceRemote = null;
//        try {
//        initialContext = new InitialContext();
//              } catch (NamingException ex) {
//                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//              }
//        try {
//        remoteServiceRemote = (RemoteServiceRemote) initialContext.lookup("remote_service");
//                } catch (NamingException ex) {
//                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//                }
//        System.out.println(((Sale)remoteServiceRemote.getAllSales().get(0)).getSecurityName());
//        Sale newSale = new Sale();
//        newSale.setPrice(59);
//        newSale.setQuantity(150);
//        newSale.setSecurityName("AMD");
//        remoteServiceRemote.addSale(newSale, 1, 1);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Создаем объект скринс-фреймворка (контейнер представлений)
        ScreensController screensContainer = new ScreensController();
        //Добавляем в него представления главного окна и окна добавления продажи
        screensContainer.loadScreen(Main.mainID, Main.mainView);
        screensContainer.loadScreen(Main.addSaleID, Main.addSaleView);
        //Устанавливаем представление главного окна в качестве текуего
        screensContainer.setScreen(Main.mainID);
        //Создаем корневой контейнер, помещаем в него наш контейнер представлений,
        //на его базе - сцену, которую подключаем в главный стейдж и отображаем стейдж
        Group root = new Group();
        root.getChildren().addAll(screensContainer);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        System.out.print("test");
    }
    
}
