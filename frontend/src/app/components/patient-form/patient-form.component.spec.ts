import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatInputModule } from '@angular/material/input';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { PatientFormComponent } from './patient-form.component';
import { ProviderService } from '../../services/provider.service';
import { ProviderStateService } from '../../services/provider-state.service';
import { of, throwError } from 'rxjs';
import { PatientRequest } from '../../models/patient-request.model';
import { Validators } from '@angular/forms';

describe('PatientFormComponent', () => {
  let component: PatientFormComponent;
  let fixture: ComponentFixture<PatientFormComponent>;
  let providerService: jasmine.SpyObj<ProviderService>;
  let providerStateService: jasmine.SpyObj<ProviderStateService>;

  beforeEach(async () => {
    const providerServiceSpy = jasmine.createSpyObj('ProviderService', [
      'findMatches',
    ]);
    providerServiceSpy.findMatches.and.returnValue(of([]));

    const providerStateServiceSpy = jasmine.createSpyObj(
      'ProviderStateService',
      ['setLoading', 'setProviders', 'setError']
    );

    await TestBed.configureTestingModule({
      imports: [
        ReactiveFormsModule,
        MatFormFieldModule,
        MatSelectModule,
        MatInputModule,
        NoopAnimationsModule,
        PatientFormComponent,
      ],
      providers: [
        { provide: ProviderService, useValue: providerServiceSpy },
        { provide: ProviderStateService, useValue: providerStateServiceSpy },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(PatientFormComponent);
    component = fixture.componentInstance;
    providerService = TestBed.inject(
      ProviderService
    ) as jasmine.SpyObj<ProviderService>;
    providerStateService = TestBed.inject(
      ProviderStateService
    ) as jasmine.SpyObj<ProviderStateService>;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize with empty form values', () => {
    expect(component.patientForm.get('areasOfConcern')?.value).toEqual([]);
    expect(component.patientForm.get('treatmentModality')?.value).toEqual([]);
    expect(component.patientForm.get('demographics.ethnicity')?.value).toBe('');
    expect(component.patientForm.get('demographics.gender')?.value).toBe('');
    expect(component.patientForm.get('demographics.religion')?.value).toBe('');
    expect(component.patientForm.get('demographics.maritalStatus')?.value).toBe(
      ''
    );
    expect(component.patientForm.get('location')?.value).toBe('');
    expect(component.patientForm.get('paymentMethod')?.value).toBe('');
    expect(component.patientForm.get('insuranceProvider')?.value).toBe('');
  });

  it('should handle payment method changes', () => {
    const insuranceControl = component.patientForm.get('insuranceProvider');

    // Initially insurance provider should be empty with no required validator
    expect(insuranceControl?.value).toBe('');
    expect(insuranceControl?.hasValidator(Validators.required)).toBeFalsy();

    // Set payment method to Insurance
    component.patientForm.get('paymentMethod')?.setValue('Insurance');
    expect(insuranceControl?.hasValidator(Validators.required)).toBeTruthy();

    // Set payment method back to Self-pay
    component.patientForm.get('paymentMethod')?.setValue('Self-pay');
    expect(insuranceControl?.value).toBe('');
    expect(insuranceControl?.hasValidator(Validators.required)).toBeFalsy();
  });

  it('should submit form when valid', () => {
    const mockRequest: PatientRequest = {
      areasOfConcern: ['anxiety', 'depression'],
      treatmentModality: ['cbt', 'mindfulness'],
      demographics: {
        ethnicity: 'East Asian',
        gender: 'Female',
        religion: 'Buddhist',
        maritalStatus: 'Single',
      },
      therapistPreferences: {
        preferredGender: 'Any',
        preferredEthnicity: 'Any',
        preferredReligion: 'Any',
        preferredLanguage: 'Any',
      },
      location: 'CA',
      paymentMethod: 'Self-pay',
    };

    component.patientForm.patchValue(mockRequest);
    component.onSubmit();

    expect(providerStateService.setLoading).toHaveBeenCalledWith(true);
    expect(providerService.findMatches).toHaveBeenCalledWith(mockRequest);
    expect(providerStateService.setProviders).toHaveBeenCalledWith([]);
    expect(providerStateService.setLoading).toHaveBeenCalledWith(false);
  });

  it('should handle error when provider service fails', () => {
    providerService.findMatches.and.returnValue(
      throwError(() => new Error('Failed to find matches'))
    );

    const mockRequest: PatientRequest = {
      areasOfConcern: ['anxiety'],
      treatmentModality: ['cbt'],
      demographics: {
        ethnicity: 'East Asian',
        gender: 'Female',
        religion: 'Buddhist',
        maritalStatus: 'Single',
      },
      therapistPreferences: {
        preferredGender: 'Any',
        preferredEthnicity: 'Any',
        preferredReligion: 'Any',
        preferredLanguage: 'Any',
      },
      location: 'CA',
      paymentMethod: 'Self-pay',
    };

    component.patientForm.patchValue(mockRequest);
    component.onSubmit();

    expect(providerStateService.setError).toHaveBeenCalledWith(
      'Failed to find matches. Please try again.'
    );
    expect(providerStateService.setLoading).toHaveBeenCalledWith(false);
    expect(component.error).toBe('Failed to find matches. Please try again.');
  });
});
