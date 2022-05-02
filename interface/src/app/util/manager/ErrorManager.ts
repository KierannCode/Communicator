import { HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormControl } from '@angular/forms';
import { AlertManager } from 'src/app/component/alert/AlertManager';
import { NavbarManager } from 'src/app/component/navbar/NavbarManager';

@Injectable({
  providedIn: 'root'
})
export class ErrorManager {
  constructor(private alertManager: AlertManager) {
  }

  setErrors(errorResponse: HttpErrorResponse, formControl?: { [key: string]: FormControl }): void {
    switch (errorResponse.status) {
      case 0:
        this.alertManager.show({ type: 'danger', message: `Unknown error : ${errorResponse.message}`, timeout: 10000 });
        break;
      case 422:
        if (formControl) {
          for (let field of Object.keys(errorResponse.error)) {
            formControl[field].setErrors({ list: errorResponse.error[field] });
            formControl[field].markAsTouched();
          }
        }
        break;
      default:
        this.alertManager.show({ type: 'danger', message: `Error ${errorResponse.status} (${errorResponse.error.error}) : ${errorResponse.error.message}`, timeout: 10000 });
        break;
    }
  }
}
