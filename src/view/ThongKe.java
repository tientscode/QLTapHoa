/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import DAO.ThongKeDao;
import java.sql.Connection;
import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import entity.SanPham;
import entity.DialogHelper;
import entity.HoaDon;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author ADMIN
 */
public class ThongKe extends javax.swing.JFrame {

    ThongKeDao dao = new ThongKeDao();
//    ArrayList<HoaDon> arr = new ArrayList<HoaDon>();

    /**
     * Creates new form ThongKe
     */
    public ThongKe() {
        initComponents();
        fillToTable();
        setLocationRelativeTo(null);
        tblHoaDon.setEnabled(false);
        ThongBaoNe.setLocationRelativeTo(null);
    }

    public void fillToTable() {

        DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
        model.setRowCount(0);
        try {
            List<HoaDon> list = dao.selectHoaDon();
            for (HoaDon hd : list) {
                Object[] row = {
                    hd.getMaHD(),
                    hd.getMaKH(),
                    hd.getMaSP(),
                    hd.getTenSP(),
                    hd.getLoai(),
                    //                  hd.getNgayLap(),
                    hd.getSoLuong(),
                    hd.getTotalPrice(),};
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Quy1() {
        DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
        model.setRowCount(0);
        try {
            List<HoaDon> list = dao.selectQuy1();
            for (HoaDon hd : list) {
                Object[] row = {
                    hd.getMaHD(),
                    hd.getMaKH(),
                    hd.getMaSP(),
                    hd.getTenSP(),
                    hd.getLoai(),
                    //                  hd.getNgayLap(),
                    hd.getSoLuong(),
                    hd.getTotalPrice(),};
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Quy2() {
        DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
        model.setRowCount(0);
        try {
            List<HoaDon> list = dao.selectQuy2();
            for (HoaDon hd : list) {
                Object[] row = {
                    hd.getMaHD(),
                    hd.getMaKH(),
                    hd.getMaSP(),
                    hd.getTenSP(),
                    hd.getLoai(),
                    //                  hd.getNgayLap(),
                    hd.getSoLuong(),
                    hd.getTotalPrice(),};
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Quy3() {
        DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
        model.setRowCount(0);
        try {
            List<HoaDon> list = dao.selectQuy3();
            for (HoaDon hd : list) {
                Object[] row = {
                    hd.getMaHD(),
                    hd.getMaKH(),
                    hd.getMaSP(),
                    hd.getTenSP(),
                    hd.getLoai(),
                    //                  hd.getNgayLap(),
                    hd.getSoLuong(),
                    hd.getTotalPrice(),};
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Quy4() {
        DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
        model.setRowCount(0);
        try {
            List<HoaDon> list = dao.selectQuy4();
            for (HoaDon hd : list) {
                Object[] row = {
                    hd.getMaHD(),
                    hd.getMaKH(),
                    hd.getMaSP(),
                    hd.getTenSP(),
                    hd.getLoai(),
                    //                  hd.getNgayLap(),
                    hd.getSoLuong(),
                    hd.getTotalPrice(),};
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void ExportExcelThongKe() {
        DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
        int rowCount = model.getRowCount();

        try {
            XSSFWorkbook wordkbook = new XSSFWorkbook();
            XSSFSheet sheet = wordkbook.createSheet("ThongKeTheoQuy");
            XSSFRow row = null;
            Cell cell = null;
            row = sheet.createRow(0);
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue("Mã hoá đơn");

            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue("Mã khách hàng");

            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue("Mã sản phẩm");

            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue("Tên sản phẩm");

//            cell = row.createCell(4, CellType.STRING);
//            cell.setCellValue("Ngày lập");
            cell = row.createCell(4, CellType.STRING);
            cell.setCellValue("Thành tiền");

            cell = row.createCell(5, CellType.STRING);
            cell.setCellValue("Số lượng");

            cell = row.createCell(6, CellType.STRING);
            cell.setCellValue("Loại");

            for (int i = 0; i < rowCount; i++) {
                row = sheet.createRow(i + 1);
                for (int j = 0; j < model.getColumnCount(); j++) {
                    cell = row.createCell(j, CellType.STRING);
                    cell.setCellValue(String.valueOf(model.getValueAt(i, j)));
                }
            }
            String a = txtFileName.getText();
            File f = new File("D:\\FPT Polytechnic\\" + a+".xlsx");
            int check = 1;
            while (f.exists()) {
                String nfn = f + "_" + check + ".xlsx";
                f = new File(nfn);
                check++;
                System.out.println(nfn);
            }

            try {
                FileOutputStream fis = new FileOutputStream(f);
                wordkbook.write(fis);
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

        ThongBaoNe = new javax.swing.JDialog();
        btnSave = new javax.swing.JButton();
        txtFileName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        tabs = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        btnQuy1 = new javax.swing.JButton();
        btnTatCa = new javax.swing.JButton();
        btnQuy2 = new javax.swing.JButton();
        btnQuy3 = new javax.swing.JButton();
        btnQuy4 = new javax.swing.JButton();
        btnExcel = new javax.swing.JButton();

        ThongBaoNe.setTitle("Hệ thống quản lý cửa hàng tạp hoá T&T");
        ThongBaoNe.setMinimumSize(new java.awt.Dimension(400, 180));

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("File's name");

        javax.swing.GroupLayout ThongBaoNeLayout = new javax.swing.GroupLayout(ThongBaoNe.getContentPane());
        ThongBaoNe.getContentPane().setLayout(ThongBaoNeLayout);
        ThongBaoNeLayout.setHorizontalGroup(
            ThongBaoNeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ThongBaoNeLayout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addGroup(ThongBaoNeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnSave)
                    .addGroup(ThongBaoNeLayout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtFileName, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(41, 41, 41))
        );
        ThongBaoNeLayout.setVerticalGroup(
            ThongBaoNeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ThongBaoNeLayout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(ThongBaoNeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFileName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addComponent(btnSave)
                .addContainerGap(61, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 102));
        jLabel1.setText("Thống Kê");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        jLabel2.setText("Thống kê theo quý");

        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã hoá đơn", "Mã  khách hàng ", "Mã sản phẩm", "Tên sản Phẩm ", "Số lượng ", "Giá ", "Loại "
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHoaDon.setRowHeight(25);
        jScrollPane1.setViewportView(tblHoaDon);
        if (tblHoaDon.getColumnModel().getColumnCount() > 0) {
            tblHoaDon.getColumnModel().getColumn(0).setHeaderValue("Mã Hóa Đơn");
            tblHoaDon.getColumnModel().getColumn(1).setResizable(false);
            tblHoaDon.getColumnModel().getColumn(2).setHeaderValue("Mã sản phẩm");
            tblHoaDon.getColumnModel().getColumn(3).setHeaderValue("Tên sản Phẩm ");
            tblHoaDon.getColumnModel().getColumn(4).setResizable(false);
        }

        btnQuy1.setText("Quý 1");
        btnQuy1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuy1ActionPerformed(evt);
            }
        });

        btnTatCa.setText("Tất cả");
        btnTatCa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTatCaActionPerformed(evt);
            }
        });

        btnQuy2.setText("Quý 2");
        btnQuy2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuy2ActionPerformed(evt);
            }
        });

        btnQuy3.setText("Quý 3");
        btnQuy3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuy3ActionPerformed(evt);
            }
        });

        btnQuy4.setText("Quý 4");
        btnQuy4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuy4ActionPerformed(evt);
            }
        });

        btnExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/excel.png"))); // NOI18N
        btnExcel.setText("ExportExcel");
        btnExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 636, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnTatCa)
                        .addGap(18, 18, 18)
                        .addComponent(btnQuy1)
                        .addGap(18, 18, 18)
                        .addComponent(btnQuy2)
                        .addGap(18, 18, 18)
                        .addComponent(btnQuy3)
                        .addGap(18, 18, 18)
                        .addComponent(btnQuy4)
                        .addGap(39, 39, 39)
                        .addComponent(btnExcel)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTatCa)
                    .addComponent(btnQuy1)
                    .addComponent(btnQuy2)
                    .addComponent(btnQuy3)
                    .addComponent(btnQuy4)
                    .addComponent(btnExcel))
                .addGap(14, 14, 14))
        );

        tabs.addTab("Hóa Đơn ", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(tabs)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabs))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnQuy1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuy1ActionPerformed
        this.Quy1();
    }//GEN-LAST:event_btnQuy1ActionPerformed

    private void btnTatCaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTatCaActionPerformed
        this.fillToTable();
    }//GEN-LAST:event_btnTatCaActionPerformed

    private void btnQuy2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuy2ActionPerformed
        this.Quy2();
    }//GEN-LAST:event_btnQuy2ActionPerformed

    private void btnQuy3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuy3ActionPerformed
        this.Quy3();
    }//GEN-LAST:event_btnQuy3ActionPerformed

    private void btnQuy4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuy4ActionPerformed
        this.Quy4();
    }//GEN-LAST:event_btnQuy4ActionPerformed

    private void btnExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcelActionPerformed
//        ExportExcelThongKe();
        ThongBaoNe.setVisible(true);
    }//GEN-LAST:event_btnExcelActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        ExportExcelThongKe();
    }//GEN-LAST:event_btnSaveActionPerformed

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
            java.util.logging.Logger.getLogger(ThongKe.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ThongKe.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ThongKe.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ThongKe.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ThongKe().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog ThongBaoNe;
    private javax.swing.JButton btnExcel;
    private javax.swing.JButton btnQuy1;
    private javax.swing.JButton btnQuy2;
    private javax.swing.JButton btnQuy3;
    private javax.swing.JButton btnQuy4;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnTatCa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTextField txtFileName;
    // End of variables declaration//GEN-END:variables

}
