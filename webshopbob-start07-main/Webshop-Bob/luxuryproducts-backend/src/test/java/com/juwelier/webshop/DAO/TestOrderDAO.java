package com.juwelier.webshop.DAO;

import com.juwelier.webshop.dao.CustomerRepository;
import com.juwelier.webshop.dao.OrderItemRepository;
import com.juwelier.webshop.dao.OrderRepository;
import com.juwelier.webshop.dao.ProductRepository;
import com.juwelier.webshop.dao.OrderDAO;
import com.juwelier.webshop.models.Customer;
import com.juwelier.webshop.models.Order;
import com.juwelier.webshop.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
public class TestOrderDAO {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private CustomerService customerService;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private OrderItemRepository orderItemRepository;

    private OrderDAO SUT;

    @BeforeEach
    public void setup(){
        this.SUT = new OrderDAO(this.orderRepository, this.customerRepository, this.customerService,
                this.productRepository, this.orderItemRepository);
    }

    @Test
    public void should_return_List_of_returnableOrders_within_30_days(){
        List<Order> DummyOrderList = new ArrayList<Order>();
        Customer dummyCustomer = new Customer();
        List<Order> dummyAllOrders = new ArrayList<Order>();
        when(this.customerService.getActiveUser()).thenReturn(dummyCustomer);
        when(this.orderRepository.findAllByCustomer(any())).thenReturn(dummyAllOrders);

        List<Order> actualOrders = this.SUT.getAllRetunableOrders();

        assertThat(actualOrders, is(DummyOrderList));
        Mockito.verify(orderRepository, times(1)).findAllByCustomer(any());
        Mockito.verify(customerService, times(1)).getActiveUser();


    }

}
