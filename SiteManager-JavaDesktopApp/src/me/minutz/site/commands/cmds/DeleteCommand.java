package me.minutz.site.commands.cmds;

import me.minutz.site.MainTip;
import me.minutz.site.User;
import me.minutz.site.commands.Command;
import me.minutz.site.commands.CommandManager;
import me.minutz.site.util.Role;

public class DeleteCommand extends Command{

	public DeleteCommand(CommandManager cmdm) {
		super(cmdm, "delete");
	}

	@Override
	public String execute(String[] args) {
		String nume = args[0];
		User u = getUser();
		if(u.getRole()==Role.Minutz){
			if(nume != null){
				User t = MainTip.getUserByNume(nume);
				if(t != null){
					MainTip.SQL.execute("DELETE FROM useri WHERE nume='"+nume+"'");
					return nume+" was deleted";
				}else{
					return nume+" not found";
				}
			}else{
				return execute();
			}
		}else{
			return noPerm+" to delete an user";
		}
	}

	@Override
	public String execute() {
		return "INCORRECT USE:\n"+help();
	}

	@Override
	public String help() {
		return "Delete an user(Minutz only)";
	}

}
