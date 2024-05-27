import { Routes } from '@angular/router';

import { CartComponent } from './cart/cart.component';
import { HomeComponent } from './home/home.component';
import { ProductsComponent } from './products/products.component';
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';
import { OrderHistoryComponent } from './order-history/order-history.component';
import {ReturnComponent} from "./return/return.component";
import {InfoComponent} from "./return/info/info.component";
import {ReturnAdminComponent} from "./return-admin/return-admin.component";
import {AdminAuthGuard} from "./auth/admin-auth.guard";
import {authGuard} from "./auth/auth.guard";

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'products', component: ProductsComponent},
  { path: 'cart', component: CartComponent },
  {path: 'orders', component: OrderHistoryComponent},
  {path: 'account/login', component: LoginComponent },
  {path: 'account/register', component: RegisterComponent},
  {path: 'return', component: ReturnComponent},
  {path: 'returnFormulier', component: InfoComponent },
  {path: 'returnAdmin', component: ReturnAdminComponent, canActivate:[AdminAuthGuard]}
];
