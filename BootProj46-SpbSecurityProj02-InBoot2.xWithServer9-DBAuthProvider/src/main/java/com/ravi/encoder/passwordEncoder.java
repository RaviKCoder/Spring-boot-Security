package com.ravi.encoder;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class passwordEncoder {

	public static void main(String[] args) {
		BCryptPasswordEncoder pwEncoder=new BCryptPasswordEncoder();
		String pwd1 = pwEncoder.encode("rani");
		String pwd2 = pwEncoder.encode("hyd");
		String pwd3 = pwEncoder.encode("vizag");
		System.out.println(pwd1+"...."+pwd2+"...."+pwd3);
	}
}
