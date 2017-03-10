package com.wosai.bright.common;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @spring.bean id="busiSpringBeanUtils"
 *  
 * @version 1.0
 */
@Service("springBeanUtils")
public class SpringBeanUtils {
	
	private static SpringBeanAdapter beanAdapter;
	
	@Resource
	private SpringBeanAdapter beanAdapterRAM;

	@PostConstruct
	public void init() {
		beanAdapter = this.beanAdapterRAM;
	}
	
	public static Object getBean(String name) {
		return beanAdapter.getBean(name);
	}

	/**
	 * 
	 * @spring.property ref="busiBeanAdapter"
	 * 设置beanAdapter 到 beanAdapter
	 * @param beanAdapter
	 *            The beanAdapter to set.
	 */
	public void setBeanAdapter(SpringBeanAdapter beanAdapter) {
		SpringBeanUtils.beanAdapter = beanAdapter;
	}
}
