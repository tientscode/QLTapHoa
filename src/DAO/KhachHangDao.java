/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import entity.JDBCHelper;
import entity.KhachHang;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Admin
 */
public class KhachHangDao extends KhachHang {
    public void insert(KhachHang model){
        String sql="INSERT INTO KhachHang(MaKH, HoTen, Email, SDT, DiaChi) VALUES(?, ?, ?, ?, ?)";
        JDBCHelper.executeUpdate(sql, 
                model.getMaKH(), 
                model.getHoTen(),
                model.getEmail(),
                model.getSDT(),
                model.getDiaChi());
    }
    
    public void update(KhachHang model){
        String sql="UPDATE KhachHang SET HoTen=?, Email=?,  SDT=?, DiaChi=? WHERE MaKH=?";
        JDBCHelper.executeUpdate(sql, 
                model.getHoTen(),
                model.getEmail(),
                model.getSDT(),
                model.getDiaChi(),
                model.getMaKH());
    }
    
    public void delete(String MaKH){
        String sql="DELETE FROM KhachHang WHERE MaKH=?";
        JDBCHelper.executeUpdate(sql, MaKH);
    }
    
    public List<KhachHang> selectAll(){
        String sql="SELECT * FROM KhachHang";
        return selectBySql(sql);
    }
    
    public KhachHang selectById(String makh){
        String sql="SELECT * FROM KhachHang WHERE MaKH=?";
        List<KhachHang> list = selectBySql(sql, makh);
        return list.size() > 0 ? list.get(0) : null;
    }
    public List<KhachHang> selectByKeyword(String keyword){
     String sql="SELECT * FROM KhachHang WHERE HoTen LIKE ?";
     return selectBySql(sql,"%"+keyword+"%");
}
    public List<KhachHang> selectBySDT(String sdt){
    String sql="Select * from KhachHang where SDT Like ?";
    return selectBySql(sql, "%"+sdt+"%");
    }
    
    protected List<KhachHang> selectBySql(String sql, Object...args){
        List<KhachHang> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JDBCHelper.executeQuery(sql, args);
                while(rs.next()){
                    KhachHang entity=new KhachHang();
                    entity.setMaKH(rs.getString("MaKH"));
                    entity.setHoTen(rs.getString("HoTen"));
                    entity.setEmail(rs.getString("Email"));
                    entity.setSDT(rs.getString("SDT"));
                    entity.setDiaChi(rs.getString("DiaChi"));
                   
                    list.add(entity);
                }
            } 
            finally{
                rs.getStatement().getConnection().close();
            }
        } 
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }
}
