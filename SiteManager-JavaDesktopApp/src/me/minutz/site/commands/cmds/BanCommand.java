package me.minutz.site.commands.cmds;

import me.minutz.site.MainTip;
import me.minutz.site.User;
import me.minutz.site.commands.Command;
import me.minutz.site.commands.CommandManager;
import me.minutz.site.util.CryptoUtil;
import me.minutz.site.util.Role;

public class BanCommand extends Command{

	public BanCommand(CommandManager cmdm) {
		super(cmdm, "ban");
	}

	@Override
	public String execute(String[] args) {
		String nume = args[0];
		User banner = getUser();
		if(nume.equals("@a")){
			if(banner.getRole()==Role.Minutz){
				for(User toBan:MainTip.getUseri()){
					if(toBan!=null){
						if(!toBan.isBanned()){
					if(toBan.getRole()!=Role.Minutz){
						CryptoUtil cr = new CryptoUtil();
						MainTip.SQL.update(toBan.getNume(), cr.encrypt(toBan.getUUID().replace("-", "")+"",toBan.getParola()), toBan.getUUID(), toBan.getLastActivity(), toBan.getLastIp(), Role.User.getI(), "da");
					}
				}
					}
				}
				MainTip.SQL.loadUseri();
				return "All users were banned";
			}else{
				return noPerm+" to ban all the users";
			}
		}else{
		User toBan = MainTip.getUserByNume(nume);
		if(toBan != null){
			if(banner != null){
		if(banner.getRole()==Role.Admin){
			if(toBan.getUUID().equals(banner.getUUID())){
				return "You can't ban yourself";
			}
			if(toBan.getRole()==Role.User){
				if(!toBan.isBanned()){
				CryptoUtil cr = new CryptoUtil();
				MainTip.SQL.update(nume, cr.encrypt(toBan.getUUID().replace("-", "")+"",toBan.getParola()), toBan.getUUID(), toBan.getLastActivity(), toBan.getLastIp(), Role.User.getI(), "da");
				MainTip.SQL.loadUseri();
				return nume+" was banned";
				}else{
					return nume+" is already banned";
				}
			}else{
				return "You can't ban an admin or higher";
			}
		}else{
			if(banner.getRole()==Role.Minutz){
				CryptoUtil cr = new CryptoUtil();
				MainTip.SQL.update(nume, cr.encrypt(toBan.getUUID().replace("-", "")+"",toBan.getParola()), toBan.getUUID(), toBan.getLastActivity(), toBan.getLastIp(), Role.User.getI(), "da");
				MainTip.SQL.loadUseri();
				return nume+" was banned";
			}
		}
		}else{
			return "You are null. Contact the developer";
		}
		}else{
			return "No user with name "+nume+" was found";
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
		return "Ban someone with /ban [name]";
	}

}
