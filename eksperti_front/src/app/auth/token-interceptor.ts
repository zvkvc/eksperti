import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {AuthService} from './auth.service';
import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable, throwError} from 'rxjs';
import {catchError, filter, switchMap, take} from 'rxjs/operators';
import {LoginResponse} from './login/loginResponse';



@Injectable({ // because we want to inject other services
  providedIn: 'root'
})
// appends JWT to every request's authorization header
export class TokenInterceptor implements HttpInterceptor {

  isTokenRefreshing = false;
  refreshTokenSubject: BehaviorSubject<any> = new BehaviorSubject(null);
  // the main reason to use a BehaviorSubject instead of  a Subject or an Observable..
  // ..is because a BehaviorSubject can have a value assigned to it so when we receive the new token from refreshToken()..
  // ..we can assign token to refreshTokenSubject and access the new token inside the interceptor

  constructor(public authService: AuthService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        if (req.url.indexOf('refresh') !== -1 || req.url.indexOf('login') !== -1) {
            return next.handle(req);
        }
        const jwtToken = this.authService.getJwtToken();
        if (jwtToken) {
            return next.handle(this.addToken(req, jwtToken)).pipe(catchError(error => {
                if (error instanceof HttpErrorResponse
                    && error.status === 403) {
                    return this.handleAuthErrors(req, next);
                } else {
                    return throwError(error);
                }
            }));
        }
        return next.handle(req);
  }
  /*
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (req.url.indexOf('refresh') !== -1 || req.url.indexOf('login') !== -1
      || (req.url.indexOf('/api/posts/') !== -1 && req.method.indexOf('GET') !== -1)
      || (req.url.indexOf('/api/subforum') !== -1 && req.method.indexOf('GET') !== -1)) {
      return next.handle(req);
    }
    if (req.url.indexOf('refresh') !== -1 || req.url.indexOf('login') !== -1) {
      // if url contains either 'refresh' or 'login' that means we're in process of getting a token (we don't have it yet)
      return next.handle(req); // pass on an unmodified request
    } // otherwise, for all other requests we must append a token ( indexOf() returns -1 if no occurrence )
  */
/*
    const jwtToken = this.authService.getJwtToken();

    if (jwtToken) {
      return next.handle(this.addToken(req, jwtToken)).pipe(catchError(error => { // return modified request
        // and since we immediately get an Observable response after handle() -- we can pipe it to catch errors
        if (error instanceof HttpErrorResponse
          && error.status === 403) { // 403==forbidden
          return this.handleAuthErrors(req, next);
        } else {
          return throwError(error);
        }
      }));
    }
    return next.handle(req);

  }
*/
  private addToken(req: HttpRequest<any>, jwtToken: any) {
    return req.clone({ // we provide an updated object with appended token
      headers: req.headers.set('Authorization', 'Bearer ' + jwtToken) // (header, value) pair
    });
    // headers is of type HttpHeaders
    // body field is of type any
    // see HttpRequest definition on angular.io for more info
  }

  // since this method gets called only in case of 403, we assume token needs refreshing
  // in that case we refreshToken() and pass on this new token to addToken()
  private handleAuthErrors(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (!this.isTokenRefreshing) {
      this.isTokenRefreshing = true;
      this.refreshTokenSubject.next(null);

      return this.authService.refreshToken().pipe(
        switchMap((refreshTokenResponse: LoginResponse) => {
          this.isTokenRefreshing = false;
          this.refreshTokenSubject.next(refreshTokenResponse.authenticationToken);

          // switchMap from LoginResponse() to:
          return next.handle(this.addToken(req,
            refreshTokenResponse.authenticationToken));
        })
      );
    } else {
      return this.refreshTokenSubject.pipe(
        filter(result => result !== null),
        take(1),
        switchMap((res) => {
          return next.handle(this.addToken(req,
            this.authService.getJwtToken()));
        })
      );
    }
  }

}
