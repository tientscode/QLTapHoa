/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.util.Date;

/**
 *
 * @author Admin
 */
public class HoaDon {
    String MaHD;
    String MaKH;
    int MaSP;
    String TenSP;
    String Loai;
    float TotalPrice;
    Date NgayLap;
    int SoLuong;

    public HoaDon() {
    }

    public HoaDon(String MaHD, String MaKH, int MaSP, String TenSP, String Loai, float TotalPrice, Date NgayLap, int SoLuong) {
        this.MaHD = MaHD;
        this.MaKH = MaKH;
        this.MaSP = MaSP;
        this.TenSP = TenSP;
        this.Loai = Loai;
        this.TotalPrice = TotalPrice;
        this.NgayLap = NgayLap;
        this.SoLuong = SoLuong;
    }

    public String getMaHD() {
        return MaHD;
    }

    public void setMaHD(String MaHD) {
        this.MaHD = MaHD;
    }

    public String getMaKH() {
        return MaKH;
    }

    public void setMaKH(String MaKH) {
        this.MaKH = MaKH;
    }

    public int getMaSP() {
        return MaSP;
    }

    public void setMaSP(int MaSP) {
        this.MaSP = MaSP;
    }

    public String getTenSP() {
        return TenSP;
    }

    public void setTenSP(String TenSP) {
        this.TenSP = TenSP;
    }

    public String getLoai() {
        return Loai;
    }

    public void setLoai(String Loai) {
        this.Loai = Loai;
    }
    @Override
    public String toString(){
        return Loai;
    }
    public float getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(float TotalPrice) {
        this.TotalPrice = TotalPrice;
    }

    public Date getNgayLap() {
        return NgayLap;
    }

    public void setNgayLap(Date NgayLap) {
        this.NgayLap = NgayLap;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int SoLuong) {
        this.SoLuong = SoLuong;
    }

    
}
