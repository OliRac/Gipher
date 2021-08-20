import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { $ } from 'protractor';
import { of } from 'rxjs';
import { IMAGE_MISSING, IMAGE_TOO_BIG, PASSWORD_MISSING, PASSWORD_TOO_BIG, PASSWORD_TOO_SHORT, REGISTER_SUCCESS, USERNAME_MISSING, USERNAME_TOO_BIG, USERNAME_TOO_SHORT } from '../messages/registration.messages';
import { UserService } from '../services/user.service';
import { UserRegistrationComponent } from '../user-registration/user-registration.component';
import { createTestImage } from './util';

describe('UserRegistrationComponent', () => {
  let component: UserRegistrationComponent;
  let fixture: ComponentFixture<UserRegistrationComponent>;
  let userService: UserService;

  let loremIpsum: string = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientModule, ReactiveFormsModule, RouterModule.forRoot([])],
      declarations: [UserRegistrationComponent],
      providers: [UserService]
    })
    .compileComponents();
    userService = TestBed.inject(UserService);
    spyOn(userService, "registerUser").and.returnValue(of(REGISTER_SUCCESS));
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UserRegistrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it("should show message if username is missing", () => {
    let usernameRequired = fixture.nativeElement.querySelector("#usernameRequired");
    expect(usernameRequired).toBeTruthy();
    expect(usernameRequired.textContent).toEqual(USERNAME_MISSING);
  })

  it("should show message if password is missing", () => {
    let passwordRequired = fixture.nativeElement.querySelector("#passwordRequired");
    expect(passwordRequired).toBeTruthy();
    expect(passwordRequired.textContent).toEqual(PASSWORD_MISSING);
  })

  /*Because of the behaviour of formControl & input type file, I needed to make the image non required and do the validation manually in onSubmit()*/
  it("should show message if image is missing", () => {
    component.onSubmit();
    expect(component.errorMsg).toBeTruthy();
    expect(component.errorMsg).toEqual(IMAGE_MISSING);
  })

  it("should show message if username is too long", () => {
    component.username.setValue(loremIpsum);
    fixture.detectChanges();
    let usernameMaxLength = fixture.nativeElement.querySelector("#usernameMaxLength");
    expect(usernameMaxLength).toBeTruthy();
    expect(usernameMaxLength.textContent).toEqual(USERNAME_TOO_BIG);
  })

  it("should show message if username is too short", () => {
    component.username.setValue("a");
    fixture.detectChanges();
    let usernameMinLength = fixture.nativeElement.querySelector("#usernameMinLength");
    expect(usernameMinLength).toBeTruthy();
    expect(usernameMinLength.textContent).toEqual(USERNAME_TOO_SHORT);
  })

  it("should show message if password is too long", () => {
    component.password.setValue(loremIpsum);
    fixture.detectChanges();
    let passwordMaxLength = fixture.nativeElement.querySelector("#passwordMaxLength");
    expect(passwordMaxLength).toBeTruthy();
    expect(passwordMaxLength.textContent).toEqual(PASSWORD_TOO_BIG);
  })

  it("should show message if password is too short", () => {
    component.password.setValue("a");
    fixture.detectChanges();
    let passwordMinLength = fixture.nativeElement.querySelector("#passwordMinLength");
    expect(passwordMinLength).toBeTruthy();
    expect(passwordMinLength.textContent).toEqual(PASSWORD_TOO_SHORT);
  })

  it("should show if image is too big", () => {
    let file = createTestImage(Math.pow(2, 30));  //creating a 1gb image
    let event = {target: {files: [file]}}

    component.onFileChange(event);
    fixture.detectChanges();

    expect(component.errorMsg).toBeTruthy();
    expect(component.errorMsg).toEqual(IMAGE_TOO_BIG);
  })

  it("should register user if form is correct", () => {
      component.username.setValue("username");
      component.password.setValue("password12345");
      
      let file = createTestImage(100);
      let event = {target: {files: [file]}}

      component.onFileChange(event);
      fixture.detectChanges();

      component.onSubmit();
      fixture.detectChanges();

      expect(component.errorMsg).toEqual(REGISTER_SUCCESS);
  })
});