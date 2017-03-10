package com.wosai.bright.common;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Service;

/**
 * @spring.bean id="busiBeanAdapter"
 *  
 * 用于动态加载bean
 *  
 * @version 1.0
 */
@Service("beanAdapter")
public class SpringBeanAdapter implements BeanFactoryAware {
	
	protected BeanFactory beanFactory;

	@Override
	public void setBeanFactory(BeanFactory arg0) throws BeansException {
		// TODO Auto-generated method stub
		this.beanFactory = arg0;
	}

	/**
	 * 动态获取一个Bean
	 * 
	 * @param beanName
	 * @return
	 */
	public Object getBean(String beanName) {
		try {
			return beanFactory.getBean(beanName);
		} catch (Exception ex) {
			return ex;
		}
	}

}
