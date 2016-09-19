import { Injectable } from '@angular/core';
import { HttpService } from '../common/http/http.service';
import {Authentication} from './authentication';
import {AuthenticationTransformer} from "./authentication.transformer";

@Injectable()
export class AuthenticationService {
    private url: string = 'authenticate';
    private transformer = new AuthenticationTransformer();
    private lastAuthentication: Authentication = null;

    constructor(private httpService: HttpService) { }

    public get(): Promise<Authentication> {
        return this.httpService
            .get("/" + this.url + "/info", true)
            .then(response => {
                this.lastAuthentication = this.transformer.dataObjetToJson(response.json());
                return this.lastAuthentication;
            });
    }

    /**
     * Returns the last authentication object requested by this service.
     * Useful when we do not want to go to the server just to get authentication information.
     * It is lazyly initialized. So if this is the first time an authentication object is requested
     * it will make a request to the server.
     */
    public getLastAuthentication(): Promise<Authentication> {
        if (!this.lastAuthentication) {
            return this.get();
        } else {
            let result = new Promise<Authentication>((resolve, reject) => {
                resolve(this.lastAuthentication);
            });
            return result;
        }
    }
}
