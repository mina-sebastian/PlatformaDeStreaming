package me.minutz.site.util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import me.minutz.site.Main;
import me.minutz.site.MainTip;
import me.minutz.site.User;

public class Dialog extends JDialog implements KeyListener{
	
	public JTextField jtf;
	public int i=1;
	public List<User> d;
	public boolean set=false,called=false;
	public String ales="";
	public Main main;
	
	public Dialog(Main m){
		setSize(60, 60);
		setTitle("Select user");
		setResizable(false);
		requestFocusInWindow();
		jtf=new JTextField(20);
		jtf.addKeyListener(this);
		add(jtf);
		main=m;
		jtf.requestFocusInWindow();
	}
	
	public void call(boolean c){
		called=c;
		setVisible(c);
	}

	@Override
	public void keyPressed(KeyEvent k) {
		if(isVisible()){
		if(k.getKeyCode()==KeyEvent.VK_SPACE){
			if(!set){
			if(!jtf.getText().isEmpty()){
				List<User> l = MainTip.getUseri();
				d = new ArrayList<User>();
				for(User u:l){
					String n= u.getNume().toLowerCase();
					String v = jtf.getText().toLowerCase().replace(" ", "");
					if(n.contains(v)){
						d.add(u);
					}
				}
				set=true;
			}
			}
			if(d != null){
			if(d.size()>0){
				ales = d.get(i-1).getNume();
				jtf.setText(ales);
				i++;
				if(i>d.size()){
					i=1;
				}
			}
			}
	}
		
		if(k.getKeyCode()==KeyEvent.VK_F3){
			call(false);
			ales="";
			i=1;
			d=null;
			set=false;
			jtf.setText("");
		}
		
		if(k.getKeyCode()==KeyEvent.VK_ENTER){
			call(false);
			main.messageBox.setText(main.messageBox.getText()+ales);
			ales="";
			i=1;
			d=null;
			set=false;
			jtf.setText("");
		}
		
		}
	}

	@Override
	public void keyReleased(KeyEvent k) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}

}
