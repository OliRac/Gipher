import { Component, OnInit } from '@angular/core';
import { Gif } from '../models/Gif';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { SearchService } from '../services/search.service';
import { Router } from '@angular/router';
import { parseTenorResponseForGifs } from '../util/tenorResponse.parser';
import { RecommendationService } from '../services/recommendation.service';
import { UserService } from '../services/user.service';
import { FavoriteService } from '../services/favorite.service';
import {
  animate,
  state,
  style,
  transition,
  trigger,
} from '@angular/animations';
import { User } from '../models/User';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
})
export class DashboardComponent implements OnInit {
  gifData: Gif[];
  display: string = 'recommended';
  searchValue: string;
  errorMsg: string;
  userId: number;
  message: string;

  username: string;
  imageUrl: string;

  form: FormGroup;
  trendingGifs: Gif[];

  /*For passing a click event from sibling to sibling components*/
  clickedEvent: Event;
  private favoritesList: String[];
  user: User;

  constructor(
    private searchService: SearchService,
    private userService: UserService,
    private formBuilder: FormBuilder,
    private recommendationService: RecommendationService,
    private router: Router,
    private favoriteService: FavoriteService
  ) {
    this.form = this.formBuilder.group({
      searchTerm: new FormControl('', [Validators.required]),
    });
  }

  ngOnInit(): void {
    this.user = this.userService.getUserSession();

    this.favoriteService.getAllFavorites(this.user).subscribe((data) => {
      this.favoritesList = data;
    });

    this.recommendationService.getTrending().subscribe((data) => {
      this.gifData = parseTenorResponseForGifs(data);
      this.trendingGifs = parseTenorResponseForGifs(data);
    });

    this.username = this.userService.getUserSession().username;
    this.imageUrl = this.userService.getUserSession().imageUrl;

    this.message = 'Here are some recommended gifs for you';
  }

  get searchTerm() {
    return this.form.get('searchTerm');
  }

  changeMessage(content: string) {
    if (content == 'search') {
      this.message = 'Here is the result of your search';
    }

    if (content == 'recommended') {
      this.message = 'Here are some recommended gifs for you';
    }
    if (content == 'favorites') {
      this.message = 'Here are all your favorites gifs';
    }
  }

  gridDisplay(content: string): boolean {
    this.changeMessage(content);
    if (this.display == content) {
      return true;
    } else {
      return false;
    }
  }

  onClickLogOut(): void {
    sessionStorage.clear();
    this.router.navigate(['/landing']);
  }

  /*For passing a click event from sibling to sibling components*/
  validSearchDone(event: Event) {
    this.clickedEvent = event;
  }

  // onSubmit(): void {
  //   if (this.form.valid) {
  //     this.searchValue = this.searchTerm.value;

  //     this.searchService.storeUserSearchTermWithUserId(this.searchValue  , this.userId).subscribe(
  //       (data) => {
  //         this.gifData.push(data);
  //       },
  //       (error) => {
  //         this.errorMsg = error.error;
  //       }
  //     );
  //   }
  // }
}
