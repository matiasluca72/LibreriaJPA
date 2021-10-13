package libreria.servicios;

import java.util.List;
import java.util.UUID;
import libreria.entidades.Autor;
import libreria.exceptions.AutorException;
import libreria.persistencia.AutorDAO;

/**
 *
 * @author Matias Luca Soto
 */
public class AutorService {

    //ATRIBUTO DAO
    private final AutorDAO dao;

    //CONSTRUCTOR
    public AutorService() {
        this.dao = new AutorDAO();
    }

    //MÉTODOS
    /**
     * Recibe por parámetro el atributo 'nombre' del Objeto Autor, realiza verificaciones y, si todo está correcto, setea los atributos para después persistirlo en la BD
     *
     * @param nombre Nombre del nuevo Autor a crear
     * @return El Objeto Autor ya creado y persistido
     * @throws AutorException Si el argumento está o si ya existe un Autor con ese nombre
     */
    public Autor crearAutor(String nombre) throws AutorException {

        try {

            //VALIDACIONES
            if (nombre.trim().isEmpty()) {
                throw new AutorException("El nombre del autor está vacio.");
            }
            if (buscarAutorPorNombre(nombre) != null) {
                throw new AutorException("El nombre ingresado ya está guardado en la base de datos.");
            }

            //ARMADO DEL OBJETO AUTOR
            Autor autor = new Autor();
            autor.setId(UUID.randomUUID().toString()); // Generación de Primary Key
            autor.setNombre(nombre);
            autor.setAlta(true);

            //GUARDADO EN BASE DE DATOS
            dao.guardarAutor(autor);
            return autor;

        } catch (AutorException e) {
            throw e;
        } catch (Exception e) {
            System.out.println("Error guardando Autor en AutorService");
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Recibe por parámetro el Objeto Autor a modificar y su nuevo atributo, realiza verificaciones y, si todo está correcto, setea los atributos para después persistirlo en la BD
     * @param autor Autor a modificar
     * @param nombre Nuevo atributo a setear
     * @throws AutorException Si el Autor es igual a null, si el nombre está vacío o si ya está seteado en otro Autor
     */
    public void modificarAutor(Autor autor, String nombre) throws AutorException {

        try {

            //VALIDACIONES
            if (autor == null) {
                throw new AutorException("El autor a modificar está vacío");
            }
            if (nombre.trim().isEmpty()) {
                throw new AutorException("No ha seleccionado un nombre nuevo para el autor");
            }
            if (buscarAutorPorNombre(nombre) != null) {
                throw new AutorException("El nombre ingresado ya pertenece a otro autor.");
            }

            //SETEO LOS NUEVOS VALORES PARA EL AUTOR
            autor.setNombre(nombre);
            autor.setAlta(true);

            //ENVIO EL AUTOR ACTUALIZADO AL DAO
            dao.modificarAutor(autor);

        } catch (AutorException e) {
            throw e;
        } catch (Exception e) {
            System.out.println("Error modificando el Autor en AutorService");
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Elimina el Objeto Autor dentro de la BD con el mismo String ID que se reciba como argumento
     * @param id ID del Objeto Autor a eliminar de la Base de Datos
     * @throws AutorException Si no se encuentra ningún Autor con ese ID
     */
    public void eliminarAutorId(String id) throws AutorException {

        try {

            //VALIDACIONES
            if (dao.buscarAutorPorId(id) == null) {
                throw new AutorException("No existe un autor con ese ID");
            }

            //Eliminamos el Autor en el DAO
            dao.eliminarAutorId(id);

        } catch (AutorException e) {
            throw e;
        } catch (Exception e) {
            System.out.println("Error eliminando el Autor en AutorService");
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Busca un Objeto Autor en la BD con el atributo 'nombre' que sea igual al pasado por argumento
     * @param nombre Nombre del Autor a buscar
     * @return El Autor de ser encontrado. Sino, devuelve null
     * @throws AutorException Si el argumento está vacío
     */
    public Autor buscarAutorPorNombre(String nombre) throws AutorException {

        try {

            //VALIDACION
            if (nombre.trim().isEmpty()) {
                throw new AutorException("El nombre del autor está vacío");
            }
            return dao.buscarAutorPorNombre(nombre);

        } catch (AutorException e) {
            throw e;
        } catch (Exception e) {
            System.out.println("Error buscando un Autor por el nombre en AutorService");
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Devuelve una Lista con todos los Objetos Autores guardados en la Base de Datos
     * @return Lista con todos los Autores en la BD
     */
    public List<Autor> listarAutores() {
        try {

            return dao.listarAutores();

        } catch (Exception e) {
            System.out.println("Error listando autores en AutorService");
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Imprime por consola un listado con todos los Autores en la BD, enumerados y con sus nombres
     * @throws AutorException Si no hay autores para mostrar.
     */
    public void imprimirAutores() throws AutorException {

        try {

            List<Autor> autores = listarAutores();

            if (autores.isEmpty()) {
                throw new AutorException("No hay autores para mostrar.");
            }

            int counter = 1;
            System.out.println("\nLISTA DE AUTORES:");
            for (Autor autor : autores) {
                System.out.println(counter + ". " + autor.getNombre());
                counter++;
            }

        } catch (AutorException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }
}
