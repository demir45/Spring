package com.springJWT.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {
	
	
	@RequestMapping("/all")
	public String allAccess() {
		return "Herkese açık içerik";
	}

	@RequestMapping("/user")
	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN','ROLE_MODERATOR')")
	public String userAccess() {
		return "Yalnızca kayıtlı kişilere açık içerik";
	}
	
	@RequestMapping("/mod")
	@PreAuthorize("hasAnyRole('ROLE_MODERATOR')")
	public String modAccess() {
		return "Yalnızca Moderatör'e açık içerik";
	}
	
	@RequestMapping("/admin")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	public String adminAccess() {
		return "Yalnızca Admine açık içerik";
	}
	
}
