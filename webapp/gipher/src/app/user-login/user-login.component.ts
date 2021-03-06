import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { timer } from 'rxjs';
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
  timer: number = 3;
  showSpinner: boolean = false;

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
        //data contains the JWT followed by the user object

        let loggedIn: User = {
          username: data.user.username,
          password: data.user.password,
          imageUrl: data.user.photo,
          id: data.user.userId,
          token: data.jwtToken
        }
        
        this.userService.setUserSession(loggedIn);
    
        setInterval(() => {
          this.showSpinner = true;
          this.timer--;

          if(this.timer == 0) {
            this.router.navigate(["/dashboard"])
          }
  
        }, 1000)

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
