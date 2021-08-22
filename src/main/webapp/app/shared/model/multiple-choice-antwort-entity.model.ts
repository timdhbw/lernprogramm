import { IAufgabenteilEntity } from 'app/shared/model/aufgabenteil-entity.model';

export interface IMultipleChoiceAntwortEntity {
  id?: number;
  checked?: boolean;
  checkedRichtig?: boolean;
  label?: string;
  aufgabenteil?: IAufgabenteilEntity;
}

export class MultipleChoiceAntwortEntity implements IMultipleChoiceAntwortEntity {
  constructor(
    public id?: number,
    public checked?: boolean,
    public checkedRichtig?: boolean,
    public label?: string,
    public aufgabenteil?: IAufgabenteilEntity
  ) {
    this.checked = this.checked || false;
    this.checkedRichtig = this.checkedRichtig || false;
  }
}
