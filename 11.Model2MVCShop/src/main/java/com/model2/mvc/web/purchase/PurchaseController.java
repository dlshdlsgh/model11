package com.model2.mvc.web.purchase;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.user.UserService;

@Controller
@RequestMapping("/purchase/*")
public class PurchaseController {
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
	
	public PurchaseController() {
		// TODO Auto-generated constructor stub
		System.out.println();
	}

	@RequestMapping("addPurchase")
	public ModelAndView addPurchase(@RequestParam("buyerId") String buyerId,@RequestParam("prodNo") int prodNo,HttpServletRequest request) throws Exception {
		System.out.println("addPurchaseAction ���� ::");

		Purchase purchase=new Purchase();
		purchase.setBuyer(userService.getUser(buyerId));
		purchase.setPurchaseProd(proService.getProduct(prodNo));
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		purchase.setReceiverName(request.getParameter("receiverName"));
		purchase.setReceiverPhone(request.getParameter("receiverPhone"));
		purchase.setDivyAddr(request.getParameter("receiverAddr"));
		purchase.setDivyRequest(request.getParameter("receiverRequest"));
		purchase.setDivyDate(request.getParameter("receiverDate"));
		System.out.println(purchase);
		
		purService.addPurchase(purchase);
		
		System.out.println("purchase vo:::"+purchase);
		System.out.println("addPurchaseAction �� ::");
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/purchase/addPurchase.jsp");
		modelAndView.addObject("purchase", purchase);
		
		return modelAndView;
	}
	
	@RequestMapping("addPurchaseView")
	public ModelAndView addPurchaseView(@RequestParam("prodNo") int prodNo) throws Exception {
		System.out.println("addPurchaseView ����:::::::::::");
		System.out.println("prodNo:::"+prodNo);
		
		Product product=proService.getProduct(prodNo);
		System.out.println("action�ȿ� prodVO"+product);
		
		System.out.println("addPurchaseView ��:::::::::::");
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/purchase/addPurchaseView.jsp");
		modelAndView.addObject("product", product);
		
		return modelAndView;
	}
	
	@RequestMapping("getPurchase")
	public ModelAndView getPurchase(@RequestParam("tranNo") int tranNo) throws Exception {
		System.out.println("getPurchaseAction ����");
		
		Purchase purchase=purService.getPurchase(tranNo);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/purchase/getPurchase.jsp");
		modelAndView.addObject("purchase", purchase);
		
		System.out.println("getPurchaseAction ��");
		
		return modelAndView;
	}
	
	@RequestMapping("listPurchase")
	public ModelAndView listPurchase(@ModelAttribute("search") Search search,HttpSession session) throws Exception {
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
		modelAndView.setViewName("forward:/purchase/listPurchase.jsp");
		modelAndView.addObject("list", map.get("list"));
		modelAndView.addObject("resultPage", resultPage);
		modelAndView.addObject("search", search);
		
		System.out.println("����Ʈ�Ǹſ��� ��");
		
		return modelAndView;
	}
	
	@RequestMapping("updatePurchase")
	public ModelAndView updatePurchase(@RequestParam("tranNo") int tranNo,HttpServletRequest request) throws Exception {
		
		Purchase purchase = new Purchase();
		purchase.setTranNo(tranNo);
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		purchase.setReceiverName(request.getParameter("receiverName"));
		purchase.setReceiverPhone(request.getParameter("receiverPhone"));
		purchase.setDivyRequest(request.getParameter("receiverRequest"));
		purchase.setDivyAddr(request.getParameter("receiverAddr"));
		purchase.setDivyDate(request.getParameter("divyDate"));
		
		purService.updatePurcahse(purchase);
		System.out.println("������Ʈ�� purchase"+purchase);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/purchase/getPurchase?tranNo="+Integer.toString(tranNo));
		
		System.out.println("���� ��");
		
		return modelAndView;
	}
	
	@RequestMapping("updatePurchaseView")
	public ModelAndView updatePurchaseView(@RequestParam("tranNo") int tranNo) throws Exception {
		
		Purchase purchase=purService.getPurchase(tranNo);
		System.out.println("������Ʈ�޺信�� purchase::"+purchase);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/purchase/updatePurchase.jsp");
		modelAndView.addObject("purchase", purchase);
		
		return modelAndView;
	}
	
	@RequestMapping(value="updateTranCode",method=RequestMethod.POST)
	public ModelAndView updateTranCode(HttpServletRequest request) throws Exception {
		
		Purchase purchase=(Purchase)request.getAttribute("purchase");
		
		System.out.println("������ƮƮ���ڵ� vo::::::"+purchase);
		purService.updateTranCode(purchase);
		
		User user=userService.getUser(purchase.getBuyer().getUserId());
		System.out.println("���� ��");
		
		String viewName="redirect:/product/listProduct?menu=manage";
		
		if (user.getRole().equals("admin")&&purchase.getTranCode().equals("2")) {
			viewName= "redirect:/product/listProduct?menu=manage";
		}else if(user.getRole().equals("user")&&purchase.getTranCode().equals("3")){
			viewName= "redirect:/purchase/listPurchase";
		}
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(viewName);
		
		System.out.println("Ʈ���ڵ������Ʈ��~~");
		return modelAndView;
	}
	
	@RequestMapping("updateTranCodeByProd")
	public ModelAndView updateTranCodeByProd(@RequestParam("prodNo") int prodNo,@RequestParam("tranCode") String tranCode) throws Exception {
		
		Purchase purchase = purService.getPurchase2(prodNo);
		purchase.setPurchaseProd(proService.getProduct(prodNo));
		purchase.setTranCode(tranCode);
		
		System.out.println("��Ʈ���ڵ� purchase::"+purchase);
		
		System.out.println("��Ʈ���������δ���");
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/purchase/updateTranCode");
		modelAndView.addObject("purchase", purchase);
		
		return modelAndView;
	}
}
