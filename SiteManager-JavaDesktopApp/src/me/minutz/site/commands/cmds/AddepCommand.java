package me.minutz.site.commands.cmds;

import me.minutz.site.MainTip;
import me.minutz.site.commands.Command;
import me.minutz.site.commands.CommandManager;

public class AddepCommand extends Command{

	public AddepCommand(CommandManager cmdm) {
		super(cmdm, "addep");
	}

	@Override
	public String execute(String[] args) {
		String serial = args[0];
		String posterlink = args[1];
		int sezon = Integer.parseInt(args[2]);
		int nr = Integer.parseInt(args[3]);
		int an = Integer.parseInt(args[4]);
		if(serial!=null && posterlink != null){
		MainTip.SQL.addEpisod(serial, posterlink, nr, sezon, an);
		return "Episode added!";
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
		return "Add an episode with /addep [serial] [posterlink] [season] [number] [year]";
	}

}
