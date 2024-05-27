import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';

import { environment } from '../../environments/environment';
import { Product } from '../models/product.model';
import { Order } from '../models/order.model';

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  private baseUrl: string = environment.base_url + "/orders";
  products: Product[] = [];

  constructor(private http: HttpClient) {}

  public placeOrder(order: Order): Observable<Order> {
    return this.http.post<Order>(this.baseUrl, order)
  }

  public getOrders(): Observable<Order[]> {
    return this.http.get<Order[]>(this.baseUrl)
  }

  public getOrder(id: number): Observable<Order[]> {
    return this.http.get<Order[]>(`${this.baseUrl}/${id}`)
  }
}
