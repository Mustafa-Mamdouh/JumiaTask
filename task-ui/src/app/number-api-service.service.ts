import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { NumberResponse } from './NumberResponse';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class NumberApiServiceService {
  private url = 'http://localhost:8080/';  // URL to web api

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };
  constructor(
    private http: HttpClient,
  ) { }

  /** GET heroes from the server */
  getAllNumbers(page: number, numberOfItems: number, direction: string, sort: string, state: string, country: string): Observable<NumberResponse> {
    let params = new HttpParams();
    params = params.append('page', page);
    params = params.append('numberOfItems', numberOfItems);
    if (sort && sort !== "none") {
      params = params.append('direction', direction);
      params = params.append('sort', sort);
    }
    if (state && state !== "none") {
      params = params.append('state', state);
    }
    if (country && country !== "none") {
      params = params.append('country', country);
    }
    return this.http.get<NumberResponse>(this.url + 'numbers', {
      'params': params
    })
      .pipe(
        catchError(this.handleError<NumberResponse>('getNumbers'))
      );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead


      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }
  getSupportedCountries(): Observable<any> {
    return this.http.get<any>(this.url + 'countries')
      .pipe(
        catchError(this.handleError<any>('getCountries'))
      );
  }
}
