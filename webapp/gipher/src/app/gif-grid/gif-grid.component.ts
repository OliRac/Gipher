import { Component, Input, OnInit, EventEmitter, Output } from '@angular/core';
import { Gif } from '../models/Gif';
import {FavoriteService} from '../services/favorite.service';
import {UserService} from '../services/user.service';
import { User } from '../models/User';


import { MatDialog } from '@angular/material/dialog';
import { GifModalComponent } from '../gif-modal/gif-modal.component';
import { SharedfavoriteslistService } from '../services/sharedfavoriteslist.service';

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

  constructor(private  favoriteService : FavoriteService, 
              private sharedFavoritesList: SharedfavoriteslistService,
              private userService : UserService,  
              public dialog: MatDialog
              )  { }

  ngOnInit(): void {

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
      this.favoriteService.removeFavorite(this.userService.getUserSession(), gif.id).subscribe(
            (data)=>{ 
              favoriteStr = data
              console.log("removed as favorite")
              console.log( data)
              gif.favorite = false;
              this.sharedFavoritesList.removeFromFavorites(gif);
            },
          ) 
        
    } else {
    this.favoriteService.addFavorite(this.userService.getUserSession(), gif.id).subscribe(
                  (data)=>{ 
                    favoriteStr = data.favoriteList
                    console.log("added as favorite")
                    console.log(data)
                    console.log( data.favoriteList)
                    gif.favorite = true;
                    this.sharedFavoritesList.addToFavorites(gif);
                  },                   
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
    this.favoriteService.checkifFavorite(this.userService.getUserSession(), gifUrl).subscribe(
      (data)=>{ this.gifASFavorite = data},
      (error : any)=>{ this.gifASFavorite= false}
    )
  }
}
