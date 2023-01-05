package me.minutz.site;

public enum Role {
	
	User(317218799,"User"),
	Admin(923872837,"Admin"),
	Minutz(1624521654,"Minutz");
	
	public int i;
	public String nick;
	
	private Role(int inum,String nic){
		i=inum;
		nick=nic;
	}

	public int getI() {
		return i;
	}

	public String getNick() {
		return nick;
	}

	
	
}
