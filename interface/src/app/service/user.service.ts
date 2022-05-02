import { Injectable } from '@angular/core';
import { map } from 'rxjs';
import { LogInData } from '../model/form_data/LogInData';
import { SignUpData } from '../model/form_data/SignUpData';
import { UserUpdateData } from '../model/form_data/UserUpdateData';
import { User } from '../model/User';
import { AppConfig } from '../util/AppConfig';
import { EntityDeserializer } from '../util/EntityDeserializer';
import { HttpRequest } from '../util/HttpRequest';
import { HttpManager } from '../util/manager/HttpManager';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private http: HttpManager) { }

  getAuthenticatedUser(): HttpRequest<User | null> {
    return this.http.manage(this.http.get(`${AppConfig.API_ENDPOINT}/user`).pipe(map(user => EntityDeserializer.deserializeNullable(user, User))));
  }

  logIn(data: LogInData): HttpRequest<User> {
    return this.http.manage(this.http.post(`${AppConfig.API_ENDPOINT}/user/authenticate`, data).pipe(map(user => EntityDeserializer.deserialize(user, User))));
  }

  logOut(): HttpRequest<void> {
    return this.http.manage(this.http.post(`${AppConfig.API_ENDPOINT}/user/logOut`, {}));
  }

  signUp(data: SignUpData): HttpRequest<User> {
    return this.http.manage(this.http.post(`${AppConfig.API_ENDPOINT}/user/register`, data).pipe(map(user => EntityDeserializer.deserialize(user, User))));
  }

  updateUser(data: UserUpdateData): HttpRequest<User> {
    return this.http.manage(this.http.patch(`${AppConfig.API_ENDPOINT}/user`, data).pipe(map(user => EntityDeserializer.deserialize(user, User))));
  }
}
