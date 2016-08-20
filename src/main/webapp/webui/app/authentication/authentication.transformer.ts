import {Authentication} from './authentication';

export class AuthenticationTransformer {

    public dataObjetToJson(data: any): Authentication {
        let result: Authentication = new Authentication();
        result.email = data.email;
        result.name = data.name;
        result.isAuthenticated = data.isAuthenticated;
        return result;
    }
}
