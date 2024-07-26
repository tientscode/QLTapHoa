package DAO.DAO.DAO;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.sql.Date;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import DAO.HoaDonDao;
import entity.HoaDon;

public class HoaDonDaoTest {
	private HoaDonDao hoaDonDao;

	@BeforeClass
	public void setUp() {
		hoaDonDao = new HoaDonDao();
	}

	@Test
	public void testInsert() {
		// Tạo một đối tượng HoaDon mới
		HoaDon hoaDon = new HoaDon();
		hoaDon.setMaHD("HD13");
		hoaDon.setMaKH("KH08");
		hoaDon.setMaSP(104);
		hoaDon.setTenSP("Sua Milo");
		hoaDon.setNgayLap(new Date(System.currentTimeMillis()));
		hoaDon.setTotalPrice(200000);
		hoaDon.setLoai("Thung");
		hoaDon.setSoLuong(1);

		hoaDonDao.insert(hoaDon);

		HoaDon insertedHoaDon = hoaDonDao.selectById("HD13");
		assertNotNull(insertedHoaDon);
		assertEquals(insertedHoaDon.getMaHD(), "HD13");
	}

	@Test(dependsOnMethods = "testInsert")
	public void testUpdate() {
		// Lấy một hóa đơn đã được insert trước đó
		HoaDon hoaDon = hoaDonDao.selectById("HD13");
		assertNotNull(hoaDon);
		hoaDon.setMaKH("KH08");
		hoaDon.setTotalPrice(25000);

		// Thực hiện update
		hoaDonDao.update(hoaDon);

		// Kiểm tra xem update có thành công không
		HoaDon updatedHoaDon = hoaDonDao.selectById("HD13");
		assertNotNull(updatedHoaDon);
		assertEquals(updatedHoaDon.getMaKH(), "KH08");
	}

//
	@Test(dependsOnMethods = "testUpdate")
	public void testDelete() {
		hoaDonDao.delete("HD13");
		HoaDon deletedHoaDon = hoaDonDao.selectById("HD13");
		assertNull(deletedHoaDon);
	}

//	@Test
//	public void testSelectAll() {
//
//		List<HoaDon> hoaDonList = hoaDonDao.selectAll();
//
//		assertTrue(hoaDonList.isEmpty());
//	}
}
