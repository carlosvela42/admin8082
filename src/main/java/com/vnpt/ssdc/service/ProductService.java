package com.vnpt.ssdc.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vnpt.ssdc.constant;
import com.vnpt.ssdc.dto.Product;
import com.vnpt.ssdc.dto.Users;

@Service
@Transactional
public class ProductService {
	
	@Autowired
	private JdbcTemplate JdbcTemplate;
	
	public List<Product> listAll() {
		List<Product> map = new ArrayList<Product>();
		String sql = "select USERS.EMAIL EMAIL, USERS.PHONE PHONE, MAP.MACHINE_ID MACHINE_ID, PACKAGES.ID PACKAGE_ID, PACKAGES.NAME PACKAGE_NAME, MAP.END_DATE END_DATE, MAP.ID ID, MAP.IS_CANCEL IS_CANCEL, MAP.TOTAL_AMOUNT TOTAL_AMOUNT, MAP.PAY_DATE PAY_DATE, MAP.CODE CODE from MAP left join USERS on USERS.ID = MAP.USER_ID left join PACKAGES on PACKAGES.ID = MAP.PACKAGE_ID ";
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		if(currentPrincipalName != null && !constant.ADMIN_EMAIL.equals(currentPrincipalName)) {
			sql += " WHERE USERS.EMAIL = '" + currentPrincipalName + "'";
		}
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = JdbcTemplate.getDataSource().getConnection();
			pstm = con.prepareStatement(sql);
			rs = pstm.executeQuery();
			while (rs.next()) {
				Product product = new Product();
				product.setEmail(rs.getString("EMAIL"));
				product.setPackageId(rs.getString("PACKAGE_ID"));
				product.setPackageName(rs.getString("PACKAGE_NAME"));
				product.setIsCancel(rs.getString("IS_CANCEL"));
				product.setTotalAmount(rs.getString("TOTAL_AMOUNT"));
				product.setPhone(rs.getString("PHONE"));
				product.setMachineId(rs.getString("MACHINE_ID"));
				product.setPaydate(rs.getString("PAY_DATE"));
				product.setEndDate(rs.getString("END_DATE"));
				product.setCode(rs.getString("CODE"));
				product.setId(rs.getLong("ID"));	
				map.add(product);
			}
		} catch (Exception e) {

		} finally {
			closeResource(con, pstm, rs);
		}
		return map;
	}
	
	public List<Product> listAll(Product productSearch) {
		List<Product> map = new ArrayList<Product>();
		String sql = "select USERS.EMAIL EMAIL,USERS.PASSWORD PASSWORD, USERS.PHONE PHONE, USERS.MACHINE_ID MACHINE_ID, PACKAGES.ID PACKAGE_ID, PACKAGES.NAME PACKAGE_NAME, MAP.ID ID, MAP.IS_CANCEL IS_CANCEL, MAP.TOTAL_AMOUNT TOTAL_AMOUNT,MAP.PAY_DATE PAY_DATE from MAP left join USERS on USERS.ID = MAP.USER_ID left join PACKAGES on PACKAGES.ID = MAP.PACKAGE_ID WHERE 1 = 1 ";
		if(productSearch.getEmail() != null && !"".equals(productSearch.getEmail())) {
			sql += " AND USERS.EMAIL = ?";
		}
		if(productSearch.getPackageId() != null && !"".equals(productSearch.getPackageId())) {
			sql += " AND PACKAGES.ID = ?";
		}
		if(productSearch.getId() != null) {
			sql += " AND MAP.ID = ?";
		}
		if(productSearch.getIsCancel() != null && !"".equals(productSearch.getIsCancel())) {
			sql += " AND MAP.IS_CANCEL = ?";
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		if(currentPrincipalName != null && !constant.ADMIN_EMAIL.equals(currentPrincipalName)) {
			sql += " AND USERS.EMAIL = '" + currentPrincipalName + "'";
		}
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = JdbcTemplate.getDataSource().getConnection();
			pstm = con.prepareStatement(sql);
			int i = 1;
			if(productSearch.getEmail() != null && !"".equals(productSearch.getEmail())) {
				pstm.setString(i, productSearch.getEmail());
				i++;
			}
			if(productSearch.getPackageId() != null && !"".equals(productSearch.getPackageId())) {
				pstm.setString(i, productSearch.getPackageId());
				i++;
			}
			if(productSearch.getId() != null) {
				pstm.setLong(i, productSearch.getId());
				i++;
			}
			if(productSearch.getIsCancel() != null) {
				pstm.setString(i, productSearch.getIsCancel());
				i++;
			}
			;
			rs = pstm.executeQuery();
			while (rs.next()) {
				Product product = new Product();
				product.setEmail(rs.getString("EMAIL"));
				product.setPackageId(rs.getString("PACKAGE_ID"));
				product.setPackageName(rs.getString("PACKAGE_NAME"));
				product.setIsCancel(rs.getString("IS_CANCEL"));
				product.setTotalAmount(rs.getString("TOTAL_AMOUNT"));
				product.setPhone(rs.getString("PHONE"));
				product.setMachineId(rs.getString("MACHINE_ID"));
				product.setId(rs.getLong("ID"));
				product.setPassword(rs.getString("PASSWORD"));
				product.setPaydate(rs.getString("PAY_DATE"));
				map.add(product);
			}
		} catch (Exception e) {

		} finally {
			closeResource(con, pstm, rs);
		}
		return map;
	}
	
	public Product selectByEmail(String email) {
		String sql = "select * from PRODUCT WHERE EMAIL = ? LIMIT 1";
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Product product = new Product();
		try {
			con = JdbcTemplate.getDataSource().getConnection();
			pstm = con.prepareStatement(sql);
			pstm.setString(1, email);
			rs = pstm.executeQuery();
			while (rs.next()) {				
				product.setCode(rs.getString("CODE"));
				product.setEmail(rs.getString("EMAIL"));
				product.setId(rs.getLong("ID"));
				product.setPackageId(rs.getString("PACKAGE_ID"));
				product.setPhone(rs.getString("PHONE"));				
			}
		} catch (Exception e) {

		} finally {
			closeResource(con, pstm, rs);
		}
		return product;
	}
	
	public void updatePaymentLink(String paymentLink, String vaNumber, String invoiceNo) {
		String sql = "update PRODUCT set PAYMENT_LINK = ?, VA_NUMBER = ? WHERE INVOICE_NO = ?";
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			con = JdbcTemplate.getDataSource().getConnection();
			pstm = con.prepareStatement(sql);
			pstm.setString(1, paymentLink);
			pstm.setString(2, vaNumber);
			pstm.setString(3, invoiceNo);
			int updateCount = pstm.executeUpdate();	
			System.out.print(updateCount);
		} catch (Exception e) {
			System.out.print(e);
		} finally {
			closeResource(con, pstm, rs);
		}
	}
	
	public void update(Users user) {
		String sql = "update USERS set PHONE = ?, EMAIL = ?, MACHINE_ID = ?, PASSWORD = ? WHERE ID = ?";
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			con = JdbcTemplate.getDataSource().getConnection();
			pstm = con.prepareStatement(sql);
			pstm.setString(1, user.getPhone());
			pstm.setString(2, user.getEmail());
			pstm.setString(3, user.getMachineId());
			if(user.getNewPassword() != null && !"".equals(user.getNewPassword())) {
				pstm.setString(4, user.getNewPassword());
			} else pstm.setString(4, user.getPassword());
			pstm.setLong(5, user.getId());
			int updateCount = pstm.executeUpdate();	
			System.out.print(updateCount);
		} catch (Exception e) {
			System.out.print(e);
		} finally {
			closeResource(con, pstm, rs);
		}
	}
	
	public void update(Product product) {
		String sql = "update USERS set PHONE = ?, PASSWORD = ? WHERE EMAIL = ?";
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			con = JdbcTemplate.getDataSource().getConnection();
			pstm = con.prepareStatement(sql);
			pstm.setString(1, product.getPhone());
			pstm.setString(2, product.getPassword());
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String currentPrincipalName = authentication.getName();
			
			pstm.setString(3, currentPrincipalName);
			
			int updateCount = pstm.executeUpdate();	
			System.out.print(updateCount);
		} catch (Exception e) {
			System.out.print(e);
		} finally {
			closeResource(con, pstm, rs);
		}
	}
	
	public void updateProduct(Product user) {
		String sql = "update MAP set PACKAGE_ID = ?, IS_CANCEL = ?, PAY_DATE = ?, END_DATE = ? WHERE ID = ?";
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			con = JdbcTemplate.getDataSource().getConnection();
			pstm = con.prepareStatement(sql);
			
			if("Y".equals(user.getIsCancel())) {
				pstm.setString(1, user.getPackageId());
				pstm.setString(2, user.getIsCancel());
				pstm.setString(3, null);
				pstm.setDate(4, new java.sql.Date(System.currentTimeMillis()));
			} else if(!user.getPackageIdNew().equals(user.getPackageId())){
				pstm.setString(1, user.getPackageIdNew());
				pstm.setString(2, user.getIsCancel());
				pstm.setDate(3, new java.sql.Date(System.currentTimeMillis()));
				pstm.setString(4, null);
				//doi goi cuoc neu gia han thi ko cap nhat, ngc lai cap nhat va goi sang epay
			} else {
				pstm.setString(1, user.getPackageId());
				pstm.setString(2, user.getIsCancel());
				pstm.setString(3, user.getPaydate());
				pstm.setString(4, null);
			}
			
			pstm.setLong(5, user.getId());
			int updateCount = pstm.executeUpdate();	
			System.out.print(updateCount);
		} catch (Exception e) {
			System.out.print(e);
		} finally {
			closeResource(con, pstm, rs);
		}
	}
	
	private void closeResource(Connection con, PreparedStatement pstm, ResultSet rs) {
		try {
			if (con != null) {
				con.close();
			}
			if (pstm != null) {
				pstm.close();
			}
			if (rs != null) {
				rs.close();
			}
		} catch (Exception e) {

		}
	}
	
}