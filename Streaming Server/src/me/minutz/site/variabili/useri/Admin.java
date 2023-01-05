package me.minutz.site.variabili.useri;

import me.minutz.site.Role;

public class Admin extends User{

	public Admin(String nume, String parola, String uuid, String lastActivity, String lastIp,
			boolean banned) {
		super(nume, parola, uuid, lastActivity, lastIp, Role.Admin, banned,"");
	}

}