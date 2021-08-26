import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { User } from '../models/User';

@Injectable({
  providedIn: 'root'
})
export class LoginGuard implements CanActivate {

  constructor(private router: Router) {};

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

//
       let mockUser = {
        userId: 1,
        username: "someuser",
        password: "difficultPassword",
        image: new File([""], "my_profile_pic.png")
      };
         sessionStorage.setItem("user", JSON.stringify(mockUser)) ;
             return true

//
//     let isAuth: boolean = sessionStorage.getItem("user") != null;
//
//     if(isAuth) {
//       return true;
//     } else {
//       this.router.navigate(["/landing"])
//     }

  }

}
