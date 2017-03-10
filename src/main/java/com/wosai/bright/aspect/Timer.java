package com.wosai.bright.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component // 初始化Bean
@Aspect
public class Timer {

	// private Logger logger =Logger.getLogger(getClass());

	// 可以尝试下这两种注解
	// @Around("execution(* org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter.handle(..))")
	@Around("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public Object logTimer(ProceedingJoinPoint thisJoinPoint) throws Throwable {
		String clazzName = thisJoinPoint.getTarget().getClass().getName();
		String methodName = thisJoinPoint.getSignature().getName();

		// 忽略entity方法，调用的日志
		if(methodName.equals("entity")){
			return thisJoinPoint.proceed();
		}
				
		// 计时并调用目标函数
		long start = System.currentTimeMillis();
		Exception e = null;
		Object result = null;
		try {
			result = thisJoinPoint.proceed();
		} catch (Exception ee) {
			e = ee;
		}
		long time = System.currentTimeMillis() - start;

		String resultOut = result != null ? result.toString() : "";
		
		// 输出计时信息 TODO 使用log4j进行日志管理
		System.out.println("[bright]  操作计时：" + time + "ms  类名: " + clazzName + "  方法名:" + methodName + "() result:" + resultOut);
		if(e != null){
 			throw e;
		}
		return result;
	}
}