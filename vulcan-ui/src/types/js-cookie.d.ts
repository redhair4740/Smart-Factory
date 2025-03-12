declare module 'js-cookie' {
  interface CookieAttributes {
    path?: string;
    domain?: string;
    expires?: number | Date;
    secure?: boolean;
    sameSite?: 'strict' | 'lax' | 'none';
  }

  interface CookiesStatic {
    set(name: string, value: string | object, options?: CookieAttributes): string | undefined;
    get(name: string): string | undefined;
    get(): { [key: string]: string };
    remove(name: string, options?: CookieAttributes): void;
    withAttributes(attributes: CookieAttributes): CookiesStatic;
    withConverter(converter: Function): CookiesStatic;
    noConflict(): CookiesStatic;
  }

  const Cookies: CookiesStatic;
  export default Cookies;
} 