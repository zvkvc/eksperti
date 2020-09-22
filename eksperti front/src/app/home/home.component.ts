import { Component, OnInit } from '@angular/core';
import {Post} from "../common/post";
import {PostService} from "../common/post.service";
import { faArrowUp, faArrowDown, faComments } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  faArrowUp = faArrowUp;
  faArrowDown = faArrowDown;
  faComments = faComments;

  PostsArrayEmpty: boolean;

  posts$: Array<Post> = [];

  constructor(private postService: PostService) {
    this.postService.getAllPosts().subscribe(postArray => {
      this.posts$ = postArray;
    })

    this.PostsArrayEmpty = !this.posts$[0];
  }

  ngOnInit(): void {
  }

}
