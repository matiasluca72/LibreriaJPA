# LibreriaJPA
< ESPAÑOL >
<h2>Proyecto Java para la práctica y estudio de la API de Persistencia en Java (JPA) estableciendo una conexión a una base de datos relacional MySQL.</h2>
<br>
El enunciado de la consigna es: <i>el desarrollo de un sistema de guardado de libros en JAVA 
utilizando una base de datos MySQL y JPA como framework de persistencia. <br>
Al alumno le toca desarrollar, las siguientes funcionalidades: 
  <ul>
<li>1) Crear base de datos Librería</li>
<li>2) Crear unidad de persistencia</li>
<li>3) Crear entidades previamente mencionadas</li>
<li>4) Generar las tablas con JPA</li>
<li>5) Crear servicios previamente mencionados.</li>
<li>6) Crear los métodos para persistir entidades en la base de datos librería</li>
<li>7) Crear los métodos para dar de alta/bajo o editar dichas entidades.</li>
<li>8) Búsqueda de un Autor por nombre.</li>
<li>9) Búsqueda de un libro por ISBN.</li>
<li>10) Búsqueda de un libro por Título.</li>
<li>11) Búsqueda de un libro/s por nombre de Autor.</li>
<li>12) Búsqueda de un libro/s por nombre de Editorial.</li>
<li>13) Agregar las siguientes validaciones a todas las funcionalidades de la aplicación:
  <ul>
<li>• Validar campos obligatorios.</li>
<li>• No ingresar datos duplicados.</li>
  </ul>
  </li>
  </ul>
</i>
<br>
<h3>Algunas características del proyecto realizado:</h3>
<ul>
  <li>Creación y manipulación del archivo de persistencia <strong>persistence.xml</strong> configurándolo con el proveedor <strong>EclipseLink</strong></li>
  <li>Desarrollo del patrón de diseño DAO (Data Access Object) para cada entidad</li>
  <li>Desarrollo desde 0 de los principales métodos CRUD utilizando la Clase <strong>EntityManager</strong></li>
  <li>Desarrollo de otras operaciones de consulta puntuales para la práctica de armado de queries nativas desde Java usando <strong>JPQL</strong></li>
  <li>Implementación de bloques try-catch para el manejo de excepciones de forma ordenada</li>
  <li>Implementación de estrategias de generación de ID y de relaciones entre Clases</li>
  <li>Modulación de las diferentes capas y acceso ordenado entre ellas (ENTIDAD >> DAO >> SERVICIO >> MENÚ (FRONT))</li>
  <li>Mapeo de las tablas usando <strong>anotaciones de JPA</strong></li>
</ul>
<br>
Este proyecto fué <strong>altamente documentado</strong> para conseguir entender y comprender la razón de ser de cada MÉTODO y ATRIBUTO y así maximizar el
conocimiento y aprendizaje del uso e importancia de la API de Persistencia en Java (JPA).

<br>
Este proyecto fue parte de las prácticas realizadas en el curso de Programador Web FullStack con Java de <a href="https://www.linkedin.com/company/eggcooperation/" target="blank_">EGG Cooperation</a>.
