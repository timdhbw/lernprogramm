import { Component, OnInit } from '@angular/core';
import {FrontendService} from "target/api/frontend.service";
import {AufgabeUiDto} from "target/model/aufgabe";
import {AufgabenteilUiDto} from "target/model/aufgabenteil";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'jhi-aufgabenseite',
  templateUrl: './aufgabenseite.component.html',
  styleUrls: ['./aufgabenseite.component.scss']
})
export class AufgabenseiteComponent implements OnInit {

  aufgabeId: number;

  aufgabe: AufgabeUiDto | undefined;

  queryParams: string | undefined;

  constructor(private frontendService: FrontendService, private route: ActivatedRoute) {
    this.aufgabeId = 1;
  }

  ngOnInit(): void {
    this.queryParams = this.route.snapshot.queryParams.aufgabeId + ' # ' + JSON.stringify(this.route);
  }

  getAufgabe(): void {
    this.frontendService.getAufgabeById(this.aufgabeId).toPromise()
      .then(aufgabe => this.aufgabe = aufgabe).catch(this.aufgabe = undefined);
  }

  getAufgabeteilListSorted(): Array<AufgabenteilUiDto> | undefined  {
    this.aufgabe?.aufgabenteilList?.sort((a, b) => {
      if (a !== undefined && b !== undefined) {
        // eslint-disable-next-line @typescript-eslint/ban-ts-ignore
       // @ts-ignore
        return (a.laufenNr > b.laufenNr) ? 1 : -1;
      }
      return 0;
    });
    return this.aufgabe?.aufgabenteilList;
  }

  getAufgabeTagList(): string | undefined {
    return this.aufgabe?.aufgabentagList?.map(tag => tag.tag).join(", ");
  }

}
