import { Component } from '@angular/core';
import { MAT_DATE_LOCALE } from '@angular/material/core';
import { RouterOutlet } from '@angular/router';
import { registerLocaleData } from '@angular/common';
import localePt from '@angular/common/locales/pt';

// Registra os dados de locale 'pt-BR'
registerLocaleData(localePt);

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  providers: [
    { 
      provide: MAT_DATE_LOCALE, 
      useValue: 'pt-BR' 
    }
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'DGA_DB_MANAGER';
}
