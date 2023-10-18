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
<img src="src\main\resources\static\uml.png" alt="UML">



<h4>Tecnologias</h4>

<ul>
    <li>Java 17</li>
    <li>password: 12345678</li>
    <li>Spring boot 3.14</li>
    <li>lombok</li>
    <li>mysql data base</li>
    <li>Spring security</li>
    <li>JWT</li>
    <li>Manejo de roles</li>
    <li>Junit y Mockito -> integration test</li>

</ul>




<p>
Al inicializar la API por primera vez, se crea un usuario con rol ADMIN y dos roles adicionales: CUSTOMER y ADMIN. Dependiendo de los roles asignados a un usuario, este tendrá acceso a diferentes puntos finales de la API.
</p>


<h3>Usuario Admin</h3>
<ul>
<li>username: admin@gmail.com</li>
<li>password: 12345678</li>
</ul>


<h3>Postman</h3>
Puedes encontrar la colección de Postman en resource/postman/
