package com.vnpt.ssdc.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
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
import com.vnpt.ssdc.dto.Code;
import com.vnpt.ssdc.dto.Code;
import com.vnpt.ssdc.dto.Product;
import com.vnpt.ssdc.repository.CodeRepository;
import com.vnpt.ssdc.repository.PackageRepository;

@Service
@Transactional
public class CodeService {
	
	@Autowired
	private JdbcTemplate JdbcTemplate;

	@Autowired
	private CodeRepository repo;
	
	public List<Code> listAll() {
		List<Code> map = new ArrayList<Code>();
		String sql = "select * from CODE";
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String currentPrincipalName = authentication.getName();
		if(currentPrincipalName != null && !constant.ADMIN_EMAIL.equals(currentPrincipalName)) {
			sql += " WHERE EMAIL = '" + currentPrincipalName + "'";
		}
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = JdbcTemplate.getDataSource().getConnection();
			pstm = con.prepareStatement(sql);
			rs = pstm.executeQuery();
			while (rs.next()) {
				Code code = new Code();
				code.setName(rs.getString("NAME"));
				code.setIsactive(rs.getString("ISACTIVE"));
				code.setPrice(rs.getString("PRICE"));
				code.setId(rs.getLong("ID"));
				code.setEmail(rs.getString("EMAIL"));
				map.add(code);
			}
		} catch (Exception e) {

		} finally {
			closeResource(con, pstm, rs);
		}
		return map;
	}
	
	public void update(Code code) {
		String sql = "update CODE set NAME = ?, PRICE = ?, ISACTIVE = ?, EMAIL = ? WHERE ID = ?";
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			con = JdbcTemplate.getDataSource().getConnection();
			pstm = con.prepareStatement(sql);
			pstm.setString(1, code.getName());
			pstm.setString(2, code.getPrice());
			pstm.setString(3, code.getIsactive());
			pstm.setString(4, code.getEmail());
			pstm.setLong(5, code.getId());
			int updateCount = pstm.executeUpdate();	
			System.out.print(updateCount);
		} catch (Exception e) {
			System.out.print(e);
		} finally {
			closeResource(con, pstm, rs);
		}
	}
	
	public void save(Code code) {
		String sql = "INSERT INTO CODE (NAME, PRICE, ISACTIVE, EMAIL) SELECT ?, ?, ?, ? FROM dual";
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			con = JdbcTemplate.getDataSource().getConnection();
			pstm = con.prepareStatement(sql);
			pstm.setString(1, code.getName());
			pstm.setString(2, code.getPrice());
			pstm.setString(3, code.getIsactive());
			pstm.setString(4, code.getEmail());
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
	
	public Code get(long id) {
		return repo.findOne(id);
	}
	
	public void delete(long id) {
		repo.delete(id);
	}
}
