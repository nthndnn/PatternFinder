package tests;

import java.awt.EventQueue;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

//import java.awt.Color;
//import java.awt.Dimension;
//import java.awt.Graphics;
//
import javax.swing.JFrame;
//import javax.swing.JPanel;

public class GuiTest {

	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable(){

			@Override
			public void run() {
				JFrame win = new JFrame();
				win.setVisible(true);
			}
			
			
		});
		
//		javax.swing.JFrame win = new javax.swing.JFrame();
//		//JFreeChart jfc = new JFreeChart(null);
//		ChartPanel cp = new ChartPanel(null);
//		win.add(cp);
//		win.pack();
////		JPanel circ = new JPanel(){
////			public void paintComponents(Graphics g){
////				System.out.println("drawing");
////				g.setColor(Color.BLACK);
////				g.drawOval(0, 0, 100, 100);
////			}
////		};
////		
////		win.setContentPane(circ);
////		
////		win.setPreferredSize(new Dimension(500,500));
////		win.pack();
////		
//		win.setVisible(true);
//		
//		while (true){
//			circ.paintComponents(circ.getGraphics());
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
	}
	

}