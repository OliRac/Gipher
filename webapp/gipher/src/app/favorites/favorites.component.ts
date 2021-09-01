import { Component, OnInit,  Input} from '@angular/core';
import {FavoriteService} from '../services/favorite.service';
import { Gif } from '../models/Gif';
import {UserService} from '../services/user.service';
import { User } from '../models/User';



@Component({
  selector: 'app-favorites',
  templateUrl: './favorites.component.html',
  styleUrls: ['./favorites.component.css']
})
export class FavoritesComponent implements OnInit {
@Input() favorites : Gif[];
user : User;
favoritesList : String[];



  constructor(private favoriteService : FavoriteService, private userService : UserService) { }

  ngOnInit(): void {
    this.user = this.userService.getUserSession();

  }

  markAsFavorite( gifUrl : string){
      this.favoriteService.checkifFavorite(this.user, gifUrl).subscribe(
        (data)=>{ this.favorites = data}
      )
    }

    // getAllFavorites(){
    //   this.favoriteService.getAllFavorites(this.user).subscribe(data => {
    //     this.favorites = data;
    //   },
    //   (error)=>{console.log(error)})
    // }

}
