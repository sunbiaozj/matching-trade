import {Component, Injectable} from '@angular/core';
import {Headers, Http} from '@angular/http';
// TODO: Move HTTP related imports from MatchingTradeApp to HttpService class, need to inject HTTP_PROVIDERS
//import { HTTP_PROVIDERS } from '@angular/http';
//import 'rxjs/add/operator/toPromise';

import { MessengerService } from '../messenger/messenger.service';

@Injectable()
export class HttpService {
    private serviceUrl: string = '/services/mt';
    private requestHeaders = new Headers({ 'Content-Type': 'application/json' });

    constructor(private http: Http, private messengerService: MessengerService) { }

    public get(url: string, basic?:boolean): Promise<any> {
        let targetUrl: string;
        if (basic) {
            targetUrl = url;
        } else {
            targetUrl = `${this.serviceUrl}/${url}`;
        }

        return this.http.get(targetUrl)
            .toPromise()
            .then(response => response)
            .catch(error => this.messengerService.setError(error));
    }

    public post(url: string, data: any): Promise<any> {
        return this.http
            .post(`${this.serviceUrl}/${url}`, JSON.stringify(data), { headers: this.requestHeaders })
            .toPromise()
            .then(response => response)
            .catch(error => this.messengerService.setError(error));
    }

    public put(url: string, data: any): Promise<any> {
        return this.http
            .put(`${this.serviceUrl}/${url}`, JSON.stringify(data), { headers: this.requestHeaders })
            .toPromise()
            .then(response => response)
            .catch(error => this.messengerService.setError(error));
    }

}
