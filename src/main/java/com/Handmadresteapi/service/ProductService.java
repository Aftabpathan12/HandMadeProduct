package com.Handmadresteapi.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Handmadresteapi.model.Product;
import com.Handmadresteapi.repository.ProductRepository;

@Service
public class ProductService{
	
	@Autowired
	private ProductRepository productrepository;
	
    private final String uploadDir = "D:/HandMadeProject1/src/main/java/com/Handmadresteapi/src/main/resources/static/images/"; // Define your directory path

	List<Product> productlist = new ArrayList<Product>();
	
	public  String saveproduct(Product productobj, MultipartFile file) {
        // Ensure the directory exists
        File directory = new File(uploadDir);
        System.out.println(directory.getPath());
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Save the file to the directory
        String filePath = uploadDir + file.getOriginalFilename();
        System.out.println(filePath);
        try {
			file.transferTo(new File(filePath));
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        productobj.setImagePath(file.getOriginalFilename());
        productrepository.save(productobj);
        return "Product object saved";
    }

	
	
	public List<Product> fetchdata()
	{
		 return (List<Product>) productrepository.findAll();
	}
	
	
	public String updatedata(int id, Product productobj)
	{
		productrepository.save(productobj);
		return "Data Updated Successfully";	
	}
	
	public String deletedata(int id)
	{
		System.out.println(id);
		productrepository.deleteById(id);
		return "data deleted";
	}
	
	public List<Product> getAllProducts() {
        return productrepository.findAll();
    }



	public List<Product> getProductsByArtistId(Long artistId) {
		// TODO Auto-generated method stub
		   return productrepository.findByArtistid(artistId);
		
	}
	
}


