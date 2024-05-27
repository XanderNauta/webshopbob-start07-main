import { Component, OnInit } from '@angular/core';
import {CurrencyPipe, formatDate} from '@angular/common';

import { CartService } from '../services/cart.service';
import { Product } from '../models/product.model';
import { OrderService } from '../services/order.service';
import { AuthService } from '../auth/auth.service';
import { Order } from '../models/order.model';
import {CartItem} from "../models/cartItem.model";

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [CurrencyPipe],
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.scss'
})
export class CartComponent implements OnInit {
  public cartProducts: Product[];
  cartItems: CartItem[] = []
  cartItem: CartItem
  productsPrice: number;
  totalPrice: number;
  shippingCosts: number = 99.95;
  userIsLoggedIn: boolean;

  order: Order = {
  "cartItems": [],
  "totalPrice": 0,
  "orderDate": ""
  };

  constructor(private cartService: CartService,
    private orderService: OrderService,
    private authService: AuthService) {}

  ngOnInit() {
    this.cartProducts = this.cartService.allProductsInCart();
    this.cartService.$productInCart.subscribe((products: Product[]) => {
      this.cartProducts = products;
      this.getTotalPrice();
      this.checkLoginState();
    });
  }

  private makeDate(){
    const format = 'yyyy/MM/dd';
    const myDate = Date.now();
    const locale = 'nl';
    const formattedDate = formatDate(myDate, format, locale)
    return formattedDate
  }

  public removeProductFromCart(product_index: number) {
    this.cartService.removeProductFromCart(product_index);
  }

  getTotalPrice() {
    this.productsPrice = this.cartProducts.reduce((acc, curr) => acc + curr.price, 0)
    this.totalPrice = this.productsPrice + this.shippingCosts;
  }

  onPlaceOrder() {
    const format = 'yyyy/MM/dd';
    let myDate = Date.now();
    const locale = 'en';
    let formattedDate = formatDate(myDate, format, locale)

    if (this.cartProducts.length != 0) {

      for (let i = 0; i < this.cartProducts.length; i++){
          const cart = {"id": this.cartProducts[i].id,
                            "name": this.cartProducts[i].name,
                            "price": this.cartProducts[i].price
          }
          this.cartItems.push(cart)

      }

      this.order.cartItems = this.cartItems.slice();
      this.order.totalPrice = this.totalPrice;
      this.order.orderDate = formatDate(Date.now(), format, locale);
      this.orderService.placeOrder(this.order).subscribe();
      this.cartService.removeAllProductsFromCart();
      alert("Thank you for your order!")
    } else {
      alert("Please add items to your cart before placing an order.")
    }

  }

  public checkLoginState() {
    this.authService
    .$userIsLoggedIn
    .subscribe((loginState: boolean) => {
      this.userIsLoggedIn = loginState;
    });
  }
}