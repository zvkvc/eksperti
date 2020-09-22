import { Injectable } from '@angular/core';
import {Comment} from './comment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor(private httpClient: HttpClient) { }

  getAllCommentsForPost(postId: number): Observable<Comment[]> {
    return this.httpClient.get<Comment[]>('http://localhost:8080/api/comments/by-post/' + postId);
  }

  postComment(comment: Comment): Observable<any> {
    return this.httpClient.post<any>('http://localhost:8080/api/comments/', comment);
  }

  getAllCommentsByUser(name: string) {
    return this.httpClient.get<Comment[]>('http://localhost:8080/api/comments/by-user/' + name);
  }
}
