import { Component, OnInit, ViewChild } from '@angular/core';
import { DefaultPageLayoutComponent } from '../../components/default-page-layout/default-page-layout.component';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { AbstractControl, FormControl, FormGroup, ReactiveFormsModule, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { UsersForm } from '../../models/users.interface';
import { Router } from '@angular/router';
import { DefaultPageService } from '../../services/default-page.service';
import { ToastrService } from 'ngx-toastr';
import { CommonModule } from '@angular/common';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { UserRoles } from '../../models/user_roles.interface';
import { Autocomplete } from '../../models/autocomplete.model';
import { map, Observable, startWith } from 'rxjs';
import { RolesService } from '../../services/roles.service';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-users',
  standalone: true,
  imports: [
    DefaultPageLayoutComponent,
    ReactiveFormsModule,
    MatInputModule,
    MatIconModule,
    MatAutocompleteModule,
    MatButtonModule,
    CommonModule //para o ngFor funcionar
  ],
  templateUrl: './users.component.html',
  styleUrl: './users.component.scss'
})
export class UsersComponent implements OnInit {

  @ViewChild(DefaultPageLayoutComponent) DefaultPageLayoutComponent!: DefaultPageLayoutComponent;

  userForm! : FormGroup<UsersForm>
  userList! : any;

  autoFnRoles! : Autocomplete ;

  optionsRoles           : UserRoles[]  = [];
  filteredOptionsRoles!  : Observable<any>;
  
  rolesControl   = new FormControl('');
  changePassword = false;

  constructor(
      private roleService  : RolesService,  
      private toastService : ToastrService
  ){

    this.userForm = new FormGroup({
        id               : new FormControl<BigInt | null>(null),
        name             : new FormControl("",[Validators.required]),
        email            : new FormControl("",[Validators.required,Validators.email]),
        password         : new FormControl("",Validators.minLength(6)),
        confirm_password : new FormControl("",Validators.minLength(6)),
        active           : new FormControl(true),
        id_role          : new FormControl("")
      },
      { validators: this.passwordsMatchValidator() } 
    );

  }

  ngOnInit() {

    this.initAutocompleteRoles();
   

  }

  initAutocompleteRoles(){
    
    
    this.autoFnRoles = new Autocomplete(this.userForm,'name','id_role','id');
    
    this.autoFnRoles.loadData(this.roleService).subscribe((res: any) => {
     
      this.optionsRoles = res ;

      this.filteredOptionsRoles = this.rolesControl.valueChanges.pipe(
        startWith(''),
        map(value => {
          const busca = typeof value === 'string' ?  value : "";
          return busca ? this.autoFnRoles.filter(busca,res) : this.optionsRoles.slice();
        }),
      );

    });

  }
  
  setList(list: []){ 
    this.userList = list;
  }
 
  edit(id:string){
    this.DefaultPageLayoutComponent.edit(id).subscribe({
      next: (res) => {

        this.roleService.get(res.roles.id).subscribe({
          next: (res) =>  {
            this.rolesControl.setValue(res);
            this.autoFnRoles.setValue(res);
          },
          error: () => this.toastService.error("Erro inesperado! Tente novamente mais tarde")
        })

      },
      error:() => this.toastService.error("Erro inesperado! Tente novamente mais tarde")

    });
  }
 
  delete(id:string){
    this.DefaultPageLayoutComponent.delete(id).subscribe();
  }

  displayFn(user_roles: UserRoles): string {
    return user_roles && user_roles.name ? user_roles.name : '';
  }

  showPasswordFields(event:Event){ 
    event.preventDefault();
    this.changePassword = !this.changePassword;
  }

  // Função para validar se os campos 'password' e 'confirm_password' são iguais
  passwordsMatchValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const password = control.get('password')?.value;
      const confirmPassword = control.get('confirm_password')?.value;
      return password === confirmPassword ? null : { passwordsMismatch: true };
    };
  }
}
