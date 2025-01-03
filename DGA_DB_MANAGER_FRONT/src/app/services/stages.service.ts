import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { DefaultPageService } from './default-page.service';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class StagesService extends DefaultPageService {
  constructor( httpClient:HttpClient) {
    super(httpClient);
    this.setApiURL(environment.apiUrl + 'activity_stage');
  }
  
}
