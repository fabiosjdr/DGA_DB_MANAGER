import { Component, OnInit } from '@angular/core';
import { ContainerGeneralComponent } from '../../components/container-general/container-general.component';
import { ClientService } from '../../services/client.service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

import { CommonModule } from '@angular/common';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginatorModule } from '@angular/material/paginator';


interface ClientForm {
  id         : FormControl<string|null>,
  name       : FormControl,
  responsable: FormControl,
  telephone  : FormControl,
  hours      : FormControl
}

interface ClientSearchForm {
  search : FormControl,
}


@Component({
  selector: 'app-client',
  standalone: true,
  imports: [
    ContainerGeneralComponent,
    ReactiveFormsModule, // sempre esqueço disso
    CommonModule,
    MatFormFieldModule, 
    MatInputModule, 
    MatIconModule,
    MatPaginatorModule
  ],
  providers:[
    ClientService
  ],
  templateUrl: './client.component.html',
  styleUrl: './client.component.scss'
})

export class ClientComponent implements OnInit {
  
  mensagem    : string = "";
  clientForm! : FormGroup<ClientForm>
  clientSearchForm! : FormGroup<ClientSearchForm> 
  clientList! : any;
  totalElements : number = 0;
  pageSize    : number = 5;
  pageLength  : number = 0;
  page        : number = 0;

  constructor(private router:Router, private clientService: ClientService, private toastService: ToastrService){

    this.clientForm = new FormGroup({
      id         : new FormControl<string | null>(null),
      name       : new FormControl("",[Validators.required]),
      responsable: new FormControl("",[Validators.required]),
      telephone  : new FormControl("",[Validators.required]),
      hours      : new FormControl("",[Validators.required]),
    });


    this.clientSearchForm = new FormGroup({
      search :  new FormControl("",[Validators.required]),
    });

  }
  
  ngOnInit(): void {
    this.search();
  }

  defaultState(){

    this.clientForm.patchValue({
      id         : null,
      name       : null,
      responsable: null,
      telephone  : null,
      hours      : null
    });
  }

  load(){
    
    this.clientService.getAll().subscribe((res) => {
      this.clientList = res;
    });


  }
  
  search(){

    this.clientService.search(this.clientSearchForm.value.search,this.page,this.pageSize).subscribe({
      next: (res) =>  {

        if(res != null){

          this.clientList = res.content;
          this.totalElements = res.totalElements;
          this.pageLength = res.totalElements;
        }else{
          this.clientList = [];
          this.totalElements = 0;
          this.pageLength = 0;
        }
       
      },
      error: () => this.toastService.error("Erro inesperado! Tente novamente mais tarde")
    })
  }

  submit(){ 
   
    this.clientService.save(this.clientForm.value).subscribe({
      next: () =>  {
         this.toastService.success("Dados salvos com sucesso!"),
         this.search();
         this.defaultState();
      },
      error: () => this.toastService.error("Erro inesperado! Tente novamente mais tarde")
    })

  }

  edit(id:string){

      this.clientService.get(id).subscribe({
      next: (res) =>  {

         this.clientForm.patchValue({
          id   : res.id,
          name: res.name,
          responsable: res.responsable,
          telephone: res.telephone,
          hours: res.hours
        });

      },
      error: () => this.toastService.error("Erro inesperado! Tente novamente mais tarde")
    })
  }

  delete(id:string){
    if(confirm('Tem certeza que deseja realizar essa operação?')){

      this.clientService.delete(id).subscribe({
        next: () =>  {
           this.toastService.success("Exclusão realizada com sucesso!"),
           this.search();
           this.defaultState();
        },
        error: () => this.toastService.error("Erro inesperado! Tente novamente mais tarde")
      });

    }
  }

  onPaginateChange(event:any){
   
    this.page = event.pageIndex;
    this.pageSize = event.pageSize;
    this.search();
  }

}
