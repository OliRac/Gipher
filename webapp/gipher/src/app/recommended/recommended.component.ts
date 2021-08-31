import { Component, OnInit } from '@angular/core';
import { Gif } from '../models/Gif';
import { TenorResponse } from '../models/TenorResponse';
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

  constructor(private recommendationService:RecommendationService, private userService: UserService) { }

  ngOnInit(): void {
    this.recommendations = [];
    
    let response = this.recommendationService.getRecommendations(this.userService.getUserSession());

    response.subscribe(data => {
      data.forEach(elem => {
        this.recommendations = this.recommendations.concat(parseTenorResponseForGifs(elem));
      });
    }, error => {
      
    })
  }
}
