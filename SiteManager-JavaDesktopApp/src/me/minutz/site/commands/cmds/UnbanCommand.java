package me.minutz.site.commands.cmds;

import me.minutz.site.MainTip;
import me.minutz.site.User;
import me.minutz.site.commands.Command;
import me.minutz.site.commands.CommandManager;
import me.minutz.site.util.CryptoUtil;
import me.minutz.site.util.Role;

public class UnbanCommand extends Command{

	public UnbanCommand(CommandManager cmdm) {
		super(cmdm, "unban");
	}

	@Override
	public String execute(String[] args) {
		String nume = args[0];
		User unbanner = getUser();
		if(nume.equals("@a")){
			if(unbanner.getRole()==Role.Minutz){
				for(User toUnBan:MainTip.getUseri()){
					if(toUnBan!=null){
						if(toUnBan.isBanned()){
						CryptoUtil cr = new CryptoUtil();
						MainTip.SQL.update(toUnBan.getNume(), cr.encrypt(toUnBan.getUUID().replace("-", "")+"-------------------",toUnBan.getParola()), toUnBan.getUUID(), toUnBan.getLastActivity(), toUnBan.getLastIp(), Role.User.getI(), "nu");
				}
					}
				}
				MainTip.SQL.loadUseri();
				return "All users were unbanned";
			}else{
				return noPerm+" to unban all the users";
			}
		}else{
		User toUnBan = MainTip.getUserByNume(nume);
		if(toUnBan != null){
			if(unbanner != null){
		if(unbanner.getRole()==Role.Admin){
			if(toUnBan.getUUID().equals(unbanner.getUUID())){
				return "You can't unban yourself";
			}
			if(toUnBan.getRole()==Role.User){
				CryptoUtil cr = new CryptoUtil();
				MainTip.SQL.update(nume, cr.encrypt(toUnBan.getUUID().replace("-", "")+"---------------------",toUnBan.getParola()), toUnBan.getUUID(), toUnBan.getLastActivity(), toUnBan.getLastIp(), Role.User.getI(), "nu");
				MainTip.SQL.loadUseri();
				return nume+" was unbanned";
			}else{
				return "You can't unban an admin or higher";
			}
		}else{
			if(unbanner.getRole()==Role.Minutz){
				CryptoUtil cr = new CryptoUtil();
				MainTip.SQL.update(nume, cr.encrypt(toUnBan.getUUID().replace("-", "")+"---------------------",toUnBan.getParola()), toUnBan.getUUID(), toUnBan.getLastActivity(), toUnBan.getLastIp(), Role.User.getI(), "nu");
				MainTip.SQL.loadUseri();
				return nume+" was unbanned";
			}
		}
		}else{
			return "You are null. Contact the developer";
		}
		}else{
			return "No user "+nume+" was found";
		}
		}
		return "Unexpected error.";
	}

	@Override
	public String execute() {
		return "INCORRECT USE:\n"+help();
	}

	@Override
	public String help() {
		return "Unban someone with /unban [name]";
	}

}
