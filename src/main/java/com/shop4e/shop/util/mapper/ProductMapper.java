package com.shop4e.shop.util.mapper;

import com.shop4e.shop.domain.Book;
import com.shop4e.shop.domain.ElectronicDevice;
import com.shop4e.shop.domain.Entertainment;
import com.shop4e.shop.domain.Genre;
import com.shop4e.shop.domain.Photo;
import com.shop4e.shop.domain.Product;
import com.shop4e.shop.web.response.BookResponse;
import com.shop4e.shop.web.response.DeviceResponse;
import com.shop4e.shop.web.response.EntertainmentResponse;
import com.shop4e.shop.web.response.ProductDetailsResponse;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
  public BookResponse mapBookToBookResponse(Book book) {
    ProductDetailsResponse generalDetails = mapProductToResponse(book);

    BookResponse response = new BookResponse();
    response.setDetails(generalDetails);
    response.setAuthor(book.getAuthor());
    response.setBookCategory(book.getBookCategory().getName());
    response.setISBN(book.getISBN());
    response.setPages(book.getPages());
    response.setPublishedAt(book.getPublishedAt());

    return response;
  }

  public EntertainmentResponse mapEntertainmentToResponse(Entertainment product) {
    ProductDetailsResponse generalDetails = mapProductToResponse(product);

    EntertainmentResponse response = new EntertainmentResponse();
    response.setGeneralDetails(generalDetails);
    response.setDetails(product.getDetails());
    response.setType(product.getType().name());
    response.setGenres(product.getGenre().stream().map(Genre::getName).toList());

    return response;
  }

  public DeviceResponse mapDeviceToDeviceResponse(ElectronicDevice product) {
    ProductDetailsResponse generalDetails = mapProductToResponse(product);

    DeviceResponse response = new DeviceResponse();
    response.setDetails(generalDetails);
    response.setType(product.getType().getTypeName());
    response.setBrand(product.getBrand());
    response.setMemory(product.getMemory());
    response.setProcessor(product.getProcessor());
    response.setHardDrive(product.getHardDrive());
    response.setVideoCard(product.getVideoCard());

    return response;
  }

  private <T extends Product> ProductDetailsResponse mapProductToResponse(T product) {
    ProductDetailsResponse response = new ProductDetailsResponse();
    response.setSeller(product.getSeller().getEmail());
    response.setId(product.getId().toString());
    response.setTitle(product.getTitle());
    response.setDescription(product.getDescription());
    response.setPrice(product.getPrice());
    response.setCurrency(product.getCurrency().name());
    response.setCategory(product.getCategory().getName());
    response.setCategoryId(product.getCategory().getId().toString());
    response.setPublished(product.getCreated());
    response.setEdited(product.getModified());
    response.setProductType(product.getProductType());
    response.setPhotos(product.getPhotos().stream()
        .map(Photo::getLocation)
        .collect(Collectors.toList()));

    return response;
  }
}
