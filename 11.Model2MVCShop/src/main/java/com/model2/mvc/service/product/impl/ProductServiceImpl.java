package com.model2.mvc.service.product.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductDao;
import com.model2.mvc.service.product.ProductService;

@Service("productServiceImpl")
public class ProductServiceImpl implements ProductService {
	@Autowired
	@Qualifier("productDaoImpl")
	private ProductDao proDao;

	public void setProDao(ProductDao proDao) {
		this.proDao = proDao;
	}

	public ProductServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addProduct(Product productVO) throws Exception {
		// TODO Auto-generated method stub
		proDao.insertProduct(productVO);
	}

	@Override
	public Product getProduct(int prodNo) throws Exception {
		// TODO Auto-generated method stub
		return proDao.findProduct(prodNo);
	}

	@Override
	public Map<String, Object> getProductList(Search search,String kind) throws Exception {
		// TODO Auto-generated method stub

		return proDao.getProductList(search, kind);
	}

	@Override
	public void updateProduct(Product productVO) throws Exception {
		// TODO Auto-generated method stub
		proDao.updateProduct(productVO);
	}

}
