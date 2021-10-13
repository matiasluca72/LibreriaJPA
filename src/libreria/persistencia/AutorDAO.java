package libreria.persistencia;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import libreria.entidades.Autor;
import libreria.exceptions.AutorException;

/**
 *
 * @author Matias Luca Soto
 */
public class AutorDAO {

    //ATRIBUTOS
    private final EntityManagerFactory emf;
    private final EntityManager em;

    //CONSTRUCTOR
    public AutorDAO() {
        this.emf = Persistence.createEntityManagerFactory("LibreriaPU");
        this.em = emf.createEntityManager();
    }

    //MÉTODOS
    /**
     * Método void que recibe un Objeto Autor para persistirlo en la Base de Datos
     *
     * @param autor Objeto Autor ya seteado para persistir en la BD
     */
    public void guardarAutor(Autor autor) {
        try {
            em.getTransaction().begin();
            em.persist(autor);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error al guardar autor en el DAO");
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Método void que recibe un Objeto Autor actualizado para aplicarle un merge en la Base de Datos
     *
     * @param autor Objeto Autor con sus atributos actualizados para aplicar merge en la BD
     */
    public void modificarAutor(Autor autor) {
        try {
            em.getTransaction().begin();
            em.merge(autor);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error al modificar autor en el DAO");
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Método void que recibe el ID de un Autor en formato String para removerlo de la BD
     *
     * @param id Primary Key del Objeto a eliminar
     * @throws AutorException Si un autor no puede ser eliminado por estar asociado a un Libro existente
     */
    public void eliminarAutorId(String id) throws AutorException  {
        try {
            Autor autor = buscarAutorPorId(id);
            em.getTransaction().begin();
            em.remove(autor);
            em.getTransaction().commit();
        } catch (RollbackException e) {
            throw new AutorException("No se puede eliminar el autor por estar asociado a uno o más libros."
                    + "\nElimine los libros primeros para poder eliminar el autor");
        } catch (Exception e) {
            System.out.println("Error al eliminar autor en el DAO");
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Método que devuelve un Objeto Autor resultado de buscar un id pasado como parámetro en la BS. Utiliza el método .find(Tipo de Clase, PM) para encontrar un Objeto específico y devolverlo en el método
     * @param id Primary Key del Objeto Autor a buscar
     * @return El Autor buscado de ser encontrado.
     */
    public Autor buscarAutorPorId(String id) {
        try {
            Autor autor = em.find(Autor.class, id);
            return autor;
        } catch (Exception e) {
            System.out.println("Error al buscar autor por ID en el DAO");
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Método que busca un Autor en la BD según el nombre pasado como argumento y lo devuelve de ser encontrado. Crea una sentencia query de JPQA para buscar el atributo del Objeto.
     * @param nombre atributo del Autor a buscar
     * @return Objeto Autor de ser encontrado. Sino, null
     */
    public Autor buscarAutorPorNombre(String nombre) {
        try {
            Autor autor = (Autor) em.createQuery("SELECT a "
                    + "FROM Autor a "
                    + "WHERE a.nombre LIKE :nombre").
                    setParameter("nombre", nombre).
                    getSingleResult();
            return autor;
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            System.out.println("Error al buscar autor por nombre en el DAO");
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Método que devuelve una Lista con todos los Objetos de la tabla Autor en la base de datos.
     * @return Lista con todos los Autores en la BD
     */
    public List<Autor> listarAutores() {
        try {
            List<Autor> autores = em.createQuery("SELECT a FROM Autor a").
                    getResultList();
            return autores;
        } catch (Exception e) {
            System.out.println("Error al listar autores en el DAO");
            e.printStackTrace();
            throw e;
        }
    }

}
