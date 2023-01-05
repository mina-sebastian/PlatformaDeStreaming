package me.minutz.site.commands;

import me.minutz.site.MainTip;
import me.minutz.site.User;

public abstract class Command {
	
	public String cmd;
	public CommandManager cmdm;
	public String noPerm = "You don't have the permission";
	
	public Command(CommandManager cmdm,String cmd){
		this.cmd = cmd;
		this.cmdm = cmdm;
	}
	
	
	public User getUser(){
		User u = MainTip.getUserByNume(cmdm.main.username);
		return u;
	}
	
	public String getCmd() {
		return cmd;
	}


	public CommandManager getCommandManager() {
		return cmdm;
	}

	public abstract String execute(String[] args);
	public abstract String execute();
	public abstract String help();

}
