import { Component } from '@angular/core';
import { LoginService } from '../../services/login.service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';

@Component({
  selector: 'app-sidebar-layout',
  standalone: true,
  imports: [],
  providers:[LoginService],
  templateUrl: './sidebar-layout.component.html',
  styleUrl: './sidebar-layout.component.scss'
})
export class SidebarLayoutComponent {

  constructor(private router:Router, private loginService: LoginService, private toastService: ToastrService){

  }

  logout(){
    this.loginService.logout().subscribe({
      next: () => { 
        this.toastService.success("Logout feito com sucesso!")
        this.router.navigate(["login"]);
      },
      error: () => this.toastService.error("Erro inesperado! Tente novamente mais tarde")
    })
  }
}
