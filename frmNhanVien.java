import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public  class frmNhanVien extends JFrame implements ActionListener, MouseListener{
private DanhSachNhanVien dsnv;
private JTable table;
private JTextField txtMaNV;
private JTextField txtHo;
private JTextField txtTen;
private JTextField txtTuoi;
private JRadioButton radNu;
private JTextField txtTienLuong;
private JTextField txtTim;
private JButton btnTim;
private JButton btnThem;
private JButton btnXoa;
private JButton btnLuu;
private JButton btnXoaTrang;
private DefaultTableModel tableModel;
fileDocGhi fi;
private JLabel lblPhong;
private JTextField txtPhong;
private JComboBox cboPhong;
private static final String FILENAME ="NhanVien.txt";

public frmNhanVien(DanhSachNhanVien dao) throws Exception{
	setTitle("^_^");
	setSize(700, 450);
	setDefaultCloseOperation(EXIT_ON_CLOSE); 
	setLocationRelativeTo(null);
	buildUI();
}
	
	private void buildUI() {
	JPanel pnlNorth;
	add(pnlNorth = new JPanel(), BorderLayout.NORTH);
	JLabel lblTieuDe;
	pnlNorth.add(lblTieuDe = new JLabel("THÔNG TIN NHÂN VIÊN"));
	lblTieuDe.setFont(new Font("Arial", Font.BOLD, 20)); lblTieuDe.setForeground(Color.BLUE);
	
	Box b = Box.createVerticalBox();
	
	Box b1, b2, b3, b4, b5;
	JLabel lblMaNV, lblHo, lblTuoi, lblPhai, lblTienLuong;
	
	b.add(b1 = Box.createHorizontalBox());
	b.add(Box.createVerticalStrut(10));
	b1.add(lblMaNV = new JLabel("Mã nhân viên: "));
	b1.add(txtMaNV = new JTextField());
	
	b.add(b2 = Box.createHorizontalBox());
	b.add(Box.createVerticalStrut(10));
	b2.add(lblHo = new JLabel("Họ: "));
	b2.add(txtHo = new JTextField());
	b2.add(new JLabel("Tên nhân viên: "));
	b2.add(txtTen = new JTextField());
	
	b.add(b3 = Box.createHorizontalBox());
	b.add(Box.createVerticalStrut(10));
	b3.add(lblTuoi = new JLabel("Tuổi: "));
	b3.add(txtTuoi = new JTextField());
	b3.add(lblPhai = new JLabel("Phái: "));
	b3.add(radNu = new JRadioButton("Nữ"));
	
	b.add(b4 = Box.createHorizontalBox());
	b. add(Box.createVerticalStrut(10));
	b4.add(lblPhong = new JLabel("Phòng: "));
	String [] phong = {"Phòng tổ chức","Phòng kỹ thuật","Phòng nhân sự","Phòng tài vụ"};
	b4.add(cboPhong= new JComboBox(phong));
	b4.add(lblTienLuong = new JLabel("Tiền lương"));
	b4.add(txtTienLuong = new JTextField());
	
	lblHo.setPreferredSize(lblMaNV.getPreferredSize());
	lblPhai.setPreferredSize(lblMaNV.getPreferredSize());
	lblPhong.setPreferredSize(lblMaNV.getPreferredSize());
	lblTuoi.setPreferredSize(lblMaNV.getPreferredSize());
	
	b.add(b5 = Box.createHorizontalBox());
	b.add(Box.createVerticalStrut(10));
	String [] headers = "Mã NV;Họ;Tên;Phái;Tuổi;Phòng;Tiền Lương".split(";");
	tableModel=new DefaultTableModel(headers,0);
	JScrollPane scroll = new JScrollPane();
	scroll.setViewportView(table = new JTable(tableModel));
	table.setRowHeight(25);
	table.setAutoCreateRowSorter(true);
	table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
	
	
	b5.add(scroll);
	add(b, BorderLayout.CENTER);
	
	JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT); 
	add(split,BorderLayout.SOUTH);
	JPanel pnlLeft, pnlRight;
	split.add(pnlLeft = new JPanel());
	split.add(pnlRight = new JPanel());
	
	pnlLeft.add(new JLabel("Nhập mã số cần tìm: "));
	pnlLeft.add(txtTim = new JTextField(10));
	pnlLeft.add(btnTim = new JButton("Tìm"));
	pnlRight.add(btnThem = new JButton("Thêm"));
	pnlRight.add(btnXoaTrang = new JButton("Xóa trắng"));
	pnlRight.add(btnXoa = new JButton("Xóa"));
	pnlRight.add(btnLuu = new JButton("Lưu"));
	
	btnThem.addActionListener(this);
	btnXoa.addActionListener(this);
	btnXoaTrang.addActionListener(this);
	btnTim.addActionListener(this);
	table.addMouseListener(this);
	
	dsnv =new DanhSachNhanVien();
	fi = new fileDocGhi();
	try {
		dsnv = (DanhSachNhanVien)fi.readFromFile(FILENAME);
	} catch (Exception e) {
		System.out.println("");
	}
	hienTable();
}
	public void actionPerformed(ActionEvent e) {
	    Object o = e.getSource();
	    if (o.equals(btnThem))
	        themActions();
	    if (o.equals(btnXoa)) 
	        xoaActions();
	    if (o.equals(btnXoaTrang)) 
	        xoaTrangActions();
	    if (o.equals(btnLuu)) {
	        try {
	            luuDuLieuVaoTepTin(FILENAME);
	            JOptionPane.showMessageDialog(this, "Dữ liệu đã được lưu thành công.");
	        } catch (IOException e1) {
	            System.out.println("Lỗi khi lưu file: " + e1.getMessage());
	            e1.printStackTrace();
	        }
	    }
	}

	private void luuDuLieuVaoTepTin(String tenTepTin) throws IOException {
	    BufferedWriter writer = new BufferedWriter(new FileWriter(tenTepTin));

	    for (int i = 0; i < dsnv.tong(); i++) {
	        NhanVien nv = dsnv.getNhanVien(i);
	        String dataRow = nv.getMaNV() + ";" + nv.getHo() + ";" + nv.getTen() + ";" + nv.isPhai() + ";" + nv.getTuoi() + ";" + nv.getPhong() + ";" + nv.getTienLuong();
	        writer.write(dataRow);
	        writer.newLine();
	    }

	    writer.close();
	}

	public void hienTable() {
		for (int i = 0;i <dsnv.tong();i++) {
			NhanVien nv =dsnv.getNhanVien(i);
			String[]
	dataRow= {nv.getMaNV()+"",nv.getHo(),nv.getTen(),Boolean.toString(nv.isPhai()),nv.getTuoi()+"",
			nv.getPhong(),nv.getTienLuong()+""};
			tableModel.addRow(dataRow);
		}
	}
	private void xoaTrangActions() { 
		txtMaNV.setText("");
		txtHo.setText("");
		txtTen.setText("");
		txtTuoi.setText("");
		txtTim.setText("");
		txtTienLuong.setText("");
		radNu.setSelected(false); 
		txtMaNV.requestFocus();
	}
	// Kiểm tra các điều kiện ràng buộc
    //if (maNV <= 0) {
       // JOptionPane.showMessageDialog(null, "Mã nhân viên phải lớn hơn 0.");
        //return;
    //}

	private void themActions() {
	    try {
	        String maNVText = txtMaNV.getText();
	        
	        // Biểu thức chính quy cho mã nhân viên
	        String maNVPattern = "[A-Z]\\d{3}";

	        // Kiểm tra mã nhân viên theo biểu thức chính quy
	        if (!maNVText.matches(maNVPattern)) {
	            JOptionPane.showMessageDialog(null, "Mã nhân viên không hợp lệ. Mã phải bắt đầu bằng một chữ cái viết hoa và theo sau bởi ba chữ số.");
	            return;
	        }

	        int maNV = Integer.parseInt(maNVText);

	        String ho = txtHo.getText();
	        String ten = txtTen.getText();

	        // Biểu thức chính quy cho họ và tên (in hoa chữ cái đầu)
	        String hoTenPattern = "[A-Z][a-z]*";
	        
	        // Kiểm tra họ và tên không để trống và phải theo đúng biểu thức
	        if (ho.isEmpty() || ten.isEmpty() || !ho.matches(hoTenPattern) || !ten.matches(hoTenPattern)) {
	            JOptionPane.showMessageDialog(null, "Họ và tên không được để trống, phải bắt đầu bằng chữ cái viết hoa và theo sau bởi chữ thường.");
	            return;
	        }

	        boolean phai = radNu.isSelected();
	        
	        int tuoi;
	        
	        // Biểu thức chính quy cho tuổi (số nguyên dương)
	        String tuoiPattern = "\\d+";

	        // Kiểm tra tuổi theo biểu thức chính quy
	        if (!txtTuoi.getText().matches(tuoiPattern)) {
	            JOptionPane.showMessageDialog(null, "Tuổi phải là một số nguyên dương.");
	            return;
	        }

	        tuoi = Integer.parseInt(txtTuoi.getText());
	        
	        // Kiểm tra tuổi lớn hơn 0
	        if (tuoi <= 0) {
	            JOptionPane.showMessageDialog(null, "Tuổi phải lớn hơn 0.");
	            return;
	        }

	        String phong = (String) cboPhong.getSelectedItem();
	        
	        double tienLuong;
	        
	        // Biểu thức chính quy cho tiền lương (số thực không âm)
	        String tienLuongPattern = "\\d*\\.?\\d+";

	        // Kiểm tra tiền lương theo biểu thức chính quy
	        if (!txtTienLuong.getText().matches(tienLuongPattern)) {
	            JOptionPane.showMessageDialog(null, "Tiền lương không hợp lệ.");
	            return;
	        }

	        if (txtTienLuong.getText().isEmpty()) {
	            // Mặc định tiền lương là 0 nếu không nhập
	            tienLuong = 0.0;
	        } else {
	            tienLuong = Double.parseDouble(txtTienLuong.getText());
	        }

	        NhanVien nv = new NhanVien(maNV, ho, ten, phai, tuoi, phong, tienLuong);

	        if (dsnv.themNhanVien(nv)) {
	            String[] row = { nv.getMaNV() + "", nv.getHo(), nv.getTen(), Boolean.toString(nv.isPhai()),
	                    nv.getTuoi() + "", nv.getPhong(), nv.getTienLuong() + "" };
	            tableModel.addRow(row);
	            xoaTrangActions();
	        } else {
	            JOptionPane.showMessageDialog(null, "Trùng mã nhân viên.");
	            txtMaNV.selectAll();
	            txtMaNV.requestFocus();
	        }
	    } catch (NumberFormatException ex) {
	        JOptionPane.showMessageDialog(null, "Lỗi nhập liệu.");
	        return;
	    }
	}
			
		private void xoaActions() {
			int row = table.getSelectedRow();
			if(row != -1){
			int maNV = Integer.parseInt((String) table.getModel().getValueAt(row, 0)); 
			int hoiNhac = JOptionPane.showConfirmDialog(this, "Chắc chắn xóa không? ", 
																"Chú ý", JOptionPane.YES_NO_OPTION);
			if(hoiNhac == JOptionPane.YES_OPTION)
			if(dsnv.xoaNhanVien(maNV))
				tableModel.removeRow(row);
			}
		}
		
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				int row = table.getSelectedRow(); // lay dong dang chon tren table 
				txtMaNV.setText(table.getValueAt(row,0).toString());
				txtHo.setText(table.getValueAt(row, 1).toString()); 
				txtTen.setText(table.getValueAt(row, 2).toString()); 
				radNu.setSelected(false);
				txtTuoi.setText(table.getValueAt(row, 4).toString()); 
				txtTienLuong.setText(table.getValueAt(row, 5).toString());
}
			
			

public static void main(String[] args) throws Exception {
	// TODO Auto-generated method stub
	DanhSachNhanVien dao = new DanhSachNhanVien(); 
	new frmNhanVien(dao).setVisible(true);
	}
@Override
public void mousePressed(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mouseReleased(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mouseEntered(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mouseExited(MouseEvent e) {
	// TODO Auto-generated method stub
	
}


}

