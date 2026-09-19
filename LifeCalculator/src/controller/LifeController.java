package controller;

import model.LifeModel;
import view.InputDialog;
import view.MainView;

import java.time.LocalDate;

public class LifeController {

    private final LifeModel model;
    private final MainView view;

    public LifeController(
            LifeModel model,
            MainView view
    ) {

        this.model = model;

        this.view = view;

        view.getInputButton()
                .addActionListener(e -> openInputDialog()
                );
    }

    private void openInputDialog() {

        LocalDate previousDate = model.getBirthDate();

        InputDialog dialog = new InputDialog(view, previousDate);

        dialog.setVisible(true);

        LocalDate newDate = dialog.getResult();

        if (newDate != null) {

            model.setBirthDate(newDate);
        }
    }
}