import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { User } from '../models/User';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-user-registration',
  templateUrl: './user-registration.component.html',
  styleUrls: ['./user-registration.component.css']
})
export class UserRegistrationComponent implements OnInit {

  form: FormGroup;
  message: string;
  imageData: string;

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
      let user: User = {
        username: this.form.get("username").value,
        password: this.form.get("password").value,
        image: this.imageData
      }
  
      this.userService.registerUser(user).subscribe(data => {
  
      }, (error) => {
        console.log(error)
      })
    }
  }

  onFileChange(event: any) {
    let reader = new FileReader();

    //making sure the event object holds the information we need
    if(event.target.files && event.target.files.length) {
      let imagePath = event.target.files[0];

      reader.onloadend = (e) => {       
        this.imageData = reader.result.toString().split(",")[1]; //image data is found after ',' in the result string
      }

      reader.readAsDataURL(imagePath);
    }
  }
}
