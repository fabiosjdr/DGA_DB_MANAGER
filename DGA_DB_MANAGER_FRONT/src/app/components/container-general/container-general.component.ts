import { Component } from '@angular/core';
import { SidebarLayoutComponent } from '../sidebar-layout/sidebar-layout.component';

@Component({
  selector: 'app-container-general',
  standalone: true,
  imports: [SidebarLayoutComponent],
  templateUrl: './container-general.component.html',
  styleUrl: './container-general.component.scss'
})
export class ContainerGeneralComponent {

}
