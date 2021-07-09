import { NgModule } from '@angular/core';
import { LernprogrammSharedLibsModule } from './shared-libs.module';
import { FindLanguageFromKeyPipe } from './language/find-language-from-key.pipe';
import { AlertComponent } from './alert/alert.component';
import { AlertErrorComponent } from './alert/alert-error.component';
import { LoginModalComponent } from './login-window/login.component';
import { HasAnyAuthorityDirective } from './auth/has-any-authority.directive';
import { LoginComponent } from './login/login.component';
import {EnumUtil} from "app/shared/util/enum-util";

@NgModule({
  imports: [LernprogrammSharedLibsModule],
  declarations: [FindLanguageFromKeyPipe, AlertComponent, AlertErrorComponent, LoginModalComponent, HasAnyAuthorityDirective, LoginComponent],
  entryComponents: [LoginModalComponent],
  exports: [
    LernprogrammSharedLibsModule,
    FindLanguageFromKeyPipe,
    AlertComponent,
    AlertErrorComponent,
    LoginModalComponent,
    HasAnyAuthorityDirective,
    LoginComponent,
  ],
  providers: [EnumUtil]
})
export class LernprogrammSharedModule {}
