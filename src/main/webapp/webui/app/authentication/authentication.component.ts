import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {ROUTE_URLS} from '../app.routes';

import { ErrorAppService } from '../common/error-app-service';

@Component({
  selector: 'trade-item-list',
  template: '<div>authenticating...<div>'
})
export class AuthenticationComponent implements OnInit {
  
  constructor(private router: Router, private errorAppService: ErrorAppService) { }

  // private addTradeItem() {
  //   let link = [ROUTE_URLS.TRADE_ITEM_NEW];
  //   this.router.navigate(link);
  // }

  ngOnInit() {
    let href:string = window.location.href;
    let webuiIndex:number = href.indexOf("webui");
    let baseURL = href.substring(0, webuiIndex);
    let authenticationURL = baseURL + "authenticate"; 
    console.log(authenticationURL);
    window.location.assign(authenticationURL);
  }

}
