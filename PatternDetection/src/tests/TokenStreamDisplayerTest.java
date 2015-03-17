package tests;

import gui.TokenStreamDisplayer;

import java.util.ArrayList;

import javax.swing.JFrame;

import patternDetection.Behavior;
import patternDetection.Token;

public class TokenStreamDisplayerTest {

	public static void main(String[] args) {
//		System.setProperty("com.apple.awt.CocoaComponent.CompatibilityMode", "false");
		JFrame win = new JFrame();
//		win.pack();
//		win.setVisible(true);
//		win.pack();
//		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		TokenStreamDisplayer tsd = new TokenStreamDisplayer(testList());
		win.add(tsd);
		win.pack();
//		
//		win.setEnabled(true);
		
		win.setVisible(true);
		while(true){
			
			tsd.paintComponents(tsd.getGraphics());
			Pnt.pnt(tsd.getWidth());
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private static ArrayList<Token> testList(){
		ArrayList<Token> out = new ArrayList<Token>();
		out.add(new Token("",Behavior.DIP,0));
		out.add(new Token("",Behavior.DEC,1));
		out.add(new Token("",Behavior.FLA,2));
		out.add(new Token("",Behavior.INC,3));
		out.add(new Token("",Behavior.SPI,4));
		out.add(new Token("",Behavior.UNK,5));
		
		return out;
	}

}