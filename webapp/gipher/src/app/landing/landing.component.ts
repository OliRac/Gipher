import { Component, OnInit } from '@angular/core';
import { Gif } from '../models/Gif';
import { RecommendationService } from '../services/recommendation.service';
import { parseTenorResponseForGifs } from '../util/tenorResponse.parser';


@Component({
  selector: 'app-landing',
  templateUrl: './landing.component.html',
  styleUrls: ['./landing.component.css']
})
export class LandingComponent implements OnInit {

  trendingGifs:Gif[];

  constructor(private recommendationService:RecommendationService) { }

  ngOnInit(): void {
    this.recommendationService.getTrending().subscribe(data => {
      this.trendingGifs = parseTenorResponseForGifs(data);
    })
  }
}
