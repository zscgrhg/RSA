var fs = require('fs');
var rsaPemToJwk = require('rsa-pem-to-jwk');

var pem = fs.readFileSync('bin\\appweb.pem');
console.log(pem)
var jwk = rsaPemToJwk(pem, {use: 'enc'}, 'public');

console.log(jwk)