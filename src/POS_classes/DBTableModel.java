package POS_classes;

import javax.swing.table.DefaultTableModel;

public class DBTableModel extends DefaultTableModel{

	private static final long serialVersionUID = -556004088603644463L;

	String columnClass[];
	
	public DBTableModel(Object rowData[][], Object columnNames[]/*, Object columnClass[]*/) throws ClassNotFoundException {
        super(rowData, columnNames);
//        setColumnClass(columnClass);
     }
	
	public void setColumnClass(Object columnClass[]){
		this.columnClass = new String[getColumnCount()];
		for (int i=0;i<columnClass.length;i++){
			this.columnClass[i] = (String) columnClass[i];
		}
		return;
	}
	
//	@Override
//	public Class getColumnClass(int col){
//			try {
//				return Class.forName(this.columnClass[col]);
//			} catch (ClassNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return Object.class;
//	}
	
	@Override
	public boolean isCellEditable(int row, int col){
		return false;
	}
	
}
