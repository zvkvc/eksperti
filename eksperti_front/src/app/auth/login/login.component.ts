import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {LoginRequestPayload} from './loginRequest';
import {AuthService} from '../auth.service';
import {ToastrService} from 'ngx-toastr';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {


  loginForm: FormGroup;
  loginRequestPayload: LoginRequestPayload;
  registerSuccessMessage: string;
  isError: Boolean;

  constructor(private authService: AuthService,
              private router: Router,
              private activatedRoute: ActivatedRoute,
              private toastr: ToastrService) {
    // initialize fields here
    this.loginRequestPayload = {
      username: '',
      password: ''
    };
  }

  ngOnInit(): void {
    this.loginForm = new FormGroup({
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required)
    });

    // pick up query parameters if there are any
    // specifically, if we are sent here from successful /signup request
    this.activatedRoute.queryParams.subscribe(params => {
      if (params.registered !== undefined && params.registered == 'true') {
        this.toastr.success('Signup Successful.');
        this.registerSuccessMessage = 'Please Check your inbox activation link.';
      }
    });
  }

  login() {
    this.loginRequestPayload.username = this.loginForm.get('username').value;
    this.loginRequestPayload.password = this.loginForm.get('password').value;
    // next step is to delegate this payload to the backend and receive an Observable
    this.authService.login(this.loginRequestPayload).subscribe(data => { // authService.login() returns Observable<boolean>
      if (data) { // if login went successfully
        this.isError = false;
        this.router.navigateByUrl('/');
        this.toastr.success('Login Successful.');
      } else {
        this.isError = true;
      }
      console.log('authService.login() returns: ' + data);
    });
    /*
    this.authService.login(this.loginRequestPayload).subscribe(data=> {
        console.log('Login successful!')
      });

     */
  }

}
