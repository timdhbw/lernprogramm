import {Component, OnInit} from '@angular/core';
import {FrontendService} from "target/api/frontend.service";
import {AufgabeUiDto} from "target/model/aufgabe";
import {ActivatedRoute, Router} from "@angular/router";
import {AufgabenabschlussModalService} from "app/aufgabenseite/aufgabeabschluss-modal/aufgabenabschluss-modal.service";

@Component({
  selector: 'jhi-aufgabenseite',
  templateUrl: './aufgabenseite.component.html',
  styleUrls: ['./aufgabenseite.component.scss']
})
export class AufgabenseiteComponent implements OnInit {

  aufgabeId: number;

  aufgabe: AufgabeUiDto | undefined;

  queryParams: string | undefined;

  constructor(private frontendService: FrontendService, private route: ActivatedRoute, private aufgabenabschlussModalService: AufgabenabschlussModalService, private router: Router) {
    this.aufgabeId = 0;
  }

  ngOnInit(): void {
    this.aufgabeId = this.route.snapshot.queryParams.aufgabeId;
    this.getAufgabe();
  }

  getAufgabe(): void {
    this.frontendService.getAufgabeById(this.aufgabeId).toPromise()
      .then(aufgabe => this.aufgabe = aufgabe).catch(this.aufgabe = undefined);
  }

  schliesseAufgabe(): void {
    if (this.aufgabe) {
      this.frontendService.aufgabenAbschluss(this.aufgabe).toPromise()
        .then(res => {
          if (res.ergenisUser && this.aufgabe) {
            this.router.navigate(['/profilseite']);
            this.aufgabenabschlussModalService.open(this.aufgabe, res.ergenisUser);
          }
        })
    }
  }

}
