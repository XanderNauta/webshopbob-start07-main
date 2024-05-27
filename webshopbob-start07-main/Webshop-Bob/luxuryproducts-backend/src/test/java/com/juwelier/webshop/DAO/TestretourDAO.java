package com.juwelier.webshop.DAO;

import com.juwelier.webshop.dao.OrderItemRepository;
import com.juwelier.webshop.dao.OrderRepository;
import com.juwelier.webshop.dao.ProductRepository;
import com.juwelier.webshop.dao.RetourDAO;
import com.juwelier.webshop.dto.ReturnDTO;
import com.juwelier.webshop.models.Order;
import com.juwelier.webshop.models.OrderItem;
import com.juwelier.webshop.models.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

import static org.hamcrest.Matchers.is;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestretourDAO {

    @Mock
    private OrderItemRepository orderItemRepository;
    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderRepository orderRepository;

    private RetourDAO SUT;

    @BeforeEach
    public void setup() {
        this.SUT = new RetourDAO(this.orderItemRepository, this.productRepository, this.orderRepository);
    }

    @Test
    public void should_return_list_of_items_with_status_returning() {
        List<OrderItem> dummyOrderItems = new ArrayList<OrderItem>();
        when(this.orderItemRepository.findAllByStatus(anyString())).thenReturn(dummyOrderItems);

        List<OrderItem> actualOrderItems = this.SUT.getAllRequests();

        assertThat(actualOrderItems, is(dummyOrderItems));
        verify(orderItemRepository, times(1)).findAllByStatus(anyString());

    }

    @Test
    public void should_Return_Item_Successfully() {
        // Arrange
        ReturnDTO returnDTO = new ReturnDTO();

        Product product = new Product();
        Order order = new Order();
        OrderItem orderItem = new OrderItem();


        when(productRepository.findProductById(returnDTO.productId)).thenReturn(product);
        when(orderRepository.findById(returnDTO.orderId)).thenReturn(Optional.of(order));
        when(orderItemRepository.findByProductAndOrder(product, order)).thenReturn(orderItem);

        // Act
        SUT.returnItem(returnDTO);

        // Assert
        verify(orderItemRepository, times(1)).save(orderItem);
        assertEquals("Returning...", orderItem.getStatus());
        assertEquals(returnDTO.reason, orderItem.getReason());
        assertEquals(returnDTO.explanation, orderItem.getExplanation());
    }

    @Test
    public void should_Throw_Exception_When_OrderItem_Not_Found_in_Return() {
        // Arrange
        ReturnDTO returnDTO = new ReturnDTO(1, UUID.randomUUID(),
                "broken", "Screen not working");
        String expectedAnswer = "404 NOT_FOUND \"item not found\"";

        Product product = new Product();

        when(productRepository.findProductById(returnDTO.productId)).thenReturn(product);
        when(orderRepository.findById(returnDTO.orderId)).thenReturn(Optional.empty());

        // Act & Assert
        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> SUT.returnItem(returnDTO));
        assertEquals(expectedAnswer, thrown.getMessage());
    }

    @Test
    public void should_Accept_ToStock_Successfully() {
        // Arrange
        ReturnDTO returnDTO = new ReturnDTO();
        Product product = new Product();
        product.setStock(10.0);
        product.setPrice(100.0);
        Order order = new Order();
        order.setTotalPrice(500.0);
        OrderItem orderItem = new OrderItem();

        when(productRepository.findProductById(returnDTO.productId)).thenReturn(product);
        when(orderRepository.findById(returnDTO.orderId)).thenReturn(Optional.of(order));
        when(orderItemRepository.findByProductAndOrder(product, order)).thenReturn(orderItem);
        // Act
        SUT.acceptToStock(returnDTO);
        // Assert
        verify(orderItemRepository, times(1)).save(orderItem);
        verify(productRepository, times(1)).save(product);
        verify(orderRepository, times(1)).save(order);
        assertEquals("Returned", orderItem.getStatus());
        assertEquals(11.0, product.getStock());
        assertEquals(400.0, order.getTotalPrice());
    }

    @Test
    public void should_Throw_Exception_When_OrderItem_Not_Found_In_toStock() {
        // Arrange
        ReturnDTO returnDTO = new ReturnDTO();
        String expectedAnswer = "404 NOT_FOUND \"Failed in returning\"";

        Product product = new Product();
        product.setStock(10.0);
        product.setPrice(100.0);

        when(productRepository.findProductById(returnDTO.productId)).thenReturn(product);
        when(orderRepository.findById(returnDTO.orderId)).thenReturn(Optional.empty());
        // Act & Assert
        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> SUT.acceptToStock(returnDTO));
        assertEquals(expectedAnswer, thrown.getMessage());

    }

    @Test
    public void should_Accept_AcceptReturn_Successfully_and_not_toStock() {
        // Arrange
        ReturnDTO returnDTO = new ReturnDTO();
        Product product = new Product();
        product.setPrice(100.0);
        Order order = new Order();
        order.setTotalPrice(500.0);
        OrderItem orderItem = new OrderItem();

        when(productRepository.findProductById(returnDTO.productId)).thenReturn(product);
        when(orderRepository.findById(returnDTO.orderId)).thenReturn(Optional.of(order));
        when(orderItemRepository.findByProductAndOrder(product, order)).thenReturn(orderItem);
        // Act
        SUT.acceptReturn(returnDTO);
        // Assert
        verify(orderItemRepository, times(1)).save(orderItem);
        verify(productRepository, times(0)).save(product);
        verify(orderRepository, times(1)).save(order);
        assertEquals("Returned", orderItem.getStatus());
        assertEquals(400.0, order.getTotalPrice());
    }

    @Test
    public void should_Throw_Exception_When_OrderItem_Not_Found_In_acceptReturn() {
        // Arrange
        ReturnDTO returnDTO = new ReturnDTO();
        String expectedAnswer = "404 NOT_FOUND \"Failed in returning\"";

        Product product = new Product();
        product.setStock(10.0);
        product.setPrice(100.0);

        when(productRepository.findProductById(returnDTO.productId)).thenReturn(product);
        when(orderRepository.findById(returnDTO.orderId)).thenReturn(Optional.empty());
        // Act & Assert
        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> SUT.acceptReturn(returnDTO));
        assertEquals(expectedAnswer, thrown.getMessage());

    }


    @Test
    public void should_Return_Item_Decline_Successfully() {
        // Arrange
        ReturnDTO returnDTO = new ReturnDTO();

        Product product = new Product();
        Order order = new Order();
        OrderItem orderItem = new OrderItem();


        when(productRepository.findProductById(returnDTO.productId)).thenReturn(product);
        when(orderRepository.findById(returnDTO.orderId)).thenReturn(Optional.of(order));
        when(orderItemRepository.findByProductAndOrder(product, order)).thenReturn(orderItem);

        // Act
        SUT.declineReturn(returnDTO);

        // Assert

        verify(orderItemRepository, times(1)).save(orderItem);

        assertEquals("Return rejected", orderItem.getStatus());

    }

    @Test
    public void should_Throw_Exception_When_orderItem_notFound_in_decline() {
        // Arrange
        ReturnDTO returnDTO = new ReturnDTO(1, UUID.randomUUID(),
                "broken", "Screen not working");
        String expectedAnswer = "404 NOT_FOUND \"Failed in returning\"";

        Product product = new Product();

        when(productRepository.findProductById(returnDTO.productId)).thenReturn(product);
        when(orderRepository.findById(returnDTO.orderId)).thenReturn(Optional.empty());

        // Act & Assert
        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> SUT.declineReturn(returnDTO));
        assertEquals(expectedAnswer, thrown.getMessage());
    }
}
