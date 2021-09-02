import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  isAuth: boolean =  true ;
  image: string;

  constructor(private userService:UserService) { }

  ngOnInit(): void {
    let user = this.userService.getUserSession();

    if(this.userService.getUserSession()) {
      this.image = user.imageUrl
    }
  }
}
