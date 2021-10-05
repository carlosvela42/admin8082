/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vnpt.ssdc.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.vnpt.ssdc.SendEmailThread;
import com.vnpt.ssdc.constant;
import com.vnpt.ssdc.dto.Packages;
import com.vnpt.ssdc.dto.Payment;
import com.vnpt.ssdc.dto.Product;
import com.vnpt.ssdc.dto.Users;
import com.vnpt.ssdc.repository.PackageRepository;
import com.vnpt.ssdc.repository.UserRepository;

/**
 *
 * @author kiendt
 */
@Service("paymentService")
public class PaymentService {
	@Autowired
	private JdbcTemplate JdbcTemplate;
	
	public List<Payment> listAll(Payment payment) {
		List<Payment> map = new ArrayList<Payment>();
		String sql = "select a.id,a.price, a.epay_date, c.name,d.email,d.phone,b.machine_id,a.status status, b.pay_date, b.next_pay_date, b.code, b.id MAP_ID from payment a left join map b on b.id = a.map_id left join packages c on c.id = b.package_id left join users d on d.id = b.user_id where 1 = 1 and (status != '3' || status is null) AND c.id not in (SELECT ID FROM PACKAGES WHERE PRICE = 0 AND TIME = 14 AND TYPE = 1) ";
		if(payment.getEpayDateStart() != null && !"".equals(payment.getEpayDateStart())) {
			sql += " and a.epay_date >= '" + payment.getEpayDateStart() + "'";
		}
		if(payment.getEpayDateEnd() != null && !"".equals(payment.getEpayDateEnd())) {
			sql += " and a.epay_date <= '" + payment.getEpayDateEnd() + "'";
		}
		if(payment.getPackageId() != null && !"".equals(payment.getPackageId())) {
			sql += " and c.id = " + payment.getPackageId() ;
		}
		if(payment.getEmail() != null && !"".equals(payment.getEmail())) {
			sql += " and d.email = '" + payment.getEmail() + "'";
		}
		if(payment.getId() != null && !"".equals(payment.getId())) {
			sql += " and a.id = " + payment.getId();
		}
		if(payment.getPhone() != null && !"".equals(payment.getPhone())) {
			sql += " and d.phone = '" + payment.getPhone() + "'";
		}
		if(payment.getMachineId() != null && !"".equals(payment.getMachineId())) {
			sql += " and b.machine_id = '" + payment.getMachineId() + "'";
		}
		if(payment.getStatus() != null && !"".equals(payment.getStatus())) {
			sql += " and a.status = '" + payment.getStatus() + "'";
		}
	
		if(payment.getNextPayDate() != null && !"".equals(payment.getNextPayDate())) {
			sql += " and b.next_pay_date = '" + payment.getNextPayDate() + "'";
		}
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = JdbcTemplate.getDataSource().getConnection();
			pstm = con.prepareStatement(sql);
			rs = pstm.executeQuery();
			Long totalAmount = 0l;
			while (rs.next()) {
				Payment pm = new Payment();
				pm.setPrice(rs.getString("PRICE"));
				pm.setEpayDate(rs.getString("EPAY_DATE"));
				pm.setName(rs.getString("NAME"));
				pm.setEmail(rs.getString("EMAIL"));
				pm.setPhone(rs.getString("PHONE"));
				pm.setMachineId(rs.getString("MACHINE_ID"));
				pm.setStatus(rs.getString("STATUS"));
				switch(rs.getString("STATUS") + "") {
				  case "0":
					  pm.setStatusName("Chưa thanh toán");
				    break;
				  case "1":
					  pm.setStatusName("Chuyển khoản");
				    break;
				  case "2":
					  pm.setStatusName("Đã thanh toán qua VNPT Epay");
				    break;
				  case "3":
					  pm.setStatusName("Hủy");
				    break;
				  default:
					  pm.setStatusName("");
				}
				
				pm.setNextPayDate(rs.getString("NEXT_PAY_DATE"));
				pm.setCode(rs.getString("CODE"));
				pm.setId(rs.getLong("ID"));
				pm.setPayDate(rs.getString("PAY_DATE"));
				pm.setMapId(rs.getLong("MAP_ID"));
				totalAmount += Long.parseLong(rs.getString("PRICE"));
				map.add(pm);
			}
			Payment pm = new Payment();
			pm.setEmail("Tổng tiền");
			pm.setPrice(totalAmount + "");
			map.add(pm);
		} catch (Exception e) {

		} finally {
			closeResource(con, pstm, rs);
		}
		return map;
	}
	
	public void updatePayment(Payment payment) {
		String sql = "update PAYMENT set STATUS = ?, EPAY_DATE = ? WHERE ID = ?";
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			con = JdbcTemplate.getDataSource().getConnection();
			pstm = con.prepareStatement(sql);
			pstm.setString(1, payment.getStatus());
			pstm.setString(2, payment.getEpayDate());
			pstm.setLong(3, payment.getId());
			int updateCount = pstm.executeUpdate();	
			System.out.print(updateCount);
		} catch (Exception e) {
			System.out.print(e);
		} finally {
			closeResource(con, pstm, rs);
		}
	}
	
	public void updateIsCancel(Payment payment) {
		String sql = "update MAP set IS_CANCEL = 'Y', END_DATE = ? WHERE ID = ?";
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			con = JdbcTemplate.getDataSource().getConnection();
			pstm = con.prepareStatement(sql);
			
			pstm.setDate(1, new java.sql.Date(System.currentTimeMillis()));
			pstm.setLong(2, payment.getMapId());
			int updateCount = pstm.executeUpdate();	
			System.out.print(updateCount);
		} catch (Exception e) {
			System.out.print(e);
		} finally {
			closeResource(con, pstm, rs);
		}
	}
	
	public void updateMap(Payment payment) {
		String sql = "update MAP set MACHINE_ID = ?, CODE = ?, PAY_DATE = ?";
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			con = JdbcTemplate.getDataSource().getConnection();
			if(payment.getNextPayDate() == null || "".equals(payment.getNextPayDate())) {
				sql += " WHERE ID = ?";
			} else {
				sql += " , NEXT_PAY_DATE = ? WHERE ID = ? ";
			}
			pstm = con.prepareStatement(sql);
			pstm.setString(1, payment.getMachineId());
			pstm.setString(2, payment.getCode());
			pstm.setString(3, payment.getEpayDate());
			if(payment.getNextPayDate() == null || "".equals(payment.getNextPayDate())) {
				pstm.setLong(4, payment.getMapId());
			} else {
				pstm.setString(4, payment.getNextPayDate());
				pstm.setLong(5, payment.getMapId());
			}
			
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
