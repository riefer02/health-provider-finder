import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Provider } from '../models/provider.model';
import { PatientRequest } from '../models/patient-request.model';

@Injectable({
  providedIn: 'root',
})
export class ProviderService {
  private apiUrl = '/api/providers';

  constructor(private http: HttpClient) {}

  findMatches(request: PatientRequest): Observable<Provider[]> {
    return this.http.post<Provider[]>(`${this.apiUrl}/match`, request);
  }
}
