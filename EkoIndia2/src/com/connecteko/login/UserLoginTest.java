package com.connecteko.login;

import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UserLoginTest {
	
static WebDriver driver;
	
	public static void main(String[] args) throws InterruptedException {
		
		System.setProperty("webdriver.chrome.driver", "Driver/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		driver.get("https://connectbeta.eko.co.in/");
		
		String username = "beta061117";
		String password = "Beta@123";
		
		
		Thread.sleep(3000);
		
		//Get shadow root element
		wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("tf-connect")));
		WebElement root1 = driver.findElement(By.tagName("tf-connect"));
		WebElement shadowRoot1 = expandRootElement(root1);
		WebElement toggleButton = shadowRoot1.findElement(By.id("btnNightMode"));
		wait.until(ExpectedConditions.visibilityOf(toggleButton));
		
		//Get inner shadow root element using shadow root1
		WebElement root2 = shadowRoot1.findElement(By.id("logincard"));
		WebElement shadowRoot2 = expandRootElement(root2);
		
		//finding element in shadow root2
		WebElement loginButton = shadowRoot2.findElement(By.id("google_signin"));
		wait.until(ExpectedConditions.visibilityOf(loginButton));
		loginButton.click();
		
		//Switching to Child Window here
		String connectEko_window = driver.getWindowHandle();
		Set<String> windows = driver.getWindowHandles();
		Iterator<String> itr = windows.iterator();
		String googleLogin_window = null;
		while(itr.hasNext()){
			googleLogin_window = itr.next();
			if(!connectEko_window.equalsIgnoreCase(googleLogin_window)){
				driver.switchTo().window(googleLogin_window);
							}
		}
	
		
		//code for entering username and password
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("identifierId")));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("identifierNext")));
		driver.findElement(By.id("identifierId")).sendKeys(username);
		driver.findElement(By.id("identifierNext")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
		driver.findElement(By.name("password")).sendKeys(password);
		driver.findElement(By.xpath("//*[@id='passwordNext']/content")).click();
		driver.switchTo().window(connectEko_window);
		
		
	}

	//Returns webelement
		public static WebElement expandRootElement(WebElement element) {
			WebElement ele = (WebElement) ((JavascriptExecutor) driver).executeScript("return arguments[0].shadowRoot",element);
			return ele;
		}
}


