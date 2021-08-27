import { Component, OnInit , Input} from '@angular/core';
// import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
 import { Gif } from '../models/Gif';
// import { SearchService } from '../services/search.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css'],
})
export class SearchComponent implements OnInit {

  @Input() gifList: Gif[];
  @Input() infoText: string ;

  constructor() { }

  ngOnInit(): void {
  }

//
//   searchValue: string;
//   errorMsg: string;
//   userId: number;
//
//   form: FormGroup;
//
//   constructor(
//     private searchService: SearchService,
//     private formBuilder: FormBuilder
//   ) {
//     this.form = this.formBuilder.group({
//       searchTerm: new FormControl('', [Validators.required]),
//     });
//   }
//
//   ngOnInit(): void {}
//
//   get searchTerm() {
//     return this.form.get('searchTerm');
//   }

//   onSubmit(): void {
//     if (this.form.valid) {
//       this.searchValue = this.searchTerm.value;
//
//       this.searchService.storeUserSearchTermWithUserId(this.searchValue  , this.userId).subscribe(
//         (data) => {
//           this.gifData.push(data);
//         },
//         (error) => {
//           this.errorMsg = error.error;
//         }
//       );
//     }
//   }
}
