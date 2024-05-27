package com.juwelier.webshop.utils;

import com.juwelier.webshop.dao.CategoryRepository;
import com.juwelier.webshop.dao.CustomerRepository;
import com.juwelier.webshop.dao.OrderRepository;
import com.juwelier.webshop.dao.ProductRepository;
import com.juwelier.webshop.models.Category;
import com.juwelier.webshop.models.Customer;
import com.juwelier.webshop.models.Order;
import com.juwelier.webshop.models.Product;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

@Component
public class Seeder {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private CustomerRepository customerRepository;
    private PasswordEncoder passwordEncoder;
    private OrderRepository orderRepository;
    private LocalDate today = LocalDate.now();

    public Seeder(ProductRepository productRepository, CategoryRepository categoryRepository, CustomerRepository customerRepository, PasswordEncoder passwordEncoder, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.orderRepository = orderRepository;
    }

    @EventListener
    public void seeder(ContextRefreshedEvent event){
        Category category1 = new Category("Watches");
        Category category2 = new Category("Rings");
        Category category3 = new Category("Necklaces");
        Category category4 = new Category("Accessories");
        this.categoryRepository.save(category1);
        this.categoryRepository.save(category2);
        this.categoryRepository.save(category3);
        this.categoryRepository.save(category4);


        Product product1 = new Product(
                "Rolex",
                "https://schapi-products.s3.eu-central-1.amazonaws.com/3523842/m228238-0061_modelpage_front_facing_landscape.png",
                "An expensive collectors item",
                40000,
                2,
                category1
        );
        Product product2 = new Product(
                "Gold chain",
                "https://i.ebayimg.com/images/g/8j8AAOSwWktaaFRP/s-l1200.webp",
                "A big, heavy gold chain",
                15000,
                2,
                category3
        );
        Product product3 = new Product(
                "Diamond ring",
                "https://idjewelry.com/media/catalog/product/cache/6772f233b6ab10acdab5c36a45eb28cd/1/2/12baf843a62e47d3db2d9fe9820c37b7.jpg",
                "Thousands of tiny diamonds",
                125000,
                2,
                category2
        );
        Product product4 = new Product(
                "Vintage watch",
                "https://encrypted-tbn3.gstatic.com/shopping?q=tbn:ANd9GcQbutRSQI54h9aDmDKvLkGGfiZZlf3hlCdoZMK8qZ79J20A5ReXuwv9A2mWGVyyNYg9bBPlGFRAyT8ih6k7mnu8VydAh9UjfRPlfMtDvuw_jWbJdPTYZvm5&usqp=CAE",
                "Made in 1939",
                20000,
                2,
                category1
        );
        Product product5 = new Product(
                "L'incomparable",
                "https://4cs.gia.edu/wp-content/uploads/2013/04/Incomparable-400.png",
                "Worlds most expensive necklace",
                550000,
                2,
                category3
        );
        Product product6 = new Product(
                "Dolce sunglasses",
                "https://eyeonoptics.com.au/media/nsrj5v1f/dolce-and-gabbana.jpg",
                "For classy sun-protection",
                95000,
                2,
                category4
        );
        Product product7 = new Product(
                "Geneva Magnificent",
                "https://robbreport.com/wp-content/uploads/2020/11/2020_GNV_18970_0354_000exceptional_pair_of_diamond_and_coloured_diamond_earrings061852-2.gif",
                "Three-colored piece of beauty",
                820000,
                2,
                category4
        );
        Product product8 = new Product(
                "Eagle belt",
                "https://i.pinimg.com/originals/c3/1b/43/c31b4383b95307094498912839164283.jpg",
                "Made from real crocodile leather",
                62000,
                2,
                category4
        );
        Product product9 = new Product(
                "Crown ring",
                "https://nationaljeweler.com/uploads/ff952437c9c312c4e84ca8f31d155d6f.jpg",
                "To make you feel like a king",
                160000,
                2,
                category2
        );
        Product product10 = new Product(
                "Mysterious ring",
                "https://upload.wikimedia.org/wikipedia/commons/d/d4/One_Ring_Blender_Render.png",
                "The one to rule them all",

                0,
                1,
                category2
        );
        this.productRepository.save(product1);
        this.productRepository.save(product2);
        this.productRepository.save(product3);
        this.productRepository.save(product4);
        this.productRepository.save(product5);
        this.productRepository.save(product6);
        this.productRepository.save(product7);
        this.productRepository.save(product8);
        this.productRepository.save(product9);
        this.productRepository.save(product10);

        String encodedPassword = passwordEncoder.encode("Test123!");
        Customer customer = new Customer(
                "Bob",
                "Webshop",
                "bob@bobsluxuryenterprise.com",
                encodedPassword,
                "Admin"
        );
        this.customerRepository.save(customer);

        this.today = LocalDate.now();

//        List<Product> products = Arrays.asList(product1, product2, product3);
//        Order order = new Order(
//                customer,
//                products,
//                5000,
//                "2024-05-16"
//        );
//        this.orderRepository.save(order);

    }
}
