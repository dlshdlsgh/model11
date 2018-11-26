package com.model2.mvc.web.product;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

@RestController
@RequestMapping("/product/*")
public class ProductRestController {
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService proService;
	
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;

	public ProductRestController() {
		// TODO Auto-generated constructor stub
		System.out.println("ProductRestController..");
	}

	@RequestMapping(value="json/addProduct",method=RequestMethod.POST)
	public Product addProduct(@RequestBody Product product,@RequestParam("fileName") MultipartFile multiPartFile) throws Exception {
		System.out.println("받은 product : "+product);
		String temDir = "C:\\workspace\\08.Model2MVCShop(RestFul Server)\\WebContent\\images\\uploadFiles\\";
		
		product.setFileName(multiPartFile.getOriginalFilename());
		File file = new File(temDir, multiPartFile.getOriginalFilename());
		multiPartFile.transferTo(file);
		
		proService.addProduct(product);
		
//		if (FileUpload.isMultipartContent(request)) {
//			String temDir = "C:\\Users\\Bit\\git\\07model\\07.Model2MVCShop(URI,pattern)\\WebContent\\images\\uploadFiles\\";
//			
//			DiskFileUpload fileUpload = new DiskFileUpload();
//			fileUpload.setRepositoryPath(temDir);
//			fileUpload.setSizeMax(1024*1024*10);
//			fileUpload.setSizeThreshold(1024*100);
//			if (request.getContentLength()<fileUpload.getSizeMax()) {
//				Product product = new Product();
//				
//				StringTokenizer token = null;
//				
//				List fileItemList = fileUpload.parseRequest(request);
//				int Size = fileItemList.size();
//				for (int i = 0; i < Size; i++) {
//					FileItem fileItem = (FileItem) fileItemList.get(i);
//					if (fileItem.isFormField()) {
//						if (fileItem.getFieldName().equals("manuDate")) {
//							token = new StringTokenizer(fileItem.getString("euc-kr"), "-");
//							String manuDate = token.nextToken()+token.nextToken()+token.nextToken();
//							product.setManuDate(manuDate);
//						}else if (fileItem.getFieldName().equals("prodName")){
//							product.setProdName(fileItem.getString("euc-kr"));
//						}else if (fileItem.getFieldName().equals("prodDetail")) {
//							product.setProdDetail(fileItem.getString("euc-kr"));
//						}else if (fileItem.getFieldName().equals("price")) {
//							product.setPrice(Integer.parseInt(fileItem.getString("euc-kr")));
//						}
//					}else {
//						if (fileItem.getSize()>0) {
//							int idx = fileItem.getName().lastIndexOf("\\");
//							if (idx == -1) {
//								idx = fileItem.getName().lastIndexOf("/");
//							}
//							String fileName = fileItem.getName().substring(idx+1);
//							product.setFileName(fileName);
//							try {
//								File uploadedFile = new File(temDir, fileName);
//								fileItem.write(uploadedFile);
//								
//							} catch (IOException e) {
//								// TODO: handle exception
//								System.out.println(e);
//							}
//						}else {
//							product.setFileName("../../images/empty.GIF");
//						}
//					}
//				}
//				
//				proService.addProduct(product);
//				
//				model.addAttribute("product", product);
//			}else {
//				int overSize = (request.getContentLength()/1000000);
//				System.out.println("<szript>alert('파일의 크기는 1MB까지 입니다. 올리신 파일 용량은 "+overSize+"MB 입니다.');");
//				System.out.println("history.back();</script>");
//			}
//		}else {
//			System.out.println("인코딩 타입이 multipart/form-data가 아닙니다..");
//		}
		
		return product;
	}
	
	@RequestMapping(value="json/getProduct/{prodNo}",method=RequestMethod.GET)
	public Product getProduct(@PathVariable int prodNo,HttpSession session) throws Exception {
		List<String> hisList=(List<String>)session.getAttribute("hisList");
		if (hisList==null) {
			hisList = new ArrayList<String>();
		}
		hisList.add(Integer.toString(prodNo));
		
		session.setAttribute("hisList", hisList);
//		
//		Cookie[] cookies = request.getCookies();
//	    if (cookies != null && cookies.length > 0) {
//	        for (int i = 0 ; i < cookies.length ; i++) {
//	        	if (cookies[i].getName().equals("history")) {
//	        		String value=cookies[i].getValue()+",";
//	        		value+=Integer.toString(prodNo);
//	        		cookies[i].setValue(value);
//	                response.addCookie(cookies[i]);
//	                System.out.println("getpr cookie 네임"+cookies[i].getName());
//				}else {
//			    	Cookie cookie = new Cookie("history", Integer.toString(prodNo));
//		            response.addCookie(cookie);
//		            System.out.println("getpr cookie 네임"+cookie.getName());
//			    }
//	        }
//	    }

		return proService.getProduct(prodNo);
	}
	
	/*
	@RequestMapping("/listProduct.do")
	public String listProduct(@ModelAttribute("search") Search search , Model model , HttpServletRequest request) throws Exception {
		System.out.println("리스트프로덕트엑션 시작");

		if(search.getCurrentPage()==0){
			search.setCurrentPage(1);
		}
		System.out.println("search::::::::"+search);
		// web.xml  meta-data 로 부터 상수 추출 
		search.setPageSize(pageSize);
		
		// Business logic 수행
		Map<String , Object> map=proService.getProductList(search);
		
		Page resultPage	= 
					new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println("resultPage ::"+resultPage);
		
		// Model 과 View 연결
		model.addAttribute("listProd", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		System.out.println("리스트프로덕트엑션 끝");
		
		return "forward:/product/listProduct.jsp";
	}
	*/
	
	@RequestMapping(value="json/listProduct",method=RequestMethod.POST)
	public Map<String, Object> listProduct(@RequestBody Map<String, Object> requestMap) throws Exception {
		
		System.out.println("/listProduct");
		Search search = new Search();
		search.setSearchCondition(requestMap.get("searchCondition").toString());
		search.setSearchKeyword(requestMap.get("searchKeyword").toString());
		String kind = requestMap.get("kind").toString();
		
		System.out.println("search :: "+search);
		System.out.println("kind :: "+kind);
		if(search.getCurrentPage()==0) {
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		System.out.println(search.getSearchKeyword());

		//Business Logic 수행
		if(kind.isEmpty()) {
			kind="";
		}
		Map<String, Object> map = proService.getProductList(search,kind);
		
		Page resultPage = new Page(search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);

		//Model과 View 연결
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("list", map.get("list"));
		map1.put("resultPage", resultPage);
		map1.put("search", search);
		map1.put("kind", kind);
		
		return map1;
	}
	
	@RequestMapping(value="json/updateProduct",method=RequestMethod.POST)
	public Product updateProduct(@RequestBody Product product,HttpServletRequest request) throws Exception {
		
		proService.updateProduct(product);
		System.out.println("유프 끝");
		return product;
	}
	
	@RequestMapping(value="json/updateProductView")
	public Product updateProductView(@RequestParam("prodNo") int prodNo) throws Exception {
		System.out.println("updateProductView");
		return proService.getProduct(prodNo);
	}
}
