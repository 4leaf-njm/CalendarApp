package com.calendar.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import com.calendar.adapter.WindowAdapter;
import com.calendar.util.ImageUtil;

public class SplashView extends JFrame{
	
	private String url;
	private final JLabel lblNewLabel = new JLabel("New label");
	
	public static void main(String[] args) {
		new SplashView();
	}
	
	public SplashView() {
		init();
		
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		
		url = "http://nscompany.speedgabia.com/images/calendar.png";
		setIconImage(ImageUtil.getImage(url));
		
		url = "http://nscompany.speedgabia.com/images/bg_splash.png";
		JPanel bgPanel = new JPanel(){
			@Override
			protected void paintComponent(Graphics g) {
				g.drawImage(ImageUtil.getImage(url), -100, -370, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		bgPanel.setLayout(null);
		c.add(bgPanel, BorderLayout.CENTER);

		JLabel lblTitle = new JLabel("일정 관리 앱");
		lblTitle.setBounds(113, 40, 400, 30);
		lblTitle.setFont(new Font("맑은 고딕", Font.BOLD, 26));
		bgPanel.add(lblTitle);
		
		String url2 = "http://nscompany.speedgabia.com/images/icon_splash.png";
		JLabel lblIcon = new JLabel();
		lblIcon.setIcon(new ImageIcon(ImageUtil.getImage(url2)));
		lblIcon.setBounds(60, 100, 256, 256);
		bgPanel.add(lblIcon);
		
		JLabel lblLoading = new JLabel();
		lblLoading.setFont(new Font("맑은 고딕", Font.BOLD, 21));
		lblLoading.setBounds(147, 400, 400, 30);
		
		Thread thread1 = new Thread(new Runnable() {
			@Override
			public void run() {
				String[] str = {"Loading .", "Loading ..", "Loading ...", "Loading ...."};
				int idx = 0;
				
				while(true) {
					lblLoading.setText(str[idx++]);
					try {
						Thread.sleep(400);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if(idx == str.length) idx = 0;
				}
			}
		});
		thread1.start();
		bgPanel.add(lblLoading);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setMaximum(10);
		progressBar.setValue(0);
		progressBar.setPreferredSize(new Dimension(400, 35));
		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				int count = 0;
				while(true){
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					progressBar.setValue(++count);
					
					if(count == progressBar.getMaximum()) {
						dispose();
						new LoginView();
					}
				}
			}
		});
		thread2.start();
		c.add(progressBar, BorderLayout.SOUTH);
		
		c.revalidate();
		c.repaint();
	}
	private void init() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("일정 관리");
		
		// 화면 크기 구하기
		Toolkit tk = getToolkit();
		Dimension dim = tk.getScreenSize();
		setSize(400, 600); // 전체화면
		setLocation((dim.width/2)-(getWidth()/2), (dim.height/2)-(getHeight()/2)); // 화면 중앙 배치
		
		setVisible(true);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				dispose();
			}
		});
	}
}
