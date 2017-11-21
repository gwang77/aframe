Config shiro client:
1. spring-shiro.xml(aframe-acm-shiro-client\src\main\resources\spring-shiro.xml). About how to do configuration, can follow shiro document.
	For none sso applications, can use spring-shiro.xml directly, It support password login and LDAP login.
	For sso applicatioins, need change spring-shiro.xml.sso.bak to spring-shiro.xml.

2. shiro-common.properties
	It can be used to configure filter rules, it is the same as configured in spring-shiro.xml bean "shiroFilter".
	You can also create own shiro-*.properties file to put your module's filer rules. 
	eg:
	filterChain./** = user,urlAcc
	"user" means need login, "urlAcc" means the user must has the url's priv. So this line means all url need login, and has priv.

