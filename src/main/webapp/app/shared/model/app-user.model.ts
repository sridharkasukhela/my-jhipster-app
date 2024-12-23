import dayjs from 'dayjs';

export interface IAppUser {
  id?: number;
  externalUserId?: string;
  username?: string;
  firstName?: string;
  lastName?: string;
  email?: string;
  registeredDate?: dayjs.Dayjs | null;
  lastLoginDate?: dayjs.Dayjs | null;
}

export const defaultValue: Readonly<IAppUser> = {};
