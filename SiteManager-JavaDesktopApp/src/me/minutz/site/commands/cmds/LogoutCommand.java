package me.minutz.site.commands.cmds;

import me.minutz.site.Main;
import me.minutz.site.commands.Command;
import me.minutz.site.commands.CommandManager;

public class LogoutCommand extends Command{

	public LogoutCommand(CommandManager cmdm) {
		super(cmdm, "logout");
		// TODO Auto-generated constructor stub
	}

	@Override
	public String execute(String[] args) {
		return execute();
	}

	@Override
	public String execute() {
		Main.props.set("uname", "null");
		Main.props.set("upass", "null");
		System.out.println("Logged out user "+cmdm.main.username);
		System.exit(1);
		return null;
	}

	@Override
	public String help() {
		return "logout";
	}

}
