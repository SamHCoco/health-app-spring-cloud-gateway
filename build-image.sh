#!/bin/bash

version=""

while getopts "v:" opt; do
  case $opt in
    v)
      version="$OPTARG"
      ;;
    \?)
      exit 1
      ;;
  esac
done


if [ -z "$version" ]; then
  echo "Image version '-v' required."
  exit 1
fi


echo -n "Building spring-cloud-gateway-service Docker image: version $version"

./mvnw clean install

docker rmi health-app-spring-cloud-gateway:$version
docker build -t health-app-spring-cloud-gateway:$version .
docker save health-app-spring-cloud-gateway:1.0.0 > health-app-spring-cloud-gateway.tar

eval $(minikube docker-env)

docker rmi health-app-spring-cloud-gateway:$version
docker load < health-app-spring-cloud-gateway.tar

kubectl apply -f k8/

sudo rm health-app-spring-cloud-gateway.tar
sudo ./mvnw clean