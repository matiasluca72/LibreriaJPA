package libreria.menu;

import java.util.Scanner;

/**
 *
 * @author Matias Luca Soto
 */
public class Menu {

    //ATRIBUTOS
    private final Scanner sc;
    private final MenuLibro menuLibro;
    private final MenuAutor menuAutor;
    private final MenuEditorial menuEditorial;

    //CONSTRUCTOR
    public Menu() {
        this.sc = new Scanner(System.in).useDelimiter("\n");
        this.menuLibro = new MenuLibro();
        this.menuAutor = new MenuAutor();
        this.menuEditorial = new MenuEditorial();
    }

    //MÉTODOS
    /**
     * Ejecución principal de toda la aplicación. Ejecuta una combinación de menues para poder crear, modificar, eliminar y consultar los datos guardados en la Base de Datos de las Entidades Libro, Autor y Editorial, junto con todas sus relaciones.
     */
    public void iniciarEjecucion() {

        //Aux
        int ejecucion;
        do {
            //Ejecución Principal
            ejecucion = menuPrincipal();
        } while (ejecucion != 4); //Condición de salida

    }

    /**
     * Ejecuta el menu principal de la aplicación.
     *
     * @return Opción elegida por el usuario
     */
    private int menuPrincipal() {

        //OUTPUT DEL MENÚ
        System.out.println("\n------------ MENÚ ------------"
                + "\n1. Menú LIBRO"
                + "\n2. Menú AUTOR"
                + "\n3. Menú EDITORIAL"
                + "\n4. Salir"
                + "\nSeleccione el menú que desea ver");

        //CONDICIONAL MÚLTIPLE PARA MOSTRAR EL SUBMENÚ CORRESPONDIENTE
        int opc = elegirOpcion(4);
        switch (opc) {
            case 1:
                menuLibro();
                break;
            case 2:
                menuAutor();
                break;
            case 3:
                menuEditorial();
                break;
            case 4:
                System.out.println("\n¡Adios!");
        }
        //SELECCIÓN DEL USUARIO PARA AVISAR CUANDO TERMINE
        return opc;
    }

    /**
     * Menú principal de la Entidad Libro. Desde acá se ejecuta su CRUD (Create, Remove, Update & Delete)
     */
    private void menuLibro() {

        //OUTPUT DEL MENÚ LIBRO
        System.out.println("\n------------ MENÚ LIBRO ------------"
                + "\n1. Ingresar un nuevo libro"
                + "\n2. Modificar un libro existente"
                + "\n3. Eliminar un libro"
                + "\n4. Buscar libro por ISBN"
                + "\n5. Buscar libro por título"
                + "\n6. Buscar libro/s por autor"
                + "\n7. Buscar libro/s por editorial"
                + "\n8. Listar todos los libros"
                + "\n9. Salir");

        //CONDICIONAL MÚLTIPLE PARA EJECUTAR EL MÉTODO CORRESPONDIENTE
        switch (elegirOpcion(9)) {
            case 1:
                menuLibro.ingresarLibro(); //OK
                break;
            case 2:
                menuLibro.modificarLibro(); //OK
                break;
            case 3:
                menuLibro.eliminarLibroIsbn(); //OK
                break;
            case 4:
                menuLibro.buscarLibroPorIsbn(); //OK
                break;
            case 5:
                menuLibro.buscarLibroPorTitulo(); //OK
                break;
            case 6:
                menuLibro.buscarLibrosPorAutor(); //OK
                break;
            case 7:
                menuLibro.buscarLibrosPorEditorial(); //OK
                break;
            case 8:
                menuLibro.imprimirLibros(); //OK 
                break;
            case 9:
                System.out.println("Volviendo al menú anterior...");
        }
    }

    /**
     * Menú principal de la Entidad Autor. Desde acá se ejecuta su CRUD (Create, Remove, Update & Delete)
     */
    private void menuAutor() {

        //OUTPUT DEL MENÚ AUTOR
        System.out.println("\n------------ MENÚ AUTOR ------------"
                + "\n1. Ingresar un nuevo autor"
                + "\n2. Modificar un autor existente"
                + "\n3. Eliminar un autor"
                + "\n4. Buscar autor por nombre"
                + "\n5. Listar todos los autores"
                + "\n6. Salir");

        //CONDICIONAL MÚLTIPLE PARA EJECUTAR EL MÉTODO CORRESPONDIENTE
        switch (elegirOpcion(6)) {
            case 1:
                menuAutor.crearAutor(); //OK
                break;
            case 2:
                menuAutor.modificarAutor(); //OK
                break;
            case 3:
                menuAutor.eliminarAutor(); //OK
                break;
            case 4:
                menuAutor.buscarAutorPorNombre(); //OK
                break;
            case 5:
                menuAutor.imprimirAutores(); //OK 
                break;
            case 6:
                System.out.println("Volviendo al menú anterior...");
        }
    }

    /**
     * Menú principal de la Entidad Editorial. Desde acá se ejecuta su CRUD (Create, Remove, Update & Delete)
     */
    private void menuEditorial() {

        //OUTPUT DEL MENÚ EDITORIAL
        System.out.println("\n------------ MENÚ EDITORIAL ------------"
                + "\n1. Ingresar una nueva editorial"
                + "\n2. Modificar una editorial existente"
                + "\n3. Eliminar una editorial"
                + "\n4. Buscar editorial por nombre"
                + "\n5. Listar todas las editoriales"
                + "\n6. Salir");

        //CONDICIONAL MÚLTIPLE PARA EJECUTAR EL MÉTODO CORRESPONDIENTE
        switch (elegirOpcion(6)) {
            case 1:
                menuEditorial.crearEditorial(); //OK
                break;
            case 2:
                menuEditorial.modificarEditorial(); //OK
                break;
            case 3:
                menuEditorial.eliminarEditorial(); //OK
                break;
            case 4:
                menuEditorial.buscarEditorialPorNombre(); //OK
                break;
            case 5:
                menuEditorial.imprimirEditoriales(); //OK 
                break;
            case 6:
                System.out.println("Volviendo al menú anterior...");
        }
    }

    /**
     * Método que devuelve una opción válida ingresada por el usuario como un int. La opción que ingrese el Usuario solo podrá ser entre 1 y el argumento recibido (ambos incluyentes).
     *
     * @param limite Opcion máxima que el usuario puede llegar a ingresar
     * @return Una opción válida ingresada por el usuario
     */
    private int elegirOpcion(int limite) {
        int opc;
        do {
            System.out.print("Ingrese su opción:");
            opc = sc.nextInt();
        } while (opc < 1 || opc > limite);
        return opc;
    }
}
