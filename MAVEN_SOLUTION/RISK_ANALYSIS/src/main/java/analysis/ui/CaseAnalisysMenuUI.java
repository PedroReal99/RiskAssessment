/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysis.ui;

import analysis.controller.CaseAnalisysController;
import analysis.controller.CaseController;
import eapli.framework.actions.menu.Menu;
import eapli.framework.actions.menu.MenuItem;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.ListUI;
import eapli.framework.presentation.console.ShowUiAction;
import eapli.framework.presentation.console.menu.MenuItemRenderer;
import eapli.framework.presentation.console.menu.MenuRenderer;
import eapli.framework.presentation.console.menu.VerticalMenuRenderer;

/**
 *
 * @author Carlos Coelho
 */
public class CaseAnalisysMenuUI extends AbstractUI {

    private final CaseAnalisysController c;

    public CaseAnalisysMenuUI(CaseAnalisysController c) {
        this.c = c;
    }

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
        mainMenu.addItem(new MenuItem(1, "List case details", new GeneralShowUiAction(new ListUI<>(c.getDetails(), System.out::println, "case details", "Details"))));
        mainMenu.addItem(new MenuItem(2, "Export case to xHtml", new GeneralShowUiAction(new ExportCaseUI(c))));
        mainMenu.addItem(new MenuItem(4, "Confirm resultls", new GeneralShowUiAction(new ConfirmCaseAssessementUI(c))));
        mainMenu.addItem(new MenuItem(5, "Manually set results", new GeneralShowUiAction(new ManuallySetResultsUI(c))));
        mainMenu.addItem(new MenuItem(6, "Show relevant surroundings", new GeneralShowUiAction(new ShowRelevantSurroundingsUI(c))));
        mainMenu.addItem(new MenuItem(7, "Add relevant surroundings", new GeneralShowUiAction(new AddRelevantSurroundingUI(c))));
        mainMenu.addItem(new MenuItem(8, "Remove relevant surroundings", new GeneralShowUiAction(new RemoveRelevantSuroundingUI(c))));
        mainMenu.addItem(new MenuItem(9, "Reevaluate request", new GeneralShowUiAction(new ReevaluateRequestUI(c))));
        mainMenu.addItem(new MenuItem(0, "Exit", new ExitUIAction()));
        return mainMenu;

    }

}
