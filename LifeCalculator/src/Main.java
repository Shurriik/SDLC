import controller.LifeController;
import model.LifeModel;
import view.MainView;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            LifeModel model = new LifeModel();

            MainView view = new MainView(model);

            new LifeController(model, view);

            view.setVisible(true);
        });
    }
}