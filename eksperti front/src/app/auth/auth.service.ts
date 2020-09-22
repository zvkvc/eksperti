import {EventEmitter, Injectable, Output} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {SignupRequest} from './signup/signup-request';
import {Observable} from 'rxjs';
import {LoginRequestPayload} from './login/loginRequest';
import {LoginResponse} from './login/loginResponse';
import {map, tap} from 'rxjs/operators';
import {LocalStorageService} from 'ngx-webstorage';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  @Output()
  loggedIn: EventEmitter<boolean> = new EventEmitter();
  @Output()
  username: EventEmitter<string> = new EventEmitter();
  refreshTokenPayload = {
    refreshToken: this.getRefreshToken(), // initialize from whatever is currently in local storage
    username: this.getUserName() // -||-
  };

  // where does localStorageService persist data?
  constructor(private httpClient: HttpClient, private localStorage: LocalStorageService) {
  }

  signup(signupRequest: SignupRequest): Observable<any> {
    console.log("Sending the payload...");
    return this.httpClient.post('http://localhost:8080/api/auth/signup', signupRequest, {responseType: 'text'});
  }

  login(loginRequestPayload: LoginRequestPayload): Observable<Boolean> {

    return this.httpClient.post<LoginResponse>('http://localhost:8080/api/auth/login', loginRequestPayload)
      .pipe(map(data => { // data is of type LoginResponse which corresponds to AuthenticationResponse DTO in Spring API
        this.localStorage.store('authenticationToken', data.authenticationToken); // (key, data) pairs
        this.localStorage.store('username', data.username);
        this.localStorage.store('refreshToken', data.refreshToken);
        this.localStorage.store('expiresAt', data.expiresAt);

        console.log(data.authenticationToken);
        console.log(data.username);
        console.log(data.refreshToken);
        console.log(data.expiresAt);

        return true; // login was successful
      }));
  }


  getJwtToken(): any {
    return this.localStorage.retrieve('authenticationToken');
  }

  refreshToken() { // gets new jwt from backend and saves it to the local cache
    return this.httpClient.post<LoginResponse>('http://localhost:8080/api/auth/refresh/token',
      this.refreshTokenPayload)
      .pipe(tap(response => {
        this.localStorage.clear('authenticationToken');
        this.localStorage.clear('expiresAt');
        this.localStorage.store('authenticationToken', response.authenticationToken);
        this.localStorage.store('expiresAt', response.expiresAt);
      }));
  }

  getUserName() {
    return this.localStorage.retrieve('username');
  }

  getRefreshToken() {
    return this.localStorage.retrieve('refreshToken');
  }

  isLoggedIn(): boolean {
    return (this.getJwtToken() != null); // (this.getJwtToken() != null) is a boolean statement
  }
}
