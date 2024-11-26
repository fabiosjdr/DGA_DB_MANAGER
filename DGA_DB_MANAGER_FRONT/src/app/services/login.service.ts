import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LoginResponse } from '../types/login-response.type';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  apiURL: string = "http://localhost:3000/";

  constructor(private httpClient:HttpClient) { }

  login(email:string,password:string){
    return this.httpClient.post<LoginResponse>(this.apiURL+'auth/login',{email,password}).pipe(
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


  signup(name:string,email: string,password:string){
    return this.httpClient.post<LoginResponse>(this.apiURL+"auth/register",{name,email,password}).pipe(
      //tap deixa sincrono
      tap((value) => {
        sessionStorage.setItem("auth-token",value.token);
        sessionStorage.setItem("username",value.email);
      })
    );
  }
}
