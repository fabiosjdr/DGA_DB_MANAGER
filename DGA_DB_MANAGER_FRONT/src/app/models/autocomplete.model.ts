import { FormGroup } from "@angular/forms";
import { Observable } from "rxjs";
import { AutoCompleteOption } from "./autocomplete.interface";


export class Autocomplete {

    //private options           : Option[] = [];

    constructor(private autocompleteForm:FormGroup,private displayField:string,private valueFieldTarget: string, private valueFieldSource:string ){

    }
    
    filter(filter: string, options: any[]): Object[] {
      
        const filterValue = filter ? filter.toLowerCase() : ''; 
        
        return options.filter(option => {
          return option[this.displayField].toLowerCase().includes(filterValue) 
        });
    }
    
    loadData(service:any): Observable<object> {
      
      return new Observable<object>((observer) => {

        service.getAll().subscribe({

          next: (res:object) => {
            
            var options : AutoCompleteOption[] = [];
            
            if (res != null && Array.isArray(res)) {
              res.map((value) => {
                options.push(value);
              });
            }

            observer.next(options); // Emitimos um evento de conclusão
            observer.complete(); // Finalizamos o Observable
          },
          error: (e:any) => {
            observer.error(e)
            observer.complete();
          }

        });

      });
  
    }

    //usando a função arrow o vinculo com o this continua 
    setValue = (value: { [key: string]: any }) => {
        this.autocompleteForm.patchValue({ 
            [this.valueFieldTarget]: value[this.valueFieldSource] ? value[this.valueFieldSource] : "" 
        });
    }

    displayFn = (value: { [key: string]: any }): string => {
        return value && value[this.displayField] ? value[this.displayField] : "";
    }
    
}