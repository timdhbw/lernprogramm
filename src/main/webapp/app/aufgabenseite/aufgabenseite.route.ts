import { Route } from '@angular/router';

import {AufgabenseiteComponent} from "./aufgabenseite.component";
import {Authority} from "app/shared/constants/authority.constants";

export const AUFGABENSEITE_ROUTE: Route = {
  path: 'aufgabenseite',
  component: AufgabenseiteComponent,
  data: {
    authorities: [Authority.ADMIN, Authority.USER],
    pageTitle: 'home.title',
  },
};
