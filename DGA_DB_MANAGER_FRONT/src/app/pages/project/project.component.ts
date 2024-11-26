import { Component, OnInit, ViewChild } from '@angular/core';
import { DefaultPageLayoutComponent } from '../../components/default-page-layout/default-page-layout.component';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { CommonModule } from '@angular/common';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { Observable } from 'rxjs';
import {map, startWith} from 'rxjs/operators';
import { DefaultPageService } from '../../services/default-page.service';
import { ToastrService } from 'ngx-toastr';
import { ProjectForm } from '../../models/project.interface';
import { Client } from '../../models/client.interface';


@Component({
  selector: 'app-project',
  standalone: true,
  imports: [
    DefaultPageLayoutComponent,
    MatInputModule,
    MatIconModule,
    MatFormFieldModule, 
    MatDatepickerModule,
    MatAutocompleteModule,
    ReactiveFormsModule,
    CommonModule
  ],
  providers: [DefaultPageService],
  templateUrl: './project.component.html',
  styleUrl: './project.component.scss'
})

export class ProjectComponent implements OnInit {

  @ViewChild(DefaultPageLayoutComponent) DefaultPageLayoutComponent!: DefaultPageLayoutComponent;

  clientControl = new FormControl('');

  projectForm! : FormGroup<ProjectForm>
  projectList! : any;

  options: Client[] = [];
  filteredOptions!: Observable<any>;

  constructor( private pageService:DefaultPageService,private clientService:DefaultPageService, private toastService: ToastrService){

    //faz o vinculo com o formulario padrao
    this.projectForm = new FormGroup({
      id         : new FormControl<BigInt | null>(null),
      name       : new FormControl("",[Validators.required]),
      description: new FormControl("",[Validators.required]),
      id_client  : new FormControl("",[Validators.required]),
      start_date : new FormControl<string | null>(null),
      end_date   : new FormControl<string | null>(null),
      id_account : new FormControl(1,[Validators.required]),
    });

  }

  ngAfterViewInit() {

    
    // Sobrescrevendo o comportamento do método do filho
    // this.DefaultPageLayoutComponent.submit = () => {
        
    //     this.pageService.setApiURL("http://localhost:3000/project");

    //     //console.log('Função do filho foi sobrescrita pelo pai!');
    //     var current_id_client = this.projectForm.value.id_client.id;
       
    //     this.projectForm.patchValue({ id_client: current_id_client });
       
        
    //     this.pageService.save(this.projectForm.value).subscribe({
    //       next: () =>  {
    //         this.toastService.success("Dados salvos com sucesso!"),
    //         this.DefaultPageLayoutComponent.search();
    //         this.DefaultPageLayoutComponent.defaultState();
    //       },
    //       error: (e) => {
    //         console.log(e);
    //         this.toastService.error("Erro inesperado! Tente novamente mais tarde")
    //       }
    //     })

    // };
  }

  ngOnInit() {

    this.loadClients().subscribe(() => {
  
      this.filteredOptions = this.clientControl.valueChanges.pipe(
        startWith(''),
        map(value => {
           const busca = typeof value === 'string' ?  value : "";
           return busca ? this._filter(busca) : this.options.slice();
        }),
      );

    });

  }


  private _filter(filter: string): Object[] {
      
      const filterValue = filter ? filter.toLowerCase() : ''; // Verifica se name é uma string e faz o toLowerCase
      
      return this.options.filter(option => {
       
        return option.name.toLowerCase().includes(filterValue) 
      
      });
  }

  
  loadClients(): Observable<void> {

    this.clientService.setApiURL("http://localhost:3000/client");
    
    return new Observable<void>((observer) => {
      this.clientService.getAll().subscribe({
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

  setList(list: []){ 
   this.projectList = list;
  }

  edit(id:string){
   
    this.DefaultPageLayoutComponent.edit(id).subscribe({
      next: (res) => {
        this.clientService.get(res.client.id).subscribe({
          next: (res) =>  {
            this.clientControl.setValue(res);
          },
          error: () => this.toastService.error("Erro inesperado! Tente novamente mais tarde")
        })
      }
    })
 
  }

  delete(id:string){
    this.DefaultPageLayoutComponent.delete(id).subscribe();
  }

  displayFn(client: Client): string {
    
    //this.projectForm.patchValue({ id_client: client.id });
    return client && client.name ? client.name : '';
  }

  setIdClient(value:Client){
    this.projectForm.patchValue({ id_client: value.id });
  }

}
