import { IBewerteterAufgabentagEntity } from 'app/shared/model/bewerteter-aufgabentag-entity.model';
import { IAufgabenhistorieEntity } from 'app/shared/model/aufgabenhistorie-entity.model';
import { IAufgabeEntity } from 'app/shared/model/aufgabe-entity.model';

export interface IProfilEntity {
  id?: number;
  profilId?: string;
  vorname?: string;
  nachname?: string;
  bewerteterAufgabentagEntities?: IBewerteterAufgabentagEntity[];
  aufgabenhistorieEntities?: IAufgabenhistorieEntity[];
  aufgabeEntities?: IAufgabeEntity[];
}

export class ProfilEntity implements IProfilEntity {
  constructor(
    public id?: number,
    public profilId?: string,
    public vorname?: string,
    public nachname?: string,
    public bewerteterAufgabentagEntities?: IBewerteterAufgabentagEntity[],
    public aufgabenhistorieEntities?: IAufgabenhistorieEntity[],
    public aufgabeEntities?: IAufgabeEntity[]
  ) {}
}
