package steplogics;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.FeaturePage;
import pageobjects.StartPage;

import java.util.List;
import java.util.Random;

public class FeaturePageLogic {

    WebDriver driver = BaseClass.getDriver();
    private int oldFeatureAmount;
    private String featureName;

    public void openStartPageOfTestCasesTool() {
        driver.get("http://3.16.129.138:8081/testcasesversion3_war/");
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
        int featureNameNumber = new Random().nextInt(100000);
        this.featureName = "testName" + featureNameNumber;
        textFieldForFeatureName.sendKeys(this.featureName);
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
        driver.switchTo().alert().accept();
    }

    public void deleteAddedFeature() {
        List<WebElement> deleteFeatureButtons = driver.findElements(FeaturePage.deleteFeatureButton);
        WebElement deleteFeatureButton = deleteFeatureButtons.stream().
                filter(button->button.getAttribute("Name").contains(featureName)).
                findFirst().get();
        deleteFeatureButton.click();
    }

    public void checkNewAmountOfFeatures() {
        int newAmountOfFeatures = driver.findElements(FeaturePage.feature).size();
        Assert.assertEquals(oldFeatureAmount, newAmountOfFeatures);
    }
}
