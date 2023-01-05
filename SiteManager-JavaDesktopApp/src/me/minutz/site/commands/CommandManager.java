package me.minutz.site.commands;

import java.util.ArrayList;
import java.util.List;

import me.minutz.site.Main;
import me.minutz.site.MainTip;
import me.minutz.site.commands.cmds.AddepCommand;
import me.minutz.site.commands.cmds.AddserialCommand;
import me.minutz.site.commands.cmds.BanCommand;
import me.minutz.site.commands.cmds.DeleteCommand;
import me.minutz.site.commands.cmds.HelpCommand;
import me.minutz.site.commands.cmds.LogoutCommand;
import me.minutz.site.commands.cmds.SetdescCommand;
import me.minutz.site.commands.cmds.SetroleCommand;
import me.minutz.site.commands.cmds.UnbanCommand;
import me.minutz.site.util.Role;

public class CommandManager{
	
	public Main main;
	
	public List<Command> cmds = new ArrayList<Command>();
	
	public CommandManager(Main m){
		main=m;
		loadcmds();
	}
	
	public void cmd(String s){
		String cmd = "--------";
		String[] ar = s.split(" ");
		int as= ar.length-1;
		String[] args = new String[as];
		if(ar.length>1){
		for(int i=0;i<ar.length;i++){
			if(i!=0){
				args[i-1]=ar[i];
			}else{
				cmd=ar[i].replace("/", "");
			}
		}
		}else{
		cmd=s.replace("/", "").replace(" ", "");
		}
		Command c = getCommand(cmd);
		MainTip.SQL.loadUseri();
		if(MainTip.getUserByNume(main.username).isBanned()){
		if(c != null){
			if(c.getCmd().equals("logout")){
				String e = c.execute();
				println(e);
				return;
			}else{
				Command comd = new LogoutCommand(this);
				comd.execute();
			}
		}else{
			Command comd = new LogoutCommand(this);
			comd.execute();
		}
		}else{
		if(MainTip.getUserByNume(main.username).getRole()==Role.User){
		if(c != null){
			if(c.getCmd().equals("logout")){
				String e = c.execute();
				println(e);
				return;
			}else{
				Command comd = new LogoutCommand(this);
				comd.execute();
			}
		}else{
			Command comd = new LogoutCommand(this);
			comd.execute();
		}
		}else{
			if(!s.equals(Main.sch)){
			Main.sch=s;
			Main.hist.add(s);
			main.kutil.g=Main.hist.size();
			}
			if(c != null){
				if(ar.length>1){
				String e = c.execute(args);
				println(e);
				}else{
				String e = c.execute();
				println(e);
				}
			}else{
				println("Unknown command. Type /help for command list");
			}
		}
		}
	}
	
	public void println(String s){
        main.chatBox.append("[Console]:  " + s
        + "\n");
	}
	
	public Command getCommand(String cmd){
		for(Command c:cmds){
			if(c.getCmd().equalsIgnoreCase(cmd)){
				return c;
			}
		}
		return null;
	}
	
	public void loadcmds(){
		cmds.add(new HelpCommand(this));
		cmds.add(new LogoutCommand(this));
		cmds.add(new BanCommand(this));
		cmds.add(new UnbanCommand(this));
		cmds.add(new SetroleCommand(this));
		cmds.add(new DeleteCommand(this));
		cmds.add(new AddserialCommand(this));
		cmds.add(new AddepCommand(this));
		cmds.add(new SetdescCommand(this));
	}

}
