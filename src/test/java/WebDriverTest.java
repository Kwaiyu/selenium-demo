import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.numberOfWindowsToBe;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class WebDriverTest {
    WebDriver driver = new ChromeDriver();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10).getSeconds());

    @AfterTest
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testBrowserNavigation() {
        try{
            driver.get("https://www.baidu.com");
            Assert.assertEquals(driver.getTitle(), "百度一下，你就知道", "Title not match");
            Assert.assertEquals(driver.getCurrentUrl(), "https://www.baidu.com/", "Title not match");
            driver.navigate().to("https://www.google.com");
            Assert.assertEquals(driver.getTitle(), "Google", "Title not match");
            Assert.assertEquals(driver.getCurrentUrl(), "https://www.google.com.hk/", "Title not match");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testBackForwardRefresh() {
        try{
            driver.get("https://www.baidu.com");
            driver.navigate().to("https://www.google.com");
            driver.navigate().back();
            Assert.assertEquals(driver.getTitle(), "百度一下，你就知道", "Back success");
            driver.navigate().forward();
            Assert.assertEquals(driver.getTitle(), "Google", "Forward success");
            driver.navigate().refresh();
            String source = driver.getPageSource();
            System.out.println(source);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testWindowsAndTabs() {
        try{
            driver.get("https://www.baidu.com");
            String originalWindow = driver.getWindowHandle();
            System.out.println(originalWindow);
            assert driver.getWindowHandles().size() == 1;
//            driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[5]/div/div/div[1]/map/area")).click();
            driver.findElement(By.cssSelector("#lg > map > area")).click();
            wait.until(numberOfWindowsToBe(2));
            for (String windowHandle : driver.getWindowHandles()){
                if(!originalWindow.contentEquals(windowHandle)){
                    driver.switchTo().window(windowHandle);
                    break;
                }
            }
            wait.until(titleIs("百度热搜_百度搜索"));
//            Assert.assertEquals(driver.getTitle(), "百度热搜_百度搜索", "Title not match!");
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            Assert.assertEquals(driver.getTitle(), "百度热搜_百度搜索", "Title not match!");
        }
        /*
        此功能适用于 Selenium 4 及更高版本。
         */
//        // Opens a new tab and switches to new tab
//        driver.switchTo().newWindow(WindowType.TAB);
//        // Opens a new window and switches to new window
//        driver.switchTo().newWindow(WindowType.WINDOW);
    }

    /**
     *  <div id="modal">
     *   <iframe id="buttonframe" name="myframe"  src="https://seleniumhq.github.io">
     *    <button>Click here</button>
     *  </iframe>
     *  </div>
     *  如果有按钮切换到iframe:
     *  driver.findElement(By.tagName("button")).click();
     *  A:如果没有按钮切换到iframe，使用WebElement切换：
     *  //Store the web element
     *  WebElement iframe = driver.findElement(By.cssSelector("#modal>iframe"));
     *  //Switch to the frame
     *  driver.switchTo().frame(iframe);
     *  //Now we can click the button
     *  driver.findElement(By.tagName("button")).click();
     *  B:使用name或者id切换：
     *  //Using the ID
     * driver.switchTo().frame("buttonframe");
     * //Or using the name instead
     * driver.switchTo().frame("myframe");
     * //Now we can click the button
     * driver.findElement(By.tagName("button")).click();
     *  C:使用index切换：
     *  // Switches to the second frame
     * driver.switchTo().frame(1);
     *
     * 离开frame
     * // Return to the top level
     * driver.switchTo().defaultContent();
     */
    @Test
    public void testFramesAndIframes() {
        try{
            driver.get("https://csreis.github.io/tests/cross-site-iframe.html");
            driver.findElement(By.tagName("navFrame('https://build.chromium.org')")).click();
        }finally {

        }
    }
}