import { Component, Input, OnInit } from '@angular/core';
import { Gif } from '../models/Gif';
import { MatGridList } from '@angular/material/grid-list';

@Component({
  selector: 'app-gif-grid',
  templateUrl: './gif-grid.component.html',
  styleUrls: ['./gif-grid.component.css']
})
export class GifGridComponent implements OnInit {

  @Input()
  gifs: Gif[];

  constructor() { }

  ngOnInit(): void {

  }
}
