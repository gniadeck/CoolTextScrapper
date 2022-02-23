# CoolTextScrapper
Selenium based scrapper that enables you to generate graphics from cooltext.com right from your Java Application

# Usage

You can use this scrapper as a mini-API to generate graphics from cooltext.com The URLs of templates that the software is going to use are located in URLs.txt file in maven's resources folder. The most important code is put in [CoolTextScrapper.java](https://github.com/komp15/CoolTextScrapper/blob/main/CoolTextScrapper/src/main/java/scrapper/CoolTextScrapper.java) file. From this class you can access some methods, that will provide you with an image with your text, generated from random template selected from URLs.txt.

Sample code to generate image with provided text
```java
		//initialization
		CoolTextScrapper coolTextScrapper = new CoolTextScrapper();
		System.out.println(coolTextScrapper.getAvailableURLs());
		
		
		//generate and save to /png path inside the projects root folder
		Random random = new Random();
		
		for(int i = 0; i < 5; i++) {
			coolTextScrapper.getRandomLogoAndSaveFileAsPNG(String.valueOf(random.nextInt(1000)));
		}

```
Code to generate image using provided template
```java
		//initialization
		CoolTextScrapper coolTextScrapper = new CoolTextScrapper();
		System.out.println(coolTextScrapper.getAvailableURLs());
		
    		//generate buffered image from specified url, then save to /png path inside the projects root folder
		BufferedImage test = coolTextScrapper.getRandomLogo("test", "https://cooltext.com/Logo-Design-Fire");

		ImageIO.write(test, "png", new File("png/test.png"));
		
		System.out.println("Image written.");

```



If you have any suggestions, please open issues or pull requests.
