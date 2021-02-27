import {Route} from '@angular/router';

import {Authority} from "../shared/constants/authority.constants";
import {ProfilseiteComponent} from "./profilseite.component";

export const PROFILSEITE_ROUTE: Route = {
  path: 'profilseite',
  component: ProfilseiteComponent,
  data: {
    authorities: [Authority.ADMIN, Authority.USER],
    pageTitle: 'home.title',
  },
};
