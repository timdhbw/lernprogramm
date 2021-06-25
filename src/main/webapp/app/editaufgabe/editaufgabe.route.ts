import { Route } from '@angular/router';

import {EditaufgabeComponent} from "./editaufgabe.component";
import {Authority} from "../shared/constants/authority.constants";

export const EDITAUFGABE_ROUTE: Route = {
  path: 'editaufgabe',
  component: EditaufgabeComponent,
  data: {
    authorities: [Authority.ADMIN, Authority.USER],
    pageTitle: 'home.title',
  },
};
