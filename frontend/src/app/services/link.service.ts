import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { CreateLinkResponse, Link } from "../models/Link";

@Injectable({
  providedIn: 'root'
})
export class LinkService {

  private readonly http = inject(HttpClient);
  private readonly apiUrl = '/api/links';

  getAll(): Observable<Link[]> {
    return this.http.get<Link[]>(this.apiUrl);
  }

    create(url: string): Observable<CreateLinkResponse> {
    return this.http.post<CreateLinkResponse>(this.apiUrl, { url });
  }

  delete(shortCode: string): Observable<void> {
    return this.http.delete<void>(
      `${this.apiUrl}/${shortCode}`
    );
  }
}