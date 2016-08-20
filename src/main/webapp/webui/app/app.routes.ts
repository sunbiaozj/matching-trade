import {provideRouter, RouterConfig}  from '@angular/router';
import {AuthenticationComponent} from './authentication/authentication.component';
import {TradeItemListComponent} from './trade-item/trade-item-list.component';
import {TradeItemEditorComponent} from './trade-item/trade-item-editor.component';

export const ROUTE_URLS = {
  AUTHENTICATION: "authentication",
  AUTHENTICATION_CALLBACK: "authentication/:status",
  TRADE_ITEM_LIST: "trade-item/trade-item-list",
  TRADE_ITEM_EDIT: "trade-item/trade-item-editor/:tradeItemId",
  TRADE_ITEM_NEW: "trade-item/trade-item-editor"
};

const routes: RouterConfig = [
  {
    path: '',
    redirectTo: ROUTE_URLS.AUTHENTICATION,
    pathMatch: 'full'
  }, {
    path: ROUTE_URLS.AUTHENTICATION,
    component: AuthenticationComponent
  }, {
    path: ROUTE_URLS.AUTHENTICATION_CALLBACK,
    component: AuthenticationComponent
  }, {
    path: ROUTE_URLS.TRADE_ITEM_LIST,
    component: TradeItemListComponent
  }, {
    path: ROUTE_URLS.TRADE_ITEM_EDIT,
    component: TradeItemEditorComponent
  }, {
    path: ROUTE_URLS.TRADE_ITEM_NEW,
    component: TradeItemEditorComponent
  }
];

export const appRouterProviders = [
  provideRouter(routes)
];

