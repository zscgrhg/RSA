var crypto = window.crypto || window.msCrypto;
var encryptAlgorithm = {
    name: "RSA-OAEP",
  //  modulusLength: 2048,
   publicExponent: new Uint8Array([0x01, 0x00, 0x01]),
    hash: {
        name: "SHA-256"
    }
};

function arrayBufferToBase64String(arrayBuffer) {
    var byteArray = new Uint8Array(arrayBuffer)
    var byteString = '';
    for (var i = 0; i < byteArray.byteLength; i++) {
        byteString += String.fromCharCode(byteArray[i]);
    }
    return btoa(byteString);
}

function base64StringToArrayBuffer(b64str) {
    var byteStr = atob(b64str);
    var bytes = new Uint8Array(byteStr.length);
    for (var i = 0; i < byteStr.length; i++) {
        bytes[i] = byteStr.charCodeAt(i);
    }
    return bytes.buffer;
}

function textToArrayBuffer(str) {
    var buf = unescape(encodeURIComponent(str)); // 2 bytes for each char
    var bufView = new Uint8Array(buf.length);
    for (var i = 0; i < buf.length; i++) {
        bufView[i] = buf.charCodeAt(i);
    }
    return bufView;
}

function convertPemToBinary(pem) {
    var lines = pem.split('\n');
    var encoded = '';
    for (var i = 0; i < lines.length; i++) {
        if (lines[i].trim().length > 0 &&
            lines[i].indexOf('-BEGIN RSA PRIVATE KEY-') < 0 &&
            lines[i].indexOf('-BEGIN RSA PUBLIC KEY-') < 0 &&
            lines[i].indexOf('-BEGIN PUBLIC KEY-') < 0 &&
            lines[i].indexOf('-END PUBLIC KEY-') < 0 &&
            lines[i].indexOf('-END RSA PRIVATE KEY-') < 0 &&
            lines[i].indexOf('-END RSA PUBLIC KEY-') < 0) {
            encoded += lines[i].trim();
        }
    }
    //console.log(encoded);
    var base64StringToArrayBuffer2 = base64StringToArrayBuffer(encoded);
    //console.log(base64StringToArrayBuffer2);
    return base64StringToArrayBuffer2;
}

function importPublicKey(pemKey) {
    return new Promise(function (resolve) {
        var importer = crypto.subtle.importKey("spki", convertPemToBinary(pemKey), encryptAlgorithm, true, ["encrypt"]);
        importer.then(function (key) {
            resolve(key);
        });
    });
}

function importPrivateKey(pemKey) {
    return new Promise(function (resolve) {
        var importer = crypto.subtle.importKey("pkcs8", convertPemToBinary(pemKey), encryptAlgorithm, false, ["decrypt"]);
        importer.then(function (key) {
            resolve(key);
        });
    });
}


var pemData="-----BEGIN PUBLIC KEY-----\n" +
    "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuTX5ddvxajZ/0QGGNT8s\n" +
    "gy25GIlKmQnvc00PNbmiH6qQTJxNu7nMUdL5kG31NmtyYlhPAv4wFaYJ9MKFTSWo\n" +
    "oGgA5XR0BCGbeU2tFXG3o4BxTHj4YaplACPah6iTQlK/rjjhj+eW13Ri0IE6Ylvr\n" +
    "QYUkgQSt0+SdyRQH7s1I6a8nekKe3gSMcpR5BKiKljUizJoOlYrx09jKkkxZ+7AI\n" +
    "pdn6Qn4e2j3KKjcziVSGo3HxKYSmyo3QP3w144kAWi7H9iWPrqj6dvNiiEHFV35M\n" +
    "Tf2/xi+Sj29JEY12scjjrzuc22KuKmTSsMX2dhaay6LpG6tWg/YkJ8bEEjG44rW4\n" +
    "PwIDAQAB\n" +
    "-----END PUBLIC KEY-----";


if (crypto.subtle) {
    start = new Date().getTime();
    importPublicKey(pemData).then(function (key) {
        crypto.subtle.encrypt(encryptAlgorithm, key, textToArrayBuffer('nihao123')).then(function (cipheredData) {
            cipheredValue = arrayBufferToBase64String(cipheredData);
            console.log("oh my god!!")
            console.log(cipheredValue);
        });
    });

}