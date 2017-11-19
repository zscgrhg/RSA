const crypto = require('crypto');


var publicKey='-----BEGIN CERTIFICATE-----\n' +
    'MIICqjCCAhOgAwIBAgIEB4YibjANBgkqhkiG9w0BAQsFADCBhzELMAkGA1UEBhMC\n' +
    'Q04xFDASBgNVBAgTC015U3RhdGVOYW1lMRcwFQYDVQQHEw5NeUxvY2FsaXR5TmFt\n' +
    'ZTEbMBkGA1UEChMSTXlPcmdhbml6YXRpb25OYW1lMRswGQYDVQQLExJNeU9yZ2Fu\n' +
    'aXphdGlvblVuaXQxDzANBgNVBAMTBk15c2VsZjAeFw0xNzExMTkwNzAyMThaFw0y\n' +
    'NzExMTcwNzAyMThaMIGHMQswCQYDVQQGEwJDTjEUMBIGA1UECBMLTXlTdGF0ZU5h\n' +
    'bWUxFzAVBgNVBAcTDk15TG9jYWxpdHlOYW1lMRswGQYDVQQKExJNeU9yZ2FuaXph\n' +
    'dGlvbk5hbWUxGzAZBgNVBAsTEk15T3JnYW5pemF0aW9uVW5pdDEPMA0GA1UEAxMG\n' +
    'TXlzZWxmMIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCFBCLx4oex+TOlzQRs\n' +
    'B0RU9mCeciBG/ucx+W7x7l0rUAFmyq6B6gM7rpIvjX5SGBr3iI3WR/UrXFQOtjlv\n' +
    '3eyvHzq6hBA+moqgbtHU7/7vfAlD7+ylcYl6xpOwqReO67yk/+5kgK0ro54oLsYr\n' +
    'm3mgtQOEDop5/Ned/COffUJgGwIDAQABoyEwHzAdBgNVHQ4EFgQUrtqew71Zs0DH\n' +
    'PS0MjSp9DWYPqhcwDQYJKoZIhvcNAQELBQADgYEAT6zC4/qVkTz05Uy+6eWdw84K\n' +
    'okZHQEn7OgVWMd/8oFiHFEhreqhLJBmVhDRxnoXMJqZVi4gtiuLc0UyV2k+w5k/F\n' +
    'a4GGZyTV+N6dXDofZvqwVv/3t4c1718TK6qzfCybXWUrbb6syhtT3JAnoQ6eA+l3\n' +
    'cZwzFtSmduWQuEHORZw=\n' +
    '-----END CERTIFICATE-----'
var options = { key: publicKey, padding: crypto.constants.RSA_PKCS1_PADDING };

var encrypted = crypto.publicEncrypt(options, new Buffer('ohahaha我爱你中国'));
console.log(encrypted.toString('base64'))