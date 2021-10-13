package libreria.menu;

import java.util.List;
import java.util.Scanner;
import libreria.entidades.Editorial;
import libreria.exceptions.EditorialException;
import libreria.servicios.EditorialService;

/**
 *
 * @author Matias Luca Soto
 */
public class MenuEditorial {

    //ATRIBUTOS
    private final Scanner sc;
    private final EditorialService editorialService;

    //CONSTRUCTOR
    public MenuEditorial() {
        this.sc = new Scanner(System.in).useDelimiter("\n");
        this.editorialService = new EditorialService();
    }

    /**
     * Pide el ingreso de un nombre para la creación de una Editorial y se lo envia al Service para que lo cree
     */
    protected void crearEditorial() {

        try {

            //INPUT
            System.out.println("Ingrese el nombre de la nueva Editorial:");
            String nombre = sc.next();

            //MÉTODO SERVICE
            editorialService.crearEditorial(nombre);

            //OUTPUT DE CONFIRMACIÓN
            System.out.println("¡Editorial ingresada con éxito!");

        } catch (EditorialException e) {
            System.out.println("Ups! Algo salió mal..."
                    + "\n" + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error desconocido creando la Editorial");
            e.printStackTrace();
            System.out.println("Información del mensaje: " + e.getMessage());
        }

    }

    /**
     * Verifica que hayan Editoriales. Si las hay, se listan y se da la opción de elegir una a modificar, luego se pide un nuevo nombre y se envian los datos como argumentos al Service para que aplique la actualización
     */
    protected void modificarEditorial() {

        try {

            //LISTADO DE LAS EDITORIALES EXISTENTES
            List<Editorial> editoriales = editorialService.listarEditoriales();

            //SI NO HAY EDITORIALES, SE MUESTRA UN MENSAJE. SI HAY, SE INICIA EL MÉTODO
            if (editoriales.isEmpty()) {
                System.out.println("No hay editoriales guardados para modificar."
                        + "\n¡Empieza añadiendo algunas!");
            } else {

                //OUTPUT DE TODAS LAS EDITORIALES GUARDADAS
                editorialService.imprimirEditoriales();

                //INPUT DE LA EDITORIAL A MODIFICAR
                int opc;
                do {
                    System.out.println("Ingrese el número de la Editorial que desea modificar:");
                    opc = sc.nextInt();
                } while (opc < 1 || opc > editoriales.size());
                Editorial editorial = editoriales.get(opc - 1);

                //INPUT DE LOS NUEVOS ATRIBUTOS PARA ESTA EDITORIAL
                System.out.println("Ingrese el nuevo nombre para la Editorial:");
                String nombre = sc.next();

                //LLAMADO AL MÉTODO SERVICE
                editorialService.modificarEditorial(editorial, nombre);

                //OUTPUT DE CONFIRMACIÓN
                System.out.println("¡Editorial modificada con éxito!");

            }

        } catch (EditorialException e) {
            System.out.println("Ups! Algo salió mal..."
                    + "\n" + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error desconocido modificando la Editorial");
            e.printStackTrace();
            System.out.println("Información del mensaje: " + e.getMessage());
        }
    }

    /**
     * Muestra un listado de todos las Editoriales que haya en la Base de Datos y pide al usuario que ingrese una opción de Editorial a eliminar. Si no hay editoriales, se muestra un mensaje por pantalla
     */
    protected void eliminarEditorial() {

        try {

            //LISTADO DE LAS EDITORIALES EXISTENTES
            List<Editorial> editoriales = editorialService.listarEditoriales();

            //SI NO HAY EDITORIALES, SE MUESTRA UN MENSAJE. SI HAY, SE INICIA EL MÉTODO
            if (editoriales.isEmpty()) {
                System.out.println("No hay editoriales guardadas para eliminar."
                        + "\n¡Empieza añadiendo algunas!");
            } else {

                //OUTPUT DE TODAS LAS EDITORIALES GUARDADAS
                editorialService.imprimirEditoriales();

                //INPUT DE LA EDITORIAL A ELIMINAR
                int opc;
                do {
                    System.out.println("Ingrese el número de la Editorial que desea eliminar:");
                    opc = sc.nextInt();
                } while (opc < 1 || opc > editoriales.size());
                Editorial editorial = editoriales.get(opc - 1);

                //LLAMADO AL MÉTODO SERVICE
                editorialService.eliminarEditorialPorId(editorial.getId());

                //OUTPUT DE CONFIRMACIÓN
                System.out.println("¡Editorial eliminada con éxito!"
                        + "\n(¡expropiese!)");

            }

        } catch (EditorialException e) {
            System.out.println("Ups! Algo salió mal..."
                    + "\n" + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error desconocido eliminando la Editorial");
            e.printStackTrace();
            System.out.println("Información del mensaje: " + e.getMessage());
        }

    }

    /**
     * Pide al usuario un nombre y se lo busca en la Base de datos. Si se lo encuentra, se muestra su toString(). Sino, se avisa por mensaje que no fue encontrado
     */
    protected void buscarEditorialPorNombre() {

        try {

            //INPUT DEL NOMBRE A BUSCAR
            System.out.print("Ingrese el nombre de la editorial a buscar:"
                    + "\nNOMBRE: ");
            String nombre = sc.next();

            //BÚSQUEDA EN LA BASE DE DATOS
            Editorial editorial = editorialService.buscarEditorialPorNombre(nombre);

            //VERIFICACIÓN
            if (editorial == null) {
                throw new EditorialException("No se encontró ninguna editorial con ese nombre.");
            }

            //OUTPUT DEL RESULTADO
            System.out.println("\nEDITORIAL ENCONTRADA:"
                    + "\n" + editorial.toString());

        } catch (EditorialException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error desconocido buscando la Editorial por nombre");
            e.printStackTrace();
            System.out.println("Información del mensaje: " + e.getMessage());
        }

    }

    /**
     * Imprime por consola todos los Objetos Editorial con sus atributos que estén guardados en la BD
     */
    protected void imprimirEditoriales() {

        try {
            //METODO SERVICE
            editorialService.imprimirEditoriales();
        } catch (EditorialException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error desconocido listando editoriales");
            e.printStackTrace();
            System.out.println("Información del mensaje: " + e.getMessage());
        }

    }

}
