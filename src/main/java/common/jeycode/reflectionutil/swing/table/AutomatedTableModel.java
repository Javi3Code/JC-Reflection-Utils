package common.jeycode.reflectionutil.swing.table;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.swing.table.AbstractTableModel;

import common.jeycode.reflectionutil.annotations.AutomatedTableColumn;

/**
 * 
 * 
 * <h2>AutomatedTableModel</h2>
 * 
 * Modelo de tabla automatizada para aceptar cualquier lista de objetos y
 * cargarla con sus datos anotados adecuadamente con
 * {@link AutomatedTableColumn}.
 * 
 * Trabaja con Reflection, obteniendo los métodos anotados. Lee los valores de
 * la anotación y construye el modelo de la tabla. Una vez creado el objeto
 * {@link AutomatedTableModel} es necesario invocar a
 * {@link #buildModel(List, Class...)}. A este método se le pasa como argumentos
 * la data y los .class requeridos.
 * 
 * 
 * @author Javier Pérez Alonso
 *
 *         01 Sept. 2021
 * 
 * @version 1.0
 * @see AutomatedTableColumn
 * @see AbstractTableModel
 * 
 */
public class AutomatedTableModel extends AbstractTableModel
{

      /**
       * {@value #NOTHING} Constante <String> que determina el valor por defecto para
       * una celda que no fuera a ser ocupada.
       */
      private static final String NOTHING = "----";
      /**
       * // * {@value #HTML} Constante <String>, etiqueta html de apertura. //
       */
//      private static final String HTML = "<html>";
      /**
       * // * {@value #HTML_CLOSE} Constante <String>, etiqueta html de cierre. //
       */
//      private static final String HTML_CLOSE = "</html>";
      /**
       * {@link #columnCount} Atributo <int>, determina el número de columnas.
       */
      private int columnCount;
      /**
       * {@link #data} Atributo <List>, contiene la data de la tabla.
       */
      private List<?> data;
      /**
       * {@link #tableColumn} Atributo <Map>, contiene el índice de columna <Integer>
       * y la anotacion <AutomatedTableColumn> correspondiente.
       */
      private Map<Integer,AutomatedTableColumn> tableColumn;
      /**
       * {@link #annotatedMethod} Atributo <Map>, contiene el índice de columna y el
       * nombre del método anotado.
       */
      private Map<Integer,String> annotatedMethod;
      /**
       * {@value #annotationClass} Constante <Class>, .class de <AutomatedTableColumn>
       */
      private static final Class<AutomatedTableColumn> annotationClass = AutomatedTableColumn.class;

      /**
       * 
       * Constructor: Instancia los mapas {@link #tableColumn} y
       * {@link #annotatedMethod}
       *
       */
      public AutomatedTableModel()
      {
            tableColumn = new HashMap<>();
            annotatedMethod = new HashMap<>();
      }

      /**
       * 
       * Method buildModel: Se asigna el valor de {@link #data} y se carga una lista
       * con los métodos de cada .class recibido por parámetro.
       * {@link #loadMethodLst(Class...)}
       *
       * @param data                  : Lista de Datos a cargar en la tabla
       * @param annotatedObjectsClass : .class a partir de los que se obtienen los
       *                              métodos.
       */
      public void buildModel(List<?> data,Class<?>...annotatedObjectsClass)
      {
            this.data = data;
            loadMethodLst(annotatedObjectsClass);
      }

      /**
       * 
       * Method loadMethodLst : Carga los métodos de los .class recibidos. Recorre
       * cada .class obteniendo un array con todos los métodos de cada clase,
       * añadiendolos a una lista. Esta lista se pasa como argumento al método
       * {@link #setDefaultValues(LinkedList)}.
       * 
       *
       * @param annotatedObjectsClass : .class a recorrer para obtener sus métodos.
       */
      private void loadMethodLst(Class<?>...annotatedObjectsClass)
      {
            var methods = new LinkedList<Method>();
            for (Class<?> element : annotatedObjectsClass)
            {
                  var allMethods = element.getMethods();
                  for (Method method : allMethods)
                  {
                        methods.add(method);
                  }
            }
            setDefaultValues(methods);
      }

      /**
       * 
       * Method setDefaultValues : Recorre la lista de métodos, obtiene la anotación
       * guardada en {@link #annotationClass} mediante reflection, si es un método
       * anotado entonces obtiene los atributos de la anotación, guardando los valores
       * en los mapas {@link #annotatedMethod} y {@link #tableColumn}. Por último,
       * establece el valor de {@link #columnCount} con el tamaño de
       * {@link #tableColumn}.
       *
       * @param methods : Lista de métodos a recorrer.
       */
      private void setDefaultValues(LinkedList<Method> methods)
      {
            for (Method method : methods)
            {
                  var declaredAnnotation = method.getDeclaredAnnotation(annotationClass);
                  if (Objects.nonNull(declaredAnnotation))
                  {
                        var key = declaredAnnotation.index();
                        annotatedMethod.put(key,method.getName());
                        tableColumn.put(key,declaredAnnotation);
                  }
            }
            columnCount = tableColumn.size();
      }

      /**
       * 
       *
       * Overwritten method getRowCount : Establece el número de filas de la tabla.
       *
       */
      @Override
      public int getRowCount()
      {
            return data.size();
      }

      /**
       * 
       *
       * Overwritten method getColumnCount : Establece el número de columnas de la
       * tabla.
       *
       */
      @Override
      public int getColumnCount()
      {
            return columnCount;
      }

      /**
       * 
       *
       * Overwritten method getColumnName : Establece el nombre de la columna para
       * cierto índice. Lo obtiene a partir de la anotación guardada en el mapa
       * {@link #tableColumn}.
       *
       */
      @Override
      public String getColumnName(int columnIndex)
      {
            return tableColumn.get(columnIndex)
                              .value();
      }

      /**
       * 
       *
       * Overwritten method getColumnClass : Devuelve el .class, este método se ha
       * sobrescrito para poder ordenar números en la tabla. Atención por posibles
       * efectos secundarios.
       *
       */
      @Override
      public Class<?> getColumnClass(int columnIndex)
      {
            try
            {
                  Double.parseDouble(getValueAt(0,columnIndex) + "");
                  return Double.class;
            }
            catch (NumberFormatException ex)
            {
                  return super.getColumnClass(columnIndex);
            }

      }

      /**
       * 
       *
       * Overwritten method getValueAt : Establece el valor de la celda según
       * fila/columna. Obtiene el valor de {@link #data} con el índice de fila. Trata
       * de obtener el valor de ese objeto para esa celda mediante el uso de
       * reflection. Se obtiene la clase, de ahí el método con el nombre
       * correspondiente para el índice de columna, obteniéndolo del mapa
       * {@link #annotatedMethod} y lo invoca. Se guarda el valor entre {@link #HTML}
       * y {@link #HTML_CLOSE}. En caso de ser un valor nulo, es decir, que el método
       * no se encuentre, entonces se devuelve {@link #NOTHING}.
       *
       */
      @Override
      public Object getValueAt(int rowIndex,int columnIndex)
      {
            var objectData = data.get(rowIndex);
            Object value = null;
            try
            {
                  value = objectData.getClass()
                                    .getMethod(annotatedMethod.get(columnIndex),null)
                                    .invoke(objectData,null);
            }
            catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException |
                  SecurityException ex)
            {
                  value = NOTHING;
            }
            return value;
      }

      public List<?> data()
      {
            return data;
      }

      /**
       * 
       * Method changeData : Se puede usar este método para cambiar los datos de la
       * tabla dinámicamente, ella sola se actualiza tanto los datos, como la
       * estructura. Por dentro se llama a {@link #buildModel(List, Class...)} para
       * reconstruir el modelo.
       *
       * @param data                  : Data a cambiar en el modelo.
       * @param annotatedObjectsClass : Nuevos .class a tener en cuenta en el cambio.
       */
      public void changeData(List<?> data,Class<?>...annotatedObjectsClass)
      {
            tableColumn = new HashMap<>();
            annotatedMethod = new HashMap<>();
            buildModel(data,annotatedObjectsClass);
            fireTableDataChanged();
            fireTableStructureChanged();
      }

      private static final long serialVersionUID = 1L;

}
