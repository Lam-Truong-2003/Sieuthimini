package GUI;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.time.LocalDate;

public class ChamCong extends JFrame {
    private JPanel contentPane;
    private JTextField txtMaNV, txtNgay, txtGioVao, txtGioRa, txtSoGioLam, txtTrangThai;
    private JTable table;
    private JButton btnThem, btnSua, btnLuu, btnLamMoi;

    public ChamCong() throws SQLException {
        setTitle("Quản lý Chấm Công Nhân Viên");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1100, 700);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Tiêu đề
        JLabel lblTitle = new JLabel("QUẢN LÝ CHẤM CÔNG NHÂN VIÊN");
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setBounds(0, 0, 1096, 52);
        contentPane.add(lblTitle);

        // Thêm các textfields và labels
        JPanel panelThongTin = new JPanel();
        panelThongTin.setBorder(BorderFactory.createTitledBorder("Thông tin chấm công"));
        panelThongTin.setBounds(0, 46, 1070, 262);
        panelThongTin.setLayout(new GridLayout(6, 4, 10, 10));  // Grid layout để sắp xếp các thành phần
        contentPane.add(panelThongTin);

        // Các trường nhập liệu
        panelThongTin.add(new JLabel("Mã Nhân Viên:"));
        txtMaNV = new JTextField();
        panelThongTin.add(txtMaNV);

        panelThongTin.add(new JLabel("Ngày:"));
        txtNgay = new JTextField();
        panelThongTin.add(txtNgay);

        panelThongTin.add(new JLabel("Giờ vào:"));
        txtGioVao = new JTextField();
        panelThongTin.add(txtGioVao);

        panelThongTin.add(new JLabel("Giờ ra:"));
        txtGioRa = new JTextField();
        panelThongTin.add(txtGioRa);

        panelThongTin.add(new JLabel("Số giờ làm:"));
        txtSoGioLam = new JTextField();
        txtSoGioLam.setEnabled(false);
        panelThongTin.add(txtSoGioLam);

        panelThongTin.add(new JLabel("Trạng thái:"));
        txtTrangThai = new JTextField();
        panelThongTin.add(txtTrangThai);

        // Thêm các nút chức năng
        JPanel panelButtons = new JPanel();
        panelButtons.setBounds(10, 318, 1060, 67);
        contentPane.add(panelButtons);

        btnThem = new JButton("Thêm");
        panelButtons.add(btnThem);

        btnSua = new JButton("Sửa");
        panelButtons.add(btnSua);

        btnLuu = new JButton("Lưu");
        panelButtons.add(btnLuu);

        btnLamMoi = new JButton("Làm mới"); 
        panelButtons.add(btnLamMoi);
        
        JComboBox comboloc = new JComboBox();
        panelButtons.add(comboloc);

        // Thêm bảng để hiển thị danh sách chấm công
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 395, 1060, 268);
        contentPane.add(scrollPane);

        table = new JTable();
        DefaultTableModel model = new DefaultTableModel(
            new Object[][] {},
            new String[] {
                "Mã CC", "Mã NV", "Ngày", "Giờ vào", "Giờ ra", "Số giờ làm", "Trạng thái"
            }
        );
        table.setModel(model);
        scrollPane.setViewportView(table);
    }

    public static void main(String[] args) {
        try {
            ChamCong frame = new ChamCong();
            frame.setVisible(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
