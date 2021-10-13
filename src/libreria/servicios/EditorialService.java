package libreria.servicios;

import java.util.List;
import java.util.UUID;
import libreria.entidades.Editorial;
import libreria.exceptions.EditorialException;
import libreria.persistencia.EditorialDAO;

/**
 *
 * @author Matias Luca Soto
 */
public class EditorialService {

    //ATRIBUTO DAO
    private final EditorialDAO dao;

    //CONSTRUCTOR
    public EditorialService() {
        this.dao = new EditorialDAO();
    }

    //MÉTODOS
    /**
     * Recibe por parámetro el atributo 'nombre' del Objeto Editorial, realiza verificaciones y, si todo está correcto, setea los atributos para después persistirlo en la BD
     *
     * @param nombre Atributo del nuevo Objeto Autor
     * @return La Editorial ya seteada y persistida
     * @throws EditorialException Si el argumento está vacío o ya existe una Editorial con ese nombre
     */
    public Editorial crearEditorial(String nombre) throws EditorialException {

        try {

            //VALIDACIONES
            if (nombre.trim().isEmpty()) {
                throw new EditorialException("El nombre de la Editorial está vacio.");
            }
            if (buscarEditorialPorNombre(nombre) != null) {
                throw new EditorialException("El nombre ingresado ya está guardado en la base de datos.");
            }

            //ARMADO DEL OBJETO EDITORIAL
            Editorial editorial = new Editorial();
            editorial.setId(UUID.randomUUID().toString()); // Generación de Primary Key
            editorial.setNombre(nombre);
            editorial.setAlta(true);

            //GUARDANDO EN BASE DE DATOS
            dao.guardarEditorial(editorial);
            return editorial;

        } catch (EditorialException e) {
            throw e;
        } catch (Exception e) {
            System.out.println("Error creando Editorial en Editorial Service");
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Recibe por parámetro el Objeto Editorial a modificar y su nuevo atributo, realiza verificaciones y, si todo está correcto, setea los atributos para después persistirlo en la BD
     *
     * @param editorial Editorial a modificar
     * @param nombre Nuevo atributo a setear
     * @throws EditorialException Si los argumentos están vacíos o el nombre ya pertenece a otra Editorial
     */
    public void modificarEditorial(Editorial editorial, String nombre) throws EditorialException {

        try {

            //VALIDACIONES
            if (editorial == null) {
                throw new EditorialException("La editorial a modificar está vacío");
            }
            if (nombre.trim().isEmpty()) {
                throw new EditorialException("No ha seleccionado ningún nombre nuevo para la editorial");
            }
            if (buscarEditorialPorNombre(nombre) != null) {
                throw new EditorialException("El nombre ingresado ya pertenece a otra editorial.");
            }

            //SETEO LOS NUEVOS VALORES PARA EL AUTOR
            editorial.setNombre(nombre);
            editorial.setAlta(true);

            //ENVIO EL AUTOR ACTUALIZADO AL DAO
            dao.modificarEditorial(editorial);

        } catch (EditorialException e) {
            throw e;
        } catch (Exception e) {
            System.out.println("Error modificando Editorial en Editorial Service");
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Elimina el Objeto Editorial dentro de la BD con el mismo String ID que se reciba como argumento
     *
     * @param id ID de la Editorial a eliminar
     * @throws EditorialException Si no se encuentra una Editorial asociada a ese ID
     */
    public void eliminarEditorialPorId(String id) throws EditorialException {

        try {

            //VALIDACIONES
            if (dao.buscarEditorialPorId(id) == null) {
                throw new EditorialException("No existe editorial con ese ID");
            }

            //ELIMINAMOS LA EDITORIAL EN EL DAO
            dao.eliminarEditorialPorId(id);

        } catch (EditorialException e) {
            throw e;
        } catch (Exception e) {
            System.out.println("Error eliminando Editorial en Editorial Service");
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Busca un Objeto Editorial en la BD con el atributo 'nombre' que sea igual al pasado por argumento
     *
     * @param nombre Nombre de la Editorial a buscar
     * @return El Objeto Editorial buscado de ser encontrado. Sino, null
     * @throws EditorialException Si el argumento está vacío
     */
    public Editorial buscarEditorialPorNombre(String nombre) throws EditorialException {

        try {

            //VALIDACION
            if (nombre.trim().isEmpty()) {
                throw new EditorialException("El nombre de la Editorial a buscar está vacío.");
            }

            return dao.buscarEditorialPorNombre(nombre);

        } catch (EditorialException e) {
            throw e;
        } catch (Exception e) {
            System.out.println("Error buscando una Editorial en Editorial Service");
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Devuelve una Lista con todos los Objetos Autores guardados en la Base de Datos
     *
     * @return Lista con todas las Editoriales de la BD
     */
    public List<Editorial> listarEditoriales() {

        try {

            return dao.listarEditoriales();

        } catch (Exception e) {
            System.out.println("Error listando Editoriales en Editorial Service");
            e.printStackTrace();
            throw e;
        }

    }

    /**
     * Imprime por consola un listado con todas las Editoriales en la BD, enumerados y con sus nombres
     *
     * @throws EditorialException Si no hay editoriales para mostrar
     */
    public void imprimirEditoriales() throws EditorialException {

        try {

            List<Editorial> editoriales = listarEditoriales();

            if (editoriales.isEmpty()) {
                throw new EditorialException("No hay editoriales para mostrar.");
            }

            int counter = 1;
            System.out.println("\nLISTA DE EDITORIALES");
            for (Editorial editorial : editoriales) {
                System.out.println(counter + ". " + editorial.getNombre());
                counter++;
            }

        } catch (EditorialException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

}
