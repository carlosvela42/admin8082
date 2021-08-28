/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vnpt.ssdc.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.vnpt.ssdc.SendEmailThread;
import com.vnpt.ssdc.dto.Packages;
import com.vnpt.ssdc.dto.Product;
import com.vnpt.ssdc.dto.Users;
import com.vnpt.ssdc.repository.PackageRepository;
import com.vnpt.ssdc.repository.UserRepository;

/**
 *
 * @author kiendt
 */
@Service("userDetailsService")
public class UserService implements UserDetailsService {
	@Autowired
	private JdbcTemplate JdbcTemplate;
	
	@Autowired
	private UserRepository repo;
	
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
    	String sql = "select * from USERS WHERE EMAIL = ? LIMIT 1";
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Users users = new Users();
		try {
			con = JdbcTemplate.getDataSource().getConnection();
			pstm = con.prepareStatement(sql);
			pstm.setString(1, userName);
			rs = pstm.executeQuery();
			while (rs.next()) {				
				users.setPassword(rs.getString("PASSWORD"));
				users.setEmail(rs.getString("EMAIL"));			
			}
		} catch (Exception e) {

		} finally {
			closeResource(con, pstm, rs);
		}
    	if (users.getEmail().equals(userName)) {
            return User.withUsername(userName).roles("USER").password(users.getPassword()).build();
        } else {
            throw new UsernameNotFoundException(userName);
        }
    }
    
    public Users selectByEmail(String email) {
		String sql = "select * from USERS WHERE EMAIL = ? LIMIT 1";
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Users user = new Users();
		try {
			con = JdbcTemplate.getDataSource().getConnection();
			pstm = con.prepareStatement(sql);
			pstm.setString(1, email);
			rs = pstm.executeQuery();
			while (rs.next()) {				
				user.setPassword(rs.getString("PASSWORD"));
				user.setEmail(rs.getString("EMAIL"));
				user.setId(rs.getLong("ID"));
				user.setMachineId(rs.getString("MACHINE_ID"));
				user.setPhone(rs.getString("PHONE"));				
			}
		} catch (Exception e) {

		} finally {
			closeResource(con, pstm, rs);
		}
		return user;
	}
    
    public void sendEmail(String content, String subject,String listRec) {
		Properties commonConfig = new Properties();
		commonConfig.setProperty("email.host", "smtp.gmail.com");		
		commonConfig.setProperty("email.port", "465");
		commonConfig.setProperty("email.auth", "true");
		commonConfig.setProperty("email.socketFactory.port", "465");
		commonConfig.setProperty("email.timeout", "120000");
		commonConfig.setProperty("email.ssl.enable", "true");
		commonConfig.setProperty("email.starttls.enable", "true");
		commonConfig.setProperty("email.from", "alert@megapay.vn");
		commonConfig.setProperty("email.recipient", "alert-megapay@vnptepay.com.vn");
		commonConfig.setProperty("email.username", "support@orderflow.vn");
		commonConfig.setProperty("email.password", "qajpmsmnvgxflacz");
		commonConfig.setProperty("email.subject", "[core - local] Megapay alert");
    	Thread sendMailThread = new Thread(new SendEmailThread(content,subject,listRec, commonConfig));
		sendMailThread.start();
	}
    
    public Users get(long id) {
		return repo.findOne(id);
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
