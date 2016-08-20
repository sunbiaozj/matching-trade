import { Injectable } from '@angular/core';
import { HttpService } from '../common/http.service';
import {Authentication} from './authentication';
import {AuthenticationTransformer} from "./authentication.transformer";

@Injectable()
export class AuthenticationService {
    private url: string = 'authenticate';
    private transformer = new AuthenticationTransformer();

    constructor(private httpService: HttpService) { }

    public get(): Promise<Authentication> {
        return this.httpService
                .get("/" + this.url + "/info", true)
                .then(response => this.transformer.dataObjetToJson(response.json()));
    }
}
