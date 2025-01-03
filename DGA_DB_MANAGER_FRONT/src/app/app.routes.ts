import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { SignUpComponent } from './pages/signup/signup.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { AuthGuard } from './services/authguard.service';
import { ClientComponent } from './pages/client/client.component';
import { ProjectComponent } from './pages/project/project.component';
import { ActivityComponent } from './pages/activity/activity.component';
import { CategoryComponent } from './pages/category/category.component';
import { StatusComponent } from './pages/status/status.component';
import { KanbanComponent } from './pages/kanban/kanban.component';
import { UsersComponent } from './pages/users/users.component';
import { MeComponent } from './pages/me/me.component';

export const routes: Routes = [
    {
        path: "",
        pathMatch: 'full',
        redirectTo: 'sprint',
    },
    
    {
        path: "login",
        component: LoginComponent
    },
    {
        path: "signup",
        component: SignUpComponent
    },
    {
        path: "dashboard",
        component: DashboardComponent,
        canActivate: [AuthGuard]
    },
    {
        path: "users",
        component: UsersComponent,
        canActivate: [AuthGuard]
    },
    {
        path: "me",
        component: MeComponent,
        canActivate: [AuthGuard]
    },
    {
        path: "client",
        component: ClientComponent,
        canActivate: [AuthGuard]
    },
    {
        path: "project",
        component: ProjectComponent,
        canActivate: [AuthGuard]
    },
    {
        path: "sprint",
        component: ActivityComponent,
        canActivate: [AuthGuard],
        
    },
    {
        path: "kanban/:id",
        component: KanbanComponent,
        canActivate: [AuthGuard],
        
    },
    {
        path: "category",
        component: CategoryComponent,
        canActivate: [AuthGuard]
    },
    {
        path: "status",
        component: StatusComponent,
        canActivate: [AuthGuard]
    }
];
