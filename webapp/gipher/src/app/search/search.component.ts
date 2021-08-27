import { Component, OnInit , ChangeDetectorRef } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { Gif } from '../models/Gif';
import { SearchService } from '../services/search.service';
import { BehaviorSubject} from 'rxjs';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css'],
})
export class SearchComponent implements OnInit {
  searchValue: string;
  errorMsg: string;
  resultList: any[] = [];
  gifs: string[] = ["https://media.tenor.com/images/90775ff4b9f889c005442826e09daace/tenor.gif"];
  userId: number;
  refreshGif$ = new BehaviorSubject<boolean>(true);
  form: FormGroup;
sessionUser: any = JSON.parse(sessionStorage.getItem('user'));
  constructor(
    private searchService: SearchService,
    private formBuilder: FormBuilder,
    private changeDetectorRef: ChangeDetectorRef
  ) {
    this.form = this.formBuilder.group({
      searchTerm: new FormControl('', [Validators.required]),
    });
  }

  ngOnInit(): void {}
  extractData(){

  }

  addGifs(gifArray: any[] ){
       for (let i = 0; i < gifArray.length; i++) {
                 let gifUrl =  gifArray[i].media[0].mediumgif.url;
                 this.gifs.push(gifUrl);
                    }
          console.log("GIfs after for loop : " , this.gifs);
         this.refreshGif$.next(true);
  }

  get searchTerm() {
    return this.form.get('searchTerm');
  }

  onSubmit(): void {
  console.log('submit');
  console.log("Json log" , this.sessionUser);
    if (this.form.valid) {
       this.searchValue = this.searchTerm.value;
       console.log(this.searchValue);
       this.searchService.searchGif(this.searchValue).subscribe(
         (data) => {
              console.log('Data:   ' , data);
              this.resultList = data.results;
               this.addGifs(this.resultList)
        },
        (error) => {
          this.errorMsg = error.error;
        }
      );

    }
  }
}
