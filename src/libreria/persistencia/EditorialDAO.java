package libreria.persistencia;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import libreria.entidades.Editorial;
import libreria.exceptions.EditorialException;

/**
 *
 * @author Matias Luca Soto
 */
public class EditorialDAO {

    //ATRIBUTOS
    private final EntityManagerFactory emf;
    private final EntityManager em;

    //CONSTRUCTOR
    public EditorialDAO() {
        this.emf = Persistence.createEntityManagerFactory("LibreriaPU");
        this.em = emf.createEntityManager();
    }

    //MÉTODOS
    /**
     * Método void que recibe un Objeto Editorial para persistirlo en la Base de Datos
     *
     * @param editorial
     */
    public void guardarEditorial(Editorial editorial) {
        try {
            em.getTransaction().begin();
            em.persist(editorial);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error al guardar editorial en el DAO");
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Método void que recibe un Objeto Editorial actualizado para aplicarle un merge en la Base de Datos
     *
     * @param editorial Objeto Editorial con sus atributos actualizados para aplicar merge en la BD
     */
    public void modificarEditorial(Editorial editorial) {
        try {
            em.getTransaction().begin();
            em.merge(editorial);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error al modificar editorial en el DAO");
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Método void que recibe el ID de una Editorial en formato String para removerlo de la BD
     *
     * @param id Primary Key del Objeto a eliminar
     * @throws EditorialException Si una Editorial no puede ser eliminada por estar asociado a un Libro existente
     */
    public void eliminarEditorialPorId(String id) throws EditorialException {
        try {
            Editorial editorial = buscarEditorialPorId(id);
            em.getTransaction().begin();
            em.remove(editorial);
            em.getTransaction().commit();
        } catch (RollbackException e) {
            throw new EditorialException("No se puede eliminar la editorial por estar asociada a uno o más libros."
                    + "\nElimine los libros primeros para poder eliminar la Editorial");
        } catch (Exception e) {
            System.out.println("Error eliminando Editorial desde el DAO");
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Método que devuelve un Objeto Editorial resultado de buscar un id pasado como parámetro en la BS. Utiliza el método .find(Tipo de Clase, PM) para encontrar un Objeto específico y devolverlo en el método
     *
     * @param id Primary Key del Objeto Editorial a buscar
     * @return La Editorial buscada de ser encontrada.
     */
    public Editorial buscarEditorialPorId(String id) {
        try {
            Editorial editorial = em.find(Editorial.class, id);
            return editorial;
        } catch (Exception e) {
            System.out.println("Error al buscar Editorial por ID en el DAO");
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Método que busca una Editorial en la BD según el nombre pasado como argumento y lo devuelve de ser encontrado. Crea una sentencia query de JPQA para buscar el atributo del Objeto.
     *
     * @param nombre atributo de la Editorial a buscar
     * @return Objeto Editorial de ser encontrado. Sino, null
     */
    public Editorial buscarEditorialPorNombre(String nombre) {
        try {
            Editorial editorial = (Editorial) em.createQuery("SELECT e "
                    + "FROM Editorial e "
                    + "WHERE e.nombre LIKE :nombre").
                    setParameter("nombre", nombre).
                    getSingleResult();
            return editorial;
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            System.out.println("Error buscando editorial por nombre desde el DAO");
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Método que devuelve una Lista con todos los Objetos de la tabla Editorial en la base de datos.
     *
     * @return Lista con todas las Editoriales de la BD
     */
    public List<Editorial> listarEditoriales() {
        try {
            List<Editorial> editoriales = em.createQuery("SELECT e FROM Editorial e").
                    getResultList();
            return editoriales;
        } catch (Exception e) {
            System.out.println("Error listando a Editoriales desde el DAO");
            e.printStackTrace();
            throw e;
        }
    }

}
