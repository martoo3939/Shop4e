package com.shop4e.shop.misc;

import com.shop4e.shop.domain.BookCategory;
import com.shop4e.shop.domain.Category;
import com.shop4e.shop.domain.CurrencyRate;
import com.shop4e.shop.domain.Genre;
import com.shop4e.shop.domain.enumeration.CurrencyType;
import com.shop4e.shop.repository.BookCategoryRepository;
import com.shop4e.shop.repository.CategoryRepository;
import com.shop4e.shop.repository.CurrencyRateRepository;
import com.shop4e.shop.repository.GenreRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder implements CommandLineRunner {

  private final CategoryRepository categoryRepository;
  private final BookCategoryRepository bookCategoryRepository;
  private final GenreRepository genreRepository;
  private final CurrencyRateRepository currencyRateRepository;

  private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseSeeder.class);

  private static final List<String> mainCategories = List.of("Electronics", "Books",
      "Music and Films");

  private static final Map<String, List<String>> bookCategories = Map.of("Computers",
      List.of("Algorithms and Data Structures", "Applications and Software",
          "Artificial Intelligence (AI)", "Computer Science"),
      "Business & Economics",
      List.of("Accounting", "E-Commerce", "Econometrics", "Economics", "Human Resources",
          "Investing"),
      "Arts",
      List.of("Architecture", "Business of Art", "Digital Music"));

  private static final List<String> entertainmentGenres = List.of("Action", "Fantasy", "Comedy",
      "Drama", "Thriller", "Hip hop", "Pop", "Rock", "Pop-folk");

  public DatabaseSeeder(
      CategoryRepository categoryRepository,
      BookCategoryRepository bookCategoryRepository,
      GenreRepository genreRepository,
      CurrencyRateRepository currencyRateRepository) {
    this.categoryRepository = categoryRepository;
    this.bookCategoryRepository = bookCategoryRepository;
    this.genreRepository = genreRepository;
    this.currencyRateRepository = currencyRateRepository;
  }

  @Override
  public void run(String... args) throws Exception {
    if (categoryRepository.count() == 0) {
      initializeCategories();
    }

    if (bookCategoryRepository.count() == 0) {
      initializeBookCategories();
    }

    if (genreRepository.count() == 0) {
      initializeGenres();
    }

    if (currencyRateRepository.count() == 0) {
      initializeCurrencies();
    }
  }

  private void initializeCurrencies() {
    CurrencyType[] supportedCurrencies = CurrencyType.values();

    List<CurrencyRate> currencyRates = Arrays.stream(supportedCurrencies)
        .flatMap(currencyFrom -> Arrays.stream(supportedCurrencies)
            .filter(currencyTo -> !currencyTo.name().equals(currencyFrom.name()))
            .map(currencyTo -> {
              BigDecimal exchangeRate = calculateExchangeRate(currencyFrom, currencyTo);

              CurrencyRate currencyRate = new CurrencyRate();
              currencyRate.setRate(exchangeRate);
              currencyRate.setSourceCurrency(currencyFrom);
              currencyRate.setTargetCurrency(currencyTo);

              return currencyRate;
            })).toList();

    currencyRateRepository.saveAll(currencyRates);
  }

  private BigDecimal calculateExchangeRate(CurrencyType currencyFrom, CurrencyType currencyTo) {
    return currencyTo.getValue().divide(currencyFrom.getValue(), 2, RoundingMode.HALF_UP);
  }

  private void initializeBookCategories() {
    List<BookCategory> categoriesToSave = new ArrayList<>();

    for (Map.Entry<String, List<String>> bookCategory : bookCategories.entrySet()) {
      BookCategory parentCategory = new BookCategory();
      parentCategory.setName(bookCategory.getKey());

      categoriesToSave.add(parentCategory);

      for (String subCategory : bookCategory.getValue()) {
        BookCategory subBookCategory = new BookCategory();
        subBookCategory.setName(subCategory);
        subBookCategory.setParentCategory(parentCategory);

        categoriesToSave.add(subBookCategory);
      }
    }

    bookCategoryRepository.saveAll(categoriesToSave);
  }

  private void initializeCategories() {
    List<Category> categoriesToSave = new ArrayList<>();

    for (String categoryName : mainCategories) {
      Category category = new Category();
      category.setName(categoryName);

      categoriesToSave.add(category);
    }

    categoryRepository.saveAll(categoriesToSave);
  }

  private void initializeGenres() {
    List<Genre> genres = new ArrayList<>();

    for (String genreName : entertainmentGenres) {
      Genre genre = new Genre();
      genre.setName(genreName);

      genres.add(genre);
    }

    genreRepository.saveAll(genres);
  }
}
