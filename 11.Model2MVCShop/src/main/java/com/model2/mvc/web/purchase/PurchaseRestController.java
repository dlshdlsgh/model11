package com.model2.mvc.web.purchase;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.user.UserService;

@RestController
@RequestMapping("/purchase/*")
public class PurchaseRestController {
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purService;
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService proService;
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	public PurchaseRestController() {
		// TODO Auto-generated constructor stub
		System.out.println("PurchasRestController");
	}

	@RequestMapping(value="json/addPurchase",method=RequestMethod.POST)
	public Purchase addPurchase(@RequestBody Purchase purchase) throws Exception {
		System.out.println("addPurchaseAction 시작 ::");
		System.out.println(purchase);
		
		purService.addPurchase(purchase);
		
		System.out.println("purchase vo:::"+purchase);
		System.out.println("addPurchaseAction 끝 ::");

		
		return purchase;
	}
	
	@RequestMapping(value="json/addPurchaseView/{prodNo}",method=RequestMethod.GET)
	public Product addPurchaseView(@PathVariable int prodNo) throws Exception {
		System.out.println("addPurchaseView 시작:::::::::::");
		System.out.println("prodNo:::"+prodNo);
		
		System.out.println("addPurchaseView 끝:::::::::::");
		
		return proService.getProduct(prodNo);
	}
	
	@RequestMapping(value="json/getPurchase/{tranNo}",method=RequestMethod.GET)
	public Purchase getPurchase(@PathVariable int tranNo) throws Exception {
		System.out.println("getPurchaseAction 시작");
		
		System.out.println("getPurchaseAction 끝");
		
		return purService.getPurchase(tranNo);
	}
	
	@RequestMapping(value="json/listPurchase",method=RequestMethod.POST)
	public ModelAndView listPurchase(@RequestBody Search search,HttpSession session) throws Exception {
		System.out.println("리스트판매엑션 시작");
		User user = (User)session.getAttribute("user");
		
		if(search.getCurrentPage()==0){
			search.setCurrentPage(1);;
		}

		search.setPageSize(pageSize);
		
		// Business logic 수행
		Map<String , Object> map=purService.getPurchaseList(search, user.getUserId());
		
		Page resultPage	= 
					new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println("ListPurchaseAction ::"+resultPage);
		
		// Model 과 View 연결
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("list", map.get("list"));
		modelAndView.addObject("resultPage", resultPage);
		modelAndView.addObject("search", search);
		
		System.out.println("리스트판매엑션 끝");
		
		return modelAndView;
	}
	
	@RequestMapping(value="json/updatePurchase",method=RequestMethod.POST)
	public Purchase updatePurchase(@RequestBody Purchase purchase) throws Exception {
		
		purService.updatePurcahse(purchase);
		System.out.println("업데이트할 purchase"+purchase);
		
		System.out.println("유펄 끝");
		
		return purchase;
	}
	
	@RequestMapping(value="json/updatePurchaseView/{tranNo}",method=RequestMethod.GET)
	public Purchase updatePurchaseView(@PathVariable("tranNo") int tranNo) throws Exception {
		
		System.out.println("업데이트펄뷰에서 purchase::");
		
		return purService.getPurchase(tranNo);
	}
	
	@RequestMapping(value="json/updateTranCode",method=RequestMethod.POST)
	public Purchase updateTranCode(@RequestBody Purchase purchase) throws Exception {
		System.out.println("업데이트트렌코드 vo::::::"+purchase);
		purService.updateTranCode(purchase);
		
		System.out.println("트렌코드업데이트끝~~");
		return purchase;
	}
	
	@RequestMapping(value="json/updateTranCodeByProd/{prodNo}/{tranCode}")
	public Purchase updateTranCodeByProd(@PathVariable("prodNo") int prodNo,@PathVariable("tranCode") String tranCode) throws Exception {
		
		Purchase purchase = purService.getPurchase2(prodNo);
		purchase.setPurchaseProd(proService.getProduct(prodNo));
		purchase.setTranCode(tranCode);
		
		System.out.println("업트렌코드 purchase::"+purchase);
		
		System.out.println("업트렌바이프로덕끝");
		
		return purchase;
	}
}
