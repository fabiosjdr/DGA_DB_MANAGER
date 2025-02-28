import { Component, OnInit, ViewChild } from '@angular/core';
import { DefaultPageLayoutComponent } from '../../components/default-page-layout/default-page-layout.component';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { TeamsForm } from '../../models/teams.interface';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatDialogModule } from '@angular/material/dialog';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { NgxMaskDirective } from 'ngx-mask';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { DefaultPageService } from '../../services/default-page.service';
import { ToastrService } from 'ngx-toastr';
import { Workspaces } from '../../models/workspaces.interface';
import { Autocomplete } from '../../models/autocomplete.model';
import { map, Observable, startWith } from 'rxjs';
import { UserService } from '../../services/user.service';
import { WorkspacesService } from '../../services/workspaces.service';

@Component({
  selector: 'app-teams',
  standalone: true,
  imports: [
    DefaultPageLayoutComponent,
    MatInputModule,
    MatIconModule,
    MatFormFieldModule,
    MatDialogModule,
    ReactiveFormsModule,
    MatAutocompleteModule,
    CommonModule
  ],
  templateUrl: './teams.component.html',
  styleUrl: './teams.component.scss'
})
export class TeamsComponent implements OnInit {

  @ViewChild(DefaultPageLayoutComponent) DefaultPageLayoutComponent!: DefaultPageLayoutComponent;

  teamsForm! : FormGroup<TeamsForm>
  teamsList! : any;

  optionsWorkspaces          : Workspaces[] = [];
  autoFnWorkspaces!          : Autocomplete ;
  filteredOptionsWorkspaces! : Observable<any>;
  workspacesControl          = new FormControl('');

  constructor(private router:Router, private service: DefaultPageService, private toastService: ToastrService,private workspacesService : WorkspacesService){

    this.teamsForm = new FormGroup({
      id           : new FormControl<bigint | null>(null),
      id_workspaces: new FormControl<bigint | null>(null),
      title        : new FormControl("",[Validators.required]),
      description  : new FormControl("")
    });

  }

  ngOnInit() {

    //this.id = this.route.snapshot.paramMap.get('id') || '';
    this.initAutocompleteWorkspaces();

  }

  initAutocompleteWorkspaces(){
    
    this.autoFnWorkspaces = new Autocomplete(this.teamsForm,'name','id_workspaces','id');
    
    this.autoFnWorkspaces.loadData(this.workspacesService).subscribe((res: any) => {
     
      this.optionsWorkspaces = res ;

      this.filteredOptionsWorkspaces = this.workspacesControl.valueChanges.pipe(
        startWith(''),
        map(value => {
          const busca = typeof value === 'string' ?  value : "";
          return busca ? this.autoFnWorkspaces.filter(busca,res) : this.optionsWorkspaces.slice();
        }),
      );

    });

  }

  setList(list: []){ 
    console.log(list);
    this.teamsList = list;
  }

  edit(id:string){
    this.DefaultPageLayoutComponent.edit(id).subscribe({

        next: (res) => {

          this.workspacesService.get(res.workspaces.id).subscribe({
            next: (resWork) =>  {
              this.workspacesControl.setValue(resWork);
              this.autoFnWorkspaces.setValue(resWork);
            },
            error: () => this.toastService.error("Erro inesperado! Tente novamente mais tarde")
          })

        }

    });
  }
 
  delete(id:string){
    this.DefaultPageLayoutComponent.delete(id).subscribe();
  }

  members(id:string){
    this.router.navigate(['members', id]);
  }


}
