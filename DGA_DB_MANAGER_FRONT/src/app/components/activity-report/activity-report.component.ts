import { Component, inject, model, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA, MatDialogActions, MatDialogClose, MatDialogContent, MatDialogRef, MatDialogTitle } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { ReportActivityService } from '../../services/report-activity.service';
import { ReportActivity } from '../../models/report-service.interface';
import { CommonModule } from '@angular/common';


export interface DialogData {
  activity: string
}

@Component({
  selector: 'app-activity-report',
  standalone: true,
  imports: [
    MatFormFieldModule,
    MatButtonModule,
    MatInputModule,
    MatDialogTitle,
    MatDialogContent,
    MatDialogActions,
    MatFormFieldModule,
    FormsModule,
    CommonModule
  ],
  templateUrl: './activity-report.component.html',
  styleUrl: './activity-report.component.scss'
})

export class ActivityReportComponent implements OnInit {

  readonly dialogRef = inject(MatDialogRef<ActivityReportComponent>);
  readonly data      = inject<DialogData>(MAT_DIALOG_DATA);

  columns          : ReportActivity[] = [];
  groupedActivities: { user_name: string; total_duration:number, activities: ReportActivity[] }[] = [];

  constructor( private reportService: ReportActivityService){}
 
  ngOnInit(){
    this.loadReport();
  }

  loadReport(){

    this.reportService.get(this.data.activity).subscribe({
      next: (res) => {

        this.columns = res.map((item: ReportActivity) => ({

          id_activity_detail: item.id_activity_detail,
          id_activity       : item.id_activity,
          id_stage          : item.id_stage,
          title             : item.title,
          stage_name        : item.stage_name,
          id_user           : item.id_user,
          user_name         : item.user_name,
          duration          : item.duration

        }));

      },
      error: (err) => {
        console.error('Erro ao tentar obter estágios:', err);
      },
      complete: () => {
        this.groupedActivities = this.groupByUser(this.columns)
      },
    });
  }

  groupByUser(data: ReportActivity[]): { user_name: string; total_duration: number; activities: ReportActivity[] }[] {
    
    const grouped = data.reduce((acc, curr) => {
      
      if (!acc[curr.user_name]) {
        acc[curr.user_name] = { activities: [], total_duration: 0 };
      }

      acc[curr.user_name].activities.push(curr);
      acc[curr.user_name].total_duration += curr.duration;

      return acc;

    },{} as Record<string, { activities: any[]; total_duration: number }>);

    return Object.keys(grouped).map((key) => ({
      user_name: key,
      total_duration: grouped[key].total_duration,
      activities: grouped[key].activities,
    }));

  }

  formatDuration(duration: number): string {
    const hours = Math.floor(duration / 60); 
    const minutes = duration % 60;
  
    
    return `${hours > 0 ? `${hours} hora${hours > 1 ? 's' : ''}` : ''} ${
      minutes > 0 ? `${minutes} minuto${minutes > 1 ? 's' : ''}` : ''
    }`.trim();
  }

  close(): void {
    this.dialogRef.close();
  }
}
