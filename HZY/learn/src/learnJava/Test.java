

import java.math.BigInteger;
//import tom.jiafei.Rational;


import java.awt.*;
import javax.swing.*;

class Test
{
	public static void main(String args[])
	{
		new ShowLayout();
	}
} 
class ShowLayout extends JFrame
{
	PanelGridLayout panelGrid;   // 网格布局的面板
	PanelNullLayout panelNull;   // 空布局面板
	JTabbedPane p;               // 选项卡窗格
	
	panelGrid = new PannelGridLayout();
	ShowLayout() 
	{
		panelGrid = new PanelGridLayout();
		panelNull = new PanelNullLayout();
		p = new JTabbedPane();
		p.add("网格布局的面板", panelGrid);
		p.add("空布局的面板", panelNull);
		add(p, BorderLayout.CENTER);
		add(new JButton("窗体是BorderLayout布局"), BorderLayout.NORTH);
		add(new JButton("南"), BorderLayout.SOUTH);
		add(new JButton("西"), BorderLayout.WEST);
		add(new JButton("东"), BorderLayout.EAST);
		
		setBounds(10, 10, 570, 390);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		validate();
	}
}

class PanelGridLayout extends JPanel  // 添加面板
{
	PanelGridLayout()
	{
		GridLayout gird = new GridLayout(12, 12);
		setLayout(gird);
		Label label[][] = Label[12][12];
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 12; j++) {
				label[i][j] = new Label();
				if ((i + j) % 2 == 0) 
					label[i][j].setBackground(Color.black);
				else
					label[i][j].setBackground(Color.white);
				add(label[i][j]);
			}
		}
	}
}

class PanelNullLayout extends JPanel
{
	JButton button;
	JTextField text;
	PanelNullLayout()
	{
		setLayout(null);
		button = new JButton("确定");
		text = new JTextField();
		add(text);
		add(button);
		text.setBounds(100, 30, 90, 30);
		button.setBounds(190, 30, 66, 30);
	}
}