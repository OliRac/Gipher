import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { GifDialogData } from '../gif-grid/gif-grid.component';

@Component({
  selector: 'app-gif-modal',
  templateUrl: './gif-modal.component.html',
  styleUrls: ['./gif-modal.component.css']
})
export class GifModalComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<GifModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: GifDialogData) { }

  ngOnInit(): void {
    
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

}
