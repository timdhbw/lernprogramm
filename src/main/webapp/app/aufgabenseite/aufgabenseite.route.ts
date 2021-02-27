import { Route } from '@angular/router';

import {AufgabenseiteComponent} from "./aufgabenseite.component";

export const AUFGABENSEITE_ROUTE: Route = {
  path: 'aufgabenseite',
  component: AufgabenseiteComponent,
  data: {
    authorities: [],
    pageTitle: 'home.title',
  },
};
