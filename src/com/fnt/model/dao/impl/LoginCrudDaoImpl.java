package com.fnt.model.dao.impl;

import com.fnt.model.dao.LoginCrudDao;
import com.fnt.model.dto.MemberDto;

import static com.fnt.model.dao.SqlMapConfig.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.apache.ibatis.session.SqlSession;
public class LoginCrudDaoImpl implements LoginCrudDao {

	private String namespace = "logincrud.";
	
	public MemberDto login(String memberid, String memberpw) {
		SqlSession session = null;
		MemberDto dto = null;
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("memberid", memberid);
		map.put("memberpw", memberpw);
		try {
			session = getSqlSessionFactory().openSession(false);
			dto = session.selectOne(namespace+"selectmemberbyidandpw",map);
			if(dto != null) {
				session.commit();
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally {
			session.close();
		}
		
		
		return dto;
	}
	
	 
	//id 분실시 id찾기
	public MemberDto searchId(String membername, String memberemail, String memberphone) {
		SqlSession session = null;
		MemberDto dto = null;
		
		Map<String, String> map = new HashMap<String, String>();
   		map.put("membername", membername);
		map.put("memberemail", memberemail);
		map.put("memberphone", memberphone);
		
		try {
			session = getSqlSessionFactory().openSession(false);
			dto = session.selectOne(namespace+"searchid",map);
			
			if(dto != null) {
				session.commit();
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally {
			session.close();
		}
		
		return dto;
	}
	//pw 분실 시 pw 찾기
	public MemberDto searchPw(String memberid, String membername, String memberemail, String memberphone) {
		SqlSession session = null;
		MemberDto dto = null;
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("memberid", memberid);
		map.put("membername", membername);
		map.put("memberemail", memberemail);
		map.put("memberphone", memberphone);
		
		try {
			session = getSqlSessionFactory().openSession(false);
			dto = session.selectOne(namespace+"searchpw", map);
			
			if(dto != null) {
				session.commit();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			session.close();
		}
		
		
		return dto;
	}
	
	//마이페이지에서 내정보 보기 누를 때 내 정보 리스트 출력하기
	public MemberDto selectOne(String memberid){
		SqlSession session = null;
		MemberDto dto = null;
		
		try {
			session = getSqlSessionFactory().openSession(false);
			dto = session.selectOne(namespace+"mypagelist",memberid);
			System.out.println(dto.toString() + "으아아악");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			session.close();
		}
		
		
		return dto;
	}
	
	//마이페이지에서 개인정보 수정하기
	public int update(MemberDto dto) {
		SqlSession session = null;
		int res = 0;
		
		try {
			session = getSqlSessionFactory().openSession(false);
			System.out.println("DAO비밀번호 : "+dto.getMemberpw());
			res = session.update(namespace+"mypageupdate",dto);
			
			if(res > 0) {
				session.commit();
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			session.close();
		}
		
		return res;
		
	}
	
	//탈퇴하기를 눌렀을 때, enabled를 update
	public int updateoutmember(MemberDto dto) {
		SqlSession session = null;
		int res = 0;
		
		try {
			session = getSqlSessionFactory().openSession(false);
			res = session.update(namespace+"updateoutmember",dto);
			
			if(res > 0) {
				session.commit();
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally {
			session.close();
		}
		
		return res;
	}
	

}
