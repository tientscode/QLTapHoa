package DAO.DAO.DAO;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import DAO.ThongKeDao;
import entity.HoaDon;

public class ThongKeDaoTest {

	private ThongKeDao thongKeDao;

	@BeforeClass
	public void setUp() {
		thongKeDao = new ThongKeDao();
	}

	@Test
	public void testSelectHoaDon() {
		List<HoaDon> hoaDons = thongKeDao.selectHoaDon();
		assertNotNull(hoaDons);

	}

	@Test
	public void testSelectQuy1() {
		List<HoaDon> hoaDons = thongKeDao.selectQuy1();
		assertNotNull(hoaDons);

	}

	@Test
	public void testSelectQuy2() {
		List<HoaDon> hoaDons = thongKeDao.selectQuy2();
		assertNotNull(hoaDons);
		// Kiểm tra nếu danh sách không null
	}

	@Test
	public void testSelectQuy3() {
		List<HoaDon> hoaDons = thongKeDao.selectQuy3();
		assertNotNull(hoaDons);
		// Kiểm tra nếu danh sách không null
	}

	@Test
	public void testSelectQuy4() {
		List<HoaDon> hoaDons = thongKeDao.selectQuy4();
		assertNotNull(hoaDons);
		// Kiểm tra nếu danh sách không null
	}
}
