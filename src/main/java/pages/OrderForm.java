package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class OrderForm {
    private final WebDriver webDriver;
    private final WebDriverWait wait;
    private final By firstNameLocator = By.xpath("//input[@placeholder='* Имя']");
    private final By lastNameLocator = By.xpath("//input[@placeholder='* Фамилия']");
    private final By addressLocator = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    private final By stationMetroLocator = By.xpath("//input[@placeholder='* Станция метро']");
    private final By numberTelephoneLocator = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");
    private final By nextOrderButtonLocator = By.cssSelector("button.Button_Button__ra12g.Button_Middle__1CSJM");
    private final By dateLocator = By.xpath("//input[@placeholder='* Когда привезти самокат']");
    private final By timeRentLocator = By.className("Dropdown-control");
    private final By dropDownMenuLocator = By.className("Dropdown-menu");
    private final By commentLocator = By.xpath("//input[@placeholder='Комментарий для курьера']");
    private final By completeNextOrderButtonLocator = By.xpath("//div[contains(@class,'Order_Buttons')]//button[text()='Заказать']");
    private final By textLocator = By.className("Order_Header__BZXOb");
    private final By conformOrderButtonLocator =  By.xpath("//div[contains(@class,'Order_Buttons')]//button[text()='Да']");
    private final By popUpLocator = By.xpath("//div[contains(@class, 'Order_Modal__YZ-d3')]//div[contains(text(), 'Заказ оформлен')]");

    public OrderForm(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
    }

    public void fillFirstStep(String name, String lastName, String address, String metro, String number) {
        webDriver.findElement(firstNameLocator).sendKeys(name);
        webDriver.findElement(lastNameLocator).sendKeys(lastName);
        webDriver.findElement(addressLocator).sendKeys(address);
        webDriver.findElement(stationMetroLocator).sendKeys(metro);
        WebElement stationOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='select-search__select']//button[contains(., '" + metro + "')]")));
        stationOption.click();
        webDriver.findElement(numberTelephoneLocator).sendKeys(number);
    }

    public void clickNextButton() {
        var nextButton = webDriver.findElement(nextOrderButtonLocator);
        nextButton.click();
    }

    public void fillSecondStep(String date, String rentPeriod, String[] colorId, String comment) {
        var inputDate = webDriver.findElement(dateLocator);
        inputDate.sendKeys(date);
        webDriver.findElement(textLocator).click();
        selectRentalPeriod(rentPeriod);
        selectColors(colorId);
        webDriver.findElement(commentLocator).sendKeys(comment);

    }

    public void openDropDown() {
        WebElement dropDownControl = wait.until(ExpectedConditions.elementToBeClickable(timeRentLocator));
        dropDownControl.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(dropDownMenuLocator));
    }

    public void selectOption(String optionText) {
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@class, 'Dropdown-option') and text()='" + optionText + "']")));
        option.click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(dropDownMenuLocator));
    }

    public void selectRentalPeriod(String periodText) {
        openDropDown();
        selectOption(periodText);
    }

    public void clickCheckboxIfExists(String checkboxId) {
        var checkbox = webDriver.findElement(By.id(checkboxId));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
    }

    public void selectColors(String[] colors) {
        for (String color : colors) {
            clickCheckboxIfExists(color);
        }
    }

    public void completeOrderButton() {
        webDriver.findElement(completeNextOrderButtonLocator).click();
    }

    public void conformOrder() {
        webDriver.findElement(conformOrderButtonLocator).click();
    }

    public boolean isCheckOrderCompletePopUpDisplayed() {
        var finalPopUp = wait.until(ExpectedConditions.visibilityOfElementLocated(popUpLocator));
        return finalPopUp.isDisplayed();
    }


}
