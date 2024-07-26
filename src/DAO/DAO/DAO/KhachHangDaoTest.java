package DAO.DAO.DAO;

import DAO.KhachHangDao;
import entity.KhachHang;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

public class KhachHangDaoTest {
	private KhachHangDao khachHangDao;

	@BeforeMethod
	public void setUp() {
		khachHangDao = new KhachHangDao();
		// Thêm dữ liệu thử nghiệm vào cơ sở dữ liệu nếu cần
	}

	@Test
	public void testInsert() {
		KhachHang khachHang = new KhachHang();
		khachHang.setMaKH("KH001");
		khachHang.setHoTen("John Doe");
		khachHang.setEmail("john.doe@example.com");
		khachHang.setSDT("123456789");
		khachHang.setDiaChi("123 Main Street");
		khachHangDao.insert(khachHang);
		KhachHang insertedKhachHang = khachHangDao.selectById("KH001");
		assertNotNull(insertedKhachHang);
		assertEquals(insertedKhachHang.getHoTen(), "John Doe");
		assertEquals(insertedKhachHang.getEmail(), "john.doe@example.com");
	}

	@Test
	public void testUpdate() {
		KhachHang khachHang = khachHangDao.selectById("KH02");
//        khachHang.setMaKH("KH02");
		khachHang.setHoTen("Jane Smith");
		khachHang.setEmail("jane.smith@example.com");
		khachHang.setSDT("987654321");
		khachHang.setDiaChi("456 Oak Street");
		khachHang.setEmail("jane.doe@example.com");
		khachHangDao.update(khachHang);

		KhachHang updatedKhachHang = khachHangDao.selectById("KH02");
		assertNotNull(updatedKhachHang);
		assertEquals(updatedKhachHang.getEmail(), "jane.doe@example.com");
	}

	@Test
	public void testDelete() {
		// Thêm một khách hàng mới vào cơ sở dữ liệu để xóa
		KhachHang khachHang = new KhachHang();
		khachHang.setMaKH("KH001");
		khachHang.setHoTen("Michael Johnson");
		khachHang.setEmail("michael.johnson@example.com");
		khachHang.setSDT("555555555");
		khachHang.setDiaChi("789 Elm Street");

		khachHangDao.insert(khachHang);

		// Xóa khách hàng vừa thêm vào
		khachHangDao.delete("KH001");

		KhachHang deletedKhachHang = khachHangDao.selectById("KH003");
		assertNull(deletedKhachHang);
	}

	@Test
	public void testSelectByKeyword() {

		List<KhachHang> khachHangList = khachHangDao.selectByKeyword("Phan Phan Ha Vy");
		assertNotNull(khachHangList);
		assertEquals(khachHangList.size(), 1);
	}

	@Test
	public void testSelectBySDT() {

		List<KhachHang> khachHangList = khachHangDao.selectBySDT("123456789");
		assertNotNull(khachHangList);
		assertEquals(khachHangList.size(), 1);
	}
}
