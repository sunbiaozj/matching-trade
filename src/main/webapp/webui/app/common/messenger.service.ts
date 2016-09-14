import {Component, Injectable} from '@angular/core';
import {Subject} from 'rxjs/Subject';
import {Observable} from 'rxjs/Observable';

@Injectable()
export class MessangerService {

  private errorSubject: Subject<string> = new Subject<string>();
  private messageSubject: Subject<string> = new Subject<string>();

  public getErrorObservable(): Observable<string> {
    return this.errorSubject.asObservable();
  }

  public getMessageObservable(): Observable<string> {
    return this.messageSubject.asObservable();
  }

  public setError(e: string): void {
    this.errorSubject.next(e);
  }

  public setMessage(m: string): void {
    this.messageSubject.next(m);
  }

}
