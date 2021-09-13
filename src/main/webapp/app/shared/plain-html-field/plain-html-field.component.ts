import {Component, Input, OnInit} from '@angular/core';
import {DomSanitizer, SafeHtml} from "@angular/platform-browser";

@Component({
  selector: 'jhi-plain-html-field',
  templateUrl: './plain-html-field.component.html',
  styleUrls: ['./plain-html-field.component.scss']
})
export class PlainHtmlFieldComponent implements OnInit {

  @Input()
  private htmlText: string | undefined;

  constructor(private sanitizer: DomSanitizer) { }

  ngOnInit(): void {
  }

  formattedHtmlString(): SafeHtml {
    if (this.htmlText) {
       return this.sanitizer.bypassSecurityTrustHtml(this.htmlText);
    }
    return this.sanitizer.bypassSecurityTrustHtml('');
  }

}
