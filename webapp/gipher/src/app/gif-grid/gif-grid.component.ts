import { Component, Input, OnInit } from '@angular/core';
import { Gif } from '../models/Gif';
import {FavoriteService} from '../services/favorite.service';
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
  Favorites : string[]

  constructor(private  favoriteService : FavoriteService, public dialog: MatDialog)  { }

  ngOnInit(): void {
  }

  openDialog(gif: Gif) {
    const dialogRef = this.dialog.open(GifModalComponent, {
      data: {gif: gif},
      height: '450px',
      width: '450px',
    });
  }

  markAsFavorite( gifUrl : string){
    this.favoriteService.checkifFavorite(gifUrl).subscribe(
      (data)=>{ this.gifASFavorite = data},
      (error : any)=>{ this.gifASFavorite= false}
    )
  }

  getAllFavorites(){
    this.favoriteService.getAllFavorites().subscribe(data => {
      this.Favorites = data;
    },
    (error)=>{console.log(error)})
  }

}
