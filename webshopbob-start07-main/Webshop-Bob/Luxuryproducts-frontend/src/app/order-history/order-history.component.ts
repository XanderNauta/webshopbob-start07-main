import { Component, OnInit } from '@angular/core';
import { OrderService } from '../services/order.service';
import { Order } from '../models/order.model';
import { AuthService } from '../auth/auth.service';
import { CurrencyPipe } from '@angular/common';
import { Customer } from '../models/customer.model';

@Component({
  selector: 'app-order-history',
  standalone: true,
  imports: [CurrencyPipe],
  templateUrl: './order-history.component.html',
  styleUrl: './order-history.component.scss'
})
export class OrderHistoryComponent implements OnInit{
  constructor(private orderService: OrderService, private authService: AuthService) {}

  public orderList: Order[];
  customer: Customer;

  ngOnInit() {
    this.orderService
    .getOrders()
    .subscribe((orders: Order[]) => {
        this.orderList = orders;
    });
    this.authService.getCurrentUser()
    .subscribe((customer: Customer) => {
      this.customer = customer;
    });
  }
}

