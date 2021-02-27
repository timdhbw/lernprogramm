import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'produkt-entity',
        loadChildren: () => import('./produkt-entity/produkt-entity.module').then(m => m.LernprogrammProduktEntityModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class LernprogrammEntityModule {}
