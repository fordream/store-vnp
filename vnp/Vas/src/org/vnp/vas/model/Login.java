/**
 * 
 */
package org.vnp.vas.model;

import com.ict.library.database.CommonDataStore;

/**
 * @author tvuong1pc
 * 
 */
public class Login {
	public Login() {
	}

	public void setLogin(boolean isLogin) {
		CommonDataStore.getInstance().save("Login_login", isLogin);
	}

	public boolean isLogin() {
		return CommonDataStore.getInstance().get("Login_login", false);
	}

	public String getPassword() {
		return CommonDataStore.getInstance().get("Login_password", "");
	}

	public void setPassword(String password) {
		CommonDataStore.getInstance().save("Login_password", password);
	}
	public String getUserName() {
		return CommonDataStore.getInstance().get("Login_UserName", "");
	}

	public void setUserName(String username) {
		CommonDataStore.getInstance().save("Login_UserName", username);
	}
}