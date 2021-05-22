import {Route} from '@angular/router';

import {FaqComponent} from "./faq.component";
import {Authority} from "app/shared/constants/authority.constants";

export const FAQ_ROUTE: Route = {
  path: 'faq',
  component: FaqComponent,
  data: {
    authorities: [Authority.ADMIN, Authority.USER],
    pageTitle: 'faq.title',
  },
};
