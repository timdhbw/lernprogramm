import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { LernprogrammSharedModule } from 'app/shared/shared.module';
import { LernprogrammCoreModule } from 'app/core/core.module';
import { LernprogrammAppRoutingModule } from './app-routing.module';
import { LernprogrammHomeModule } from './home/home.module';
import { LernprogrammEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';
import {LernprogrammProfilseiteModule} from "app/profilseite/profilseite.module";
import {LernprogrammAufgabenseiteModule} from "app/aufgabenseite/aufgabenseite.module";
import {ApiModule} from "target/api.module";
import {Configuration} from "target/configuration";
import {AuthServerProvider} from "app/core/auth/auth-jwt.service";
import {SERVER_API_URL} from "app/app.constants";
import {LernprogrammFaqModule} from './faq/faq.module';
import { LernprogrammEditaufgabeModule } from './editaufgabe/editaufgabe.module';

@NgModule({
  imports: [
    ApiModule,
    BrowserModule,
    LernprogrammSharedModule,
    LernprogrammCoreModule,
    LernprogrammHomeModule,
    LernprogrammFaqModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    LernprogrammEntityModule,
    LernprogrammProfilseiteModule,
    LernprogrammAufgabenseiteModule,
    LernprogrammEditaufgabeModule,
    LernprogrammAppRoutingModule,

  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [MainComponent],
  providers: [
    {
      provide: Configuration,
      useFactory: (authService: AuthServerProvider) => new Configuration(
        {
          basePath: SERVER_API_URL + "/api",
          accessToken: authService.getToken()
        }
      ),
      deps: [AuthServerProvider],
      multi: false
    }
  ],
})
export class LernprogrammAppModule {}
