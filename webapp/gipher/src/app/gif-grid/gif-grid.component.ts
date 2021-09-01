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
  //@Output() Favorites : EventEmitter<string[]> = new EventEmitter<string[]>();
  //favorites: Gif[];

  user : User;


  constructor(private  favoriteService : FavoriteService, private userService : UserService,  public dialog: MatDialog)  { }


  ngOnInit(): void {
    //this.favorites = []
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
    let favoriteStr:string[] = [];

    if (gif.favorite==true) {
      this.favoriteService.removeFavorite(this.user, gif.id).subscribe(
            (data)=>{ 
              favoriteStr = data
              console.log("removed as favorite")
              console.log( data)},
          ) }
    else{
    this.favoriteService.addFavorite(this.user, gif.id).subscribe(
                  (data)=>{ 
                    favoriteStr = data.favoriteList
                    console.log("removed as favorite")
                    console.log(data)
                    console.log( data.favoriteList)},

                )
    } 

    console.log(gif.id);
    /* API returns list of gif ID / url 
    compare id to current gifs to figure out which one is a favorite
    add that to the list of favorites*/
    /*let favorites: Gif[] = [];

    favoriteStr.forEach(fav => {
      this.gifs.forEach(gif => {
        if(gif.itemurl === fav) {
          favorites.push(gif);
        }
      })
    })

    this.favoriteService.updateFavorites(favorites);*/
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

  /*getAllFavorites(){
    this.favoriteService.getAllFavorites(this.user).subscribe(data => {
      this.Favorites = data;
    },
    (error)=>{console.log(error)})
  }*/

}
