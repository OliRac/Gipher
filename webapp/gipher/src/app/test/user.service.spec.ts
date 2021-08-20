import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { UserService } from '../services/user.service';
import { User } from '../models/User';
import { createTestImage } from './util';

describe('UserService', () => {
  let userService: UserService;
  let httpMock: HttpTestingController

  beforeEach(() => {
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

  it("registerUser() should register a new user", () => {
    let user: User = {
      username: "someuser",
      password: "somepassword",
      image: createTestImage(100)
    }

    userService.registerUser(user).subscribe(data => {

    })

    const req = httpMock.expectOne(userService.registerURL);
    expect(req.request.method).toEqual("POST");
  });
});
