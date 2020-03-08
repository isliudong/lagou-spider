import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


/**
 * @program: be quick
 * @description:
 * @author: 闲乘月
 * @create: 2020-03-07 11:18
 **/
public class School2 {
    public static void main(String[] args) {
        MailOp mail = new MailOp();
        boolean state=false;

        String path = LagouSearcher.class.getClassLoader().getResource("chromedriver.exe").getPath();
        path=mail.getPath()+"geckodriver";
        System.setProperty("webdriver.firefox.bin", path);
        System.out.println(path);
        WebDriver webDriver = new FirefoxDriver();

        webDriver.get("http://xg.swpu.edu.cn/SPCP/Web/Account");
        String account = "201731062227";
        String password = "148817";

        login(webDriver, account, password);

        WebElement chosenElementad = webDriver.findElement(By.xpath("//div[@id='platfrom2']//a"));

        chosenElementad.click();

        String tips = "tips";

        try {
            tips = webDriver.findElement(By.xpath("//div[@class='layui-layer-content layui-layer-paddin']")).getText();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println(tips);

        try {
            String date = webDriver.findElement(By.xpath("//*[@id=\"RegisterDate\"]")).getAttribute("value");
            webDriver.findElement(By.xpath("//*[@id=\"SaveBtnDiv\"]/button")).click();
            System.out.println("登记成功 时间：" + date);
            state=true;

        } finally {
            if (state) {
                mail.send("2351036454@qq.com", "486545887@qq.com", "又是元气满满的一天", "防疫登记成功", "D:\\lagou-spider\\src\\main\\resources\\心情童年.jpg");
            }
            else {
                mail.send("2351036454@qq.com", "486545887@qq.com", "完蛋", "防疫登记失败了", "D:\\lagou-spider\\src\\main\\resources\\心情童年.jpg");
            }
            webDriver.quit();
        }


    }

    private static void login(WebDriver webDriver, String account, String password) {
        WebElement idElement = webDriver.findElement(By.id("StudentId"));
        WebElement passwordElement = webDriver.findElement(By.id("Name"));
        WebElement codeInputElement = webDriver.findElement(By.id("codeInput"));
        WebElement SubmitElement = webDriver.findElement(By.id("Submit"));
        String code = webDriver.findElement(By.id("code-box")).getText();


        idElement.sendKeys(account);

        passwordElement.sendKeys(password);
        codeInputElement.sendKeys(code);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SubmitElement.click();
    }

}
