package com.springJWT.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springJWT.model.ERoller;
import com.springJWT.model.Kisi;
import com.springJWT.model.KisiRole;
import com.springJWT.repository.KisiRepository;
import com.springJWT.repository.RoleRepository;
import com.springJWT.reqres.LoginRequest;
import com.springJWT.reqres.JwtResponse;
import com.springJWT.reqres.MesajResponse;
import com.springJWT.reqres.RegisterRequest;
import com.springJWT.security.jwt.JwtUtils;
import com.springJWT.service.KisiServiceImpl;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	KisiRepository kisiRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@PostMapping("/login")
	public ResponseEntity<?> girisYap(@RequestBody LoginRequest loginRequest){
		
		Authentication authentication = authenticationManager
										.authenticate(new UsernamePasswordAuthenticationToken(
												loginRequest.getUsername(),loginRequest.getPassword()));
		
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.jwtOlustur(authentication);
		
		
		KisiServiceImpl loginKisi = (KisiServiceImpl) authentication.getPrincipal();
		
		List<String> roller = loginKisi.getAuthorities()
										.stream().map(item -> item.getAuthority())
										.collect(Collectors.toList());
		
		
		
		return ResponseEntity.ok(new JwtResponse(
									 jwt,
									 loginKisi.getId(),
									 loginKisi.getUsername(),
									 loginKisi.getEmail(),
									 roller));
		
	}
	
	
	@PostMapping("/register")
	public ResponseEntity<?> kayitOl(@RequestBody RegisterRequest registerRequest){
		
		if (kisiRepository.existsByUsername(registerRequest.getUsername())) {
			
			return ResponseEntity.badRequest()
					.body(new MesajResponse("Hata : Username kullanılıyor."));
			
		}
		
		if (kisiRepository.existsByEmail(registerRequest.getEmail())) {
			
			return ResponseEntity.badRequest()
					.body(new MesajResponse("Hata : email kullanılıyor."));
			
		}
		
		Kisi kisi=new Kisi(registerRequest.getUsername(), passwordEncoder.encode(registerRequest.getPassword()), registerRequest.getEmail());
		
		Set<String> stringRoller = registerRequest.getRole();
		Set<KisiRole> roller = new HashSet<>();
		
		
		if (stringRoller == null) {
			
			KisiRole userRole = roleRepository.findByName(ERoller.ROLE_USER).orElseThrow(() -> new RuntimeException("Hata : Veritabanında Role kayıtlı değil")); 
			
			roller.add(userRole);
		}else {
			stringRoller.forEach(role -> {
				//*******ÖNEMLİ ==> Hoca switch case ile yaptı bende hata verdi. Ben de İf clouse ile yaptım. 
				if (role.equals("admin")) {
					KisiRole adminRole = roleRepository.findByName(ERoller.ROLE_ADMIN).
							orElseThrow(() -> new RuntimeException("Hata: Role mevcut değil."));
					roller.add(adminRole);
				}else if(role.equals("mod")) {
					KisiRole modRole = roleRepository.findByName(ERoller.ROLE_MODERATOR)
	                        .orElseThrow(() -> new RuntimeException("EHata: Role mevcut değil."));
	                roller.add(modRole);
				}else {
					KisiRole userRole = roleRepository.findByName(ERoller.ROLE_USER)
	                        .orElseThrow(() -> new RuntimeException("Hata: Role mevcut değil."));
	                roller.add(userRole);
				}
				
			});
			
			kisi.setRoller(roller);
			kisiRepository.save(kisi);
					
		}
		
		return ResponseEntity.ok(new MesajResponse("Kullanıcı başarıyla kaydedildi.."));

	}
}
