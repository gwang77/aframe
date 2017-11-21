SET UI_COMPILE_PARAM=prd
IF NOT "%1"=="" set UI_COMPILE_PARAM=%1

cd aframe-acm-cas-server
call mvn clean package -DskipTests=true -P %UI_COMPILE_PARAM%
cd ..
xcopy /Y aframe-acm-cas-server\target\acmsso.war project_deploy\war\%UI_COMPILE_PARAM%\
