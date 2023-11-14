# TpiBackend
Antes de todo, debemos asegurarnos de tener el .jar actualizado
podemos hacer esto con el comando 

mvn clean install
mvn package

esto debemos hacerlo en la carpeta raiz, y nos actualizara el jar de Estaciones y Alquileres
luego entramos a la carpeta Gateway y aplicamos lo mismpo para actualizar el jar del Gateway

y para desplegar el proyecto en un contenedor, debemos tener instalado previamente docker desktop
una ves que abrimos docker, podemos ir a la carpeta del proyecto, abrir una terminarl y colocar el siguiente comando

docker-compose up --build   (si ponemos -d al comando anterior lo ejecutara en segundo plano)

esto nos depliega el proyecto y en la terminal podremos visualizar los logs que se esten ejecutando de los 3 microservicios

para detener podemos apretar ctrl + c
o con el comando 

docker-compose down


