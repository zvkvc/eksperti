import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Post} from "./post";
import {CreatePostPayload} from "../post/create-post/create-post.payload";

@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(private httpClient: HttpClient) { }

  getAllPosts(): Observable<Array<Post>> {
    return this.httpClient.get<Array<Post>>('http://localhost:8080/api/posts/');
  }

  createPost(postPayload: CreatePostPayload): Observable<any> {
    return this.httpClient.post('http://localhost:8080/api/posts/', postPayload);
  }



  getPost(id: number): Observable<Post> {
    return this.httpClient.get<Post>('http://localhost:8080/api/posts/' + id);
  }

  getAllPostsByUser(name: string): Observable<Post[]> {
    return this.httpClient.get<Post[]>('http://localhost:8080/api/posts/by-user/' + name);
  }
}
