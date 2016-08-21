import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {ROUTE_URLS} from '../app.routes';
import {ActivatedRoute} from '@angular/router';


import { ErrorAppService } from '../common/error-app-service';

import {AuthenticationService} from './authentication.service';

@Component({
  selector: 'authentication',
  templateUrl: 'app/authentication/authentication.html',
  providers: [AuthenticationService]
})
export class AuthenticationComponent implements OnInit {
  private authenticatedEmail: string;
  private authenticationStatus: string;

  constructor(
    private router: Router,
    private errorAppService: ErrorAppService,
    private route: ActivatedRoute,
    private authenticationService: AuthenticationService) { }

  ngOnInit() {
    let authenticationPromise = this.authenticationService.get();
    authenticationPromise.then(authentication => this.authenticatedEmail = authentication.email);

    // GET authentication/:status from URL parameter
    this.authenticationStatus = this.route.snapshot.params['status'];

  }

  public authenticate(): void {
    window.location.assign("/authenticate");
  }

}
