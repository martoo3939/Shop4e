package com.shop4e.shop.service.impl;

import com.shop4e.shop.domain.Book;
import com.shop4e.shop.domain.Category;
import com.shop4e.shop.domain.ElectronicDevice;
import com.shop4e.shop.domain.Entertainment;
import com.shop4e.shop.domain.Photo;
import com.shop4e.shop.domain.Product;
import com.shop4e.shop.domain.ProductProjection;
import com.shop4e.shop.domain.User;
import com.shop4e.shop.domain.enumeration.CurrencyType;
import com.shop4e.shop.domain.enumeration.DeviceType;
import com.shop4e.shop.exception.CustomException;
import com.shop4e.shop.repository.PhotoRepository;
import com.shop4e.shop.repository.ProductRepository;
import com.shop4e.shop.service.CategoryService;
import com.shop4e.shop.service.PhotoService;
import com.shop4e.shop.util.UserUtil;
import com.shop4e.shop.util.mapper.ProductMapper;
import com.shop4e.shop.web.marker.ProductResponse;
import com.shop4e.shop.web.request.EntertainmentType;
import com.shop4e.shop.web.request.ProductCreationRequest;
import com.shop4e.shop.web.request.ProductCurrency;
import com.shop4e.shop.web.request.ProductFilter;
import com.shop4e.shop.web.request.ProductSortRequest;
import com.shop4e.shop.web.request.SortDirection;
import com.shop4e.shop.web.response.BookResponse;
import com.shop4e.shop.web.response.DeviceResponse;
import com.shop4e.shop.web.response.PagedResponse;
import com.shop4e.shop.web.response.ProductCombinedResponse;
import com.shop4e.shop.web.response.ProductFilterResponse;
import com.shop4e.shop.web.response.ProductDetailsResponse;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class ShopService {

  private final ProductRepository productRepository;
  private final CategoryService categoryService;
  private final PhotoRepository photoRepository;
  private final PhotoService photoService;
  private final UserUtil userUtil;
  private final ProductMapper productMapper;

  public ShopService(
      ProductRepository productRepository,
      CategoryService categoryService,
      PhotoRepository photoRepository,
      UserUtil userUtil,
      PhotoService photoService,
      ProductMapper productMapper) {
    this.productRepository = productRepository;
    this.categoryService = categoryService;
    this.photoRepository = photoRepository;
    this.photoService = photoService;
    this.userUtil = userUtil;
    this.productMapper = productMapper;
  }

  public ProductDetailsResponse getProduct(String productId) {
    Product product = productRepository.findById(UUID.fromString(productId)).orElseThrow(
        () -> new CustomException(String.format("Product with id: %s does not exist", productId)));

    return mapProductToResponse(product);
  }

  public ProductDetailsResponse updateProduct(String productId, ProductCreationRequest request,
      Authentication principal) {
    User seller = getUserFromPrincipal(principal);

    checkCurrency(request);

    CurrencyType currency = CurrencyType.getCurrency(request.getCurrency());
    Category category = categoryService.getCategoryOrFail(request);

    Product product = productRepository.findProductByIdAndSeller(UUID.fromString(productId), seller)
        .orElseThrow(() -> new CustomException(
            String.format("Product with id: %s not found in your profile.", productId))
        );

    product.setTitle(request.getTitle());
    product.setDescription(request.getDescription());
    product.setCurrency(currency);
    product.setPrice(request.getPrice());
    product.setCategory(category);

    productRepository.saveAndFlush(product);

    return mapProductToResponse(product);
  }

  public PagedResponse getProducts(int page, int size, String category) {
    PageRequest pageable = PageRequest.of(page, size, Sort.by(Direction.DESC, "created"));
    List<ProductDetailsResponse> products;
    Page<Product> all;
    if (category == null) {
      all = productRepository.findAllByDeletedAtIsNull(pageable);
      products = all.get().map(this::mapProductToResponse)
          .collect(Collectors.toList());
    } else {
      all = productRepository.findAllByCategory(category, pageable);
      products = all.get().map(this::mapProductToResponse).collect(Collectors.toList());
    }

    return new PagedResponse(products, all.getTotalElements(), all.getTotalPages());
  }

  public void deleteProduct(String productId, Authentication principal) {
    User seller = getUserFromPrincipal(principal);

    Product product = productRepository.findProductByIdAndSeller(UUID.fromString(productId), seller)
        .orElseThrow(() -> new CustomException(
            String.format("Product with id: %s does not exist in your profile.", productId)));

    product.setDeletedAt(LocalDateTime.now(ZoneOffset.UTC));

    productRepository.saveAndFlush(product);
  }

  private User getUserFromPrincipal(Authentication principal) {
    return userUtil.getUserFromPrincipal(principal);
  }

  private ProductDetailsResponse mapProductToResponse(Product product) {
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

    return response;
  }

  private <T extends ProductCurrency> void checkCurrency(T request) {
    if (!CurrencyType.supportCurrency(request.getCurrency())) {
      throw new CustomException("Currency not supported!");
    }
  }

  private <T extends EntertainmentType> void checkEntertainmentType(T request) {
    if (!com.shop4e.shop.domain.enumeration.EntertainmentType.supportEntertainment(
        request.getType())) {
      throw new CustomException("Entertainment type not supported!");
    }
  }

//  public BookResponse createBook(BookCreationRequest request, Authentication principal) {
//    User seller = getUserFromPrincipal(principal);
//
//    checkCurrency(request);
//
//    CurrencyType currency = CurrencyType.getCurrency(request.getCurrency());
//    Category category = categoryService.getCategoryOrFail(request);
//    BookCategory bookCategory = categoryService.getBookCategoryOrFail(request);
//
//    Book product = new Book();
//    product.setSeller(seller);
//    product.setTitle(request.getTitle());
//    product.setDescription(request.getDescription());
//    product.setCurrency(currency);
//    product.setPrice(request.getPrice());
//    product.setCategory(category);
//    //Book specific
//    product.setAuthor(request.getAuthor());
//    product.setBookCategory(bookCategory);
//    product.setISBN(request.getISBN());
//    product.setPages(request.getPages());
//    product.setPublishedAt(request.getPublishedAt());
//
//    bookRepository.saveAndFlush(product);
//
//    BookResponse response = mapBookToBookResponse(product);
//
//    return response;
//  }
//
//  public BookResponse updateBook(String bookId, BookUpdateRequest request,
//      Authentication principal) {
//    User seller = getUserFromPrincipal(principal);
//
//    checkCurrency(request);
//
//    CurrencyType currency = CurrencyType.getCurrency(request.getCurrency());
//    BookCategory bookCategory = categoryService.getBookCategoryOrFail(request);
//
//    Book product = bookRepository.findBookByIdAndSeller(UUID.fromString(bookId), seller)
//        .orElseThrow(
//            () -> new CustomException(String.format("Product with id: %s does not exist", bookId)));
//    product.setTitle(request.getTitle());
//    product.setDescription(request.getDescription());
//    product.setCurrency(currency);
//    product.setPrice(request.getPrice());
//    //Book specific
//    product.setAuthor(request.getAuthor());
//    product.setBookCategory(bookCategory);
//    product.setISBN(request.getISBN());
//    product.setPages(request.getPages());
//    product.setPublishedAt(request.getPublishedAt());
//
//    bookRepository.saveAndFlush(product);
//
//    BookResponse response = mapBookToBookResponse(product);
//
//    return response;
//  }
//
//  public BookResponse getBook(String id) {
//    Book book = bookRepository.findById(UUID.fromString(id))
//        .orElseThrow(() -> new CustomException("Book not found."));
//
//    return mapBookToBookResponse(book);
//  }

  private BookResponse mapBookToBookResponse(Book book) {
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

//  public PagedResponse searchBooks(BookSearchRequest request, Pageable pageable,
//      Authentication principal) {
//    Page<Book> searchResult = bookRepository.searchBooks(request, pageable);
//
//    List<BookResponse> books = searchResult.stream().map(this::mapBookToBookResponse)
//        .collect(Collectors.toList());
//
//    return new PagedResponse(books, searchResult.getTotalElements(), searchResult.getTotalPages());
//  }
//
//  public DeviceResponse createElectronicDevice(DeviceCreationRequest request,
//      Authentication principal) {
//    User seller = getUserFromPrincipal(principal);
//
//    checkCurrency(request);
//    checkDeviceType(request);
//
//    CurrencyType currency = CurrencyType.getCurrency(request.getCurrency());
//    Category category = categoryService.getCategoryOrFail(request);
//    DeviceType deviceType = DeviceType.getDeviceType(request.getType());
//
//    ElectronicDevice product = new ElectronicDevice();
//    product.setSeller(seller);
//    product.setTitle(request.getTitle());
//    product.setDescription(request.getDescription());
//    product.setCurrency(currency);
//    product.setPrice(request.getPrice());
//    product.setCategory(category);
//    //Device specific
//    product.setType(deviceType);
//    product.setBrand(request.getBrand());
//    product.setMemory(request.getMemory());
//    product.setProcessor(request.getProcessor());
//    product.setHardDrive(request.getHardDrive());
//    product.setVideoCard(request.getVideoCard());
//
//    electronicDeviceRepository.saveAndFlush(product);
//
//    DeviceResponse response = mapDeviceToDeviceResponse(product);
//
//    return response;
//  }

  private DeviceResponse mapDeviceToDeviceResponse(ElectronicDevice product) {
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

  private <T extends com.shop4e.shop.web.request.DeviceType> void checkDeviceType(T request) {
    if (!DeviceType.supportDevice(request.getType())) {
      throw new CustomException("Device type not supported!");
    }
  }

//  public DeviceResponse getDevice(String id) {
//    ElectronicDevice device = electronicDeviceRepository.findById(UUID.fromString(id))
//        .orElseThrow(() -> new CustomException("This device does not exist!"));
//
//    return mapDeviceToDeviceResponse(device);
//  }
//
//  public DeviceResponse updateDevice(String deviceId, DeviceUpdateRequest request,
//      Authentication principal) {
//    User seller = getUserFromPrincipal(principal);
//
//    checkCurrency(request);
//    checkDeviceType(request);
//
//    CurrencyType currency = CurrencyType.getCurrency(request.getCurrency());
//    DeviceType deviceType = DeviceType.getDeviceType(request.getType());
//
//    ElectronicDevice product = electronicDeviceRepository.findElectronicDeviceByIdAndSeller(
//            UUID.fromString(deviceId), seller)
//        .orElseThrow(() -> new CustomException("Device not found."));
//    product.setTitle(request.getTitle());
//    product.setDescription(request.getDescription());
//    product.setCurrency(currency);
//    product.setPrice(request.getPrice());
//    //Device specific
//    product.setType(deviceType);
//    product.setBrand(request.getBrand());
//    product.setMemory(request.getMemory());
//    product.setProcessor(request.getProcessor());
//    product.setHardDrive(request.getHardDrive());
//    product.setVideoCard(request.getVideoCard());
//
//    electronicDeviceRepository.saveAndFlush(product);
//
//    DeviceResponse response = mapDeviceToDeviceResponse(product);
//
//    return response;
//  }
//
//  public EntertainmentResponse createEntertainment(EntertainmentCreationRequest request,
//      Authentication principal) {
//    User seller = getUserFromPrincipal(principal);
//
//    checkCurrency(request);
//    checkEntertainmentType(request);
//
//    CurrencyType currency = CurrencyType.getCurrency(request.getCurrency());
//    com.shop4e.shop.domain.enumeration.EntertainmentType entertainmentType = com.shop4e.shop.domain.enumeration.EntertainmentType.getEntertainmentType(
//        request.getType());
//    Category category = categoryService.getCategoryOrFail(request);
//    List<Genre> genres = categoryService.getGenresOrFail(request);
//
//    Entertainment product = new Entertainment();
//    product.setSeller(seller);
//    product.setTitle(request.getTitle());
//    product.setDescription(request.getDescription());
//    product.setCurrency(currency);
//    product.setPrice(request.getPrice());
//    product.setCategory(category);
//    //Entertainment specific
//    product.setType(entertainmentType);
//    product.setGenre(genres);
//    product.setDetails(request.getDetails());
//
//    entertainmentRepository.saveAndFlush(product);
//
//    EntertainmentResponse response = mapEntertainmentToResponse(product);
//
//    return response;
//  }
//
//  private EntertainmentResponse mapEntertainmentToResponse(Entertainment product) {
//    ProductResponse generalDetails = mapProductToResponse(product);
//
//    EntertainmentResponse response = new EntertainmentResponse();
//    response.setGeneralDetails(generalDetails);
//    response.setDetails(product.getDetails());
//    response.setType(product.getType().name());
//    response.setGenres(product.getGenre().stream().map(Genre::getName).toList());
//
//    return response;
//  }
//
//  public EntertainmentResponse getEntertainment(String id) {
//    Entertainment product = entertainmentRepository.findById(UUID.fromString(id))
//        .orElseThrow(() -> new CustomException("Product not found."));
//
//    return mapEntertainmentToResponse(product);
//  }

//  public EntertainmentResponse updateEntertainment(
//      String id,
//      EntertainmentUpdateRequest request,
//      Authentication principal) {
//    User seller = getUserFromPrincipal(principal);
//
//    checkCurrency(request);
//    checkEntertainmentType(request);
//
//    CurrencyType currency = CurrencyType.getCurrency(request.getCurrency());
//    com.shop4e.shop.domain.enumeration.EntertainmentType entertainmentType = com.shop4e.shop.domain.enumeration.EntertainmentType.getEntertainmentType(
//        request.getType());
//    List<Genre> genres = categoryService.getGenresOrFail(request);
//
//    Entertainment product = entertainmentRepository.findEntertainmentByIdAndSeller(
//            UUID.fromString(id), seller)
//        .orElseThrow(() -> new CustomException("Product not found."));
//    product.setTitle(request.getTitle());
//    product.setDescription(request.getDescription());
//    product.setCurrency(currency);
//    product.setPrice(request.getPrice());
//    //Entertainment specific
//    product.setType(entertainmentType);
//    product.setGenre(genres);
//    product.setDetails(request.getDetails());
//
//    entertainmentRepository.saveAndFlush(product);
//
//    EntertainmentResponse response = mapEntertainmentToResponse(product);
//
//    return response;
//  }

  public PagedResponse getAllProducts(int page, int size, List<ProductSortRequest> sort,
      Authentication principal) {
    User user = getUserFromPrincipal(principal);
    if (sort.isEmpty()) {
      ProductSortRequest defaultSort = new ProductSortRequest()
          .setField("created")
          .setDirection(SortDirection.DESC);
      sort.add(defaultSort);
    }

    List<Order> mappedOrders = sort.stream()
        .map(o -> new Order(Direction.fromString(o.getDirection().name()), o.getField())).collect(
            Collectors.toList());

    Sort sortObject = Sort.by(mappedOrders);
    PageRequest pageable = PageRequest.of(page, size, sortObject);
    Page<ProductProjection> products = productRepository.findAllProducts(user.getId().toString(),
        pageable);
    List<ProductCombinedResponse> content = products.getContent().stream()
        .map(this::mapToProduct)
        .collect(Collectors.toList());

    PagedResponse response = new PagedResponse();
    response.setContent(content);
    response.setTotalPages(products.getTotalPages());
    response.setTotalElements(products.getTotalElements());

    return response;
  }

  public PagedResponse getAllFilteredProducts(int page, int size, ProductFilter filter,
      List<ProductSortRequest> sort, Authentication principal) {
    User user = getUserFromPrincipal(principal);

    if (sort.isEmpty()) {
      ProductSortRequest defaultSort = new ProductSortRequest()
          .setField("created")
          .setDirection(SortDirection.DESC);
      sort.add(defaultSort);
    }

    List<Order> mappedOrders = sort.stream()
        .map(o -> new Order(Direction.fromString(o.getDirection().name()), o.getField())).collect(
            Collectors.toList());

    Sort sortObject = Sort.by(mappedOrders);
    PageRequest pageable = PageRequest.of(page, size, sortObject);
    Page<ProductFilterResponse> products = productRepository.findAllProducts(
        user.getId().toString(), filter, pageable);
    List<ProductFilterResponse> content = products.getContent().stream()
        .collect(Collectors.toList());

    PagedResponse response = new PagedResponse();
    response.setContent(content);
    response.setTotalPages(products.getTotalPages());
    response.setTotalElements(products.getTotalElements());

    return response;
  }

  private ProductCombinedResponse mapToProduct(ProductProjection product) {
    ProductCombinedResponse mappedProduct = new ProductCombinedResponse(product);
    return mappedProduct;
  }

  public PagedResponse getAllFilteredProductsByGuest(int page, int size, ProductFilter filter,
      List<ProductSortRequest> sort) {
    if (sort.isEmpty()) {
      ProductSortRequest defaultSort = new ProductSortRequest()
          .setField("created")
          .setDirection(SortDirection.DESC);
      sort.add(defaultSort);
    }

    List<Order> mappedOrders = sort.stream()
        .map(o -> new Order(Direction.fromString(o.getDirection().name()), o.getField())).collect(
            Collectors.toList());

    Sort sortObject = Sort.by(mappedOrders);
    PageRequest pageable = PageRequest.of(page, size, sortObject);
    Page<ProductFilterResponse> products = productRepository.findAllProductsByGuest(filter,
        pageable);
    List<ProductFilterResponse> content = products.getContent().stream()
        .collect(Collectors.toList());

    PagedResponse response = new PagedResponse();
    response.setContent(content);
    response.setTotalPages(products.getTotalPages());
    response.setTotalElements(products.getTotalElements());

    return response;
  }

  public void deleteProductImage(String imageIdentifier, Authentication principal) {
    User user = getUserFromPrincipal(principal);

    Photo photoToDelete = photoRepository.findPhotoByIdentifierAndProduct_Seller(imageIdentifier,
            user)
        .orElseThrow(() -> new CustomException("Image not found."));

    photoService.deletePhoto(photoToDelete.getIdentifier());
  }

  public <P extends ProductResponse> P getProductById(String productId) {
    Product product = productRepository.findProductByIdAndDeletedAtIsNull(
            UUID.fromString(productId))
        .orElseThrow(() -> new CustomException("Product not found."));

    ProductResponse response = null;
    if (product instanceof Book) {
      response = productMapper.mapBookToBookResponse((Book) product);
    } else if (product instanceof ElectronicDevice) {
      response = productMapper.mapDeviceToDeviceResponse((ElectronicDevice) product);
    } else if (product instanceof Entertainment) {
      response = productMapper.mapEntertainmentToResponse((Entertainment) product);
    }

    return (P) response;
  }
}
