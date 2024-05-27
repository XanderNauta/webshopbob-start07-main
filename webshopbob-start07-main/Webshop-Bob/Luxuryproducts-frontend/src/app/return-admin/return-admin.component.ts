import {Component, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {Order} from "../models/order.model";
import {CartService} from "../services/cart.service";
import {OrderService} from "../services/order.service";
import {RetourService} from "../services/retour.service";
import {AuthService} from "../auth/auth.service";
import {ActivatedRoute, Router} from "@angular/router";
import {ReturnModel} from "../models/return.model";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-return-admin',
  standalone: true,
  imports: [
    FormsModule
  ],
  templateUrl: './return-admin.component.html',
  styleUrl: './return-admin.component.scss'
})
export class ReturnAdminComponent implements OnInit{
  orderSubscription: Subscription
  userIsLoggedIn: boolean;
  returnModels: ReturnModel[]
  searchTerm: string = "";

  constructor(private cartService: CartService,
              private orderService: OrderService,
              private retourService : RetourService,
              private authService: AuthService,
              private route: Router,
              private router: ActivatedRoute) {
  }

  ngOnInit() {
    this.orderSubscription = this.retourService
        .getRequests()
        .subscribe(
            (value: ReturnModel[] ) =>
            {this.returnModels = value});

    this.checkLoginState();

  }

  public checkLoginState()  {

    this.authService
        .$userIsLoggedIn
        .subscribe((loginState: boolean) => {
          this.userIsLoggedIn = loginState;
        });
  }

  toStock(productNumber: number | undefined , orderNumber: string | undefined , reason: string, explanation: string){
    if( productNumber != null && orderNumber != null) {
      let returnInfo: ReturnModel = {
        "productId": productNumber,
        "orderId": orderNumber,
        "reason": reason,
        "explanation": explanation
      }
      this.retourService.toStock(returnInfo).subscribe()
      window.location.reload();
    }
  }

  acceptReturn (productNumber: number | undefined , orderNumber: string | undefined , reason: string, explanation: string){
    if( productNumber != null && orderNumber != null) {
      let returnInfo: ReturnModel = {
        "productId": productNumber,
        "orderId": orderNumber,
        "reason": reason,
        "explanation": explanation
      }
      this.retourService.acceptReturn(returnInfo).subscribe()
      window.location.reload();
    }
  }

  declineReturn(productNumber: number | undefined , orderNumber: string | undefined , reason: string, explanation: string){
    if( productNumber != null && orderNumber != null) {
      let returnInfo: ReturnModel = {
        "productId": productNumber,
        "orderId": orderNumber,
        "reason": reason,
        "explanation": explanation
      }
      this.retourService.declineReturn(returnInfo).subscribe()
      window.location.reload();
    }
  }

  onSearch() {
    this.returnModels = this.returnModels.filter((returnModel: ReturnModel) => {
      if (returnModel.reserveorderId != null) {

        return returnModel.reserveorderId.toLowerCase().includes(this.searchTerm.toLowerCase());
      }
      return null

    });
  }

}
