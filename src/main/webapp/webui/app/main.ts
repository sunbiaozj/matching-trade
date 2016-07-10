import { bootstrap }      from '@angular/platform-browser-dynamic';
import { HTTP_PROVIDERS } from '@angular/http';

import { MatchingTradeAppComponent } from './matching-trade-app.component';

bootstrap(MatchingTradeAppComponent, [
  HTTP_PROVIDERS
]);