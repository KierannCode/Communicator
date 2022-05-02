import { Injectable } from "@angular/core";
import { MatSnackBarRef, MatSnackBar } from "@angular/material/snack-bar";
import { Subscription } from "rxjs";
import { Alert } from "src/app/util/Alert";
import { Stack } from "src/app/util/Stack";
import { AlertComponent } from "./alert.component";

@Injectable({
    providedIn: 'root'
})
export class AlertManager {
    alertQueue = new Stack<Alert>();

    currentSnackBar: MatSnackBarRef<AlertComponent> | null = null;
    currentDismissSubscription: Subscription | null = null;

    constructor(private snackBar: MatSnackBar) {
    }

    show(alert: Alert): void {
        if (this.currentSnackBar != null) {
            this.currentDismissSubscription!.unsubscribe();
            this.currentSnackBar.dismiss();
        }
        this.alertQueue.push(alert);
        this.openTopAlert();
    }

    openTopAlert(): void {
        let alert = this.alertQueue.topValue();
        if (alert != null) {
            this.currentSnackBar = this.snackBar.openFromComponent(AlertComponent,
                { data: alert.message, duration: alert.timeout, panelClass: [`alert-${alert.type}`] });
            this.currentDismissSubscription = this.currentSnackBar.afterDismissed().subscribe(() => {
                this.alertQueue.pop();
                this.openTopAlert();
            });
        }
    }
}