import { Injectable } from '@angular/core';

import { DefaultPageService } from './default-page.service';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class ClientService  extends DefaultPageService {
  constructor( httpClient:HttpClient) {
    super(httpClient);
    this.setApiURL(environment.apiUrl + 'client');
  }
  
}
