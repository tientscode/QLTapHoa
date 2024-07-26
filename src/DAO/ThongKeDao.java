/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import entity.HoaDon;
import entity.JDBCHelper;
import entity.SanPham;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class ThongKeDao {

    public List<HoaDon> selectHoaDon() {
        String sql = "SELECT * FROM HoaDon";
        return selectBySql(sql);
    }

    public List<HoaDon> selectQuy1() {
        String sql = "SELECT \n"
                + "    MaHD, MaKH, MaSP, TenSP, SoLuong, TotalPrice, NgayLap, Loai\n"
                + "FROM \n"
                + "    HoaDon\n"
                + "WHERE \n"
                + "    DATEPART(QUARTER, CONVERT(DATE, NgayLap, 111)) = 1\n"
                + "    AND YEAR(CONVERT(DATE, NgayLap, 111)) = 2023;";

        List<HoaDon> list = selectBySql(sql);
        return list;
    }
    
    public List<HoaDon> selectQuy2() {
        String sql = "SELECT \n"
                + "    MaHD, MaKH, MaSP, TenSP, SoLuong, TotalPrice, NgayLap, Loai\n"
                + "FROM \n"
                + "    HoaDon\n"
                + "WHERE \n"
                + "    DATEPART(QUARTER, CONVERT(DATE, NgayLap, 111)) = 2\n"
                + "    AND YEAR(CONVERT(DATE, NgayLap, 111)) = 2023;";

        List<HoaDon> list = selectBySql(sql);
        return list;
    }
    
    public List<HoaDon> selectQuy3() {
        String sql = "SELECT \n"
                + "    MaHD, MaKH, MaSP, TenSP, SoLuong, TotalPrice, NgayLap, Loai\n"
                + "FROM \n"
                + "    HoaDon\n"
                + "WHERE \n"
                + "    DATEPART(QUARTER, CONVERT(DATE, NgayLap, 111)) = 3\n"
                + "    AND YEAR(CONVERT(DATE, NgayLap, 111)) = 2023;";

        List<HoaDon> list = selectBySql(sql);
        return list;
    }
    
    
    public List<HoaDon> selectQuy4() {
        String sql = "SELECT \n"
                + "    MaHD, MaKH, MaSP, TenSP, SoLuong, TotalPrice, NgayLap, Loai\n"
                + "FROM \n"
                + "    HoaDon\n"
                + "WHERE \n"
                + "    DATEPART(QUARTER, CONVERT(DATE, NgayLap, 111)) = 4\n"
                + "    AND YEAR(CONVERT(DATE, NgayLap, 111)) = 2023;";

        List<HoaDon> list = selectBySql(sql);
        return list;
    }
    

    protected List<HoaDon> selectBySql(String sql) {
        List<HoaDon> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JDBCHelper.executeQuery(sql);
                while (rs.next()) {
                    HoaDon entity = new HoaDon();
                    entity.setMaHD(rs.getString("MaHD"));
                    entity.setMaKH(rs.getString("MaKH"));
                    entity.setMaSP(rs.getInt("MaSP"));
                    entity.setTenSP(rs.getString("TenSP"));
                    entity.setNgayLap(rs.getDate("NgayLap"));
                    entity.setTotalPrice(rs.getFloat("TotalPrice"));
                    entity.setLoai(rs.getString("Loai"));
                    entity.setSoLuong(rs.getInt("SoLuong"));
                    list.add(entity);
                }
            } finally {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }
}
