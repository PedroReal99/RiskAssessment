/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysis.ui;

import eapli.framework.actions.menu.Menu;
import eapli.framework.actions.menu.MenuItem;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.menu.MenuItemRenderer;
import eapli.framework.presentation.console.menu.MenuRenderer;
import eapli.framework.presentation.console.menu.VerticalMenuRenderer;

/**
 *
 * @author Vasco_Rodrigues
 */
public class MainMenuUI extends AbstractUI {

    @Override
    public String headline() {
        return "Risk Analysis Menu:";
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
        mainMenu.addItem(new MenuItem(1, "List Pending cases", new CasePendingAction()));
        mainMenu.addItem(new MenuItem(2, "List my Pending cases or analyse one pending case", new AssignedCasePendingAction()));
        mainMenu.addItem(new MenuItem(3, "List cases validated by me", new ViewCasesValidatedByMeAction()));
        mainMenu.addItem(new MenuItem(5, "Register a Surrounding in a certain Location", new RegisterSurroundingInACertainLocationAction()));
        mainMenu.addItem(new MenuItem(6, "List Surroundings", new SurroundingAction()));
        mainMenu.addItem(new MenuItem(7, "Perform Risk Assessment", new RiskAssessmentAction()));
        mainMenu.addItem(new MenuItem(8, "Logout", new LoginAction()));
        mainMenu.addItem(new MenuItem(0, "Exit", new ExitUIAction()));
        return mainMenu;

    }

}
