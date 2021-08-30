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
		String sql = "select a.price, a.epay_date, c.name,d.email from payment a left join map b on b.id = a.map_id left join packages c on c.id = b.package_id left join users d on d.id = b.user_id where 1 = 1";
		if(payment.getEpayDateStart() != null) {
			sql += " and a.epay_date >= '" + payment.getEpayDateStart() + "'";
		}
		if(payment.getEpayDateEnd() != null) {
			sql += " and a.epay_date <= '" + payment.getEpayDateEnd() + "'";
		}
		if(payment.getPackageId() != null) {
			sql += " and c.id = " + payment.getPackageId() ;
		}
		if(payment.getEmail() != null) {
			sql += " and d.email = '" + payment.getEmail() + "'";
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
				totalAmount += Long.parseLong(rs.getString("PRICE"));
				map.add(pm);
			}
			Payment pm = new Payment();
			pm.setPrice(totalAmount + "");
			map.add(pm);
		} catch (Exception e) {

		} finally {
			closeResource(con, pstm, rs);
		}
		return map;
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
