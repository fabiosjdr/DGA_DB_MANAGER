import { Component } from '@angular/core';
import { ContainerGeneralComponent } from '../../components/container-general/container-general.component';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    ContainerGeneralComponent,
    
  ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent {

}
