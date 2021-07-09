import {Injectable} from "@angular/core";

@Injectable()
export class EnumUtil {

  public getWerteOfEnum(typeEnum: any): any [] {
    const values: any [] = [];
    for (const value in typeEnum) {
      if (value !== undefined) {
        values.push(value);
      }
    }
    return values
  }
}
