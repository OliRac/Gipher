import { HttpClient, HttpHeaders} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { User } from '../models/User';
import { UserTerm } from '../models/UserTerm';


@Injectable({
  providedIn: 'root',
})
export class SearchService {
  storedUser: User = JSON.parse(sessionStorage.getItem('user'));
  searchURL: string;

  constructor(private http: HttpClient) {}

  
  storeUserSearchTermWithUserId(user: UserTerm): Observable<any> {

    let header = new HttpHeaders().set("Authorization", `Bearer ${this.storedUser.token}`);
    header.set('Content-Type', 'application/json');
    this.searchURL = environment.SEARCH_SERVICE_URL + `/gifs/search`;

    return this.http.post(this.searchURL, user, {headers:header});
  }

  searchGif(searchTerm: string): Observable<any> {
    var header = {
    headers: new HttpHeaders().set('Authorization', `Bearer ${this.storedUser.token}`)};
    this.searchURL = environment.SEARCH_SERVICE_URL + `/gifs/${searchTerm}`;
    return this.http.get(this.searchURL, header);
  }

}
