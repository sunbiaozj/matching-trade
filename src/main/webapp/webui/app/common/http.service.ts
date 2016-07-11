import { Component, OnInit } from '@angular/core';
import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';

// TODO: Move HTTP related imports from MatchingTradeApp to HttpService class, need to inject HTTP_PROVIDERS
//import { HTTP_PROVIDERS } from '@angular/http';
//import 'rxjs/add/operator/toPromise';

@Injectable()
export class HttpService {
    serviceUrl: string = '/matching-trade/services/mt';

    constructor(private http: Http) { }

    get(url: string): Promise<any> {
        return this.http.get(`${this.serviceUrl}/${url}`)
            .toPromise()
            .then(response => response)
            .catch(error => error);
    }

    post(url: string, data: any): Promise<any> {
        let headers = new Headers({ 'Content-Type': 'application/json' });
        return this.http
            .post(`${this.serviceUrl}/${url}`, JSON.stringify(data), { headers: headers })
            .toPromise()
            .then(response => response)
            .catch(error => error);
    }

}
