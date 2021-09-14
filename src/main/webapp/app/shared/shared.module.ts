import { NgModule } from '@angular/core';
import { LernprogrammSharedLibsModule } from './shared-libs.module';
import { FindLanguageFromKeyPipe } from './language/find-language-from-key.pipe';
import { AlertComponent } from './alert/alert.component';
import { AlertErrorComponent } from './alert/alert-error.component';
import { LoginModalComponent } from './login-window/login.component';
import { HasAnyAuthorityDirective } from './auth/has-any-authority.directive';
import { LoginComponent } from './login/login.component';
import {EnumUtil} from "app/shared/util/enum-util";
import { YoutubeEmbededComponent } from './youtube-embeded/youtube-embeded.component';
import { PlainHtmlFieldComponent } from './plain-html-field/plain-html-field.component';
import {AngularMaterialModule} from "app/shared/angular-material/angular-material.module";

@NgModule({
  imports: [LernprogrammSharedLibsModule, AngularMaterialModule],
  declarations: [FindLanguageFromKeyPipe, AlertComponent, AlertErrorComponent, LoginModalComponent, HasAnyAuthorityDirective, LoginComponent, YoutubeEmbededComponent, PlainHtmlFieldComponent],
  entryComponents: [LoginModalComponent],
  exports: [
    LernprogrammSharedLibsModule,
    FindLanguageFromKeyPipe,
    AlertComponent,
    AlertErrorComponent,
    LoginModalComponent,
    HasAnyAuthorityDirective,
    LoginComponent,
    YoutubeEmbededComponent,
    PlainHtmlFieldComponent,
    AngularMaterialModule
  ],
  providers: [EnumUtil]
})
export class LernprogrammSharedModule {}
