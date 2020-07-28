package com.fnt.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.fnt.model.biz.AdminPageBiz;
import com.fnt.model.biz.ReportPageBiz;
import com.fnt.model.biz.impl.AdminPageBizImpl;
import com.fnt.model.biz.impl.ReportPageBizImpl;
import com.fnt.model.dto.MemberDto;
import com.fnt.model.dto.ReportDto;
import com.google.gson.Gson;

import net.sf.json.JSONException;

/**
 * Servlet implementation class AdminController
 */
@WebServlet("/admin.do")
public class AdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AdminController() {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		AdminPageBiz adminpagebiz = new AdminPageBizImpl();
		ReportPageBiz reportpagebiz = new ReportPageBizImpl();

		String command = request.getParameter("command");
		System.out.println("<" + command + ">");

		// header에 role를 클릭하면 adminpage로 이동한다.
		if (command.equals("adminpage")) {
			response.sendRedirect("fntadminpage.jsp");
		}
		// adminpage에서 "전체회원조회"를 클릭하면

		// enabled가 report이면 reportdto로 전체출력해주고 뿌려줘야한다.
		if (command.equals("select")) {
			String enabled = request.getParameter("enabled");
				List<MemberDto> list = new ArrayList<>();
		        list = adminpagebiz.selectAll(enabled);
		        List<ReportDto> list1 = new ArrayList<>();
		        list1 = reportpagebiz.selectList();
		        
		        
		        JSONObject obj = new JSONObject();
		        JSONObject obj1 = new JSONObject();
		        try {
		        JSONArray jArray = new JSONArray();//배열이 필요할때
		        JSONArray jArray1 = new JSONArray();
		        //배열
		        for (int i = 0; i < list.size(); i++){
		           JSONObject sObject = new JSONObject();//배열 내에 들어갈 json
		           sObject.put("memberid", list.get(i).getMemberid());
		           sObject.put("membernickname", list.get(i).getMembernickname());
		           sObject.put("membername", list.get(i).getMembername());
		           sObject.put("memberbirth", list.get(i).getMemberbirth());
		           sObject.put("memberphone", list.get(i).getMemberphone());
		           sObject.put("memberaddr", list.get(i).getMemberaddr());
		           sObject.put("memberemail", list.get(i).getMemberemail());
		           sObject.put("memberregdate", list.get(i).getMemberregdate());
		           jArray.add(sObject);
		        }
		        for(int i = 0; i < list1.size(); i++) {
		        	JSONObject sObject1 = new JSONObject();
		        	sObject1.put("reportno", list1.get(i).getReportno());
		        	sObject1.put("reporttitle", list1.get(i).getReporttitle());
		        	sObject1.put("sendid", list1.get(i).getSendid());
		        	sObject1.put("sendnickname", list1.get(i).getSendnickname());
		        	sObject1.put("receiveid", list1.get(i).getReceiveid());
		        	sObject1.put("receivenickname", list1.get(i).getReceivenickname());
		        	sObject1.put("reportregdate", list1.get(i).getReportregdate());
		        	jArray1.add(sObject1);
		        }
		        // obj.put("DESCRIPTION","{\"memberid\":\"아이디\",\"membernickname\":\"닉네임\",\"membername\":\"이름\",\"memberbirth\":\"생일\",\"memberphone\":\"전화번호\",\"memberaddr\":\"주소\",\"memberemail\":\"이메일\",\"memberregdate\":\"가입날짜\"}");
		        obj.put("LIST", jArray);//배열을 넣음
		        obj1.put("REPORT", jArray1);
		        
		        
	
		        } catch (JSONException e) {
		        e.printStackTrace();
		        }
		         
		        Gson gson = new Gson();
		        String jsonPlace = gson.toJson(obj);
		        System.out.println(jsonPlace);
		        
		        Gson gson1 = new Gson();
		        String jsonPlace1 = gson1.toJson(obj1);
		        System.out.println(jsonPlace1);
		        
		         
		        PrintWriter out = response.getWriter(); 
		        if(enabled.equals("Y") || enabled.equals("N") || enabled.equals("R")) {
		        	out.print(jsonPlace);
		        } else if(enabled.equals("report")) {
		        	out.print(jsonPlace1);
		        }
	        
	         
			// 신고내용 자세히 보기
		} else if (command.equals("reportdetail")) {
			int reportno = Integer.parseInt(request.getParameter("reportno"));
			ReportDto dto = new ReportDto();
			dto = reportpagebiz.selectOne(reportno);
			request.setAttribute("reportdto", dto);
			dispatch("fntadminpagereportdetail.jsp", request, response);

			// 처리버튼 누르면 enabled를 r로 바꿔주기.
		} else if (command.equals("change")) {
			String receiveid = request.getParameter("receiveid");
			int res = adminpagebiz.updateRole(receiveid);
			if (res > 0) {
				jsResponse("신고처리가 완료되었습니다.", "admin.do?command=adminpage", response);
			}
		}
		// 회원상태조회에서 복귀버튼 누르면 enabled를 y로 변경
		else if (command.equals("reset")) {
			String id = request.getParameter("id");
			System.out.println(id + "컨트롤러에서 찍힌 id");
			int res = adminpagebiz.restEnabled(id);
			if (res > 0) {
				jsResponse("복귀처리가 완료되었습니다.", "admin.do?command=adminpage", response);
			}
		}
	}

	public void dispatch(String url, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher dispatch = request.getRequestDispatcher(url);

		dispatch.forward(request, response);
	}

	public void jsResponse(String msg, String url, HttpServletResponse response) throws IOException {

		String s = "<script type='text/javascript'>" + "alert('" + msg + "');" + "location.href = '" + url + "';"
				+ "</script>";

		PrintWriter out = response.getWriter();
		out.append(s);
	}

}
