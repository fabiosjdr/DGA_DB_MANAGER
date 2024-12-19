import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-kanban-column-custom-header',
  standalone: true,
  imports: [],
  templateUrl: './kanban-column-custom-header.component.html',
  styleUrl: './kanban-column-custom-header.component.scss'
})
export class KanbanColumnCustomHeaderComponent {
  @Input() data: any; // Dados da coluna recebidos pelo componente

  teste(){
    alert('teste');
  }
}
