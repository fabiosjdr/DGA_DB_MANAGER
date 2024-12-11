import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LoginResponse } from '../types/login-response.type';
import { Observable, tap } from 'rxjs';
import { DefaultPageService } from './default-page.service';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class LoginService extends DefaultPageService {


  constructor(httpClient:HttpClient) { 
    super(httpClient);
  }

  login(email:string,password:string){
    this.setApiURL(environment.apiUrl+'auth/login');
    return this.post({email,password}).pipe(
      //tap deixa sincrono
      tap((value) => {
        sessionStorage.setItem("auth-token",value.token);
        sessionStorage.setItem("username",value.email);
      })
    );
  }

  signup(name:string,email: string,password:string){

    this.setApiURL(environment.apiUrl+'auth/register');

    return this.post({name,email,password}).pipe(
      //tap deixa sincrono
      tap((value) => {
        sessionStorage.setItem("auth-token",value.token);
        sessionStorage.setItem("username",value.email);
      })
    );
  }

  logout(): Observable<void>{

    return new Observable<void>((observer) => {

      sessionStorage.removeItem("auth-token");
      sessionStorage.removeItem("username");

      observer.next(); // Emitimos um evento de conclusão
      observer.complete(); // Finalizamos o Observable
    })
    
  }
}

