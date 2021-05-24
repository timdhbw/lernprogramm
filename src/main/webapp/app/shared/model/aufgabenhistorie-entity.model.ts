import { Moment } from 'moment';
import { IProfilEntity } from 'app/shared/model/profil-entity.model';
import { IAufgabeEntity } from 'app/shared/model/aufgabe-entity.model';

export interface IAufgabenhistorieEntity {
  id?: number;
  datum?: Moment;
  bewertungsveraenderung?: number;
  profil?: IProfilEntity;
  aufgabe?: IAufgabeEntity;
}

export class AufgabenhistorieEntity implements IAufgabenhistorieEntity {
  constructor(
    public id?: number,
    public datum?: Moment,
    public bewertungsveraenderung?: number,
    public profil?: IProfilEntity,
    public aufgabe?: IAufgabeEntity
  ) {}
}
