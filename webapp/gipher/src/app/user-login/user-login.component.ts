import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { connectableObservableDescriptor } from 'rxjs/internal/observable/ConnectableObservable';
import { messages } from '../messages/registration.messages';
import { User } from '../models/User';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-user-login',
  templateUrl: './user-login.component.html',
  styleUrls: ['./user-login.component.css']
})
export class UserLoginComponent implements OnInit {

  form: FormGroup;
  errorMsg: string;

  constructor(private userService: UserService, private formBuilder: FormBuilder, private router: Router) {
    this.form = this.formBuilder.group({
      username: new FormControl("", [Validators.required]),
      password: new FormControl("", [Validators.required])
    })
  }

  ngOnInit(): void {

  }

  onSubmit(): void {
    if(this.form.valid) {
      let user: User = {
        username: this.username.value,
        password: this.password.value
      }

      this.userService.login(user).subscribe(data => {
        //Need to get in touch to figure out what is returned with login
        //how jwt token will be managed (local storage, cookie...)

        let loggedIn: User = {
          username: data.user.username,
          password: data.user.password,
          image: data.user.photo,
          id: data.user.userId,
          token: data.jwtToken
        }
        
        sessionStorage.setItem("user", JSON.stringify(loggedIn));

        this.errorMsg = messages.LOGIN_GREET;

        setTimeout(() => {
          this.router.navigate(["/"])
        }, 5000);
      }, error => {
        this.errorMsg = error.error;
      })
    }
  }

  get username() {
    return this.form.get("username");
  }

  get password() {
    return this.form.get("password");
  }
}
