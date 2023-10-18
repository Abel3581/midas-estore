# midas-estore
<h2>Tienda de productos</h2>
<p>
Enunciado del ejercicio:
Necesitamos desarrollar las API en java 11 o superior, para la parte backend de un sistema de una tienda. Según el tipo de usuario, se va a permitir realizar distintas acciones.

Para simplificar, sólo consideraremos dos tipos de usuarios: los clientes y los administradores


Los administradores pueden:
- Iniciar sesión
- Realizar abm de productos
- Ver la lista de todos los usuarios registrados en el sistema.
- Ver la lista de todos los pedidos realizados en el sistema.


Los clientes pueden:
- Registrarse
- Iniciar sesión
- Listar todos los productos
- Agregar productos al carrito
- Realizar compra de lo agregado al carrito

El producto debe tener al menos los siguientes atributos:
- id
- name
- description
- price
- count
- state
- stock


Requisitos técnicos que se valoran en la resolución del ejercicio:
- Utilización de algún framework web (por ejemplo, Spring Boot).
- Implementación sobre una base de datos en memoria (por ejemplo, H2).
- Implementación de autenticación y autorización de forma que solo los usuarios autenticados puedan acceder a la API.
- Documentación de la API utilizando Swagger o una herramienta similar.
- Inclusión de pruebas unitarias del código.
- Manejo de errores (Exceptions)
</p>



<h3>UML</h3>
![uml.png](src%2Fmain%2Fresources%2Fstatic%2Fuml.png)

<h4>Tecnologias</h4>
- Java 17
- Spring boot 3.14
- lombok
- mysql data base
- Spring security
- JWT
- Manejo de roles
- Postman
- Junit y Mockito -> integration test



<p>
Al inicializar la API por primera vez, se crea un usuario con rol ADMIN y dos roles adicionales: CUSTOMER y ADMIN. Dependiendo de los roles asignados a un usuario, este tendrá acceso a diferentes puntos finales de la API.
</p>


<h3>Usuario Admin</h3>
- username: admin@gmail.com
- password: 12345678

<h3>Postman</h3>
Puedes encontrar la colección de Postman aca [GestionDeTurnos.postman_collection.json](src%2Fmain%2Fresources%2Fpostman%2FGestionDeTurnos.postman_collection.json).
