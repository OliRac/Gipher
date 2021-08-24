import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.css']
})
export class LogoutComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  onClick(): void {
    if(sessionStorage.getItem("user") != null) {
      sessionStorage.removeItem("user");
      this.router.navigate(["/landing"]);
    }
  }
}
