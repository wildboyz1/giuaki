import java.io.Serializable;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class ThuVien {
	private ArrayList<Sach> dsSach;
	public ThuVien() {
		dsSach = new ArrayList<Sach>(10);
}
public boolean themSach(Sach s) { 
	if(dsSach.contains(s))
		return false;
	return dsSach.add(s);
}
public boolean xoalCuonSach(int index) {
	if(index >= 0 && index < dsSach.size()){
		dsSach.remove(index);
return true;
}
return false;
}
public ArrayList<Sach> getDsSach() { 
	return dsSach;
}
public Sach GetSach(int index) {
	if(index<0| index>dsSach.size()) 
		return null;
	return dsSach.get(index);
	}
	public Sach timKiem(String maSach) { 
		Sach s = new Sach(maSach);
		if(dsSach.contains(s))
	return dsSach.get(dsSach.indexOf(s)); 
		return null;
	}
	/**
	*	Cập nhật thõng tin cuốn sách khi biết mã sách
	*	Không đươc phép câp nhãt trên trường khóa
	*	@param maSachOld
	*	@param sachNew
	*	@return
	*/
	public boolean capNhatSach(String maSachOld, Sach sachNew) {
	Sach sachOld = new Sach(maSachOld);
	if(dsSach.contains(sachOld)){
	sachOld = dsSach.get(dsSach.indexOf(sachOld));
	sachOld.setTuaSach(sachNew.getTuaSach());
	sachOld.setTacGia(sachNew.getTacGia());
	sachOld.setNamXB(sachNew.getNamXB());
	sachOld.setNhaXB(sachNew.getNhaXB());
	sachOld.setSoTrang(sachNew.getSoTrang()); 
	sachOld.setDonGia(sachNew.getDonGia()); 
	sachOld.setIsbn(sachNew.getlsbn());
	return true;
	}
	return false;
	}
	}

