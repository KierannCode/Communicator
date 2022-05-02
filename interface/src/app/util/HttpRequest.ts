import { HttpErrorResponse } from "@angular/common/http";
import { Observable } from "rxjs";
import { ErrorManager } from "./manager/ErrorManager";
import { Progress } from "./Progress";
import { FormControl } from "@angular/forms";
import { AlertManager } from "../component/alert/AlertManager";

export class HttpRequest<T> {
    callback?: (value: T) => void;

    formControl?: {[key: string]: FormControl};

    progress?: Progress;

    successMessageBuilder?: (value: T) => string;

    successMessageTimeOut: number | undefined = 4000;

    constructor(private observable: Observable<T>, private errorManager: ErrorManager, private alertManager: AlertManager) { }

    send(): void {
        let subscription = this.observable.subscribe({
            next: value => {
                if (this.callback) {
                    this.callback(value);
                }
                if (this.successMessageBuilder) {
                    this.alertManager.show({ type: 'success', message: this.successMessageBuilder(value), timeout: this.successMessageTimeOut });
                }
            },
            error: (errorResponse: HttpErrorResponse) => {
                this.errorManager.setErrors(errorResponse, this.formControl);
            }
        });
        if (this.progress) {
            this.progress.value = 0;
            subscription.add(() => {
                if (this.progress) {
                    this.progress.value = 1;
                }
            });
        }
    }
}