import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { RouterModule, Routes } from '@angular/router';
import { SignUpComponent } from './auth/sign-up/sign-up.component';
import { LoginComponent } from './auth/login/login.component';
import { WeatherComponet } from 'src/app/weather-componet/weather-componet.component';
import { AuthInterceptorService } from './auth/auth-interceptor.service';
import { NotesComponent } from './notes/notes.component';
import { AuthGuard } from './auth/services/auth.guard';
import { PermissionDeniedComponent } from './permission-denied/permission-denied.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { SuperAdminAuthGuard } from './auth/services/super.admin.auth.guard';
import { AdminAuthNotesGuard } from './auth/services/admin.auth.notes.guard';
import { AdminsComponent } from './super-admin-only/admins.component';
import { UserAdminProfileComponent } from './user-admin-profile/user-admin-profile.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
 import { ToastrModule } from 'ngx-toastr';
import { LineBreakePipePipe } from './line-breake-pipe.pipe';

const appRoutes: Routes = [

   { path: '', component: WeatherComponet },
  { path: 'sign-up', component: SignUpComponent },
  { path: 'login', component: LoginComponent },
    { path: 'profile/:userId', component: UserAdminProfileComponent  },
  { path: 'notes', component: NotesComponent , canActivate: [AuthGuard , AdminAuthNotesGuard ]  }, 
  { path: 'admins', component: AdminsComponent ,  canActivate: [AuthGuard , SuperAdminAuthGuard ] }, 
  { path: 'permission-denied', component: PermissionDeniedComponent }, 
  { path: '**', component: NotFoundComponent }, 


];


@NgModule({
  declarations: [
    AppComponent,
    SignUpComponent,
    LoginComponent,
    UserAdminProfileComponent,
    WeatherComponet,
    NotesComponent,
    AdminsComponent,
    PermissionDeniedComponent,
    NotFoundComponent,
    LineBreakePipePipe,
    
 
   
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    BrowserAnimationsModule, 
    RouterModule.forRoot(appRoutes),
    ToastrModule.forRoot({
      positionClass:"toast-center-center",
      preventDuplicates: true,
      timeOut:2000
    })
    
 
  ],
  providers: [
    {
    provide: HTTP_INTERCEPTORS,
    useClass:  AuthInterceptorService,
    multi: true
  }
  ],
  bootstrap: [AppComponent],

})
export class AppModule { }
