package stepdefs;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import steplogics.BaseClass;

public class ServiceHooks {

    BaseClass baseClass;

    public ServiceHooks() {
        baseClass = new BaseClass();
    }

    @Before
    public void before() {
        baseClass.beforeTestSteps();
    }

    @After
    public void cleanup() {
        baseClass.cleanup();
    }
}
