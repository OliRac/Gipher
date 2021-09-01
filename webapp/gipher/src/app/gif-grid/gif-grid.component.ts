import { Component, Input, OnInit, EventEmitter, Output } from '@angular/core';
import { Gif } from '../models/Gif';
import {FavoriteService} from '../services/favorite.service';
import {UserService} from '../services/user.service';
import { User } from '../models/User';


import { MatDialog } from '@angular/material/dialog';
import { GifModalComponent } from '../gif-modal/gif-modal.component';

export interface GifDialogData {
  gif: Gif;
}

@Component({
  selector: 'app-gif-grid',
  templateUrl: './gif-grid.component.html',
  styleUrls: ['./gif-grid.component.css']
})
export class GifGridComponent implements OnInit {
  @Input() gifs: Gif[];
  @Input() infoText : string;
  gifASFavorite : boolean
  @Output() Favorites : EventEmitter<string[]> = new EventEmitter<string[]>();
  user : User;


  constructor(private  favoriteService : FavoriteService, private userService : UserService,  public dialog: MatDialog)  { }


  ngOnInit(): void {

     this.user = this.userService.getUserSession();

  }

  

  setColor(favoriteStatus : boolean) : string{
    if( favoriteStatus==true){
      return "warn"
      }
    else{
      return "white"
      }
  }

 onClickFavorite(gif: Gif): void {
       if (gif.favorite==true) {
         this.favoriteService.removeFavorite(this.user, gif.itemurl).subscribe(
               (data)=>{ this.Favorites = data
                         console.log("removed as favorite")
                         console.log( data)},
             ) }
       else{
        this.favoriteService.addFavorite(this.user, gif.itemurl).subscribe(
                     (data)=>{ this.Favorites = data.favoriteList
                                console.log("removed as favorite")
                                     console.log(data)
                                     console.log( data.favoriteList)},

                   )
       }

       }

  openDialog(gif: Gif) {
    const dialogRef = this.dialog.open(GifModalComponent, {
      data: {gif: gif},
      height: '450px',
      width: '450px',
    });
  }

  markAsFavorite( gifUrl : string){
    this.favoriteService.checkifFavorite(this.user, gifUrl).subscribe(
      (data)=>{ this.gifASFavorite = data},
      (error : any)=>{ this.gifASFavorite= false}
    )
  }

  getAllFavorites(){
    this.favoriteService.getAllFavorites(this.user).subscribe(data => {
      this.Favorites = data;
    },
    (error)=>{console.log(error)})
  }

}
