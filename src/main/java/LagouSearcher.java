import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

/**
 * @program: lagou-spider
 * @description: 拉勾网爬虫
 * @author: 闲乘月
 * @create: 2020-02-25 10:19
 **/
public class LagouSearcher {
    public static void main(String[] args) {
        //设置webdriver路径
        String path = LagouSearcher.class.getClassLoader().getResource("chromedriver.exe").getPath();
        System.setProperty("webdriver.chrome.driver", path);

        //创建webDriver
        WebDriver webDriver=new ChromeDriver();
        webDriver.get("https://www.lagou.com/zhaopin/Java/?labelWords=label");


        //获取页面元素并操作

        //点击跳过广告
        WebElement chosenElementad = webDriver.findElement(By.xpath("//div[@class='body-container showData']//div[contains(text(),'给也不要')]"));
        chosenElementad.click();



        //操作业务

        clickOption(webDriver, "工作经验", "不限");
        clickOption(webDriver, "学历要求", "本科");
        clickOption(webDriver, "融资阶段", "不限");
        clickOption(webDriver, "公司规模", "不限");
        clickOption(webDriver, "行业领域", "移动互联网");
        getJobByPage(webDriver);


    }

    private static void getJobByPage(WebDriver webDriver) {
        //解析页面元素

        List<WebElement> jobElements = webDriver.findElements(By.className("con_list_item"));
        for (WebElement jobElement : jobElements) {
            WebElement moneyElement = jobElement.findElement(By.className("position")).findElement(By.className("money"));
            String companyName = jobElement.findElement(By.className("company_name")).getText();
            System.out.println(companyName+" : "+moneyElement.getText());
        }

        WebElement next_pageBtn = webDriver.findElement(By.className("pager_next"));
        if (!next_pageBtn.getAttribute("class").contains("pager_next_disabled")){
            next_pageBtn.click();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            getJobByPage(webDriver);
        }
    }

    private static void clickOption(WebDriver webDriver, String choseTitle, String optinTitle) {
        WebElement chosenElement = webDriver.findElement(By.xpath("//li[@class='multi-chosen']//span[contains(text(),'" + choseTitle + "')]"));
        WebElement optionElement = chosenElement.findElement(By.xpath("../a[contains(text(),'" + optinTitle + "')]"));
        optionElement.click();
    }
}
