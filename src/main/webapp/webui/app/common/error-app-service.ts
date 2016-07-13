import { Component, Injectable } from '@angular/core';
import { Subject } from 'rxjs/Subject';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class ErrorAppService {

  private error: string;
  private subject: Subject<string> = new Subject<string>();

  public getErrorObservable(): Observable<string> {
    return this.subject.asObservable();
  }

  public addError(e: string): void {
    this.error = e;
    this.subject.next(e);
  }

}
