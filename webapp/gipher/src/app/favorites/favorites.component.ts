import { Component, OnInit,  Input} from '@angular/core';
import {FavoriteService} from '../services/favorite.service';
import { Gif } from '../models/Gif';
import {UserService} from '../services/user.service';
import { User } from '../models/User';
import { parseTenorResponseForGifs } from '../util/tenorResponse.parser';
import { SharedfavoriteslistService } from '../services/sharedfavoriteslist.service';



@Component({
  selector: 'app-favorites',
  templateUrl: './favorites.component.html',
  styleUrls: ['./favorites.component.css']
})
export class FavoritesComponent implements OnInit {
  @Input() 
  favorites : string[];
  title: string = "Favorites";

  constructor(private favoriteService : FavoriteService, 
              private userService : UserService,
              private sharedFavoritesList: SharedfavoriteslistService) { }

  /*Fill the shared favorites list on start*/
  ngOnInit(): void {
    
    if(this.sharedFavoritesList.isEmpty) {
      this.favoriteService.getAllFavorites(this.userService.getUserSession()).subscribe(data => {
        this.sharedFavoritesList.favorites = parseTenorResponseForGifs(data, true);
      });
    }
  }

  markAsFavorite( gifUrl : string){
    this.favoriteService.checkifFavorite(this.userService.getUserSession(), gifUrl).subscribe(
      (data)=>{ this.favorites = data}
    )
  }
  
  getFavorites(): Gif[]{
    return this.sharedFavoritesList.favorites;
  }
}
