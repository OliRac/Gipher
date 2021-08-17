import { Injectable } from '@angular/core';

import { User } from '../models/User';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  url: string = "TBD"

  constructor(private http: HttpClient) { }

  registerUser(user: User): Observable<any> {
    return null;
  }

  login(user: User): Observable<any> {
    return null;
  }
}
