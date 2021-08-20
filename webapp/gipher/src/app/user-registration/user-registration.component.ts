import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { messages } from '../messages/registration.messages';
import { User } from '../models/User';
import { UserService } from '../services/user.service';

const MAX_FILE_SIZE = 10485760  //10 MB;
const PASSWORD_MIN_SIZE = 8
const PASSWORD_MAX_SIZE = 25
const USERNAME_MIN_SIZE = 3
const USERNAME_MAX_SIZE = 25

@Component({
  selector: 'app-user-registration',
  templateUrl: './user-registration.component.html',
  styleUrls: ['./user-registration.component.css']
})
export class UserRegistrationComponent implements OnInit {

  form: FormGroup;
  errorMsg: string;
  imageData: File;

  constructor(private formBuilder: FormBuilder, private userService: UserService, private router: Router) {
    this.form = this.formBuilder.group({
      username: new FormControl("", [Validators.required, Validators.maxLength(USERNAME_MAX_SIZE), Validators.minLength(USERNAME_MIN_SIZE)]),
      password: new FormControl("", [Validators.required, Validators.maxLength(PASSWORD_MAX_SIZE), Validators.minLength(PASSWORD_MIN_SIZE)])
    })
  }

  ngOnInit(): void {

  }

  /*Validate form data, call registerUser from the userService*/
  onSubmit(): void {
    if(!this.imageData) {
      this.errorMsg = messages.IMAGE_MISSING;
      return;
    }

    if(this.form.valid) {
      let user: User = {
        username: this.username.value,
        password: this.password.value,
        image: this.imageData
      }

      this.userService.registerUser(user).subscribe(data => {
        this.errorMsg = messages.REGISTER_SUCCESS;
        //more logic later with JWT / auth
        //wait 5 sec and redirect to login
        setTimeout(() => {
          this.router.navigate(["/"])
        }, 5000);
      }, (error: HttpErrorResponse) => {
        this.errorMsg = error.error;
      })
    }
  }

  onFileChange(event: any) {
    let imgFile = event.target.files[0]

    //making sure the event object holds the information we need
    if(imgFile && imgFile.size < MAX_FILE_SIZE) {
      this.imageData = imgFile;   //typescript does not like me calling this.form.get("").setValue() here :(
    } else {
      this.errorMsg = messages.IMAGE_TOO_BIG;
      event.target.value = null;
    }
  }

  get username() {
    return this.form.get("username");
  }

  get password() {
    return this.form.get("password");
  }
}
