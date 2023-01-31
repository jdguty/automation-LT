package com.qa.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Test1 {

	public WebDriver driver;
	public static String NameItem = null;
	
	@BeforeAll
	public void start() {
		System.setProperty("webdriver.edge.driver","./src/test/resources/edgedriver/msedgedriver.exe");	
		driver = new EdgeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(40));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		
		driver.get("https://www.saucedemo.com/");

	}

	
	@Test
	public void Login() {
		
		WebElement userinput = driver.findElement(By.id("user-name"));
		userinput.clear();
		userinput.sendKeys("standard_user");
		
		WebElement passinput = driver.findElement(By.id("password"));
		passinput.clear();
		passinput.sendKeys("secret_sauce");
		passinput.submit();
		
		WebElement products = driver.findElement(By.xpath("//*[@id=\"header_container\"]/div[2]/span"));
		
		assertEquals("PRODUCTS", products.getText());
	}
	
	@Test
	public void AddItem() {
		WebElement item = driver.findElement(By.xpath("//*[@id=\"item_4_title_link\"]/div"));
		NameItem = item.getText();
		item.click();
		
		driver.findElement(By.xpath("//*[@id=\"add-to-cart-sauce-labs-backpack\"]")).click();
		
		driver.findElement(By.xpath("//*[@id=\"shopping_cart_container\"]/a")).click();
		
		WebElement buyitem = driver.findElement(By.xpath("//*[@id=\"item_4_title_link\"]/div"));
		String NameItemBuy = buyitem.getText();
		
		assertEquals(NameItem, NameItemBuy);

	}
	
	@Test
	public void Checkout() {
		driver.findElement(By.xpath("//*[@id=\"checkout\"]")).click();
		
		driver.findElement(By.id("first-name")).sendKeys("Juan");
		driver.findElement(By.id("last-name")).sendKeys("Gutierrez");
		driver.findElement(By.id("postal-code")).sendKeys("123456");
		driver.findElement(By.id("continue")).click();
		
		WebElement checkoutitem = driver.findElement(By.xpath("//*[@id=\"item_4_title_link\"]/div"));
		
		assertEquals(NameItem, checkoutitem.getText());
	}
	
	@Test
	public void Finish() {
		driver.findElement(By.id("finish")).click();
		WebElement finish = driver.findElement(By.xpath("//*[@id=\"header_container\"]/div[2]/span"));
		
		assertEquals("CHECKOUT: COMPLETE!", finish.getText());
		
	}
	
	@AfterAll
	public void exit() {
		driver.quit();
	}
	
}
