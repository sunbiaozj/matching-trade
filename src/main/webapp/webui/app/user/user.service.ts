import {Injectable} from '@angular/core';
import {HttpService} from '../common/http/http.service';
import {User} from './user';
import {UserTransformer} from './user.transformer';


@Injectable()
export class UserService {
    private url: string = 'users';
    private transformer = new UserTransformer();

    constructor(private httpService: HttpService) { }

    public get(userId: number): Promise<User> {
        return this.httpService
            .get(this.url + "/" + userId)
            .then(response => this.transformer.dataObjetToJson(response.json().data));
    }

    public save(user: User): Promise<User> {
        if (user.userId) {
            return this.httpService.put(this.url, user)
                .then(response => this.transformer.dataObjetToJson(response.json().data));       
        } else {
            return this.httpService.post(this.url, user)
                .then(response => this.transformer.dataObjetToJson(response.json().data));
        }
    }

    public search(): Promise<User[]> {
        return this.httpService.get(this.url)
            .then(response => this.transformer.dataArrayToJson(response.json().data));
    }

}
