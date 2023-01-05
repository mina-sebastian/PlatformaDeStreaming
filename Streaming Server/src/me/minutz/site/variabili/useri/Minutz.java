package me.minutz.site.variabili.useri;

import me.minutz.site.Role;

public class Minutz extends Admin{

	public Minutz(String nume, String parola, String uuid, String lastActivity, String lastIp, boolean banned) {
		super(nume, parola, uuid, lastActivity, lastIp, banned);
		setRole(Role.Minutz);
	}

}
