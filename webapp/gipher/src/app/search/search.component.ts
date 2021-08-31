import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { BehaviorSubject } from 'rxjs';
import { User } from '../models/User';
import { UserTerm } from '../models/UserTerm';
import { SearchService } from '../services/search.service';
import { UserService } from '../services/user.service';
import { parseTenorResponseForGifs } from '../util/tenorResponse.parser';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css'],
})
export class SearchComponent implements OnInit {  
  searchValue: string;
  errorMsg: string;
  parentData: any[] = [];
  resultList: any[] = [];
  userId: number;
  form: FormGroup;
  refreshGif$ = new BehaviorSubject<boolean>(true);

  constructor(
    private searchService: SearchService,
    private userService: UserService,
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

  onSubmit(): void {
    if (this.form.valid) {
      this.searchValue = this.searchTerm.value;
      const userTerm: UserTerm = {
        searchTerm: this.searchValue,
        userId: this.userService.getUserSession().id
      }

      this.searchService.storeUserSearchTermWithUserId(userTerm).subscribe(data => {
        this.resultList = parseTenorResponseForGifs(data);
      })

      this.refreshGif$.next(true);
      
    }
  }
}
