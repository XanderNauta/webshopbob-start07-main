import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from './auth.service';
import { switchMap } from 'rxjs';
import {Customer} from "../models/customer.model";

export const AdminAuthGuard: CanActivateFn = (route, state) => {

    const router: Router = inject(Router);
    const authService: AuthService = inject(AuthService);

    return authService.getCurrentUser().pipe(
        switchMap((customer: Customer) => {
            if (customer && customer.role == "Admin") {
                return [true];
            } else {
                router.navigate([""])
                return [false];
            }
        })
    );
}