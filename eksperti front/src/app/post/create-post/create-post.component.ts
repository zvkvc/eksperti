import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {CreatePostPayload} from "./create-post.payload";
import {SubforumService} from "../../subforum/subforum.service";
import {throwError} from "rxjs";
import {SubforumModel} from "../../subforum/subforum-response";
import {PostService} from "../../common/post.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent implements OnInit {

  createPostForm: FormGroup;
  postPayload: CreatePostPayload;
  subforums: Array<SubforumModel>;

  constructor(private router: Router, private postService: PostService,
              private subforumService: SubforumService) {
    this.postPayload = {
      postName: '',
      url: '',
      description: '',
      subforumName: ''
    }
  }

  ngOnInit() {
    this.createPostForm = new FormGroup({
      postName: new FormControl('', Validators.required),
      subforumName: new FormControl('', Validators.required),
      url: new FormControl('', Validators.required),
      description: new FormControl('', Validators.required),
    });
    this.subforumService.getAllSubforums().subscribe((data) => {
      this.subforums = data;
    }, error => {
      throwError(error);
    });
  }

  createPost() {
    this.postPayload.postName = this.createPostForm.get('postName').value;
    this.postPayload.subforumName = this.createPostForm.get('subforumName').value;
    this.postPayload.url = this.createPostForm.get('url').value;
    this.postPayload.description = this.createPostForm.get('description').value;

    this.postService.createPost(this.postPayload).subscribe((data) => {
      this.router.navigateByUrl('/');
    }, error => {
      throwError(error);
    })
  }

  discardPost() {
    this.router.navigateByUrl('/');
  }

}
