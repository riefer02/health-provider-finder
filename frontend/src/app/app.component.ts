import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatToolbarModule } from '@angular/material/toolbar';
import { PatientFormComponent } from './components/patient-form/patient-form.component';
import { ProviderListComponent } from './components/provider-list/provider-list.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule,
    MatToolbarModule,
    PatientFormComponent,
    ProviderListComponent,
  ],
  template: `
    <mat-toolbar color="primary">
      <span>Anise Health Provider Matching</span>
    </mat-toolbar>

    <main>
      <app-patient-form></app-patient-form>
      <app-provider-list></app-provider-list>
    </main>
  `,
  styles: [
    `
      main {
        padding: 2rem;
        max-width: 1200px;
        margin: 0 auto;
      }

      mat-toolbar {
        margin-bottom: 2rem;
      }
    `,
  ],
})
export class AppComponent {}
