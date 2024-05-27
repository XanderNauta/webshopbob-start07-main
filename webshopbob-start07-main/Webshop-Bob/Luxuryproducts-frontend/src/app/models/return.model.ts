import {Product} from "./product.model";

export class ReturnModel{

    public orderId: string
    public productId: number
    public product?: Product
    public reason: string
    public explanation: string
    public reserveorderId?: string;
}