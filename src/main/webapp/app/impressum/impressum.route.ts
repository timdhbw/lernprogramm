import {Route} from '@angular/router';
import {ImpressumComponent} from "./impressum.component";

export const IMPRESSUM_ROUTE: Route = {
  path: 'impressum',
  component: ImpressumComponent,
  data: {
    pageTitle: 'impressum.title',
  },
};
