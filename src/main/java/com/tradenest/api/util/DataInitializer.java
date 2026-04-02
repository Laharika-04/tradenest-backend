package com.tradenest.api.util;

import com.tradenest.api.entity.*;
import com.tradenest.api.enums.ProductStatus;
import com.tradenest.api.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CityRepository cityRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(CategoryRepository categoryRepository,
                           UserRepository userRepository,
                           ProductRepository productRepository,
                           CityRepository cityRepository,
                           PasswordEncoder passwordEncoder) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.cityRepository = cityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        if (categoryRepository.count() > 0) return;

        // ========================= CATEGORIES =========================
        Category cars = createCategory("Cars","cars","🚗","Cars marketplace");
        Category bikes = createCategory("Motorcycles","motorcycles","🏍️","Bikes marketplace");
        Category phones = createCategory("Mobile Phones","phones","📱","Smartphones");
        Category electronics = createCategory("Electronics","electronics","💻","Electronics");
        Category furniture = createCategory("Furniture","furniture","🪑","Furniture");
        Category fashion = createCategory("Fashion","fashion","👕","Clothing");
        Category books = createCategory("Books","books","📚","Books");
        Category realEstate = createCategory("Real Estate","real-estate","🏠","Property");
        Category laptops = createCategory("Laptops","laptops","💻","Laptops");
        Category pets = createCategory("Pets","pets","🐶","Pets");
        Category sports = createCategory("Sports","sports","⚽","Sports");
        Category services = createCategory("Services","services","🛠️","Services");

        categoryRepository.saveAll(List.of(
                cars,bikes,phones,electronics,furniture,
                fashion,books,realEstate,laptops,pets,sports,services
        ));

        // ========================= CITIES =========================
        cityRepository.saveAll(List.of(
                c("Mumbai","Maharashtra",19.076,72.8777),
                c("Delhi","Delhi",28.7041,77.1025),
                c("Bangalore","Karnataka",12.9716,77.5946),
                c("Hyderabad","Telangana",17.385,78.4867)
        ));

        // ========================= USERS =========================
        User seller1 = u("Rahul Sharma","seller1@tn.com","Bangalore","Karnataka","9999999991");
        User seller2 = u("Amit Verma","seller2@tn.com","Mumbai","Maharashtra","9999999992");
        User seller3 = u("Kiran Kumar","seller3@tn.com","Hyderabad","Telangana","9999999993");

        userRepository.saveAll(List.of(seller1,seller2,seller3));

        // ========================= PRODUCTS =========================
        productRepository.saveAll(List.of(
                p("Honda City 2022","Single owner","850000","Mumbai","Maharashtra",cars,seller2,true),
                p("Yamaha R15","Like new","145000","Bangalore","Karnataka",bikes,seller1,true),
                p("iPhone 15 Pro","Brand new","130000","Hyderabad","Telangana",phones,seller3,true)
        ));

        System.out.println("✅ TradeNest Seed Data Loaded");
    }

    private Category createCategory(String n,String s,String i,String d){
        Category c=new Category();
        c.setName(n);
        c.setSlug(s);
        c.setIcon(i);
        c.setDescription(d);
        return c;
    }

    private City c(String city,String state,double lat,double lng){
        City x=new City();
        x.setCity(city);
        x.setState(state);
        x.setLat(lat);
        x.setLng(lng);
        return x;
    }

    private User u(String n,String e,String c,String s,String p){
        User x=new User();
        x.setName(n);
        x.setEmail(e);
        x.setPassword(passwordEncoder.encode("password123"));
        x.setCity(c);
        x.setState(s);
        x.setPhone(p);
        x.setVerified(true);
        return x;
    }

    private Product p(String t,String d,String price,String c,String s,Category cat,User seller,boolean f){
        Product x=new Product();
        x.setTitle(t);
        x.setDescription(d);
        x.setPrice(new BigDecimal(price));
        x.setCity(c);
        x.setState(s);
        x.setCategory(cat);
        x.setSeller(seller);
        x.setFeatured(f);
        x.setStatus(ProductStatus.ACTIVE);
        x.setImages("sample.jpg");
        x.setViewCount(0);
        return x;
    }
}