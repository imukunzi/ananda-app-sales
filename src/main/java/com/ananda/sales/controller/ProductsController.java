package com.ananda.sales.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ananda.sales.file.message.ResponseFile;
import com.ananda.sales.file.message.ResponseMessage;
import com.ananda.sales.file.service.FileStorageService;
import com.ananda.sales.model.FileDB;
import com.ananda.sales.model.Products;
import com.ananda.sales.model.ReportBodyDetails;
import com.ananda.sales.model.StockLevel;
import com.ananda.sales.repository.ProductsRepository;
import com.ananda.sales.repository.StockLevelRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/")
public class ProductsController {

	@Autowired
	private FileStorageService storageService;

	@Autowired
	private ProductsRepository productRepository;

	@Autowired
	private StockLevelRepository stockLevelRepository;

	private int avairableQty = 0;

	private Sort.Direction getSortDirection(String direction) {

		if (direction.equals("asc")) {
			return Sort.Direction.ASC;
		} else if (direction.equals("desc")) {
			return Sort.Direction.DESC;
		}

		return Sort.Direction.ASC;

	}

	@GetMapping("/products")
	@PreAuthorize("hasRole('ROLE_PRODUCTS')")
	public ResponseEntity<Map<String, Object>> getAllProductsPage(@RequestParam(required = false) String code,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "50") int size,
			@RequestParam(defaultValue = "id,desc") String[] sort) {

		try {
			List<Order> orders = new ArrayList<Order>();

			if (sort[0].contains(",")) {
				// will sort more than 2 fields
				// sortOrder="field, direction"
				for (String sortOrder : sort) {
					String[] _sort = sortOrder.split(",");
					orders.add(new Order(getSortDirection(_sort[1]), _sort[0]));
				}
			} else {
				// sort=[field, direction]
				orders.add(new Order(getSortDirection(sort[1]), sort[0]));
			}

			List<Products> products = new ArrayList<Products>();
			Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));

			Page<Products> pageTuts;
			if (code == null)
				pageTuts = productRepository.findAll(pagingSort);
			else
				pageTuts = productRepository.findByCodeContaining(code, pagingSort);

			products = pageTuts.getContent();

			Map<String, Object> response = new HashMap<>();
			response.put("tutorials", products);
			response.put("currentPage", pageTuts.getNumber());
			response.put("totalItems", pageTuts.getTotalElements());
			response.put("totalPages", pageTuts.getTotalPages());

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("+++++++++++++++++++++++++++++++++++++" + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@PostMapping("/productscanned")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Map<String, Object>> getAllProductscanned(@RequestBody ReportBodyDetails r) {

		try {
			List<Order> orders = new ArrayList<Order>();

		   String code = r.getText1();
		 
			Products products = productRepository.scanProduct(code);				 

			Map<String, Object> response = new HashMap<>();
			response.put("tutorials", products);
			
			System.out.println("++++++++++++++++++++++product scanner 137 +++++++++++++++" +code + " "+products);
			 
			 
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("+++++++++++++++++++++++++++++++++++++" + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@GetMapping("/productsearch")
	@PreAuthorize("hasRole('ROLE_PRODUCTS')")
	public ResponseEntity<Map<String, Object>> getAllProductSearch(@RequestParam(required = false) String code,@RequestParam(required = false) String type,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "50") int size,
			@RequestParam(defaultValue = "id,desc") String[] sort) {

		try {
			List<Order> orders = new ArrayList<Order>();

			if (sort[0].contains(",")) {
				// will sort more than 2 fields
				// sortOrder="field, direction"
				for (String sortOrder : sort) {
					String[] _sort = sortOrder.split(",");
					orders.add(new Order(getSortDirection(_sort[1]), _sort[0]));
				}
			} else {
				// sort=[field, direction]
				orders.add(new Order(getSortDirection(sort[1]), sort[0]));
			}

			List<Products> products = new ArrayList<Products>();
			Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));

			Page<Products> pageTuts;
			if (code == null) {
				pageTuts = productRepository.findAll(pagingSort);
			}
			else {
				
				if(type.equals("code")) {
					
					pageTuts = productRepository.findByCodeContaining(code, pagingSort);
					
				}else {
					pageTuts = productRepository.findByNameContaining(code, pagingSort);
				}
				
				
			}

			products = pageTuts.getContent();

			Map<String, Object> response = new HashMap<>();
			response.put("tutorials", products);
			response.put("currentPage", pageTuts.getNumber());
			response.put("totalItems", pageTuts.getTotalElements());
			response.put("totalPages", pageTuts.getTotalPages());

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("+++++++++++++++++++++++++++++++++++++" + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/productssearchwithname")
	@PreAuthorize("hasRole('ROLE_PRODUCTS')")
	public ResponseEntity<Map<String, Object>> getAllProductsPageSearchByName(
			@RequestParam(required = false) String code, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "50") int size, @RequestParam(defaultValue = "id,desc") String[] sort) {

		try {
			List<Order> orders = new ArrayList<Order>();

			if (sort[0].contains(",")) {
				// will sort more than 2 fields
				// sortOrder="field, direction"
				for (String sortOrder : sort) {
					String[] _sort = sortOrder.split(",");
					orders.add(new Order(getSortDirection(_sort[1]), _sort[0]));
				}
			} else {
				// sort=[field, direction]
				orders.add(new Order(getSortDirection(sort[1]), sort[0]));
			}

			List<Products> products = new ArrayList<Products>();
			Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));

			Page<Products> pageTuts;
			if (code == null)
				pageTuts = productRepository.findAll(pagingSort);
			else
				pageTuts = productRepository.findByNameContaining(code, pagingSort);

			products = pageTuts.getContent();

			Map<String, Object> response = new HashMap<>();
			response.put("tutorials", products);
			response.put("currentPage", pageTuts.getNumber());
			response.put("totalItems", pageTuts.getTotalElements());
			response.put("totalPages", pageTuts.getTotalPages());

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("+++++++++++++++++++++++++++++++++++++" + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/products")
	@PreAuthorize("hasRole('ROLE_PRODUCTS')")
	public ResponseEntity<String> saveProducts(@RequestBody Products product) {

		try {

			// check if product exist

			int count = productRepository.checkIfProductExist(product.getName(), product.getCode(), product.getSize(),
					product.getColor());

			// System.out.println("====118==="+count);

			if (count > 0) {

				return new ResponseEntity<>("This product exist!", HttpStatus.CREATED);

			} else {

				Products p = productRepository.save(new Products(product.getCode(), product.getName(),
						product.getDescription(), product.getMin_price(), product.getMax_price(),product.getSuply_price(), product.getColor(),
						product.getSize(), product.getRemark(), product.getUsername(), product.getGoldenStand(),
						product.getClassicStand(), product.getBusinesStand(), product.getEconomicStand(),
						product.getKidStand(), product.getShoesStand(), product.getWatchStand(),
						product.getWomenStand(), "Pending"));

				return new ResponseEntity<>("Product saved.", HttpStatus.CREATED);

			}
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@GetMapping("/product/{id}")
	@PreAuthorize("hasRole('ROLE_PRODUCTS')")
	public ResponseEntity<Products> getProduct(@PathVariable("id") long id) {

		Optional<Products> productData = productRepository.findById(id);

		if (productData.isPresent()) {
			return new ResponseEntity<>(productData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@PutMapping("/product/{id}")
	@PreAuthorize("hasRole('ROLE_PRODUCTS')")
	public ResponseEntity<String> updateProducts(@PathVariable("id") long id, @RequestBody Products p) {

//		int count = productRepository.checkIfProductExist(p.getName(), p.getCode(), p.getSize(), p.getColor());
//		
//		System.out.println("====118==="+count);
//		
//		if(count > 0) {
//			return new ResponseEntity<>("This product exist!", HttpStatus.OK);
//			
//		}

		Optional<Products> productData = productRepository.findById(id);

		if (productData.isPresent()) {

			Products product = productData.get();

			product.setCode(p.getCode());
			product.setName(p.getName());
			product.setDescription(p.getDescription());
			product.setMin_price(p.getMin_price());
			product.setMax_price(p.getMax_price());
			product.setSuply_price(p.getSuply_price());
			product.setColor(p.getColor());
			// product.setSize(p.getSize());
			product.setRemark(p.getRemark());

			product.setGoldenStand(p.getGoldenStand());
			product.setClassicStand(p.getClassicStand());
			product.setBusinesStand(p.getBusinesStand());
			product.setEconomicStand(p.getEconomicStand());
			product.setKidStand(p.getKidStand());
			product.setShoesStand(p.getShoesStand());
			product.setWatchStand(p.getWatchStand());
			product.setWomenStand(p.getWomenStand());
			product.setConfirmation(p.getConfirmation());

			productRepository.save(product);

			return new ResponseEntity<>("Updated successfully!", HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@DeleteMapping("/product/{id}")
	@PreAuthorize("hasRole('ROLE_PRODUCTS')")
	public ResponseEntity<HttpStatus> delete(@PathVariable("id") long id) {

		try {
			productRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/avairableqty")
	@PreAuthorize("hasRole('ROLE_PRODUCTS')")
	public ResponseEntity<Integer> checkAvairableQty(@RequestBody ReportBodyDetails r) {

		String id = r.getText1();
		String stand = r.getText2();
		
		//System.out.println("=====product=======338===="+id+" STAND:"+stand);

		try {

			// check if produch is avairable in stock

			avairableQty = stockLevelRepository.getAvailableQty(Integer.valueOf(id), stand);
			
			//System.out.println("=====product=======344===="+id+" "+stand+" qty:"+avairableQty);

			return new ResponseEntity<>(avairableQty, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("=====product=======350===="+e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//////////////////// images

	@PostMapping("/upload")
	@PreAuthorize("hasRole('ROLE_PRODUCTS')")
	public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
		String message = "";
		try {
			storageService.store(file);
			message = "Uploaded the file successfully: " + file.getOriginalFilename();
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
		} catch (Exception e) {
			message = "Could not upload the file: " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
		}
	}

	@GetMapping("/files")
	@PreAuthorize("hasRole('ROLE_PRODUCTS')")
	public ResponseEntity<List<ResponseFile>> getListFiles() {
		List<ResponseFile> files = storageService.getAllFiles().map(dbFile -> {
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/files/")
					.path(dbFile.getId()).toUriString();
			return new ResponseFile(dbFile.getName(), fileDownloadUri, dbFile.getType(), dbFile.getData().length);
		}).collect(Collectors.toList());
		return ResponseEntity.status(HttpStatus.OK).body(files);
	}

	@GetMapping("/files/{id}")
	@PreAuthorize("hasRole('ROLE_PRODUCTS')")
	public ResponseEntity<byte[]> getFile(@PathVariable String id) {
		FileDB fileDB = storageService.getFile(id);
		
		System.out.println("products=======get file====="+id);
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
				.body(fileDB.getData());
	}
	
//	@GetMapping("/files/{id}")
//	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
//	public ResponseEntity<FileDB> getFile(@PathVariable String id) {
//		FileDB fileDB = storageService.getFile(id);
//		
//		System.out.println("products=======get file====="+id);
//		
//		return new ResponseEntity<>(fileDB, HttpStatus.OK);
//	}

}
