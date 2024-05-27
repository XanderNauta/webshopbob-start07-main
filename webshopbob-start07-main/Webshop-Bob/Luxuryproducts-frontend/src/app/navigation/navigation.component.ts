import {booleanAttribute, Component, OnInit} from '@angular/core';
import { Router, RouterModule } from '@angular/router';

import { CartService } from '../services/cart.service';
import { Product } from '../models/product.model';
import { CommonModule } from '@angular/common';
import { AuthService } from '../auth/auth.service';
import {Customer} from "../models/customer.model";
import {AdminAuthGuard} from "../auth/admin-auth.guard";
import {TokenService} from "../auth/token.service";

@Component({
  selector: 'app-navigation',
  standalone: true,
  imports: [RouterModule, CommonModule],
  templateUrl: './navigation.component.html',
  styleUrl: './navigation.component.scss'
})
export class NavigationComponent implements OnInit {
  public amountOfProducts: number = 0;
  public userIsLoggedIn: boolean = false;
  public userIsAdmin: boolean = false;
  public customer: Customer
  userClass = "dropdown-menu dropdown-menu-end"

  constructor(private cartService: CartService,
    public authService: AuthService,
    public tokenService: TokenService,
    private router: Router) {}

  ngOnInit() {
    this.cartService.$productInCart.subscribe((products: Product[]) => {
      this.amountOfProducts = products.length;
      this.checkLoginState();
    })
    this.authService.$userIsAdmin.subscribe((booleanAttribute)=>
        this.userIsAdmin = booleanAttribute
    )
  }

  public checkLoginState() {
    this.authService
    .$userIsLoggedIn
    .subscribe((loginState: boolean) => {
      this.userIsLoggedIn = loginState;
    });
  }

  public onLogout() {
    this.authService.logOut();
    this.router.navigate(['/'])
  }

  onToggleMenu() {
    if (this.userClass == "dropdown-menu dropdown-menu-end") {
      this.userClass = "dropdown-menu dropdown-menu-start"
    } else {
      this.userClass = "dropdown-menu dropdown-menu-end"
    }
  }
}
