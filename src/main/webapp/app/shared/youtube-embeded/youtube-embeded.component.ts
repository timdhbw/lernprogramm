import {Component, Input, OnInit} from '@angular/core';
import {DomSanitizer, SafeResourceUrl, SafeUrl} from "@angular/platform-browser";

@Component({
  selector: 'jhi-youtube-embeded',
  templateUrl: './youtube-embeded.component.html',
  styleUrls: ['./youtube-embeded.component.scss']
})
export class YoutubeEmbededComponent implements OnInit {

  @Input()
  private youtubeUrl: string | undefined;

  constructor(private sanitizer: DomSanitizer) { }

  ngOnInit(): void {
  }

  getYoutubeSrc(): SafeResourceUrl {
    return this.sanitizer.bypassSecurityTrustResourceUrl(this.getYoutubeUrlEmbedded());
  }

  private getYoutubeUrlEmbedded(): string {
    if (this.youtubeUrl !== undefined) {
      const splitted = this.youtubeUrl.split('?');
      if (splitted.length > 0) {
        const res = splitted.find(str => str.startsWith("v="));
        if (res) {
          return "https://www.youtube.com/embed/" + res.substring(2);
        }
      }
    }
    return ''
  }
}
