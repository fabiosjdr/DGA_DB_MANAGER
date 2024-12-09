import { Component, EventEmitter, input, Input, OnInit, Output, ViewChild } from '@angular/core';
import { DefaultPageLayoutComponent } from '../default-page-layout/default-page-layout.component';
import { AbstractControl, FormControl, FormGroup, ReactiveFormsModule, ValidationErrors, ValidatorFn, Validators} from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { CommonModule } from '@angular/common';
import { UserRoles } from '../../models/user_roles.interface';
import { RolesService } from '../../services/roles.service';
import { ToastrService } from 'ngx-toastr';
import { map, Observable, startWith } from 'rxjs';
import { Autocomplete } from '../../models/autocomplete.model';
import { MatButtonModule } from '@angular/material/button';
import { Router } from '@angular/router';
import { DefaultPageService } from '../../services/default-page.service';
import { UsersForm } from '../../models/users.interface';

@Component({
  selector: 'app-default-user-layout',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatInputModule,
    MatIconModule,
    MatAutocompleteModule,
    MatButtonModule,
    CommonModule //para o ngFor funcionar
  ],
  templateUrl: './default-user-layout.component.html',
  styleUrl: './default-user-layout.component.scss'
})

export class DefaultUserLayoutComponent implements OnInit {

  //@ViewChild(DefaultPageLayoutComponent) DefaultPageLayoutComponent!: DefaultPageLayoutComponent;
 // @Input()  DefaultPageLayoutComponent! : DefaultPageLayoutComponent;
  @Output() formValid       = new EventEmitter();
  @Output() formInitialized = new EventEmitter<FormGroup<UsersForm>>();

  autoFnRoles! : Autocomplete ;
  userForm!    : FormGroup<UsersForm>;

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
      password         : new FormControl<string | null>(null,Validators.minLength(6)),
      confirm_password : new FormControl<string | null>(null,Validators.minLength(6)),
      active           : new FormControl(true),
      id_role          : new FormControl("")
    },
    { validators: this.passwordsMatchValidator() } 
  );
    
  }

  passwordsMatchValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const password = control.get('password')?.value;
      const confirmPassword = control.get('confirm_password')?.value;
      return password === confirmPassword ? null : { passwordsMismatch: true };
    };
  }

  ngOnInit() {

    //this.userForm.setValidators(this.passwordsMatchValidator());

    // Revalida o formulário após adicionar o validador
    //this.userForm.updateValueAndValidity();

    this.initAutocompleteRoles();

    this.formInitialized.emit(this.userForm);

    // Escuta mudanças na validação
    this.userForm.statusChanges.subscribe(() => {
      this.formValid.emit(this.userForm.valid);
    });
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

  showPasswordFields(event:Event){ 
    event.preventDefault();
    this.changePassword = !this.changePassword;
  }

  displayFn(user_roles: UserRoles): string {
    return user_roles && user_roles.name ? user_roles.name : '';
  }

  edit(DefaultPageLayoutComponent:DefaultPageLayoutComponent,id:string){
    
    DefaultPageLayoutComponent.edit(id).subscribe({
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

  
  
}
