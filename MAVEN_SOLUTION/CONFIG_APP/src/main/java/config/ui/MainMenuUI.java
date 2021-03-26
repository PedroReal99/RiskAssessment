/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config.ui;

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
        return "Insurance Main Menu:";
    }

    @Override
    public boolean show() {
        drawFormTitle();
        return doShow();
    }

 

    /**

     * @return true if the user selected the exit option

     */

    @Override

    public boolean doShow() {

        final Menu menu = buildMainMenu();

        final MenuRenderer renderer;
        
        final MenuItemRenderer iren = new MenuItemRenderer();

        renderer = new VerticalMenuRenderer(menu , iren);

        return renderer.render();

    }

 

    private Menu buildMainMenu() {
        final Menu mainMenu = new Menu();
        mainMenu.addItem(new MenuItem(1, "Create Coverage" , new CreateCoverageAction()));
        mainMenu.addItem(new MenuItem(2, "List Coverages", new ListCoverageAction()));
        mainMenu.addItem(new MenuItem(3, "Create Surrounding Type" , new CreateSurroundingTypeAction()));
        mainMenu.addItem(new MenuItem(4, "List Surrounding Type", new ListSurroundingTypeAction()));
        mainMenu.addItem(new MenuItem(5, "Create Risk Factor", new CreateRiskFactorAction()));
        mainMenu.addItem(new MenuItem(6, "List Risk Factor", new ListRiskFactorUIAction()));
        mainMenu.addItem(new MenuItem(7, "Import Characterization", new ImportCharacterizationAction()));
        mainMenu.addItem(new MenuItem(8, "Export Meaningful Risk Factors for each Coverage", new ExportMeaninfulRiskFactorsAction()));
        mainMenu.addItem(new MenuItem(9, "Get coverages not configured in a risk matrix", new CheckCoveragesInMatrixAction()));
        mainMenu.addItem(new MenuItem(10, "Get risk factors not configured in a risk matrix", new CheckRiskFactorsInMatrixAction()));
        mainMenu.addItem(new MenuItem(11, "Show Surrounding Types for a Specific Location", new SurroundingTypeByLocationAction()));
        mainMenu.addItem(new MenuItem(12, "Matrix configuration", new MatrixConfigMenuAction()));
        mainMenu.addItem(new MenuItem(0, "Exit", new ExitUIAction()));
        
        return mainMenu;

    }

 
}
