package com.ananda.sales.controller;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.ananda.sales.model.Cashing;
import com.ananda.sales.model.ReportBodyDetails;

import com.ananda.sales.repository.CashingRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/")
public class CashingController {

	@Autowired
	CashingRepository cashingRepo;

	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private DecimalFormat decimalFormatter = new DecimalFormat("#,###.00");

	@PostMapping("/cashinglist")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<List<Cashing>> getAllCashing(@RequestBody ReportBodyDetails r) {

		try {

			String date1 = r.getText1();
			String date2 = r.getText2();
			String cashier = r.getText3();

			List<Cashing> list = cashingRepo.findBycashierContaining(date1, date2, cashier);

			return new ResponseEntity<>(list, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("+++++++++++++++++++++++++++++++++++++" + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/cashingvalidation")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<String> ValidateCashing(@RequestBody Cashing cashing) {

		try {

			String date = formatter.format(new Date());
			String cashier = cashing.getCashier();
			double amount = cashing.getAmount();
			String amount2 = cashing.getAmount2();
			String headCashier = cashing.getHeadCashier();
			double headCashierAmount = cashing.getHeadCashierAmount();
			String comment = cashing.getComment();
			double fiveThousands = cashing.getFiveThousands();
			double twoThousands = cashing.getTwoThousands();
			double oneThousands = cashing.getOneThousands();
			double fiveHundred = cashing.getFiveHundred();
			double oneHundred = cashing.getOnehundred();
			double fifty = cashing.getFifty();
			double twenty = cashing.getTwenty();
			double ten = cashing.getTen();
			double five = cashing.getFive();
			double two = cashing.getTwo();
			double one = cashing.getOne();

			double euro = cashing.getEuro();
			double euroRate = cashing.getEuroRate();
			double euro2 = cashing.getEuro2();
			double euroRate2 = cashing.getEuroRate2();
			double euro3 = cashing.getEuro3();
			double euroRate3 = cashing.getEuroRate3();
			double euro4 = cashing.getEuro4();
			double euroRate4 = cashing.getEuroRate4();

			double dollar = cashing.getDollar();
			double dollarRate = cashing.getDollarRate();

			double dollar2 = cashing.getDollar2();
			double dollarRate2 = cashing.getDollarRate2();
			double dollar3 = cashing.getDollar3();
			double dollarRate3 = cashing.getDollarRate3();
			double dollar4 = cashing.getDollar4();
			double dollarRate4 = cashing.getDollarRate4();
			double caisseInit = cashing.getCaisseInit();
			String pointerName = cashing.getPointerName();
			double pointerAmount = cashing.getPointerAmount();
			String receivedB = cashing.getReceivedBy();

			System.out.println("====cashing====123====" + caisseInit);

			double totalCashing = fiveThousands * 5000 + twoThousands * 2000 + oneThousands * 1000 + fiveHundred * 500
					+ oneHundred * 100 + fifty * 50 + twenty * 20 + ten * 10 + five * 5 + two * 2 + one * 1;

			// Cashing c = cashingRepo.save(new Cashing(date, cashier, amount, amount2,
			// headCashier, headCashierAmount, comment, fiveThousands, twoThousands,
			// oneThousands, fiveHundred, oneHundred, fifty, twenty, ten,five,two,one, euro,
			// euroRate, euro2, euroRate2, euro3, euroRate3, euro4, euroRate4, dollar,
			// dollarRate, dollar2, dollarRate2, dollar3, dollarRate3, dollar4, dollarRate4,
			// caisseInit, pointerName, pointerAmount, receivedB));

			return new ResponseEntity<>(decimalFormatter.format(totalCashing), HttpStatus.CREATED);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@PostMapping("/cashing")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<String> saveProducts(@RequestBody Cashing cashing) {

		try {

			String date = formatter.format(new Date());
			String cashier = cashing.getCashier();
			double amount = cashing.getAmount();
			String amount2 = cashing.getAmount2();
			String headCashier = cashing.getHeadCashier();
			double headCashierAmount = cashing.getHeadCashierAmount();
			String comment = cashing.getComment();
			double fiveThousands = cashing.getFiveThousands();
			double twoThousands = cashing.getTwoThousands();
			double oneThousands = cashing.getOneThousands();
			double fiveHundred = cashing.getFiveHundred();
			double oneHundred = cashing.getOnehundred();
			double fifty = cashing.getFifty();
			double twenty = cashing.getTwenty();
			double ten = cashing.getTen();
			double five = cashing.getFive();
			double two = cashing.getTwo();
			double one = cashing.getOne();

			double euro = cashing.getEuro();
			double euroRate = cashing.getEuroRate();
			double euro2 = cashing.getEuro2();
			double euroRate2 = cashing.getEuroRate2();
			double euro3 = cashing.getEuro3();
			double euroRate3 = cashing.getEuroRate3();
			double euro4 = cashing.getEuro4();
			double euroRate4 = cashing.getEuroRate4();

			double dollar = cashing.getDollar();
			double dollarRate = cashing.getDollarRate();

			double dollar2 = cashing.getDollar2();
			double dollarRate2 = cashing.getDollarRate2();
			double dollar3 = cashing.getDollar3();
			double dollarRate3 = cashing.getDollarRate3();
			double dollar4 = cashing.getDollar4();
			double dollarRate4 = cashing.getDollarRate4();
			double caisseInit = cashing.getCaisseInit();
			String pointerName = cashing.getPointerName();
			double pointerAmount = cashing.getPointerAmount();
			String receivedB = cashing.getReceivedBy();

			double totalCashing = fiveThousands * 5000 + twoThousands * 2000 + oneThousands * 1000 + fiveHundred * 500
					+ oneHundred * 100 + fifty * 50 + twenty * 20 + ten * 10 + five * 5 + two * 2 + one * 1;

			Cashing c = cashingRepo.save(new Cashing(date, cashier, totalCashing, amount2, headCashier,
					headCashierAmount, comment, fiveThousands, twoThousands, oneThousands, fiveHundred, oneHundred,
					fifty, twenty, ten, five, two, one, euro, euroRate, euro2, euroRate2, euro3, euroRate3, euro4,
					euroRate4, dollar, dollarRate, dollar2, dollarRate2, dollar3, dollarRate3, dollar4, dollarRate4,
					caisseInit, pointerName, pointerAmount, receivedB));

			String response = "Saved";

			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@GetMapping("/cashing/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Cashing> getCashing(@PathVariable("id") long id) {

		Optional<Cashing> data = cashingRepo.findById(id);

		if (data.isPresent()) {
			return new ResponseEntity<>(data.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@PutMapping("/cashing/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Cashing> updateCashing(@PathVariable("id") long id, @RequestBody Cashing cashing) {

		Optional<Cashing> data = cashingRepo.findById(id);

		if (data.isPresent()) {

			Cashing s = data.get();

			s.setDate(cashing.getDate());
			s.setCashier(cashing.getCashier());
			s.setAmount(cashing.getAmount());
			s.setAmount2(cashing.getAmount2());
			s.setHeadCashier(cashing.getHeadCashier());
			s.setHeadCashierAmount(cashing.getHeadCashierAmount());
			s.setComment(cashing.getComment());
			s.setFiveThousands(cashing.getFiveThousands());
			s.setTwoThousands(cashing.getTwoThousands());
			s.setOneThousands(cashing.getOneThousands());
			s.setFiveHundred(cashing.getFiveHundred());
			s.setOnehundred(cashing.getOnehundred());
			s.setFifty(cashing.getFifty());
			s.setTwenty(cashing.getTwenty());
			s.setTen(cashing.getTen());
			s.setFive(cashing.getFive());
			s.setTwo(cashing.getTwo());
			s.setOne(cashing.getOne());

			s.setEuro(cashing.getEuro());
			s.setEuroRate(cashing.getEuroRate());
			s.setEuro2(cashing.getEuro2());
			s.setEuroRate2(cashing.getEuroRate2());
			s.setEuro3(cashing.getEuro3());
			s.setEuroRate3(cashing.getEuroRate3());
			s.setEuro4(cashing.getEuro4());
			s.setEuroRate4(cashing.getEuroRate4());

			s.setDollar(cashing.getDollar());
			s.setDollarRate(cashing.getDollarRate());

			s.setDollar2(cashing.getDollar2());
			s.setDollarRate2(cashing.getDollarRate2());
			s.setDollar3(cashing.getDollar3());
			s.setDollarRate3(cashing.getDollarRate3());
			s.setDollar4(cashing.getDollar4());
			s.setDollarRate4(cashing.getDollarRate4());
			s.setCaisseInit(cashing.getCaisseInit());
			s.setPointerName(cashing.getPointerName());
			s.setPointerAmount(cashing.getPointerAmount());
			s.setReceivedBy(cashing.getReceivedBy());

			return new ResponseEntity<>(cashingRepo.save(s), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@DeleteMapping("/cashing/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<HttpStatus> delete(@PathVariable("id") long id) {

		try {
			cashingRepo.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/cashingconfirmation")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Map<String, Object>> confirmCashing(@RequestBody ReportBodyDetails r) {

		try {

			long id = Long.valueOf(r.getText1());
			String amount = r.getText2();
			String comment = r.getText3();
			String receivedBy = r.getText4();
			String headCashier = r.getText5();			
			
			int x = cashingRepo.confirmCashing(amount, comment, receivedBy,headCashier, id);

			

			Map<String, Object> response = new HashMap<>();
			
			
			if (x == 1) {
				String message = "Done successfully";
				response.put("message", message);

				return new ResponseEntity<>(response, HttpStatus.OK);

			}else {
				
				String message = "Error occured.";
				response.put("message", message);

				return new ResponseEntity<>(response, HttpStatus.OK);
				
			}
			

		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

}
