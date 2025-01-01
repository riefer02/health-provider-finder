import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatChipsModule } from '@angular/material/chips';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatDividerModule } from '@angular/material/divider';
import { Provider } from '../../models/provider.model';
import { ProviderStateService } from '../../services/provider-state.service';

@Component({
  selector: 'app-provider-list',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatChipsModule,
    MatIconModule,
    MatProgressSpinnerModule,
    MatDividerModule,
  ],
  template: `
    @if (loading) {
    <div class="loading-container">
      <mat-spinner></mat-spinner>
      <p>Finding the best matches for you...</p>
    </div>
    } @else if (error) {
    <div class="error-container">
      <p>{{ error }}</p>
    </div>
    } @else if (providers.length === 0) {
    <div class="provider-list">
      <p class="no-results">
        No matching providers found. Please try adjusting your preferences.
      </p>
    </div>
    } @else {
    <div class="provider-list">
      @for (provider of providers; track provider.firstName + provider.lastName)
      {
      <mat-card class="provider-card">
        <mat-card-header>
          <mat-card-title>
            <h2>{{ provider.firstName }} {{ provider.lastName }}</h2>
          </mat-card-title>
          <mat-card-subtitle>
            <div class="provider-info">
              <span
                ><mat-icon>person</mat-icon> {{ provider.ethnicIdentity }} |
                {{ provider.genderIdentity }}</span
              >
              <span><mat-icon>place</mat-icon> {{ provider.location }}</span>
              <span
                ><mat-icon>group</mat-icon> Available for
                {{ provider.availableCapacity }} new clients</span
              >
            </div>
          </mat-card-subtitle>
        </mat-card-header>

        <mat-card-content>
          <div class="section">
            <h3><mat-icon>description</mat-icon> About</h3>
            <p class="bio">{{ provider.bio }}</p>
          </div>

          <mat-divider></mat-divider>

          <div class="section">
            <h3><mat-icon>psychology</mat-icon> Treatment Approaches</h3>
            <div class="chip-grid">
              @for (modality of cleanModalities(provider.treatmentModalities ||
              []); track modality) {
              <mat-chip highlighted>{{ formatModality(modality) }}</mat-chip>
              }
            </div>
          </div>

          <div class="section">
            <h3><mat-icon>medical_information</mat-icon> Areas of Focus</h3>
            <div class="chip-grid">
              @for (area of cleanSpecializations(provider.specializationAreas ||
              []); track area) {
              <mat-chip>{{ formatSpecialization(area) }}</mat-chip>
              }
            </div>
          </div>

          <mat-divider></mat-divider>

          <div class="additional-info">
            <div class="info-item">
              <mat-icon>translate</mat-icon>
              <div>
                <strong>Languages:</strong>
                <p>{{ formatLanguages(provider.languages || []) }}</p>
              </div>
            </div>

            <div class="info-item">
              <mat-icon>favorite</mat-icon>
              <div>
                <strong>Background:</strong>
                <p>{{ provider.religiousBackground || 'Not specified' }}</p>
              </div>
            </div>
          </div>
        </mat-card-content>
      </mat-card>
      }
    </div>
    }
  `,
  styles: [
    `
      .provider-list {
        display: flex;
        flex-direction: column;
        gap: 1.5rem;
        padding: 1rem;
        max-width: 800px;
        margin: 0 auto;
      }

      .provider-card {
        margin-bottom: 1rem;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
      }

      mat-card-title h2 {
        margin: 0;
        font-size: 1.5rem;
        color: #2c3e50;
      }

      .provider-info {
        display: flex;
        flex-wrap: wrap;
        gap: 1rem;
        margin-top: 0.5rem;
        color: #666;

        span {
          display: flex;
          align-items: center;
          gap: 0.25rem;

          mat-icon {
            font-size: 1rem;
            height: 1rem;
            width: 1rem;
            color: #666;
          }
        }
      }

      .section {
        margin: 1.5rem 0;

        h3 {
          margin-bottom: 1rem;
          color: #2c3e50;
          display: flex;
          align-items: center;
          gap: 0.5rem;
          font-size: 1.1rem;

          mat-icon {
            color: #666;
          }
        }
      }

      .bio {
        margin: 0;
        line-height: 1.6;
        color: #2c3e50;
      }

      .chip-grid {
        display: flex;
        flex-wrap: wrap;
        gap: 0.5rem;
      }

      mat-chip {
        font-size: 0.9rem;
      }

      .additional-info {
        margin-top: 1.5rem;
        display: grid;
        grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
        gap: 1rem;
      }

      .info-item {
        display: flex;
        gap: 0.5rem;
        align-items: flex-start;

        mat-icon {
          color: #666;
        }

        div {
          flex: 1;

          strong {
            color: #2c3e50;
            font-size: 0.9rem;
          }

          p {
            margin: 0.25rem 0 0;
            color: #666;
          }
        }
      }

      mat-divider {
        margin: 1.5rem 0;
      }

      .no-results {
        text-align: center;
        color: #666;
        padding: 2rem;
        background: #f5f5f5;
        border-radius: 4px;
      }

      .loading-container {
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        padding: 2rem;
        text-align: center;
        color: #666;
      }

      .error-container {
        text-align: center;
        color: #f44336;
        padding: 2rem;
        background: #ffebee;
        border-radius: 4px;
        margin: 1rem;
      }
    `,
  ],
})
export class ProviderListComponent implements OnInit {
  providers: Provider[] = [];
  loading = false;
  error: string | null = null;

  constructor(private stateService: ProviderStateService) {}

  ngOnInit() {
    this.stateService.providers$.subscribe(
      (providers) => (this.providers = providers)
    );
    this.stateService.loading$.subscribe((loading) => (this.loading = loading));
    this.stateService.error$.subscribe((error) => (this.error = error));
  }

  cleanModalities(modalities: string[]): string[] {
    if (!modalities) return [];
    return modalities
      .map((modality) =>
        modality
          .trim()
          .replace(/\([^)]*\)/g, '')
          .replace(/\s+/g, ' ')
          .trim()
      )
      .filter(
        (modality) => modality.length > 0 && !modality.match(/^[A-Za-z]{1,2}$/)
      );
  }

  cleanSpecializations(specializations: string[]): string[] {
    if (!specializations) return [];
    return specializations
      .map((area) =>
        area
          .trim()
          .replace(/\([^)]*\)/g, '')
          .replace(/\s+/g, ' ')
          .replace(/and\/or/g, 'and')
          .trim()
      )
      .filter((area) => area.length > 0 && !area.match(/^[A-Za-z]{1,2}$/));
  }

  formatModality(modality: string): string {
    const commonAbbreviations: { [key: string]: string } = {
      CBT: 'Cognitive Behavioral Therapy',
      DBT: 'Dialectical Behavioral Therapy',
      ACT: 'Acceptance & Commitment Therapy',
      MBCT: 'Mindfulness-Based Cognitive Therapy',
      EMDR: 'Eye Movement Desensitization & Reprocessing',
      MI: 'Motivational Interviewing',
      PE: 'Prolonged Exposure Therapy',
    };

    for (const [abbr, full] of Object.entries(commonAbbreviations)) {
      if (modality.toUpperCase() === abbr) {
        return full;
      }
    }

    for (const [abbr, full] of Object.entries(commonAbbreviations)) {
      if (modality.includes(abbr)) {
        return full;
      }
    }

    return modality
      .replace(/\(.*?\)/g, '')
      .replace(/therapy/gi, 'Therapy')
      .trim();
  }

  formatSpecialization(area: string): string {
    return area
      .replace(/related issues/gi, '')
      .replace(/difficulties/gi, '')
      .replace(/problems/gi, '')
      .replace(/concerns/gi, '')
      .replace(/and\/or/g, 'and')
      .replace(/\band\b/g, '&')
      .replace(/\s+/g, ' ')
      .trim()
      .split(' ')
      .map((word) => word.charAt(0).toUpperCase() + word.slice(1).toLowerCase())
      .join(' ');
  }

  formatLanguages(languages: string[]): string {
    if (!languages || languages.length === 0) return 'Not specified';
    return languages
      .map((lang) => lang.trim())
      .filter((lang) => lang.length > 0)
      .map((lang) => lang.charAt(0).toUpperCase() + lang.slice(1).toLowerCase())
      .join(', ');
  }
}
