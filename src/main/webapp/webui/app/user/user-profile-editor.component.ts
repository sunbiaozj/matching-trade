import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Location} from '@angular/common';
import {ROUTE_URLS} from '../app.routes';

import {Subject} from 'rxjs/Subject';
import {Observable} from 'rxjs/Observable';

import {AuthenticationService} from '../authentication/authentication.service';

import {FORM_DIRECTIVES, REACTIVE_FORM_DIRECTIVES, FormBuilder, FormGroup, FormControl, Validators} from '@angular/forms';


@Component({
  selector: 'trade-item-list',
//  providers: [TradeItemService],
  directives: [FORM_DIRECTIVES, REACTIVE_FORM_DIRECTIVES],
  templateUrl: 'app/user/user-profile-editor.html'
})
export class UserProfileEditorComponent implements OnInit {
  private userId: number;
  private name: string;
  private email: string;

  private formGroup: FormGroup;
  private nameFormControl: FormControl = new FormControl('', Validators.required);

  constructor(
    private location: Location,
    private route: ActivatedRoute,
    private authenticationService: AuthenticationService,
    private formBuilder: FormBuilder) {    
    this.formGroup = new FormGroup({
        name: this.nameFormControl
    });
  }


  private navigate(s: string): void {
    if (s == "back") {
      this.location.back();
    }
  }

  public ngOnInit() {

    let authenticationPromise = this.authenticationService.getLastAuthentication();
    authenticationPromise.then(authentication => {
      this.userId = authentication.userId;
      this.name = authentication.name;

      this.nameFormControl.updateValue(this.name);
    });
  }

}
