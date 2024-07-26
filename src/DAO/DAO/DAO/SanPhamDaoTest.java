package DAO.DAO.DAO;

import static org.junit.Assert.assertFalse;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import DAO.SanPhamDao;
import entity.SanPham;

public class SanPhamDaoTest {
	private SanPhamDao sanPhamDao;

	@BeforeMethod
	public void setUp() {
		sanPhamDao = new SanPhamDao();
	}
//
//    @AfterMethod
//    public void tearDown() {
//        // Xóa dữ liệu thử nghiệm sau mỗi phương thức kiểm tra nếu cần thiết
//    }

	@Test
	public void testInsert() {
		SanPham sanPham = new SanPham();
		sanPham.setMaSP(110);
		sanPham.setTenSP("Test Product");
		sanPham.setGia(100.0f);
//        sanPham.setGia((float) 100); // Ép kiểu từ int sang float
		sanPham.setSoLuong(10);
		sanPham.setMoTa("Test Description");
		sanPham.setHinhAnh("test.jpg");

		sanPhamDao.insert(sanPham);

		SanPham insertedSanPham = sanPhamDao.selectById(1);
		assertNotNull(insertedSanPham);
		assertEquals(insertedSanPham.getTenSP(), "Test Product");
	}

	@Test(dependsOnMethods = "testInsert")
	public void testUpdate() {
		SanPham sanPham = sanPhamDao.selectById(110);
		assertNotNull(sanPham);
		sanPham.setTenSP("be xuan mai lon ton");
		sanPhamDao.update(sanPham);

		SanPham updatedSanPham = sanPhamDao.selectById(110);
		assertNotNull(updatedSanPham);
		assertEquals(updatedSanPham.getTenSP(), "be xuan mai lon ton");
	}

	@Test(dependsOnMethods = "testUpdate")
	public void testDelete() {
		sanPhamDao.delete(110);

		SanPham deletedSanPham = sanPhamDao.selectById(110);
		assertNull(deletedSanPham);
	}

//	@Test
//	public void testSelectAll() {
//		assertTrue(!sanPhamDao.selectAll().isEmpty());
//
//		// Kiểm tra rằng cơ sở dữ liệu trống
//		// (Đảm bảo rằng kết quả của phương thức selectAll() là một danh sách trống)
//		sanPhamDao.deleteAll(); // Xóa toàn bộ dữ liệu trong bảng sản phẩm
//		assertTrue(sanPhamDao.selectAll().isEmpty());
//	}

	@Test
	public void testSelectById() {
		// Thêm một số sản phẩm vào cơ sở dữ liệu để kiểm tra phương thức selectById()
		// ... (Thêm các sản phẩm vào cơ sở dữ liệu)

		SanPham selectedSanPham = sanPhamDao.selectById(109);
		assertNotNull(selectedSanPham);
	}
}
