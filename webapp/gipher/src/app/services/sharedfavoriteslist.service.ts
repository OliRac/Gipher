import { Injectable, Optional, SkipSelf } from '@angular/core';
import { Gif } from '../models/Gif';
import { parseTenorResponseForGifs } from '../util/tenorResponse.parser';
import { FavoriteService } from './favorite.service';
import { UserService } from './user.service';

@Injectable({
  providedIn: 'root'
})
export class SharedfavoriteslistService {

  private favoritesList: Gif[];

  constructor() { 
    if(!this.favoritesList) {
      this.favoritesList = [];
    }
  }

  get favorites() {
    return this.favoritesList;
  }

  set favorites(gifs: Gif[]) {
    this.favoritesList = gifs;
  }

  addToFavorites(gif:Gif): void {
    this.favoritesList.push(gif);
  }

  removeFromFavorites(gif: Gif): void {
    this.favoritesList = this.favorites.filter(elem => elem.id !== gif.id);
  }

  resetFavorites(): void {
    this.favoritesList = [];
  }

  isEmpty(): boolean {
    return !this.favorites || this.favoritesList.length == 0;
  }
}
