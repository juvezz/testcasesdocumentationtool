package stepdefs;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import steplogics.FeaturePageLogic;

public class FeaturePageStepsDef {

    FeaturePageLogic featurePageLogic = new FeaturePageLogic();

    @When("^open start page of test cases tool$")
    public void openStartPageOfTestCasesTool() {
        featurePageLogic.openStartPageOfTestCasesTool();
    }

    @And("^click features link$")
    public void clickFeaturesLink() {
        featurePageLogic.clickFeaturesLink();
    }

    @And("^add click add new feature$")
    public void addClickAddNewFeature() {
        featurePageLogic.addClickAddNewFeature();
    }

    @And("^write new name for feature file$")
    public void writeNewNameForFeatureFile() {
        featurePageLogic.writeNewNameForFeatureFile();
    }

    @And("^click save button$")
    public void clickSaveButton() {
        featurePageLogic.clickSaveButton();
    }

    @Then("^new feature file is added$")
    public void newFeatureFileIsAdded() {
        featurePageLogic.newFeatureFileIsAdded();
    }

    @And("^close alert window$")
    public void closeAlertWindow() {
        featurePageLogic.closeAlertWindow();
    }

    @And("^delete added feature$")
    public void deleteAddedFeature() {
        featurePageLogic.deleteAddedFeature();
    }

    @And("^check new amount of features$")
    public void checkNewAmountOfFeatures() {
        featurePageLogic.checkNewAmountOfFeatures();
    }
}
