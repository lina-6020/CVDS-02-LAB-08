# MyBatis-Guice-PrimeFaces -Integración de capas con Google Guice   
## Parte I. Inicio en clase   
En esta parte inicial utilizamos el proyecto del laboratorio anterior e hicimos algunas actualizaciones tales como:
* Se creo la interface ```ItemDAO``` y lo utilizamos como referencia para las demas entidades ```ClienteDAO``` y ```TipoItemDAO```.Usando como referencia la implementación de ```ItemDAO:MyBATISItemDao``` creamos el ```DAO MyBATIS``` de las demas entidades ```MyBATISClienteDAO```,```MyBATISItemDao``` y ```MyBATISTipoItemDAO```.Creamos la clase ```ServiciosAlquiler``` e hicimos la implementación de ```ServiciosAlquilerImpl``` , ```ServiciosAlquilerItemsStub``` y ```ServiciosAlquilerFactory```. Logrando la siguiente estructura funcional.
* Comprobamos que a través de la capa lógica se puede consultar un cliente.

## Parte II. Pruebas
En esta parte se crearon pruebas utilizando diferentes clases de equivalencia necesarias para las diferentes operaciones definidas en los servicios.
 
## Parte III - Capa Presentación 
* Realizamos cambios en el ```pom.xml``` para que el proyecto se construya correctamente como una aplicación WEB
* Desplegamos la aplicación con ```mvn tomcat7:run```

## Parte IV - Entrega continua 
Adjuntamos los enlaces de 
* _CircleCI_
* _Codacy_
* _Heroku_
