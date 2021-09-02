import { Component, Input, OnInit, EventEmitter, Output } from '@angular/core';
import { Gif } from '../models/Gif';
import {FavoriteService} from '../services/favorite.service';
import {UserService} from '../services/user.service';
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
              public dialog: MatDialog)  { 

  }

  ngOnInit(): void {

  }

  /*Sets the color of the heart on hover*/
  setColor(favoriteStatus : boolean) : string{
    if(favoriteStatus==true){
      return "warn"
    } else {
      return "white"
    }
  }

  /* On click, if its a favorite remove it and vice versa */
  onClickFavorite(gif: Gif): void {
    let user = this.userService.getUserSession();

    if(user){
      if (gif.favorite==true) {
        gif.favorite=false
        this.favoriteService.removeFavorite(user, gif.id).subscribe(data => { 
          this.sharedFavoritesList.removeFromFavorites(gif);
        }) 
      } else {
        gif.favorite=true
        this.favoriteService.addFavorite(user, gif.id).subscribe(data => { 
            this.sharedFavoritesList.addToFavorites(gif);
        })
      } 
    }
  }

  OnClearFavorite(): void{
    this.favoriteService.emptyFavoriteList(this.userService.getUserSession()).subscribe(data => { 
      this.sharedFavoritesList.resetFavorites();
    }) 
  }

  openDialog(gif: Gif) {
    let multiplier = 1.5;
    const dialogRef = this.dialog.open(GifModalComponent, {
      data: {gif: gif}
    });
  }

  markAsFavorite( gifUrl : string){
    let user = this.userService.getUserSession();

    if(user) {
      this.favoriteService.checkifFavorite(user, gifUrl).subscribe(
        (data)=>{ this.gifASFavorite = data},
        (error : any)=>{ this.gifASFavorite= false}
      )
    }
  }
}
