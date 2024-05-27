import {Component, OnInit} from '@angular/core';
import {Order} from "../models/order.model";
import {CartService} from "../services/cart.service";
import {OrderService} from "../services/order.service";
import {AuthService} from "../auth/auth.service";
import {Product} from "../models/product.model";
import {CurrencyPipe} from "@angular/common";
import {Observable, Subscription} from "rxjs";
import {resourceChangeTicket} from "@angular/compiler-cli/src/ngtsc/core";
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {RetourService} from "../services/retour.service";

@Component({
  selector: 'app-return',
  standalone: true,
  imports: [
    CurrencyPipe,
    RouterLink
  ],
  templateUrl: './return.component.html',
  styleUrl: './return.component.scss'
})
export class ReturnComponent implements OnInit {
  orderSubscription: Subscription
  userIsLoggedIn: boolean;
  allOrders: Order[]

  constructor(private cartService: CartService,
              private orderService: OrderService,
              private retourService : RetourService,
              private authService: AuthService,
              private route: Router,
              private router: ActivatedRoute) {
  }

  ngOnInit() {
    this.orderSubscription = this.retourService
        .returnableOrders()
        .subscribe(
            (value: Order[] ) =>
            {this.allOrders = value});

    this.checkLoginState();

  }

  public changeData(order: Order, productId: number, orderlist: Order[]){
    this.retourService.setorderId(order)
    this.retourService.setproductId(productId)
    this.retourService.setOrderList(orderlist)
  }

  public returnProduct(indexOrderItem: number){
    this.route.navigate(['/returnFormulier'])

  }

  public checkLoginState()  {

    this.authService
        .$userIsLoggedIn
        .subscribe((loginState: boolean) => {
          this.userIsLoggedIn = loginState;
        });
  }
}


