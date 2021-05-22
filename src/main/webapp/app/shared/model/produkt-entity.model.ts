export interface IProduktEntity {
  id?: number;
  name?: string;
  bewertung?: number;
}

export class ProduktEntity implements IProduktEntity {
  constructor(public id?: number, public name?: string, public bewertung?: number) {}
}
