import { IAufgabentagEntity } from 'app/shared/model/aufgabentag-entity.model';
import { IProfilEntity } from 'app/shared/model/profil-entity.model';

export interface IBewerteterAufgabentagEntity {
  id?: number;
  bewertung?: number;
  aufgabentag?: IAufgabentagEntity;
  profil?: IProfilEntity;
}

export class BewerteterAufgabentagEntity implements IBewerteterAufgabentagEntity {
  constructor(public id?: number, public bewertung?: number, public aufgabentag?: IAufgabentagEntity, public profil?: IProfilEntity) {}
}
