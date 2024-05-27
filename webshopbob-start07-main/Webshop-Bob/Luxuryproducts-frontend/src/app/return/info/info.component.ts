import {Component, OnInit} from '@angular/core';
import {FormArray, FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {ActivatedRoute, ParamMap, Params, Router} from "@angular/router";
import {OrderService} from "../../services/order.service";
import {ReturnModel} from "../../models/return.model";
import {OrderItem} from "../../models/orderItem.model";
import {RetourService} from "../../services/retour.service";
import {Order} from "../../models/order.model";
import {Product} from "../../models/product.model";
import {of} from "rxjs";

@Component({
  selector: 'app-info',
  standalone: true,
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: 'info.component.html',
  styleUrl: './info.component.scss'
})
export class InfoComponent implements OnInit{
  id: number;
  returnForm: FormGroup
  order: Order;
  orderList: Order[];
  productId: number;
  product: Product;





  constructor(private route: ActivatedRoute,
              private orderService: OrderService,
              private router: Router,
              private retourService: RetourService) {
    this.retourService.getOrderId.subscribe(msg => this.order = msg)
    this.retourService.getProductId.subscribe(msg => this.productId = msg)
    this.retourService.getOrderList.subscribe(msg => this.orderList = msg)
  }
   ngOnInit(): void {
    this.returnForm = new FormGroup({
        'reason':new FormControl('broken', Validators.required),
        'explanation': new FormControl('What is wrong?', Validators.required)
    })
  }


  onSubmit(){

    if (this.order.id != null) {

      let returnInfo: ReturnModel = {
        "productId": this.productId,
        "orderId": this.order.id?.toString(),
        "reason": this.returnForm.get('reason')?.value,
        "explanation": this.returnForm.get('explanation')?.value
      }
      this.retourService.returnItem(returnInfo).subscribe()
      alert("Thank you for your return!")
      this.onCancel();
    } else {
      alert("Somting went wrong")
    }



  }

  onCancel(){
    this.router.navigate(['/return'])
  }
}
