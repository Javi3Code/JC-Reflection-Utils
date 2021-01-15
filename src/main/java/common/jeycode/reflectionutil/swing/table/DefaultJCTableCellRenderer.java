package common.jeycode.reflectionutil.swing.table;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import common.jeycode.reflectionutil.swing.table.constants.TableColorsManager;

public class DefaultJCTableCellRenderer extends DefaultTableCellRenderer
{

      private final boolean dynamicColors = true;
      private Color oddBgRowColor,evenBgRowColor,oddfgRowColor,evenFrRowColor,selectedBgRowColor,selectedFgRowColor;

      public DefaultJCTableCellRenderer()
      {
            loadColors();
            setHorizontalAlignment(SwingConstants.CENTER);
      }

      private void loadColors()
      {
            var map = TableColorsManager.getMapOfColors();

            oddBgRowColor = map.getOrDefault(TableColorsManager.BG_COLOR_ODD_ROWS,Color.WHITE);
            evenBgRowColor = map.getOrDefault(TableColorsManager.BG_COLOR_EVEN_ROWS,Color.WHITE);
            oddfgRowColor = map.getOrDefault(TableColorsManager.FG_COLOR_ODD_ROWS,Color.BLACK);
            evenFrRowColor = map.getOrDefault(TableColorsManager.FG_COLOR_EVEN_ROWS,Color.BLACK);
            selectedBgRowColor = map.getOrDefault(TableColorsManager.BG_COLOR_SELECTED_ROWS,Color.LIGHT_GRAY);
            selectedFgRowColor = map.getOrDefault(TableColorsManager.FG_COLOR_SELECTED_ROWS,Color.BLACK);
      }

      @Override
      public Component getTableCellRendererComponent(JTable table,Object value,boolean isSelected,boolean hasFocus,int row,int column)
      {
            super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column);
            if (dynamicColors)
            {
                  setTableCellEvent(isSelected,hasFocus,row);
            }
            else
            {
                  setBackground(oddBgRowColor);
                  setForeground(oddfgRowColor);
            }

            return this;
      }

      // Queda pendiente usar hasFocus para eventos de celda
      private void setTableCellEvent(boolean isSelected,boolean hasFocus,int row)
      {
            var isEven = row % 2 == 0;
            if (isSelected)
            {
                  setBackground(selectedBgRowColor);
                  setForeground(selectedFgRowColor);

            }
            else
            {
                  if (isEven)
                  {
                        setBackground(evenBgRowColor);
                        setForeground(evenFrRowColor);
                  }
                  else
                  {
                        setBackground(oddBgRowColor);
                        setForeground(oddfgRowColor);
                  }
            }
      }

      private static final long serialVersionUID = -8562511393155890850L;

}
