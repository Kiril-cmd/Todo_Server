package jat.appClasses;

import jat.ServiceLocator;
import jat.abstractClasses.Controller;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

/**
 * Copyright 2015, FHNW, Prof. Dr. Brad Richards. All rights reserved. This code
 * is licensed under the terms of the BSD 3-clause license (see the file
 * license.txt).
 * 
 * @author Brad Richards
 */

// Controller needed to initialize the server. Interaction with view class not necessary.
public class App_Controller extends Controller<App_Model, App_View> {
    ServiceLocator serviceLocator;
    private int port = 50002;

    public App_Controller(App_Model model, App_View view) {
        super(model, view);
        
        serviceLocator = ServiceLocator.getServiceLocator();        
        serviceLocator.getLogger().info("Application controller initialized");
        model.startServer(port);
    }
 
}
