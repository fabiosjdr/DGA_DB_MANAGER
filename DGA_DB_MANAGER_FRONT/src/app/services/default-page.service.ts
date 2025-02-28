import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from 'rxjs';
import { SearchResponse } from '../types/search-response.type';

let token   :string|null;
let headers :HttpHeaders;
let params  :HttpParams;

@Injectable({
  providedIn: 'root'
})

export class DefaultPageService {
  
  private apiURL: string = '';
  private pathVariable : string = '';
  private methodPathVariable! : Array<string> ;

  constructor(private httpClient:HttpClient) {

     token   =  sessionStorage.getItem("auth-token");
     headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
     params  = new HttpParams();
   }

    setApiURL(url: string) {
      this.apiURL = url;
    }

    setPathVariable(variable: any) {
      this.pathVariable = variable;
    }

    setMethodPathVariable(methodPathVariable: string[]) {
      this.methodPathVariable = methodPathVariable;
    }

    setParams(id:string,value:string){
      params = params.set(id, value);
    }

    getApiURL(): string {
      return this.apiURL;
    }

    getAll(){

        var url = this.apiURL;
        
        if ( this.pathVariable != '') {
          url += '/'+this.pathVariable;
        }
        
        return this.httpClient.get<any>(url, { headers,params }).pipe(
          map((response: any) => response)
        );
    }

    get(id:string){
     
      return this.httpClient.get<any>(this.apiURL+"/"+id, { headers,params }).pipe(
        map((response: any) => response)
      );
    }

    post(values:object){
      return this.httpClient.post<any>(this.apiURL,values,{headers,params});
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

      var url = this.apiURL;
      
      if (this.methodPathVariable.includes('search') && this.pathVariable != '') {
        url += '/'+this.pathVariable;
      }
     
      var query = url+"/search?text="+search+"&page="+page+"&size="+size;
      //console.log(query);
      return this.httpClient.get<SearchResponse>(query, { headers,params })

    }

}
