package com.wosai.bright;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class StartupInit {

	@PostConstruct
	public void init() {
		System.out.println("StartupInit..");

		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					// 启动TCP/UDP服务
					// new EchoServer().startService();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		t.start();
	}
}
