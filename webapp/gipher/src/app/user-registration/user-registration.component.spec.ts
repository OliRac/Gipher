import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { IMAGE_MISSING, PASSWORD_MISSING, PASSWORD_TOO_BIG, PASSWORD_TOO_SHORT, USERNAME_MISSING, USERNAME_TOO_BIG } from '../messages/registration.messages';
import { UserService } from '../services/user.service';
import { UserRegistrationComponent } from './user-registration.component';

describe('UserRegistrationComponent', () => {
  let component: UserRegistrationComponent;
  let fixture: ComponentFixture<UserRegistrationComponent>;
  let userService: UserService;

  let mockImage = {0: {name: "image.png", size: 500}};

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientModule, ReactiveFormsModule],
      declarations: [UserRegistrationComponent],
      providers: [UserService]
    })
    .compileComponents();
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
    component.form.get("username").setValue(null);
    component.form.get("password").setValue("test");
    component.form.get("image").setValue("");
    component.onSubmit();
    expect(component.message).toEqual(USERNAME_MISSING);
  })

  it("should show message if password is missing", () => {
    component.form.get("username").setValue("test");
    component.form.get("password").setValue(null);
    component.form.get("image").setValue("");
    component.onSubmit();
    expect(component.message).toEqual(PASSWORD_MISSING); 
  })

  it("should show message if image is missing", () => {
    component.form.get("username").setValue("test");
    component.form.get("password").setValue("test");
    component.form.get("image").setValue("");
    component.onSubmit();
    expect(component.message).toEqual(IMAGE_MISSING);
  })

  it("should show message if username is too long", () => {
    component.form.get("username").setValue("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
    component.form.get("password").setValue("test");
    component.form.get("image").setValue("");
    component.onSubmit();
    expect(component.message).toEqual(USERNAME_TOO_BIG);
  })

  it("should show message if username is too short", () => {
    component.form.get("username").setValue("a");
    component.form.get("password").setValue("test");
    component.form.get("image").setValue("");
    component.onSubmit();
    expect(component.message).toEqual(USERNAME_TOO_BIG);
  })

  it("should show message if password is too long", () => {
    component.form.get("password").setValue("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
    component.form.get("username").setValue("test");
    component.form.get("image").setValue("");
    component.onSubmit();
    expect(component.message).toEqual(PASSWORD_TOO_BIG);
  })

  it("should show message if password is too short", () => {
    component.form.get("username").setValue("test");
    component.form.get("password").setValue("a");
    component.form.get("image").setValue("");
    component.onSubmit();
    expect(component.message).toEqual(PASSWORD_TOO_SHORT);
  })

  it("should show if image is too big", () => {

  })

  it("should register user if form is correct", () => {
      component.form.get("username").setValue("test");
      component.form.get("password").setValue("a");
      component.form.get("image").setValue("");
      component.onSubmit();
  })
});
