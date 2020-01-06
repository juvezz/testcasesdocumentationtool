package steplogics;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.FeaturePage;
import pageobjects.StartPage;

public class FeaturePageLogic {

    WebDriver driver = BaseClass.getDriver();
    private int oldFeatureAmount;

    public void openStartPageOfTestCasesTool() {
        driver.get("http://localhost:8080/testcasesversion3_war/");
    }

    public void clickFeaturesLink() {
        WebElement featureMenuItem = driver.findElement(StartPage.featurePageLink);
        featureMenuItem.click();
    }

    public void addClickAddNewFeature() {
        oldFeatureAmount = driver.findElements(FeaturePage.feature).size();
        WebElement addNewFeatureButton = driver.findElement(FeaturePage.addNewFeatureButton);
        addNewFeatureButton.click();
    }

    public void writeNewNameForFeatureFile() {
        WebElement textFieldForFeatureName = driver.findElement(FeaturePage.textFieldForFeatureName);
        textFieldForFeatureName.sendKeys("testName");
    }

    public void clickSaveButton() {
        WebElement saveButton = driver.findElement(FeaturePage.saveButton);
        saveButton.click();
    }

    public void newFeatureFileIsAdded() {
        int newAmountOfFeatures = driver.findElements(FeaturePage.feature).size();
        Assert.assertEquals(oldFeatureAmount + 1, newAmountOfFeatures);
    }

    public void closeAlertWindow() {
        
    }
}
