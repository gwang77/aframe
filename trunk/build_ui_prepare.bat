call npm install -g bower
call npm install -g grunt-cli
call npm install -g xsm-cli
call npm install -g bower-nexus3-resolver

cd aframe-ui
call npm install
call bower install
call xsm bootstrap build
cd..

