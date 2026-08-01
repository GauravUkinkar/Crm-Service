package com.CrmService.util;

public class RolesConstants {
	public static final String[] SUPER_ADMIN={"/UserController/delete"};
	public static final String[] ADMIN= {"/UserController/register","/UserController/{username}"};
	public static final String[] MANAGER= {"/User/getAllUsers"};
	public static final String[] EMPLOYEE= {"/TaskController/add"};
}
