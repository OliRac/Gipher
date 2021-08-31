import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { User } from '../models/User';
import { UserService } from '../services/user.service';

@Injectable({
  providedIn: 'root'
})
export class LoginGuard implements CanActivate {

  constructor(private router: Router, private userService: UserService) {};

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

//
/*       let mockUser = {
        userId: 1,
        username: "someuser",
        password: "difficultPassword",
        image: new File([""], "my_profile_pic.png")
      };
         sessionStorage.setItem("user", JSON.stringify(mockUser)) ;
             return true*/

    if(this.userService.getUserSession()) {
      return true;
    } else {
      this.router.navigate(["/landing"])
    }
  }
}
