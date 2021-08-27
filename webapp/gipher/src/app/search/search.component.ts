import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
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
  parentData: any[] = [];
  gifs: Gif[] = [];
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
  extractData(){

  }

  get searchTerm() {
    return this.form.get('searchTerm');
  }

  onSubmit(): void {
    if (this.form.valid) {
       this.searchValue = this.searchTerm.value;
       console.log(this.searchValue);
//
//       this.searchService
//         .storeUserSearchTermWithUserId(this.searchValue, this.userId)
//         .subscribe(
//           (data) => {
//             this.gifData.push(data);
//           },
//           (error) => {
//             this.errorMsg = error.error;
//           }
//         );
      // testing!
      this.searchService.searchGif(this.searchValue).subscribe(
        (data) => {
          console.log('Data:   ' , data);
          this.parentData = data.results;
           console.log('ParentData :   ' , this.parentData);

        //loop extracting data
        for (let i = 0; i < this.parentData.length; i++) {

          console.log ("Media: " , this.parentData[i].media[i].mediumgif.url);
        }


        },
        (error) => {
          this.errorMsg = error.error;
        }
      );
    }
  }
}
