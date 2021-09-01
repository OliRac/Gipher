import { Component, Input, OnInit } from '@angular/core';
import { Gif } from '../models/Gif';
import { TenorResponse } from '../models/TenorResponse';
import { User } from '../models/User';
import { RecommendationService } from '../services/recommendation.service';
import { UserService } from '../services/user.service';
import { parseTenorResponseForGifs } from '../util/tenorResponse.parser';

@Component({
  selector: 'app-recommended',
  templateUrl: './recommended.component.html',
  styleUrls: ['./recommended.component.css']
})
export class RecommendedComponent implements OnInit {
  recommendations: Gif[];
  title: string;
  refresh: boolean = false;
  user: User;

  /*When a user does a valid search, new recommendations need to be fetched!*/
  @Input()
  refreshEvent: Event;

  constructor(private recommendationService:RecommendationService, private userService: UserService) { }

  ngOnInit(): void {
    this.user = this.userService.getUserSession();
    this.getRecommendations(this.user);
  }

  ngOnChanges() {
    if(this.refreshEvent) {
      this.getRecommendations(this.user);
    }
  }

  getRecommendations(user: User) {
    this.recommendations = [];
    let response = this.recommendationService.getRecommendations(user);

    response.subscribe(data => {
      data.forEach(elem => {
        this.recommendations = this.recommendations.concat(parseTenorResponseForGifs(elem));
      });
      this.title = "Recommendations";
    }, error => {
      this.title = "No recommendations available yet, showing trending";
      this.getTrending();
    })
  }

  getTrending() {
    this.recommendations = [];
    let response = this.recommendationService.getTrending();
      
    response.subscribe(data => {
      this.recommendations = parseTenorResponseForGifs(data);
      console.log(this.recommendations);
    })
  }
}
