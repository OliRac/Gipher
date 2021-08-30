import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { BehaviorSubject } from 'rxjs';
import { User } from '../models/User';
import { UserTerm } from '../models/UserTerm';
import { SearchService } from '../services/search.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css'],
})
export class SearchComponent implements OnInit {

  searchValue: string;
  errorMsg: string;
  parentData: any[] = [];
  gifs: string[] = [];
  resultList: any[] = [];
  userId: number;
  storedUser: User = JSON.parse(sessionStorage.getItem("user"));
  form: FormGroup;
  refreshGif$ = new BehaviorSubject<boolean>(true);

  constructor(
    private searchService: SearchService,
    private formBuilder: FormBuilder
  ) {
    this.form = this.formBuilder.group({
      searchTerm: new FormControl('', [Validators.required]),
    });
  }

  ngOnInit(): void {}

  get searchTerm() {
    return this.form.get('searchTerm');
  }

  addGifs(gifArray: any[]){
    for (let i = 0; i < gifArray.length; i++) {
      let gifUrl =  gifArray[i].media[0].mediumgif.url;
      this.gifs.push(gifUrl);
    }
    this.refreshGif$.next(true);
  }


  onSubmit(): void {
    if (this.form.valid) {
      this.gifs = [];
      this.searchValue = this.searchTerm.value;

      const userTerm: UserTerm = {
        searchTerm: this.searchValue,
        userId: this.storedUser.id
      }
      this.searchService.storeUserSearchTermWithUserId(userTerm).subscribe(
        (data) => {
          this.resultList = data.results;
          this.addGifs(this.resultList);
        },
        (error) => {
          this.errorMsg = error.error;
        }
      );
    }
  }
}
