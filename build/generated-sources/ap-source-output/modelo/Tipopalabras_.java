package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Palabras;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2021-11-15T16:51:50")
@StaticMetamodel(Tipopalabras.class)
public class Tipopalabras_ { 

    public static volatile SingularAttribute<Tipopalabras, Integer> idtipos;
    public static volatile CollectionAttribute<Tipopalabras, Palabras> palabrasCollection;
    public static volatile SingularAttribute<Tipopalabras, String> description;

}