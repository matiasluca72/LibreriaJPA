package libreria.menu;

import java.util.List;
import java.util.Scanner;
import libreria.entidades.Libro;
import libreria.exceptions.AutorException;
import libreria.exceptions.EditorialException;
import libreria.exceptions.LibroException;
import libreria.servicios.AutorService;
import libreria.servicios.EditorialService;
import libreria.servicios.LibroService;

/**
 *
 * @author Matias Luca Soto
 */
public class MenuLibro {

    //ATRIBUTOS
    private final Scanner sc;
    private final LibroService libroService;
    private final AutorService autorService;
    private final EditorialService editorialService;

    //CONSTRUCTOR
    public MenuLibro() {
        this.sc = new Scanner(System.in).useDelimiter("\n");
        this.libroService = new LibroService();
        this.autorService = new AutorService();
        this.editorialService = new EditorialService();
    }

    //MÉTODOS
    /**
     * Método para ingresar todos los atributos de un nuevo Objeto Libro, auto-generar la cantidad de ejemplares y persistirlo en la Base de Datos
     */
    protected void ingresarLibro() {

        try {

            //INPUT DE LOS VALORES DEL NUEVO OBJETO LIBRO
            System.out.println("Ingrese el título del libro:");
            String nombre = sc.next();
            System.out.println("Ingrese el año del libro:");
            Integer anio = sc.nextInt();
            System.out.println("Ingrese el código ISBN:");
            Long isbn = sc.nextLong();

            //N° DE EJEMPLARES GENERADOS ALEATORIAMENTE
            Integer ejemplares = (int) (Math.random() * 9 + 1);
            Integer ejemplaresPrestados = (int) (Math.random() * ejemplares);
            Integer ejemplaresRestantes = ejemplares - ejemplaresPrestados;

            //INGRESAR AUTOR
            /* Si no hay autores, se pide que se ingrese el primero,
            sino se imprime una lista para seleccionar de los ya existentes o ingresar uno nuevo*/
            String nombreAutor;
            if (autorService.listarAutores().isEmpty()) {
                System.out.println("Ingrese el nombre del autor:");
                nombreAutor = sc.next();
            } else {
                nombreAutor = seleccionarAutor();
            }

            //INGRESAR EDITORIAL
            //Same logic as Autor applies here
            String nombreEditorial;
            if (editorialService.listarEditoriales().isEmpty()) {
                System.out.println("Ingrese la editorial:");
                nombreEditorial = sc.next();
            } else {
                nombreEditorial = seleccionarEditorial();
            }

            //LLAMADO AL METODO SERVICE
            libroService.guardarLibro(isbn, nombre, anio, ejemplares, ejemplaresPrestados, ejemplaresRestantes, nombreAutor, nombreEditorial);

            //OUTPUT DE CONFIRMACIÓN
            System.out.println("¡Libro añadido con éxito!");

        } catch (AutorException | EditorialException | LibroException e) {
            System.out.println("Ups! Algo salio mal..."
                    + "\n" + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error desconocido creando el Libro");
            e.printStackTrace();
            System.out.println("Información del mensaje: " + e.getMessage());
        }

    }

    /**
     * Método para imprimir una lista con todos los Libros en la BD para seleccionar uno e ingresar nuevos atributos para el Libro seleccionado y enviarlos a actualizar en la BD
     */
    protected void modificarLibro() {

        try {

            //LISTADO DE LOS LIBROS EXISTENTES
            List<Libro> libros = libroService.listarLibros();

            //SI NO HAY LIBROS, SE MUESTRA UN MENSAJE. SI HAY LIBROS, SE INICIA EL MÉTODO
            if (libros.isEmpty()) {
                System.out.println("No hay libros guardados para modificar."
                        + "\n¡Empieza agregando uno!");
            } else {

                //Output de todos los libros guardados
                int counter = 1;
                System.out.println("LIBROS GUARDADOS:");
                for (Libro libro : libros) {
                    System.out.println(counter + ". " + libro.toString());
                    counter++;
                }

                //Input del Libro a modificar
                int opc;
                do {
                    System.out.println("Ingrese el número del Libro que desea modificar:");
                    opc = sc.nextInt();
                } while (opc < 1 || opc > libros.size());
                Libro libro = libros.get(opc - 1);

                //Input de los nuevos atributos para ese Libro
                System.out.println("A continuación, ingrese los nuevos valores para el libro elegido");
                System.out.print("Titulo: ");
                String nuevoTitulo = sc.next();
                System.out.print("ISBN: ");
                Long nuevoIsbn = sc.nextLong();
                System.out.print("Año: ");
                Integer nuevoAnio = sc.nextInt();
                System.out.print("Ejemplares totales: ");
                Integer nuevoEjemplares = sc.nextInt();

                //Bucle Do-While para solo ingresar una cantidad de ejemplares prestados menor o igual al total
                Integer nuevoPrestados;
                do {
                    System.out.print("Ejemplares prestados(debe ser menor igual al total ingresado):");
                    nuevoPrestados = sc.nextInt();
                } while (nuevoPrestados > nuevoEjemplares);

                //Calculo de los ejemplares restantes
                Integer nuevoRestantes = nuevoEjemplares - nuevoPrestados;

                //Selección del nombre del Autor
                String nombreAutor = seleccionarAutor();

                //Seleccion del nombre de la editorial
                String nombreEditorial = seleccionarEditorial();

                //Llamado al método del LibroService que conectará con el DAO
                libroService.modificarLibro(libro, nuevoIsbn, nuevoTitulo, nuevoAnio, nuevoEjemplares, nuevoPrestados, nuevoRestantes, nombreAutor, nombreEditorial);

                //Output de confirmación
                System.out.println("¡Libro modificado con éxito!");
            }

        } catch (AutorException | EditorialException | LibroException e) {
            System.out.println("Ups! Algo salio mal..."
                    + "\n" + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error desconocido modificando el Libro");
            e.printStackTrace();
            System.out.println("Información del mensaje: " + e.getMessage());
        }
    }

    /**
     * Método para seleccionar un Libro entre todos los de la BS y eliminarlo
     */
    protected void eliminarLibroIsbn() {

        try {

            //LISTADO DE TODOS LOS LIBROS
            List<Libro> libros = libroService.listarLibros();

            //SI NO HAY LIBROS, SE MUESTRA UN MENSAJE. SINO, SE INICIA EL MÉTODO
            if (libros.isEmpty()) {
                System.out.println("No hay libros guardados para eliminar."
                        + "\n¡Empieza agregando uno!");
            } else {

                //OUTPUT DE TODOS LOS LIBROS CON SU ISBN Y NOMBRE
                System.out.println("LISTADO DE LIBROS:");
                for (Libro libro : libros) {
                    System.out.println("ISBN: " + libro.getIsbn() + " / '" + libro.getTitulo() + "'");
                }
                
                //INPUT DEL LIBRO A ELIMINAR SEGUN ISBN
                System.out.println("\nIngrese el código ISBN del libro a eliminar:");
                Long opc = sc.nextLong();

                //METODO SERVICE PARA ELIMINAR EL LIBRO
                libroService.eliminarLibroIsbn(opc);

                //OUTPUT DE CONFIRMACIÓN
                System.out.println("\n¡Libro eliminado con éxito!");

            }

        } catch (LibroException e) {
            System.out.println("Ups! Algo salio mal..."
                    + "\n" + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error desconocido eliminando el Libro");
            e.printStackTrace();
            System.out.println("Información del mensaje: " + e.getMessage());
        }

    }

    /**
     * Pide el ingreso de un código ISBN y muestra los atributos del Libro asociado a ese ISBN. Si no lo encuentra, larga una excepción y lo muestra con un mensaje
     */
    protected void buscarLibroPorIsbn() {

        try {

            //INPUT DEL CÓDIGO ISBN
            System.out.print("Ingrese el código ISBN del libro a buscar:"
                    + "\nISBN: ");
            Long isbn = sc.nextLong();

            //LLAMADA AL SERVICE
            Libro libro = libroService.buscarLibroPorIsbn(isbn);

            //OUTPUT DEL RESULTADO
            System.out.println("\nDATOS DEL LIBRO ENCONTRADO:"
                    + "\n" + libro.toString());

            //USO DEL NullPointerException PARA MOSTRAR QUE NO SE ENCONTRÓ NINGUN RESULTADO
        } catch (NullPointerException e) {
            System.out.println("Ups! No se ha encontrado ningún libro con el ISBN ingresado."
                    + "\n¡Vuelva a intentarlo!");
        } catch (Exception e) {
            System.out.println("Error desconocido buscando Libro por ISBN");
            e.printStackTrace();
            System.out.println("Información del mensaje: " + e.getMessage());
        }

    }

    /**
     * Busca un Libro según el título ingresado por el usuario y muestra sus atributos en caso de encontrarlo.
     */
    protected void buscarLibroPorTitulo() {

        try {

            //INPUT DEL TITULO DEL LIBRO A BUSCAR
            System.out.print("Ingrese el título del libro a buscar:"
                    + "\nTITULO: ");
            String titulo = sc.next();

            //LLAMADO AL SERIVCE PARA QUE BUSQUE EL LIBRO
            Libro libro = libroService.buscarLibroPorTitulo(titulo);

            //OUTPUT DEL RESULTADO FINAL
            System.out.println("\nDATOS DEL LIBRO ENCONTRADO:"
                    + "\n" + libro.toString());

            //USO DEL LibroException PARA VERIFICAR EN EL SERVICE SI ESTA NULO Y MOSTRAR EN ESTA CAPA EL MENSAJE
        } catch (LibroException e) {
            System.out.println("Ups! Algo salio mal..."
                    + "\n" + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error desconocido buscando Libros por Titulo");
            e.printStackTrace();
            System.out.println("Información del mensaje: " + e.getMessage());
        }

    }

    /**
     * Busca uno o más Libros según el Autor ingresado por el usuario y muestra sus atributos en caso de encontrarlo/s.
     */
    protected void buscarLibrosPorAutor() {

        try {

            //INPUT DEL NOMBRE DEL AUTOR
            System.out.print("Ingrese el nombre del Autor a buscar:"
                    + "\nAUTOR: ");
            String nombre = sc.next();

            //LLAMADA AL SERVICE QUE TRAERÁ 1 O MÁS LIBROS
            List<Libro> libros = libroService.buscarLibrosPorAutor(nombre);

            //OUTPUT DEL RESULTADO
            int counter = 1;
            System.out.println("LIBRO/S ENCONTRADO/S:");
            for (Libro libro : libros) {
                System.out.println(counter + ". " + libro.toString());
                counter++;
            }

        } catch (LibroException e) {
            System.out.println("Ups! Algo salio mal..."
                    + "\n" + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error desconocido buscando Libros por Autor");
            e.printStackTrace();
            System.out.println("Información del mensaje: " + e.getMessage());
        }

    }

    /**
     * Busca uno o más Libros según la Editorial ingresada por el usuario y muestra sus atributos en caso de encontrarlo/s.
     */
    protected void buscarLibrosPorEditorial() {

        try {

            //INPUT DE LA EDITORIAL 
            System.out.print("Ingrese el nombre de la Editorial a buscar:"
                    + "\nEDITORIAL: ");
            String nombre = sc.next();

            //LLAMADO A LA EDITORIAL PARA GUARDAR TODOS LOS LIBROS QUE CUMPLAN LA CONDICIÓN
            List<Libro> libros = libroService.buscarLibrosPorEditorial(nombre);

            //OUTPUT DE LOS RESULTADOS
            int counter = 1;
            System.out.println("LIBRO/S ENCONTRADO/S:");
            for (Libro libro : libros) {
                System.out.println(counter + ". " + libro.toString());
                counter++;
            }

        } catch (LibroException e) {
            System.out.println("Ups! Algo salio mal..."
                    + "\n" + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error desconocido buscando Libros por Editorial");
            e.printStackTrace();
            System.out.println("Información del mensaje: " + e.getMessage());
        }

    }

    /**
     * Muestra por consola todos los Libros dentro de la BD. Si no hay elementos, se avisa con un mensaje
     */
    protected void imprimirLibros() {

        try {

            //MÉTODO SERVICE QUE DEVUELVE UNA LISTA CON TODOS LOS LIBROS
            libroService.imprimirLibros();

        } catch (Exception e) {
            System.out.println("Error desconocido listando todos los Libros");
            e.printStackTrace();
            System.out.println("Información del mensaje: " + e.getMessage());
        }

    }

    /**
     * Método para seleccionar un Autor ya existente en la BD o ingresar uno nuevo y devuelver su nombre. Se imprime por consola un listado con todos los Autores existentes y una opción extra para ingresar uno nuevo. Dependiendo lo que ingrese el usuario, el nombre del Autor seleccionado será devuelto por el método.
     *
     * @return Nombre de un Autor ya existente o uno nuevo, a elección del Usuario
     */
    private String seleccionarAutor() {

        try {
            //Si ya hay autores, guardo la cantidad de estos, los imprimo y doy la opción de que se ingrese uno nuevo
            String nombreAutor;
            int cantAutores = autorService.listarAutores().size();
            autorService.imprimirAutores();
            System.out.println((cantAutores + 1) + ". Ingresar un nuevo autor");

            //Tomo lo que el usuario desea seleccionar
            int opc;
            do {
                opc = sc.nextInt();
            } while (opc < 1 || opc > (cantAutores + 1));

            /*Si quiere crear un nuevo autor, le pido que lo ingrese.
            Si selecciona uno ya existente, seteo su nombre en el String*/
            if (opc == cantAutores + 1) {
                System.out.println("Ingrese el nombre del nuevo autor:");
                nombreAutor = sc.next();
            } else {
                nombreAutor = autorService.listarAutores().get(opc - 1).getNombre();
            }
            return nombreAutor;

        } catch (AutorException e) {
            System.out.println("Ups! Algo salio mal..."
                    + "\n" + e.getMessage());
            return "Autor no cargado.";
        } catch (Exception e) {
            System.out.println("Error desconocido seleccionando un Autor");
            e.printStackTrace();
            System.out.println("Información del mensaje: " + e.getMessage());
            return "Autor no cargado.";
        }
    }

    //MÉTODOS
    /**
     * Método para seleccionar una Editorial ya existente en la BD o ingresar uno nuevo y devuelver su nombre. Se imprime por consola un listado con todos los Autores existentes y una opción extra para ingresar uno nuevo. Dependiendo lo que ingrese el usuario, el nombre de la Editorial seleccionado será devuelto por el método.
     *
     * @return Nombre de una Editorial ya existente o una nueva, a elección del usuario
     */
    private String seleccionarEditorial() {

        try {

            String nombreEditorial;
            int cantEditoriales = editorialService.listarEditoriales().size(); // CANTIDAD DE EDITORIALES

            //OUTPUT DE UN LISTADO CON TODAS LAS EDITORIALES
            editorialService.imprimirEditoriales();
            System.out.println((cantEditoriales + 1) + ". Ingresar una nueva editorial");

            //INPUT DEL USUARIO
            int opc;
            do {
                opc = sc.nextInt();
            } while (opc < 1 || opc > (cantEditoriales + 1));

            //SI ELEGIÓ INGRESAR UNA NUEVA EDITORIAL, PIDO SU NOMBRE. SINO, SETEO EL QUE SELECCIONÓ DE LA LISTA
            if (opc == cantEditoriales + 1) {
                System.out.println("Ingrese la nueva editorial:");
                nombreEditorial = sc.next();
            } else {
                nombreEditorial = editorialService.listarEditoriales().get(opc - 1).getNombre(); // GUARDO EL NOMBRE DE LA EDITORIAL SELECCIONADA
            }
            return nombreEditorial;

        } catch (EditorialException e) {
            System.out.println("Ups! Algo salió mal..."
                    + "\n" + e.getMessage());
            return "Editorial no cargada.";
        } catch (Exception e) {
            System.out.println("Error desconocido creando la Editorial");
            e.printStackTrace();
            System.out.println("Información del mensaje: " + e.getMessage());
            return "Editorial no cargada.";
        }

    }

}
