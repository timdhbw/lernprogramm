import { IMultipleChoiceAntwortEntity } from 'app/shared/model/multiple-choice-antwort-entity.model';
import { IAufgabeEntity } from 'app/shared/model/aufgabe-entity.model';
import { AufgabenteiltypEnum } from 'app/shared/model/enumerations/aufgabenteiltyp-enum.model';

export interface IAufgabenteilEntity {
  id?: number;
  laufenNr?: number;
  aufgabenteiltyp?: AufgabenteiltypEnum;
  text?: string;
  multipleChoiceAntwortEntities?: IMultipleChoiceAntwortEntity[];
  aufgabe?: IAufgabeEntity;
}

export class AufgabenteilEntity implements IAufgabenteilEntity {
  constructor(
    public id?: number,
    public laufenNr?: number,
    public aufgabenteiltyp?: AufgabenteiltypEnum,
    public text?: string,
    public multipleChoiceAntwortEntities?: IMultipleChoiceAntwortEntity[],
    public aufgabe?: IAufgabeEntity
  ) {}
}
