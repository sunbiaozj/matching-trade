import { provideRouter, RouterConfig }  from '@angular/router';
import {TradeItemListComponent} from './trade-item/trade-item-list.component';
import {TradeItemEditorComponent} from './trade-item/trade-item-editor.component';

export const ROUTE_URLS = {
  TRADE_ITEM_LIST: "trade-item/trade-item-list",
  TRADE_ITEM_EDITOR: "trade-item/trade-item-editor/:tradeItemId"
}; 

const routes: RouterConfig = [
  {
    path: '',
    redirectTo: ROUTE_URLS.TRADE_ITEM_LIST,
    pathMatch: 'full'
  }, {
    path: ROUTE_URLS.TRADE_ITEM_LIST,
    component: TradeItemListComponent
  }, {
    path: ROUTE_URLS.TRADE_ITEM_EDITOR,
    component: TradeItemEditorComponent
  }
];

export const appRouterProviders = [
  provideRouter(routes)
];

