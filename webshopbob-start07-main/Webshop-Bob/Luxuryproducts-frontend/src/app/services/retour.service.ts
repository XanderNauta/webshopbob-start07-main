import {Injectable} from "@angular/core";
import {Order} from "../models/order.model";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {BehaviorSubject, Observable} from "rxjs";
import {ReturnModel} from "../models/return.model";

@Injectable({
    providedIn: 'root'
})
export class RetourService {
    private baseUrl: string = environment.base_url + "/retour";
    private orderItemId = new BehaviorSubject(0)
    private productId  = new BehaviorSubject(0)
    private order: BehaviorSubject<Order>  = new BehaviorSubject(new Order())
    private orderList = new BehaviorSubject([new Order()])

    constructor(private http: HttpClient) {}

    getOrderList = this.orderList.asObservable()
    getOrderItemId = this.orderItemId.asObservable()
    getProductId = this.productId.asObservable()
    getOrderId = this.order.asObservable()


    setorderItemId(value: number) {
        this.orderItemId.next(value)
    }

    setproductId(value: number) {
       this.productId.next(value)
    }

    setorderId(value: Order) {
        this.order.next(value);
    }

    setOrderList(value: Order[]) {
        this.orderList.next(value);
    }

    public returnableOrders() : Observable<Order[]>{
    return this.http.get<Order[]>(this.baseUrl)
    }

    public returnItem(returnModel: ReturnModel): Observable<ReturnModel>{
        return this.http.post<ReturnModel>(this.baseUrl + "/return", returnModel)
    }

    public getRequests(): Observable<ReturnModel[]> {
        return this.http.get<ReturnModel[]>(this.baseUrl + "/requests")
    }

    public toStock(returnModel: ReturnModel): Observable<ReturnModel>{
        return this.http.post<ReturnModel>(this.baseUrl + "/return/tostock", returnModel)
    }

    public acceptReturn(returnModel: ReturnModel): Observable<ReturnModel>{
        return this.http.post<ReturnModel>(this.baseUrl + "/return/accept", returnModel)
    }

    public declineReturn(returnModel: ReturnModel): Observable<ReturnModel>{
        return this.http.post<ReturnModel>(this.baseUrl + "/return/decline", returnModel)
    }
}