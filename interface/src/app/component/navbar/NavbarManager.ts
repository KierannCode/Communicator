import { Injectable } from "@angular/core";
import { UserService } from "src/app/service/user.service";
import { User } from "src/app/model/User";
import { MatMenuTrigger } from "@angular/material/menu";
import { MatDialog, MatDialogRef } from "@angular/material/dialog";
import { SignUpDialogComponent } from "./sign-up-dialog/sign-up-dialog.component";
import { FormControl } from "@angular/forms";
import { AccountDialogComponent } from "./account-dialog/account-dialog.component";

@Injectable({
    providedIn: 'root'
})
export class NavbarManager {
    user: User | null = null;

    logInMenuTrigger!: MatMenuTrigger;
    logInFormControl!: {[key: string]: FormControl};

    signUpDialog!: MatDialogRef<SignUpDialogComponent>;

    accountDialog!: MatDialogRef<AccountDialogComponent>;

    constructor(userService: UserService, private matDialog: MatDialog) {
        let request = userService.getAuthenticatedUser();
        request.callback = user => this.user = user;
        request.send();
    }

    openSignUpDialog() {
        this.signUpDialog = this.matDialog.open(SignUpDialogComponent, {width: '400px'});
    }

    closeSignUpDialog() {
        this.signUpDialog.close();
    }

    openAccountModal() {
        this.accountDialog = this.matDialog.open(AccountDialogComponent, {width: '400px'});
    }

    closeAccountDialog() {
        this.accountDialog.close();
    }
}