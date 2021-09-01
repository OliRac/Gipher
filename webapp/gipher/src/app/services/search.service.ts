import { HttpClient, HttpHeaders} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { User } from '../models/User';
import { UserTerm } from '../models/UserTerm';
import { UserService } from './user.service';


@Injectable({
  providedIn: 'root',
})
export class SearchService {
  searchURL: string;

  constructor(private http: HttpClient, private userService: UserService) {}

  
  storeUserSearchTermWithUserId(user: UserTerm): Observable<any> {
    let header = new HttpHeaders().set("Authorization", `Bearer ${this.userService.getUserSession().token}`);
    header.set('Content-Type', 'application/json');
    this.searchURL = environment.SEARCH_SERVICE_URL + `/gifs/search`;

    return this.http.post(this.searchURL, user, {headers:header});
  }

  searchGif(searchTerm: string): Observable<any> {
    var header = {
    headers: new HttpHeaders().set('Authorization', `Bearer ${this.userService.getUserSession().token}`)};
    this.searchURL = environment.SEARCH_SERVICE_URL + `/gifs/${searchTerm}`;
    return this.http.get(this.searchURL, header);
  }

}
