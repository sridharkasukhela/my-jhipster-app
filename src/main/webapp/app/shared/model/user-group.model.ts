import { IAppUser } from 'app/shared/model/app-user.model';

export interface IUserGroup {
  id?: number;
  name?: string;
  appUser?: IAppUser | null;
}

export const defaultValue: Readonly<IUserGroup> = {};
