import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { of } from 'rxjs';
import { messages } from '../messages/registration.messages';
import { UserService } from '../services/user.service';

import { UserLoginComponent } from '../user-login/user-login.component';

describe('UserLoginComponent', () => {
  let component: UserLoginComponent;
  let fixture: ComponentFixture<UserLoginComponent>;
  let userService: UserService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientModule, ReactiveFormsModule],
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
    let error: string = "user does not exist"
    spyOn(userService, "login").and.returnValue(of(error));
    component.username.setValue("nonexistantuser");
    component.password.setValue("somepassword");
    component.onSubmit();
    expect(component.errorMsg).toEqual(error);
  });

  it("should show message if user exists but password is bad", () => {
    let error: string = "wrong password"
    spyOn(userService, "login").and.returnValue(of(error));
    component.username.setValue("someuser");
    component.password.setValue("somebadpassword");
    component.onSubmit();
    expect(component.errorMsg).toEqual(error);
  });

  it("should greet if user exists", () => {
    let greet: string = "welcome!"
    spyOn(userService, "login").and.returnValue(of(greet));
    component.username.setValue("someuser");
    component.password.setValue("somepassword");
    component.onSubmit();
    expect(component.errorMsg).toEqual(greet);
  });
});
