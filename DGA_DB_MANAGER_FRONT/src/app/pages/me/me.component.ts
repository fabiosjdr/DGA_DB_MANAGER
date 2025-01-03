import { AfterViewInit, ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { DefaultPageLayoutComponent } from '../../components/default-page-layout/default-page-layout.component';
import { FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { UsersForm } from '../../models/users.interface';
import { CommonModule } from '@angular/common';
import { DefaultUserLayoutComponent } from '../../components/default-user-layout/default-user-layout.component';
import { UserService } from '../../services/user.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-me',
  standalone: true,
  imports: [
    DefaultPageLayoutComponent,
    DefaultUserLayoutComponent,
    ReactiveFormsModule,
    CommonModule //para o ngFor funcionar
  ],
  templateUrl: './me.component.html',
  styleUrl: './me.component.scss'
})
export class MeComponent implements OnInit,AfterViewInit {

  @ViewChild(DefaultPageLayoutComponent) DefaultPageLayoutComponent!: DefaultPageLayoutComponent;
  @ViewChild(DefaultUserLayoutComponent) DefaultUserLayoutComponent!: DefaultUserLayoutComponent;

  meForm!   : FormGroup<UsersForm>
  formValid : boolean = false;

  constructor(
    private cdr: ChangeDetectorRef,
    private meService: UserService,
    private toastService: ToastrService
  ){}

  ngOnInit(){
   // this.DefaultUserLayoutComponent.edit('me');
  }

  ngAfterViewInit() {

    if (this.DefaultUserLayoutComponent) {
      this.DefaultUserLayoutComponent.edit(this.DefaultPageLayoutComponent,'me');
    } 

    this.cdr.detectChanges(); // Atualiza a visualização, se necessário
  }

  onFormInitialized(form: FormGroup<UsersForm>) {
  
    this.meForm = form;

    // Marca a visualização como atualizada
    this.cdr.detectChanges();
  }

  setFormValid(valid:boolean){
    this.formValid = valid;
  }
}
