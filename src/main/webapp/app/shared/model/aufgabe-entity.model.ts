import { IAufgabenteilEntity } from 'app/shared/model/aufgabenteil-entity.model';
import { IAufgabenbwtunghistEntity } from 'app/shared/model/aufgabenbwtunghist-entity.model';
import { IAufgabenhistorieEntity } from 'app/shared/model/aufgabenhistorie-entity.model';
import { IAufgabentagEntity } from 'app/shared/model/aufgabentag-entity.model';
import { IProfilEntity } from 'app/shared/model/profil-entity.model';
import { KategorieEnum } from 'app/shared/model/enumerations/kategorie-enum.model';

export interface IAufgabeEntity {
  id?: number;
  aufgabentitel?: string;
  kategorie?: KategorieEnum;
  aufgabenteilEntities?: IAufgabenteilEntity[];
  aufgabenbwtunghistEntities?: IAufgabenbwtunghistEntity[];
  aufgabenhistorieEntities?: IAufgabenhistorieEntity[];
  aufgabentags?: IAufgabentagEntity[];
  autor?: IProfilEntity;
}

export class AufgabeEntity implements IAufgabeEntity {
  constructor(
    public id?: number,
    public aufgabentitel?: string,
    public kategorie?: KategorieEnum,
    public aufgabenteilEntities?: IAufgabenteilEntity[],
    public aufgabenbwtunghistEntities?: IAufgabenbwtunghistEntity[],
    public aufgabenhistorieEntities?: IAufgabenhistorieEntity[],
    public aufgabentags?: IAufgabentagEntity[],
    public autor?: IProfilEntity
  ) {}
}
