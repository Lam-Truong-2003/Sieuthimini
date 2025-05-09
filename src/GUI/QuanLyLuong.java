package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.google.protobuf.TextFormat.ParseException;
import com.toedter.calendar.JDateChooser;

import BLL.KhachHangBLL;
import BLL.LuongBLL;
import DAL.KhachHangDAL;
import DAL.LuongDAL;
import DTO.KhachHang;
import DTO.Luong;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

public class QuanLyLuong extends JFrame {

    /**
	 * 
	 */
	private JDateChooser txtNgayChot;
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private JTable table;
    private JTextField txtMaNV, txtThangNam, txtNgayCong, txtGioLam, txtLuongCoBan, txtPhuCap, txtThuong, txtPhat, txtTongLuong;
    private JButton btnThem, btnSua, btnLuu, btnLamMoi;
    private JButton btnTinhLuong;
    boolean addbtn, fixbtn = false;
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                QuanLyLuong frame = new QuanLyLuong();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
   
    public QuanLyLuong() throws SQLException {
    	
        setTitle("Quản lý Lương Nhân Viên");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1100, 700);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(null); // Dùng layout null cho phép điều chỉnh tự do vị trí các component

        // Thêm nút "Hệ thống" cạnh góc trái
        JButton btnHeThong = new JButton("Hệ thống");
        btnHeThong.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		setVisible(false);
        		AdminHome admin= new AdminHome();
        		admin.setLocationRelativeTo(null);
        		admin.setVisible(true);
        	}
        });
        btnHeThong.setBounds(94, 10, 120, 40); // Đặt vị trí của nút (x, y, width, height)
        contentPane.add(btnHeThong);

        // Thêm tiêu đề "QUẢN LÝ TIỀN LƯƠNG NHÂN VIÊN"
        JLabel lblTitle = new JLabel("QUẢN LÝ TIỀN LƯƠNG NHÂN VIÊN");
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setBounds(36, 0, 1060, 52);  // Đặt tiêu đề cách nút "Hệ thống"
        contentPane.add(lblTitle);
        	
        // Tiếp tục các phần còn lại của UI...
    

        JPanel panelThongTin = new JPanel();
        panelThongTin.setBorder(BorderFactory.createTitledBorder("Thông tin lương"));
        panelThongTin.setBounds(0, 46, 1070, 262);
        panelThongTin.setLayout(new GridLayout(8, 4, 10, 10));  // Cập nhật số dòng của GridLayout
        contentPane.add(panelThongTin);

        // Các label và textfield
        panelThongTin.add(new JLabel("Mã Lương:"));
        JTextField txtMaLuong = new JTextField();
        panelThongTin.add(txtMaLuong);

        panelThongTin.add(new JLabel("Mã NV:"));
        JTextField txtMaNV = new JTextField();
        panelThongTin.add(txtMaNV);

        panelThongTin.add(new JLabel("Tháng/Năm:"));
        JTextField txtThangNam = new JTextField();
        panelThongTin.add(txtThangNam);
        txtThangNam.setEditable(false);
        panelThongTin.add(new JLabel("Số ngày công:"));
        JTextField txtNgayCong = new JTextField();
        panelThongTin.add(txtNgayCong);
        txtNgayCong.setEditable(false);
        panelThongTin.add(new JLabel("Số giờ làm:"));
        JTextField txtGioLam = new JTextField();
        panelThongTin.add(txtGioLam);

        panelThongTin.add(new JLabel("Lương cơ bản:"));
        JTextField txtLuongCoBan = new JTextField();
        panelThongTin.add(txtLuongCoBan);

        panelThongTin.add(new JLabel("Phụ cấp:"));
        JTextField txtPhuCap = new JTextField();
        panelThongTin.add(txtPhuCap);

        panelThongTin.add(new JLabel("Tiền thưởng:"));
        JTextField txtThuong = new JTextField();
        panelThongTin.add(txtThuong);

        panelThongTin.add(new JLabel("Tiền phạt:"));
        JTextField txtPhat = new JTextField();
        panelThongTin.add(txtPhat);
        panelThongTin.add(new JLabel("Tổng lương:"));
        JTextField txtTongLuong = new JTextField();
        txtTongLuong.setEnabled(false); // Tổng tự động tính
        panelThongTin.add(txtTongLuong);

        panelThongTin.add(new JLabel("Ngày chốt lương:"));
        txtNgayChot = new JDateChooser();
        panelThongTin.add(txtNgayChot);
        txtNgayChot.addPropertyChangeListener("date", new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                if (txtNgayChot.getDate() != null) {
                    // Định dạng ngày theo kiểu yyyy-MM-dd (ngày-tháng-năm)
                    String ngayChot = new SimpleDateFormat("yyyy-MM-dd").format(txtNgayChot.getDate());
                    // Gán giá trị này cho các mục khác nếu cần
                    System.out.println(ngayChot); // In ra để kiểm tra
                }
            }
        });
        // Thêm label và textfield cho "Họ tên nhân viên"
        panelThongTin.add(new JLabel("Họ tên nhân viên:"));
        JTextField txtHoTen = new JTextField();
        panelThongTin.add(txtHoTen);

        // Thêm label và textfield cho "Mã chức vụ"
        panelThongTin.add(new JLabel("Mã chức vụ:"));  // Thêm Label "Mã chức vụ"
        JTextField txtMaChucVu = new JTextField();  // Thêm TextField cho mã chức vụ
        panelThongTin.add(txtMaChucVu);  // Thêm vào Panel
        txtHoTen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LuongDAL luong = null;
                try {
                    luong = new LuongDAL();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Lỗi kết nối với cơ sở dữ liệu!");
                    return;
                }

                try {
                    // Lấy mã nhân viên từ tên
                    String MaNV = luong.getMaNV(txtHoTen.getText());
                    txtMaNV.setText(MaNV);

                    // Lấy tháng và năm hiện tại
                    LocalDate currentDate = LocalDate.now();
                    String currentMonth = String.valueOf(currentDate.getMonthValue());  // Tháng hiện tại
                    String currentYear = String.valueOf(currentDate.getYear());  // Năm hiện tại

                    // Lấy tổng số ngày công
                    String songay = luong.getTongNgayCong(txtMaNV.getText(), currentMonth, currentYear);
                    txtNgayCong.setText(songay);

                    // Lấy số giờ làm từ phương thức
                    String sogio = luong.getTongGioLam(txtMaNV.getText(), currentMonth, currentYear);
                    double soGioLam = 0;
                    try {
                        soGioLam = Double.parseDouble(sogio);  // Chuyển đổi chuỗi sang số thập phân
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Số giờ làm không hợp lệ!");
                        return;
                    }

                    // Hiển thị số giờ làm với một chữ số thập phân
                    txtGioLam.setText(String.format("%.1f", soGioLam));

                    // Lấy số ngày nghỉ phép
                    String songaynghiphep = luong.getTongSoNgayNghi(txtMaNV.getText(), currentMonth, currentYear);

                    // Kiểm tra nếu có ngày nghỉ phép và thêm 4 giờ vào số giờ làm
                    if (songaynghiphep != null && Integer.parseInt(songaynghiphep) == 1) {
                        soGioLam += 4;  // Cộng thêm 4 giờ vào số giờ làm
                        txtGioLam.setText(String.format("%.1f", soGioLam));  // Cập nhật lại giá trị giờ làm
                    }

                    // Lấy số ngày trễ
                    // Lấy mã chức vụ
                    String macv = luong.getMaCV(txtMaNV.getText());
                    txtMaChucVu.setText(macv);

                    // Kiểm tra mã chức vụ và gán lương cơ bản tương ứng
                    if ("CV2".equals(txtMaChucVu.getText())) {
                        txtLuongCoBan.setText("50,000");
                    } else if ("CV3".equals(txtMaChucVu.getText()) || "CV4".equals(txtMaChucVu.getText())) {
                        txtLuongCoBan.setText("40,000");
                    } else {
                        txtLuongCoBan.setText("0");
                    }

                } catch (SQLException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Lỗi khi truy xuất dữ liệu!");
                }

                try {
                    // Tính tiền phạt nếu có
                    LocalDate currentDate = LocalDate.now();
                    String currentMonth = String.valueOf(currentDate.getMonthValue());  // Tháng hiện tại
                    String currentYear = String.valueOf(currentDate.getYear());  // Năm hiện tại

                    String songaytre = luong.getTongSoNgayTre(txtMaNV.getText(), currentMonth, currentYear);
                    if (songaytre != null && Integer.parseInt(songaytre) >= 1) {
                        // Chuyển số ngày trễ sang BigDecimal
                        BigDecimal soNgayTre = new BigDecimal(songaytre);
                        BigDecimal phat = soNgayTre.multiply(BigDecimal.valueOf(50000));  // Nhân với 50.000 VND
                        
                        // Định dạng số tiền phạt với dấu phân cách hàng nghìn
                        DecimalFormat df = new DecimalFormat("#,###.##");
                        String formattedPhat = df.format(phat);
                        
                        // Hiển thị tiền phạt vào text field
                        txtPhat.setText(formattedPhat);  // Hiển thị kết quả vào text field
                    } else {
                        txtPhat.setText("0");
                    }

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                try {
                    // Tính tiền thưởng nếu có
                    LocalDate currentDate = LocalDate.now();
                    String currentMonth = String.valueOf(currentDate.getMonthValue());  // Tháng hiện tại
                    String currentYear = String.valueOf(currentDate.getYear());  // Năm hiện tại

                    // Lấy tổng số ngày đúng giờ
                    String tien = luong.getTongSoNgayDungGio(txtMaNV.getText(), currentMonth, currentYear);

                    if (tien != null) {
                        // Chuyển đổi giá trị trả về thành số
						int tienthuong = Integer.parseInt(tien);
						
						// Kiểm tra số ngày đúng giờ và gán tiền thưởng
						if (tienthuong >= 24) {
						    txtThuong.setText("200,000");
						} else {
						    txtThuong.setText("0");
						}
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });


        // Panel nút chức năng
        JPanel panelButtons = new JPanel();
        panelButtons.setBounds(10, 318, 1060, 67);
        contentPane.add(panelButtons);

        JButton btnThem = new JButton("Thêm");
        btnThem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	btnLuu.setEnabled(true);
                LuongBLL lBLL = new LuongBLL();
                try {
                    String lastMal = lBLL.getLastMaL();
                    int lastMalInt = Integer.parseInt(lastMal);
                    txtMaLuong.setText(String.valueOf(lastMalInt + 1));
                    btnLamMoi.setEnabled(false);
                    btnSua.setEnabled(false);
                    addbtn = true;
                    LocalDate currentDate = LocalDate.now();

                 // Lấy tháng và năm hiện tại
                 String currentMonth = String.format("%02d", currentDate.getMonthValue());  // Tháng hiện tại (với định dạng 2 chữ số)
                 String currentYear = String.valueOf(currentDate.getYear());  // Năm hiện tại

                 // Tạo chuỗi Tháng/Năm
                 String thangNam = currentYear + "-" + currentMonth;

                 // Đặt giá trị cho txtNgayCong (hoặc bất kỳ trường JTextField nào bạn muốn)
                 txtThangNam.setText(thangNam);
                    try {
                        hienthiluong("them");
                    } catch (SQLException e3) {
                        // TODO Auto-generated catch block
                        e3.printStackTrace();
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnThem.setIcon(new ImageIcon("D:\\ECLIPSE\\Github\\SieuThiMini\\SieuThiMini\\bin\\GUI\\Image\\Add.png"));
        panelButtons.add(btnThem);

        btnSua = new JButton("Sửa");
        btnSua.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		fixbtn=true;
        		btnThem.setEnabled(false);
        		btnLuu.setEnabled(true);
        	}
        });
        btnSua.setIcon(new ImageIcon("D:\\ECLIPSE\\Github\\SieuThiMini\\SieuThiMini\\bin\\GUI\\Image\\Change.png"));
        btnLuu = new JButton("Lưu");
        btnLuu.setEnabled(false);
      
        btnLuu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // Kiểm tra các trường nhập liệu chỉ khi addbtn là true
                    if (addbtn) {
                        // Thêm mã lương mới
                        int confirmed = JOptionPane.showConfirmDialog(null,
                                "Bạn đã chắc thông tin lưu mã lương này không ",
                                "Confirmation", JOptionPane.YES_NO_OPTION);
                        if (confirmed == JOptionPane.YES_OPTION) {
                            // Tạo đối tượng Luong và gán các giá trị từ các trường nhập liệu
                            Luong l = new Luong();
                            LuongBLL luul = new LuongBLL();

                            try {
                                // Cập nhật giá trị cho đối tượng Luong
                                l.setMaNV(txtMaNV.getText());

                                // Chuyển đổi ngày tháng về kiểu java.sql.Date
                                Date ngayChot = new java.sql.Date(txtNgayChot.getDate().getTime());  // Chuyển Date
                                l.setNgaychotluong(ngayChot.toString());  // Set ngày vào đối tượng Luong

                                // Cập nhật số ngày công và số giờ làm
                                l.setSoNGAYCONG(Integer.parseInt(txtNgayCong.getText()));  // Chuyển đổi thành số
                                l.setSoGIOLAM(Float.parseFloat(txtGioLam.getText())); // Chuyển đổi thành số
                                l.setThangnam(txtThangNam.getText());

                                // Loại bỏ dấu phẩy (nếu có) và trim trước khi chuyển đổi
                                String luongCoBanStr = txtLuongCoBan.getText().replace(",", "").trim();
                                String phatStr = txtPhat.getText().replace(",", "").trim();
                                String thuongStr = txtThuong.getText().replace(",", "").trim();
                                String phuCapStr = txtPhuCap.getText().replace(",", "").trim();
                                String tongLuongStr = txtTongLuong.getText().replace(",", "").trim();

                                // Đảm bảo rằng DecimalFormat dùng dấu chấm phân cách thập phân
                                DecimalFormat df = new DecimalFormat("#,###.##");
                                df.setParseBigDecimal(true);  // Đảm bảo rằng số sẽ được xử lý chính xác

                                // Cố gắng chuyển đổi các giá trị thành số sau khi xử lý dấu phẩy
                                l.setTienluongcoban(df.parse(luongCoBanStr).floatValue());
                                l.setTienphat(df.parse(phatStr).floatValue());
                                l.setTienthuong(df.parse(thuongStr).floatValue());
                                l.setPhucap(df.parse(phuCapStr).floatValue());
                                l.setTongluong(df.parse(tongLuongStr).floatValue());

                                // Lưu thông tin vào cơ sở dữ liệu
                                luul.addLuong(l); // Giả sử `addLuong` là phương thức để lưu thông tin

                                // Hiển thị thông báo thành công và cập nhật bảng
                                JOptionPane.showMessageDialog(contentPane, "Thêm thành công");
                                btnSua.setEnabled(true);
                                btnLamMoi.setEnabled(true);
                                hienthiluong("hien thi");  // Gọi lại hàm hiển thị dữ liệu
                                addbtn = false;  // Đặt lại trạng thái addbtn sau khi thêm
                            } catch (NumberFormatException ex) {
                                // Xử lý lỗi nếu có giá trị không hợp lệ trong các trường số
                                JOptionPane.showMessageDialog(contentPane, "Vui lòng nhập đúng định dạng số!");
                            }
                        } else {
                            JOptionPane.showMessageDialog(contentPane, "Thêm thất bại");
                        }
                    }

                    // Xử lý sửa thông tin (fix)
                    if (fixbtn) {
                        int confirmed = JOptionPane.showConfirmDialog(null, 
                                "Bạn có muốn sửa lại tiền phụ cấp của mã lương này " + txtMaLuong.getText(),
                                "Confirmation", JOptionPane.YES_NO_OPTION);
                        if (confirmed == JOptionPane.YES_OPTION) {
                            LuongBLL luul = new LuongBLL();
                            String phuCapStr = txtPhuCap.getText().replace(",", "");
                            BigDecimal phuCap = new BigDecimal(phuCapStr);
                            String tientongStr = txtTongLuong.getText().replace(",", "");
                            BigDecimal tientong = new BigDecimal(tientongStr);
                            // Cập nhật tiền phụ cấp
                            boolean checkAddPro = luul.updateLuong(txtMaLuong.getText(), phuCap,tientong);
                          
                            if (checkAddPro) {
                                JOptionPane.showMessageDialog(contentPane, "Sửa thành công");
                                hienthiluong("hien thi");  // Cập nhật lại bảng sau khi sửa
                                fixbtn = false;  // Đặt lại trạng thái fixbtn sau khi sửa
                                btnThem.setEnabled(true);
                                btnLamMoi.setEnabled(true);
                            } else {
                                JOptionPane.showMessageDialog(contentPane, "Sửa thất bại");
                            }
                        }
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(contentPane, "Vui lòng nhập đúng định dạng số!");
                    ex.printStackTrace();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(contentPane, "Lỗi kết nối cơ sở dữ liệu!");
                    ex.printStackTrace();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(contentPane, "Đã xảy ra lỗi không mong muốn!");
                    ex.printStackTrace();
                }
            }
        });

        // Phương thức kiểm tra tính hợp lệ của các trường nhập liệu
        

        btnLuu.setIcon(new ImageIcon("D:\\ECLIPSE\\Github\\SieuThiMini\\SieuThiMini\\bin\\GUI\\Image\\Save.png"));
        btnLamMoi = new JButton("Làm mới");
        btnLamMoi.addActionListener(new ActionListener() {

			@Override
		    public void actionPerformed(ActionEvent e) {
		        // Reset các JTextField
				 if (txtMaNV != null) txtMaNV.setText("");
				    if (txtThangNam != null) txtThangNam.setText("");
				    if (txtNgayCong != null) txtNgayCong.setText("");
				    if (txtGioLam != null) txtGioLam.setText("");
				    if (txtLuongCoBan != null) txtLuongCoBan.setText("");
				    if (txtPhuCap != null) txtPhuCap.setText("");
				    if (txtThuong != null) txtThuong.setText("");
				    if (txtPhat != null) txtPhat.setText("");
				    if (txtTongLuong != null) txtTongLuong.setText("");
				    if (txtNgayChot != null) txtNgayChot.setDate(null);  // Nếu là JDateChooser

			}});
        btnLamMoi.setIcon(new ImageIcon("D:\\ECLIPSE\\Github\\SieuThiMini\\SieuThiMini\\bin\\GUI\\Image\\Refresh-icon.png"));

        panelButtons.add(btnThem);
        panelButtons.add(btnSua);
        panelButtons.add(btnLuu);
        panelButtons.add(btnLamMoi);

        btnTinhLuong = new JButton("Tính lương");
        btnTinhLuong.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // Kiểm tra nếu các trường không rỗng và chuyển các giá trị từ JTextField thành BigDecimal
                    if (txtGioLam.getText().isEmpty() || txtLuongCoBan.getText().isEmpty() || txtThuong.getText().isEmpty() || 
                        txtPhuCap.getText().isEmpty() || txtPhat.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!");
                        return; // Dừng thực thi nếu có trường bị bỏ trống
                    }

                    // Xử lý dấu phẩy và chuyển các giá trị từ JTextField thành BigDecimal
                    BigDecimal gioLam = new BigDecimal(txtGioLam.getText().replace(",", "")); // Vẫn giữ gioLam là BigDecimal
                    BigDecimal luongCoBan = new BigDecimal(txtLuongCoBan.getText().replace(",", ""));
                    BigDecimal thuong = new BigDecimal(txtThuong.getText().replace(",", ""));
                    BigDecimal phuCap = new BigDecimal(txtPhuCap.getText().replace(",", ""));
                    BigDecimal phat = new BigDecimal(txtPhat.getText().replace(",", ""));

                    // Tính tổng lương (gioLam * luongCoBan + thuong + phuCap - phat)
                    BigDecimal tongTien = gioLam.multiply(luongCoBan).add(thuong).add(phuCap).subtract(phat);

                    // Định dạng tổng lương
                    DecimalFormat df = new DecimalFormat("#,###.##"); // Định dạng với dấu phân cách hàng nghìn
                    String formattedTongTien = df.format(tongTien);

                    // Hiển thị tổng lương
                    txtTongLuong.setText(formattedTongTien);

                } catch (NumberFormatException ex) {
                    // Xử lý lỗi nếu người dùng nhập không phải là số
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đúng giá trị số!");
                }
            }
        });


        btnTinhLuong.setIcon(new ImageIcon("D:\\ECLIPSE\\Github\\SieuThiMini\\SieuThiMini\\bin\\GUI\\Image\\Pay.png"));
        panelButtons.add(btnTinhLuong);

        // Bảng dữ liệu
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 395, 1060, 268);
        contentPane.add(scrollPane);

        table = new JTable();
        DefaultTableModel model = new DefaultTableModel(
            new Object[][] {},
            new String[] {
                "Mã Lương", "Mã nhân viên", "Tháng-Năm", "Số ngày công", "Số giờ làm",
                "Tiền lương cơ bản", "Tiền phạt", "Phụ cấp", "Tiền thưởng", "Tổng lương", "Ngày chốt"
            }
        );
        table.setModel(model); // Khởi tạo model trước khi gọi hienthiluong
        table.setRowHeight(25);
        scrollPane.setViewportView(table);

        // Gọi hienthiluong sau khi table được khởi tạo
        hienthiluong("hien thi");

        // Xử lý sự kiện click dòng
     // Xử lý sự kiện click dòng
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();

                // Lấy giá trị từ bảng và gán vào các JTextField
                txtMaLuong.setText(table.getValueAt(row, 0).toString());  // Lấy "Mã Lương" từ cột đầu tiên
                txtMaNV.setText(table.getValueAt(row, 1).toString());      // Lấy "Mã NV"
                txtThangNam.setText(table.getValueAt(row, 2).toString());  // Lấy "Tháng/Năm"
                txtNgayCong.setText(table.getValueAt(row, 3).toString());  // Lấy "Số ngày công"
                txtGioLam.setText(table.getValueAt(row, 4).toString());    // Lấy "Số giờ làm"
                txtLuongCoBan.setText(table.getValueAt(row, 5).toString()); // Lấy "Lương cơ bản"
               txtPhat.setText(table.getValueAt(row, 6).toString());    // Lấy "Phụ cấp"
               txtPhuCap.setText(table.getValueAt(row, 7).toString());    // Lấy "Tiền thưởng"
                txtThuong.setText(table.getValueAt(row, 8).toString());      // Lấy "Tiền phạt"
                txtTongLuong.setText(table.getValueAt(row, 9).toString()); // Lấy "Tổng lương"
                txtNgayChot.setToolTipText(table.getValueAt(row, 10).toString()); // Lấy "Ngày chốt"
            }
        });


        // Gán sự kiện cho nút Làm mới
        btnLamMoi.addActionListener(e -> clearForm());
    }


    private void clearForm() {
        txtMaNV.setText("");
        txtThangNam.setText("");
        txtNgayCong.setText("");
        txtGioLam.setText("");
        txtLuongCoBan.setText("");
        txtPhuCap.setText("");
        txtThuong.setText("");
        txtPhat.setText("");
        txtTongLuong.setText("");
    }
    public void hienthiluong(String condition) throws SQLException {
        LuongBLL lBll = new LuongBLL();
        ArrayList<Luong> arrl = new ArrayList<Luong>();
        if (condition.equals("hien thi")) {
            arrl = lBll.getLuong("docluong");
        }
        if (condition == "them") {
            arrl = lBll.getLuong("docluong");
        }


        String[] columnNames = {
            "Mã Lương", "Mã nhân viên", "Tháng-Năm", "Số ngày công", "Số giờ làm",
            "Tiền lương cơ bản", "Tiền phạt", "Phụ cấp", "Tiền thưởng", "Tổng lương", "Ngày chốt"
        };

        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        table.setModel(model);
        model.setRowCount(0);

        DecimalFormat df = new DecimalFormat("###,###,###"); // Định dạng số với dấu phẩy

        for (Luong ldata : arrl) {
            Object[] row = new Object[]{
                ldata.getMaL(),
                ldata.getMaNV(),
                ldata.getThangnam(),
                ldata.getSoNGAYCONG(),
                ldata.getSoGIOLAM(),
                df.format(ldata.getTienluongcoban()), // Định dạng tiền lương cơ bản
                df.format(ldata.getTienphat()), // Định dạng tiền phạt
                df.format(ldata.getPhucap()),
               
                 // Định dạng phụ cấp
                df.format(ldata.getTienthuong()), // Định dạng tiền thưởng
                df.format(ldata.getTongluong()), // Định dạng tổng lương
                ldata.getNgaychotluong()
            };
            model.addRow(row);
        }
    }
}
