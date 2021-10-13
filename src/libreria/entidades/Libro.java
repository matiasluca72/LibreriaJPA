package libreria.entidades;

import com.sun.istack.internal.NotNull;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
//import javax.persistence.OneToOne;
//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;
//import org.eclipse.persistence.jpa.config.Cascade;

/**
 *
 * @author Matias Luca Soto
 */
@Entity
public class Libro {

    //ATRIBUTOS
    @Id
    @GeneratedValue
    private String id;

    @NotNull
    private Long isbn; // PRIMARY KEY

    @NotNull
    private String titulo; // NO NULO

    //@Temporal(TemporalType.DATE)
    private Integer anio;

    private Integer ejemplares;
    private Integer ejemplaresPrestados;
    private Integer ejemplaresRestantes;
    private Boolean alta;

    @ManyToOne(cascade = CascadeType.REFRESH, optional = false)
    private Autor autor; // RELACIÓN 1 A 1

    @ManyToOne(cascade = CascadeType.REFRESH, optional = false)
    private Editorial editorial; // RELACIÓN 1 A 1

    //CONSTRUCTORES
    /**
     * Todos los atributos pasados por parámetro
     *
     * @param isbn
     * @param titulo
     * @param anio
     * @param ejemplares
     * @param ejemplaresPrestados
     * @param ejemplaresRestantes
     * @param autor
     * @param editorial
     */
    public Libro(Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Integer ejemplaresRestantes, Autor autor, Editorial editorial) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.anio = anio;
        this.ejemplares = ejemplares;
        this.ejemplaresPrestados = ejemplaresPrestados;
        this.ejemplaresRestantes = ejemplaresRestantes;
        this.alta = true;
        this.autor = autor;
        this.editorial = editorial;
    }

    /**
     * Todos los atributos menos Autor y Editorial
     *
     * @param isbn
     * @param titulo
     * @param anio
     * @param ejemplares
     * @param ejemplaresPrestados
     * @param ejemplaresRestantes
     */
    public Libro(Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Integer ejemplaresRestantes) {
        this.titulo = titulo;
        this.anio = anio;
        this.ejemplares = ejemplares;
        this.ejemplaresPrestados = ejemplaresPrestados;
        this.ejemplaresRestantes = ejemplaresRestantes;
        this.alta = true;
    }

    /**
     * Constructor vacio
     */
    public Libro() {
    }

    //GETTERS & SETTERS
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getIsbn() {
        return isbn;
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getEjemplares() {
        return ejemplares;
    }

    public void setEjemplares(Integer ejemplares) {
        this.ejemplares = ejemplares;
    }

    public Integer getEjemplaresPrestados() {
        return ejemplaresPrestados;
    }

    public void setEjemplaresPrestados(Integer ejemplaresPrestados) {
        this.ejemplaresPrestados = ejemplaresPrestados;
    }

    public Integer getEjemplaresRestantes() {
        return ejemplaresRestantes;
    }

    public void setEjemplaresRestantes(Integer ejemplaresRestantes) {
        this.ejemplaresRestantes = ejemplaresRestantes;
    }

    public Boolean getAlta() {
        return alta;
    }

    public void setAlta(Boolean alta) {
        this.alta = alta;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Editorial getEditorial() {
        return editorial;
    }

    public void setEditorial(Editorial editorial) {
        this.editorial = editorial;
    }

    //toString
    @Override
    public String toString() {

        String output = "Titulo: " + titulo + " / ISBN: " + isbn + " / Año: " + anio + " / Ejemplares: " + ejemplares + " / Prestados: " + ejemplaresPrestados + " / Restantes: " + ejemplaresRestantes;
        if (getAutor() != null) {
            output = output.concat(" / Autor: " + autor.getNombre());
        } else {
            output = output.concat(" / Autor: Sin seleccionar");
        }
        if (getEditorial() != null) {
            output = output.concat(" / Editorial: " + editorial.getNombre());
        } else {
            output = output.concat(" / Editorial: Sin seleccionar");
        }
        return output;
    }

}
