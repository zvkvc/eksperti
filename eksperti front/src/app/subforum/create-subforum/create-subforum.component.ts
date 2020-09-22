import { Component, OnInit } from '@angular/core';
import {SubforumModel} from '../subforum-response';
import {SubforumService} from '../subforum.service';
import {throwError} from 'rxjs';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';

@Component({
  selector: 'app-create-subforum',
  templateUrl: './create-subforum.component.html',
  styleUrls: ['./create-subforum.component.css']
})
export class CreateSubforumComponent implements OnInit {
  createSubforumForm: FormGroup;
  subforumModel: SubforumModel;
  title = new FormControl('');
  description = new FormControl('');

  constructor(private router: Router, private subforumService: SubforumService) {
    this.createSubforumForm = new FormGroup({
      title: new FormControl('', Validators.required),
      description: new FormControl('', Validators.required)
    });
    this.subforumModel = {
      name: '',
      description: ''
    }
  }

  ngOnInit() {
  }

  discard() {
    this.router.navigateByUrl('/');
  }

  createSubforum() {
    this.subforumModel.name = this.createSubforumForm.get('title')
      .value;
    this.subforumModel.description = this.createSubforumForm.get('description')
      .value;
    this.subforumService.createSubforum(this.subforumModel).subscribe(data => {
      this.router.navigateByUrl('/list-subforums');
    }, error => {
      throwError(error);
    })
  }
}
