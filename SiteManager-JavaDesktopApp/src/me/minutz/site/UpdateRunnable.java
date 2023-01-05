package me.minutz.site;

import me.minutz.site.commands.Command;
import me.minutz.site.util.Role;

public class UpdateRunnable implements Runnable{

	Main m;
	
	public UpdateRunnable(Main ma){
		m=ma;
	}
	
	@Override
	public void run() {
		if(m.log){
    	if(Main.checkUpdate()){
        	System.out.println("Update found!");
    		Main.callUpdate();
    	}
		}
		if(m.log){
		User u = MainTip.getUserByNume(m.username);
		if(u.isBanned()){
			Command c = Main.command.getCommand("logout");
			System.out.println("You are banned!");
			c.execute();
		}
		if(u.getRole()==Role.User){
			Command c = Main.command.getCommand("logout");
			System.out.println("Unauthorized!");
			c.execute();
		}
	}
	}

}
