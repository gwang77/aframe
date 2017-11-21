SET UI_COMPILE_PARAM=prd
IF NOT "%1"=="" set UI_COMPILE_PARAM=%1

cd aframe
call mvn clean install -DskipTests=true -P %UI_COMPILE_PARAM%
cd ..

cd aframe-acm-shiro-client
call mvn clean install -DskipTests=true -P %UI_COMPILE_PARAM%
cd ..

cd aframe-acm
call mvn clean install -DskipTests=true -P %UI_COMPILE_PARAM%
cd ..
xcopy /Y aframe-acm\target\acm.war project_deploy\war\%UI_COMPILE_PARAM%\
