import { Injectable } from '@angular/core';
import {SubforumModel} from "./subforum-response";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class SubforumService {
  constructor(private httpClient: HttpClient) { }

  getAllSubforums(): Observable<Array<SubforumModel>> {
    return this.httpClient.get<Array<SubforumModel>>('http://localhost:8080/api/subforums/');
  }

  createSubforum(subforumModel: SubforumModel): Observable<SubforumModel> {
    return this.httpClient.post<SubforumModel>('http://localhost:8080/api/subforums/',
      subforumModel);
  }
}
