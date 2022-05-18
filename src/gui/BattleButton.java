package gui;

import javax.swing.JButton;
import javax.swing.JTextArea;

import units.Unit;

@SuppressWarnings("serial")
public class BattleButton extends JButton {
Unit attacker;
Unit defender;
JTextArea area;
String state;
public BattleButton(String r,Unit u1,Unit u2, JTextArea a,String state) {
	super(r);
	attacker=u1;
	defender=u2;
	area=a;
	this.state = state;
}

}
