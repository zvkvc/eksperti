import { Component, OnInit } from '@angular/core';
import {SubforumModel} from "../subforum-response";
import {SubforumService} from "../subforum.service";
import {throwError} from "rxjs";

@Component({
  selector: 'app-list-subforums',
  templateUrl: './list-subforums.component.html',
  styleUrls: ['./list-subforums.component.css']
})
export class ListSubforumsComponent implements OnInit {

  subforums: Array<SubforumModel>;
  constructor(private subforumService: SubforumService) { }

  ngOnInit() {
    this.subforumService.getAllSubforums().subscribe(data => {
      this.subforums = data;
    }, error => {
      throwError(error);
    })
  }
}
