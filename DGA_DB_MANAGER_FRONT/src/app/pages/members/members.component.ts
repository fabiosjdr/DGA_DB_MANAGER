import { Component, OnInit, ViewChild } from '@angular/core';
import { DefaultPageLayoutComponent } from '../../components/default-page-layout/default-page-layout.component';
import { CommonModule } from '@angular/common';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { DefaultPageService } from '../../services/default-page.service';
import { ToastrService } from 'ngx-toastr';
import { UserService } from '../../services/user.service';
import { ActivatedRoute } from '@angular/router';
import { MembersForm } from '../../models/members.interface';
import { Users } from '../../models/users.interface';
import { Autocomplete } from '../../models/autocomplete.model';
import { map, Observable, startWith } from 'rxjs';

@Component({
  selector: 'app-members',
  standalone: true,
  imports: [
    DefaultPageLayoutComponent,
    MatInputModule,
    MatIconModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    MatAutocompleteModule,
    CommonModule
  ],
  templateUrl: './members.component.html',
  styleUrl: './members.component.scss'
})

export class MembersComponent implements OnInit {


  @ViewChild(DefaultPageLayoutComponent) DefaultPageLayoutComponent!: DefaultPageLayoutComponent;

  membersForm! : FormGroup<MembersForm>
  membersList! : any;
  
  optionsUsers : Users[]   = [];

  userControl    = new FormControl('');

  autoFnClient!  : Autocomplete ;
  filteredOptionsUser!  : Observable<any>;

  id! : string;

  constructor(
    private pageService    : DefaultPageService,
    private toastService   : ToastrService,
    private userService    : UserService,
    private route          : ActivatedRoute,
  ){
   
    //faz o vinculo com o formulario padrao
    this.membersForm = new FormGroup({
      id       : new FormControl<string | null>(null),
      id_user  : new FormControl("",[Validators.required]),
      id_team  : new FormControl("",[Validators.required]),
      active   : new FormControl("",[Validators.required])
    });
    
    
  }

  ngOnInit() {

    this.id = this.route.snapshot.paramMap.get('id') || '';
    this.initAutocompleteUser();

    this.membersForm.patchValue({id_team:this.id});
    
  }

  initAutocompleteUser(){
    
    this.autoFnClient = new Autocomplete(this.membersForm,'name','id_user','id');
    
    this.autoFnClient.loadData(this.userService).subscribe((res: any) => {
     
      this.optionsUsers = res ;

      this.filteredOptionsUser = this.userControl.valueChanges.pipe(
        startWith(''),
        map(value => {
          const busca = typeof value === 'string' ?  value : "";
          return busca ? this.autoFnClient.filter(busca,res) : this.optionsUsers.slice();
        }),
      );

    });

  }

  getPath(){
    //return `members/${this.id}`;
    return `members`;
  }

  getPathVariable(){
    return this.id;
  }

  setList(list: []){ 
    this.membersList = list;
  }

  edit(id:string){
    // this.DefaultPageLayoutComponent.edit(id);
     this.DefaultPageLayoutComponent.edit(id).subscribe({
 
       next: (res) => {
      
         this.membersForm.patchValue({id_team:res.teams.id});
       
         this.userService.get(res.user.id).subscribe({
           next: (resUser) => {
             this.userControl.setValue(resUser);
             this.autoFnClient.setValue(resUser);
           },
           error: () => this.toastService.error("Erro inesperado! Tente novamente mais tarde")
         })
 
 
       }
 
     })
   }
 
   delete(id:string){
     this.DefaultPageLayoutComponent.delete(id).subscribe();
   }
   
}
