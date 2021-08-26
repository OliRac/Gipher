import { HttpClient, HttpHeaders} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { User } from '../models/User';


@Injectable({
  providedIn: 'root',
})
export class SearchService {
  storedUser: User = JSON.parse(sessionStorage.getItem('user'));
  searchURL: string;

  constructor(private http: HttpClient) {}

  storeUserSearchTermWithUserId(
    searchTerm: string,
    id: number
  ): Observable<any> {
    this.searchURL =
      environment.SEARCH_SERVICE_URL + `/searchTerm/${searchTerm}/userId/${id}`;

    return this.http.get(this.searchURL);
  }
  searchGif(searchTerm: string): Observable<any> {
    var header = {
    headers: new HttpHeaders().set('Authorization', `Bearer ${this.storedUser.token}`)}
    this.searchURL = environment.SEARCH_SERVICE_URL + `/gifs/${searchTerm}`;
    return this.http.get(this.searchURL, header);
  }
}
