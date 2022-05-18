package gui;
import javax.imageio.ImageIO;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import units.*;

import javax.swing.*;

import engine.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.event.ActionEvent;

import exceptions.*;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import buildings.*;
public class GameController implements ActionListener {
	private GameView View;
	private Game Model;
	private String playerName;
	private JTextField myTextField;

	public GameController() throws Exception {
		View = new GameView();
		View.setLayout(new BoxLayout(View.getContentPane(), BoxLayout.PAGE_AXIS));
		myTextField = new JTextField(30);
		myTextField.setVisible(true);
		JPanel dataPanel = new JPanel();
		dataPanel.setOpaque(false);
		JPanel name = new JPanel();
		name.setLayout(new BorderLayout());
		BufferedImage myPicture = ImageIO.read(new File("png3.png"));
		JLabel picLabel = new JLabel(new ImageIcon(myPicture));
		name.setOpaque(false);
		JPanel gamename = new JPanel();
		name.add(gamename,BorderLayout.CENTER);
		gamename.add(picLabel);
		gamename.setOpaque(false);
		View.add(name);
		name.setForeground(new Color(189, 255, 253));
		name.setFont(new Font("TimesRoman", Font.BOLD, 48));
		JLabel myLabel = new JLabel("Enter your name!");
		myTextField.setColumns(17);
		myLabel.setOpaque(false);
		myLabel.setForeground(new Color(189, 255, 253));
		myTextField.setFont(new Font("Serif", Font.BOLD, 48));
		myLabel.setFont(new Font("Serif", Font.BOLD, 48));
		myTextField.setActionCommand("PlayerEntersName");
		JPanel text = new JPanel();
		text.add(myLabel, BorderLayout.EAST);
		text.add(myTextField, BorderLayout.CENTER);
		myTextField.setPreferredSize(new Dimension(25, 70));
		myTextField.setFont(new Font("Serif", Font.BOLD, 48));
		View.add(text);
		text.setOpaque(false);
		this.View.validate();
		this.View.repaint();
		myTextField.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("PlayerEntersName")) {
			if(((JTextField) e.getSource()).getText().equals("")){
				myTextField = new JTextField(30);
				myTextField.setVisible(true);
				myTextField.setColumns(17);
				myTextField.setFont(new Font("Serif", Font.BOLD, 48));
				myTextField.setActionCommand("PlayerEntersName");
				myTextField.setFont(new Font("Serif", Font.BOLD, 48));
				myTextField.setPreferredSize(new Dimension(25, 70));
				JPanel text = new JPanel();
				text.add(myTextField, BorderLayout.CENTER);
				View.add(text);
				JOptionPane
				.showMessageDialog(null,
						"You can't leave your name empty!");
			}
			else{
				playerName = ((JTextField) e.getSource()).getText();
				View.getContentPane().removeAll();
				View.setVisible(true);
				View.revalidate();
				View.repaint();
				View.add(new JPanel(), BorderLayout.WEST);
				JPanel cityChoice = new JPanel();
				JLabel myLabel = new JLabel("Choose Your City!");
				try {
					View.setContentPane(new JLabel(new ImageIcon(ImageIO
							.read(new File("jpeg2.jpg")))));
				} catch (IOException Z) {
					Z.printStackTrace();
				}
				View.setLayout(new BorderLayout());
				View.pack();
				View.add(cityChoice, BorderLayout.CENTER);
				cityChoice.add(myLabel);
				cityChoice.setLayout(new GridLayout());
				myLabel.setOpaque(false);
				cityChoice.setOpaque(false);
				JButton R = new JButton();
				R.setOpaque(false);
				R.setContentAreaFilled(false);
				cityChoice.add(R);
				JButton C = new JButton();
				C.setOpaque(false);
				C.setContentAreaFilled(false);
				cityChoice.add(C);
				JButton S = new JButton();
				cityChoice.add(S);
				S.setOpaque(false);
				S.setContentAreaFilled(false);
				myLabel.setForeground(Color.white);
				R.setForeground(Color.white);
				S.setForeground(Color.white);
				C.setForeground(Color.white);
				R.setHorizontalTextPosition(JButton.CENTER);
				R.setVerticalTextPosition(JButton.NORTH);
				S.setHorizontalTextPosition(JButton.CENTER);
				S.setVerticalTextPosition(JButton.NORTH);
				C.setHorizontalTextPosition(JButton.CENTER);
				C.setVerticalTextPosition(JButton.NORTH);
				R.setFont(new Font("Serif", Font.BOLD, 48));
				S.setFont(new Font("Serif", Font.BOLD, 48));
				C.setFont(new Font("Serif", Font.BOLD, 48));
				try {
					Image img = ImageIO.read(getClass().getResource(
							"/images/jpeg3.jpg"));
					R.setIcon(new ImageIcon(img));
				} catch (Exception ex) {
				}
				try {
					Image img = ImageIO.read(getClass().getResource(
							"/images/jpeg5.jpg"));
					C.setIcon(new ImageIcon(img));
				} catch (Exception ex) {
				}
				try {
					Image img = ImageIO.read(getClass().getResource(
							"/images/jpeg4.jpg"));
					S.setIcon(new ImageIcon(img));
				} catch (Exception ex) {

				}
				R.setText("Rome");
				C.setText("Cairo");
				S.setText("Sparta");
				myLabel.setFont(new Font("Serif", Font.BOLD, 48));
				View.validate();
				View.repaint();
				R.addActionListener(this);
				C.addActionListener(this);
				S.addActionListener(this);
			}
		}
		
		if ((e.getActionCommand().equals("Rome"))
				|| (e.getActionCommand().equals("Cairo"))
				|| (e.getActionCommand().equals("Sparta"))) {
			try {
				Model = new Game(playerName,
						((JButton) e.getSource()).getText());
				View.getContentPane().removeAll();
				this.displayWorldView(this.View, this.Model);
			} catch (IOException Z) {

			}
		}
		if (e.getActionCommand().equals("End Turn")) {
			boolean flag = true;
			this.Model.endTurn();
			for (int i = 0; i < this.Model.getPlayer().getControlledArmies()
					.size(); i++) {
				if (this.Model.getPlayer().getControlledArmies().get(i)
						.getUnits().size() == 0)
					this.Model
							.getPlayer()
							.getControlledArmies()
							.remove(this.Model.getPlayer()
									.getControlledArmies().get(i));
			}
			View.getContentPane().removeAll();
			this.displayWorldView(View, Model);
			if (!(this.Model.getPlayer().getControlledArmies().size() == 0)) {
				for (int i = 0; i < this.Model.getPlayer()
						.getControlledArmies().size(); i++) {
					if (this.Model.getPlayer().getControlledArmies().get(i)
							.getCurrentStatus() == Status.BESIEGING) {
						for (int j = 0; j < this.Model.getAvailableCities()
								.size(); j++) {
							if (this.Model
									.getAvailableCities()
									.get(j)
									.getName()
									.equals(this.Model.getPlayer()
											.getControlledArmies().get(i)
											.getCurrentLocation())) {
								if (!this.Model.getAvailableCities().get(j)
										.isUnderSiege()) {
									flag = true;
									Army a = this.Model.getPlayer()
											.getControlledArmies().get(i);
									City c = this.Model.getAvailableCities()
											.get(j);
									String options1[] = { "Attack", "Leave" };
									boolean y = true;
									while (y) {
										int x1 = -1;
										while (x1 == -1) {
											x1 = JOptionPane
													.showOptionDialog(
															null,
															"Your army "
																	+ a.getName()
																	+ " has reached "
																	+ a.getCurrentLocation(),
															"Choose An Action",
															JOptionPane.DEFAULT_OPTION,
															JOptionPane.QUESTION_MESSAGE,
															null, options1,
															options1[0]);
										}
										if (x1 == 0) {
											String[] options2 = { "Manual",
													"AutoResolve" };
											int n = JOptionPane
													.showOptionDialog(
															View,
															"Choose your action!",
															"What do you want to do",
															JOptionPane.DEFAULT_OPTION,
															JOptionPane.QUESTION_MESSAGE,
															null, options2,
															options2[0]);
											if (n != -1)
												y = false;
											if (n != 0)
												this.displayWorldView(View,
														Model);
											if (n == 0) {
												JTextArea t = new JTextArea();
												t.setAlignmentX(JTextArea.LEFT_ALIGNMENT);
												t.setText("Battle Log: ");
												t.setFont(new Font("serif",
														Font.BOLD, 25));
												t.setForeground(Color.black);
												t.setEditable(false);
												displayBattleView(View, Model,
														a, null, t);
												flag = false;
											} else if (n == 1) {

												try {
													Model.autoResolve(a, c
															.getDefendingArmy());
													if (a.getUnits().size() == 0) {
														JOptionPane
																.showMessageDialog(
																		null,
																		"The battle ended and your army lost!");
													} else {
														JOptionPane
																.showMessageDialog(
																		null,
																		"The battle ended and your army won!");
													}
													
													this.displayWorldView(View,
															Model);
												} catch (FriendlyFireException l) {

												}
											}
										} else if (x1 == 1) {
											ArrayList<String> targetOptions = new ArrayList<String>();
											for (int k = 0; k < this.Model
													.getAvailableCities()
													.size(); k++) {
												if (!(this.Model
														.getAvailableCities()
														.get(k).getName()
														.equals(a
																.getCurrentLocation())))
													targetOptions
															.add(this.Model
																	.getAvailableCities()
																	.get(k)
																	.getName());
											}
											String[] targetString = new String[targetOptions
													.size()];

											for (int k = 0; k < targetOptions
													.size(); k++) {
												targetString[k] = targetOptions
														.get(k);
											}
											if (targetString.length == 0) {
												JOptionPane
														.showMessageDialog(
																null,
																"You already control all cities!");
											}
											
											String s = (String) JOptionPane
													.showInputDialog(
															null,
															"Choose the target city!",
															"Target A City",
															JOptionPane.QUESTION_MESSAGE,
															null, targetString,
															targetString[0]);
											

											try {
												if (!(s==null)){
												y=false;
												this.Model.targetCity(a, s);
												a.setCurrentStatus(Status.MARCHING);
												}
												else
													y=true;
											} catch (AlreadyTargetedException z) {
												JOptionPane
														.showMessageDialog(
																null,
																"This city has already been targeted!");
											}
											
										}
									}
								}
							break;
							}
						}
					}
				}
				

				ArrayList<String> noncontrolled = new ArrayList<String>();
				for (int i = 0; i < Model.getAvailableCities().size(); i++) {
					if (!Model.getPlayer().getControlledCities()
							.contains(Model.getAvailableCities().get(i)))
						noncontrolled.add(Model.getAvailableCities().get(i)
								.getName());
				}
				for (int i = 0; i < Model.getPlayer().getControlledArmies()
						.size(); i++) {
					if (noncontrolled.contains(Model.getPlayer()
							.getControlledArmies().get(i).getCurrentLocation())
							&& Model.getPlayer().getControlledArmies().get(i)
									.getCurrentStatus() == Status.IDLE) {
						flag = true;
						Army a = Model.getPlayer().getControlledArmies().get(i);
						City c = null;
						for (int j = 0; j < Model.getAvailableCities().size(); j++) {
							if (Model.getAvailableCities().get(j).getName()
									.equals(a.getCurrentLocation()))
								c = Model.getAvailableCities().get(j);
						}
						String options1[] = { "Attack", "Siege", "Leave" };
						boolean y = true;
						while (y) {
							int x1 = -1;
							while (x1 == -1) {
								x1 = JOptionPane.showOptionDialog(
										null,
										"Your army " + a.getName()
												+ " has reached "
												+ a.getCurrentLocation(),
										"Choose An Action",
										JOptionPane.DEFAULT_OPTION,
										JOptionPane.QUESTION_MESSAGE, null,
										options1, options1[0]);
							}
							if (x1 == 0) {
								String[] options2 = { "Manual", "AutoResolve" };
								int n = JOptionPane.showOptionDialog(View,
										"Choose your action!",
										"What do you want to do",
										JOptionPane.DEFAULT_OPTION,
										JOptionPane.QUESTION_MESSAGE, null,
										options2, options2[0]);
								if (n != -1)
									y = false;

								if (n == 0) {
									JTextArea t = new JTextArea();
									t.setAlignmentX(JTextArea.LEFT_ALIGNMENT);
									t.setText("Battle Log: ");
									t.setFont(new Font("serif", Font.BOLD, 25));
									t.setForeground(Color.black);
									t.setEditable(false);
									displayBattleView(View, Model, a, null, t
											);
									flag = false;
								} else if (n == 1) {

									try {
										Model.autoResolve(a,
												c.getDefendingArmy());
										if (a.getUnits().size() == 0) {
											JOptionPane
													.showMessageDialog(null,
															"The battle ended and your army lost!");
										} else {
											JOptionPane
													.showMessageDialog(null,
															"The battle ended and your army won!");
										}
					
										this.displayWorldView(View, Model);
									} catch (FriendlyFireException l) {
										JOptionPane.showMessageDialog(null,
												"ezay");
									}
								}
							} else if (x1 == 1) {
								try {
									this.Model.getPlayer().laySiege(a, c);
								} catch (FriendlyCityException l) {
									JOptionPane
											.showMessageDialog(null,
													"You cannot siege a controlled city!");
								} catch (TargetNotReachedException l) {
									JOptionPane
											.showMessageDialog(null,
													"Your army hasn't reached this city yet!");
								}
								y = false;
							} else {
								ArrayList<String> targetOptions = new ArrayList<String>();
								for (int j = 0; j < this.Model
										.getAvailableCities().size(); j++) {
									if (!(this.Model.getAvailableCities()
											.get(j).getName().equals(a
											.getCurrentLocation())))
										targetOptions.add(this.Model
												.getAvailableCities().get(j)
												.getName());
								}
								String[] targetString = new String[targetOptions
										.size()];

								for (int j = 0; j < targetOptions.size(); j++) {
									targetString[j] = targetOptions.get(j);
								}
								if (targetString.length == 0) {
									JOptionPane.showMessageDialog(null,
											"You already control all cities!");

								}
								String s = (String) JOptionPane
										.showInputDialog(null,
												"Choose the target city!",
												"Target A City",
												JOptionPane.QUESTION_MESSAGE,
												null, targetString,
												targetString[0]);
								if(s==null)
									y = true;
								else{
								try {
									this.Model.targetCity(a, s);

								} catch (AlreadyTargetedException z) {
									JOptionPane
											.showMessageDialog(null,
													"This city has already been targeted!");
								}
								
							}}
						}
					}
				}

			}
			if (Model.isGameOver()) {
				flag = false;
				if (Model.getPlayer().getControlledCities().size() == Model
						.getAvailableCities().size()) {
					View.getContentPane().removeAll();
					View.setVisible(true);
					View.revalidate();
					View.repaint();
					try {
						View.setContentPane(new JLabel(new ImageIcon(ImageIO
								.read(new File("png2.png")))));
					} catch (IOException Z) {
						Z.printStackTrace();
					}
					View.pack();
				
					
				} else {
					View.getContentPane().removeAll();
					View.setVisible(true);
					View.revalidate();
					View.repaint();
					try {
						View.setContentPane(new JLabel(new ImageIcon(ImageIO
								.read(new File("png1.png")))));
					} catch (IOException Z) {
						Z.printStackTrace();
					}
					View.pack();
				
				}
				Timer timer = new Timer(4000, this);
				timer.setRepeats(false);
				timer.start();
				timer.setActionCommand("End game");
			}
			if (flag)
				displayWorldView(View, Model);
		}
		if (e.getActionCommand().equals("View City")) {
			BuildButton b = (BuildButton) e.getSource();
			this.displayCityView(View, Model, b.c);
		}

		if (e.getActionCommand().equals("World Map"))
			displayWorldView(View, Model);
		if (e.getActionCommand().equals("Build")) {
			BuildButton b = (BuildButton) (e.getSource());
			String kk = "";
			if (b.c.equals("Cairo (Controlled)"))
				kk = "Cairo";
			if (b.c.equals("Sparta (Controlled)"))
				kk = "Sparta";
			if (b.c.equals("Rome (Controlled)"))
				kk = "Rome";
			City city = null;
			for (int i = 0; i < Model.getPlayer().getControlledCities().size(); i++) {
				if (Model.getPlayer().getControlledCities().get(i).getName()
						.equals(kk))
					city = Model.getPlayer().getControlledCities().get(i);
			}
			ArrayList<String> original = new ArrayList<>();
			original.add("Farm Cost: 1000");
			original.add("Market Cost: 1500");

			original.add("ArcheryRange Cost: 1500");
			original.add("Stable Cost: 2500");
			original.add("Barracks Cost: 2000");
			for (int i = 0; i < city.getEconomicalBuildings().size(); i++) {
				if (city.getEconomicalBuildings().get(i) instanceof Farm) {
					original.remove("Farm Cost: 1000");
				} else if (city.getEconomicalBuildings().get(i) instanceof Market) {
					original.remove("Market Cost: 1500");
				}

			}
			for (int i = 0; i < city.getMilitaryBuildings().size(); i++) {
				if (city.getMilitaryBuildings().get(i) instanceof ArcheryRange) {
					original.remove("ArcheryRange Cost: 1500");
				} else if (city.getMilitaryBuildings().get(i) instanceof Stable) {
					original.remove("Stable Cost: 2500");
				} else if (city.getMilitaryBuildings().get(i) instanceof Barracks) {
					original.remove("Barracks Cost: 2000");
				}
			}
			String[] options = new String[original.size()];
			for (int i = 0; i < original.size(); i++) {
				options[i] = original.get(i);
			}
			if (original.size() == 0) {
				JOptionPane.showMessageDialog(null,
						"You Cannot build any more buildings!");
			} else {
				int x = JOptionPane
						.showOptionDialog(null,
								"Which building do you wish to build?",
								"Choose a building",
								JOptionPane.DEFAULT_OPTION,
								JOptionPane.QUESTION_MESSAGE, null, options,
								options[0]);
				if (x == -1)
					return;
				String y = "";
				if (options[x] == "Farm Cost: 1000")
					y = "Farm";
				else if (options[x] == "Market Cost: 1500")
					y = "Market";
				else if (options[x] == "ArcheryRange Cost: 1500")
					y = "ArcheryRange";
				else if (options[x] == "Barracks Cost: 2000")
					y = "Barracks";
				else if (options[x] == "Stable Cost: 2500")
					y = "Stable";
				try {
					Model.getPlayer().build(y, kk);
					this.displayCityView(View, Model, b.c);
				} catch (NotEnoughGoldException l) {
					JOptionPane.showMessageDialog(null,
							"You don't have enough gold");
				}

			}

		}

		if (e.getActionCommand() == "building") {
			BuildButton b = (BuildButton) e.getSource();
			Building building = (Building) (b.b);
			if (building instanceof Farm || building instanceof Market) {
				if (building.getLevel() == 3) {
					JOptionPane.showMessageDialog(null,
							"This building has reached the max level!");
					return;
				}
				String[] options = { "YES", "NO" };
				int x = JOptionPane
						.showOptionDialog(
								null,
								((building instanceof Farm) ? "This farm currently makes "
										+ ((Farm) building).harvest()
										+ " food per turn"
										: "This market currently makes "
												+ ((Market) building).harvest()
												+ " gold per turn")
										+ "\nThe upgrade cost is: "
										+ building.getUpgradeCost()
										+ " gold"
										+ "\nAre you sure you want to upgrade?",
								"Upgrade", JOptionPane.DEFAULT_OPTION,
								JOptionPane.QUESTION_MESSAGE, null, options,
								options[0]);
				if (x == 0) {
					try {
						Model.getPlayer().upgradeBuilding(building);
					} catch (NotEnoughGoldException c1) {
						JOptionPane.showMessageDialog(null,
								"You don't have enough gold!");
					} catch (BuildingInCoolDownException c) {
						JOptionPane.showMessageDialog(null,
								"This building is on cooldown now!");
					} catch (MaxLevelException c) {
						JOptionPane.showMessageDialog(null,
								"This building is at the max level!");
					}
				}

			}

			else {
				MilitaryBuilding mb = (MilitaryBuilding) building;
				String r = (mb instanceof Barracks) ? "Infantry"
						: (mb instanceof ArcheryRange) ? "Archer" : "Cavalry";
				if (building.getLevel() == 3) {
					String[] options = { "YES", "NO" };

					int x = JOptionPane
							.showOptionDialog(
									null,
									"The recruitment cost is: "
											+ mb.getRecruitmentCost()
											+ " gold"
											+ "\nAre you sure you want to recruit a unit?",
									"Recruit A Unit",
									JOptionPane.DEFAULT_OPTION,
									JOptionPane.QUESTION_MESSAGE, null,
									options, options[0]);

					if (x == 0) {

						try {
							Model.getPlayer().recruitUnit(r, b.c);

						} catch (NotEnoughGoldException c1) {
							JOptionPane.showMessageDialog(null,
									"You don't have enough gold!");
						} catch (BuildingInCoolDownException c) {
							JOptionPane.showMessageDialog(null,
									"This building is on cooldown now!");
						} catch (MaxRecruitedException c) {
							JOptionPane
									.showMessageDialog(null,
											"You've reached the maximum amount of recruitments allowed this turn!");
						}
					}

				} else {
					String[] options = { "Upgrade", "Recruit" };
					int x = JOptionPane.showOptionDialog(null,
							"The upgrade cost is: " + mb.getUpgradeCost()
									+ " gold" + "\nThe recruitment cost is: "
									+ mb.getRecruitmentCost() + " gold",
							"Upgrade", JOptionPane.DEFAULT_OPTION,
							JOptionPane.QUESTION_MESSAGE, null, options,
							options[0]);
					if (x == 0) {

						try {
							Model.getPlayer().upgradeBuilding(mb);

						} catch (NotEnoughGoldException c1) {
							JOptionPane.showMessageDialog(null,
									"You don't have enough gold!");
						} catch (BuildingInCoolDownException c) {
							JOptionPane.showMessageDialog(null,
									"This building is on cooldown now!");
						} catch (MaxLevelException c) {
							JOptionPane.showMessageDialog(null,
									"This building is at the max level!");
						}
					} else if (x == 1) {
						try {
							Model.getPlayer().recruitUnit(r, b.c);
						} catch (NotEnoughGoldException c1) {
							JOptionPane.showMessageDialog(null,
									"You don't have enough gold!");
						} catch (BuildingInCoolDownException c) {
							JOptionPane.showMessageDialog(null,
									"This building is on cooldown now!");
						} catch (MaxRecruitedException c) {
							JOptionPane
									.showMessageDialog(null,
											"You've reached the maximum amount of recruitments allowed this turn!");
						}
					}
				}
			}
			this.displayCityView(View, Model, b.c + " (Controlled)");
		}
		if (e.getActionCommand() == "Unit") {
			BuildButton b = (BuildButton) e.getSource();
			Unit u = (Unit) b.b;
			String[] options = { "Yes", "No" };
			int x = JOptionPane
					.showOptionDialog(
							null,
							b.getText()
									+ "\nCurrent soldier count: "
									+ u.getCurrentSoldierCount()
									+ "\nMax soldier count: "
									+ u.getMaxSoldierCount()
									+ "\nDo you wish to relocate this unit to one of your armies?",
							"Unit Infromation", JOptionPane.DEFAULT_OPTION,
							JOptionPane.QUESTION_MESSAGE, null, options,
							options[0]);
			if (x == 0) {
				ArrayList<String> armies = new ArrayList<String>();
				for (int i = 0; i < this.Model.getPlayer()
						.getControlledArmies().size(); i++) {
					if (this.Model.getPlayer().getControlledArmies().get(i)
							.getCurrentLocation().equals(b.c)
							&& !this.Model.getPlayer().getControlledArmies()
									.get(i).equals(u.getParentArmy()))
						armies.add(Model.getPlayer().getControlledArmies()
								.get(i).getName());

				}

				City c = null;

				for (int i = 0; i < this.Model.getPlayer()
						.getControlledCities().size(); i++) {
					if (Model.getPlayer().getControlledCities().get(i)
							.getName().equals(b.c))
						c = Model.getPlayer().getControlledCities().get(i);
				}
				// if(!u.getParentArmy().equals(c.getDefendingArmy())) {
				// armies.add("Defending Army");
				// }
				options = new String[armies.size()];
				for (int i = 0; i < armies.size(); i++) {
					options[i] = armies.get(i);
				}
				if (options.length == 0) {
					JOptionPane.showMessageDialog(null,
							"You don't have any other army");
					return;
				}
				String s = (String) JOptionPane.showInputDialog(null,
						"Choose the destination army for this unit ",
						"Unit Relocation", JOptionPane.QUESTION_MESSAGE, null,
						options, options[0]);
				// if(s.equals("Defending Army")) {

				// }
				for (int i = 0; i < this.Model.getPlayer()
						.getControlledArmies().size(); i++) {
					if (this.Model.getPlayer().getControlledArmies().get(i)
							.getCurrentLocation().equals(b.c)) {
						if (this.Model.getPlayer().getControlledArmies().get(i)
								.getName().equals(s)) {
							try {
								Army oldparent = u.getParentArmy();
								this.Model.getPlayer().getControlledArmies()
										.get(i).relocateUnit(u);
								for (int j = 0; j < this.Model.getPlayer()
										.getControlledArmies().size(); j++) {
									if (this.Model.getPlayer()
											.getControlledArmies().get(j)
											.getUnits().size() == 0)
										this.Model
												.getPlayer()
												.getControlledArmies()
												.remove(this.Model.getPlayer()
														.getControlledArmies()
														.get(j));
								}
								if (!oldparent.equals(c.getDefendingArmy()))
									displayArmy(oldparent, b.c);
								else

									this.displayCityView(View, Model, b.c
											+ " (Controlled)");
							} catch (MaxCapacityException l) {
								JOptionPane.showMessageDialog(null,
										"This army is already full!");
							}
							break;
						}

					}

				}

			}

		}
		if (e.getActionCommand().equals("Initiate Army")) {
			BuildButton b = (BuildButton) e.getSource();
			String s = (String) JOptionPane.showInputDialog(null,
					"Enter the name of your new army", "Army Naming",
					JOptionPane.QUESTION_MESSAGE, null, null, null);
			if (s == null) {
				return;
			}
			if (s.equals("")) {
				JOptionPane.showMessageDialog(null,
						"You can't leave the army name empty!");
				return;
			}
			for (int i = 0; i < this.Model.getPlayer().getControlledArmies()
					.size(); i++) {
				if (this.Model.getPlayer().getControlledArmies().get(i)
						.getName().equals(s)) {
					JOptionPane.showMessageDialog(null,
							"You can't give two armies the same name!");
					return;
				}
			}
			ArrayList<String> optionsD = new ArrayList<String>();
			for(int i =0;i<((City)b.b).getDefendingArmy().getUnits().size();i++){
				optionsD.add(((City)b.b).getDefendingArmy().getUnits().get(i).getClass().getSimpleName()+" Level "+((City)b.b).getDefendingArmy().getUnits().get(i).getLevel()+" ,Soldiers: "+((City)b.b).getDefendingArmy().getUnits().get(i).getCurrentSoldierCount());
			}
			String options[] = new String[optionsD.size()];
			for(int i = 0;i<optionsD.size();i++){
				options[i]=optionsD.get(i);
			}
			if(options.length==0){
				JOptionPane.showMessageDialog(null,
						"Your defending army is empty!");
				return;
			}
			String choice = (String) JOptionPane
					.showInputDialog(null,
							"Choose a unit to add to your new army",
							"Initiate an army",
							JOptionPane.QUESTION_MESSAGE,
							null, options,
							options[0]);
			int j;
			if(choice==null)
				return;
			for(j=0;j<options.length;j++){
				if(choice.equals(options[j]))
					break;
			}
			this.Model.getPlayer().initiateArmy((City)b.b, ((City)b.b).getDefendingArmy().getUnits().get(j));
			this.displayCityView(View, Model, b.c + " (Controlled)");
			Model.getPlayer().getControlledArmies()
					.get(Model.getPlayer().getControlledArmies().size() - 1)
					.setName(s);
			this.displayCityView(View, Model, b.c + " (Controlled)");
		}
		if (e.getActionCommand().equals("Army")) {
			BuildButton b = (BuildButton) e.getSource();
			Army a = (Army) (b.b);
			if (a.getTarget() != "") {
				String options1[] = { "Yes", "No" };
				int x1 = JOptionPane.showOptionDialog(
						null,
						"Name: " + a.getName() + "\nCurrent Status: "
								+ a.getCurrentStatus()
								+ "\nCurrent Unit Count: "
								+ a.getUnits().size()
								+ "\n The targeted city: " + a.getTarget()
								+ "\n Turns left till the army reaches: "
								+ a.getDistancetoTarget()
								+ "\nDo you want to display army's units",
						"View Army", JOptionPane.DEFAULT_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, options1,
						options1[0]);
				if (x1 == -1)
					return;
				if (x1 == 0) {
					displayArmy(a, b.c);
				}
			} else if (a.getCurrentStatus() == Status.BESIEGING) {
				String options[] = { "Attack", "Leave", "Army Data" };
				int x = JOptionPane
						.showOptionDialog(null,
								"Name: " + a.getName() + "\nCurrent Status: "
										+ a.getCurrentStatus()
										+ "\nCurrent Unit Count: "
										+ a.getUnits().size()
										+ "\nMax Units this army can hold: "
										+ a.getMaxToHold()
										+ "\nChoose an action to perform",
								"Choose An Action", JOptionPane.DEFAULT_OPTION,
								JOptionPane.QUESTION_MESSAGE, null, options,
								options[0]);
				if (x == -1)
					return;
				if (x == 0) {
					String[] options1 = { "Manual", "AutoResolve" };
					int x1 = JOptionPane.showOptionDialog(null,
							"Choose how you want to fight", "Choose An Action",
							JOptionPane.DEFAULT_OPTION,
							JOptionPane.QUESTION_MESSAGE, null, options1,
							options1[0]);
					if (x1 == -1)
						return;
					if (x1 == 0) {
						JTextArea t = new JTextArea();
						t.setAlignmentX(JTextArea.LEFT_ALIGNMENT);
						t.setText("Battle Log: ");
						t.setForeground(Color.blue);
						t.setFont(new Font("serif", Font.BOLD, 15));
						t.setForeground(Color.black);
						t.setEditable(false);
						displayBattleView(View, Model, a, null, t);
					}
					if (x1 == 1) {
						int i;
						for (i = 0; i < this.Model.getAvailableCities().size(); i++) {
							if (this.Model.getAvailableCities().get(i)
									.getName().equals(a.getCurrentLocation()))
								break;
						}
						try {
							this.Model.autoResolve(a, this.Model
									.getAvailableCities().get(i)
									.getDefendingArmy());
							if (this.Model.getAvailableCities().get(i)
									.getDefendingArmy().getUnits().size() == 0)
								JOptionPane.showMessageDialog(null,
										"The battle ended and you won!");
							else if (a.getUnits().size() == 0)
								JOptionPane.showMessageDialog(null,
										"The battle ended and you lost!");
						} catch (FriendlyFireException z) {
							JOptionPane.showMessageDialog(null,
									"You are targeting a friendly army!");
						}
					}
				}
				if (x == 1) {
					ArrayList<String> targetOptions = new ArrayList<String>();
					for (int i = 0; i < this.Model.getAvailableCities().size(); i++) {
						if (!(this.Model.getAvailableCities().get(i).getName()
								.equals(a.getCurrentLocation())))
							targetOptions.add(this.Model.getAvailableCities()
									.get(i).getName());
					}
					String[] targetString = new String[targetOptions.size()];
					for (int i = 0; i < targetOptions.size(); i++) {
						targetString[i] = targetOptions.get(i);
					}
					String s = (String) JOptionPane.showInputDialog(null,
							"Choose the target city!", "Target A City",
							JOptionPane.QUESTION_MESSAGE, null, targetString,
							targetString[0]);
					if (s == null)
						return;
					if (s.equals(""))
						return;
					try {
						this.Model.targetCity(a, s);
						a.setCurrentStatus(Status.MARCHING);
					} catch (AlreadyTargetedException z) {
						JOptionPane.showMessageDialog(null,
								"This city has already been targeted!");
					}
				}
				if (x == 2)
					displayArmy(a, "world");
			} else {
				String options[] = { "Target A City", "Army Data" };
				int x = JOptionPane
						.showOptionDialog(
								null,
								"Name: "
										+ a.getName()
										+ "\nCurrent Status: "
										+ a.getCurrentStatus()
										+ "\nCurrent Unit Count: "
										+ a.getUnits().size()
										+ "\nMax Units this army can hold: "
										+ a.getMaxToHold()
										+ "\nDo you want to display army's units",
								"View Army", JOptionPane.DEFAULT_OPTION,
								JOptionPane.QUESTION_MESSAGE, null, options,
								options[0]);
				if (x == -1)
					return;
				if (x == 0) {
					ArrayList<String> targetOptions = new ArrayList<String>();
					for (int i = 0; i < this.Model.getAvailableCities().size(); i++) {
						if (!(this.Model.getAvailableCities().get(i).getName()
								.equals(a.getCurrentLocation())))
							targetOptions.add(this.Model.getAvailableCities()
									.get(i).getName());
					}
					String[] targetString = new String[targetOptions.size()];
					if (targetOptions.size() == 0) {
						JOptionPane.showMessageDialog(null,
								"You already control all cities!");
						return;
					}
					for (int i = 0; i < targetOptions.size(); i++) {
						targetString[i] = targetOptions.get(i);
					}
					if (targetString.length == 0) {
						JOptionPane.showMessageDialog(null,
								"You already control all cities!");
						return;
					}
					String s = (String) JOptionPane.showInputDialog(null,
							"Choose the target city!", "Target A City",
							JOptionPane.QUESTION_MESSAGE, null, targetString,
							targetString[0]);
					if (s == null)
						return;
					if (s.equals(""))
						return;
					try {
						this.Model.targetCity(a, s);
					} catch (AlreadyTargetedException z) {
						JOptionPane.showMessageDialog(null,
								"This city has already been targeted!");
					}
				}
				if (x == 1) {
					displayArmy(a, b.c);
				}
			}
		}
		if (e.getActionCommand().equals("Back To City")) {
			BuildButton b = (BuildButton) e.getSource();
			displayCityView(View, Model, b.c + " (Controlled)");
		}
		if (e.getActionCommand().equals("Back To World")) {
			displayWorldView(View, Model);
		}

		if (e.getActionCommand().equals("Back To City")) {
			BuildButton b = (BuildButton) e.getSource();
			displayCityView(View, Model, b.c + " (Controlled)");
		}
		if (e.getActionCommand().equals("Back To World")) {
			displayWorldView(View, Model);
		}
		if (e.getActionCommand().equals("Attack")) {
			if ((((BattleButton) e.getSource()).defender == null)
					&& ((((BattleButton) e.getSource()).attacker != null))) {
				Unit attacker = ((BattleButton) e.getSource()).attacker;
				Army attackerParent = ((BattleButton) e.getSource()).attacker
						.getParentArmy();
				for (int i = 0; i < attackerParent.getUnits().size(); i++) {
					if (attackerParent.getUnits().get(i) == attacker) {
						String[] options = {"Attack","View Data"};
						int n = JOptionPane.showOptionDialog(View,
								"Choose your action!",
								"What do you want to do?",
								JOptionPane.DEFAULT_OPTION,
								JOptionPane.QUESTION_MESSAGE, null,
								options, options[0]);
						if (n==0){
						((BattleButton) e.getSource()).setBorder(BorderFactory.createMatteBorder(10,10, 10, 10, Color.GREEN));
						((BattleButton) e.getSource()).area.setText(((BattleButton) e.getSource()).area
										.getText()
										+ "\nYou picked the unit "
										+ attacker.getClass().getSimpleName()
										+ " Level "
										+ attacker.getLevel()
										+ " to attack ");
						displayBattleView(View, Model,
								attacker.getParentArmy(), attacker,
								((BattleButton) e.getSource()).area
								);
						}
						else if (n==1){
							JOptionPane.showMessageDialog(null,"This unit is of type "+((BattleButton)e.getSource()).attacker.getClass().getSimpleName()+" Level "+((BattleButton)e.getSource()).attacker.getLevel()+"\nThis unit is currently battling in "+((BattleButton)e.getSource()).attacker.getParentArmy().getCurrentLocation()+"\nThis unit currently has "+((BattleButton)e.getSource()).attacker.getCurrentSoldierCount()+" soldiers\nThe max soldier count is "+((BattleButton)e.getSource()).attacker.getMaxSoldierCount());	
						}
					}
				}
			}
		}
		if (e.getActionCommand().equals("Enemy Data")){
			Unit defender = ((BattleButton)e.getSource()).defender;
			JOptionPane.showMessageDialog(null,"This unit is of type "+((BattleButton)e.getSource()).defender.getClass().getSimpleName()+" Level "+((BattleButton)e.getSource()).defender.getLevel()+"\nThis unit is currently defending "+defender.getParentArmy().getCurrentLocation()+"\nThis unit currently has "+defender.getCurrentSoldierCount()+" Soldiers\nThe max soldier count is "+defender.getMaxSoldierCount());		
			}
		if (e.getActionCommand().equals("FriendlyAttack")){
			JOptionPane.showMessageDialog(null,"You can't attack a friendly unit!");	
			displayBattleView(View, Model, ((BattleButton)e.getSource()).attacker.getParentArmy() ,((BattleButton)e.getSource()).attacker, ((BattleButton)e.getSource()).area);
		}
		if (e.getActionCommand().equals("NormalAttack")){
			String[] options = {"Attack","View Data"};
			int n = JOptionPane.showOptionDialog(View,
					"Choose your action!",
					"What do you want to do?",
					JOptionPane.DEFAULT_OPTION,
					JOptionPane.QUESTION_MESSAGE, null,
					options, options[0]);
			if (n==0){
				int before2 = ((BattleButton)e.getSource()).defender.getCurrentSoldierCount();
				try{((BattleButton)e.getSource()).attacker.attack(((BattleButton)e.getSource()).defender);}
				catch(FriendlyFireException z){	
					
				}
				int numatt = 0 + (int)(Math.random() * (((BattleButton)e.getSource()).attacker.getParentArmy().getUnits().size()));
				int numdef = 0 + (int)(Math.random() * (((BattleButton)e.getSource()).defender.getParentArmy().getUnits().size()));
				((BattleButton)e.getSource()).area.setText(((BattleButton)e.getSource()).area.getText()+((BattleButton)e.getSource()).defender.getClass().getSimpleName()+" Level "+((BattleButton)e.getSource()).defender.getLevel()+"\nThe enemy unit lost " + (before2-((BattleButton)e.getSource()).defender.getCurrentSoldierCount()) +" soldiers\n");

				if(((BattleButton)e.getSource()).defender.getParentArmy().getUnits().size()==0){
					JOptionPane.showMessageDialog(null,"Your army won the fight!");	
					this.Model.occupy(((BattleButton)e.getSource()).attacker.getParentArmy(),((BattleButton)e.getSource()).attacker.getParentArmy().getCurrentLocation());
					displayWorldView(View,Model);
					return;
				}
				Unit defender2 = ((BattleButton)e.getSource()).defender.getParentArmy().getUnits().get(numdef);
				Unit attacker2 = ((BattleButton)e.getSource()).attacker.getParentArmy().getUnits().get(numatt);
				int before = attacker2.getCurrentSoldierCount();
				try{((BattleButton)e.getSource()).defender.getParentArmy().getUnits().get(numdef).attack(((BattleButton)e.getSource()).attacker.getParentArmy().getUnits().get(numatt));}
				catch(FriendlyFireException z){
					
				}				
				if(((BattleButton)e.getSource()).attacker.getParentArmy().getUnits().size()==0){
					JOptionPane.showMessageDialog(null,"Your army lost the fight!");
					this.Model.getPlayer().getControlledArmies().remove(((BattleButton)e.getSource()).attacker.getParentArmy());
					displayWorldView(View, Model);
				}
				else{
					((BattleButton)e.getSource()).area.setText(((BattleButton)e.getSource()).area.getText()+defender2.getClass().getSimpleName()+" Level "+defender2.getLevel()+" attacked "+attacker2.getClass().getSimpleName()+" Level "+attacker2.getLevel()+"\nYour unit lost "+(before - attacker2.getCurrentSoldierCount()) +" soldiers\n");
					displayBattleView(View, Model, ((BattleButton)e.getSource()).attacker.getParentArmy(), null,((BattleButton)e.getSource()).area) ;
			}
				}
		}
		if (e.getActionCommand().equals("End game")){
			View.getContentPane().removeAll();
			View.setVisible(true);
			View.revalidate();
			View.repaint();
			View.setBackground(Color.gray);
			try {
				View.setContentPane(new JLabel(new ImageIcon(ImageIO
						.read(new File("png4.png")))));
			} catch (IOException Z) {
				Z.printStackTrace();
			}
			View.revalidate();
			View.repaint();

		}


	}

	public void displayData(GameView View, Game Model) {
		View.getContentPane().removeAll();
		View.setVisible(true);
		View.revalidate();
		View.repaint();

		JPanel data = new JPanel();
		View.setLayout(new BorderLayout());
		data.setLayout(new BorderLayout());
		View.add(data, BorderLayout.NORTH);
		JLabel dataLabel = new JLabel("Name: " + Model.getPlayer().getName()
				+ "    Turn: " + Model.getCurrentTurnCount() + "    Gold: "
				+ Model.getPlayer().getTreasury() + "    Food: "
				+ Model.getPlayer().getFood()
				+ "                                       ");
		data.add(dataLabel, BorderLayout.WEST);
		dataLabel.setForeground(Color.white);
		data.setOpaque(false);
		dataLabel.setFont(new Font("Serif", Font.BOLD, 48));
		dataLabel.setOpaque(true);
		JButton b = new JButton("End Turn");
		b.setForeground(Color.white);
		b.setBackground(new Color(214, 122, 9));
		b.addActionListener(this);
		b.setFont(new Font("Serif", Font.BOLD, 48));
		b.setSize(200, 50);
		data.setBackground(new Color(102, 61, 0));
		dataLabel.setBackground(new Color(102, 61, 0));
		data.add(b, BorderLayout.EAST);
		JPanel empty = new JPanel();
		empty.setBackground(new Color(102, 61, 0));
		data.add(empty, BorderLayout.CENTER);
		View.setExtendedState(JFrame.MAXIMIZED_BOTH);
		View.revalidate();
		View.repaint();
	}

	public void displayWorldView(GameView View, Game Model) {
		this.displayData(View, Model);
		JPanel c = new JPanel();
		c.setLayout(new GridLayout(0, 2));
		View.add(c, BorderLayout.CENTER);
		for (int i = 0; i < Model.getPlayer().getControlledCities().size(); i++) {
			JPanel d = new JPanel();
			d.setLayout(new BorderLayout());
			JPanel title = new JPanel();
			JLabel e = new JLabel(Model.getPlayer().getControlledCities()
					.get(i).getName()
					+ " (Controlled)");
			c.add(d);
			d.add(title, BorderLayout.NORTH);
			title.add(e);
			JPanel armies = new JPanel();
			JPanel s = new JPanel();
			armies.setLayout(new GridLayout(0, 3));
			BuildButton b = new BuildButton("View City", Model.getPlayer()
					.getControlledCities().get(i).getName()
					+ " (Controlled)", null);
			b.addActionListener(this);
			s.add(b);
			d.add(s, BorderLayout.SOUTH);
			d.add(armies, BorderLayout.CENTER);
			for (int j = 0; j < Model.getPlayer().getControlledArmies().size(); j++) {
				if (Model.getPlayer().getControlledArmies().get(j)
						.getCurrentStatus() == Status.IDLE) {
					if (Model
							.getPlayer()
							.getControlledArmies()
							.get(j)
							.getCurrentLocation()
							.equals(Model.getPlayer().getControlledCities()
									.get(i).getName())) {
						BuildButton idleArmy = new BuildButton(Model
								.getPlayer().getControlledArmies().get(j)
								.getName(), "world", Model.getPlayer()
								.getControlledArmies().get(j));
						idleArmy.setActionCommand("Army");
						idleArmy.setBackground(Color.BLUE);
						idleArmy.setFont(new Font("Serif", Font.BOLD, 48));
						idleArmy.setForeground(Color.white);
						armies.add(idleArmy);
						idleArmy.addActionListener(this);
					}
				}
			}
		}
		for (int i = 0; i < Model.getAvailableCities().size(); i++) {
			if (!(Model.getPlayer().getControlledCities().contains(Model
					.getAvailableCities().get(i)))) {
				JPanel d = new JPanel();
				d.setLayout(new BorderLayout());
				JPanel title = new JPanel();
				JLabel e = new JLabel(Model.getAvailableCities().get(i)
						.getName());
				c.add(d);
				d.add(title, BorderLayout.NORTH);
				title.add(e);
				JPanel armies = new JPanel();
				JPanel s = new JPanel();
				armies.setLayout(new GridLayout(0, 3));
				d.add(s, BorderLayout.SOUTH);
				d.add(armies, BorderLayout.CENTER);
				for (int j = 0; j < Model.getPlayer().getControlledArmies()
						.size(); j++) {
					if ((Model.getPlayer().getControlledArmies().get(j)
							.getCurrentStatus() == Status.BESIEGING)
							&& (Model.getAvailableCities().get(i).getName()
									.equals(Model.getPlayer()
											.getControlledArmies().get(j)
											.getCurrentLocation()))) {
						BuildButton siegingArmy = new BuildButton(Model
								.getPlayer().getControlledArmies().get(j)
								.getName(), "world", Model.getPlayer()
								.getControlledArmies().get(j));
						siegingArmy.setBackground(Color.black);
						siegingArmy.setFont(new Font("Serif", Font.BOLD, 48));
						siegingArmy.setForeground(Color.white);
						siegingArmy.setActionCommand("Army");
						d.add(siegingArmy);
						siegingArmy.addActionListener(this);
					}

				}
			}
		}
		JPanel marchingPanel = new JPanel();
		marchingPanel.setLayout(new BorderLayout());
		JLabel marchingLabel = new JLabel("Marching Armies: ");
		Panel marchingTitle = new Panel();
		Panel marchingCore = new Panel();
		marchingCore.setLayout(new GridLayout(0, 3));
		marchingTitle.add(marchingLabel);
		marchingPanel.add(marchingTitle, BorderLayout.NORTH);
		marchingPanel.add(marchingCore, BorderLayout.CENTER);
		c.add(marchingPanel);

		for (int i = 0; i < Model.getPlayer().getControlledArmies().size(); i++) {
			if (Model.getPlayer().getControlledArmies().get(i)
					.getCurrentStatus() == Status.MARCHING) {
				BuildButton marchingArmy = new BuildButton(Model.getPlayer()
						.getControlledArmies().get(i).getName(), "world", Model
						.getPlayer().getControlledArmies().get(i));
				marchingArmy.setActionCommand("Army");
				marchingArmy.setBackground(Color.yellow);
				marchingArmy.setFont(new Font("Serif", Font.BOLD, 48));
				marchingArmy.setForeground(Color.white);
				marchingCore.add(marchingArmy);
				marchingArmy.addActionListener(this);
			}
		}
		View.revalidate();
		View.repaint();

	}

	public void displayBattleView(GameView View, Game Model, Army a, Unit u,JTextArea text) {
			View.getContentPane().removeAll();
			View.revalidate();
			View.repaint();
			View.setVisible(true);
			JPanel myarmy = new JPanel();
			JPanel title = new JPanel();
			JPanel cent = new JPanel();
			cent.setLayout(new GridLayout(0, 3));
			View.add(cent, BorderLayout.CENTER);
			title.setBackground(Color.BLACK);
			View.add(title, BorderLayout.NORTH);
			myarmy.setLayout(new GridLayout(0, 4));
			String s = a.getCurrentLocation();
			for (int i = 0; i < a.getUnits().size(); i++) {
				if (a.getUnits().get(i) instanceof Archer) {
					BattleButton b = new BattleButton("Archer"
							+ a.getUnits().get(i).getLevel(), a.getUnits().get(
							i), null, text, "default");
					b.addActionListener(this);
					b.setForeground(Color.white);
					b.setFont(new Font("serif", Font.BOLD, 24));
					b.setBackground(new Color(24, 140, 57));
					if(u==null)
						b.setActionCommand("Attack");
					else
						b.setActionCommand("FriendlyAttack");	
					myarmy.add(b);
				}
				else if (a.getUnits().get(i) instanceof Cavalry) {
					BattleButton b = new BattleButton("Cavalry"
						, a.getUnits().get(
							i), null, text, "default");
					b.addActionListener(this);
					b.setForeground(Color.white);
					b.setFont(new Font("serif", Font.BOLD, 24));
					b.setBackground(new Color(192, 192, 192));
					if(u==null)
						b.setActionCommand("Attack");
					else
						b.setActionCommand("FriendlyAttack");	
					myarmy.add(b);
				} else if (a.getUnits().get(i) instanceof Infantry) {
					BattleButton b = new BattleButton("Infantry", a.getUnits().get(
							i), null, text, "default");
					b.addActionListener(this);
					b.setForeground(Color.white);
					b.setFont(new Font("serif", Font.BOLD, 24));
					b.setBackground(new Color(255, 201, 102));
					if(u==null)
						b.setActionCommand("Attack");
					else
						b.setActionCommand("FriendlyAttack");	
					myarmy.add(b);
				}
			}
			JPanel left = new JPanel();
			JPanel right = new JPanel();
			left.setLayout(new FlowLayout());
			myarmy.setBorder(BorderFactory.createLineBorder(Color.black));
			title.setLayout(new GridLayout(0, 3));

			Army ac = null;
			for (int i = 0; i < Model.getAvailableCities().size(); i++) {
				if (Model.getAvailableCities().get(i).getName().equals(s)) {
					ac = Model.getAvailableCities().get(i).getDefendingArmy();
				}
			}
			left.add(new Label("My army"));
			right.add(new Label("Defending army"));
			title.add(left);
			title.add(right);
			JPanel o = new JPanel();
			o.add((new JLabel("Events in battle")));
			title.add(o);
			JPanel Darmy = new JPanel();
			Darmy.setLayout(new GridLayout(0, 4));

			for (int i = 0; i < ac.getUnits().size(); i++) {
				if (ac.getUnits().get(i) instanceof Archer) {
					BattleButton b = new BattleButton("Archer",u, ac.getUnits()
							.get(i), text,"default");
					b.addActionListener(this);
					b.setForeground(Color.white);
					b.setFont(new Font("serif", Font.BOLD, 24));
					b.setBackground(new Color(24, 140, 57));
					if(u==null)
						b.setActionCommand("Enemy Data");
					else
						b.setActionCommand("NormalAttack");	
					Darmy.add(b);
				}

				else if (ac.getUnits().get(i) instanceof Cavalry) {
					BattleButton b = new BattleButton("Cavalry",u, ac.getUnits()
							.get(i), text,"default");
					b.addActionListener(this);
					b.setForeground(Color.white);
					b.setFont(new Font("serif", Font.BOLD, 24));
					b.setBackground(new Color(192, 192, 192));
					if(u==null)
						b.setActionCommand("Enemy Data");
					else
						b.setActionCommand("NormalAttack");	
					Darmy.add(b);
				} else if (ac.getUnits().get(i) instanceof Infantry) {
					BattleButton b = new BattleButton("Infantry", u,ac.getUnits()
							.get(i), text,"deafult");
					b.addActionListener(this);
					b.setForeground(Color.white);
					b.setFont(new Font("serif", Font.BOLD, 24));
					b.setBackground(new Color(255, 201, 102));
					if(u==null)
						b.setActionCommand("Enemy Data");
					else
						b.setActionCommand("NormalAttack");	
					Darmy.add(b);
				}
			}

			Darmy.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10,
					new Color(245, 83, 69)));
			myarmy.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10,
					new Color(125, 255, 102)));
			JPanel event = new JPanel();
			event.setLayout(new BorderLayout());
			JScrollPane scroll = new JScrollPane (text, 
					   JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);			
			event.add(scroll, BorderLayout.WEST);
			event.setBackground(Color.white);
			cent.add(myarmy, BorderLayout.WEST);
			cent.add(Darmy, BorderLayout.CENTER);
			cent.add(scroll, BorderLayout.EAST);
			View.revalidate();
			View.repaint();
		
	}

	public void displayCityView(GameView View, Game Model, String currCity) {
		View.getContentPane().removeAll();
		View.setVisible(true);
		View.revalidate();
		View.repaint();
		
		String kk = "";
		if (currCity.equals("Cairo (Controlled)"))
			kk = "Cairo";
		if (currCity.equals("Sparta (Controlled)"))
			kk = "Sparta";
		if (currCity.equals("Rome (Controlled)"))
			kk = "Rome";
		City city = null;
		for (int i = 0; i < Model.getPlayer().getControlledCities().size(); i++) {
			if (Model.getPlayer().getControlledCities().get(i).getName()
					.equals(kk))
				city = Model.getPlayer().getControlledCities().get(i);
		}
		JPanel p2 = new JPanel();
		p2.setLayout(new BorderLayout());
		if(kk.equals("Rome")){
			try {
		        View.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("3.png")))));
		      } catch (IOException e) {
		          e.printStackTrace();
		      }
		    View.pack();
			
			}
		if(kk.equals("Cairo")){
			try {
		        View.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("2.png")))));
		      } catch (IOException e) {
		          e.printStackTrace();
		      }
		    View.pack();
			
		}
		if(kk.equals("Sparta")){
			try {
		        View.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("1.png")))));
		      } catch (IOException e) {
		          e.printStackTrace();
		      }
		    View.pack();
			}
		this.displayData(View, Model);
		JPanel p6 = new JPanel();
		BuildButton b2 = new BuildButton("Build", currCity, p6);
		b2.setForeground(Color.WHITE);
		b2.setBackground(new Color(214, 122, 9));
		b2.setFont(new Font("Serif", Font.BOLD, 48));
		b2.addActionListener(this);
		JButton b3 = new JButton("World Map");
		b3.setForeground(Color.WHITE);
		b3.setBackground(new Color(214, 122, 9));
		b3.setFont(new Font("Serif", Font.BOLD, 48));
		b3.addActionListener(this);
		JPanel p8 = new JPanel();
		JLabel l2 = new JLabel("You are now in " + kk);
		l2.setFont(new Font("serif", Font.BOLD, 48));
		l2.setForeground(Color.white);
		p8.setBackground(new Color(54, 52, 50));
		p2.add(b2, BorderLayout.WEST);
		p2.add(p8, BorderLayout.CENTER);
		p8.setPreferredSize(new Dimension(p2.getWidth(), p2.getHeight()));
		BuildButton initiate = new BuildButton("Initiate Army", kk, city);
		initiate.setForeground(Color.WHITE);
		initiate.setBackground(new Color(214, 122, 9));
		initiate.addActionListener(this);
		p8.add(initiate);
		p8.add(l2);
		initiate.setFont(new Font("Serif", Font.BOLD, 48));
		p2.add(b3, BorderLayout.EAST);
		View.add(p2, BorderLayout.SOUTH);
		JPanel p3 = new JPanel();
		p3.setLayout(new GridLayout(0, 1));
		View.add(p3, BorderLayout.CENTER);
		JPanel p4 = new JPanel();
		p4.setLayout(new GridLayout(0, 7));
		p3.add(p4);
		JPanel p5 = new JPanel();
		p5.setLayout(new GridLayout(0, 2));
		p3.add(p5);

		JPanel p7 = new JPanel();
		p5.add(p6);
		p6.setLayout(new GridLayout(0, 2));
		p5.add(p7);
		p7.setLayout(new GridLayout(0, 2));
		p6.setOpaque(false);
		p7.setOpaque(false);
		p5.setOpaque(false);
		p3.setOpaque(false);
		p2.setOpaque(false);
		p4.setOpaque(false);
		for (int i = 0; i < city.getEconomicalBuildings().size(); i++) {
			if (city.getEconomicalBuildings().get(i) instanceof Farm) {
				BuildButton b = null;
				if (city.getEconomicalBuildings().get(i).getLevel() == 3)
					b = new BuildButton("Farm Level: "
							+ city.getEconomicalBuildings().get(i).getLevel(),
							kk, city.getEconomicalBuildings().get(i));
				else
					b = new BuildButton("Farm Level: "
							+ city.getEconomicalBuildings().get(i).getLevel()
							+ ", UpgradeCost: "
							+ city.getEconomicalBuildings().get(i)
									.getUpgradeCost(), kk, city
							.getEconomicalBuildings().get(i));
				if (city.getEconomicalBuildings().get(i).isCoolDown())
					b.setBackground(new Color(245, 66, 66));
				else
					b.setBackground(new Color(125, 255, 102));
				b.setActionCommand("building");
				b.setForeground(Color.white);
				b.setFont(new Font("serif", Font.BOLD, 24));
				b.addActionListener(this);
				p6.add(b);
			} else if (city.getEconomicalBuildings().get(i) instanceof Market) {
				BuildButton b = null;
				if (city.getEconomicalBuildings().get(i).getLevel() == 3)
					b = new BuildButton("Market Level: "
							+ city.getEconomicalBuildings().get(i).getLevel(),
							kk, city.getEconomicalBuildings().get(i));
				else
					b = new BuildButton("Market Level: "
							+ city.getEconomicalBuildings().get(i).getLevel()
							+ ", UpgradeCost: "
							+ city.getEconomicalBuildings().get(i)
									.getUpgradeCost(), kk, city
							.getEconomicalBuildings().get(i));
				if (city.getEconomicalBuildings().get(i).isCoolDown())
					b.setBackground(new Color(245, 66, 66));
				else
					b.setBackground(new Color(125, 255, 102));
				b.setActionCommand("building");
				b.setForeground(Color.white);
				b.setFont(new Font("serif", Font.BOLD, 24));
				b.setActionCommand("building");
				b.addActionListener(this);
				p6.add(b);
			}

		}
		for (int i = 0; i < city.getMilitaryBuildings().size(); i++) {
			if (city.getMilitaryBuildings().get(i) instanceof ArcheryRange) {
				BuildButton b = null;
				if (city.getMilitaryBuildings().get(i).getLevel() == 3)
					b = new BuildButton("ArcheryRange Level: "
							+ city.getMilitaryBuildings().get(i).getLevel(),
							kk, city.getMilitaryBuildings().get(i));
				else
					b = new BuildButton("ArcheryRange Level: "
							+ city.getMilitaryBuildings().get(i).getLevel()
							+ ", UpgradeCost: "
							+ city.getMilitaryBuildings().get(i)
									.getUpgradeCost(), kk, city
							.getMilitaryBuildings().get(i));
				if (city.getMilitaryBuildings().get(i).isCoolDown())
					b.setBackground(new Color(245, 66, 66));
				else
					b.setBackground(new Color(125, 255, 102));
				b.setActionCommand("building");
				b.setForeground(Color.white);
				b.setFont(new Font("serif", Font.BOLD, 24));
				b.setActionCommand("building");
				b.addActionListener(this);
				p6.add(b);
			}

			else if (city.getMilitaryBuildings().get(i) instanceof Stable) {
				BuildButton b = null;
				if (city.getMilitaryBuildings().get(i).getLevel() == 3)
					b = new BuildButton("Stable Level: "
							+ city.getMilitaryBuildings().get(i).getLevel(),
							kk, city.getMilitaryBuildings().get(i));
				else
					b = new BuildButton("Stable Level: "
							+ city.getMilitaryBuildings().get(i).getLevel()
							+ ", UpgradeCost: "
							+ city.getMilitaryBuildings().get(i)
									.getUpgradeCost(), kk, city
							.getMilitaryBuildings().get(i));
				b.setActionCommand("building");
				if (city.getMilitaryBuildings().get(i).isCoolDown())
					b.setBackground(new Color(245, 66, 66));
				else
					b.setBackground(new Color(125, 255, 102));
				b.setActionCommand("building");
				b.setForeground(Color.white);
				b.setFont(new Font("serif", Font.BOLD, 24));
				b.addActionListener(this);
				p6.add(b);
			} else if (city.getMilitaryBuildings().get(i) instanceof Barracks) {
				BuildButton b = null;
				if (city.getMilitaryBuildings().get(i).getLevel() == 3)
					b = new BuildButton("Barracks Level: "
							+ city.getMilitaryBuildings().get(i).getLevel(),
							kk, city.getMilitaryBuildings().get(i));
				else
					b = new BuildButton("Barracks Level: "
							+ city.getMilitaryBuildings().get(i).getLevel()
							+ ", UpgradeCost "
							+ city.getMilitaryBuildings().get(i)
									.getUpgradeCost(), kk, city
							.getMilitaryBuildings().get(i));
				b.setActionCommand("building");
				if (city.getMilitaryBuildings().get(i).isCoolDown())
					b.setBackground(new Color(245, 66, 66));
				else
					b.setBackground(new Color(125, 255, 102));
				b.setActionCommand("building");
				b.setForeground(Color.white);
				b.setFont(new Font("serif", Font.BOLD, 24));
				b.addActionListener(this);
				p6.add(b);
			}
		}
		for (int i = 0; i < city.getDefendingArmy().getUnits().size(); i++) {
			if (city.getDefendingArmy().getUnits().get(i) instanceof Archer) {
				BuildButton b = new BuildButton("Archer Level: "
						+ city.getDefendingArmy().getUnits().get(i).getLevel(),
						kk, city.getDefendingArmy().getUnits().get(i));
				b.addActionListener(this);
				b.setForeground(Color.white);
				b.setFont(new Font("serif", Font.BOLD, 24));
				b.setBackground(new Color(24, 140, 57));
				b.setActionCommand("Unit");
				p4.add(b);
			}

			else if (city.getDefendingArmy().getUnits().get(i) instanceof Cavalry) {
				BuildButton b = new BuildButton("Cavalry Level: "
						+ city.getDefendingArmy().getUnits().get(i).getLevel(),
						kk, city.getDefendingArmy().getUnits().get(i));
				b.addActionListener(this);
				b.setActionCommand("Unit");
				b.setForeground(Color.white);
				b.setFont(new Font("serif", Font.BOLD, 24));
				b.setBackground(new Color(192, 192, 192));
				p4.add(b);
			} else if (city.getDefendingArmy().getUnits().get(i) instanceof Infantry) {
				BuildButton b = new BuildButton("Infantry Level: "
						+ city.getDefendingArmy().getUnits().get(i).getLevel(),
						kk, city.getDefendingArmy().getUnits().get(i));
				b.addActionListener(this);
				b.setForeground(Color.white);
				b.setFont(new Font("serif", Font.BOLD, 24));
				b.setBackground(new Color(255, 201, 102));
				b.setActionCommand("Unit");
				p4.add(b);
			}
		}
		for (int i = 0; i < Model.getPlayer().getControlledArmies().size(); i++) {
			if (Model.getPlayer().getControlledArmies().get(i)
					.getCurrentLocation().equals(kk)) {
				BuildButton b = new BuildButton("Army: "
						+ Model.getPlayer().getControlledArmies().get(i)
								.getName(), kk, Model.getPlayer()
						.getControlledArmies().get(i));
				b.setActionCommand("Army");
				b.setBackground(new Color(66, 135, 245));
				b.setForeground(Color.white);
				b.setFont(new Font("serif", Font.BOLD, 24));
				b.addActionListener(this);
				p7.add(b);
			}
		}
		View.validate();
		View.repaint();
	}

	public void displayArmy(Army a, String r) {
		View.getContentPane().removeAll();
		View.setVisible(true);
		View.revalidate();
		View.repaint();
		this.displayData(View, Model);
		Panel title = new Panel();

		Panel Core = new Panel();
		Core.setBackground(new Color(54, 52, 50));
		Core.setLayout(new BorderLayout());
		Core.add(title, BorderLayout.NORTH);
		Label Name = new Label("Army " + a.getName());
		Name.setFont(new Font("serif", Font.BOLD, 48));
		Name.setForeground(Color.white);
		title.add(Name);
		View.add(Core, BorderLayout.CENTER);
		Panel units = new Panel();
		units.setLayout(new GridLayout(0, 5));
		Core.add(units, BorderLayout.CENTER);
		Panel Back = new Panel();
		Back.setLayout(new BorderLayout());
		// BuildButton backToCity=new BuildButton("Back To
		// City",a.getCurrentLocation(),null);
		BuildButton back = new BuildButton("Back", a.getCurrentLocation(), null);
		if (r == "world")
			back.setActionCommand("Back To World");
		else
			back.setActionCommand("Back To City");

		// backToCity.addActionListener(this);
		// backToCity.setBackground(new Color(214, 122, 9));
		// backToCity.setPreferredSize(new Dimension(400,80));
		// backToCity.setForeground(Color.white);
		// backToCity.setFont(new Font("serif",Font.BOLD,48));
		back.addActionListener(this);
		back.setBackground(new Color(214, 122, 9));
		back.setPreferredSize(new Dimension(400, 80));
		back.setForeground(Color.white);
		back.setFont(new Font("serif", Font.BOLD, 48));
		Back.setLayout(new FlowLayout(FlowLayout.LEFT));
		// Back.add(backToCity);
		Back.add(back);
		Core.add(Back, BorderLayout.SOUTH);
		for (int i = 0; i < a.getUnits().size(); i++) {
			if (a.getUnits().get(i) instanceof Archer) {
				BuildButton b = new BuildButton("Archer Level: "
						+ a.getUnits().get(i).getLevel(),
						a.getCurrentLocation(), a.getUnits().get(i));
				b.addActionListener(this);
				b.setForeground(Color.white);
				b.setFont(new Font("serif", Font.BOLD, 24));
				b.setBackground(new Color(24, 140, 57));
				b.setActionCommand("Unit");
				units.add(b);
			}

			else if (a.getUnits().get(i) instanceof Cavalry) {
				BuildButton b = new BuildButton("Cavalry Level: "
						+ a.getUnits().get(i).getLevel(),
						a.getCurrentLocation(), a.getUnits().get(i));
				b.addActionListener(this);
				b.setForeground(Color.white);
				b.setFont(new Font("serif", Font.BOLD, 24));
				b.setBackground(new Color(192, 192, 192));
				b.setActionCommand("Unit");
				units.add(b);
			} else if (a.getUnits().get(i) instanceof Infantry) {
				BuildButton b = new BuildButton("Infantry Level: "
						+ a.getUnits().get(i).getLevel(),
						a.getCurrentLocation(), a.getUnits().get(i));
				b.addActionListener(this);
				b.setForeground(Color.white);
				b.setFont(new Font("serif", Font.BOLD, 24));
				b.setBackground(new Color(255, 201, 102));
				b.setActionCommand("Unit");
				units.add(b);
			}
		}
		View.validate();
		View.repaint();
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		try {	
			TimeUnit.SECONDS.sleep(6);
			URL soundbyte = new File("wav1.wav").toURI().toURL();
			java.applet.AudioClip clip = java.applet.Applet.newAudioClip(soundbyte);
			clip.play();
			GameController e = new GameController();
			Army a = new Army("Rome");
		} catch (Exception e) {
			;
		}

	}
}
