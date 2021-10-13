package libreria.persistencia;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import libreria.entidades.Libro;

/**
 *
 * @author Matias Luca Soto
 */
public final class LibroDAO {

    //ATRIBUTOS
    private final EntityManagerFactory emf;
    private final EntityManager em;

    //CONSTRUCTOR
    public LibroDAO() {
        this.emf = Persistence.createEntityManagerFactory("LibreriaPU");
        this.em = emf.createEntityManager();
    }

    //MÉTODOS
    /**
     * Método void que recibe un Objeto Libro para persistirlo en la Base de Datos
     *
     * @param libro Libro a persistir
     */
    public void guardarLibro(Libro libro) {
        try {
            em.getTransaction().begin();
            em.persist(libro);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error al guardar libro en el DAO");
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Método void que recibe un Objeto Libro actualizado para aplicarle un merge en la Base de Datos
     *
     * @param libro Libro con sus atributos ya actualizados
     */
    public void modificarLibro(Libro libro) {
        try {
            em.getTransaction().begin();
            em.merge(libro);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error al modificar libro en el DAO");
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Método void que recibe el ID de un Libro en formato String para removerlo de la BD
     *
     * @param id Primary Key del Objeto a remover
     */
    public void eliminarLibroPorId(String id) {
        try {
            Libro libro = buscarLibroPorId(id);
            em.getTransaction().begin();
            em.remove(libro);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error eliminando libro desde el DAO");
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Método que devuelve un Objeto Libro resultado de buscar un id pasado como parámetro en la BS. Utiliza el método .find(Tipo de Clase, PM) para encontrar un Objeto específico y devolverlo en el método
     *
     * @param id Primary Key del Objeto Libro a buscar
     * @return Libro buscado si existe. Sino, null
     */
    public Libro buscarLibroPorId(String id) {
        try {
            Libro libro = em.find(Libro.class, id);
            return libro;
        } catch (Exception e) {
            System.out.println("Error al buscar libro por ISBN en el DAO");
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Método que devuelve un Objeto Libro encontrado en la BS según el Long isbn enviado como argumento. Ejecuta un query de JPQA para buscar el Libro según el código ISBN
     *
     * @param isbn Código de tipo Long que corresponda al Libro a buscar
     * @return Libro con ese isbn de ser encontrado. Sino, null
     */
    public Libro buscarLibroPorIsbn(Long isbn) {
        try {
            Libro libro = (Libro) em.createQuery("SELECT l "
                    + "FROM Libro l "
                    + "WHERE l.isbn = :isbn").
                    setParameter("isbn", isbn).
                    getSingleResult();
            return libro;
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            System.out.println("Error buscando libro por título desde el DAO");
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Método que busca un Libro en la BD según el String titulo pasado como argumento y lo devuelve. Crea una sentencia query de JPQA para buscar el atributo del Objeto.
     * @param titulo String correspondiente al titulo del Libro
     * @return El Libro buscado de ser encontrado. Sino, null
     */
    public Libro buscarLibroPorTitulo(String titulo) {
        try {
            Libro libro = (Libro) em.createQuery("SELECT l "
                    + "FROM Libro l "
                    + "WHERE l.titulo LIKE :titulo").
                    setParameter("titulo", titulo).
                    getSingleResult();
            return libro;
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            System.out.println("Error buscando libro por título desde el DAO");
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Método que busca Libros matcheados con el String nombre pasado como argumento del atributo Autor del Libro y devuelve el o los Libros encontrados en una Lista.
     * @param nombre nombre del autor de los libros a buscar
     * @return Lista con todos los libros encontrados. Si no se encontró ninguno, la lista estará vacia
     */
    public List<Libro> buscarLibrosPorAutor(String nombre) {

        try {

            List<Libro> libros = em.createQuery("SELECT l "
                    + "FROM Libro l "
                    + "WHERE l.autor.nombre LIKE :nombre")
                    .setParameter("nombre", nombre)
                    .getResultList();
            return libros;

        } catch (Exception e) {
            System.out.println("Error buscando libro por nombre del autor desde el DAO");
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Método que busca libros matcheados con el nombre de editorial pasado como argumento y los devuelve dentro de una Lista.
     * @param nombre nombre de la editorial 
     * @return Lista con todos los libros encontrados. Si no se encontró ninguno, se devuleve vacia
     */
    public List<Libro> buscarLibrosPorEditorial(String nombre) {
        try {

            List<Libro> libros = em.createQuery("SELECT l "
                    + "FROM Libro l "
                    + "WHERE l.editorial.nombre LIKE :nombre")
                    .setParameter("nombre", nombre)
                    .getResultList();
            return libros;

        } catch (Exception e) {
            System.out.println("Error buscando libro por nombre de la editorial desde el DAO");
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Método que devuelve una Lista con todos los Objetos de la tabla Libro en la base de datos.
     * @return Lista con todos los Libros en la BD
     */
    public List<Libro> listarLibros() {
        try {
            List<Libro> libros = em.createQuery("SELECT l FROM Libro l").
                    getResultList();
            return libros;
        } catch (Exception e) {
            System.out.println("Error listando a los libros desde el DAO");
            e.printStackTrace();
            throw e;
        }
    }

}
