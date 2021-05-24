import { IBewerteterAufgabentagEntity } from 'app/shared/model/bewerteter-aufgabentag-entity.model';
import { IAufgabeEntity } from 'app/shared/model/aufgabe-entity.model';

export interface IAufgabentagEntity {
  id?: number;
  tag?: string;
  text?: string;
  bewerteterAufgabentagEntities?: IBewerteterAufgabentagEntity[];
  aufgabes?: IAufgabeEntity[];
}

export class AufgabentagEntity implements IAufgabentagEntity {
  constructor(
    public id?: number,
    public tag?: string,
    public text?: string,
    public bewerteterAufgabentagEntities?: IBewerteterAufgabentagEntity[],
    public aufgabes?: IAufgabeEntity[]
  ) {}
}
