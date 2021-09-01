import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Gif } from '../models/Gif';
import { RecommendationService } from '../services/recommendation.service';
import { UserService } from '../services/user.service';
import { parseTenorResponseForGifs } from '../util/tenorResponse.parser';


@Component({
  selector: 'app-landing',
  templateUrl: './landing.component.html',
  styleUrls: ['./landing.component.css']
})
export class LandingComponent implements OnInit {

  trendingGifs:Gif[];

  constructor(private recommendationService:RecommendationService, private userService: UserService, private router: Router) { }

  ngOnInit(): void {
    if(this.userService.getUserSession()) {
      this.router.navigate(["/dashboard"]);
    }

    this.recommendationService.getTrending().subscribe(data => {
      this.trendingGifs = parseTenorResponseForGifs(data);
    })
  }
}
