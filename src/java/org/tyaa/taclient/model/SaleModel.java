/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tyaa.taclient.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Yurii
 */
public class SaleModel {
    
    private IntegerProperty id;
    private StringProperty securityName;
    private IntegerProperty quantity;
    private DoubleProperty price;
    
    public SaleModel(int _id, String _securityName, int _quantity, double _price){
        this.id = new SimpleIntegerProperty(_id);
        this.securityName = new SimpleStringProperty(_securityName);
        this.quantity = new SimpleIntegerProperty(_quantity);
        this.price = new SimpleDoubleProperty(_price);
    }

    public int getId() {
        return id.getValue();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getSecurityName() {
        return securityName.getValue();
    }

    public StringProperty securityNameProperty() {
        return securityName;
    }

    public int getQuantity() {
        return quantity.getValue();
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public double getPrice() {
        return price.getValue();
    }

    public DoubleProperty priceProperty() {
        return price;
    }
    
}
