import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Vote} from "./vote-button/Vote";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class VoteService {

  constructor(private httpClient: HttpClient) { }

  vote(vote: Vote): Observable<any> {
    return this.httpClient.post('http://localhost:8080/api/votes/', vote);
  }
}
