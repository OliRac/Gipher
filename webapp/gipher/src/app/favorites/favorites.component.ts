import { Component, OnInit,  Input} from '@angular/core';
import {FavoriteService} from '../services/favorite.service';
import { Gif } from '../models/Gif';
import {UserService} from '../services/user.service';
import { User } from '../models/User';
import { parseTenorResponseForGifs } from '../util/tenorResponse.parser';



@Component({
  selector: 'app-favorites',
  templateUrl: './favorites.component.html',
  styleUrls: ['./favorites.component.css']
})
export class FavoritesComponent implements OnInit {
  @Input() 
  favorites : string[];
  user : User;
  title: string;

  constructor(private favoriteService : FavoriteService, private userService : UserService) { }

  ngOnInit(): void {
    this.user = this.userService.getUserSession();
    this.favoriteService.getAllFavorites(this.user).subscribe(data => {
          this.favoriteService.setFavoritesList(parseTenorResponseForGifs(data, true));
          this.title="Favorites";
    }, error => {
      this.title = "You have no favorites!";
    })
  }

  markAsFavorite( gifUrl : string){
      this.favoriteService.checkifFavorite(this.user, gifUrl).subscribe(
        (data)=>{ this.favorites = data}
      )
  }
  
  getFavorites(): Gif[]{
    return this.favoriteService.getFavoritesList(this.userService.getUserSession());
  }
}
