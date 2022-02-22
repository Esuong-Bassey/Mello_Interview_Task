package TestSuite;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class Tests {
    // import the selenium webDriver
    private WebDriver driver;
    // import chromeDriver
    @BeforeClass
    public void setup() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "resources/chromedriver.exe");
        driver = new ChromeDriver();
        // Input project URL
        driver.get("https://computer-database.herokuapp.com/computers");
        // waiting for globally
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        // maximize window
        driver.manage().window().maximize();
        // get page title
        System.out.println(driver.getTitle());
        Thread.sleep(1000);
    }

    @Test(priority = 0)
    public void validatePageURL_Test(){
        if (driver.getCurrentUrl ().contains ("https://computer-database.herokuapp.com/computers"))
            //Pass
            System.out.println ("The Page URL is valid");
        else
            //Fail
            System.out.println ("The Page URL is not valid");
        System.out.println("validate Page URL test: Pass");
    }

    @Test(priority = 1)
    public void addNewComputerRecord(){
        // Locate Add a new computer button
        driver.findElement(By.id("add")).click();
        //assert page - Add a computer
        String expectedPageName = "Add a computer";
        String actualPageName = driver.findElement(By.xpath ("//*[@id=\"main\"]/h1")).getText();
        Assert.assertEquals(actualPageName, expectedPageName);
        //input details
        driver.findElement(By.id("name")).sendKeys("Bassey Test");
        driver.findElement(By.id("introduced")).sendKeys("2021-03-01");
        driver.findElement(By.id("discontinued")).sendKeys("2022-03-01");
        //select company
        driver.findElement(By.id("company")).click();
        driver.findElement(By.xpath("//*[@id=\"company\"]/option[2]")).click();
        //click "Create this computer" button
        driver.findElement(By.xpath("//*[@id=\"main\"]/form/div/input")).click();
        //assert success message
        String expectedAlertMessage = "Done! Computer Bassey Test has been created";
        String actualAlertMessage = driver.findElement(By.xpath ("//*[@id=\"main\"]/div[1]")).getText();
        Assert.assertEquals(actualAlertMessage, expectedAlertMessage);
        System.out.println("add New Computer Record test: Pass");
    }

    @Test(priority = 2)
    public void validateNewComputerRecord(){
        //input search box
        driver.findElement(By.id("searchbox")).sendKeys("Bassey Test");
        driver.findElement(By.id("searchsubmit")).click();
        //assert returned computer record
        String expectedSearchName = "Bassey Test";
        String actualSearchName = driver.findElement(By.xpath ("//*[@id=\"main\"]/table/tbody/tr/td[1]/a")).getText();
        Assert.assertEquals(actualSearchName, expectedSearchName);
        //click Bassey Test
        driver.findElement(By.xpath("//*[@id=\"main\"]/table/tbody/tr[1]/td[1]/a")).click();
        //assert Bassey Test record
        driver.findElement(By.xpath("//*[@value='Bassey Test']")).isDisplayed();
        driver.findElement(By.xpath("//*[@value='2021-03-01']")).isDisplayed();
        driver.findElement(By.xpath("//*[@value='2022-03-01']")).isDisplayed();
        //click cancel button
        driver.findElement(By.xpath("//*[@id=\"main\"]/form[1]/div/a")).click();
        System.out.println("validate New Computer Record test: Pass");
    }

    @Test(priority = 3)
    public void updateComputerRecord(){
        //input search box
        driver.findElement(By.id("searchbox")).sendKeys("Bassey Test");
        driver.findElement(By.id("searchsubmit")).click();
        //assert returned computer record
        String expectedSearchName = "Bassey Test";
        String actualSearchName = driver.findElement(By.xpath ("//*[@id=\"main\"]/table/tbody/tr/td[1]/a")).getText();
        Assert.assertEquals(actualSearchName, expectedSearchName);
        //click Bassey Test
        driver.findElement(By.xpath("//*[@id=\"main\"]/table/tbody/tr[1]/td[1]/a")).click();
        //update Bassey Test record
        driver.findElement(By.id("name")).clear();
        driver.findElement(By.id("name")).sendKeys("Bassey MolloTest");
        driver.findElement(By.id("introduced")).clear();
        driver.findElement(By.id("introduced")).sendKeys("2022-03-01");
        driver.findElement(By.id("discontinued")).clear();
        driver.findElement(By.id("discontinued")).sendKeys("2021-03-01");
        //click Save this computer button
        driver.findElement(By.xpath("//*[@id=\"main\"]/form[1]/div/input")).click();
        //assert success message
        String expectedAlertMessage = "Done! Computer Bassey MolloTest has been updated";
        String actualAlertMessage = driver.findElement(By.xpath ("//*[@id=\"main\"]/div[1]")).getText();
        Assert.assertEquals(actualAlertMessage, expectedAlertMessage);
        System.out.println("update Computer Record test: Pass");
    }

    @Test(priority = 4)
    public void deleteComputerRecord(){
        //input search box
        driver.findElement(By.id("searchbox")).sendKeys("Bassey MolloTest");
        driver.findElement(By.id("searchsubmit")).click();
        //assert returned computer record
        String expectedSearchName = "Bassey MolloTest";
        String actualSearchName = driver.findElement(By.xpath ("//*[@id=\"main\"]/table/tbody/tr/td[1]/a")).getText();
        Assert.assertEquals(actualSearchName, expectedSearchName);
        //click Bassey MolloTest
        driver.findElement(By.xpath("//*[@id=\"main\"]/table/tbody/tr[1]/td[1]/a")).click();
        //click Delete this computer button
        driver.findElement(By.xpath("//*[@id=\"main\"]/form[2]/input")).click();
        //assert success message
        String expectedAlertMessage = "Done! Computer has been deleted";
        String actualAlertMessage = driver.findElement(By.xpath ("//*[@id=\"main\"]/div[1]")).getText();
        Assert.assertEquals(actualAlertMessage, expectedAlertMessage);
        System.out.println("delete Computer Record test: Pass");
    }

    @AfterTest
    public void closeBrowser() throws InterruptedException {
        Thread.sleep(10000);
        driver.quit();
    }
}
