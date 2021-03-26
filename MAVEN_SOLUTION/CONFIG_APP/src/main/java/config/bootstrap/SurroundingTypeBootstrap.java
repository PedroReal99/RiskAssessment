package config.bootstrap;

import config.bootstrapper.TestDataConstants;
import config.controller.SurroundingTypeController;
import eapli.framework.actions.Action;

public class SurroundingTypeBootstrap implements Action {
    @Override
    public boolean execute(){
        register();
        return true;
    }
    public void register(){
        SurroundingTypeController controller =new SurroundingTypeController();
        controller.createSurroundingType(TestDataConstants.ST1);
        controller.createSurroundingType(TestDataConstants.ST2);
        controller.createSurroundingType(TestDataConstants.ST3);
    }
}
