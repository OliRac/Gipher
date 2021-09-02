import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { UserRegistrationComponent } from './user-registration/user-registration.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { MatSliderModule } from '@angular/material/slider';
import { MatButtonModule } from '@angular/material/button';
import { RouterModule } from '@angular/router';
import { UserLoginComponent } from './user-login/user-login.component';
import { HeaderComponent } from './header/header.component';
import { SearchComponent } from './search/search.component';
import { FavoritesComponent } from './favorites/favorites.component';
import { RecommendedComponent } from './recommended/recommended.component';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatCardModule} from '@angular/material/card';
import { DashboardComponent } from './dashboard/dashboard.component';
import { LandingComponent } from './landing/landing.component';
import { GifGridComponent } from './gif-grid/gif-grid.component';
import { MatGridListModule } from '@angular/material/grid-list';
import {MatIconModule} from '@angular/material/icon';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatDialogModule} from '@angular/material/dialog';
import { GifModalComponent } from './gif-modal/gif-modal.component';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';

@NgModule({
  declarations: [
    AppComponent,
    UserRegistrationComponent,
    UserLoginComponent,
    HeaderComponent,
    SearchComponent,
    FavoritesComponent,
    RecommendedComponent,
    DashboardComponent,
    LandingComponent,
    GifGridComponent,
    GifModalComponent,
  ],
  imports: [
    MatProgressSpinnerModule,
    MatDialogModule,
    MatCardModule,
    MatFormFieldModule,
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatSliderModule,
    MatButtonModule,
    RouterModule,
    MatGridListModule,
    MatIconModule,
    MatToolbarModule,



  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
