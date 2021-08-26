import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { User } from '../models/User';

@Injectable({
  providedIn: 'root'
})
export class FavoriteService {

  storedUser: User = JSON.parse(sessionStorage.getItem("user"));
  favoriteUrl : string ;

  constructor(private http: HttpClient) { }

  getAllFavorites(): Observable<any>{
    this.favoriteUrl = environment.FAVORITE_SERVICE_URL + `/favorites/${this.storedUser.id}`
    return this.http.get(this.favoriteUrl);
  }

  checkifFavorite(gifUrl: string): Observable<any>{
    this.favoriteUrl = environment.FAVORITE_SERVICE_URL + `/favorite/${this.storedUser.id}/${gifUrl}`
    return this.http.get(this.favoriteUrl);
  }

  emptyFavoriteList(): Observable<any>{
    this.favoriteUrl = environment.FAVORITE_SERVICE_URL + `/emptyFavoriteList/${this.storedUser.id}`
    return this.http.delete(this.favoriteUrl);
  }

  removeFavorite(gifUrl : string): Observable<any>{
    const headers = { 'content-type': 'application/json'} 
    const body= null     //JSON.stringify(person);
    this.favoriteUrl = environment.FAVORITE_SERVICE_URL + `/removeFavorite/${this.storedUser.id}/${gifUrl}`
    return this.http.post(this.favoriteUrl,   body,{'headers':headers});
  }

  addFavorite(gifUrl : string): Observable<any>{
    const headers = { 'content-type': 'application/json'} 
    const body= null     //JSON.stringify(person);
    this.favoriteUrl = environment.FAVORITE_SERVICE_URL + `/addFavorite/${this.storedUser.id}/${gifUrl}`
    return this.http.post(this.favoriteUrl,  body,{'headers':headers});
  }





}
