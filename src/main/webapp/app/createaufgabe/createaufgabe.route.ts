import { Route } from '@angular/router';

import {CreateaufgabeComponent} from "./createaufgabe.component";
import {Authority} from "../shared/constants/authority.constants";

export const CREATEAUFGABE_ROUTE: Route = {
  path: 'createaufgabe',
  component: CreateaufgabeComponent,
  data: {
    authorities: [Authority.ADMIN, Authority.USER],
    pageTitle: 'home.title',
  },
};
