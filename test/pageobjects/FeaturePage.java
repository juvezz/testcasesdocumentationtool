package pageobjects;

import org.openqa.selenium.By;

public class FeaturePage {
    public static By addNewFeatureButton = By.id("addNewFeature");
    public static By textFieldForFeatureName = By.xpath("//input[@name='newtestfeaturename']");
    public static By saveButton = By.id("saveButton");
    public static By feature = By.id("testFeatureName");
}
