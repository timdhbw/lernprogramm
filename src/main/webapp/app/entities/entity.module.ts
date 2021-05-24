import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'produkt-entity',
        loadChildren: () => import('./produkt-entity/produkt-entity.module').then(m => m.LernprogrammProduktEntityModule),
      },
      {
        path: 'profil-entity',
        loadChildren: () => import('./profil-entity/profil-entity.module').then(m => m.LernprogrammProfilEntityModule),
      },
      {
        path: 'aufgabenhistorie-entity',
        loadChildren: () =>
          import('./aufgabenhistorie-entity/aufgabenhistorie-entity.module').then(m => m.LernprogrammAufgabenhistorieEntityModule),
      },
      {
        path: 'bewerteter-aufgabentag-entity',
        loadChildren: () =>
          import('./bewerteter-aufgabentag-entity/bewerteter-aufgabentag-entity.module').then(
            m => m.LernprogrammBewerteterAufgabentagEntityModule
          ),
      },
      {
        path: 'aufgabe-entity',
        loadChildren: () => import('./aufgabe-entity/aufgabe-entity.module').then(m => m.LernprogrammAufgabeEntityModule),
      },
      {
        path: 'aufgabentag-entity',
        loadChildren: () => import('./aufgabentag-entity/aufgabentag-entity.module').then(m => m.LernprogrammAufgabentagEntityModule),
      },
      {
        path: 'aufgabenteil-entity',
        loadChildren: () => import('./aufgabenteil-entity/aufgabenteil-entity.module').then(m => m.LernprogrammAufgabenteilEntityModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class LernprogrammEntityModule {}
