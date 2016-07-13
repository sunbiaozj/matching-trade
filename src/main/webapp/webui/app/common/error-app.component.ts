import { Component, OnInit } from '@angular/core';

import { ErrorAppService } from './error-app-service';

@Component({
    selector: 'error-app',
    templateUrl: 'app/common/error-app.html'
})
export class ErrorAppComponent implements OnInit {

    errors: string[] = [];

    constructor(private errorAppService: ErrorAppService) { };

    ngOnInit() {
        this.errorAppService.getErrorObservable().subscribe((error: string) => {
            this.addError(error);
        });
    }

    public addError(s: string): void {
        this.errors.push(s);
    }

    public clearErrors(): void {
        this.errors = [];
    }

}