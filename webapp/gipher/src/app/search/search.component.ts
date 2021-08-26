import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Gif } from '../models/Gif';
import { SearchService } from '../services/search.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css'],
})
export class SearchComponent implements OnInit {
  searchValue: string;
  errorMsg: string;
  gifData: Gif[];
  userId: number;

  form: FormGroup;

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

  onSubmit(): void {
    if (this.form.valid) {
      this.searchValue = this.searchTerm.value;

      this.searchService.storeUserSearchTermWithUserId(this.searchValue  , this.userId).subscribe(
        (data) => {
          this.gifData.push(data);
        },
        (error) => {
          this.errorMsg = error.error;
        }
      );
    }
  }
}
