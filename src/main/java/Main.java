import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class Main {
    public static void main(String[] args) throws InterruptedException {
        String login = "webinar.test@gmail.com";
        String pwd = "Xcg7299bnSmMuRLp9ITw";
        String url = "http://prestashop-automation.qatestlab.com.ua/admin147ajyvk0/";

        WebDriver driver =  getChromeDriver();
        driver.get(url);

        loginAsAdministrator(driver, login, pwd);

        WebElement itemUser = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.className("employee_avatar_small")));
        itemUser.click();

        WebElement itemExit = driver.findElement(By.id("header_logout"));
        itemExit.click();

        loginAsAdministrator(driver, login, pwd);

        List<WebElement> menuElements = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy((By.cssSelector(".maintab"))));

        List<String> menuElementsId = new ArrayList<String>();
        for (WebElement el : menuElements){
            String element_id = el.getAttribute("id");
            menuElementsId.add(element_id);
        }

        for (int i= 0; i < menuElementsId.size(); i++) {
            WebElement menuItem = driver.findElement(By.id(menuElementsId.get(i)));
            menuItem.click();
            Thread.sleep(1000);
            String pageTitle = driver.getTitle();
            System.out.println("Page title is: " + pageTitle);
            driver.navigate().refresh();
            Thread.sleep(1000);
            String pageTitle2 = driver.getTitle();
            System.out.println("Is it the same page?: "  + pageTitle.equals(pageTitle2));
            if (i > 0) {
                driver.navigate().back();
            }
        }

        driver.quit();
    }

    public static void loginAsAdministrator(WebDriver webDriver, String adminLogin, String adminPwd) {
        WebElement fieldEmail = webDriver.findElement(By.id("email"));
        WebElement fieldPwd = webDriver.findElement(By.id("passwd"));
        fieldEmail.sendKeys(adminLogin);
        fieldPwd.sendKeys(adminPwd);
        WebElement buttonEnter = webDriver.findElement(By.xpath("//*[@id=\"login_form\"]/div[3]/button"));
        buttonEnter.click();
    }

    public static WebDriver getChromeDriver() {
        String driverPath = System.getProperty("user.dir") + "/drivers/chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", driverPath);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");

        return new ChromeDriver(options);
    }
}
