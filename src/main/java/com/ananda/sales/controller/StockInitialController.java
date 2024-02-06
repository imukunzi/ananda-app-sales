package com.ananda.sales.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ananda.sales.model.Expenses;
import com.ananda.sales.model.ReportBodyDetails;
import com.ananda.sales.model.StockInitial;
import com.ananda.sales.model.StockLevel;
import com.ananda.sales.repository.StockInitialRepository;
import com.ananda.sales.repository.StockLevelRepository;
import com.ananda.sales.repository.StockRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/")
public class StockInitialController {

	@Autowired
	StockInitialRepository stockInitialRepo;
	
	@Autowired
	StockRepository stockRepo;

	@Autowired
	StockLevelRepository stockLevelRepo;

	private List<StockLevel> stockLevelList;
	
	private List<StockInitial> stockInitialList;

	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");

	@GetMapping("/createstockinitial")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public void createDailyStockInitial() {

		
		stockLevelList = new ArrayList<>();
		// get morning stock lever and save it as morning stock initial

		stockLevelList = stockLevelRepo.findAll();
		
		

		String date = formatter2.format(new Date());
		String time = formatter.format(new Date());

		for (StockLevel s : stockLevelList) {

			String code = s.getCode();
			int pid = s.getPid();
			String name = s.getName();
			String color = s.getColor();
			String size = s.getSize();
			String stockname = s.getStockname();
			// String date = date;
			int current_stock_qty = s.getCurrent_stock_qty();
			String status = s.getStatus();
			double price = s.getPrice();
			String description =s.getDescription();
			

			// check if this stock initial was saved

			if (stockInitialRepo.checkIfStockInitialExist(pid, date) == 0) {
                 double p =0.0;
				stockInitialRepo.save(new StockInitial( code,  pid,  name,  color,  size,   stockname,   date,
						  current_stock_qty,   status,   price, 0  ,   0,  time,description));
			}
			
			//get stock out
			
			if (stockRepo.getTodayStockOut(pid,date,stockname) !=null) {
				
				int qty_out =0;
				int price_out=0;
				if(stockRepo.getTodayStockOut(pid,date,stockname) !=null) {
					
					 qty_out =stockRepo.getTodayStockOut(pid,date,stockname);
					
					
				}
				
				if(stockRepo.getTodayStockOutPrice(pid,date,stockname) !=null) {
					  price_out =stockRepo.getTodayStockOutPrice(pid,date,stockname);
				}
				
				
				//System.out.println("====stock initial=====88===="+qty_out+" "+price_out+" "+pid+" "+name);
				
				//update stock_initial_qty_out
				
				int y = stockInitialRepo.updateStockInitialQtyOut(qty_out, price_out, pid, date, stockname);
				
				
				
				
				
			}
			


		}

	}
	
	@PostMapping("/getstockinitial")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<List<StockInitial>> getDailyStockInitial(@RequestBody ReportBodyDetails r) {

		String date = r.getText1();
		String stock = r.getText2();
		String description = r.getText3();
		
		System.out.println("====stock initial=====116===="+date+" "+stock+" "+description);

		try {


			return new ResponseEntity<>(stockInitialRepo.getTodayStockInitial(date, stock,description), HttpStatus.OK);

		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}
	
	@PostMapping("/getstockinitialsearch")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<List<StockInitial>> getDailyStockInitialSearch(@RequestBody ReportBodyDetails r) {

		String date = r.getText1();
		String stock = r.getText2();
		String searchText = r.getText3();

		try {
			
			return new ResponseEntity<>(stockInitialRepo.getTodayStockInitialSearch(date, stock,searchText), HttpStatus.OK);

		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}


}
