import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { LoginGuard } from './guards/login.guard';
import { LandingComponent } from './landing/landing.component';
import { LogoutComponent } from './logout/logout.component';
import { UserLoginComponent } from './user-login/user-login.component';
import { UserRegistrationComponent } from './user-registration/user-registration.component';

const routes: Routes = [
  {
    path:"register", component: UserRegistrationComponent
  },
  {
    path:"login", component: UserLoginComponent
  },  
  {
    path:"landing", component: LandingComponent
  },
  {
    path:"dashboard", component: DashboardComponent, canActivate: [LoginGuard]
  },
  {
    path: "logout", component: LogoutComponent, canActivate:[LoginGuard]
  },
  {
    path: "", redirectTo: "dashboard", pathMatch: "full"
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
