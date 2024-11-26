import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { DefaultPageLayoutComponent } from '../../components/default-page-layout/default-page-layout.component';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { CommonModule, DatePipe } from '@angular/common';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { provideNativeDateAdapter } from '@angular/material/core';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { map, Observable, startWith } from 'rxjs';
import { DefaultPageService } from '../../services/default-page.service';
import { ToastrService } from 'ngx-toastr';
import { ActivityForm } from '../../models/activity.interface';
import { Client } from '../../models/client.interface';
import { Autocomplete } from '../../models/autocomplete.model';
import { Category } from '../../models/category.interface';
import { ClientService } from '../../services/client.service';
import { CategoryService } from '../../services/category.service';
import { Project } from '../../models/project.interface';
import { ProjectService } from '../../services/project.service';
import { Status } from '../../models/status.interface';
import { StatusService } from '../../services/status.service';
import { NgxMaskDirective, provideNgxMask } from 'ngx-mask';

@Component({
  selector: 'app-activity',
  standalone: true,
  imports: [
    DefaultPageLayoutComponent,
    MatDatepickerModule,
    MatInputModule,
    MatIconModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    MatAutocompleteModule,
    NgxMaskDirective,
    CommonModule //para o ngFor funcionar,
  ],
  providers: [
    provideNgxMask(),
    provideNativeDateAdapter(),
  ],
  templateUrl: './activity.component.html',
  styleUrl: './activity.component.scss'
})
export class ActivityComponent implements OnInit {

  @ViewChild(DefaultPageLayoutComponent) DefaultPageLayoutComponent!: DefaultPageLayoutComponent;

  activityForm! : FormGroup<ActivityForm>
  activityList! : any;
  
  optionsClient  : Client[]   = [];
  optionsCategory: Category[] = [];
  optionsProject : Project[]  = [];
  optionsStatus  : Status[]  = [];

  filteredOptionsClient!  : Observable<any>;
  filteredOptionsCategory!: Observable<any>;
  filteredOptionsProject! : Observable<any>;
  filteredOptionsStatus!  : Observable<any>;

  clientControl    = new FormControl('');
  categoryControl  = new FormControl('');
  projectControl   = new FormControl('');
  statusControl    = new FormControl('');

  startHourControl = new FormControl('');
  endHourControl   = new FormControl('');

  autoFnClient!  : Autocomplete ;
  autoFnCategory!: Autocomplete ;
  autoFnProject! : Autocomplete ;
  autoFnStatus!  : Autocomplete ;

  constructor(
    private pageService    : DefaultPageService,
    private toastService   : ToastrService,
    private clientService  : ClientService,
    private categoryService: CategoryService, 
    private projectService : ProjectService, 
    private statusService  : StatusService, 
  ){
   
    //faz o vinculo com o formulario padrao
    this.activityForm = new FormGroup({
      id         : new FormControl<string | null>(null),
      activity   : new FormControl("",[Validators.required]),
      id_category: new FormControl("",[Validators.required]),
      id_client  : new FormControl("",[Validators.required]),
      id_project : new FormControl("",[Validators.required]),
      id_status  : new FormControl("",[Validators.required]),
      start_date : new FormControl("",[Validators.required]),
      end_date   : new FormControl("",[Validators.required]),
      id_account : new FormControl(1,[Validators.required])
    });
    
    
  }

  validateDate(e:any,type:string,field:string){
    
    const datePipe      = new DatePipe('pt-BR');

    if(type == 'hour'){
      var dataFormatada   = this.activityForm.get(field)?.value;
      dataFormatada       = dataFormatada != '' ? datePipe.transform(dataFormatada, `yyyy-MM-dd ${e.target.value}`) : datePipe.transform( new Date(), `yyyy-MM-dd ${e.target.value}`);
      this.activityForm.patchValue({[field]:dataFormatada});
    }else{
      const hour          = this.startHourControl.value != null && this.startHourControl.value != '' ? this.startHourControl.value.slice(0,2) + ':' + this.startHourControl.value.slice(2) : '00:00';
      const dataFormatada = datePipe.transform(e.target.value, `yyyy-MM-dd ${hour}`)
      this.activityForm.patchValue({[field]:dataFormatada});
    }
    console.log(this.activityForm.value)
  }

  
  ngOnInit() {

    this.initAutocompleteClient();
    this.initAutocompleteCategory();
    this.initAutocompleteProject();
    this.initAutocompleteStatus();

  }

  initAutocompleteClient(){
    
    
    this.autoFnClient = new Autocomplete(this.activityForm,'name','id_client','id');
    
    this.autoFnClient.loadData(this.clientService).subscribe((res: any) => {
     
      this.optionsClient = res ;

      this.filteredOptionsClient = this.clientControl.valueChanges.pipe(
        startWith(''),
        map(value => {
          const busca = typeof value === 'string' ?  value : "";
          return busca ? this.autoFnClient.filter(busca,res) : this.optionsClient.slice();
        }),
      );

    });

  }

  initAutocompleteCategory(){
   
    this.autoFnCategory = new Autocomplete(this.activityForm,'name','id_category','id');
    
    this.autoFnCategory.loadData(this.categoryService).subscribe((res: any) => {
     
      this.optionsCategory = res ;

      this.filteredOptionsCategory = this.categoryControl.valueChanges.pipe(
        startWith(''),
        map(value => {
          const busca = typeof value === 'string' ?  value : "";
          return busca ? this.autoFnCategory.filter(busca,this.optionsCategory) : this.optionsCategory.slice();
        }),
      );

    });

  }

  initAutocompleteProject(){
   
    this.autoFnProject = new Autocomplete(this.activityForm,'name','id_project','id');
    
    this.autoFnProject.loadData(this.projectService).subscribe((res: any) => {
     
      this.optionsProject = res ;

      this.filteredOptionsProject = this.projectControl.valueChanges.pipe(
        startWith(''),
        map(value => {
          const busca = typeof value === 'string' ?  value : "";
          return busca ? this.autoFnProject.filter(busca,this.optionsProject) : this.optionsProject.slice();
        }),
      );

    });

  }

  initAutocompleteStatus(){
   
    this.autoFnStatus = new Autocomplete(this.activityForm,'name','id_status','id');
    
    this.autoFnStatus.loadData(this.statusService).subscribe((res: any) => {
      
      this.optionsStatus = res ;

      this.filteredOptionsStatus = this.statusControl.valueChanges.pipe(
        startWith(''),
        map(value => {
          const busca = typeof value === 'string' ?  value : "";
          return busca ? this.autoFnStatus.filter(busca,this.optionsStatus) : this.optionsStatus.slice();
        }),
      );

    });

  }

  setList(list: []){ 
   this.activityList = list;
  }

  edit(id:string){
   // this.DefaultPageLayoutComponent.edit(id);

    this.DefaultPageLayoutComponent.edit(id).subscribe({
      next: (res) => {

        this.clientService.get(res.client.id).subscribe({
          next: (res) =>  {
            this.clientControl.setValue(res);
          },
          error: () => this.toastService.error("Erro inesperado! Tente novamente mais tarde")
        })

        this.categoryService.get(res.category.id).subscribe({
          next: (res) =>  {
            this.categoryControl.setValue(res);
          },
          error: () => this.toastService.error("Erro inesperado! Tente novamente mais tarde")
        })

        this.statusService.get(res.status.id).subscribe({
          next: (res) =>  {
            this.statusControl.setValue(res);
          },
          error: () => this.toastService.error("Erro inesperado! Tente novamente mais tarde")
        })

        this.projectService.get(res.project.id).subscribe({
          next: (res) =>  {
            this.projectControl.setValue(res);
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

