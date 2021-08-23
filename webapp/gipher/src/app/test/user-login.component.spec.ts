import { HttpClientModule, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { of, throwError } from 'rxjs';
import { messages } from '../messages/registration.messages';
import { User } from '../models/User';
import { UserService } from '../services/user.service';

import { UserLoginComponent } from '../user-login/user-login.component';

describe('UserLoginComponent', () => {
  let component: UserLoginComponent;
  let fixture: ComponentFixture<UserLoginComponent>;
  let userService: UserService;

  let mockUser = {
    userId: 1,
    username: "someuser",
    password: "difficultPassword",
    image: new File([""], "my_profile_pic.png")
  }

  let mockToken = "my token"

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientModule, ReactiveFormsModule, RouterModule.forRoot([])],
      declarations: [ UserLoginComponent ],
      providers: [UserService]
    })
    .compileComponents();
    userService = TestBed.inject(UserService);
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UserLoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  /*tests:  
  missing username
  missing password
  non-existing username,
  existing username bad password 
  existing user */

  it("should show message if username is missing", () => {
    let usernameRequired = fixture.nativeElement.querySelector("#usernameRequired");
    expect(usernameRequired).toBeTruthy();
    expect(usernameRequired.textContent).toEqual(messages.USERNAME_MISSING);
  });

  it("should show message if password is missing", () => {
    let passwordRequired = fixture.nativeElement.querySelector("#passwordRequired");
    expect(passwordRequired).toBeTruthy();
    expect(passwordRequired.textContent).toEqual(messages.PASSWORD_MISSING);
  })

  it("should show message if user does not exist", () => {
    let error = new HttpErrorResponse({
      error: "User does not exist",
      status: 401,
      statusText: "Unauthorized"
    });

    spyOn(userService, "login").and.callFake(() => {
      return throwError(error)
    });

    component.username.setValue("nonexistantuser");
    component.password.setValue("somepassword");
    component.onSubmit();

    expect(component.errorMsg).toEqual(error.error);
  });

  it("should show message if user exists but password is bad", () => {
    let error = new HttpErrorResponse({
      error: "Invalid Password",
      status: 401,
      statusText: "Unauthorized"
    });

    spyOn(userService, "login").and.callFake(() => {
      return throwError(error)
    });

    component.username.setValue("someuser");
    component.password.setValue("somebadpassword");
    component.onSubmit();

    expect(component.errorMsg).toEqual(error.error);
  });

  it("should put user in local storage on successful login", () => {
    spyOn(userService, "login").and.returnValue(of({
        jwtToken: mockToken,
        user: mockUser
    }));

    component.username.setValue(mockUser.username);
    component.password.setValue(mockUser.password);
    component.onSubmit();
    
    let storedUser: User = JSON.parse(sessionStorage.getItem("user"));

    expect(storedUser).toBeTruthy;
    expect(storedUser.username).toEqual(mockUser.username);
    expect(storedUser.password).toEqual(mockUser.password);
    expect(storedUser.id).toEqual(mockUser.userId);
  });
});
