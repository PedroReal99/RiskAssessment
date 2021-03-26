/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config.ui;

import config.controller.RiskMatrixController;
import eapli.framework.actions.menu.Menu;
import eapli.framework.actions.menu.MenuItem;
import eapli.framework.application.Controller;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.menu.MenuItemRenderer;
import eapli.framework.presentation.console.menu.MenuRenderer;
import eapli.framework.presentation.console.menu.VerticalMenuRenderer;

/**
 *
 * @author CarloS
 */
public class MatrixConfigMenuUI extends AbstractUI {

    RiskMatrixController controller;

    public MatrixConfigMenuUI(RiskMatrixController controller) {
        this.controller = controller;
    }

    @Override
    public String headline() {
        return "Configurig matrix " + controller.version() + " - " + controller.state();
    }

    @Override
    public boolean show() {
        drawFormTitle();
        return doShow();
    }

    /**
     *
     * @return true if the user selected the exit option
     *
     */
    @Override

    public boolean doShow() {

        final Menu menu = buildMainMenu();

        final MenuRenderer renderer;

        final MenuItemRenderer iren = new MenuItemRenderer();

        renderer = new VerticalMenuRenderer(menu, iren);

        return renderer.render();

    }

    private Menu buildMainMenu() {
        final Menu mainMenu = new Menu();
        mainMenu.addItem(new MenuItem(1, "Initialize new Matrix", new MatrixConfigNewAction(controller)));
        mainMenu.addItem(new MenuItem(2, "Select Matrix", new MatrixConfigSelectAction(controller)));
        mainMenu.addItem(new MenuItem(3, "Initialize new Matrix from an existing one", new MatrixConfigStartFromExistingAction(controller)));
        mainMenu.addItem(new MenuItem(4, "List all Matrixes", new MatrixConfigShowAllAction(controller)));
        mainMenu.addItem(new MenuItem(5, "Publish a matrix", new MatrixConfigPublixMatrixAction(controller)));
        mainMenu.addItem(new MenuItem(6, "Show selectedMatrix", new MatrixConfigShowCurrentAction(controller)));
        mainMenu.addItem(new MenuItem(0, "Exit", new ExitUIAction()));

        return mainMenu;

    }

}
