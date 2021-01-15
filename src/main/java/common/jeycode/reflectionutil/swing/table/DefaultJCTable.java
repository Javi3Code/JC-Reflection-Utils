package common.jeycode.reflectionutil.swing.table;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class DefaultJCTable extends JTable
{

      private static final String MYANMAR_TEXT = "Myanmar Text";
      private static final String BERNARD_MT_CONDENSED = "Bernard MT Condensed";
      private static final Dimension dimension = Toolkit.getDefaultToolkit()
                                                        .getScreenSize();
      private static final int WIDTH_CELL = dimension.width >> 5;
      private static final int HEIGHT_CELL = dimension.height >> 4;
      private final AutomatedTableModel tableModel;
      private final Color bgHeaderColor;
      private final Color fgHeaderColor;

      public DefaultJCTable(AutomatedTableModel tableModel,Color bgHeaderColor,Color fgHeaderColor)
      {
            super(tableModel);
            this.tableModel = tableModel;
            this.bgHeaderColor = bgHeaderColor;
            this.fgHeaderColor = fgHeaderColor;
            setTableRows();
            setTableHeader();
      }

      private void setTableHeader()
      {
            var header = getTableHeader();
            var rowSorter = new TableRowSorter<TableModel>(tableModel);
            setRowSorter(rowSorter);
            header.setFont(new Font(BERNARD_MT_CONDENSED,0,HEIGHT_CELL >> 1));
            header.setBackground(bgHeaderColor);
            header.setForeground(fgHeaderColor);
            header.setPreferredSize(new Dimension(WIDTH_CELL,HEIGHT_CELL));
            setAutoResizeMode(AUTO_RESIZE_ALL_COLUMNS);
      }

      private void setTableRows()
      {
            setFont(new Font(MYANMAR_TEXT,0,HEIGHT_CELL >> 2));
            setRowHeight(HEIGHT_CELL >> 1);
            setShowGrid(false);
            setTableCellRenderer();
      }

      private void setTableCellRenderer()
      {
            var tableCellRenderer = new DefaultJCTableCellRenderer();
            var columns = getColumnModel().getColumns();
            var columnIterator = columns.asIterator();

            while (columnIterator.hasNext())
            {
                  var column = columnIterator.next();
                  column.setCellRenderer(tableCellRenderer);
            }
      }

      public void setBgHeaderColor(Color bgHeaderColor)
      {
            getTableHeader().setBackground(bgHeaderColor);
      }

      public void setFgHeaderColor(Color fgHeaderColor)
      {
            getTableHeader().setForeground(fgHeaderColor);
      }

      private static final long serialVersionUID = 1L;

      public void fireDataChanged()
      {
            tableModel.fireTableDataChanged();
      }

      public void reBuildTable(List<?> data,Class<?>...annotatedObjectsClass)
      {
            tableModel.changeData(data,annotatedObjectsClass);
            setTableRows();
            setTableHeader();

      }

}
