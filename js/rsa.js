const crypto = require('crypto');


var publicKey='-----BEGIN CERTIFICATE-----\n' +
    'MIICqjCCAhOgAwIBAgIESfKpJzANBgkqhkiG9w0BAQsFADCBhzELMAkGA1UEBhMC\n' +
    'Q04xFDASBgNVBAgTC015U3RhdGVOYW1lMRcwFQYDVQQHEw5NeUxvY2FsaXR5TmFt\n' +
    'ZTEbMBkGA1UEChMSTXlPcmdhbml6YXRpb25OYW1lMRswGQYDVQQLExJNeU9yZ2Fu\n' +
    'aXphdGlvblVuaXQxDzANBgNVBAMTBk15c2VsZjAeFw0xNzExMTkwNzE4MjdaFw0y\n' +
    'NzExMTcwNzE4MjdaMIGHMQswCQYDVQQGEwJDTjEUMBIGA1UECBMLTXlTdGF0ZU5h\n' +
    'bWUxFzAVBgNVBAcTDk15TG9jYWxpdHlOYW1lMRswGQYDVQQKExJNeU9yZ2FuaXph\n' +
    'dGlvbk5hbWUxGzAZBgNVBAsTEk15T3JnYW5pemF0aW9uVW5pdDEPMA0GA1UEAxMG\n' +
    'TXlzZWxmMIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCsbGVn9u6edFMB+Qty\n' +
    'hH2Z6hTHDYqqiCsLc1Y0Faq7t8K698pjuALhCcAGmtYzTFNjQ1+XinlSaQRYHDId\n' +
    '6l9WvbDtAmX40b0hW3kyfg2A9QDdxWQHPd1zav8N5V5DyT3GBIkx5zEi4a5K8Yli\n' +
    'WonYu2ySp//hsB4Ekzwn4LUaxQIDAQABoyEwHzAdBgNVHQ4EFgQUsjhgFmAvQRxV\n' +
    'CCBdYW5VRmt74oswDQYJKoZIhvcNAQELBQADgYEAft5wZIiH90cBrCyI1n2VXAgI\n' +
    't6fJpdOq01WWXagPrIMQ5aiMr/Rs9N2Y8PfRQrHUkjyh3qR8JaHMeSSbBSFo6/Xw\n' +
    'QJyDUE5rXtCq57pk7Lf2TGgEx8aBLKM6mz5qlr0nkbMRPAf2ginFsyvzzHdxgbv9\n' +
    'CEyYZ5Fb9nL/iNnPqmI=\n' +
    '-----END CERTIFICATE-----'
var options = { key: publicKey, padding: crypto.constants.RSA_PKCS1_PADDING };

var encrypted = crypto.publicEncrypt(options, new Buffer('ohahaha我爱你中国'));
console.log(encrypted.toString('base64'))