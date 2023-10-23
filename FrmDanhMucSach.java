import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import java.util.ArrayList;
import java.util.Calendar;
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

public  class FrmDanhMucSach extends JFrame implements ActionListener, MouseListener{
private ThuVien thuvien;
private JTable table;
private JTextField txtMasach;
private JTextField txtTuaSach;
private JTextField txtTacGia;
private JTextField txtNamXB;
private JTextField txtNhaXB;
private JTextField txtSotrang;
private JTextField txtDonGia;
private JTextField txtISBN;
private JButton btnThem;
private JButton btnXoa;
private JButton btnLuu;
private JButton btnSua;
private JButton btnXoaRong;
private JButton btnLoc;
private DefaultTableModel tableModel;
fileDocGhi1 fi;
private JLabel lblPhong;
private JTextField txtMess;
private JComboBox<String> cboMaSach;
private JLabel lblDonGia;
private JLabel lblSoTrang;
private JLabel lblNhaXB;
private JLabel lblNamXB;
private JLabel lblTacGia;
private JLabel lblTuaSach;
private JLabel lblMaSach;
private JLabel lblISBN;
private static final String FILENAME ="duLieu.txt";

public FrmDanhMucSach() throws Exception{
	setTitle("Quản lý sách");
	setSize(900, 600);
	setDefaultCloseOperation(EXIT_ON_CLOSE); 
	setLocationRelativeTo(null);
	
	buildUI();
	LoadDatabase();
}
	private void buildUI() {
	//Phan North
	JPanel pnlNorth; 
	add(pnlNorth = new JPanel(), BorderLayout.NORTH);
	pnlNorth.setPreferredSize(new Dimension(0,180));
	pnlNorth. setBorder( BorderFactory.createTitledBorder("Records Editor"));
	pnlNorth.setLayout(null); //Absolute layout
	JLabel lblMaSach, lblTuaSach, lblTacGia, lblNamXB, lblNhaXB, lblSoTrang, lblDonGia, lbllSBN;
	pnlNorth.add(lblMaSach = new JLabel("Mã sách: "));
	pnlNorth.add(lblTuaSach = new JLabel("Tựa sách: ")); 
	pnlNorth.add(lblTacGia = new JLabel("Tác giả: ")); 
	pnlNorth.add(lblNamXB = new JLabel("Năm xuất bản: ")); 
	pnlNorth.add(lblNhaXB = new JLabel("Nhà xuất bản: "));
	pnlNorth.add(lblSoTrang = new JLabel("Số trang: "));
	pnlNorth.add(lblDonGia = new JLabel("Đơn giá: "));
	pnlNorth.add(lblISBN = new JLabel("International standard Book Number: "));
	pnlNorth.add(txtMasach = new JTextField());
	pnlNorth.add(txtTuaSach = new JTextField());
	pnlNorth.add(txtTacGia = new JTextField());
	pnlNorth.add(txtNamXB = new JTextField());
	pnlNorth.add(txtNhaXB = new JTextField());
	pnlNorth.add(txtSotrang = new JTextField());
	pnlNorth.add(txtDonGia = new JTextField());
	pnlNorth.add(txtISBN = new JTextField());
	pnlNorth.add(txtMess = new JTextField());
	txtMess.setEditable(false);
	txtMess.setBorder(null);
	txtMess.setForeground (Color. red) ;
	txtMess.setFont(new Font("Arial", Font.ITALIC, 12));
	int wl = 100, w2 = 300, h = 20;
	lblMaSach.setBounds(20, 20, wl, h); txtMasach.setBounds(120, 20, 200, h);
	lblTuaSach.setBounds(20, 45, wl, h); txtTuaSach.setBounds(120, 45, w2, h);
	lblTacGia.setBounds(450, 45, wl, h); txtTacGia.setBounds(570, 45, w2, h);
	lblNamXB.setBounds(20, 70, wl, h); txtNamXB.setBounds(120, 70, w2, h);
	lblNhaXB.setBounds(450, 70, wl, h); txtNhaXB.setBounds(570, 70, w2, h ) ;
	lblSoTrang.setBounds(20, 95, wl, h); txtSotrang.setBounds(120, 95 ,w2, h);
	lblDonGia.setBounds(450, 95, wl, h); txtDonGia.setBounds(570, 95, w2, h);
	lblISBN.setBounds(20, 120, 220, h); txtISBN.setBounds(240, 120, 180, h);
	txtMess.setBounds(20, 145, 550, 20);
	//Phan Center
	JPanel pnlCenter;
	add(pnlCenter = new JPanel(), BorderLayout.CENTER) ; 
	pnlCenter.add(btnThem = new JButton("Thêm"));
	pnlCenter.add(btnXoaRong = new JButton("Xóa rỗng"));
	pnlCenter.add(btnXoa = new JButton("Xóa"));
	pnlCenter.add(btnSua = new JButton("Sửa"));
	pnlCenter.add(btnLuu = new JButton("Lưu"));
	pnlCenter.add(new JLabel("Tìm theo mã sách: "));
	pnlCenter.add(cboMaSach = new JComboBox<String>());
	cboMaSach.setPreferredSize(new Dimension(100, 25));
	pnlCenter.add(btnLoc = new JButton("Lọc theo tựa sách"));
	//Phan South
	JScrollPane scroll;
	String [] headers =
	"MaSach;TuaSach;TacGia;NamXuatBan;NhaXuatBan;SoTrang;DonGia;ISBN".split(";");
	tableModel=new DefaultTableModel(headers,0);
	add(scroll = new JScrollPane(table = new JTable(tableModel),
	JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
	JScrollPane .HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout. SOUTH) ;
	scroll.setBorder(BorderFactory.createTitledBorder("Danh sách các cuốn sách"));
	table.setRowHeight(20);
	scroll.setPreferredSize(new Dimension(0, 350));
	//Xử lý
	table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		

	public void valueChanged(ListSelectionEvent e) {
		int row = table.getSelectedRow(); //Lấy dòng đươc chon 
		fillForm(row); //Hiển thi trên các components
		}
	});
	cboMaSach.addActionListener(this); 
	btnThem.addActionListener(this);
	btnXoa.addActionListener(this); 
	btnXoaRong.addActionListener(this); 
	btnSua.addActionListener(this); 
	btnLuu.addActionListener(this); 
	btnLoc.addActionListener(this);
	}

/*** Nap dữ liêu lúc chương trình khời đông */ 
	 private void LoadDatabase() {
		BufferedReader br = null;
		thuvien=new ThuVien() ;//khởi đông danh sách
		try {
		//doc danh mue hàng
		if (new File(FILENAME).exists()){
		br = new BufferedReader(new FileReader(FILENAME) ) ;
		//BỎ qua dòng tiêu đề cột
		br.readLine();
		while(br.ready()){
		String line = br.readLine();
		if(line != null && !line.trim().equals("")){
		String[] a = line.split(";");
		Sach s = new Sach(a[0], a[1], a[2],
		Integer.parseInt(a[3]), a[4], Integer.parseInt(a[5]), Double.parseDouble(a[6]), a[7]);
		//đưa vào danh mue
		thuvien.themSach(s); 
		tableModel.addRow(a);
		}
	}
}
}catch(Exception ex){
		ex.printStackTrace();
		}
	}
	 private void SaveDatabase(ArrayList<Sach> dsSach) {
		    BufferedWriter bw = null;

		    try {
		        bw = new BufferedWriter(new FileWriter(FILENAME));

		        // Write the header row
		        bw.write("MaSach;TuaSach;TacGia;NamXuatBan;NhaXuatBan;SoTrang;DonGia;ISBN\n");

		        // Write each Sach object to the file
		        for (Sach s : dsSach) {
		            String line = s.getMaSach() + ";" + s.getTuaSach() + ";" + s.getTacGia() + ";"
		                    + s.getNamXB() + ";" + s.getNhaXB() + ";" + s.getSoTrang() + ";"
		                    + s.getDonGia() + ";" + s.getlsbn();
		            bw.write(line + "\n");
		        }
		    } catch (Exception ex) {
		        ex.printStackTrace();
		    } finally {
		        try {
		            if (bw != null) {
		                bw.close();
		            }
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		    }
		}
	private void fillForm(int row) {
	if(row != -1){
	String maSach = (String) table.getValueAt(row, 0);
	Sach s = new Sach(maSach);
	ArrayList<Sach> dsSach = thuvien.getDsSach();
	if(thuvien.getDsSach().contains(s)){
		s = dsSach.get(dsSach.indexOf(s));
		txtMasach.setText(s.getMaSach());
		txtTuaSach.setText(s.getTuaSach());
		txtTacGia.setText(s.getTacGia()); 
		txtNhaXB.setText(s.getNhaXB());
		txtNamXB.setText(s.getNamXB() + "" );
		txtSotrang.setText(s.getSoTrang()+"");
		txtDonGia.setText(s.getDonGia() +"");
		txtISBN.setText(s.getlsbn());
		txtMasach.setEditable(false);
		}
	}
	}
	private void clearTextfields() {
		txtMasach.setText("");
		txtTuaSach.setText("");
		txtTacGia.setText("");
		txtNamXB.setText("");
		txtNhaXB.setText("");
		txtSotrang.setText("");
		txtDonGia.setText("");
		txtISBN.setText("");
		txtMasach.setEditable(true);
		txtMasach.requestFocus();
	} 
	private void showMessage(String message, JTextField txt) { txt.requestFocus();
	txtMess.setText(message);
	}
	String maSach,tuaSach,tacGia,nam,nhaXB,trang,gia,isbn;
	int namXB;
	int soTrang;
	double donGia ;
	private Sach revertSachFromTextfields() {
	maSach = txtMasach.getText() .trim();
	tuaSach = txtTuaSach.getText().trim();
	tacGia = txtTacGia.getText().trim();
	nam = txtNamXB.getText().trim();
	namXB = nam.length() == 0 ? 0 : Integer.parseInt(nam); //Để trống thì coi như là 0
	nhaXB = txtNhaXB.getText() .trim();
	trang = txtSotrang.getText() .trim();
	soTrang = trang.length() == 0 ? 0 : Integer.parseInt(trang);
	gia = txtDonGia.getText() .trim();
	donGia = gia.equals("") ? 0 : Double.parseDouble(gia);
	isbn = txtISBN.getText().trim();
	return new Sach(maSach, tuaSach, tacGia, namXB, nhaXB, soTrang, donGia, isbn);
	}
	private boolean validData() {
	String maSach = txtMasach.getText().trim();
	String tuaSach = txtTuaSach.getText().trim();
	String tacGia = txtTacGia.getText().trim();
	String nam = txtNamXB.getText().trim();
	String gia = txtDonGia.getText().trim();
	String isbn = txtISBN.getText().trim();
	String soTrang = txtSotrang.getText().trim();
	if(!(maSach.length() > 0 && maSach.matches("[A-Z]\\d{3}"))){ showMessage("Error: Mã sách theo mẫu: rA-Z]\\d{3}", txtMasach);
	JOptionPane.showMessageDialog(this,"Error: Mã sách theo mẫu: [A-Z]\\d{3}");
	return false;
	}
	if(!(tuaSach.length() > 0 && tuaSach.matches("[a-zA-Z']+"))){ showMessage("Error: Tựa sách theo mẫu: [a-zA-Z' ]+", txtTuaSach);
	return false;
	}
	if ( ! (tacGia.length() > 0 && tacGia.matches("[a-zA-Z' ]+"))){ showMessage("Error: Tác giả theo mẫu: [a-zA-Z‘ ]+", txtTacGia);
	return false;
	}
	if(nam.length() > 0){
	try{
	int x = Integer.parseInt(nam) ;
	int namHienTai =
	Calendar.getInstance().get(Calendar.YEAR) ; 
	if(!(x >= 1900 && x <=namHienTai)){ showMessage("Error: Năm xuất bản >= 1900 && <= "+  namHienTai, txtNamXB);
	JOptionPane.showMessageDialog(this,"Error: Năm xuất bản >= 1900 && <="+ namHienTai);
	return false;
	}
	}catch(NumberFormatException ex){ showMessage("Error: Năm xuất bản phải nhập số.", txtNamXB);
	return false;
	}
	}
	if(soTrang.length() > 0){
	try{
	int x = Integer.parseInt(soTrang) ;
	if(x <= 0){
	showMessage("Error: số trang phải nhập số nguyên dương.", txtSotrang);
	return false;
	}
	}catch(NumberFormatException ex){ showMessage("Error: Sô' trang phải nhập số nguyên dương.", txtSotrang) ;
	return false;
	}
	}
	if(gia.length() > 0){
		try{
		double y = Double.parseDouble(gia);
		if(y < 0){
		showMessage("Error: Đơn giá phải > 0.", txtDonGia); return false;
		}
		}catch(NumberFormatException ex){ showMessage("Error: Đơn giá phải nhập số.", txtDonGia); return false;
		}
		}
		if(isbn.length() > 0)
		if (!isbn.matches("\\d+(-\\d+){3,4}")){ showMessage("Error: ISBN có mẫu dạng X-X-X-X (hoặc X-X-X-X-X).", txtISBN);
		return false;
		}
		return true;
		}

	@Override
	public void actionPerformed(ActionEvent e) {
	Object o = e.getSource();
	if(o.equals(cboMaSach)){
	String maSach = (String) cboMaSach.getSelectedItem();
	Sach s = thuvien.timKiem(maSach); if(s != null){
	int index = thuvien.getDsSach().indexOf(s); fillForm(index);
	table.getSelectionModel().setSelectionInterval(index, index);
	table.scrollRectToVisible(table.getCellRect(index, index ,true));
	}	
	}else if (o.equals(btnXoa)){
	int row = table.getSelectedRow();
	if(row != -1){
	int hoiNhac = JOptionPane.showConfirmDialog(this, "Chắc chắn xóa không? ", "Chú ý", JOptionPane.YES_NO_OPTION);
	if(hoiNhac == JOptionPane.YES_OPTION) {
	if(thuvien.xoalCuonSach(row)){
	tableModel.removeRow(row);
	txtMess.setText("Đã xóa 1 cuốn sách.");
	clearTextfields();
	}
	}
	}else
	txtMess.setText("Bạn phải chọn cuốn sách cần xóa. ");
	}else if(o.equals(btnThem)){
	if(validData()){
	Sach s = revertSachFromTextfields();
	String []row= {maSach, tuaSach, tacGia,
	Integer.toString(namXB), nhaXB, Integer.toString(soTrang),
	Double.toString(donGia), isbn+""};
	tableModel.addRow(row);
	if(thuvien.themSach(s)){
	txtMess.setText("Thêm thành công 1 cuốn sách.");
	}else{
	showMessage("Error: Trùng mã sách", txtMasach);
	}
	}
	}else if(o.equals(btnXoaRong)){
	clearTextfields();
	}else if(o.equals(btnSua)){
	String maSachOld = txtMasach.getText().trim();
	Sach sachNew = revertSachFromTextfields(); 
	if(thuvien.capNhatSach(maSachOld, sachNew)){ 
		txtMess.setText("Cập nhật thành công. ");
	//lay dong dang chon tren table 
	int row = table.getSelectedRow();
	tableModel.setValueAt( txtTuaSach.getText(), row,1);
	tableModel.setValueAt( txtTacGia.getText(), row, 2 );
	tableModel.setValueAt( txtNamXB.getText(), row, 3 );
	tableModel.setValueAt( txtNhaXB.getText(), row, 4 );
	tableModel.setValueAt( txtSotrang.getText(), row, 5); 
	tableModel.setValueAt( txtDonGia.getText(), row, 6);
	tableModel.setValueAt( txtISBN.getText(), row, 7);
	}else
	txtMess.setText("C'ân chọn cuốn sách để cập nhật. "); }
	else if(o.equals(btnLuu)){
	SaveDatabase(thuvien.getDsSach()); txtMess.setText("Lưu thành công. ");
	}}

	
	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    FrmDanhMucSach frm = new FrmDanhMucSach();
                    frm.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
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



	
	

