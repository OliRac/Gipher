import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-user-registration',
  templateUrl: './user-registration.component.html',
  styleUrls: ['./user-registration.component.css']
})
export class UserRegistrationComponent implements OnInit {

  form: FormGroup;
  message: string;

  constructor(private formBuilder: FormBuilder, private userService: UserService) {
    this.form = this.formBuilder.group({
      username: new FormControl("", [Validators.required]),
      password: new FormControl("", [Validators.required]),
      image: new FormControl("", [Validators.required])
    })
  }

  ngOnInit(): void {

  }

  /*Validate form data, call registerUser from the userService*/
  onSubmit(): void {
    if(this.form.valid) {
      console.log("lol");
    }
  }
}
