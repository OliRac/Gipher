import { Component, OnInit } from '@angular/core';
import { Gif } from '../models/Gif';
import { TrendingService } from '../services/trending.service';


@Component({
  selector: 'app-landing',
  templateUrl: './landing.component.html',
  styleUrls: ['./landing.component.css']
})
export class LandingComponent implements OnInit {

  trendingGifs:Gif[];

  constructor(private trendingService:TrendingService) { }

  ngOnInit(): void {
    this.trendingGifs = this.trendingService.getGifs();
  }

}
