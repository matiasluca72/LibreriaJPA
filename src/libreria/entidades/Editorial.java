package libreria.entidades;

import com.sun.istack.internal.NotNull;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Matias Luca Soto
 */
@Entity
public class Editorial {

    //ATRIBUTOS
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id; // PRIMARY KEY

    @NotNull
    private String nombre;
    private Boolean alta;

    //CONSTRUCTORES
    /**
     * Constructor con todos los atributos como parámetros
     *
     * @param nombre
     */
    public Editorial(String nombre) {
        this.nombre = nombre;
        this.alta = true;
    }

    /**
     * Constructor vacio
     */
    public Editorial() {
    }

    //GETTERS & SETTERS
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getAlta() {
        return alta;
    }

    public void setAlta(Boolean alta) {
        this.alta = alta;
    }

    //toString
    @Override
    public String toString() {
        return "ID: " + id + " / Nombre: " + nombre + " / Alta:" + alta;
    }

}
