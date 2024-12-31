export interface PatientRequest {
  areasOfConcern: string[];
  treatmentModality: string[];
  demographics: {
    ethnicity: UserEthnicity;
    gender: UserGender;
    religion: UserReligion;
    maritalStatus: MaritalStatus;
  };
  therapistPreferences: {
    preferredGender: Gender;
    preferredEthnicity: Ethnicity;
    preferredReligion: Religion;
    preferredLanguage: Language;
  };
  location: string;
  paymentMethod: PaymentMethod;
  insuranceProvider?: InsuranceProvider;
}

export type PaymentMethod = 'Insurance' | 'Self-pay';

export type InsuranceProvider = 'Aetna' | 'Magellan' | 'Anthem' | 'Other';

// User Demographics (without 'Any')
export type UserEthnicity =
  | 'African American'
  | 'Chinese American'
  | 'East Asian'
  | 'Indian'
  | 'Japanese American'
  | 'Korean American'
  | 'Latino/Hispanic'
  | 'Middle Eastern'
  | 'Native American'
  | 'Pacific Islander'
  | 'South Asian'
  | 'Southeast Asian'
  | 'White/Caucasian'
  | 'Mixed/Multiracial'
  | 'Prefer Not to Say'
  | 'Other';

export type UserGender =
  | 'Female'
  | 'Male'
  | 'Non-binary'
  | 'Transgender'
  | 'Gender Fluid'
  | 'Prefer Not to Say'
  | 'Other';

export type UserReligion =
  | 'Agnostic'
  | 'Atheist'
  | 'Buddhist'
  | 'Catholic'
  | 'Christian'
  | 'Hindu'
  | 'Jewish'
  | 'Muslim'
  | 'Sikh'
  | 'Spiritual'
  | 'None'
  | 'Prefer Not to Say'
  | 'Other';

// Therapist Preferences (with 'Any')
export type Ethnicity =
  | 'Any'
  | 'African American'
  | 'Chinese American'
  | 'East Asian'
  | 'Indian'
  | 'Japanese American'
  | 'Korean American'
  | 'Latino/Hispanic'
  | 'Middle Eastern'
  | 'Native American'
  | 'Pacific Islander'
  | 'South Asian'
  | 'Southeast Asian'
  | 'White/Caucasian'
  | 'Mixed/Multiracial'
  | 'Other';

export type Religion =
  | 'Any'
  | 'Agnostic'
  | 'Atheist'
  | 'Buddhist'
  | 'Catholic'
  | 'Christian'
  | 'Hindu'
  | 'Jewish'
  | 'Muslim'
  | 'Sikh'
  | 'Spiritual'
  | 'None'
  | 'Other';

export type Gender =
  | 'Any'
  | 'Female'
  | 'Male'
  | 'Non-binary'
  | 'Transgender'
  | 'Gender Fluid'
  | 'Other';

export type MaritalStatus =
  | 'Single'
  | 'Married'
  | 'Divorced'
  | 'Separated'
  | 'Widowed'
  | 'Partnered'
  | 'Prefer Not to Say';

export type Language =
  | 'Any'
  | 'English'
  | 'Spanish'
  | 'Mandarin'
  | 'Cantonese'
  | 'Korean'
  | 'Vietnamese'
  | 'Hindi'
  | 'Urdu'
  | 'Arabic'
  | 'French'
  | 'Japanese'
  | 'Tagalog'
  | 'Other';
