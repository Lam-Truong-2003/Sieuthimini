package BLL;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

import DAL.KhachHangDAL;
import DAL.LuongDAL;
import DTO.KhachHang;
import DTO.Luong;

public class LuongBLL {
	public ArrayList<Luong> getLuong(String condition) throws SQLException {
		LuongDAL luong = new LuongDAL();
		ArrayList<Luong> arr = new ArrayList<Luong>();
		arr = luong.docLuong(condition);
		return arr;
	}
	public String getLastMaL() throws SQLException {
		LuongDAL luongDAL = new LuongDAL();
		return luongDAL.layMaLcuoi();
	}
	public boolean addLuong(Luong obj) throws SQLException {
		LuongDAL luong = new LuongDAL();
		return luong.themLuong(obj);
	}
	public boolean updateLuong(String mal, BigDecimal tienphucap,BigDecimal tientong) throws SQLException {
	    LuongDAL luong = new LuongDAL();
	    return luong.suaTienPhucap(mal, tienphucap,tientong);
	}}
