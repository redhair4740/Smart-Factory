export interface User {
  id: number;
  code?: string;
  name?: string;
  loginName: string;
  password?: string;
  phone?: string;
  email?: string;
  plantCode?: string;
  superAdminFlag?: number;
  plantAdminFlag?: number;
  loginType?: string;
}