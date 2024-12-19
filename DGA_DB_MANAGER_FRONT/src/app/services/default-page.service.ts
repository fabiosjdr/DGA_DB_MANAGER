import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from 'rxjs';
import { SearchResponse } from '../types/search-response.type';

let token   :string|null;
let headers :HttpHeaders;

@Injectable({
  providedIn: 'root'
})

export class DefaultPageService {
  
  private apiURL: string = '';

  constructor(private httpClient:HttpClient) {

     token =  sessionStorage.getItem("auth-token");
     headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

   }

    setApiURL(url: string) {
      this.apiURL = url;
    }

    getApiURL(): string {
      return this.apiURL;
    }

    getAll(){
        return this.httpClient.get<any>(this.apiURL, { headers }).pipe(
          map((response: any) => response)
        );
    }

    get(id:string){
     
      return this.httpClient.get<any>(this.apiURL+"/"+id, { headers }).pipe(
        map((response: any) => response)
      );
    }

    post(values:object){
      return this.httpClient.post<any>(this.apiURL,values,{headers});
    }

    save(values:object){

      if('id' in values && values.id != null){
        return this.httpClient.put<any>(this.apiURL,values,{headers});
      }else{
        return this.httpClient.post<any>(this.apiURL,values,{headers});
      }
    }

    delete(id:string){
      return this.httpClient.delete<any>(this.apiURL+"/"+id,{headers});
    }

    search(search:string,page:number,size:number){
     
      var query = this.apiURL+"/search?text="+search+"&page="+page+"&size="+size;
      //console.log(query);
      return this.httpClient.get<SearchResponse>(query, { headers })

    }

}
