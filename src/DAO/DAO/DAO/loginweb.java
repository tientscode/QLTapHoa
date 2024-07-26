package DAO.DAO.DAO;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class loginweb {
	WebDriver driver;
//	String driverPath = "D:\\java_web\\poly\\chromedriver-win64\\chromedriver.exe";

	@Test
	public void setup() throws InterruptedException {
		// Khởi tạo trình duyệt Chrome
		System.setProperty("webdriver.chrome.driver", "D:\\java_web\\poly\\chromedriver-win64\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://hoanghamobile.com/");
		Thread.sleep(3000);

		WebElement close = driver.findElement(By.xpath("//*[@id=\"popup-modal\"]/a[2]"));
		close.click();
		Thread.sleep(4000);

		WebElement loginButton = driver.findElement(By.xpath("//*[@id=\"login-modal\"]"));
		loginButton.click();
		Thread.sleep(5000);
		WebElement usernameInput = driver.findElement(By.xpath("//*[@id=\"UserName\"]"));
		WebElement passwordInput = driver.findElement(By.xpath("//*[@id=\"password\"]"));
		usernameInput.sendKeys("tien124346");//tien124346
		Thread.sleep(3000);
		passwordInput.sendKeys("606670"); //606670
		Thread.sleep(3000);
		WebElement a = driver.findElement(By.xpath("/html/body/div[9]/div/div/div[2]/div[3]/form/div[4]/div/button"));
		a.click();
		Thread.sleep(5000);
		

	}

}
