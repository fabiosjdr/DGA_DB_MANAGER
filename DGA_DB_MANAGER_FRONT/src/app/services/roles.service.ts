import { Injectable } from '@angular/core';
import { DefaultPageService } from './default-page.service';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class RolesService extends DefaultPageService{

  constructor(httpClient:HttpClient) { 
    super(httpClient);
    this.setApiURL(environment.apiUrl + 'user_roles');
  }
}