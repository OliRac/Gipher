cd $PSScriptRoot/eureka-server
mvn clean package
docker build -t springboot-eureka-server:1.0 . 

cd $PSScriptRoot/favoriteservice
mvn clean package
docker build -t springboot-favoriteservice:1.0 . 

cd $PSScriptRoot/gateway
mvn clean package
docker build -t springboot-spring-cloud-gateway:1.0 . 

cd $PSScriptRoot/recommendedservice
mvn clean package
docker build -t springboot-recommendedservice:1.0 .

cd $PSScriptRoot/searchservice
mvn clean package
docker build -t springboot-searchservice:1.0 .

cd $PSScriptRoot/userservice
mvn clean package
docker build -t springboot-userservice:1.0 .

Write-Host "Success! Press any key to continue ..."
$host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")