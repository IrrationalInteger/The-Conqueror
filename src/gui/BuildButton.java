package gui;

import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings({ "serial", "unused" })
public class BuildButton extends JButton {

	String c;
	Object b;
	 public BuildButton(String s,String c,Object b){
		 super(s);
		 this.c=c;
		 this.b=b;
	 }
}
