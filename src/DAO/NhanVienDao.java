
package DAO;

import entity.ChangePassword;
import entity.JDBCHelper;
import entity.JDBCHelper;
import entity.NhanVien;
import entity.NhanVien;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 *
 * @author Admin
 */
public class NhanVienDao {

	public List<NhanVien> selectAll() {
		String sql = "SELECT * FROM NhanVien";
		return this.selectBySql(sql);
	}

	@Test
	public NhanVien findById(String username) {
		String sql = "SELECT * FROM NhanVien WHERE Username=?";
		List<NhanVien> list = this.selectBySql(sql, username);
		return list.size() > 0 ? list.get(0) : null;
	}

	protected List<NhanVien> selectBySql(String sql, Object... args) {
		List<NhanVien> list = new ArrayList<>();
		try {
			ResultSet rs = null;
			try {
				rs = JDBCHelper.executeQuery(sql, args);
				while (rs.next()) {
					NhanVien entity = new NhanVien();
					entity.setTenNV(rs.getString("Username"));
					entity.setTenNV(rs.getString("TenNV"));
					entity.setMatKhau(rs.getString("MatKhau"));
					entity.setVaiTro(rs.getBoolean("VaiTro"));
					entity.setMaNV(rs.getString("MaNV"));
					list.add(entity);
				}
			} finally {
				rs.getStatement().getConnection().close();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		return list;
	}

	public void update1(ChangePassword model) {
		String sql = "UPDATE nhanvien SET Matkhau=? WHERE Manv=?";
		JDBCHelper.executeUpdate(sql, model.getPassword(), model.getUsername());

	}

	public void delete(String MaNV) {
		String sql = "DELETE FROM nhanvien WHERE Manv=?";
		JDBCHelper.executeUpdate(sql, MaNV);
	}
}
