import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { UserService } from '../services/user.service';
import { User } from '../models/User';
import { createTestImage } from './util';

describe('UserService', () => {
  let userService: UserService;
  let httpMock: HttpTestingController

  beforeEach(async () => {
    TestBed.configureTestingModule({
      imports:[HttpClientTestingModule],
      providers:[UserService]
    });
    httpMock = TestBed.inject(HttpTestingController);
    userService = TestBed.inject(UserService);
  });

  it('should be created', () => {
    expect(userService).toBeTruthy();
  });

  it("registerUser() should return a user", () => {
    let user: User = {
      id: 1,
      username: "someuser",
      password: "somepassword",
      image: createTestImage(100)
    }

    userService.registerUser(user).subscribe(data => {
      expect(data.id).toBeTruthy();
      expect(data.username).toBeTruthy();
      expect(data.password).toBeTruthy();
      expect(data.image).toBeTruthy();
    })

    const req = httpMock.expectOne(userService.registerURL);
    expect(req.request.method).toEqual("POST");
    req.flush(user);

    httpMock.verify();
  });

  it("login() should return a user", () => {
    let user: User = {
      username: "someuser",
      password: "somepassword"
    }

    userService.login(user).subscribe(data => {
      expect(data.username).toBeTruthy();
      expect(data.password).toBeTruthy();
    })

    const req = httpMock.expectOne(userService.loginURL);
    expect(req.request.method).toEqual("POST");
    req.flush(user);

    httpMock.verify();
  });
});
