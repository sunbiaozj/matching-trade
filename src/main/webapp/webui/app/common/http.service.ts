import { Component, OnInit } from '@angular/core';
import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';

// TODO: Move HTTP related imports from MatchingTradeApp to HttpService class, need to inject HTTP_PROVIDERS
//import { HTTP_PROVIDERS } from '@angular/http';
//import 'rxjs/add/operator/toPromise';

import { ErrorAppService } from '../common/error-app-service';

@Injectable()
export class HttpService {
    private serviceUrl: string = '/services/mt';
    private requestHeaders = new Headers({ 'Content-Type': 'application/json' });

    constructor(private http: Http, private errorAppService: ErrorAppService) { }

    public get(url: string): Promise<any> {
        return this.http.get(`${this.serviceUrl}/${url}`)
            .toPromise()
            .then(response => response)
            .catch(error => this.errorAppService.addError(error));
    }

    public post(url: string, data: any): Promise<any> {
        return this.http
            .post(`${this.serviceUrl}/${url}`, JSON.stringify(data), { headers: this.requestHeaders })
            .toPromise()
            .then(response => response)
            .catch(error => this.errorAppService.addError(error));
    }

    public put(url: string, data: any): Promise<any> {
        return this.http
            .put(`${this.serviceUrl}/${url}`, JSON.stringify(data), { headers: this.requestHeaders })
            .toPromise()
            .then(response => response)
            .catch(error => this.errorAppService.addError(error));
    }

}
