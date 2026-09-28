package LifeCalculator {
    part def LifeModel;
    part def MainView;
    part def InputDialog;
    part def LifeController;
    part application {
        part model : LifeModel;
        part view : MainView;
        part dialog : InputDialog;
        part controller : LifeController;
        connect view to controller;
        connect controller to model;
        connect model to view;
    }
}