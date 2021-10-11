import {Component, Input, OnInit} from '@angular/core';
import {LoginService} from "app/core/login/login.service";
import {Router} from "@angular/router";

@Component({
  selector: 'jhi-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  username: string;
  passwort: string;
  rememberMe: boolean;
  @Input() nextPage: string;

  authenticationError = false;

  constructor(private loginService:LoginService, private router: Router) {
    this.username = '';
    this.passwort = '';
    this.rememberMe = false;
    this.nextPage = '';
  }

  ngOnInit(): void {
  }

  register(): void {
    this.router.navigate(['/account/register']);
  }

  login(): void {
    // eslint-disable-next-line no-console
    console.log('infoLogin' + this.rememberMe)
    this.loginService
      .login({
        username: this.username,
        password: this.passwort,
        rememberMe: this.rememberMe,
      })
      .subscribe(
        () => {
          this.authenticationError = false;
          if (
            this.router.url === '/account/register' ||
            this.router.url.startsWith('/account/activate') ||
            this.router.url.startsWith('/account/reset/') ||
            this.router.url === ''
          ) {
            // eslint-disable-next-line no-console
            console.log('infoLoginsdfe')
          }
          this.router.navigate([this.nextPage]);
        },
        () => (this.authenticationError = true)
      );
  }
}
