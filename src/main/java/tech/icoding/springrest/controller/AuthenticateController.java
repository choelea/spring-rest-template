package tech.icoding.springrest.controller;

import java.util.Collections;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import tech.icoding.springrest.data.MsgData;
import tech.icoding.springrest.data.TokenData;
import tech.icoding.springrest.data.UserInfo;
import tech.icoding.springrest.form.LoginForm;


/**
 * Fake for UI
 * @author joe
 *
 */
@RestController
public class AuthenticateController {
	/**
	 * 模拟
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	@PostMapping("/auth/authenticate")
	public ResponseEntity<?> authenticate(@RequestBody LoginForm loginForm) {
		if ("superAdmin".equals(loginForm.getUsername()) && "superAdmin".equals(loginForm.getPassword())) {
			TokenData data = new TokenData();
			data.setToken("000-1112-324234234-3434");
			data.setUserInfo(new UserInfo(1, "superAdmin", "Super Admin", "ADMIN", Collections.singletonList("ALL")));
			return ResponseEntity.ok(data);
		} else {
			return ResponseEntity.badRequest().body(new MsgData("用户名/密码 错误"));
		}
	}

	@GetMapping("/currentUser")
	public ResponseEntity<?> currentUser() {
		return ResponseEntity
				.ok(new UserInfo(1, "superAdmin", "Super Admin", "ADMIN", Collections.singletonList("ALL")));
	}
}
