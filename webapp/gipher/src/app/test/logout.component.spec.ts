import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Router, RouterModule } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { userInfo } from 'os';

import { LogoutComponent } from '../logout/logout.component';

describe('LogoutComponent', () => {
  let component: LogoutComponent;
  let fixture: ComponentFixture<LogoutComponent>;

  let mockUser: {
    username: "mockUser",
    password: "mockpassword"
  }

  let routerSpy = {navigate: jasmine.createSpy("navigate")};

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports:[RouterTestingModule],
      declarations: [ LogoutComponent ],
      providers: [{provide: Router, useValue: routerSpy}]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LogoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  afterEach(() => {
    sessionStorage.clear();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it("should remove user from session if logged in", () => {
    sessionStorage.setItem("user", JSON.stringify(mockUser));
    component.onClick();
    expect(sessionStorage.getItem("user")).toBeFalsy();
    expect(routerSpy.navigate).toHaveBeenCalledWith(["/landing"]);
    expect(routerSpy.navigate).toHaveBeenCalled();
  });
});
