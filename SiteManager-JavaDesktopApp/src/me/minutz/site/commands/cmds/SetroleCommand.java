package me.minutz.site.commands.cmds;

import me.minutz.site.MainTip;
import me.minutz.site.User;
import me.minutz.site.commands.Command;
import me.minutz.site.commands.CommandManager;
import me.minutz.site.util.CryptoUtil;
import me.minutz.site.util.Role;

public class SetroleCommand extends Command{

	public SetroleCommand(CommandManager cmdm) {
		super(cmdm, "setrole");
	}

	public Role getRoleByName(String role){
		for(Role rl:Role.values()){
			if(rl.getNick().toLowerCase().equals(role.toLowerCase())){
				return rl;
			}
		}
		return null;
	}
	
	@Override
	public String execute(String[] args) {
		String usernume = args[0];
		String role = args[1];
		User setter = getUser();
		if(usernume != null){
			if(!usernume.equals("@a")){
			if(role != null){
				User toSet = MainTip.getUserByNume(usernume);
				if(setter.getRole()==Role.Admin){
//				if(toSet.getRole()==Role.User){
//					Role r = getRoleByName(role);
//					if(r != null){
//					CryptoUtil cr = new CryptoUtil();
//					MainTip.SQL.update(usernume, cr.encrypt(toSet.getUUID().replace("-", "")+"-----------------",toSet.getParola()), toSet.getUUID(), toSet.getLastActivity(), toSet.getLastIp(), r.getI(), "nu");
//					MainTip.SQL.loadUseri();
//					}else{
//						return "Role not found";
//					}
//					}else{
//					return "You can't set the role to an admin or higher";
//				}
					return noPerm+" to set a role";
				}else{
					if(setter.getRole()==Role.Minutz){
						Role r = getRoleByName(role);
						if(r != null){
						CryptoUtil cr = new CryptoUtil();
						MainTip.SQL.update(usernume, cr.encrypt(toSet.getUUID().replace("-", "")+"-------------",toSet.getParola()), toSet.getUUID(), toSet.getLastActivity(), toSet.getLastIp(), r.getI(), "nu");
						MainTip.SQL.loadUseri();
						return usernume+"'s role was set to "+r.getNick();
						}else{
							return "Role not found";
						}
					}else{
						Command c = cmdm.getCommand("logout");
						c.execute();
					}
				}
			}else{
				return execute();
			}
			}else{
				if(setter.getRole()==Role.Minutz){
					if(role !=null){
						Role r = getRoleByName(role);
						if(r != null){
				for(User toSet:MainTip.getUseri()){
					if(toSet!=null){
						if(toSet.getRole() != Role.Minutz){
							if(!toSet.isBanned()){
						CryptoUtil cr = new CryptoUtil();
						MainTip.SQL.update(toSet.getNume(), cr.encrypt(toSet.getUUID().replace("-", "")+"--------------",toSet.getParola()), toSet.getUUID(), toSet.getLastActivity(), toSet.getLastIp(), r.getI(), "nu");
						}
						}
					}
				}
				MainTip.SQL.loadUseri();
				return "All users role were set to "+role;
    					}else{
							return "Role not found";
						}
				}else{
					return execute();
				}
				}
			}
		}else{
			return execute();
		}
		
		return "Unexpected error.";
	}

	@Override
	public String execute() {
		return "INCORRECT USE:\n"+help();
	}

	@Override
	public String help() {
		return "Set user's role with /setrole [user] [role](Minutz only)";
	}
	

}
