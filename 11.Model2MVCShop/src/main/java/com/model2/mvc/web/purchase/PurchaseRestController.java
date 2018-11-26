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
		System.out.println("addPurchaseAction ���� ::");
		System.out.println(purchase);
		
		purService.addPurchase(purchase);
		
		System.out.println("purchase vo:::"+purchase);
		System.out.println("addPurchaseAction �� ::");

		
		return purchase;
	}
	
	@RequestMapping(value="json/addPurchaseView/{prodNo}",method=RequestMethod.GET)
	public Product addPurchaseView(@PathVariable int prodNo) throws Exception {
		System.out.println("addPurchaseView ����:::::::::::");
		System.out.println("prodNo:::"+prodNo);
		
		System.out.println("addPurchaseView ��:::::::::::");
		
		return proService.getProduct(prodNo);
	}
	
	@RequestMapping(value="json/getPurchase/{tranNo}",method=RequestMethod.GET)
	public Purchase getPurchase(@PathVariable int tranNo) throws Exception {
		System.out.println("getPurchaseAction ����");
		
		System.out.println("getPurchaseAction ��");
		
		return purService.getPurchase(tranNo);
	}
	
	@RequestMapping(value="json/listPurchase",method=RequestMethod.POST)
	public ModelAndView listPurchase(@RequestBody Search search,HttpSession session) throws Exception {
		System.out.println("����Ʈ�Ǹſ��� ����");
		User user = (User)session.getAttribute("user");
		
		if(search.getCurrentPage()==0){
			search.setCurrentPage(1);;
		}

		search.setPageSize(pageSize);
		
		// Business logic ����
		Map<String , Object> map=purService.getPurchaseList(search, user.getUserId());
		
		Page resultPage	= 
					new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println("ListPurchaseAction ::"+resultPage);
		
		// Model �� View ����
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("list", map.get("list"));
		modelAndView.addObject("resultPage", resultPage);
		modelAndView.addObject("search", search);
		
		System.out.println("����Ʈ�Ǹſ��� ��");
		
		return modelAndView;
	}
	
	@RequestMapping(value="json/updatePurchase",method=RequestMethod.POST)
	public Purchase updatePurchase(@RequestBody Purchase purchase) throws Exception {
		
		purService.updatePurcahse(purchase);
		System.out.println("������Ʈ�� purchase"+purchase);
		
		System.out.println("���� ��");
		
		return purchase;
	}
	
	@RequestMapping(value="json/updatePurchaseView/{tranNo}",method=RequestMethod.GET)
	public Purchase updatePurchaseView(@PathVariable("tranNo") int tranNo) throws Exception {
		
		System.out.println("������Ʈ�޺信�� purchase::");
		
		return purService.getPurchase(tranNo);
	}
	
	@RequestMapping(value="json/updateTranCode",method=RequestMethod.POST)
	public Purchase updateTranCode(@RequestBody Purchase purchase) throws Exception {
		System.out.println("������ƮƮ���ڵ� vo::::::"+purchase);
		purService.updateTranCode(purchase);
		
		System.out.println("Ʈ���ڵ������Ʈ��~~");
		return purchase;
	}
	
	@RequestMapping(value="json/updateTranCodeByProd/{prodNo}/{tranCode}")
	public Purchase updateTranCodeByProd(@PathVariable("prodNo") int prodNo,@PathVariable("tranCode") String tranCode) throws Exception {
		
		Purchase purchase = purService.getPurchase2(prodNo);
		purchase.setPurchaseProd(proService.getProduct(prodNo));
		purchase.setTranCode(tranCode);
		
		System.out.println("��Ʈ���ڵ� purchase::"+purchase);
		
		System.out.println("��Ʈ���������δ���");
		
		return purchase;
	}
}
