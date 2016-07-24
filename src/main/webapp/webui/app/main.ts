import { bootstrap } from '@angular/platform-browser-dynamic';

import { provide } from '@angular/core';
import { disableDeprecatedForms, provideForms } from '@angular/forms'; 

import { LocationStrategy, HashLocationStrategy } from '@angular/common';

import { AppComponent } from './app.component';
import { appRouterProviders } from './app.routes';


/*
 * Using HashLocationStrategy
 * See: https://angular.io/docs/ts/latest/guide/router.html#!#browser-url-styles
 */
bootstrap(AppComponent, [
  appRouterProviders,
  { provide: LocationStrategy, useClass: HashLocationStrategy },
  disableDeprecatedForms(), provideForms()
])
.catch(err => console.error(err));