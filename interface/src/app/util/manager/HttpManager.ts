import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { AlertManager } from 'src/app/component/alert/AlertManager';
import { AppConfig } from '../AppConfig';
import { HttpRequest } from '../HttpRequest';
import { ErrorManager } from './ErrorManager';

@Injectable({
  providedIn: 'root'
})
export class HttpManager {
  authenticationToken = '';

  constructor(private client: HttpClient, private errorManager: ErrorManager, private alertManager: AlertManager) {
    let authenticationToken = localStorage.getItem(AppConfig.AUTHENTICATION_TOKEN_HEADER);
    if (authenticationToken != null) {
      this.authenticationToken = authenticationToken;
    }
  }

  prepare<T>(observable: Observable<any>): Observable<T> {
    return observable.pipe(map(response => {
      let headers = response.headers;
      this.authenticationToken = headers.get(AppConfig.AUTHENTICATION_TOKEN_HEADER);
      localStorage.setItem(AppConfig.AUTHENTICATION_TOKEN_HEADER, this.authenticationToken);
      return response.body;
    }));
  }

  get<T>(url: string): Observable<T> {
    let observable;
    if (this.authenticationToken != null) {
      observable = this.client.get(url, { headers: { [AppConfig.AUTHENTICATION_TOKEN_HEADER]: this.authenticationToken }, observe: 'response' })
    } else {
      observable = this.client.get(url, { observe: 'response' });
    }
    return this.prepare(observable);
  }

  post<T>(url: string, body: any): Observable<T> {
    let observable;
    if (this.authenticationToken != null) {
      observable = this.client.post(url, body, { headers: { [AppConfig.AUTHENTICATION_TOKEN_HEADER]: this.authenticationToken }, observe: 'response' })
    } else {
      observable = this.client.post(url, body, { observe: 'response' });
    }
    return this.prepare(observable);
  }

  patch<T>(url: string, body: any): Observable<T> {
    let observable;
    if (this.authenticationToken != null) {
      observable = this.client.patch(url, body, { headers: { [AppConfig.AUTHENTICATION_TOKEN_HEADER]: this.authenticationToken }, observe: 'response' })
    } else {
      observable = this.client.patch(url, body, { observe: 'response' });
    }
    return this.prepare(observable);
  }

  manage<T>(observable: Observable<T>): HttpRequest<T> {
    return new HttpRequest(observable, this.errorManager, this.alertManager);
  }
}
