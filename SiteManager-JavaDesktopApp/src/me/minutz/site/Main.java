package me.minutz.site;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import me.minutz.site.commands.CommandManager;
import me.minutz.site.udate.Update;
import me.minutz.site.udate.Updater;
import me.minutz.site.util.CryptoUtil;
import me.minutz.site.util.Dialog;
import me.minutz.site.util.KeyUtil;
import me.minutz.site.util.Role;

public class Main{

    String      appName     = "SiteManager";
    Main     mainGUI;
    JFrame      newFrame    = new JFrame(appName);
    JButton     sendMessage;
    public JTextField  messageBox;
    public JTextArea   chatBox;
    JTextField  usernameChooser;
    JTextField  pass;
    JFrame      preFrame;
	public Dialog d;
    public String username;
    String password;
    public KeyUtil kutil;
    public boolean log=false;
    static CommandManager command;
    public static Props props;
    static CryptoUtil cr = new CryptoUtil();
    public static String version = "1.4",sch="-------";
    public static List<String> hist = new ArrayList<String>();

    public static void main(String[] args) {
    	
    	System.out.println("Checking for updates...");
    	if(checkUpdate()){
        	System.out.println("Update found!");
    		callUpdate();
    	}else{
        	System.out.println("No updates found!");
    	}
    	System.out.println("Starting...");
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager
                            .getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Main mainGUI = new Main();
                command = new CommandManager(mainGUI);
                props = new Props(mainGUI);
                if(props.getDbPass() !=null){
                MainTip.load(props.getDbPass());
                }else{
                	System.out.println("DBPass is null");
    		    	try {
    					Thread.sleep(3000);
    					System.exit(1);
    				} catch (InterruptedException e) {
    		    	System.exit(1);
    					e.printStackTrace();
    				}
                }
                mainGUI.preDisplay();
                final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
                scheduler.scheduleAtFixedRate(new UpdateRunnable(mainGUI), 9, 9, TimeUnit.SECONDS);
            }
        });
    }

    public void preDisplay() {
        kutil=new KeyUtil(this);
        newFrame.setVisible(false);
        preFrame = new JFrame(appName);
        usernameChooser = new JTextField(20);
        pass = new JPasswordField(20);
        JLabel chooseUsernameLabel = new JLabel("Username:");
        JLabel passLbl = new JLabel("Password:");
        JButton enterServer = new JButton("Login");
        enterServer.addActionListener(new enterServerButtonListener());
        JPanel prePanel = new JPanel(new GridBagLayout());

        GridBagConstraints preRight = new GridBagConstraints();
        preRight.insets = new Insets(0, 0, 0, 10);
        preRight.anchor = GridBagConstraints.EAST;
        GridBagConstraints preLeft = new GridBagConstraints();
        preLeft.anchor = GridBagConstraints.WEST;
        preLeft.insets = new Insets(0, 10, 0, 10);
        preRight.fill = GridBagConstraints.HORIZONTAL;
        preRight.gridwidth = GridBagConstraints.REMAINDER;

        prePanel.add(chooseUsernameLabel, preLeft);
        prePanel.add(usernameChooser, preRight);
        prePanel.add(passLbl, preLeft);
        prePanel.add(pass, preRight);
        preFrame.add(BorderLayout.CENTER, prePanel);
        preFrame.add(BorderLayout.SOUTH, enterServer);
        preFrame.setSize(300, 300);
        preFrame.setVisible(true);
        pass.addKeyListener(kutil);
        usernameChooser.addKeyListener(kutil);
        
        checkLog();
    }

    public void display() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel southPanel = new JPanel();
        southPanel.setBackground(Color.CYAN);
        southPanel.setLayout(new GridBagLayout());

        messageBox = new JTextField(30);

        sendMessage = new JButton("Send Message");
        sendMessage.addActionListener(new sendMessageButtonListener());

        chatBox = new JTextArea();
        chatBox.setEditable(false);
        chatBox.setFont(new Font("Serif", Font.PLAIN, 15));
        chatBox.setLineWrap(true);

        mainPanel.add(new JScrollPane(chatBox), BorderLayout.CENTER);

        GridBagConstraints left = new GridBagConstraints();
        left.anchor = GridBagConstraints.LINE_START;
        left.fill = GridBagConstraints.HORIZONTAL;
        left.weightx = 512.0D;
        left.weighty = 1.0D;

        GridBagConstraints right = new GridBagConstraints();
        right.insets = new Insets(0, 10, 0, 0);
        right.anchor = GridBagConstraints.LINE_END;
        right.fill = GridBagConstraints.NONE;
        right.weightx = 1.0D;
        right.weighty = 1.0D;

        southPanel.add(messageBox, left);
        southPanel.add(sendMessage, right);

        mainPanel.add(BorderLayout.SOUTH, southPanel);

        newFrame.add(mainPanel);
        newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newFrame.setSize(470, 300);
        newFrame.setVisible(true);
        d=new Dialog(this);
        messageBox.addKeyListener(kutil);
        messageBox.requestFocusInWindow();
        
    }

    class sendMessageButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
        	send();
        }
    }

    class enterServerButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
        	callLogin();
        }
    }
    
    public void callChooser(){
    	d.call(true);
    }
    
    public void send(){
        if (messageBox.getText().length() < 1) {
        } else if (messageBox.getText().equals(".clear")) {
            chatBox.setText("");
            messageBox.setText("");
        } else {
        	if(!messageBox.getText().startsWith("/")){
            chatBox.append("<" + username + ">:  " + messageBox.getText()
                    + "\n");
            messageBox.setText("");
        	}else{
        		String comanda = messageBox.getText();
        		messageBox.setText("");
        		command.cmd(comanda);
        	}
        }
        messageBox.requestFocusInWindow();
    }
    
    public void checkLog(){
    	if(props.getPass()!=null){
    		if(props.getUName()!=null){
                preFrame.setVisible(false);
                username=props.getUName();
                if (!(username.length() < 1)) {
                	User user = MainTip.getUserByNume(username);
                if(user !=null){
                password=cr.decrypt(user.getUUID().replace("-", "")+"613278461278641278647218647821648128764278", props.getPass());
                    if (!(password.length() < 1)) {
                    		if(user.getParola().equals(password)){
                    			if(user.getRole() != Role.User){
                    				     display();
                    				      log=true;
                    				      System.out.println("Logged user "+username);
                    			}else{
                        			System.out.println("Unauthorized");
                        			System.exit(1);	
                    			}
                    		}else{
                    			System.out.println("Wrong password");
                    			System.exit(1);
                    		}
                    }else{
                    	System.out.println("ERRR PASS");
                    	System.exit(1);                    	
                    }
                }else{
                	System.out.println("User not found");
                	System.exit(1);
                }
                }
                

    		}
    	}
    }
    
    public static boolean checkUpdate(){
    	String ver = Updater.up();
    	if(!version.equals(ver)){
    		return true;
    	}
    	return false;
    }
    
    public static void callUpdate(){
    	Update update = new Update("http://188.173.52.120:8080/MinSrv/update/ServerManager.jar");
    	update.externalUpdate();
    }
    
    public void callLogin(){
    	MainTip.SQL.loadUseri();
        username = usernameChooser.getText();
        password = pass.getText();
        if (!(username.length() < 1)) {
            if (!(password.length() < 1)) {
            	User user = MainTip.getUserByNume(username);
            	if(user != null){
            		if(user.getParola().equals(password)){
            			if(user.getRole() != Role.User){
            preFrame.setVisible(false);
            display();
            log=true;
            props.set("uname", username);
            props.set("upass", cr.encrypt(user.getUUID().replace("-", "")+"613278461278641278647218647821648128764278", password));
		      System.out.println("Logged user "+username);
            			}else{
                			System.out.println("Unauthorized");
            		    	try {
            					Thread.sleep(3000);
            					System.exit(1);
            				} catch (InterruptedException e) {
            		    	System.exit(1);
            					e.printStackTrace();
            				}
            			}
            		}else{
            			System.out.println("Wrong password");
        		    	try {
        					Thread.sleep(3000);
        					System.exit(1);
        				} catch (InterruptedException e) {
        		    	System.exit(1);
        					e.printStackTrace();
        				}
            		}
            	}else{
        			System.out.println("User not found");
    		    	try {
    					Thread.sleep(3000);
    					System.exit(1);
    				} catch (InterruptedException e) {
    		    	System.exit(1);
    					e.printStackTrace();
    				}
            	}
            }else{
    			System.out.println("404 PASS");
		    	try {
					Thread.sleep(3000);
					System.exit(1);
				} catch (InterruptedException e) {
		    	System.exit(1);
					e.printStackTrace();
				}
            }
        } else {
			System.out.println("404 USER");
	    	try {
				Thread.sleep(3000);
				System.exit(1);
			} catch (InterruptedException e) {
	    	System.exit(1);
				e.printStackTrace();
			}
        }
    
    }
    
}