import { Component, OnInit } from '@angular/core';
import { Gif } from '../models/Gif';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { parseTenorResponseForGifs } from '../util/tenorResponse.parser';
import { RecommendationService } from '../services/recommendation.service';
import { UserService } from '../services/user.service';
import { User } from '../models/User';


@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  username: string;
  imageUrl: string;

  /*For passing a click event from sibling to sibling components*/
  clickedEvent: Event;
  
  constructor( private userService: UserService, private router: Router) {

  }

  ngOnInit(): void {
    this.username = this.userService.getUserSession().username;
    this.imageUrl = this.userService.getUserSession().imageUrl;
  }

  onClickLogOut(): void {
    sessionStorage.clear();
    this.router.navigate(['/landing']);
  }

  /*For passing a click event from sibling to sibling components*/
  validSearchDone(event:Event) {
    this.clickedEvent = event;
  }
}
