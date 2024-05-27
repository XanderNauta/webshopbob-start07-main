import { Customer } from "./customer.model";
import { Product } from "./product.model";
import {CartItem} from "./cartItem.model";
import {OrderItem} from "./orderItem.model";

export class Order {
  public id?: number;
  public customer?: Customer;
  public orderItems?: OrderItem[];
  public products?: Product[]
  public cartItems?: CartItem[];
  public totalPrice: number;
  public orderStatus?: string;
  public orderDate: string;
}
