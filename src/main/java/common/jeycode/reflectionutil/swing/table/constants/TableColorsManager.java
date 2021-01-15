package common.jeycode.reflectionutil.swing.table.constants;

import java.awt.Color;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import common.jeycode.reflectionutil.swing.table.exceptions.TableColorsConfigException;

public class TableColorsManager
{

      private static final String TO_STRING = "";
      public static final String FILE_PATH = "resources" + File.separator + "TableColors.properties";
      private static final Properties properties = new Properties();

      public static final String BG_COLOR_ODD_ROWS = "bg.color.odd.rows";
      public static final String BG_COLOR_EVEN_ROWS = "bg.color.even.rows";
      public static final String FG_COLOR_ODD_ROWS = "fg.color.odd.rows";
      public static final String FG_COLOR_EVEN_ROWS = "fg.color.even.rows";
      public static final String BG_COLOR_SELECTED_ROWS = "bg.color.selected.rows";
      public static final String FG_COLOR_SELECTED_ROWS = "fg.color.selected.rows";
      private static final List<String> ALL_KEYS = List.of(BG_COLOR_ODD_ROWS,BG_COLOR_EVEN_ROWS,FG_COLOR_ODD_ROWS,FG_COLOR_EVEN_ROWS,
                                                           BG_COLOR_SELECTED_ROWS,FG_COLOR_SELECTED_ROWS);

      private TableColorsManager()
      {}

      public static Map<String,Color> getMapOfColors()
      {
            var colorsMap = new HashMap<String,Color>();
            try
            {
                  properties.load(new FileReader(TableColorsManager.FILE_PATH));
                  ALL_KEYS.forEach(key-> colorsMap.put(key,Color.decode(properties.get(key) + TO_STRING)));
            }
            catch (IOException | NumberFormatException ex)
            {
                  if (ex instanceof IOException)
                  {
                        throw new TableColorsConfigException(TableColorsConfigException.IO_ERROR);
                  }

            }
            return colorsMap;
      }
}
