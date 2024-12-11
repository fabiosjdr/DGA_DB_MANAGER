import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
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
import { Observable } from 'rxjs';
import { UserService } from '../../services/user.service';
import { Users } from '../../models/users.interface';

@Component({
  selector: 'app-kanban',
  standalone: true,
  imports: [
    ContainerGeneralComponent,
    CommonModule, 
    KanbanModule
  ],
  templateUrl: './kanban.component.html',
  styleUrl: './kanban.component.scss'
})
export class KanbanComponent implements AfterViewInit, OnInit{

  @ViewChild('kanban', { read: KanbanComponent, static: false }) kanban!: KanbanComponent;
  

  id!: string;
 
  addNewColumn             = true;
  allowColumnRemove        = true;
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
  taskActions              = false;
  taskDue                  = false;
  taskComments             = false;
  currentUser              = 0;
  taskProgress             = true;

  messages                 = ptBr
  
  
  columns   : { id: number; label : string; dataField: string}[] = [];
  dataSource: { id: number; status: string; text     : string}[] = [];
  users     : { id: number; name  : string}[] = [];
  // users = [
  //   { id: 0, name: 'Andrew', image: './../../../src/images/people/andrew.png' },
  //   { id: 1, name: 'Anne', image: './../../../src/images/people/anne.png' },
  //   { id: 2, name: 'Janet', image: './../../../src/images/people/janet.png' },
  //   { id: 3, name: 'John', image: './../../../src/images/people/john.png' },
  //   { id: 4, name: 'Laura', image: './../../../src/images/people/laura.png' }
  // ];

  constructor(
    private route  : ActivatedRoute,
    private stage  : StagesService, 
    private user   : UserService, 
    private detail : DetailsService, 
    private toastService: ToastrService
  ) {}

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id') || '';
   /// this.stage.setApiURL(this.stage.getApiURL()+'/'+this.id);
    //this.detail.setApiURL(this.detail.getApiURL()+'/'+this.id);
  }

  ngAfterViewInit(): void {
    this.init();
  }

  init(): void {
      // init code.
      this.loadColumns();
      this.loadUser();
      this.loadKanbanData();
  }

  loadUser(): void{

    this.user.getAll().subscribe({

      next: (res) => {
        //console.log(res);
        this.users = res.map((item: Users) => ({
          id       : item.id,
          name     : item.name
        }));

      },
      error: (err) => {
        console.error('Erro ao tentar obter usuários:', err);
      },
      complete: () => {
        //console.log(this.users)
        //console.log('Operação finalizada.');
      },
    });

  }

  loadColumns(): void {
      
    this.stage.get(this.id).subscribe({
      next: (res) => {

        this.columns = res.map((item: Stage) => ({
          id       : item.id,
          label    : item.name,
          dataField: item.name.replace(' ', '_')
        }));

      },
      error: (err) => {
        console.error('Erro ao tentar obter estágios:', err);
      },
      complete: () => {
        //console.log('Operação finalizada.');
      },
    });

  }

  loadKanbanData() : void{

    this.detail.get(this.id).subscribe({
      next: (res) => {
        console.log(res);
        this.dataSource = res.map((item: Detail) => ({
          id         : Number(item.id), 
          text       : item.title, 
          status     : item.stage.name.replace(' ','_'),
          description: item.description,
          priority   : item.priority,
          color      : item.color,
          userId     : item.user.id,
          startDate  : item.start_date,
          dueDate    : item.due_date
        }));

      },
      error: (err) => {
        console.error('Erro ao tentar obter estágios:', err);
      },
      complete: () => {
        
      },
    });
  
  }

  onKanbanChange(event: any): void {
    
    const { detail } = event;
    console.log(detail);
    
  }

  onColumnAdd(event: any){

    const detail = event.detail.newColumn;
    this.saveColumn(detail);
   
  }
  
  onColumnUpdate(event:any) {
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
    
    this.detail.delete(id).subscribe({
      next: () =>  {
         this.toastService.success("Dados removidos com sucesso!");
      },
      error: () => this.toastService.error("Erro inesperado! Tente novamente mais tarde")
    });

  }

  saveTask(detail:any): void{

    const payload = this.prepareTasKData(detail);
    console.log(payload);
    this.detail.save(payload).subscribe({
      next: () =>  {
         this.toastService.success("Dados salvos com sucesso!");
      },
      error: () => this.toastService.error("Erro inesperado! Tente novamente mais tarde")
    });
  }

  prepareTasKData(detail:any){
   
    const value  = [detail.value];
    const filter = detail.value.status;
    const id     = detail.id;

    const stage  = this.columns.filter(option => {
      return option.dataField.includes(filter) 
    })

    const payload  = value.map((item: any) => ({
                      id         : (id != undefined) ? id : null,
                      id_activity: this.id,
                      title      : item.text,
                      description: item.description,
                      priority   : item.priority,
                      color      : item.color,
                      id_user    : item.userId,
                      start_date : item.startDate,
                      due_date   : item.dueDate,
                      id_stage   : stage[0].id
                    })
                  );

    return payload[0];
  }

}
