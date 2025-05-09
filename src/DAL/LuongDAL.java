package DAL;

import java.math.BigDecimal;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import DTO.KhachHang;
import DTO.Luong;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LuongDAL extends connectSql {

    public LuongDAL() throws SQLException {
        super();
    }

    public ArrayList<Luong> docLuong(String condition) {
        ArrayList<Luong> arrList = new ArrayList<>();
        String sql = "";

        try {
            if (condition.equals("docluong")) {
                sql = "SELECT * FROM LUONG";
            }

            PreparedStatement pstm = conn.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                Luong luong = new Luong();

                // Lấy các giá trị từ ResultSet và gán vào đối tượng Luong
                luong.setMaL(rs.getInt("MaLuong"));
                luong.setMaNV(rs.getString("MaNV"));
                luong.setThangnam(rs.getString("ThangNam"));
                luong.setSoNGAYCONG(rs.getInt("SoNgayCong"));
                luong.setSoGIOLAM(rs.getInt("SoGioLam"));

                // Lấy giá trị tiền tệ bằng float
                float tienLuongCoBan = rs.getFloat("TienLuongCoBan");
                float tienPhat = rs.getFloat("TienPhat");
                float phuCap = rs.getFloat("PhuCap");
                float tienThuong = rs.getFloat("TienThuong");
                float tongLuong = rs.getFloat("TongLuong");
                String ngayChotLuong = rs.getString("NgayChotLuong");

                // Đặt giá trị vào đối tượng Luong
                luong.setTienluongcoban(tienLuongCoBan);
                luong.setTienphat(tienPhat);
                luong.setPhucap(phuCap);
                luong.setTienthuong(tienThuong);
                luong.setTongluong(tongLuong);
                luong.setNgaychotluong(ngayChotLuong);

                arrList.add(luong);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return arrList;
    }
    public String layMaLcuoi() throws SQLException {
        String sql = "SELECT MAX(MaLuong) FROM LUONG";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } 
        return null;
    }
    public String getMaNV(String name) throws SQLException {
        String MaNV = "";  // Lưu mã nhân viên vào biến này

        // Thay đổi cột 'TenKH' thành tên cột đúng
        String sql = "SELECT MaNV FROM NHANVIEN WHERE TenNV LIKE ?";  // Thay 'TenNV' cho đúng với cột trong cơ sở dữ liệu
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);  // Thêm tham số tên nhân viên vào câu lệnh
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                MaNV = rs.getString("MaNV");  // Lấy mã nhân viên từ cột 'MaNV'
            }
        }
        return MaNV;  // Trả về mã nhân viên
    }
    public String getMaCV(String name) throws SQLException {
        String MaCV = "";  // Lưu mã nhân viên vào biến này

        // Thay đổi cột 'TenKH' thành tên cột đúng
        String sql = "SELECT MaCV FROM NHANVIEN WHERE MaNV LIKE ?";  // Thay 'TenNV' cho đúng với cột trong cơ sở dữ liệu
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);  // Thêm tham số tên nhân viên vào câu lệnh
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                MaCV = rs.getString("MaCV");  // Lấy mã nhân viên từ cột 'MaNV'
            }
        }
        return MaCV;  // Trả về mã nhân viên
    }
    public String getTongNgayCong(String maNV, String month, String year) throws SQLException {
        String tongNgayCong = "0";  // Khởi tạo tổng ngày công là 0

        // Câu lệnh SQL để tính tổng số ngày công của nhân viên trong tháng và năm cụ thể
        String sql = "SELECT count(*) AS TongNgayCong " +
                     "FROM CHAMCONG " +
                     "WHERE MaNV = ? AND MONTH(Ngay) = ? AND YEAR(Ngay) = ? " +
                     "AND (TrangThai = 'ĐÚNG GIỜ' OR TrangThai = 'TRỄ')";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Đặt các tham số cho câu lệnh SQL
            pstmt.setString(1, maNV);  // Mã nhân viên
            pstmt.setString(2, month);  // Tháng (tháng mà bạn muốn tính)
            pstmt.setString(3, year);   // Năm (năm mà bạn muốn tính)

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                tongNgayCong = rs.getString("TongNgayCong");
            }
        }

        return tongNgayCong;  // Trả về tổng số ngày công
    }

    public String getTongGioLam(String maNV, String month, String year) throws SQLException {
        String tongGioLam = "0";  // Khởi tạo tổng số giờ làm là 0

        // Câu lệnh SQL để tính tổng số giờ làm của nhân viên trong tháng hiện tại
        String sql = "SELECT SUM(SoGioLam) AS TongGioLam " +
                     "FROM CHAMCONG " +
                     "WHERE MaNV = ? AND MONTH(Ngay) = ? AND YEAR(Ngay) = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Đặt các tham số cho câu lệnh SQL
            pstmt.setString(1, maNV);  // Mã nhân viên
            pstmt.setString(2, month);  // Tháng (tháng mà bạn muốn tính)
            pstmt.setString(3, year);   // Năm (năm mà bạn muốn tính)

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                tongGioLam = rs.getString("TongGioLam");
            } else {
                System.out.println("Không có dữ liệu hoặc không có giờ làm nào cho mã NV: " + maNV);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi trong khi truy vấn: " + e.getMessage());
        }

        return tongGioLam;  // Trả về tổng số giờ làm
    }
    public String getTongSoNgayTre(String maNV, String month, String year) throws SQLException {
        String tongNgayTre = "0";  // Khởi tạo tổng ngày trễ là 0

        // Câu lệnh SQL để tính tổng số ngày trễ của nhân viên trong tháng và năm
        String sql = "SELECT count(*) AS TongNgayTre " +
                     "FROM CHAMCONG " +
                     "WHERE MaNV = ? AND MONTH(Ngay) = ? AND YEAR(Ngay) = ? " +
                     "AND TrangThai = 'TRỄ'";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Đặt các tham số cho câu lệnh SQL
            pstmt.setString(1, maNV);  // Mã nhân viên
            pstmt.setString(2, month);  // Tháng
            pstmt.setString(3, year);   // Năm

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                tongNgayTre = rs.getString("TongNgayTre");
            }
        }

        return tongNgayTre;  // Trả về tổng số ngày trễ
    }

    public String getTongSoNgayDungGio(String maNV, String month, String year) throws SQLException {
        String tongNgayDung = "0";  // Khởi tạo tổng số ngày đúng giờ là 0

        // Câu lệnh SQL để tính tổng số ngày đúng giờ của nhân viên trong tháng và năm
        String sql = "SELECT count(*) AS TongNgayDung " +
                     "FROM CHAMCONG " +
                     "WHERE MaNV = ? AND MONTH(Ngay) = ? AND YEAR(Ngay) = ? " +
                     "AND TrangThai = 'ĐÚNG GIỜ'";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Đặt các tham số cho câu lệnh SQL
            pstmt.setString(1, maNV);  // Mã nhân viên
            pstmt.setString(2, month);  // Tháng
            pstmt.setString(3, year);   // Năm

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                tongNgayDung = rs.getString("TongNgayDung");
            }
        }

        return tongNgayDung;  // Trả về tổng số ngày đúng giờ
    }

    public String getTongSoNgayNghi(String maNV, String month, String year) throws SQLException {
        String tongNgayNghi = "0";  // Khởi tạo tổng số ngày nghỉ phép là 0

        // Câu lệnh SQL để tính tổng số ngày nghỉ phép của nhân viên trong tháng và năm
        String sql = "SELECT count(*) AS TongNgayNghi " +
                     "FROM CHAMCONG " +
                     "WHERE MaNV = ? AND MONTH(Ngay) = ? AND YEAR(Ngay) = ? " +
                     "AND TrangThai = 'NGHỈ PHÉP'";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Đặt các tham số cho câu lệnh SQL
            pstmt.setString(1, maNV);  // Mã nhân viên
            pstmt.setString(2, month);  // Tháng
            pstmt.setString(3, year);   // Năm

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                tongNgayNghi = rs.getString("TongNgayNghi");
            }
        }

        return tongNgayNghi;  // Trả về tổng số ngày nghỉ phép
    }

    public boolean themLuong(Luong luong) throws SQLException {
        String sql = "INSERT INTO LUONG ( MaNV, ThangNam, SoNgayCong, SoGioLam, TienLuongCoBan, TienPhat, PhuCap, TienThuong, TongLuong, NgayChotLuong) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
           
            pstm.setString(1, luong.getMaNV());  // MaNV
            pstm.setString(2, luong.getThangnam());  // ThangNam
            pstm.setInt(3, luong.getSoNGAYCONG());  // SoNgayCong
            pstm.setFloat(4, luong.getSoGIOLAM());  // SoGioLam
            pstm.setFloat(5, luong.getTienluongcoban());  // TienLuongCoBan
            pstm.setFloat(6, luong.getTienphat());  // TienPhat
            pstm.setFloat(7, luong.getPhucap());  // PhuCap
            pstm.setFloat(8, luong.getTienthuong());  // TienThuong
            pstm.setFloat(9, luong.getTongluong());  // TongLuong
            pstm.setDate(10, java.sql.Date.valueOf(luong.getNgaychotluong()));  // NgayChotLuong

            pstm.executeUpdate();
            closeConnection();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            closeConnection();
            return false;
        }
    }
    public boolean suaTienPhucap(String maL, BigDecimal tienPhucap, BigDecimal tientong) throws SQLException {
        String sql = "UPDATE LUONG SET PhuCap = ?, TongLuong = ? WHERE MaLuong = ?";
        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            // Thiết lập các giá trị cho PreparedStatement
            pstm.setBigDecimal(1, tienPhucap);  // Cập nhật tiền phụ cấp
            pstm.setBigDecimal(2, tientong);    // Cập nhật tổng lương
            pstm.setString(3, maL);             // Cập nhật mã lương

            // Thực hiện câu lệnh cập nhật
            int rowsUpdated = pstm.executeUpdate();

            // Trả về true nếu có dòng dữ liệu được cập nhật, ngược lại trả về false
            return rowsUpdated > 0;
        }
    }

    public static void main(String[] args) throws SQLException {
        // Tạo đối tượng LuongDAL để kết nối và đọc dữ liệu
        LuongDAL luongDAL = new LuongDAL();

        // Mã nhân viên ví dụ
        String maNV = "NV002";

        // Lấy tháng và năm hiện tại từ LocalDate
        LocalDate currentDate = LocalDate.now();  // Lấy ngày hiện tại
        String currentMonth = String.valueOf(currentDate.getMonthValue());  // Tháng hiện tại
        String currentYear = String.valueOf(currentDate.getYear());  // Năm hiện tại
        System.out.println("Month: " + currentMonth);
        System.out.println("Year: " + currentYear);

        // Gọi phương thức để tính tổng ngày công của nhân viên
        String tongNgayCong = luongDAL.getTongNgayCong(maNV, currentMonth, currentYear);
        System.out.println("Tổng số ngày công của nhân viên " + maNV + " trong tháng hiện tại: " + tongNgayCong);

        // Gọi phương thức để tính tổng số giờ làm của nhân viên
        String tongGioLam = luongDAL.getTongGioLam(maNV, currentMonth, currentYear);
        System.out.println("Tổng số giờ làm của nhân viên " + maNV + " trong tháng hiện tại: " + tongGioLam);
    }

}

    