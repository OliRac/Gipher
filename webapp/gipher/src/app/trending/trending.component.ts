import { Component, OnInit } from '@angular/core';
import { Gif } from '../models/Gif';
import { TrendingService } from '../services/trending.service';

@Component({
  selector: 'app-trending',
  templateUrl: './trending.component.html',
  styleUrls: ['./trending.component.css']
})
export class TrendingComponent implements OnInit {

  trendingGifs:Gif[];

  constructor(private trendingService:TrendingService) { }

  ngOnInit(): void {
    this.trendingGifs = this.trendingService.getGifs();
  }
}
