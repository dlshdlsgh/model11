package com.model2.mvc.web.product;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

@Controller
@RequestMapping("/product/*")
public class ProductController {
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService proService;
	
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;

	public ProductController() {
		// TODO Auto-generated constructor stub
	}

	@RequestMapping(value="addProduct",method=RequestMethod.POST)
	public String addProduct(HttpServletRequest request,Model model,@RequestParam("fileName") MultipartFile multiPartFile) throws Exception {
		
		String temDir = "C:\\workspace\\11.Model2MVCShop\\WebContent\\images\\uploadFiles\\";
		
		Product product=new Product();
		product.setProdName(request.getParameter("prodName"));
		product.setProdDetail(request.getParameter("prodDetail"));
		product.setManuDate(request.getParameter("manuDate"));
		product.setPrice(Integer.parseInt(request.getParameter("price")));
		product.setFileName(multiPartFile.getOriginalFilename());
		product.setKind(request.getParameter("kind"));
		
		model.addAttribute("product", product);
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
		
		return "getProduct.jsp";
	}
	
	@RequestMapping("getProduct")
	public ModelAndView getProduct(@RequestParam("prodNo") int prodNo,HttpSession session) throws Exception {
		Product product=proService.getProduct(prodNo);
		
		
		List<Product> hisList=(List<Product>)session.getAttribute("hisList");
		List<Product> resultList = new ArrayList<Product>();
		
		resultList.add(product);
		if (hisList!=null) {
			for (int i = 0; i < hisList.size(); i++) {
				if (product.getProdNo()!=hisList.get(i).getProdNo()) {
					resultList.add(hisList.get(i));
				}
			}
		}
		
		session.setAttribute("hisList", resultList);
	    
	    ModelAndView modelAndView = new ModelAndView();
	    modelAndView.setViewName("forward:/product/getProduct.jsp");
		modelAndView.addObject("product", product);

		return modelAndView;
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
	
	@RequestMapping(value="listProduct")
	public String listProduct(@ModelAttribute("search") Search search, Model model,@ModelAttribute("kind") String kind,HttpServletRequest request) throws Exception {
		
		System.out.println("/listProduct");
		
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
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		model.addAttribute("kind", kind);
		if (request.getParameter("menu").equals("search")) {
			return "forward:/product/listProductSearch.jsp";
		}
		return "forward:/product/listProduct.jsp";
	}
	
	@RequestMapping(value="updateProduct",method=RequestMethod.POST)
	public String updateProduct(@RequestParam("prodNo") int prodNo,@RequestParam("price") int price,HttpServletRequest request) throws Exception {
		
		Product product = new Product();
		product.setProdNo(prodNo);
		product.setProdName(request.getParameter("prodName"));
		product.setProdDetail(request.getParameter("prodDetail"));
		product.setManuDate(request.getParameter("manuDate"));
		product.setPrice(price);
		product.setFileName(request.getParameter("fileName"));
		
		proService.updateProduct(product);
		System.out.println("유프 끝");
		return "redirect:/product/getProduct?prodNo="+prodNo;
	}
	
	@RequestMapping(value="updateProductView")
	public ModelAndView updateProductView(@RequestParam("prodNo") int prodNo) throws Exception {
		
		Product product=proService.getProduct(prodNo);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/product/updateProduct.jsp");
		modelAndView.addObject("product", product);
		
		return modelAndView;
	}
	
	@RequestMapping(value="historyList")
	public ModelAndView historyList(HttpSession session,@ModelAttribute("search") Search search,@ModelAttribute("kind") String kind) {
		
		List<Product> hisList=(List<Product>)session.getAttribute("hisList");
		if(search.getCurrentPage()==0) {
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		System.out.println(search.getSearchKeyword());

		//Business Logic 수행
		if(kind.isEmpty()) {
			kind="";
		}
		
		Page resultPage = new Page(search.getCurrentPage(),hisList.size(), pageUnit, pageSize);
		System.out.println(resultPage);

		//Model과 View 연결
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("history.jsp");
		modelAndView.addObject("hisList", hisList);
		modelAndView.addObject("resultPage", resultPage);
		modelAndView.addObject("search", search);
		modelAndView.addObject("kind", kind);
		
		
		return modelAndView;
	}
}
