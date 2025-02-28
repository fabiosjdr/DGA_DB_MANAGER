import { AfterViewInit, Component, OnInit, ViewChild,CUSTOM_ELEMENTS_SCHEMA, Renderer2, ElementRef, TemplateRef, ViewContainerRef, ComponentFactoryResolver, ComponentRef} from '@angular/core';
import { ContainerGeneralComponent } from '../../components/container-general/container-general.component';
import { CommonModule } from '@angular/common';
import { KanbanModule } from 'smart-webcomponents-angular/kanban';
import { ActivatedRoute } from '@angular/router';
import {ptBr} from "../../translate/pt-br.language"

import { StagesService } from '../../services/stages.service';
import { Stage } from '../../models/stage.interface';
import { DetailsService } from '../../services/details.service';
import { Detail } from '../../models/detail.interface';
import { ToastrService } from 'ngx-toastr';
import { Observable, take } from 'rxjs';
import { UserService } from '../../services/user.service';
import { Users } from '../../models/users.interface';
import { KanbanColumnCustomHeaderComponent } from '../../components/kanban-column-custom-header/kanban-column-custom-header.component';
import { ClientService } from '../../services/client.service';
import { Client } from '../../types/client-response.type';
import { Members, MembersResponse } from '../../models/members.interface';
import { MembersService } from '../../services/members.service';

@Component({
  selector: 'app-kanban',
  standalone: true,
  imports: [
    ContainerGeneralComponent,
    CommonModule, 
    KanbanModule
    
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  templateUrl: './kanban.component.html',
  styleUrls: ['./kanban.component.scss']
})


export class KanbanComponent implements AfterViewInit, OnInit{

  
  @ViewChild('kanban', { static: false }) kanban: ElementRef | undefined;
  @ViewChild('smartWindow', { static: false }) smartWindow!: any;

  id!: string;
 
  addNewColumn             = false;
  allowColumnRemove        = false;
  collapsible              = false;
  addNewButton             = true;
  editable                 = true;
  columnActions            = true;
  columnEditMode           = 'menu';
  columnFooter             = true;
  columnSummary            = true;
  columnColors             = true;
  columnColorEntireSurface = true;
  allowColumnEdit          = true;
  allowColumnReorder       = true;
  allowComment             = true;
  taskActions              = true;
  taskDue                  = true;
  taskComments             = true;
  currentUser              = 0;
  taskProgress             = true;
  
  messages                 = ptBr;
  
  closeButtonHit           = false;
  
  columns   : { id: number; label : string; dataField: string,func:any}[] = [];
  dataSource: { id: number; status: string; text : string,clientId:number|null,comment:[]}[] = [];

  clientValue : {value:string} = {value:''};

  
  users     : { id: number; name  : string}[] = [];
  clients   : { value: number; label  : string}[] = [];

  mydialog! : any;
  mytask!   : any;
  myeditor  : any;
  myvalidate: boolean = false;
  mycomment : boolean = false;

  private clickListener!: () => void ;

  private userLoaded     = false;
  private clientLoaded   = false;
  private columnsLoaded  = false;
  private dataLoaded     = false;

  constructor(
    private route  : ActivatedRoute,
    private stage  : StagesService, 
    private user   : UserService, 
    private members: MembersService,
    private client : ClientService, 
    private detailService : DetailsService, 
    private toastService: ToastrService,
    private renderer: Renderer2
  ) {}


  ngOnInit(): void {

    this.id = this.route.snapshot.paramMap.get('id') || '';

    //descomentar caso deseje habilitar a mudança de contabilização do timer no kanaban ao clicar no icone
    // this.clickListener = this.renderer.listen('document', 'click', (event: Event) => {

    //   const target = event.target as HTMLElement;
     
    //   if (target.classList.contains('columnTimer')) {
    //      this.toggleTimer(target);
    //   }

    // });
  }

  ngOnDestroy() {
    if (this.clickListener) {
      this.clickListener(); // Remove o ouvinte para evitar vazamentos de memória
    }
  }

  ngAfterViewInit(): void {

    
      this.init();

    

  }

  ngAfterViewChecked() {

    // if(this.userLoaded && this.dataLoaded && this.columnsLoaded){

    //   this.userLoaded = this.dataLoaded = this.columnsLoaded = false;
    //   const savedState = sessionStorage.getItem('kanbanState');
    //   setTimeout(() => {
    //     if (savedState) {
    //         const parsedState = JSON.parse(savedState);
    //         this.kanban?.nativeElement.loadState(parsedState);
    //         console.log('Kanban state loaded:', parsedState);
    //     } else {
    //         console.warn('No saved Kanban state found');
    //     }
    //   }, 150);
      

    // }
    
  }

  init(): void {
      // init code.
      this.loadColumns();
      this.loadUser();
      this.loadClients();
      this.loadKanbanData();
  }

  loadClients(): void{

    this.client.getAll().subscribe({

      next: (res) => {
        //console.log(res);
        this.clients = res.map((item: Client) => ({
          value     : item.id,
          label     : item.name
        }));

      },
      error: (err) => {
        console.error('Erro ao tentar obter usuários:', err);
      },
      complete: () => {
        this.clientLoaded = true;
        //console.log(this.clients)
        //console.log('Operação finalizada.');
      },
    });

  }

  loadUser(): void{

    this.members.setParams('id_team','1');
    this.members.getAll().subscribe({

      next: (res) => {
        //console.log(res);
        this.users = res.map((item: MembersResponse) => ({
          id       : item.user.id,
          name     : item.user.name
        }));

      },
      error: (err) => {
        console.error('Erro ao tentar obter usuários:', err);
      },
      complete: () => {
        this.userLoaded = true;
      },
    });

  }

  loadColumns(): void {

    this.stage.get(this.id).subscribe({

      next: (res) => {
      
        this.columns = res.map((item: Stage) => ({
          id       : item.id,
          label    : item.name,
          dataField: item.name.replace(' ', '_'),
          timer    : item.timer
        }));

      },
      error: (err) => {
        console.error('Erro ao tentar obter estágios:', err);
      },
      complete: () => {
        this.columnsLoaded = true;
      },
      
    });

  }

  loadKanbanData() : void{

    this.dataLoaded = false;
    
    this.detailService.get(this.id).subscribe({
      
      next: (res) => {
      
        this.dataSource = res.map((item: Detail) => ({
          id         : Number(item.id), 
          text       : item.title, 
          status     : item.stage.name.replace(' ','_'),
          description: item.description,
          priority   : item.priority,
          color      : item.color,
          progress   : item.progress,
          userId     : item.user?.id,
          clientId   : item.client?.id,
          startDate  : item.start_date,
          dueDate    : item.due_date,
          comments   : []
        }));

      },
      error: (err) => {
        console.error('Erro ao tentar obter estágios:', err);
      },
      complete: () => {
        
        this.dataLoaded = true;

      },
    });

    

  
  }

  onKanbanChange(event: any): void {
    //console.log('change');
    const { detail } = event;
    //console.log(this.kanban);
  }

  onColumnAdd(event: any){

    const detail = event.detail.newColumn;
    this.saveColumn(detail);
   
  }
  
  onColumnUpdate(event:any) {
    //console.log(this.kanban);
    const detail = event.detail.column;
    this.saveColumn(detail);
  }

  onColumnRemove(event:any) {
    const detail = event.detail.column,
    id = detail.id;

    this.stage.delete(id).subscribe({
      next: () =>  {
         this.toastService.success("Dados removidos com sucesso!");
      },
      error: () => this.toastService.error("Erro inesperado! Tente novamente mais tarde")
    });
    // event handling code goes here.
  }

  saveColumn(detail:any){
    
    const payload = this.prepareColumnData(detail);
    
    this.stage.save(payload).subscribe({
      next: (res) =>  {

         this.toastService.success("Dados salvos com sucesso!");
         this.loadColumns();
      },
      error: () => this.toastService.error("Erro inesperado! Tente novamente mais tarde")
    });

  }

  prepareColumnData(detail:any){

    const value = [detail];

    const payload  = value.map((item: any) => ({
                      id         : (item?.id) ? item.id : null,
                      id_activity: this.id,
                      name       : item.label,
                      timer      : false
                    })
                  );

    return payload[0];
  }

  shouldClose(): Promise<boolean> {
    
    return new Promise((resolve) => {
      setTimeout(() => {
        resolve(this.closeButtonHit); // Espera um tempo antes de verificar
      }, 100); // Tempo para garantir a verificação
    });
  }

  onTaskBeforeAdd = (event: any) => {
    
    
    
  }

  onTaskAdd(event: any){
   
      const detail = event.detail;
      this.saveTask(detail);
   
    
  }

  onTaskUpdate(event:any) {
    const detail = event.detail;
    
    this.saveTask(detail);
  }

  onTaskRemove(event:any) {
    const detail = event.detail,
    id = detail.id;
    
    this.detailService.delete(id).subscribe({
      next: () =>  {
         this.toastService.success("Dados removidos com sucesso!");
      },
      error: () => this.toastService.error("Erro inesperado! Tente novamente mais tarde")
    });

  }

  onClosing(event: any){
   
    if(!this.myvalidate){

      event.preventDefault();
  
      if(this.myeditor.text.value == ''){

        setTimeout(() => {
          if(this.myvalidate == false && this.mycomment == false){
            alert('Por favor preencha o campo título!');
          }
        }, 300);
        
      }else{
        this.myvalidate = true;
        this.mydialog.close();
      }
    
    }
  }

  saveTask(detail:any): void{
    
    detail.value.clientId = this.clientValue.value;
  
    console.log(detail.value);
    return;
    if(this.mycomment == false){

      const payload = this.prepareTasKData(detail);
  
      // console.log(payload);
      // return;
  
      this.detailService.save(payload).subscribe({
        next: (res) =>  {
  
          if (res && res.id) {
            detail.value.id = res.id; 
          }
        
          this.toastService.success("Dados salvos com sucesso!");
          this.loadKanbanData();
  
        },
        error: () => this.toastService.error("Erro inesperado! Tente novamente mais tarde")
      });
    
    }else{
      this.mycomment = false;
    }

    
    
  }

  prepareTasKData(detail:any){
   
    const value      = [detail.value];
    const filter     = detail.value.status;
   
    const old_filter = (detail?.oldValue) ? detail.oldValue.status :  null
    const id         = detail.id;

    const stage  = this.columns.filter(option => {
      return option.dataField.includes(filter) 
    })

    const old_stage  = this.columns.filter(option => {
      return option.dataField.includes(old_filter) 
    })
    
    const old_id_stage = (old_stage.length) ? old_stage[0].id : null;

    const payload  = value.map((item: any) => ({
                      id          : (id != undefined) ? id  : null,
                      id_activity : this.id,
                      title       : item.text,
                      description : item.description,
                      priority    : item.priority,
                      color       : item.color,
                      id_user     : item.userId,
                      id_client   : item.clientId,
                      start_date  : item.startDate,
                      due_date    : item.dueDate,
                      progress    : item.progress,
                      id_stage    : stage[0].id,
                      old_id_stage: old_id_stage,
                      comments    : item.comments
                    })
                  );
                  //console.log(payload);
    return payload[0];
  }

  dialogRendered = (dialog: any, editors: any, labels: any, tabs: any, layout: any) => {
    
    this.mydialog = dialog;

    // hides the tabs in the kanban.
    //console.log(tabs);
    //tabs['all'].style.display = 'none';
    //tabs['subtasks'].style.display = 'none';

    // the editors layout. By default it is in 2 columns and uses Grid layout. We set it to block in order to make it to occupy the full width.
    //layout.style.display = 'block';

    // the following editors would be hidden.
    //console.log(dialog.editors)
    for (let key in dialog.editors) {
        //console.log(key);
        switch (key) {
            
            case 'text':
              
              // const smartInput = dialog.editors[key];
              // const inputElement = smartInput.querySelector('input'); // O input interno

              // if (inputElement) { 
              //     inputElement.setAttribute('required', 'true');

              //     // Adiciona validação ao digitar
              //     inputElement.addEventListener('input', (event: any) => {
              //         const value = event.target.value.trim();
              //         event.target.setCustomValidity(value ? '' : 'Este campo é obrigatório!');
              //     });
              // }
             
            break;
            case 'progress':
                //editors[key].style.display = 'none';
                //labels[key].style.display = 'none';
            break;
            case 'userId':

              editors[key].style.display = 'block';
              // console.log( editors[key]);
              // if (this.users.length > 0) {
              //   editors[key].value = String(this.users[0].id);  // Set the first user as the selected value
              // }

            break;
            case 'emptyTags':
            case 'tags':
              editors[key].style.display = 'none';
              labels[key].style.display = 'none';
            break;
            case 'startDate':
            case 'color':
            case 'priority':
            case 'dueDate':
            case 'checklist': {
              //editors[key].style.display = 'none';
              //labels[key].style.display = 'none';
            break;
            }
        }
    }

    document.querySelector('.smart-close-button')?.addEventListener('click', () => {
      this.myvalidate = true;
      this.mycomment  = true;
      this.mydialog.close();
    });

    document.querySelector('.send')?.addEventListener('click', () => {
      this.myvalidate = true;
    });

    document.querySelector('.cancel')?.addEventListener('click', () => {
      this.myvalidate = true;
      this.mydialog.close();
    });

  };

  dialogCustomizationFunction = (dialog: any, task: any, editors: any, labels: any,type:any) => {
    
    this.myvalidate = false;
    this.mycomment  = false;

    this.mytask     = task;
    this.myeditor   = editors;

    

    if(type == 'edit'){ 
      this.clientValue.value = task.data?.clientId ? task.data.clientId.toString() : "" ;
    }else{
      this.clientValue.value = '';
    }
    
    if (!editors.clientId) { 
     
        const multiComboInput = document.createElement('smart-multi-combo-input');

        multiComboInput.setAttribute('data-field', 'clientId');
        multiComboInput.setAttribute('allow-deselect', 'true'); 
        multiComboInput.setAttribute('single-select', '');

        // Atribui a lista de clientes
        multiComboInput.dataSource = this.clients;

        // Define valor inicial do campo
        multiComboInput.value = this.clientValue.value ;

        const label = document.createElement('div');
        label.setAttribute('class', 'editor-label');
        label.innerHTML = 'Cliente';

        const secondColumn = dialog.content.querySelector('.column:not(.single-column)');

        if (secondColumn) {
            secondColumn.appendChild(label);
            secondColumn.appendChild(multiComboInput);
            editors.clientId = multiComboInput;
            editors.clientId.value = this.clientValue.value;
        } else {
            console.error('Segunda coluna não encontrada no diálogo.');
        }

        // Evento para atualizar a task ao alterar o valor
        
          multiComboInput.addEventListener('change', (event: any) => {
          
            const selectedValue     = event.detail.value;
            editors.clientId.value  = selectedValue;
            this.clientValue.value  = selectedValue;

            if(task?.data){
              task.data.clientId = selectedValue
            }

          });


    } else { 
        editors.clientId.value = this.clientValue.value;
    }

    
  };

  headerHtml(data:any){
  
    //chamado automaticamente pelo kaban na hora de montar os header da coluna #columnHeaderTemplate
    const span = document.createElement('span');

    const icon = (data.timer) ? "bi-clock-fill" : "bi-clock";
    const title = (data.timer) ? "Controle de tempo ativo" : "Controle de tempo inativo";
    span.innerHTML = `${data.label} <i id="${data.id}" timer="${data.timer}" class="bi ${icon} columnTimer"></i>`;
    //span.classList.add('columnTimer');
    span.title = title;
    span.id = data.id;

    return span.outerHTML;  // Retorna o HTML como string
  }

  toggleTimer(element:HTMLElement){
   
    const payload = this.prepareStageData(element);
    
    const icon = (payload?.timer) ? "bi-clock-fill" : "bi-clock";
    
    element.outerHTML = `<i id="${payload.id}" timer="${payload.timer}" class="bi ${icon} columnTimer"></i>`;
    
    this.stage.save(payload).subscribe({
      next: () =>  {
         this.toastService.success("Dados salvos com sucesso!");
      },
      error: () => this.toastService.error("Erro inesperado! Tente novamente mais tarde")
    });

  }

  prepareStageData(element:HTMLElement){

    const payload  = {
      id         : element.id,
      id_activity: this.id,
      timer      : !(element.getAttribute('timer') == 'true') //inverto o valor 
    }

    return payload;

  }

  onColumnReorder(event:any){
    this.kanban?.nativeElement.saveState();
  }

  onDragEnd = (event: any) => {
   
    setTimeout(() => {
      
      var kanbanState = this.kanban?.nativeElement.getState();
      sessionStorage.setItem('kanbanState',JSON.stringify(kanbanState))
      console.log('Kanban state saved:', kanbanState);

    }, 800);
    
  }
  
  

}
