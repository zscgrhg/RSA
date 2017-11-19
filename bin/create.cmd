@echo off
set DNAME="CN=Myself, OU=MyOrganizationUnit, O=MyOrganizationName, L=MyLocalityName,S=MyStateName, C=CN"
set "STOREPASS=mystorepass"
set "KEYPASS=mykeypass"
set "STOREPATH=appweb.jks"
set "VALIDITY=3650"
set "ALIAS=appweb"
set KEYALG="RSA"
set "KEYSIZE=2048"

if  exist "%STOREPATH%" goto end

keytool -genkeypair -dname %DNAME% -storetype jks -keystore %STOREPATH%  -storepass %STOREPASS% -alias %ALIAS% -keyalg %KEYALG% -keysize %KEYSIZE% -keypass %KEYPASS% -validity %VALIDITY%

keytool -exportcert -alias %ALIAS% -file %ALIAS%.pem -storetype jks  -keystore %STOREPATH%   -storepass %STOREPASS% -rfc

:end
keytool -printcert -file .\appweb.pem