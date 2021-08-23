import { Injectable } from '@angular/core';

import { User } from '../models/User';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  registerURL: string = environment.USER_SERVICE_URL + "/auth/register";
  loginURL: string = environment.USER_SERVICE_URL + "/auth/login";

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

    return this.http.post(this.registerURL, formData, options);
  }

  login(user: User): Observable<any> {
    let headers = new HttpHeaders();

    headers.append('Content-Type','application/json');
    headers.append("Accept", "application/json");

    let options = {
      headers: headers,
    }

    return this.http.post(this.loginURL, user, options);
  }
}
