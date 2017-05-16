/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tyaa.taclient.rmi;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.tyaa.taclient.viewcontroller.MainController;
import org.tyaa.tradingactivity.session.RemoteServiceRemote;

/**
 *
 * @author Yurii
 */
public class RMIConnector {
    
    private static InitialContext mInitialContext;
    private static RemoteServiceRemote mRemoteServiceRemote;
    
    static{
        try {
            Properties p = new Properties();
            //corbaname:iiop:1.2@192.168.1.124:3700
            p.setProperty(Context.INITIAL_CONTEXT_FACTORY, "com.sun.enterprise.naming.SerialInitContextFactory");
            //p.setProperty(Context.PROVIDER_URL, "corbaname:iiop:192.168.1.124:3700");
            p.setProperty("org.omg.CORBA.ORBInitialHost", "91.196.229.101");
            p.setProperty("org.omg.CORBA.ORBInitialPort", "3700");
            mInitialContext = new InitialContext(p);
            //mInitialContext = new InitialContext();
        } catch (NamingException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            //192.168.1.124
            
            mRemoteServiceRemote =
                    (RemoteServiceRemote) mInitialContext.lookup("remoteservice");
        } catch (NamingException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static RemoteServiceRemote getRemoteService(){
        return mRemoteServiceRemote;
    }
    
}
