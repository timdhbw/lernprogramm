import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LernprogrammTestModule } from '../../../test.module';
import { ProfilEntityDetailComponent } from 'app/entities/profil-entity/profil-entity-detail.component';
import { ProfilEntity } from 'app/shared/model/profil-entity.model';

describe('Component Tests', () => {
  describe('ProfilEntity Management Detail Component', () => {
    let comp: ProfilEntityDetailComponent;
    let fixture: ComponentFixture<ProfilEntityDetailComponent>;
    const route = ({ data: of({ profilEntity: new ProfilEntity(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LernprogrammTestModule],
        declarations: [ProfilEntityDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ProfilEntityDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProfilEntityDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load profilEntity on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.profilEntity).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
