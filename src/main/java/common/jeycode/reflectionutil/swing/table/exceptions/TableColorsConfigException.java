package common.jeycode.reflectionutil.swing.table.exceptions;

import common.jeycode.reflectionutil.swing.table.constants.TableColorsManager;

public class TableColorsConfigException extends RuntimeException
{

      public static String IO_ERROR = "Debe ubicarse el archivo properties a primer nivel dentro del proyecto paquete resources\nPath->"
                              + TableColorsManager.FILE_PATH;

//      public static String DECODE_ERROR = "Algún color no se pudo decodificar, comprueba los valores";

      public TableColorsConfigException(String msg)
      {
            super(msg);
      }

      private static final long serialVersionUID = -2236515455533982938L;

}
