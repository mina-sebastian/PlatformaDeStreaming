package me.minutz.site.util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import me.minutz.site.Main;

public class KeyUtil implements KeyListener{
	
	public Main main;
	public int g = 0;
	
	public KeyUtil(Main m){
		main=m;
	}

	@Override
	public void keyPressed(KeyEvent k) {
		if(k.getKeyCode()==KeyEvent.VK_F3){
			if(!main.d.called){
			main.callChooser();
			}else{
				main.d.call(false);
			}
		}
		
		if(k.getKeyCode()==KeyEvent.VK_ENTER){
			if(main.log){
				main.send();
			}else{
				main.callLogin();
			}
		}
		
		if(k.getKeyCode()==KeyEvent.VK_UP){
			main.messageBox.setText(Main.hist.get(g-1));
			if(g>1){
			g--;
			}else{
				g=Main.hist.size();
			}
		}
		if(k.getKeyCode()==KeyEvent.VK_BACK_SPACE){
			if(g!=Main.hist.size()){
			g=Main.hist.size();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent k) {
		
	}

	@Override
	public void keyTyped(KeyEvent k) {
	}

}
