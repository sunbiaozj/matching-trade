import { Component, OnInit } from '@angular/core';
import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';

@Injectable()
export class HttpService {
    serviceUrl:string = '/matching-trade/services/mt';

    constructor(private http: Http) { }

    get(url:string): Promise<any> {
         return this.http.get(`${this.serviceUrl}/${url}`)
               .toPromise()
               .then(response => response )
               .catch(error => error);
    }
    
}
