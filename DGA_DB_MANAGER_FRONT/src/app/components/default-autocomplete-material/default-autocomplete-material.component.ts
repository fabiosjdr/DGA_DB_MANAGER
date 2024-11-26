import { Component, Input } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { map, Observable, startWith } from 'rxjs';
import { DefaultPageService } from '../../services/default-page.service';
import { ToastrService } from 'ngx-toastr';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatFormFieldModule, MatLabel } from '@angular/material/form-field';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-default-autocomplete-material',
  standalone: true,
  imports: [
    MatAutocompleteModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    CommonModule
  ],
  templateUrl: './default-autocomplete-material.component.html',
  styleUrl: './default-autocomplete-material.component.scss'
})
export class DefaultAutocompleteMaterialComponent {
  
  @Input() displayField = "";
  @Input() valueField   = "";
 
  @Input() pageForm!  : FormGroup;
  @Input() autoCompleteControl!  : FormControl;

  //options: any[] = [];
  options: any[] = [];
  filteredOptions!: Observable<any>;

  constructor(private pageService:DefaultPageService, private toastService: ToastrService){


  }

  ngOnInit() {

    this.loadData().subscribe(() => {
  
      this.filteredOptions = this.autoCompleteControl.valueChanges.pipe(
        startWith(''),
        map(value => {
           const busca = typeof value === 'string' ?  value : "";
           return busca ? this._filter(busca) : this.options.slice();
        }),
      );

    });

  }

  loadData(): Observable<void> {

    this.pageService.setApiURL("http://localhost:3000/client");
    
    return new Observable<void>((observer) => {

      this.pageService.getAll().subscribe({
        next: (res) => {
          if (res != null && Array.isArray(res)) {
            res.map((value) => {
              this.options.push(value);
            });
          }
          observer.next(); // Emitimos um evento de conclusão
          observer.complete(); // Finalizamos o Observable
        },
        error: () => {
          this.toastService.error("Erro inesperado! Tente novamente mais tarde");
          observer.error(); // Emitimos um evento de erro
        }
      });
    });

  }

  displayFn(value: any): string {
    return value && value[this.displayField] ? value[this.displayField] : '';
  }

  setCurrentValue(value:any){
    this.pageForm.patchValue({ valueField: value[this.valueField] });
  }

  private _filter(filter: string): Object[] {
      
    const filterValue = filter ? filter.toLowerCase() : ''; // Verifica se name é uma string e faz o toLowerCase
    
    return this.options.filter(option => {
     
      return option.name.toLowerCase().includes(filterValue) 
    
    });
  }

}
