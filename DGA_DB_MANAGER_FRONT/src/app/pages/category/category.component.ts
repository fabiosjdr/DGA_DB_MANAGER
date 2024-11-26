import { Component, ViewChild } from '@angular/core';
import { DefaultPageLayoutComponent } from '../../components/default-page-layout/default-page-layout.component';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { DefaultPageService } from '../../services/default-page.service';
import { CategoryForm } from '../../models/category.interface';

@Component({
  selector: 'app-category',
  standalone: true,
  imports: [
    DefaultPageLayoutComponent,
    MatInputModule,
    MatIconModule,
    ReactiveFormsModule,
    CommonModule //para o ngFor funcionar
  ],
  templateUrl: './category.component.html',
  styleUrl: './category.component.scss'
})

export class CategoryComponent {

  @ViewChild(DefaultPageLayoutComponent) DefaultPageLayoutComponent!: DefaultPageLayoutComponent;

  categoryForm! : FormGroup<CategoryForm>
  categoryList! : any;

  constructor(private router:Router, private service: DefaultPageService, private toastService: ToastrService){

    this.categoryForm = new FormGroup({
      id         : new FormControl<string | null>(null),
      name       : new FormControl("",[Validators.required])
    });

  }
  
  setList(list: []){ 
    this.categoryList = list;
  }
 
  edit(id:string){
    this.DefaultPageLayoutComponent.edit(id).subscribe();
  }
 
  delete(id:string){
    this.DefaultPageLayoutComponent.delete(id).subscribe();
  }
}
