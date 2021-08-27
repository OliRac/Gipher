import { Component, Input, OnInit } from '@angular/core';
import { Gif } from '../models/Gif';
import { MatGridList } from '@angular/material/grid-list';
import {FavoriteService} from '../services/favorite.service';

@Component({
  selector: 'app-gif-grid',
  templateUrl: './gif-grid.component.html',
  styleUrls: ['./gif-grid.component.css']
})
export class GifGridComponent implements OnInit {

  @Input() gifs: Gif[];
  @Input() infoText : string;
  gifASFavorite : boolean
  Favorites : string[]

  constructor(private  favoriteService : FavoriteService)  { }

  ngOnInit(): void {
  }

  markAsFavorite( gifUrl : string){
    this.favoriteService.checkifFavorite(gifUrl).subscribe(
      (data)=>{ this.gifASFavorite = data},
      (error : any)=>{ this.gifASFavorite= false}
    )
  }

  getAllFavorites(){
    this.favoriteService.getAllFavorites().subscribe(data => {
      this.Favorites = data;
    },
    (error)=>{console.log(error)})
  }

}
