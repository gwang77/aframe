Config CAS sso server:

1. Copy casserver_localhost.keystore to "D:\casserver_localhost.keystore".

2. Config Tomcat
    <Connector port="8443" protocol="org.apache.coyote.http11.Http11NioProtocol"
               maxThreads="150" SSLEnabled="true" scheme="https" secure="true"
               clientAuth="false" sslProtocol="TLS" 
			   keystoreFile="D:\casserver_localhost.keystore" keystorePass="casserver"
	/>

3. Change build configuration:
	Open config_dev(sit/uat/prd).properties file, configure parameters
	Change etc\cas.properties to set some parameters.

4. Build acmsso.war file.

5. Deploy acmsso.war.



Client:
1. Export key:
keytool -export -alias localhost -file D:\casserver_localhost.cer -keystore D:\casserver_localhost.keystore
password is casserver

2. Import key to JDK.
cd %JAVA_HOME%\jre\lib\security
%JAVA_HOME%\bin\keytool -import -alias casserver -file D:\casserver_localhost.cer -noprompt -trustcacerts -storetype jks -keystore cacerts -storepass casserver
(if import failed, can delete file cacerts)

3. Change build configuration:
	Open config_dev(sit/uat/prd).properties file, configure parameters

4. Build client war file.

5. Deploy client war file



CAS Server Generate key:
keytool -genkey -alias casserver -keypass casserver -keyalg RSA  -keystore casserver_localhost.keystore  -validity 365
password:casserver
host:localhost
......

keytool -export -alias casserver -storepass casserver -file casserver_localhost.cer -keystore casserver_localhost.keystore


CAS Client import key:
keytool -import -alias casserver -file casserver_localhost.cer -noprompt -trustcacerts -storetype jks -keystore casserver_localhost.keystore -storepass casserver

