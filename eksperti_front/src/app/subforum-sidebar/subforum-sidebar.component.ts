import { Component, OnInit } from '@angular/core';
import {SubforumService} from '../subforum/subforum.service';
import {SubforumModel} from '../subforum/subforum-response';

@Component({
  selector: 'app-subforum-sidebar',
  templateUrl: './subforum-sidebar.component.html',
  styleUrls: ['./subforum-sidebar.component.css']
})
export class SubforumSideBarComponent implements OnInit {
  subforums: Array<SubforumModel> = [];
  displayViewAll: boolean;

  constructor(private subforumService: SubforumService) {
    this.subforumService.getAllSubforums().subscribe(data => {
      if (data.length > 3) {
        this.subforums = data.splice(0, 3);
        this.displayViewAll = true;
      } else {
        this.subforums = data;
      }
    });
  }

  ngOnInit(): void { }

}
