package libreria.servicios;

import java.util.List;
import java.util.UUID;
import libreria.entidades.Autor;
import libreria.entidades.Editorial;
import libreria.entidades.Libro;
import libreria.exceptions.AutorException;
import libreria.exceptions.EditorialException;
import libreria.exceptions.LibroException;
import libreria.persistencia.LibroDAO;

/**
 *
 * @author Matias Luca Soto
 */
public final class LibroService {

    //ATRIBUTO DAO / AUTOR SERVICE / EDITORIAL SERVICE
    private final LibroDAO dao;
    private final AutorService autorService;
    private final EditorialService editorialService;

    //CONSTRUCTOR
    public LibroService() {
        this.dao = new LibroDAO();
        this.autorService = new AutorService();
        this.editorialService = new EditorialService();
    }

    //MÉTODOS
    /**
     * Recibe por parámetro todos los atributos del Objeto Libro, les realiza verificaciones y, si todo está correcto, setea los atributos para después persistirlo en la BD
     *
     * @param isbn
     * @param titulo
     * @param anio
     * @param ejemplares
     * @param ejemplaresPrestados
     * @param ejemplaresRestantes
     * @param nombreAutor
     * @param nombreEditorial
     * @throws LibroException
     * @throws AutorException
     * @throws EditorialException
     */
    public void guardarLibro(Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Integer ejemplaresRestantes, String nombreAutor, String nombreEditorial) throws LibroException, AutorException, EditorialException {

        try {

            //VALIDACIONES
            if (isbn < 0) {
                throw new LibroException("El ISBN ingresado no es válido");
            }
            //Si ya hay un libro con el mismo ISBN, se lanza la excepción y se muestra cual Libro ya tiene ese código
            Libro verificacionIsbn = buscarLibroPorIsbn(isbn);
            if (verificacionIsbn != null) {
                throw new LibroException("El ISBN ingresado ya pertenece al siguiente libro:"
                        + "\nISBN: " + verificacionIsbn.getIsbn() + " / Titulo: " + verificacionIsbn.getTitulo());
            }
            if (titulo.trim().isEmpty()) {
                throw new LibroException("Debe indicar el título del libro");
            }
            if (anio < 0) {
                throw new LibroException("Debe ingresar un año válido.");
            }
            if (ejemplares < 0) {
                throw new LibroException("No puede ingresar un valor negativo en ejemplares totales.");
            }
            if (ejemplaresPrestados < 0) {
                throw new LibroException("No puede ingresar un valor negativo en ejemplares prestados.");
            }
            if (ejemplaresRestantes < 0) {
                throw new LibroException("No puede ingresar un valor negativo en ejemplares restantes.");
            }
            if (nombreAutor.trim().isEmpty()) {
                throw new LibroException("El autor no puede estar vacio.");
            }
            if (nombreEditorial.trim().isEmpty()) {
                throw new LibroException("La editorial no puede estar vacia.");
            }
            if (dao.buscarLibroPorTitulo(titulo) != null) {
                throw new LibroException("El titulo ingresado ya existe.");
            }

            //ARMADO DEL OBJETO LIBRO
            Libro libro = new Libro();
            libro.setId(UUID.randomUUID().toString()); // Generación de código hexadecimal
            libro.setIsbn(isbn);
            libro.setTitulo(titulo);
            libro.setAnio(anio);
            libro.setEjemplares(ejemplares);
            libro.setEjemplaresPrestados(ejemplaresPrestados);
            libro.setEjemplaresRestantes(ejemplaresRestantes);

            /* VERIFICO SI EL AUTOR INGRESADO YA EXISTE.
            Si no existe todavia, lo creo y lo seteo al Libro. Si ya existe, ya lo seteo. */
            Autor autor = autorService.buscarAutorPorNombre(nombreAutor);
            if (autor == null) {
                libro.setAutor(autorService.crearAutor(nombreAutor));
            } else {
                libro.setAutor(autor);
            }

            //VERIFICO SI LA EDITORIAL YA EXISTE - Misma lógica que con Autor
            Editorial editorial = editorialService.buscarEditorialPorNombre(nombreEditorial);
            if (editorial == null) {
                libro.setEditorial(editorialService.crearEditorial(nombreEditorial));
            } else {
                libro.setEditorial(editorial);
            }

            //DAMOS EL ALTA AL LIBRO
            libro.setAlta(true);

            //GUARDADO EN BASE DE DATOS
            dao.guardarLibro(libro);

        } catch (LibroException | AutorException | EditorialException e) {
            throw e;
        } catch (Exception e) {
            System.out.println("Error guardando Libro en LibroService");
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Recibe por parámetro el Objeto Libro a modificar y todos los nuevos atributos, les realiza verificaciones y, si todo está correcto, setea los atributos para después persistirlo en la BD
     *
     * @param libro
     * @param isbn
     * @param titulo
     * @param anio
     * @param ejemplares
     * @param ejemplaresPrestados
     * @param ejemplaresRestantes
     * @param nombreAutor
     * @param nombreEditorial
     * @throws LibroException
     * @throws Exception
     */
    public void modificarLibro(Libro libro, Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Integer ejemplaresRestantes, String nombreAutor, String nombreEditorial) throws LibroException, Exception {

        try {
            //VALIDACIONES
            if (libro == null) {
                throw new LibroException("El libro a modificar está vacío.");
            }
            if (isbn < 0) {
                throw new LibroException("El código ISBN no es válido.");
            }
            /* Si ya hay un libro con el mismo ISBN Y no pertenece al Libro recibido, se lanza la excepción y se muestra cual Libro ya tiene ese código.
            Esta verificación extra es permitir que el Libro a modificar conserve su ISBN anterior */
            Libro verificacionIsbn = buscarLibroPorIsbn(isbn);
            if (verificacionIsbn != null && verificacionIsbn != libro) {
                throw new LibroException("El ISBN ingresado ya pertenece al siguiente libro:"
                        + "\nISBN: " + verificacionIsbn.getIsbn() + " / Titulo: " + verificacionIsbn.getTitulo());
            }
            if (titulo.trim().isEmpty()) {
                throw new LibroException("Debe indicar el título del libro");
            }
            if (anio < 0) {
                throw new LibroException("Debe ingresar un año válido.");
            }
            if (ejemplares < 0) {
                throw new LibroException("No puede ingresar un valor negativo en ejemplares totales.");
            }
            if (ejemplaresPrestados < 0) {
                throw new LibroException("No puede ingresar un valor negativo en ejemplares prestados.");
            }
            if (ejemplaresRestantes < 0) {
                throw new LibroException("No puede ingresar un valor negativo en ejemplares restantes.");
            }
            if (nombreAutor == null) {
                throw new LibroException("El autor no puede estar vacio.");
            }
            if (nombreEditorial == null) {
                throw new LibroException("La editorial no puede estar vacia.");
            }

            //Seteo los nuevos valores para el Libro
            libro.setIsbn(isbn);
            libro.setTitulo(titulo);
            libro.setAnio(anio);
            libro.setEjemplares(ejemplares);
            libro.setEjemplaresPrestados(ejemplaresPrestados);
            libro.setEjemplaresRestantes(ejemplaresRestantes);

            //VERIFICO SI EL AUTOR INGRESADO YA EXISTE
            Autor autor = autorService.buscarAutorPorNombre(nombreAutor);
            if (autor == null) {
                libro.setAutor(autorService.crearAutor(nombreAutor));
            } else {
                libro.setAutor(autor);
            }

            //VERIFICO SI LA EDITORIAL YA EXISTE
            Editorial editorial = editorialService.buscarEditorialPorNombre(nombreEditorial);
            if (editorial == null) {
                libro.setEditorial(editorialService.crearEditorial(nombreEditorial));
            } else {
                libro.setEditorial(editorial);
            }

            libro.setAlta(true);

            //Envio el Libro actualizado al DAO
            dao.modificarLibro(libro);

        } catch (LibroException e) {
            throw e;
        } catch (Exception e) {
            System.out.println("Error modificando Libro en LibroService");
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Recibe un código Long ISBN como parámetro para eliminar el Libro matcheado a ese ISBN de la BD.
     *
     * @param isbn Código Long del Libro a eliminar
     * @throws LibroException Si no se encuentra ningún Libro con ese ISBN, se lanza la excepción
     */
    public void eliminarLibroIsbn(Long isbn) throws LibroException {

        try {

            Libro libro = dao.buscarLibroPorIsbn(isbn);
            //Verificación
            if (libro == null) {
                throw new LibroException("El código ISBN no pertenece a ningún libro guardado.");
            }
            //Elimino el Libro en el DAO pasando su Primary Key como argumento
            dao.eliminarLibroPorId(libro.getId());
        } catch (LibroException e) {
            throw e;
        } catch (Exception e) {
            System.out.println("Error eliminando Libro en LibroService");
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Busca un Objeto Libro con el mismo código ISBN pasado como parámetro y lo devuelve de la BD
     *
     * @param isbn Código del Libro a buscar
     * @return El libro buscando de ser encontrado, sino null
     */
    public Libro buscarLibroPorIsbn(Long isbn) {
        try {

            return dao.buscarLibroPorIsbn(isbn);

        } catch (Exception e) {
            System.out.println("Error buscando Libro por ISBN en LibroService");
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Busca un Objeto Libro con el Título pasado como parámetro y lo devuelve de la BD
     *
     * @param titulo Nombre del Libro a buscar (no case-sensitive)
     * @return El Libro con el titulo pasado como argumento. Si no existe, se arroja un LibroException
     * @throws LibroException En caso de llegar un parámetro vacio o no encontrar ningún Libro
     */
    public Libro buscarLibroPorTitulo(String titulo) throws LibroException {
        try {

            //VERIFICACION DEL ARGUMENTO
            if (titulo.trim().isEmpty()) {
                throw new LibroException("El título del Libro está vacío.");
            }

            //SE BUSCA EL LIBRO EN LA BD
            Libro libro = dao.buscarLibroPorTitulo(titulo);

            //VERIFICACIÓN DEL OBJETO DEVUELTO POR LA BD
            if (libro == null) {
                throw new LibroException("El título no pertenece a ningún libro guardado.");
            } else {
                return libro;
            }

        } catch (LibroException e) {
            throw e;
        } catch (Exception e) {
            System.out.println("Error buscando Libro por título en LibroService");
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Busca y devuelve una Lista con todos los Libros de la BD que tengan el mismo nombre de Autor en sus atributos.
     *
     * @param nombre Nombre del Autor que esté ligado a los Libros a buscar
     * @return Lista con todos los Libros que cumplan la condición. Si está vacia, se lanza un LibroException
     * @throws LibroException Si la lista está vacía
     */
    public List<Libro> buscarLibrosPorAutor(String nombre) throws LibroException {

        try {

            //VERIFICACIÓN DEL ARGUMENTO
            if (nombre.trim().isEmpty()) {
                throw new LibroException("El nombre del autor está vacío.");
            }

            //SE BUSCAN TODOS LOS LIBROS CON EL MISMO AUTOR
            List<Libro> libros = dao.buscarLibrosPorAutor(nombre);

            //VERIFICACIÓN DE RESULTADOS
            if (libros.isEmpty()) {
                throw new LibroException("No se encontraron Libros con ese autor :(");
            } else {
                return libros;
            }

        } catch (LibroException e) {
            throw e;
        } catch (Exception e) {
            System.out.println("Error buscando Libros por nombre de autor en LibroService");
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Busca todos los Libros en la BD que estén matcheados al nombre de la Editorial pasada como argumento y los devuelve en una Lista
     *
     * @param nombre Nombre de la Editorial
     * @return Lista con todos los Libros que cumplan la condición
     * @throws LibroException Si el argumento está vació o si no hay libros que cumplan la condición
     */
    public List<Libro> buscarLibrosPorEditorial(String nombre) throws LibroException {

        try {

            //VERIFICACIÓN DEL ARGUMENTO
            if (nombre.trim().isEmpty()) {
                throw new LibroException("El nombre de la editorial está vacío.");
            }

            //BÚSQUEDA DE TODOS LOS LIBROS QUE CUMPLAN LA CONDICIÓN
            List<Libro> libros = dao.buscarLibrosPorEditorial(nombre);

            //VERIFICACIÓN DE LOS RESULTADOS
            if (libros.isEmpty()) {
                throw new LibroException("No se encontró ningún Libro con esa editorial :(");
            } else {
                return libros;
            }

        } catch (LibroException e) {
            throw e;
        } catch (Exception e) {
            System.out.println("Error buscando Libros por nombre de editorial en LibroService");
            e.printStackTrace();
            throw e;
        }

    }

    /**
     * Devuelve una Lista con todos los Objetos Libros que se encuentren guardados en la BD
     *
     * @return Lista con todos los Objetos Libro en la BD
     */
    public List<Libro> listarLibros() {

        try {

            return dao.listarLibros();

        } catch (Exception e) {
            System.out.println("Error listando Libros en LibroService");
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * Método que muestra por consola todos los Libros dentro de la BD. Si no hay elementos, se avisa con un mensaje
     */
    public void imprimirLibros() {

        try {

            //MÉTODO SERVICE QUE DEVUELVE UNA LISTA CON TODOS LOS LIBROS
            List<Libro> libros = listarLibros();
            
            //SI NO HAY LIBROS, SE MUESTRA UN MENSAJE. SINO, SE INICIA EL MÉTODO
            if (libros.isEmpty()) {
                System.out.println("No hay libros guardados para mostrar."
                        + "\n¡Empieza agregando uno!");
            } else {

                //OUTPUT
                System.out.println("\nLIBROS EN BASE DE DATOS:");
                for (Libro libro : libros) {
                    System.out.println(libro.toString());
                }

            }

        } catch (Exception e) {
            System.out.println("Error imprimiendo Libros en LibroService");
            e.printStackTrace();
            throw e;
        }

    }

}
