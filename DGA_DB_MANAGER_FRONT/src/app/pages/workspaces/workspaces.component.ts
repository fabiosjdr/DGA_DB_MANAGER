import { Component, ViewChild } from '@angular/core';
import { DefaultPageLayoutComponent } from '../../components/default-page-layout/default-page-layout.component';
import { Router } from '@angular/router';
import { DefaultPageService } from '../../services/default-page.service';
import { ToastrService } from 'ngx-toastr';
import { WorkspacesForm } from '../../models/workspaces.interface';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-workspaces',
  standalone: true,
  imports: [
    DefaultPageLayoutComponent,
    MatInputModule,
    MatIconModule,
    ReactiveFormsModule,
    CommonModule //para o ngFor funcionar
  ],
  templateUrl: './workspaces.component.html',
  styleUrl: './workspaces.component.scss'
})
export class WorkspacesComponent {

  @ViewChild(DefaultPageLayoutComponent) DefaultPageLayoutComponent!: DefaultPageLayoutComponent;

  workspacesForm! : FormGroup<WorkspacesForm>
  workspacesList! : any;

  constructor(private router:Router, private service: DefaultPageService, private toastService: ToastrService){

    this.workspacesForm = new FormGroup({
      id         : new FormControl<string | null>(null),
      name       : new FormControl("",[Validators.required])
    });

  }


  setList(list: []){ 
    this.workspacesList = list;
  }
 
  edit(id:string){
    this.DefaultPageLayoutComponent.edit(id).subscribe();
  }
 
  delete(id:string){
    this.DefaultPageLayoutComponent.delete(id).subscribe();
  }

}
