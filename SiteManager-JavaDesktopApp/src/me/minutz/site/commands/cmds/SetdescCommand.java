package me.minutz.site.commands.cmds;

import me.minutz.site.MainTip;
import me.minutz.site.commands.Command;
import me.minutz.site.commands.CommandManager;

public class SetdescCommand extends Command{

	public SetdescCommand(CommandManager cmdm) {
		super(cmdm, "setdesc");
	}

	@Override
	public String execute(String[] args) {
		String serial = args[0];
		
		if(serial != null){
			String desc = "";
		for(String s:args){
			if(!s.equals(serial)){
				if(desc.isEmpty()){
					desc=s;	
				}else{
				desc=desc+" "+s;
				}
			}
		}
		MainTip.SQL.updateDesc(serial, desc);
		return "Description set!";
		}else{
			return execute();
		}
		
	}

	@Override
	public String execute() {
		return "INCORRECT USE:\n"+help();
	}

	@Override
	public String help() {
		return "Set a description with /setdesc [serial] [desc]";
	}

}
