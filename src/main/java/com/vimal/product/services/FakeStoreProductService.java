package com.vimal.product.services;

import com.vimal.product.customException.ProductNotFoundException;
import com.vimal.product.dtos.CreateProductRequestDto;
import com.vimal.product.dtos.FakeStoreProductDto;
import com.vimal.product.models.Product;
import com.vimal.product.projections.ProductTitleProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeStoreProductService implements ProductService{

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public List<Product> getAllProducts() {
        FakeStoreProductDto[] fakeStoreProductDtos = restTemplate.getForObject("https://fakestoreapi.com/products/", FakeStoreProductDto[].class);

        List<Product> products = new ArrayList<>();
        for (FakeStoreProductDto fakeStoreProductDto : fakeStoreProductDtos) {
            Product product = fakeStoreProductDto.toProduct();
            products.add(product);
        }

        return products;
    }

    @Override
    public Product getProductById(long id) throws ProductNotFoundException {

        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity = restTemplate.getForEntity("https://fakestoreapi.com/products/" + id,
                FakeStoreProductDto.class);

        if(fakeStoreProductDtoResponseEntity.getStatusCode() != HttpStatusCode.valueOf(200)){

        }
        FakeStoreProductDto fakeStoreProductDto = fakeStoreProductDtoResponseEntity.getBody();

        if(fakeStoreProductDto==null){
            throw new ProductNotFoundException("Product with id " + id + " is not present with the service. It's an invalid id");
        }

        return fakeStoreProductDto.toProduct();


    }

    @Override
    public Product addProduct(String title, String description, double price, String image, String category) {


        FakeStoreProductDto fakeStoreProductDto =  new FakeStoreProductDto();
        fakeStoreProductDto.setTitle(title);
        fakeStoreProductDto.setDescription(description);
        fakeStoreProductDto.setPrice(price);
        fakeStoreProductDto.setImage(image);
        fakeStoreProductDto.setCategory(category);
        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity = restTemplate.postForEntity("https://fakestoreapi.com/products",
                fakeStoreProductDto,
                FakeStoreProductDto.class);

        FakeStoreProductDto fakeStoreProductDto1 = fakeStoreProductDtoResponseEntity.getBody();
        if(fakeStoreProductDto1 != null) {
            return fakeStoreProductDto1.toProduct();
        }

        return null;
    }

    @Override
    public List<Product> createManyProduct(List<CreateProductRequestDto> createProductRequestDtos) {
        return List.of();
    }

    @Override
    public List<ProductTitleProjection> getProductTitles() {
        return List.of();
    }
}
