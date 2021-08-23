import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { User } from '../models/User';

@Injectable({
  providedIn: 'root'
})
export class SearchService {
  storedUser: User = JSON.parse(sessionStorage.getItem("user"));
  searchURL: string;


  constructor(private http: HttpClient) { }

  searchGifs(searchTerm: string): Observable<any>{
    this.searchURL = environment.SEARCH_SERVICE_URL + `/searchTerm/${searchTerm}/userId/${this.storedUser.id}`
    
    return this.http.get(this.searchURL);
  }

}
