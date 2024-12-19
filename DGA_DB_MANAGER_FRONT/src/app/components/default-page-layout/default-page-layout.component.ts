import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ContainerGeneralComponent } from '../container-general/container-general.component';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { DefaultPageService } from '../../services/default-page.service';
import { MatPaginatorModule } from '@angular/material/paginator';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { CommonModule, DatePipe } from '@angular/common';

interface PageSearchForm {
  search : FormControl,
}


@Component({
  selector: 'app-default-page-layout',
  standalone: true,
  imports: [
    ContainerGeneralComponent,
    ReactiveFormsModule,
    MatPaginatorModule,
    CommonModule
  ],
  providers: [DefaultPageService],
  templateUrl: './default-page-layout.component.html',
  styleUrl: './default-page-layout.component.scss'
})

export class DefaultPageLayoutComponent {

  @Input() title               : String = "";
  @Input() pageForm!           : FormGroup;
  @Input() pageServiceOptional : any = null;
  @Input() path                : string = "";
  @Input() disableSaveBtn      : boolean = true;
  @Input() showList            : boolean = true;

  @Output() setList = new EventEmitter();
 
  pageSearchForm! : FormGroup<PageSearchForm> 
  pageList!       : any;
  totalElements   : number = 0;
  pageSize        : number = 5;
  pageLength      : number = 0;
  page            : number = 0;
  

  constructor(private router:Router, private pageService:DefaultPageService,  private toastService: ToastrService){
    
    this.pageSearchForm = new FormGroup({
      search :  new FormControl("",[Validators.required]),
    });

  }

  ngOnInit(): void {

    if(this.path != ''){ 
      this.pageService.setApiURL(environment.apiUrl+this.path);
    }

    if(this.pageServiceOptional != null){
      this.setPageService(this.pageServiceOptional);
    }

    if(this.showList){
      this.search();
    }
  }

  triggerSubmit(){ 
    //this.pageForm.valid
    if (this.pageForm.valid) {
      this.submit();
    }

  }

  setPageService(pageService:any){
    this.pageService = pageService;
  }

  

  defaultState(){

    let tempPageForm: { [key: string]: any } = {};

    Object.keys(this.pageForm.controls).forEach(controlName => {
      // Acessa o controle correspondente
      const control = this.pageForm.get(controlName);
      
      //console.log(`Nome do controle: ${controlName}, Valor: ${control?.value}`);
      // Você pode verificar se o controle é válido, estado de touch, etc.
      if (control) {
        tempPageForm[controlName] = null;
      }

    });

    this.pageForm.patchValue(tempPageForm);
    
  }

  load(){
    
    this.pageService.getAll().subscribe((res) => {
      this.pageList = res;
    });

  }
  
  search(){
    
    this.pageService.search(this.pageSearchForm.value.search,this.page,this.pageSize).subscribe({
      
      next: (res) =>  {

        if(res != null){

          this.pageList      = res.content;
          this.totalElements = res.totalElements;
          this.pageLength    = res.totalElements;

          this.setList.emit(this.pageList);


        }else{
         
          this.pageList      = [];
          this.totalElements = 0;
          this.pageLength    = 0;
          this.setList.emit(this.pageList);
        }
       
      },
      error: () => this.toastService.error("Erro inesperado! Tente novamente mais tarde")
    })
  }

  submit(){ 
    console.log(this.pageForm.value);
    this.pageService.save(this.pageForm.value).subscribe({
      next: () =>  {
         this.toastService.success("Dados salvos com sucesso!"),
         this.search();
         this.defaultState();
      },
      error: () => this.toastService.error("Erro inesperado! Tente novamente mais tarde")
    })

  }

  edit(id:string): Observable <any>{
    
    return new Observable<any>((observer) => {
     
        this.pageService.get(id).subscribe({

          next: (res) =>  {
    
            let tempPageForm: { [key: string]: any } = {};
    
            Object.keys(this.pageForm.controls).forEach(controlName => {
              // Acessa o controle correspondente
              const control = this.pageForm.get(controlName);
              
              //console.log(`Nome do controle: ${controlName}, Valor: ${control?.value}`);
              // Você pode verificar se o controle é válido, estado de touch, etc.
              if (control) {
                tempPageForm[controlName] = res[controlName];
              }

              observer.next(res); // Emitimos um evento de conclusão
              observer.complete(); // Finalizamos o Observable
    
            });
    
            this.pageForm.patchValue(tempPageForm);
    
          },
          error: () => this.toastService.error("Erro inesperado! Tente novamente mais tarde")
        })

    });
     
  }

  delete(id:string): Observable <any>{

    return new Observable<any>((observer) => {

        if(confirm('Tem certeza que deseja realizar essa operação?')){

          this.pageService.delete(id).subscribe({
            next: () =>  {
              this.toastService.success("Exclusão realizada com sucesso!"),
              this.search();
              this.defaultState();

              observer.next(); // Emitimos um evento de conclusão
              observer.complete(); // Finalizamos o Observable
            },
            error: () => this.toastService.error("Erro inesperado! Tente novamente mais tarde")
          });
    
        }else{
          observer.complete();
        }
    });

    
  }

  onPaginateChange(event:any){
   
    this.page     = event.pageIndex;
    this.pageSize = event.pageSize;
    this.search();
  }

  validateDate(e:any,type:string,field:string,formControl:FormControl){
    
    const datePipe      = new DatePipe('pt-BR');
   
    if(type == 'hour'){
      var dataFormatada   = this.pageForm.get(field)?.value;
      dataFormatada       = dataFormatada != '' ? datePipe.transform(dataFormatada, `yyyy-MM-dd ${e.target.value}`) : datePipe.transform( new Date(), `yyyy-MM-dd ${e.target.value}`);
      
      if(this.checkDate(dataFormatada)){
        this.pageForm.patchValue({[field]:dataFormatada});
      }else{
        alert('hora invalida');
        e.target.value = null;
      }

    }else{
      const hour          = formControl.value != null && formControl.value != '' ? formControl.value.slice(0,2) + ':' + formControl.value.slice(2) : '00:00';
      const dataFormatada = datePipe.transform(e.target.value, `yyyy-MM-dd ${hour}`)

      if(this.checkDate(dataFormatada)){
        this.pageForm.patchValue({[field]:dataFormatada});
      }else{
        alert('data invalida');
        e.target.value = null;
      }
      this.pageForm.patchValue({[field]:dataFormatada});
      
    }
    console.log(this.pageForm.value)
  }

  checkDate(dateString: string|null): boolean {

    if(dateString == null){
      return false;
    }
    const date = new Date(dateString);

    // Verifica se a data é válida
    return !isNaN(date.getTime()); // `getTime()` retorna NaN para datas inválidas
  }
  
}
