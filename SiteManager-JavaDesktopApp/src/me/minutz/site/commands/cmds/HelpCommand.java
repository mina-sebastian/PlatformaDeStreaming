package me.minutz.site.commands.cmds;

import me.minutz.site.commands.Command;
import me.minutz.site.commands.CommandManager;

public class HelpCommand extends Command{

	public HelpCommand(CommandManager cmdm) {
		super(cmdm, "help");
	}

	@Override
	public String execute(String[] args) {
		return execute();
	}

	@Override
	public String execute() {
		StringBuilder sb = new StringBuilder();
		sb.append("Help list:\n");
		int i = 0;
		for(Command c:cmdm.cmds){
			i++;
			sb.append("/"+c.getCmd()+": "+c.help());
			if(i<cmdm.cmds.size()){
				sb.append("\n");
			}
		}
		return sb.toString();
	}

	@Override
	public String help() {
		return "help";
	}

}
