import { provideRouter, RouterConfig }  from '@angular/router';
import {TradeItemListComponent} from './trade-item/trade-item-list.component';
import {TradeItemEditorComponent} from './trade-item/trade-item-editor.component';

const routes: RouterConfig = [
  {
    path: '',
    redirectTo: 'trade-item/trade-item-list',
    pathMatch: 'full'
  }, {
    path: 'trade-item/trade-item-list',
    component: TradeItemListComponent
  }, {
    path: 'trade-item/trade-item-editor',
    component: TradeItemEditorComponent
  }
];

export const appRouterProviders = [
  provideRouter(routes)
];
