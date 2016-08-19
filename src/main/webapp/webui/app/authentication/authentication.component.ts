import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {ROUTE_URLS} from '../app.routes';

import { ErrorAppService } from '../common/error-app-service';

import {AuthenticationService} from './authentication.service';

@Component({
  selector: 'authentication',
  templateUrl: 'app/authentication/authentication.html',
  providers: [AuthenticationService]
})
export class AuthenticationComponent implements OnInit {
  private authenticatedEmail: string;
  
  constructor(private router: Router, private errorAppService: ErrorAppService, private authenticationService: AuthenticationService) { }
  
  ngOnInit() {
    let authenticationPromise = this.authenticationService.get();
    authenticationPromise.then(authentication => this.authenticatedEmail = authentication.email);
  }

  private signIn():void {
    let href:string = window.location.href;
    let webuiIndex:number = href.indexOf("webui");
    let baseURL = href.substring(0, webuiIndex);
    let authenticationURL = baseURL + "authenticate";
    window.location.assign(authenticationURL);
  }

}
