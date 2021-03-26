package config.bootstrap;

import config.bootstrapper.TestDataConstants;
import config.controller.SurroundingController;
import config.controller.SurroundingTypeController;
import eapli.framework.actions.Action;

public class SurroundingBootstrap implements Action {
    @Override
    public boolean execute(){
        register();
        return true;
    }
    public void register(){
        SurroundingController controller =new SurroundingController();
        controller.createSurrounding(TestDataConstants.STN1, TestDataConstants.GPSL1, TestDataConstants.ST1);
        controller.createSurrounding(TestDataConstants.STN2, TestDataConstants.GPSL2, TestDataConstants.ST2);
        controller.createSurrounding(TestDataConstants.STN3, TestDataConstants.GPSL3, TestDataConstants.ST3);
    }
}
