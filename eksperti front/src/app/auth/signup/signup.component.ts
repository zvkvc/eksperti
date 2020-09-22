import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {SignupRequest} from './signup-request';
import {AuthService} from '../auth.service';
import {ToastrService} from 'ngx-toastr';
import {Router} from '@angular/router';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  // define component-wide fields here
  signupRequest: SignupRequest;
  signupForm: FormGroup; // will be populated automatically as user provides an input

  // inject fields here
  constructor(private authService: AuthService,
              private router: Router,
              private toastr: ToastrService) {
    // initialize fields here
    this.signupRequest = {
      username: '',
      email: '',
      password: ''
    };
  }

  ngOnInit(): void {
    this.signupForm = new FormGroup ({
      username: new FormControl('', Validators.required),
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', Validators.required)
    });
  }

  // runs when user submits
  signup() {
    this.signupRequest.email = this.signupForm.get('email').value;
    this.signupRequest.username = this.signupForm.get('username').value;
    this.signupRequest.password = this.signupForm.get('password').value;
    console.log('Register request email: '+ this.signupRequest.email);
    console.log('Register request username: '+ this.signupRequest.username);
    console.log('Register payload about to be sent...');
    // next step is to delegate this payload to the backend and receive an Observable
    this.authService.signup(this.signupRequest)
      .subscribe(() => {
        this.router.navigate(['/login'], // success case
        {queryParams: { registered: 'true'}});
      }, () => { // error case
        this.toastr.error('Registration Failed. Please try again.');
      });
  }

}
