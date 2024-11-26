import { Component, ViewChild } from '@angular/core';

import { ClientService } from '../../services/client.service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

import { CommonModule } from '@angular/common';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';

import { DefaultPageLayoutComponent } from '../../components/default-page-layout/default-page-layout.component';
import { ClientForm } from '../../models/client.interface';


@Component({
  selector: 'app-client',
  standalone: true,
  imports: [
    DefaultPageLayoutComponent,
    MatInputModule,
    MatIconModule,
    ReactiveFormsModule,
    CommonModule //para o ngFor funcionar
  ],
  providers:[
    ClientService
  ],
  templateUrl: './client.component.html',
  styleUrl: './client.component.scss'
})

export class ClientComponent {
  
  @ViewChild(DefaultPageLayoutComponent) DefaultPageLayoutComponent!: DefaultPageLayoutComponent;

  clientForm! : FormGroup<ClientForm>
  clientList! : any;

  constructor(private router:Router, private clientService: ClientService, private toastService: ToastrService){

    this.clientForm = new FormGroup({
      id         : new FormControl<string | null>(null),
      name       : new FormControl("",[Validators.required]),
      responsable: new FormControl("",[Validators.required]),
      telephone  : new FormControl("",[Validators.required]),
      hours      : new FormControl("",[Validators.required])
    });

  }
  
  setList(list: []){ 
    this.clientList = list;
  }
 
  edit(id:string){
    this.DefaultPageLayoutComponent.edit(id).subscribe();
  }
 
  delete(id:string){
    this.DefaultPageLayoutComponent.delete(id).subscribe();
  }

}
