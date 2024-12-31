import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Provider } from '../models/provider.model';

@Injectable({
  providedIn: 'root',
})
export class ProviderStateService {
  private loadingSubject = new BehaviorSubject<boolean>(false);
  private providersSubject = new BehaviorSubject<Provider[]>([]);
  private errorSubject = new BehaviorSubject<string | null>(null);

  loading$ = this.loadingSubject.asObservable();
  providers$ = this.providersSubject.asObservable();
  error$ = this.errorSubject.asObservable();

  setLoading(loading: boolean) {
    this.loadingSubject.next(loading);
  }

  setProviders(providers: Provider[]) {
    this.providersSubject.next(providers);
    this.errorSubject.next(null);
  }

  setError(error: string) {
    this.errorSubject.next(error);
    this.providersSubject.next([]);
  }

  reset() {
    this.loadingSubject.next(false);
    this.providersSubject.next([]);
    this.errorSubject.next(null);
  }
}
