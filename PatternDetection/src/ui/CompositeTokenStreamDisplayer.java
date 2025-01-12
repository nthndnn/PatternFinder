package ui;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import behaviorClassification.RawTimeSeriesTable;
import patternDetection.SimpleTokenStream;

public class CompositeTokenStreamDisplayer extends JPanel {
	
	private ArrayList<TokenStreamDisplayer> displays;
	
	
	public CompositeTokenStreamDisplayer(SimpleTokenStream sts){
		
		displays = new ArrayList<TokenStreamDisplayer>();
//		String[] quants = sts.quantities();
//		for (int i=0; i<quants.length; i++){
//			
//		}
		for (String q : sts.quantities()){
			displays.add(
					new TokenStreamDisplayer(sts.getQuant(q))
					);
		}
		
		initializeGui();
		
	}
	
	public CompositeTokenStreamDisplayer(SimpleTokenStream sts, boolean b){
		this(sts, sts.reconstruct());
	}
	
	
	public CompositeTokenStreamDisplayer(SimpleTokenStream sts, RawTimeSeriesTable table){
		displays = new ArrayList<TokenStreamDisplayer>();
//		String[] quants = sts.quantities();
//		for (int i=0; i<quants.length; i++){
//			
//		}
		for (String q : sts.quantities()){
			displays.add(
					new TokenStreamDisplayer(sts, table, q)
					);
		}
		
		initializeGui();
		
	}
	
	private void initializeGui() {
		this.setLayout(new GridLayout(displays.size(),1));
		for (TokenStreamDisplayer tsd : displays){
			this.add(tsd);
		}
	}
	
	public void display(){
		JFrame win = new JFrame();
		win.add(this);
		win.pack();
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		win.setVisible(true);
	}
	
	
}
