package libreria.menu;

import java.util.List;
import java.util.Scanner;
import libreria.entidades.Autor;
import libreria.exceptions.AutorException;
import libreria.servicios.AutorService;

/**
 *
 * @author Matias Luca Soto
 */
public class MenuAutor {

    //ATRIBUTOS
    private final Scanner sc;
    private final AutorService autorService;

    //CONSTRUCTOR
    public MenuAutor() {
        this.sc = new Scanner(System.in).useDelimiter("\n");
        this.autorService = new AutorService();
    }

    //MÉTODOS
    /**
     * Pide el ingreso de un nombre para la creación de un nuevo Autor y se lo envia al Service para que lo cree
     */
    protected void crearAutor() {

        try {

            //INPUT
            System.out.println("Ingrese el nombre del nuevo Autor:");
            String nombre = sc.next();

            //LLAMADA AL MÉTODO SERVICE
            autorService.crearAutor(nombre);

            System.out.println("¡Autor ingresado con éxito!");

        } catch (AutorException e) {
            System.out.println("Ups! Algo salio mal..."
                    + "\n" + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error desconocido creando el Autor");
            e.printStackTrace();
            System.out.println("Información del mensaje: " + e.getMessage());
        }
    }

    /**
     * Verifica que hayan autores. Si los hay, se los lista y se da la opción de elegir uno a modificar, luego se pide un nuevo nombre y se envian los datos como argumentos al Service para que aplique la actualización
     */
    protected void modificarAutor() {

        try {

            //LISTADO DE LOS AUTORES EXISTENTES
            List<Autor> autores = autorService.listarAutores();

            //SI NO HAY AUTORES, SE MUESTRA UN MENSAJE. SI HAY, SE INICIA EL MÉTODO
            if (autores.isEmpty()) {
                System.out.println("No hay autores guardados para modificar."
                        + "\n¡Empieza añadiendo algunos!");
            } else {

                //OUTPUT DE TODOS LOS LIBROS GUARDADOS
                autorService.imprimirAutores();

                //INPUT DE LOS AUTORES A MODIFICAR
                int opc;
                do {
                    System.out.println("Ingrese el número del Autor que desea modificar:");
                    opc = sc.nextInt();
                } while (opc < 1 || opc > autores.size());

                //TRAIGO EL AUTOR SELECCIONADO
                Autor autor = autores.get(opc - 1);

                //INPUT DE LOS NUEVOS ATRIBUTOS PARA ESTE AUTOR
                System.out.println("Ingrese el nuevo nombre para el Autor:");
                String nombre = sc.next();

                //LLAMADO AL MÉTODO SERVICE
                autorService.modificarAutor(autor, nombre);

                System.out.println("¡Autor modificado con éxito!");

            }

        } catch (AutorException e) {
            System.out.println("Ups! Algo salio mal..."
                    + "\n" + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error desconocido modificando un autor");
            e.printStackTrace();
            System.out.println("Información del mensaje: " + e.getMessage());
        }

    }

    /**
     * Muestra un listado de todos los autores que haya en la Base de Datos y pide al usuario que ingrese una opción del Autor a eliminar. Si no hay autores, se muestra un mensaje por pantalla
     */
    protected void eliminarAutor() {

        try {

            //LISTADO DE LOS AUTORES EXISTENTES
            List<Autor> autores = autorService.listarAutores();

            //SI NO HAY AUTORES, SE MUESTRA UN MENSAJE. SI HAY, SE INICIA EL MÉTODO
            if (autores.isEmpty()) {
                System.out.println("No hay autores guardados para eliminar."
                        + "\n¡Empieza añadiendo algunos!");
            } else {

                //OUTPUT DE TODOS LOS LIBROS GUARDADOS
                autorService.imprimirAutores();

                //INPUT DEL AUTOR A ELIMINAR
                int opc;
                do {
                    System.out.println("Ingrese el número del Autor que desea eliminar:");
                    opc = sc.nextInt();
                } while (opc < 1 || opc > autores.size());
                Autor autor = autores.get(opc - 1);

                //ELIMINACIÓN DEL AUTOR DE LA BASE DE DATOS
                autorService.eliminarAutorId(autor.getId());

                System.out.println("¡Autor eliminado con éxito!"
                        + "\n(congratulations, you're a murderer.)");

            }

        } catch (AutorException e) {
            System.out.println("Ups! Algo salio mal..."
                    + "\n" + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error desconocido eliminando un autor");
            e.printStackTrace();
            System.out.println("Información del mensaje: " + e.getMessage());
        }

    }

    /**
     * Pide al usuario un nombre y se lo busca en la Base de datos. Si se lo encuentra, se muestra su toString(). Sino, se avisa por mensaje que no fue encontrado
     */
    protected void buscarAutorPorNombre() {

        try {

            //INPUT DEL NOMBRE DEL AUTOR
            System.out.print("Ingrese el nombre del autor a buscar:"
                    + "\nNOMBRE: ");
            String nombre = sc.next();

            //SE BUSCA EL AUTOR EN LA BASE DE DATOS
            Autor autor = autorService.buscarAutorPorNombre(nombre);

            //VERIFICACIÓN
            if (autor == null) {
                throw new AutorException("No se encontró ningún autor con ese nombre.");
            }

            System.out.println("\nAUTOR ENCONTRADO:"
                    + "\n" + autor.toString());

        } catch (AutorException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error desconocido buscando el autor por nombre.");
            e.printStackTrace();
            System.out.println("Información del mensaje: " + e.getMessage());
        }

    }

    /**
     * Imprime por consola todos los Objetos Autores con sus atributos que estén guardados en la BD
     */
    protected void imprimirAutores() {

        try {
            autorService.imprimirAutores();
        } catch (AutorException e) {
            System.out.println("Ups! Algo salio mal..."
                    + "\n" + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error desconocido imprimiendo los autores");
            e.printStackTrace();
            System.out.println("Información del mensaje: " + e.getMessage());
        }

    }

}
