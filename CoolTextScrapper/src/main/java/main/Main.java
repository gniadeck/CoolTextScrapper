package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import scrapper.CoolTextScrapper;

public class Main {

	public static void main(String[] args) throws Exception {
		
		
		CoolTextScrapper coolTextScrapper = new CoolTextScrapper();
		System.out.println(coolTextScrapper.getAvailableURLs());
		Random random = new Random();
		
		for(int i = 0; i < 20; i++) {
			coolTextScrapper.getRandomLogoAndSaveFileAsPNG(String.valueOf(random.nextInt(1000)));
		}
		
		
		

	}

}
