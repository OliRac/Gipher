import { Injectable } from '@angular/core';

import { User } from '../models/User';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  url: string = "http://localhost:8080/register"
  //url: string = "http://localhost:3000/users"

  constructor(private http: HttpClient) { }

  registerUser(user: User): Observable<any> {
    let formData: FormData = new FormData();

    formData.append("username", user.username);
    formData.append("password", user.password);
    formData.append("img", user.image);

    let headers = new HttpHeaders();
    headers.append("Content-Type","multipart/form-data");
    headers.append("Accept", "application/json");

    let options = {
      headers: headers,
    }

    return this.http.post(this.url, formData, options);
  }

  login(user: User): Observable<any> {
    return null;
  }
}
