import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { TenorResponse } from '../models/TenorResponse';
import { User } from '../models/User';

@Injectable({
  providedIn: 'root'
})
export class RecommendationService {

  trendingURL: string = environment.RECOMM_SERVICE_URL + "/trending";
  recommendationURL: string = environment.RECOMM_SERVICE_URL + "/recommendation"

  constructor(private http:HttpClient) { }

  getTrending(): Observable<TenorResponse> {
    return this.http.get(this.trendingURL) as Observable<TenorResponse>;
  }

  getRecommendations(user: User): Observable<any> {
    let headers = new HttpHeaders().set('Authorization', "Bearer " + user.token);
    headers.append("Accept", "application/json");

    return this.http.post(this.recommendationURL, user.id, {headers:headers});
  }
}
