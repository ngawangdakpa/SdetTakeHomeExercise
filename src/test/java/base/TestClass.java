package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;

public class TestClass {
    @Test
    public void findFakeGoldBar() throws InterruptedException {
        WebDriver driver;
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        driver.get("http://sdetchallenge.fetch.com/");
        WebElement leftSide = driver.findElement(By.id("left_0"));
        WebElement rightSide = driver.findElement(By.id("right_0"));
        WebElement weigh = driver.findElement(By.id("weigh"));
        WebElement result = driver.findElement(By.xpath("//div[@class='result']//parent::button[@id='reset']"));
        WebElement reset = driver.findElement(By.xpath("//button[@id='reset' and text()='Reset']"));
        String fakeGold ="coin_";
        for (int i=0;i<8;i++){
            String finalResult =findTheResult(i+1,leftSide,rightSide,weigh,result);
            if (finalResult.equals("<")){
                fakeGold=fakeGold.concat(String.valueOf(0));
                break;
            } else if (finalResult.equals(">")) {
                fakeGold=fakeGold.concat(String.valueOf(i+1));
                break;
            }
            reset.click();
        }
        WebElement fakeGoldElement = driver.findElement(By.id(fakeGold));
        fakeGoldElement.click();
        Thread.sleep(3000);
        System.out.println(driver.switchTo().alert().getText());
        System.out.println("The fake bar is: "+fakeGold.charAt(5));
        driver.switchTo().alert().accept();
        driver.close();
    }
    public String findTheResult(int number, WebElement leftSide, WebElement rightSide, WebElement weigh, WebElement result) throws InterruptedException {
        leftSide.sendKeys("0");
        rightSide.sendKeys(Integer.toString(number));
        weigh.click();
        Thread.sleep(3000);
        return result.getText();
    }
}
