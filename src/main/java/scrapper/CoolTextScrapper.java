package scrapper;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import io.github.bonigarcia.wdm.WebDriverManager;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.Request;
import okhttp3.Response;


public class CoolTextScrapper {
	
	private ArrayList<String> availableURLs;
	private Random generator;
	private WebDriver driver;
	
	public CoolTextScrapper() throws FileNotFoundException, IOException {
		
		this.generator = new Random();
		this.availableURLs = new ArrayList<String>();
		
		
		
		WebDriverManager.chromedriver().browserVersion("77.0.3865.40").setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized"); 
		options.addArguments("enable-automation"); 
		options.addArguments("--no-sandbox"); 
		options.addArguments("--disable-infobars");
		options.addArguments("--disable-dev-shm-usage");
		options.addArguments("--disable-browser-side-navigation"); 
		options.addArguments("--disable-gpu"); 
		options.addArguments("--headless");
		driver = new ChromeDriver(options);
		
	}
	
	
	
	

	public  boolean getRandomLogoAndSaveToFileAsJPG(String text, File file) throws Exception {
		
		BufferedImage img = getRandomLogo(text);
		
		ImageIO.write(img, "jpg", file);
		
		
		return true;
	}
	
	public  boolean getRandomLogoAndSaveToFileAsPNG(String text, File file) throws Exception {
		
		BufferedImage img = getRandomLogo(text);
		
		ImageIO.write(img, "png", file);
		
		
		return true;
	}
	
	
	public boolean getRandomLogoAndSaveFileAsGIF(String text) throws Exception {
		
		BufferedImage img = getRandomLogo(text);
		File file = new File("gif/");
		file.mkdir();		
		ImageIO.write(img, "gif", new File("gif/" + text + ".gif"));
		
		
		return true;
	}
	
	
	public  boolean getRandomLogoAndSaveFileAsPNG(String text) throws Exception {
		
		BufferedImage img = getRandomLogo(text);
		File file = new File("png/");
		file.mkdir();
		
		ImageIO.write(img, "png", new File("png/" + text + ".png"));
		
		
		return true;
	}
	
	
	public BufferedImage getRandomLogo(String text) throws Exception {
		
		//check if file was already loaded
		if(this.availableURLs.size() == 0) {
		try (BufferedReader br = new BufferedReader(new FileReader(CoolTextScrapper.class.getClassLoader().getResource("URLs.txt").getFile()))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		       
		    	this.availableURLs.add(line);
		    	
		    }
		} catch(FileNotFoundException e) {
			throw new Exception("URLs.txt not found. Please provide the file or add link parameter to getRandomLogo()");

		}
		}
		
 
		
		FluentWait wait = new FluentWait(driver);
		

	    String url = this.getAvailableURLs().get(this.generator.nextInt(this.getAvailableURLs().size()));
	    driver.get(url);

	    List<WebElement> srcs;

	    srcs = driver.findElements(By.xpath("/html/body/table/tbody/tr[2]/td[2]/div[1]/div/form/div[1]/table/tbody/tr[1]/td[2]/textarea"));
	    
	    srcs.get(0).clear();
	    srcs.get(0).sendKeys(text);
	    //wait for the preview to load
	    Thread.sleep(5000);
	    WebElement submitButton = driver.findElement(By.xpath("/html/body/table/tbody/tr[2]/td[2]/center[2]/input"));

	    submitButton.click();
	    Thread.sleep(2500);
	    System.out.println("...");
	    WebElement downloadButton = driver.findElement(By.xpath("/html/body/table/tbody/tr[2]/td[2]/center[1]/p[1]/a[1]"));

	    
	   String downloadURL =  downloadButton.getAttribute("href");
	   System.out.println(downloadURL);


	    

		return download(downloadURL);
	}
	
	public BufferedImage getRandomLogo(String text, String url) throws Exception {
		
 
		
		FluentWait wait = new FluentWait(driver);
		

	    driver.get(url);

	    List<WebElement> srcs;

	    srcs = driver.findElements(By.xpath("/html/body/table/tbody/tr[2]/td[2]/div[1]/div/form/div[1]/table/tbody/tr[1]/td[2]/textarea"));
	    
	    srcs.get(0).clear();
	    srcs.get(0).sendKeys(text);
	    //wait for the preview to load
	    Thread.sleep(5000);
	    WebElement submitButton = driver.findElement(By.xpath("/html/body/table/tbody/tr[2]/td[2]/center[2]/input"));

	    submitButton.click();
	    Thread.sleep(2500);
	    System.out.println("...");
	    WebElement downloadButton = driver.findElement(By.xpath("/html/body/table/tbody/tr[2]/td[2]/center[1]/p[1]/a[1]"));

	    
	   String downloadURL =  downloadButton.getAttribute("href");
	   System.out.println(downloadURL);


	    

		return download(downloadURL);
	}
	

	  public  BufferedImage download(String url) throws Exception {
		  
		  OkHttpClient client = new OkHttpClient();
		  BufferedImage img;
		  
		  url = url.replace("https", "http");
		  System.out.println("Starting downloading " + url);
		  
		    Request request = new Request.Builder()
		        .url(url)
		        .build();

		    try (Response response = client.newCall(request).execute()) {
		      if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
		      
		      
		      img = ImageIO.read(response.body().byteStream());
		      
		    }
		    System.out.println("Downloaded " + url);
		    return img;
		    
		  }



	public ArrayList<String> getAvailableURLs() {
		return availableURLs;
	}



	public void setAvailableURLs(ArrayList<String> availableURLs) {
		this.availableURLs = availableURLs;
	}
	
	@Override
	public void finalize() throws IOException {
	    driver.close();
	    System.out.println("Closed Chrome Driver.");
	}
	

}
