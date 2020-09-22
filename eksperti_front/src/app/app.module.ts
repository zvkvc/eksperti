import { BrowserModule, Title } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { SignupComponent } from './auth/signup/signup.component';
import {ReactiveFormsModule} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import { LoginComponent } from './auth/login/login.component';
import {NgxWebstorageModule} from 'ngx-webstorage';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {ToastrModule} from 'ngx-toastr';
import { HomeComponent } from './home/home.component';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import { PostTileComponent } from './common/post-tile/post-tile.component';
import { VoteButtonComponent } from './common/vote-button/vote-button.component';
import { SideBarComponent } from './common/sidebar/sidebar.component';
import { SubforumSideBarComponent } from './subforum-sidebar/subforum-sidebar.component';
import { ListSubforumsComponent } from './subforum/list-subforums/list-subforums.component';
import { CreateSubforumComponent } from './subforum/create-subforum/create-subforum.component';
import { CreatePostComponent } from './post/create-post/create-post.component';
import { ViewPostComponent } from './post/view-post/view-post.component';

import {EditorModule} from '@tinymce/tinymce-angular';
import {TokenInterceptor} from './auth/token-interceptor';
import {UserProfileComponent} from './auth/user-profile/user-profile.component';
import { FooterComponent } from './footer/footer.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    SignupComponent,
    LoginComponent,
    HomeComponent,
    PostTileComponent,
    VoteButtonComponent,
    SideBarComponent,
    SubforumSideBarComponent,
    ListSubforumsComponent,
    CreateSubforumComponent,
    CreatePostComponent,
    ViewPostComponent,
    UserProfileComponent,
    FooterComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgxWebstorageModule.forRoot(), // for accessing LocalStorage
    BrowserAnimationsModule,
    ToastrModule.forRoot(),
    FontAwesomeModule,
    EditorModule

  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    },
    Title
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
