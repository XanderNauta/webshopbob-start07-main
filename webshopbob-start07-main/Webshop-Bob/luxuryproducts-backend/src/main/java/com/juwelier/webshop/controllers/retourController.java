package com.juwelier.webshop.controllers;

import com.juwelier.webshop.dao.OrderDAO;
import com.juwelier.webshop.dao.RetourDAO;
import com.juwelier.webshop.dto.ReturnDTO;
import com.juwelier.webshop.models.Order;
import com.juwelier.webshop.models.OrderItem;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "http://s1130782.student.inf-hsleiden.nl:10782"})
@RequestMapping("/retour")
public class retourController {

    private OrderDAO orderDAO;

    private RetourDAO retourDAO;

    public retourController(OrderDAO orderDAO, RetourDAO retourDAO) {
        this.orderDAO = orderDAO;
        this.retourDAO = retourDAO;
    }

    @GetMapping
    public ResponseEntity<List<Order>> getRetours(){

    return ResponseEntity.ok(this.orderDAO.getAllRetunableOrders());
    }

    @GetMapping("/requests")
    public ResponseEntity<List<OrderItem>> getAllRequests(){
        return ResponseEntity.ok(this.retourDAO.getAllRequests());
    }

    @PostMapping("/return")
    public ResponseEntity<String> returnItem(@RequestBody ReturnDTO returnDTO) {

        this.retourDAO.returnItem(returnDTO);


        return ResponseEntity.ok("return is in handling");
    }

    @PostMapping("/return/tostock")
    public ResponseEntity<String> acceptToStock(@RequestBody ReturnDTO returnDTO) {
        this.retourDAO.acceptToStock(returnDTO);
     return ResponseEntity.ok("return is done");
    }

    @PostMapping("/return/accept")
    public ResponseEntity<String> acceptReturn(@RequestBody ReturnDTO returnDTO){
        this.retourDAO.acceptReturn(returnDTO);
        return ResponseEntity.ok("Return is done");
    }

    @PostMapping("/return/decline")
    public ResponseEntity<String> declineReturn(@RequestBody ReturnDTO returnDTO){
        this.retourDAO.declineReturn(returnDTO);
        return ResponseEntity.ok("Return rejected");
    }

}
