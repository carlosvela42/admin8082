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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vnpt.ssdc.dto.Packages;
import com.vnpt.ssdc.dto.Product;
import com.vnpt.ssdc.repository.PackageRepository;

@Service
@Transactional
public class PackagesService {
	
	@Autowired
	private JdbcTemplate JdbcTemplate;

	@Autowired
	private PackageRepository repo;
	
	public List<Packages> listAll() {
		List<Packages> map = new ArrayList<Packages>();
		String sql = "select * from PACKAGES";
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = JdbcTemplate.getDataSource().getConnection();
			pstm = con.prepareStatement(sql);
			rs = pstm.executeQuery();
			while (rs.next()) {
				Packages packages = new Packages();
				packages.setName(rs.getString("NAME"));
				packages.setPrice(rs.getString("PRICE"));
				packages.setId(rs.getLong("ID"));
				packages.setCategory(rs.getString("CATEGORY"));
				packages.setTime(rs.getString("TIME"));
				packages.setCode(rs.getString("CODE"));
				packages.setTemplate(rs.getString("TEMPLATE"));
				packages.setType(rs.getString("TYPE"));
				packages.setSubject(rs.getString("SUBJECT"));
				map.add(packages);
			}
		} catch (Exception e) {

		} finally {
			closeResource(con, pstm, rs);
		}
		return map;
	}
	
	public void update(Packages packages) {
		String sql = "update PACKAGES set NAME = ?, PRICE = ?, CATEGORY = ?, TIME = ?, CODE = ?, TEMPLATE = ?, TYPE = ?, SUBJECT = ? WHERE ID = ?";
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			con = JdbcTemplate.getDataSource().getConnection();
			pstm = con.prepareStatement(sql);
			pstm.setString(1, packages.getName());
			pstm.setString(2, packages.getPrice());
			pstm.setString(3, packages.getCategory());
			pstm.setString(4, packages.getTime());
			pstm.setString(5, packages.getCode());
			pstm.setString(6, packages.getTemplate());
			pstm.setString(7, packages.getType());
			pstm.setString(8, packages.getSubject());
			pstm.setLong(9, packages.getId());
			int updateCount = pstm.executeUpdate();	
			System.out.print(updateCount);
		} catch (Exception e) {
			System.out.print(e);
		} finally {
			closeResource(con, pstm, rs);
		}
	}
	
	public void save(Packages packages) {
		String sql = "INSERT INTO PACKAGES (NAME, PRICE, CATEGORY, TIME, CODE, TEMPLATE, TYPE, SUBJECT) SELECT ?, ?, ?, ?, ?, ?, ?, ? FROM dual";
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			con = JdbcTemplate.getDataSource().getConnection();
			pstm = con.prepareStatement(sql);
			pstm.setString(1, packages.getName());
			pstm.setString(2, packages.getPrice());
			pstm.setString(3, packages.getCategory());
			pstm.setString(4, packages.getTime());
			pstm.setString(5, packages.getCode());
			pstm.setString(6, packages.getTemplate());
			pstm.setString(7, packages.getType());
			pstm.setString(8, packages.getSubject());
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
	
	public Packages get(long id) {
		return repo.findOne(id);
	}
	
	public void delete(long id) {
		repo.delete(id);
	}
}
