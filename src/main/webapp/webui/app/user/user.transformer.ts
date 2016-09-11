import {User} from './user';

export class UserTransformer {
    public dataArrayToJson(data:any[]): User[] {
        let result:Array<User> = new Array<User>();
        data.forEach(e => {
            let user: User = this.dataObjetToJson(e);
            result.push(e);
        });
        return result;
    }

    public dataObjetToJson(data:any): User {
        let result: User = new User();
        result.userId = data.userId;
        result.name = data.name;
        return result;
    }

}
