package com.ananda.sales;


import java.util.Timer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//ANANDA SALES
@SpringBootApplication
public class Application {
	

	public static void main(String[] args) {
		
		SpringApplication.run(Application.class, args);  
		
		//start and monitor react backend
		Timer timer = new Timer();
		//timer.schedule(new BackEndTimer(), 0, 10000); //5000=>5sec
		
						
	}
	
	
	
		
	
}
