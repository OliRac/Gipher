import { Component, OnInit } from '@angular/core';
import { Gif } from '../models/Gif';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { SearchService } from '../services/search.service';
import { TrendingService } from '../services/trending.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  gifData: Gif[];
  display : string="search";
  searchValue: string;
  errorMsg: string;
  userId: number;
  message : string ;

  username :string ='Julie';

  form: FormGroup;

  constructor(
    private searchService: SearchService,
    private formBuilder: FormBuilder,
    private trendingService:TrendingService,
    private  router : Router
  ) {
    this.form = this.formBuilder.group({
      searchTerm: new FormControl('', [Validators.required]),
    });
  }

  ngOnInit(): void {
  //this migth be changed to this.recommendation.getGifs()
  this.gifData=this.trendingService.getGifs()
  this.message ="Here are some recommended gifs for you"
  }

  get searchTerm() {
    return this.form.get('searchTerm');
  }

  changeMessage(content : string){
    if(content=="search"){
      this.message="Here is the result of your search"
    }

    if(content=="recommended"){
        this.message="Here are some recommended gifs for you"
      }
    if(content=="favorites"){
            this.message="Here are all your favorites gifs"
          }

  }

  gridDisplay( content : string) : boolean {
      this.changeMessage(content)
     if(this.display==content){
        return true;}
     else{
        return false;}
  }


  onClickLogOut(): void {
    if(sessionStorage.getItem("user") != null) {
      sessionStorage.removeItem("user");
      this.router.navigate(["/landing"]);
    }
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
