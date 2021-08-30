import { Injectable } from '@angular/core';
import { Gif } from '../models/Gif';

import { PLACEHOLDER_GIFS } from "../../../data/gifs";
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TenorResponse } from '../models/TenorResponse';

@Injectable({
  providedIn: 'root'
})
export class TrendingService {

  trendingURL: string = environment.RECOMM_SERVICE_URL + "/trending";

  constructor(private http:HttpClient) { }

  getGifs(max?: number): Observable<TenorResponse> {
    return this.http.get(this.trendingURL) as Observable<TenorResponse>;
  }
}
