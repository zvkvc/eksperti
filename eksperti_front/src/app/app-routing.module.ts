import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {SignupComponent} from './auth/signup/signup.component';
import {LoginComponent} from './auth/login/login.component';
import {HomeComponent} from './home/home.component';
import {CreateSubforumComponent} from './subforum/create-subforum/create-subforum.component';
import {ViewPostComponent} from './post/view-post/view-post.component';
import {CreatePostComponent} from './post/create-post/create-post.component';
import {ListSubforumsComponent} from './subforum/list-subforums/list-subforums.component';
import {UserProfileComponent} from './auth/user-profile/user-profile.component';
import {AuthGuard} from './auth/auth.guard';


const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'view-post/:id', component: ViewPostComponent },
  { path: 'user-profile/:name', component: UserProfileComponent, canActivate: [AuthGuard] },
  { path: 'list-subforums', component: ListSubforumsComponent },
  { path: 'create-post', component: CreatePostComponent, canActivate: [AuthGuard] },
  { path: 'create-subforum', component: CreateSubforumComponent, canActivate: [AuthGuard] },
  { path: 'signup', component: SignupComponent },
  { path: 'login', component: LoginComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
