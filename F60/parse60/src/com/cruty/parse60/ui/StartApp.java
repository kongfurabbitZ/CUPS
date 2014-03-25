package com.cruty.parse60.ui;

import javax.swing.UIManager;

public class StartApp {
	public static void main(String[] args) {
		
		try {
//			UIManager.setLookAndFeel("javax.swing.plaf.mac.MacLookAndFeel");
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
//			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		} 
		new MainFrame().setVisible(true);
//		System.out.println("12345678900000000079000000000".substring(8,10));
//		System.out.println("00000000160008001179235403201".substring(8,10));
//		System.out.println("00000000160008001179235403201".substring(28,30));

	}
}
