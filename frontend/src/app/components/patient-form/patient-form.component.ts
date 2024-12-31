import { Component } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  Validators,
  ReactiveFormsModule,
} from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatChipsModule } from '@angular/material/chips';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { CommonModule } from '@angular/common';
import { ProviderService } from '../../services/provider.service';
import { ProviderStateService } from '../../services/provider-state.service';
import {
  PaymentMethod,
  InsuranceProvider,
  PatientRequest,
  Ethnicity,
  Religion,
  Gender,
  UserEthnicity,
  UserGender,
  UserReligion,
  MaritalStatus,
  Language,
} from '../../models/patient-request.model';

@Component({
  selector: 'app-patient-form',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule,
    MatChipsModule,
    MatProgressSpinnerModule,
  ],
  template: `
    <form
      [formGroup]="patientForm"
      (ngSubmit)="onSubmit()"
      class="form-container"
    >
      <h2>Find Your Therapist Match</h2>

      <div class="form-section">
        <h3>Areas of Concern</h3>
        <mat-form-field appearance="outline">
          <mat-label>Select Your Areas of Concern</mat-label>
          <mat-select formControlName="areasOfConcern" multiple>
            <mat-option value="anxiety">Anxiety</mat-option>
            <mat-option value="depression">Depression</mat-option>
            <mat-option value="trauma">Trauma & PTSD</mat-option>
            <mat-option value="stress">Work-related Stress</mat-option>
            <mat-option value="relationships"
              >Relationship Difficulties</mat-option
            >
            <mat-option value="self-esteem">Low Self-esteem</mat-option>
            <mat-option value="identity">Identity Issues</mat-option>
            <mat-option value="cultural">Cultural & Racial Identity</mat-option>
            <mat-option value="lgbtq">LGBTQ+ Related Concerns</mat-option>
            <mat-option value="academic">Academic Stress</mat-option>
            <mat-option value="transitions">Major Life Transitions</mat-option>
            <mat-option value="social">Social Fears</mat-option>
            <mat-option value="interpersonal"
              >Interpersonal Problems</mat-option
            >
            <mat-option value="anger">Anger Management</mat-option>
            <mat-option value="grief">Grief & Bereavement</mat-option>
            <mat-option value="eating">Eating Disorders</mat-option>
            <mat-option value="sleep">Sleep Problems</mat-option>
            <mat-option value="adhd">ADHD</mat-option>
          </mat-select>
          <mat-error
            *ngIf="patientForm.get('areasOfConcern')?.hasError('required')"
          >
            Please select at least one area of concern
          </mat-error>
        </mat-form-field>
      </div>

      <div class="form-section">
        <h3>Treatment Modality</h3>
        <mat-form-field appearance="outline">
          <mat-label>Select Treatment Approaches</mat-label>
          <mat-select formControlName="treatmentModality" multiple>
            <mat-option value="cbt"
              >Cognitive Behavioral Therapy (CBT)</mat-option
            >
            <mat-option value="dbt"
              >Dialectical Behavioral Therapy (DBT)</mat-option
            >
            <mat-option value="act"
              >Acceptance and Commitment Therapy (ACT)</mat-option
            >
            <mat-option value="mbct"
              >Mindfulness-Based Therapy (MBCT)</mat-option
            >
            <mat-option value="emdr">EMDR</mat-option>
            <mat-option value="psychodynamic">Psychodynamic Therapy</mat-option>
            <mat-option value="mi">Motivational Interviewing</mat-option>
            <mat-option value="art">Art Therapy</mat-option>
            <mat-option value="person-centered"
              >Person Centered Therapy</mat-option
            >
            <mat-option value="narrative">Narrative Therapy</mat-option>
            <mat-option value="family">Family Systems Therapy</mat-option>
            <mat-option value="trauma-focused">Trauma Focused CBT</mat-option>
          </mat-select>
        </mat-form-field>
      </div>

      <div formGroupName="demographics" class="form-section">
        <h3>Your Demographics</h3>
        <div class="form-grid">
          <mat-form-field appearance="outline">
            <mat-label>Your Ethnicity</mat-label>
            <mat-select formControlName="ethnicity" required>
              @for (ethnicity of userEthnicityOptions; track ethnicity) {
              <mat-option [value]="ethnicity">{{ ethnicity }}</mat-option>
              }
            </mat-select>
            <mat-error
              *ngIf="
                patientForm.get('demographics.ethnicity')?.hasError('required')
              "
            >
              Ethnicity is required
            </mat-error>
          </mat-form-field>

          <mat-form-field appearance="outline">
            <mat-label>Your Gender</mat-label>
            <mat-select formControlName="gender" required>
              @for (gender of userGenderOptions; track gender) {
              <mat-option [value]="gender">{{ gender }}</mat-option>
              }
            </mat-select>
            <mat-error
              *ngIf="
                patientForm.get('demographics.gender')?.hasError('required')
              "
            >
              Gender is required
            </mat-error>
          </mat-form-field>

          <mat-form-field appearance="outline">
            <mat-label>Your Religion</mat-label>
            <mat-select formControlName="religion" required>
              @for (religion of userReligionOptions; track religion) {
              <mat-option [value]="religion">{{ religion }}</mat-option>
              }
            </mat-select>
            <mat-error
              *ngIf="
                patientForm.get('demographics.religion')?.hasError('required')
              "
            >
              Religion is required
            </mat-error>
          </mat-form-field>

          <mat-form-field appearance="outline">
            <mat-label>Marital Status</mat-label>
            <mat-select formControlName="maritalStatus" required>
              @for (status of maritalStatusOptions; track status) {
              <mat-option [value]="status">{{ status }}</mat-option>
              }
            </mat-select>
            <mat-error
              *ngIf="
                patientForm
                  .get('demographics.maritalStatus')
                  ?.hasError('required')
              "
            >
              Marital status is required
            </mat-error>
          </mat-form-field>
        </div>
      </div>

      <div formGroupName="therapistPreferences" class="form-section">
        <h3>Therapist Preferences</h3>
        <div class="form-grid">
          <mat-form-field appearance="outline">
            <mat-label>Preferred Therapist Gender</mat-label>
            <mat-select formControlName="preferredGender" required>
              @for (gender of genderOptions; track gender) {
              <mat-option [value]="gender">{{ gender }}</mat-option>
              }
            </mat-select>
            <mat-error
              *ngIf="
                patientForm
                  .get('therapistPreferences.preferredGender')
                  ?.hasError('required')
              "
            >
              Please select a gender preference
            </mat-error>
          </mat-form-field>

          <mat-form-field appearance="outline">
            <mat-label>Preferred Therapist Ethnicity</mat-label>
            <mat-select formControlName="preferredEthnicity" required>
              @for (ethnicity of ethnicityOptions; track ethnicity) {
              <mat-option [value]="ethnicity">{{ ethnicity }}</mat-option>
              }
            </mat-select>
            <mat-error
              *ngIf="
                patientForm
                  .get('therapistPreferences.preferredEthnicity')
                  ?.hasError('required')
              "
            >
              Please select an ethnicity preference
            </mat-error>
          </mat-form-field>

          <mat-form-field appearance="outline">
            <mat-label>Preferred Therapist Religion</mat-label>
            <mat-select formControlName="preferredReligion" required>
              @for (religion of religionOptions; track religion) {
              <mat-option [value]="religion">{{ religion }}</mat-option>
              }
            </mat-select>
            <mat-error
              *ngIf="
                patientForm
                  .get('therapistPreferences.preferredReligion')
                  ?.hasError('required')
              "
            >
              Please select a religion preference
            </mat-error>
          </mat-form-field>

          <mat-form-field appearance="outline">
            <mat-label>Preferred Language</mat-label>
            <mat-select formControlName="preferredLanguage" required>
              @for (language of languageOptions; track language) {
              <mat-option [value]="language">{{ language }}</mat-option>
              }
            </mat-select>
            <mat-error
              *ngIf="
                patientForm
                  .get('therapistPreferences.preferredLanguage')
                  ?.hasError('required')
              "
            >
              Please select a language preference
            </mat-error>
          </mat-form-field>
        </div>
      </div>

      <div class="form-section">
        <h3>Location</h3>
        <mat-form-field appearance="outline">
          <mat-label>Select Your Location</mat-label>
          <mat-select formControlName="location">
            <mat-option value="CA">California (CA)</mat-option>
            <mat-option value="NY">New York (NY)</mat-option>
            <mat-option value="FL">Florida (FL)</mat-option>
            <mat-option value="TX">Texas (TX)</mat-option>
            <mat-option value="IL">Illinois (IL)</mat-option>
            <mat-option value="MA">Massachusetts (MA)</mat-option>
            <mat-option value="WA">Washington (WA)</mat-option>
          </mat-select>
          <mat-error *ngIf="patientForm.get('location')?.hasError('required')">
            Location is required
          </mat-error>
        </mat-form-field>
      </div>

      <div class="form-section">
        <h3>Payment Information</h3>
        <div class="payment-fields">
          <mat-form-field appearance="outline">
            <mat-label>Payment Method</mat-label>
            <mat-select formControlName="paymentMethod" required>
              <mat-option value="Insurance">Insurance</mat-option>
              <mat-option value="Self-pay">Self-pay</mat-option>
            </mat-select>
            <mat-error
              *ngIf="
                patientForm.get('paymentMethod')?.hasError('required') &&
                patientForm.get('paymentMethod')?.touched
              "
            >
              Payment method is required
            </mat-error>
          </mat-form-field>

          @if (showInsuranceProviders) {
          <mat-form-field appearance="outline">
            <mat-label>Insurance Provider</mat-label>
            <mat-select formControlName="insuranceProvider" required>
              <mat-option value="Aetna">Aetna</mat-option>
              <mat-option value="Magellan">Magellan</mat-option>
              <mat-option value="Anthem">Anthem</mat-option>
              <mat-option value="Other">Other</mat-option>
            </mat-select>
            <mat-error
              *ngIf="
                patientForm.get('insuranceProvider')?.hasError('required') &&
                patientForm.get('insuranceProvider')?.touched
              "
            >
              Insurance provider is required
            </mat-error>
          </mat-form-field>
          }
        </div>
      </div>

      <div class="form-actions">
        <button
          mat-raised-button
          color="primary"
          type="submit"
          [disabled]="!patientForm.valid || loading"
        >
          @if (loading) {
          <mat-spinner diameter="20"></mat-spinner>
          Finding Matches... } @else { Find Matches }
        </button>
      </div>

      @if (error) {
      <div class="error-message">
        {{ error }}
      </div>
      }
    </form>
  `,
  styles: [
    `
      .form-container {
        max-width: 800px;
        margin: 2rem auto;
        padding: 2rem;
        display: flex;
        flex-direction: column;
        gap: 2rem;
      }

      .form-section {
        background: #f8f9fa;
        padding: 2rem;
        border-radius: 8px;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
      }

      .form-grid {
        display: grid;
        grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
        gap: 1rem;
      }

      .payment-fields {
        display: grid;
        grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
        gap: 1rem;
      }

      h2 {
        color: #2c3e50;
        text-align: center;
        margin-bottom: 1rem;
        font-size: 1.75rem;
        font-weight: 500;
      }

      h3 {
        color: #2c3e50;
        margin-bottom: 1.5rem;
        font-size: 1.25rem;
        font-weight: 500;
      }

      mat-form-field {
        width: 100%;
      }

      .form-actions {
        display: flex;
        justify-content: center;
        margin-top: 1rem;
      }

      button {
        min-width: 200px;
        padding: 1rem 2rem;
        font-size: 1.1rem;
        display: flex;
        gap: 0.5rem;
        align-items: center;
        justify-content: center;
      }

      .error-message {
        color: #f44336;
        text-align: center;
        padding: 1rem;
        background: #ffebee;
        border-radius: 4px;
        margin-top: 1rem;
      }

      mat-select {
        font-size: 1rem;
      }

      ::ng-deep .mat-mdc-select-panel {
        max-height: 300px !important;
      }

      ::ng-deep .mat-mdc-option {
        font-size: 1rem;
        line-height: 1.2;
        padding: 0.75rem 1rem;
      }

      ::ng-deep .mat-mdc-select-value {
        font-size: 1rem;
      }
    `,
  ],
})
export class PatientFormComponent {
  // User demographic options (without 'Any')
  userEthnicityOptions: UserEthnicity[] = [
    'African American',
    'Chinese American',
    'East Asian',
    'Indian',
    'Japanese American',
    'Korean American',
    'Latino/Hispanic',
    'Middle Eastern',
    'Native American',
    'Pacific Islander',
    'South Asian',
    'Southeast Asian',
    'White/Caucasian',
    'Mixed/Multiracial',
    'Prefer Not to Say',
    'Other',
  ];

  userGenderOptions: UserGender[] = [
    'Female',
    'Male',
    'Non-binary',
    'Transgender',
    'Gender Fluid',
    'Prefer Not to Say',
    'Other',
  ];

  userReligionOptions: UserReligion[] = [
    'Agnostic',
    'Atheist',
    'Buddhist',
    'Catholic',
    'Christian',
    'Hindu',
    'Jewish',
    'Muslim',
    'Sikh',
    'Spiritual',
    'None',
    'Prefer Not to Say',
    'Other',
  ];

  // Therapist preference options (with 'Any')
  ethnicityOptions: Ethnicity[] = [
    'Any',
    'African American',
    'Chinese American',
    'East Asian',
    'Indian',
    'Japanese American',
    'Korean American',
    'Latino/Hispanic',
    'Middle Eastern',
    'Native American',
    'Pacific Islander',
    'South Asian',
    'Southeast Asian',
    'White/Caucasian',
    'Mixed/Multiracial',
    'Other',
  ];

  religionOptions: Religion[] = [
    'Any',
    'Agnostic',
    'Atheist',
    'Buddhist',
    'Catholic',
    'Christian',
    'Hindu',
    'Jewish',
    'Muslim',
    'Sikh',
    'Spiritual',
    'None',
    'Other',
  ];

  genderOptions: Gender[] = [
    'Any',
    'Female',
    'Male',
    'Non-binary',
    'Transgender',
    'Gender Fluid',
    'Other',
  ];

  maritalStatusOptions: MaritalStatus[] = [
    'Single',
    'Married',
    'Divorced',
    'Separated',
    'Widowed',
    'Partnered',
    'Prefer Not to Say',
  ];

  languageOptions: Language[] = [
    'Any',
    'English',
    'Spanish',
    'Mandarin',
    'Cantonese',
    'Korean',
    'Vietnamese',
    'Hindi',
    'Urdu',
    'Arabic',
    'French',
    'Japanese',
    'Tagalog',
    'Other',
  ];

  paymentMethods: PaymentMethod[] = ['Insurance', 'Self-pay'];
  insuranceProviders: InsuranceProvider[] = [
    'Aetna',
    'Magellan',
    'Anthem',
    'Other',
  ];

  loading = false;
  error: string | null = null;
  patientForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private providerService: ProviderService,
    private stateService: ProviderStateService
  ) {
    this.patientForm = this.fb.group({
      areasOfConcern: [[], [Validators.required]],
      treatmentModality: [[]],
      demographics: this.fb.group({
        ethnicity: ['', [Validators.required]],
        gender: ['', [Validators.required]],
        religion: ['', [Validators.required]],
        maritalStatus: ['', [Validators.required]],
      }),
      therapistPreferences: this.fb.group({
        preferredGender: ['Any', [Validators.required]],
        preferredEthnicity: ['Any', [Validators.required]],
        preferredReligion: ['Any', [Validators.required]],
        preferredLanguage: ['Any', [Validators.required]],
      }),
      location: ['', [Validators.required]],
      paymentMethod: ['', [Validators.required]],
      insuranceProvider: [''],
    });

    // Subscribe to payment method changes
    this.patientForm.get('paymentMethod')?.valueChanges.subscribe((method) => {
      const insuranceControl = this.patientForm.get('insuranceProvider');
      if (method === 'Insurance') {
        insuranceControl?.setValidators([Validators.required]);
      } else {
        insuranceControl?.clearValidators();
        insuranceControl?.setValue('');
      }
      insuranceControl?.updateValueAndValidity();
    });
  }

  get showInsuranceProviders(): boolean {
    return this.patientForm.get('paymentMethod')?.value === 'Insurance';
  }

  onSubmit() {
    if (this.patientForm.valid) {
      this.loading = true;
      this.error = null;
      this.stateService.setLoading(true);

      const formValue = { ...this.patientForm.value } as PatientRequest;
      if (formValue.paymentMethod === 'Self-pay') {
        delete formValue.insuranceProvider;
      }

      this.providerService.findMatches(formValue).subscribe({
        next: (providers) => {
          this.stateService.setProviders(providers);
          this.stateService.setLoading(false);
          this.loading = false;

          // Add a small delay to ensure the results are rendered before scrolling
          setTimeout(() => {
            const resultsElement = document.querySelector('app-provider-list');
            if (resultsElement) {
              resultsElement.scrollIntoView({
                behavior: 'smooth',
                block: 'start',
              });
            }
          }, 100);
        },
        error: (err) => {
          this.error = 'Failed to find matches. Please try again.';
          this.stateService.setError(this.error);
          this.stateService.setLoading(false);
          this.loading = false;
        },
      });
    }
  }
}
