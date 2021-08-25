import { TestBed } from '@angular/core/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';

import { LoginGuard } from '../guards/login.guard';

describe('LoginGuard', () => {
  let guard: LoginGuard;
  let router: Router;

  let mockUser: {
    username: "mockUser",
    password: "mockpassword"
  }

  beforeEach(async () => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule],
      providers:[
        LoginGuard
      ]
    });
    guard = TestBed.inject(LoginGuard);
    router = TestBed.inject(Router);
  });

  afterEach(() => {
    sessionStorage.clear();
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });

  it("should return true if user is authenticated", () => {
    sessionStorage.setItem("user", JSON.stringify(mockUser));
    expect(guard.canActivate(null, null)).toBeTruthy();
  });

  it("should return false if user is not authenticated", () => {
    expect(guard.canActivate(null, null)).toBeFalsy();
  });
});
