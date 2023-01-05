package me.minutz.site.commands.cmds;

import me.minutz.site.MainTip;
import me.minutz.site.commands.Command;
import me.minutz.site.commands.CommandManager;
import me.minutz.site.posts.Postare;

public class AddserialCommand extends Command{

	public AddserialCommand(CommandManager cmdm) {
		super(cmdm, "addserial");
	}

	public int getNextId(){
		int j = 1;
		for(Postare p:MainTip.getPostari()){
			if(p.getId()>j){
				j=p.getId()+1;
			}
		}
		return j;
	}
	
	@Override
	public String execute(String[] args) {
		String serial = args[0];
		String imagelink = args[1];
		int rating = -1;
		rating = Integer.parseInt(args[2]);
		String titlu = args[3];
		String desc = args[4];
		int an = -1;
		an = Integer.parseInt(args[5]);;
		String desc2 = args[6];
		String imgLink2 = args[7];
		if(serial != null&&imagelink != null&&titlu != null&&desc != null&&desc2 != null&&imgLink2 != null){
			if(rating != -1){
				if(an != -1){
					MainTip.SQL.loadSeriale();
					if(MainTip.getSerialByNume(serial) == null){
					MainTip.SQL.addPost(getNextId(), serial, imagelink, rating, titlu, desc, an, desc2);
					MainTip.SQL.addSerial(serial, imgLink2);
					}
				return "Post added!";
				}else{
					return "Year error";
				}
			}else{
				return "Rating error";
			}
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
		return "Add a series with:/addserial [serialNume] [imageLink] [rating] [titlu] [descriere] [an] [descriere2] [posterLink]";
	}

}
