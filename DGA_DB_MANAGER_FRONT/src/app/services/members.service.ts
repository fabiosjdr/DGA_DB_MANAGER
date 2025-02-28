import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { DefaultPageService } from './default-page.service';

@Injectable({
  providedIn: 'root'
})
export class MembersService extends DefaultPageService {

  constructor(httpClient:HttpClient) { 
    super(httpClient);
    this.setApiURL(environment.apiUrl + 'members');
  }
}
