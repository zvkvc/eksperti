import {Component, Input, OnInit} from '@angular/core';
import {Post} from "../post";
import {Router} from "@angular/router";
import {faComments} from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: 'app-post-tile',
  templateUrl: './post-tile.component.html',
  styleUrls: ['./post-tile.component.css']
})
export class PostTileComponent implements OnInit {

  faComments = faComments;

  @Input() // from home.component.ts
  posts: Post[];

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  goToPost(id: number): void {
    this.router.navigateByUrl('/view-post/' + id);
  }
}

