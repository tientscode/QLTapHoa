/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import DAO.HoaDonDao;
import DAO.KhachHangDao;
import DAO.NhanVienDao;
import DAO.SanPhamDao;
import entity.DateHelper;
import entity.DialogHelper;
import entity.HoaDon;
import entity.KhachHang;
import entity.SanPham;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import entity.ShareHelper;
import java.awt.Image;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Admin
 */
public final class DonDatHang extends javax.swing.JFrame {

    int index = 0;
    HoaDonDao dao = new HoaDonDao();
    NhanVienDao nvdao = new NhanVienDao();
    KhachHangDao KH = new KhachHangDao();
    List<HoaDon> HoaDon = dao.selectAll();
    SanPhamDao dao1 = new SanPhamDao();

    /**
     * Creates new form DonDatHang
     */
    public DonDatHang() {
        initComponents();
        setLocationRelativeTo(null);
        fillToTable();
        setStatus(true);
        ChiTietHoaDonJDialog.setLocationRelativeTo(null);

    }

    public void fillToTable() {
        DefaultTableModel model = (DefaultTableModel) tblDanhSach.getModel();
        model.setRowCount(0);
        try {
            List<HoaDon> list = dao.selectAll();
            for (HoaDon hd : list) {
                Object[] row = {
                    hd.getMaHD(),
                    hd.getMaKH(),
                    hd.getMaSP(),
                    hd.getTenSP(),
                    hd.getLoai(),
                    hd.getNgayLap(),
                    hd.getTotalPrice(),
                    hd.getSoLuong()

                };
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void fillComboBox() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboLoai.getModel();
        model.removeAllElements();
        try {
            List<HoaDon> list = dao.selectAll();
            Set<String> uniqueLoai = new HashSet<>();
            for (HoaDon hd : list) {
                if (!uniqueLoai.contains(hd.getLoai().trim())) {
                    uniqueLoai.add(hd.getLoai().trim());
                    model.addElement(hd);

                }
            }

        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    public boolean check() {
        if (txtMaHD.getText().equals("") || txtMaKH.getText().equals("") || txtMaSP.getText().equals("") || txtTenSP.getText().equals("")
                || txtTotalPrice.getText().equals("") || txtNgayLap.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Hãy nhập đủ dữ liệu sau đó ấn Save", "Error", 1);
            return false;
        }
        try {
            int masp = Integer.parseInt(txtMaSP.getText());
            if (masp <= 0) {
                JOptionPane.showMessageDialog(this, "Ma san pham phai dung dinh dang !");
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ma san pham phai la so !");
            return false;
        }
        try {
            int sl = Integer.parseInt(txtSL.getText());
            if (sl <= 0) {
                JOptionPane.showMessageDialog(this, "So luong phai lon hon 0 !");
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "So luong phai la so !");
            return false;
        }
        try {
            float Gia = Float.parseFloat(txtTotalPrice.getText());
            if (Gia <= 0) {
                JOptionPane.showMessageDialog(this, "Thanh tien phai lon hon 0 !");
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Thanh tien phai la so !");
            return false;
        }
        SimpleDateFormat formater = new SimpleDateFormat();
        formater.applyPattern("yyyy-DD-mm");
        try {
            Date ngay = formater.parse(txtNgayLap.getText());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ngay Sinh khong hop le !");
            return false;

        }
        return true;

    }

    void setModel(HoaDon model) {
        txtMaHD.setText(String.valueOf(model.getMaHD()));
        txtMaKH.setText(String.valueOf(model.getMaKH()));
        txtMaSP.setText(String.valueOf(model.getMaSP()));
        txtTenSP.setText(String.valueOf(model.getTenSP()));

        DefaultComboBoxModel modelcbo = (DefaultComboBoxModel) cboLoai.getModel();
        int size = modelcbo.getSize();
        String targetLoai = model.getLoai();

        for (int i = 0; i < size; i++) {
            HoaDon element = (HoaDon) modelcbo.getElementAt(i);
            String currentLoai = element.getLoai();

            if (currentLoai != null && targetLoai != null && currentLoai.trim().equals(targetLoai.trim())) {
                cboLoai.setSelectedIndex(i);
                break;
            }
        }

        try {
            int maSP = model.getMaSP();
            SanPham sanPham = dao1.selectById(maSP);

            if (sanPham != null) {
                float giaSanPham = sanPham.getGia();
                System.out.println(giaSanPham);
                int soLuong = model.getSoLuong();
                System.out.println(soLuong);

                float totalPrice = giaSanPham * soLuong;

                txtTotalPrice.setText(String.format("%.2f", totalPrice));
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu sản phẩm!");
        }

        txtNgayLap.setText(String.valueOf(model.getNgayLap()));
        txtSL.setText(String.valueOf(model.getSoLuong()));
    }

    void setModel1(KhachHang model) {
        txtMaKH.setText(String.valueOf(model.getMaKH()));
        txtname.setText(String.valueOf(model.getHoTen()));
        txtemail.setText(String.valueOf(model.getEmail()));
        txtsdt.setText(String.valueOf(model.getSDT()));
        txtdiachi.setText(model.getDiaChi());
    }

    void setModel2(SanPham model) {
        txtgia.setText(String.valueOf(model.getGia()));
        lblanh.setToolTipText(model.getHinhAnh());
        if (model.getHinhAnh() != null) {
            ResizeImage(model.getHinhAnh());
            lblanh.setIcon(ShareHelper.readLogo(model.getHinhAnh()));
        }
    }

    public void ResizeImage(String imageName) {
        ImageIcon icon = new ImageIcon("src\\img\\" + imageName);
        Image image = icon.getImage();
        ImageIcon icon1 = new ImageIcon(image.getScaledInstance(150, 187, image.SCALE_SMOOTH));
        lblanh.setIcon(icon1);

    }

    HoaDon getModel() {
        HoaDon model = new HoaDon();
        model.setMaHD(String.valueOf(txtMaHD.getText()));
        model.setMaKH(String.valueOf(txtMaKH.getText()));
        model.setMaSP(Integer.valueOf(txtMaSP.getText()));
        model.setTenSP(String.valueOf(txtTenSP.getText()));
        model.setLoai(String.valueOf(cboLoai.getSelectedItem()));
        model.setTotalPrice(Float.valueOf(txtTotalPrice.getText()));
        model.setNgayLap(DateHelper.toDate(txtNgayLap.getText()));
        model.setSoLuong(Integer.valueOf(txtSL.getText()));

        return model;
    }

    void setStatus(boolean insertable
    ) {
        btnSua.setEnabled(!insertable);
//        btnThem.setEnabled(!insertable);
        btnXoa.setEnabled(!insertable);

        boolean first = this.index > 0;
        boolean last = this.index < tblDanhSach.getRowCount() - 1;
        btnFirst.setEnabled(!insertable && first);
        btnPrev.setEnabled(!insertable && first);
        btnLast.setEnabled(!insertable && last);
        btnNext.setEnabled(!insertable && last);
    }

    void insert() {
        HoaDon model = getModel();
        if (ShareHelper.USER.isVaiTro()) {
            try {
                dao.insert(model);
                this.fillToTable();
                this.clear();
                DialogHelper.alert(this, "Thêm mới thành công!");
            } catch (Exception e) {
                e.printStackTrace();
                DialogHelper.alert(this, "Thêm mới thất bại!");
            }
        } else {
            DialogHelper.alert(this, "Bạn không có đủ quyền hạn để làm việc này");
        }
    }

    void update() {
        HoaDon model = getModel();
        try {
            dao.update(model);
            this.fillToTable();
            DialogHelper.alert(this, "Cập nhật thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(this, "Cập nhật thất bại!");
        }
    }

    void delete() {
        HoaDon model = getModel();
        if (DialogHelper.confirm(this, "Bạn có muốn xóa hay không?")) {
            if (ShareHelper.USER.isVaiTro()) {
                String mahd = String.valueOf(txtMaHD.getText());
                try {
                    dao.delete(mahd);
                    this.fillToTable();
                    this.clear();
                    DialogHelper.alert(this, "Xóa thành công!");
                } catch (Exception e) {
                    DialogHelper.alert(this, "Xóa thất bại!");
                }
            } else {
                DialogHelper.alert(this, "Bạn không có đủ quyền hạn để xoá");
            }
        }
    }

    void edit() {
        try {
            String mahd = (String) tblDanhSach.getValueAt(this.index, 0);
            HoaDon model = dao.selectById(mahd);
            if (model != null) {
                this.setModel(model);
                this.setStatus(false);
                tblDanhSach.setRowSelectionInterval(index, index);
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void edit1() {
        try {
//            String makh = (String) tblDanhSach.getValueAt(this.index, 1);
            String makh = txtMaKH.getText();
            KhachHang model = KH.selectById(makh);
            if (model != null) {
                this.setModel1(model);
                this.setStatus(false);
                tblDanhSach.setRowSelectionInterval(index, index);
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void edit2() {
        try {
            int maSP = (int) tblDanhSach.getValueAt(this.index, 2);
            SanPham model = dao1.selectById(maSP);
            if (model != null) {
                this.setModel2(model);
                this.setStatus(false);
                tblDanhSach.setRowSelectionInterval(index, index);
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void clear() {
        int selectedRowIndex = tblDanhSach.getSelectedRow();
        if (selectedRowIndex != -1) {
            tblDanhSach.clearSelection();
        }
        this.setModel(new HoaDon());
        this.setStatus(false);
    }

    public static void exportExcelHD(List<HoaDon> HoaDon) {
        try {
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet("HoaDon");
            XSSFRow row = null;
            Cell cell = null;
            int check = 1;

            row = sheet.createRow(0);
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue("Mã hoá đơn");

            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue("Mã khách hàng");

            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue("Mã sản phẩm");

            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue("Tên sản phẩm");

            cell = row.createCell(4, CellType.STRING);
            cell.setCellValue("Ngày lập");

            cell = row.createCell(5, CellType.STRING);
            cell.setCellValue("Thành tiền");

            cell = row.createCell(6, CellType.STRING);
            cell.setCellValue("Loại");

            cell = row.createCell(7, CellType.STRING);
            cell.setCellValue("Số lượng");

            for (int i = 0; i < HoaDon.size(); i++) {
                //Modelbook book =arr.get(i);
                row = sheet.createRow(i + 1);

                cell = row.createCell(0, CellType.NUMERIC);
                cell.setCellValue(HoaDon.get(i).getMaHD());

                cell = row.createCell(1, CellType.STRING);
                cell.setCellValue(HoaDon.get(i).getMaKH());

                cell = row.createCell(2, CellType.STRING);
                cell.setCellValue(HoaDon.get(i).getMaSP());

                cell = row.createCell(3, CellType.STRING);
                cell.setCellValue(HoaDon.get(i).getTenSP());

                cell = row.createCell(4, CellType.STRING);
                cell.setCellValue(HoaDon.get(i).getNgayLap().toString());

                cell = row.createCell(5, CellType.STRING);
                cell.setCellValue(HoaDon.get(i).getTotalPrice());

                cell = row.createCell(6, CellType.STRING);
                cell.setCellValue(HoaDon.get(i).getLoai());

                cell = row.createCell(7, CellType.STRING);
                cell.setCellValue(HoaDon.get(i).getSoLuong());
            }

            File f = new File("D:\\FPT Polytechnic\\HoaDon.xlsx");

            while (f.exists()) {
                String nfn = "D:\\FPT Polytechnic\\HoaDon" + check + ".xlsx";
                f = new File(nfn);
                check++;
                System.out.println(nfn);
            }

            try {
                FileOutputStream fis = new FileOutputStream(f);
                wb.write(fis);
                fis.close();
                DialogHelper.alert(null, "Export success!!");
            } catch (IOException ex) {
                DialogHelper.alert(null, "Không tìm thấy địa chỉ export!!");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            DialogHelper.alert(null, "Lỗi ở hàng hoặc cột không thể getData");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ChiTietHoaDonJDialog = new javax.swing.JDialog();
        jLabel11 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        lblMaHD = new javax.swing.JLabel();
        lblMaKH = new javax.swing.JLabel();
        lblMaSP = new javax.swing.JLabel();
        lblTenSP = new javax.swing.JLabel();
        lblLoai = new javax.swing.JLabel();
        lblNgaylap = new javax.swing.JLabel();
        lblThanhTien = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        lblSoLuong = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblNgayLap = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtMaHD = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtMaKH = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDanhSach = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cboLoai = new javax.swing.JComboBox<>();
        txtMaSP = new javax.swing.JTextField();
        txtTenSP = new javax.swing.JTextField();
        txtTotalPrice = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtNgayLap = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtSL = new javax.swing.JTextField();
        txtname = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblanh = new javax.swing.JLabel();
        txtsdt = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        txtemail = new javax.swing.JTextField();
        txtgia = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtdiachi = new javax.swing.JTextArea();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        HDCT = new javax.swing.JButton();
        ExportExcel = new javax.swing.JButton();

        ChiTietHoaDonJDialog.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        ChiTietHoaDonJDialog.setTitle("Chi tiết hoá đơn");
        ChiTietHoaDonJDialog.setMinimumSize(new java.awt.Dimension(330, 340));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 102, 102));
        jLabel11.setText("CHI TIẾT HOÁ ĐƠN");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel12.setText("Mã Hóa Đơn : ");

        jLabel14.setText("Mã khách hàng : ");

        jLabel16.setText("Mã sản phẩm : ");

        jLabel18.setText("Ngày đặt hàng: ");

        jLabel10.setText("Tên Sản Phẩm : ");

        jLabel15.setText("Loại : ");

        jLabel17.setText("Thành Tiền : ");

        jLabel19.setText("Số lượng:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING)))
                            .addComponent(jLabel14)
                            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(lblSoLuong, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblThanhTien, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                                .addComponent(lblNgaylap, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblMaHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addGap(13, 13, 13)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(lblTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblNgaylap, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblThanhTien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel17)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19)
                    .addComponent(lblSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout ChiTietHoaDonJDialogLayout = new javax.swing.GroupLayout(ChiTietHoaDonJDialog.getContentPane());
        ChiTietHoaDonJDialog.getContentPane().setLayout(ChiTietHoaDonJDialogLayout);
        ChiTietHoaDonJDialogLayout.setHorizontalGroup(
            ChiTietHoaDonJDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ChiTietHoaDonJDialogLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addGap(142, 142, 142))
            .addGroup(ChiTietHoaDonJDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        ChiTietHoaDonJDialogLayout.setVerticalGroup(
            ChiTietHoaDonJDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ChiTietHoaDonJDialogLayout.createSequentialGroup()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(83, Short.MAX_VALUE))
        );

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 102));
        jLabel1.setText("QUẢN LÝ ĐƠN ĐẶT HÀNG");

        lblNgayLap.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setText("Mã hóa đơn ");

        jLabel3.setText("Mã khách hàng");

        jLabel4.setText("Thành tiền");

        tblDanhSach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã HD", "Mã KH", "Mã SP", "Tên SP", "Loại", "Ngày lập", "Giá", "Số lượng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDanhSach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDanhSachMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDanhSach);

        jLabel5.setText("Mã sản phẩm");

        jLabel7.setText("Tên sản phẩm");

        jLabel6.setText("Loại");

        txtTotalPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalPriceActionPerformed(evt);
            }
        });

        jLabel8.setText("Ngày Lập");

        jLabel9.setText("Số điện thoại");

        jLabel13.setText("Số lượng");

        jLabel20.setText("Tên khách hàng");

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblanh, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(lblanh, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel22.setText("Email");

        jLabel23.setText("Giá");

        jLabel24.setText("Địa chỉ");

        txtdiachi.setColumns(20);
        txtdiachi.setRows(5);
        jScrollPane2.setViewportView(txtdiachi);

        javax.swing.GroupLayout lblNgayLapLayout = new javax.swing.GroupLayout(lblNgayLap);
        lblNgayLap.setLayout(lblNgayLapLayout);
        lblNgayLapLayout.setHorizontalGroup(
            lblNgayLapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lblNgayLapLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(lblNgayLapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(lblNgayLapLayout.createSequentialGroup()
                        .addGroup(lblNgayLapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(lblNgayLapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtname, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addGroup(lblNgayLapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtNgayLap, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE))))
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(lblNgayLapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtemail, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                        .addComponent(txtsdt, javax.swing.GroupLayout.Alignment.LEADING))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(77, 77, 77)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(lblNgayLapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(lblNgayLapLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(lblNgayLapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(lblNgayLapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel6)
                                .addComponent(jLabel5))
                            .addGroup(lblNgayLapLayout.createSequentialGroup()
                                .addGap(49, 49, 49)
                                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(lblNgayLapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtgia, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(lblNgayLapLayout.createSequentialGroup()
                                .addGroup(lblNgayLapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cboLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(lblNgayLapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(18, 18, 18)
                                .addGroup(lblNgayLapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtSL, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(lblNgayLapLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(lblNgayLapLayout.createSequentialGroup()
                        .addGap(143, 143, 143)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(txtTotalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        lblNgayLapLayout.setVerticalGroup(
            lblNgayLapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lblNgayLapLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(lblNgayLapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(lblNgayLapLayout.createSequentialGroup()
                        .addGroup(lblNgayLapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(48, 48, 48)
                        .addGroup(lblNgayLapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(lblNgayLapLayout.createSequentialGroup()
                                .addGroup(lblNgayLapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5))
                                .addGap(18, 18, 18)
                                .addGroup(lblNgayLapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(cboLoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(lblNgayLapLayout.createSequentialGroup()
                                .addGroup(lblNgayLapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(lblNgayLapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel13)
                                    .addComponent(txtSL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addGroup(lblNgayLapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtgia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23))
                        .addGap(28, 28, 28)
                        .addGroup(lblNgayLapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtTotalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(lblNgayLapLayout.createSequentialGroup()
                        .addGroup(lblNgayLapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1)
                            .addGroup(lblNgayLapLayout.createSequentialGroup()
                                .addGroup(lblNgayLapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(lblNgayLapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNgayLap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(lblNgayLapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel20))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(lblNgayLapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtsdt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel22)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtemail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel24)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );

        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Save.png"))); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Sync.png"))); // NOI18N
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Delete.png"))); // NOI18N
        btnXoa.setText("Xoá");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Add.png"))); // NOI18N
        btnMoi.setText("Mới");
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });

        btnLast.setText(">|");
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        btnNext.setText(">>");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnPrev.setText("<<");
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnFirst.setText("|<");
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        HDCT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/orderdetails.png"))); // NOI18N
        HDCT.setText("Chi tiết hoá đơn");
        HDCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HDCTActionPerformed(evt);
            }
        });

        ExportExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/excel.png"))); // NOI18N
        ExportExcel.setText("ExportExcel");
        ExportExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExportExcelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(lblNgayLap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnThem)
                        .addGap(6, 6, 6)
                        .addComponent(btnSua)
                        .addGap(6, 6, 6)
                        .addComponent(btnXoa)
                        .addGap(6, 6, 6)
                        .addComponent(btnMoi)
                        .addGap(18, 18, 18)
                        .addComponent(ExportExcel)
                        .addGap(18, 18, 18)
                        .addComponent(HDCT)
                        .addGap(149, 149, 149)
                        .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(lblNgayLap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnThem)
                    .addComponent(btnSua)
                    .addComponent(btnXoa)
                    .addComponent(btnMoi)
                    .addComponent(ExportExcel)
                    .addComponent(HDCT)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnFirst)
                            .addComponent(btnPrev)
                            .addComponent(btnNext)
                            .addComponent(btnLast)))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        this.index++;
        this.edit();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        if (check()) {
            this.insert();
        } else {
            DialogHelper.alert(this, "Lỗi, vui lòng xem lại ");
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void tblDanhSachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDanhSachMouseClicked

        if (evt.getClickCount() == 1) {
            this.index = tblDanhSach.getSelectedRow();
            this.edit();
            this.edit1();
            this.edit2();
        }
    }//GEN-LAST:event_tblDanhSachMouseClicked

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        this.clear();
    }//GEN-LAST:event_btnMoiActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        if (check()) {
            this.update();
        } else {
            DialogHelper.alert(this, "Lỗi, vui lòng xem lại ");
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void HDCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HDCTActionPerformed
        ChiTietHoaDonJDialog.setVisible(true);
        setLocationRelativeTo(null);
        HoaDon hd = getModel();
        lblMaHD.setText(hd.getMaHD());
        lblMaKH.setText(hd.getMaKH());
        lblMaSP.setText(String.valueOf(hd.getMaSP()));
        lblTenSP.setText(hd.getTenSP());
        lblLoai.setText(hd.getLoai());
        lblNgaylap.setText(DateHelper.toString(hd.getNgayLap()));
        lblThanhTien.setText(String.valueOf(hd.getTotalPrice()));
        lblSoLuong.setText(String.valueOf(hd.getSoLuong()));

    }//GEN-LAST:event_HDCTActionPerformed

    private void ExportExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExportExcelActionPerformed
        exportExcelHD(HoaDon);
    }//GEN-LAST:event_ExportExcelActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        this.index--;
        this.edit();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        this.index = 0;
        this.edit();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        this.index = tblDanhSach.getRowCount() - 1;
        this.edit();
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        this.delete();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        this.fillComboBox();
        fillToTable();
        this.setStatus(true);

    }//GEN-LAST:event_formWindowOpened

    private void txtTotalPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalPriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalPriceActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DonDatHang.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DonDatHang.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DonDatHang.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DonDatHang.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DonDatHang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog ChiTietHoaDonJDialog;
    private javax.swing.JButton ExportExcel;
    private javax.swing.JButton HDCT;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cboLoai;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblLoai;
    private javax.swing.JLabel lblMaHD;
    private javax.swing.JLabel lblMaKH;
    private javax.swing.JLabel lblMaSP;
    private javax.swing.JPanel lblNgayLap;
    private javax.swing.JLabel lblNgaylap;
    private javax.swing.JLabel lblSoLuong;
    private javax.swing.JLabel lblTenSP;
    private javax.swing.JLabel lblThanhTien;
    private javax.swing.JLabel lblanh;
    private javax.swing.JTable tblDanhSach;
    private javax.swing.JTextField txtMaHD;
    private javax.swing.JTextField txtMaKH;
    private javax.swing.JTextField txtMaSP;
    private javax.swing.JTextField txtNgayLap;
    private javax.swing.JTextField txtSL;
    private javax.swing.JTextField txtTenSP;
    private javax.swing.JTextField txtTotalPrice;
    private javax.swing.JTextArea txtdiachi;
    private javax.swing.JTextField txtemail;
    private javax.swing.JTextField txtgia;
    private javax.swing.JTextField txtname;
    private javax.swing.JTextField txtsdt;
    // End of variables declaration//GEN-END:variables
}
