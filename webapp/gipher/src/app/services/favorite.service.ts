import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { User } from '../models/User';
import { UserService } from '../services/user.service';
import { UserGif } from '../models/UserGif';
import { Gif } from '../models/Gif';



@Injectable({
  providedIn: 'root'
})
export class FavoriteService {

  favoriteUrl : string ;
  userGif : UserGif;

  constructor(private http: HttpClient) { }

  getAllFavorites( user : User ): Observable<any>{
    let headers = new HttpHeaders().set('Authorization', "Bearer " + user.token);
           headers.append("Accept", "application/json");
    this.favoriteUrl = environment.FAVORITE_SERVICE_URL + `/favorites/${user.id}`
    return this.http.get(this.favoriteUrl,  {headers:headers} );
  }

  checkifFavorite(user : User, gifUrl: string): Observable<any>{
    this.userGif= {
      userId : user.id,
      gifUrl : gifUrl
    }
    let headers = new HttpHeaders().set('Authorization', "Bearer " + user.token);
           headers.append("Accept", "application/json");
    this.favoriteUrl = environment.FAVORITE_SERVICE_URL + `/favorite`
    return this.http.post(this.favoriteUrl,   {headers:headers});
  }

  emptyFavoriteList(user : User): Observable<any>{
    let headers = new HttpHeaders().set('Authorization', "Bearer " + user.token);
         headers.append("Accept", "application/json");
    this.favoriteUrl = environment.FAVORITE_SERVICE_URL + `/emptyFavoriteList/${user.id}`
    return this.http.delete(this.favoriteUrl,  {headers:headers});
  }

  removeFavorite(user : User, gifUrl : string): Observable<any>{
    this.userGif= {
      userId : user.id,
      gifUrl : gifUrl
    }
    let headers = new HttpHeaders().set('Authorization', "Bearer " + user.token);
       headers.append("Accept", "application/json");
    this.favoriteUrl = environment.FAVORITE_SERVICE_URL + `/removeFavorite`
    return this.http.put(this.favoriteUrl,this.userGif,  {headers:headers});
  }

  addFavorite(user : User, gifUrl : string): Observable<any>{
    this.userGif= {
      userId : user.id,
      gifUrl : gifUrl
    }
    let headers = new HttpHeaders().set('Authorization', "Bearer " + user.token);
    headers.append("Accept", "application/json");
    this.favoriteUrl = environment.FAVORITE_SERVICE_URL + `/addFavorite`
    return this.http.post(this.favoriteUrl, this.userGif,{headers:headers});
  }
}
