import {Route} from '@angular/router';

import {FaqComponent} from "./faq.component";

export const FAQ_ROUTE: Route = {
  path: 'faq',
  component: FaqComponent,
  data: {
    pageTitle: 'faq.title',
  },
};
