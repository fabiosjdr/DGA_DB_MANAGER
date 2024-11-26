import { Component, ViewChild } from '@angular/core';
import { DefaultPageLayoutComponent } from '../../components/default-page-layout/default-page-layout.component';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { DefaultPageService } from '../../services/default-page.service';
import { ToastrService } from 'ngx-toastr';
import { StatusForm } from '../../models/status.interface';

@Component({
  selector: 'app-status',
  standalone: true,
  imports: [
    DefaultPageLayoutComponent,
    MatInputModule,
    MatIconModule,
    ReactiveFormsModule,
    CommonModule //para o ngFor funcionar
  ],
  templateUrl: './status.component.html',
  styleUrl: './status.component.scss'
})
export class StatusComponent {

  @ViewChild(DefaultPageLayoutComponent) DefaultPageLayoutComponent!: DefaultPageLayoutComponent;

  statusForm! : FormGroup<StatusForm>
  statusList! : any;

  constructor(private router:Router, private service: DefaultPageService, private toastService: ToastrService){

    this.statusForm = new FormGroup({
      id         : new FormControl<string | null>(null),
      name       : new FormControl("",[Validators.required])
    });

  }
  
  setList(list: []){ 
    this.statusList = list;
  }
 
  edit(id:string){
    this.DefaultPageLayoutComponent.edit(id).subscribe();
  }
 
  delete(id:string){
    this.DefaultPageLayoutComponent.delete(id).subscribe();
  }
}
