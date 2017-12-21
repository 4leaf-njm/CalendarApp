package com.calendar.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.calendar.adapter.MouseAdapter;
import com.calendar.adapter.WindowAdapter;
import com.calendar.service.MemberService;
import com.calendar.service.impl.MemberServiceImpl;
import com.calendar.util.ImageUtil;

// 로그인을 하기위한 View 화면
public class LoginView extends JFrame{
	private MemberService memberService;
	
	private String url;
	
	private JTextField txtID;
	private JPasswordField txtPW;

	public LoginView() {
		init();
		
		memberService = MemberServiceImpl.getInstance();
		
		url = "http://nscompany.speedgabia.com/images/calendar.png";
		setIconImage(ImageUtil.getImage(url));
		
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		
		url = "http://nscompany.speedgabia.com/images/user-silhouette.png";
		JLabel lblIDImage = new JLabel("");
		lblIDImage.setBounds(47, 47, 32, 32);
		lblIDImage.setIcon(new ImageIcon(ImageUtil.getImage(url)));
		getContentPane().add(lblIDImage);
		
		url = "http://nscompany.speedgabia.com/images/locked-padlock.png";
		JLabel lblPWImage = new JLabel("");
		lblPWImage.setBounds(47, 110, 32, 32);
		lblPWImage.setIcon(new ImageIcon(ImageUtil.getImage(url)));
		getContentPane().add(lblPWImage);
		
		txtID = new JTextField();
		txtID.setBounds(179, 47, 159, 32);
		getContentPane().add(txtID);
		txtID.requestFocus();
		txtID.setColumns(10);
		
		JLabel lblID = new JLabel("아이디");
		lblID.setBounds(92, 50, 59, 25);
		lblID.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		getContentPane().add(lblID);
		
		JLabel lblPW = new JLabel("비밀번호");
		lblPW.setBounds(92, 118, 77, 22);
		lblPW.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		getContentPane().add(lblPW);
		
		JButton btnLogin = new JButton("로그인");
		btnLogin.setForeground(Color.DARK_GRAY);
		btnLogin.setBounds(47, 174, 292, 44);
		btnLogin.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		btnLogin.setBackground(new Color(175, 238, 238));
		btnLogin.setBorder(null);
		btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String id = txtID.getText();
				String pw = txtPW.getText();
				
				int result = memberService.login(id, pw);
				if(result == 0){
					JOptionPane.showMessageDialog(null, "아이디가 존재하지 않습니다.");
					txtID.requestFocus();
					return;
				}
				else if(result == -1){
					JOptionPane.showMessageDialog(null, "비밀번호가 일치하지 않습니다.");
					txtPW.requestFocus();
					return;
				}
				else if(result == 1) {
					JOptionPane.showMessageDialog(null, "로그인에 성공했습니다.");
					dispose();

					MainView mainView = new MainView();
					mainView.setLoginUser(id);
				}
			}
		});
		getContentPane().add(btnLogin);
		
		JLabel lblJoin = new JLabel("회원가입");
		lblJoin.setFont(new Font("나눔고딕 ExtraBold", Font.BOLD, 18));
		lblJoin.setBounds(126, 243, 134, 43);
		lblJoin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblJoin.setHorizontalAlignment(SwingConstants.CENTER);
		lblJoin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JoinView joinView = new JoinView();
				joinView.setLoginView(LoginView.this);
				setVisible(false);
				txtID.setText("");
				txtPW.setText("");
			}
		});
		getContentPane().add(lblJoin);
		
		txtPW = new JPasswordField();
		txtPW.setBounds(179, 114, 159, 32);
		getContentPane().add(txtPW);
		
		getContentPane().revalidate();
		getContentPane().repaint();
	}
	
	private void init() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("일정 관리 | 로그인");
		
		// 화면 크기 구하기
		Toolkit tk = getToolkit();
		Dimension dim = tk.getScreenSize();
		setSize(400, 345); // 전체화면
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
	
	// 회원가입 후 아이디 세팅
	public void setting(String id) {
		txtID.setText(id);
		txtPW.requestFocus();
	}
}
