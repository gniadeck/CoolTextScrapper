package main;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import scrapper.CoolTextScrapper;

public class Main {
	
	//demo class
	
	public static void main(String[] args) throws Exception {
		
		
		
		
		//initialization
		CoolTextScrapper coolTextScrapper = new CoolTextScrapper();
		System.out.println(coolTextScrapper.getAvailableURLs());
		
		
		//generate and save to /png path inside the projects root folder
		Random random = new Random();
		
		for(int i = 0; i < 5; i++) {
			coolTextScrapper.getRandomLogoAndSaveFileAsPNG(String.valueOf(random.nextInt(1000)));
		}
		
		
		//generate buffered image from specified url, then save to /png path inside the projects root folder
		BufferedImage test = coolTextScrapper.getRandomLogo("test", "https://cooltext.com/Logo-Design-Fire");

		ImageIO.write(test, "png", new File("png/test.png"));
		
		System.out.println("Image written.");
		
		
		
		

	}

}
