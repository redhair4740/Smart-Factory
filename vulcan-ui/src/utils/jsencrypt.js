import JSEncrypt from 'jsencrypt/bin/jsencrypt.min'

// 密钥对生成 http://web.chacuo.net/netrsakeypair

const publicKey = 'MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBANv9lg/mMuBDCbyUBisOWMRz/Aq/HUWi\n' +
    'y+bxIg7pqi3YnYQilccU0ydyE4T1VNidhSU2TSWcJVncmY1QqVs6Wz8CAwEAAQ=='

const privateKey = 'MIIBUwIBADANBgkqhkiG9w0BAQEFAASCAT0wggE5AgEAAkEA2/2WD+Yy4EMJvJQG\n' +
    'Kw5YxHP8Cr8dRaLL5vEiDumqLdidhCKVxxTTJ3IThPVU2J2FJTZNJZwlWdyZjVCp\n' +
    'WzpbPwIDAQABAkAO67Mq3uUiesmFRdDpSK7SnD9DB1VX7MPBpSc3Nbf6Je/P+S9C\n' +
    'yE2AgXkKkJiKFENfXhbZZJo3xMnIBHavs12ZAiEA7bzYnYv20SaduIy03CyRigGo\n' +
    'DbQN7dAkJSQqT+N9/C0CIQDs471uApYSixx81QakZ1mXEFnEr3/XViyz8mEcd6zc\n' +
    'mwIgGQxRol/R1H2GFqC0mZIUj37XLwJuiUTp2gPydVJZvs0CIQDatF/9oxGeySRh\n' +
    '8+MQAgLqv0PBnS3wRzU6ZXpW5/6GJQIfEo1V520WaL3C4263Zk7YBP4JBf27xhEz\n' +
    'XLtIkCEQ8A=='

// 加密
export function encrypt(txt) {
  const encryptor = new JSEncrypt()
  encryptor.setPublicKey(publicKey) // 设置公钥
  return encryptor.encrypt(txt) // 对数据进行加密
}

// 解密
export function decrypt(txt) {
  const encryptor = new JSEncrypt()
  encryptor.setPrivateKey(privateKey) // 设置私钥
  return encryptor.decrypt(txt) // 对数据进行解密
}

