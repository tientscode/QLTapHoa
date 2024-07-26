package DAO.DAO.DAO;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

import java.util.List;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import DAO.NhanVienDao;
import entity.ChangePassword;
import entity.NhanVien;

public class NhanVienDaoTest {
    
    private NhanVienDao nhanVienDao;
    
    @BeforeMethod
    public void setUp() {
        nhanVienDao = new NhanVienDao();
    }
    
    @AfterMethod
    public void tearDown() {
        // Thực hiện dọn dẹp sau mỗi phương thức kiểm thử nếu cần
        nhanVienDao = null;
    }
    
    @Test
    public void testSelectAll() {
        List<NhanVien> nhanViens = nhanVienDao.selectAll();
        assertNotNull(nhanViens);
        // Kiểm tra số lượng nhân viên trả về
        assertEquals(nhanViens.size(), 2); // Giả sử có 3 nhân viên trong cơ sở dữ liệu
    }
    
    @Test
    public void testFindById() {

        NhanVien nhanVien = nhanVienDao.findById("taipa");
        assertNotNull(nhanVien);
        assertEquals(nhanVien.getTenNV(), "Phan Anh Tài");
        assertEquals(nhanVien.getMatKhau(), "123456");
        assertEquals(nhanVien.isVaiTro(), false);
        assertEquals(nhanVien.getMaNV(), "2");
        
//        NhanVien nonExistentNhanVien = nhanVienDao.findById("nouser");
//        assertNull(nonExistentNhanVien);
    }
 

    
    @Test
    public void testDelete() {
        nhanVienDao.delete("3");
        NhanVien deletedNhanVien = nhanVienDao.findById("3");
        assertNull(deletedNhanVien);
    }
}
