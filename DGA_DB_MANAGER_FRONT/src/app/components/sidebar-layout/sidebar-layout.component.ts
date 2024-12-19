import { AfterViewInit, Component, OnInit } from '@angular/core';
import { LoginService } from '../../services/login.service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import { Menu } from '../../models/menu.interface';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-sidebar-layout',
  standalone: true,
  imports: [CommonModule],
  providers:[LoginService],
  templateUrl: './sidebar-layout.component.html',
  styleUrl: './sidebar-layout.component.scss'
})
export class SidebarLayoutComponent implements OnInit,AfterViewInit {
  
  menuList    : Menu[] = [];
  selectedMenu: string | null = sessionStorage.getItem('selectedMenu');

  constructor(private router:Router, private loginService: LoginService, private userService:UserService, private toastService: ToastrService){
    
  }

  ngOnInit(){
    // this.DefaultUserLayoutComponent.edit('me');
    
  }

  ngAfterViewInit(): void {
    
    this.userService.get('me').subscribe({

      next: (res) =>  {

        if(res != null){

          res.roles.permissions.map((value:any) => {
           
            this.menuList.push(value.menu)
          });

        }

      },
      error: (e) =>{ 
        console.log(e);
        this.toastService.error("Erro inesperado! Falha ao obter dados do usuário logado")

      }
    
    })
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

  capitalizeWords(value: string): string {
    if (!value) return '';
    return value
      .toLowerCase()
      .split(' ')
      .map(word => word.charAt(0).toUpperCase() + word.slice(1))
      .join(' ');
  }

  onMenuClick(menuName: string): void {
    this.selectedMenu = menuName;
    sessionStorage.setItem('selectedMenu',menuName);
  }
}
