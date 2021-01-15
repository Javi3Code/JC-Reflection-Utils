package common.jeycode.reflectionutil.annotations;

import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import common.jeycode.reflectionutil.swing.table.AutomatedTableModel;

/**
 * 
 *
 *
 * 
 * 
 * <h2>AutomatedTableColumn</h2>
 * 
 * Anotación de método, se lee en ejecución. Debe usarse en objetos
 * <i>"tontos"</i>, que solo encapsulan datos, en los getters.
 * 
 * Trabaja en conjunto con {@link AutomatedTableModel}.
 * 
 * <strong> Es de obligación </strong>dar valor adecuado a sus atributos
 * {@link #value()} y {@link #index()}, estos determinan el valor de columna y
 * su número de columna en el modelo de tabla.
 * 
 * Acepta herencia.
 * 
 * 
 * 
 * 
 * @author Javier Pérez Alonso
 * 
 *         01 Sept. 2021
 * 
 * @version 1.0
 * @see AutomatedTableModel
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(METHOD)
@Inherited
public @interface AutomatedTableColumn
{

      /**
       * Atributo de anotación.
       *
       * @return el nombre de la columna.
       */
      String value();

      /**
       * 
       * Atributo de anotación.
       *
       * @return el índice de la columna.
       */
      int index();
}
