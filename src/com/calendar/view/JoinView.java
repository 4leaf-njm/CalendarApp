package com.calendar.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.calendar.adapter.WindowAdapter;
import com.calendar.domain.MemberVO;
import com.calendar.service.MemberService;
import com.calendar.service.impl.MemberServiceImpl;
import com.calendar.util.ImageUtil;

/* 회원가입을 하기 위한 View 화면 */
public class JoinView extends JFrame{
	private MemberService memberService;
	private LoginView loginView;
	
	private JTextField txtName;
	private JTextField txtID;
	private JPasswordField pwfPW;
	private JPasswordField pwfBirth;
	private JTextField txtPhone1;
	private JTextField txtPhone2;
	private JTextField txtPhone3;
	private JTextField txtBirth;
	private JCheckBox chkAgree;
	
	private String url;
	
	public void setLoginView(LoginView loginView) {
		this.loginView = loginView;
	}
	
	public JoinView() {
		init();
		
		memberService = MemberServiceImpl.getInstance();
		
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		
		url = "http://nscompany.speedgabia.com/images/calendar.png";
		setIconImage(ImageUtil.getImage(url));
		
		JLabel lblName = new JLabel("이름");
		lblName.setBounds(46, 67, 32, 17);
		lblName.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		getContentPane().add(lblName);
		
		JLabel lblID = new JLabel("아이디");
		lblID.setBounds(46, 118, 47, 17);
		lblID.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		getContentPane().add(lblID);
		
		txtName = new JTextField();
		txtName.setBounds(150, 65, 143, 25);
		getContentPane().add(txtName);
		txtName.setColumns(10);
		
		txtID = new JTextField();
		txtID.setBounds(150, 115, 143, 25);
		getContentPane().add(txtID);
		txtID.setColumns(10);
		
		JLabel lblPW = new JLabel("비밀번호");
		lblPW.setBounds(46, 167, 81, 17);
		lblPW.setFont(new Font("굴림", Font.BOLD, 15));
		getContentPane().add(lblPW);
		
		JLabel lblNewLabel = new JLabel("주민등록번호");
		lblNewLabel.setBounds(46, 215, 92, 17);
		lblNewLabel.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		getContentPane().add(lblNewLabel);
		
		pwfPW = new JPasswordField();
		pwfPW.setBounds(150, 164, 143, 25);
		getContentPane().add(pwfPW);
		
		JLabel lblNewLabel_1 = new JLabel("-");
		lblNewLabel_1.setBounds(215, 216, 6, 15);
		getContentPane().add(lblNewLabel_1);
		
		pwfBirth = new JPasswordField();
		pwfBirth.addKeyListener(new KeyAdapter(){
			public void keyTyped(KeyEvent e){
				JPasswordField src = (JPasswordField)e.getSource();
				if(src.getText().length()>=7) e.consume();
				char c = e.getKeyChar();
				  if (!Character.isDigit(c)) {
				   e.consume();
				   return;
				  }
			}
		});
		pwfBirth.setBounds(224, 213, 67, 25);
		getContentPane().add(pwfBirth);
		
		JLabel lblPhoneNumber = new JLabel("휴대전화");
		lblPhoneNumber.setBounds(46, 267, 67, 17);
		lblPhoneNumber.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		getContentPane().add(lblPhoneNumber);
		
		txtPhone1 = new JTextField();
		txtPhone1.setBounds(150, 265, 35, 25);
		getContentPane().add(txtPhone1);
		txtPhone1.setColumns(10);
		txtPhone1.addKeyListener(new KeyAdapter(){
			public void keyTyped(KeyEvent e){
				JTextField src = (JTextField)e.getSource();
				if(src.getText().length()>=3) e.consume(); // 길이를 3자리로 제한
				char c = e.getKeyChar();
				  if (!Character.isDigit(c)) { // 숫자만 입력받게 함
				   e.consume();
				   return;
				  }
			}
		});
		
		JLabel lblPhoneBar = new JLabel("-");
		lblPhoneBar.setBounds(189, 268, 6, 15);
		getContentPane().add(lblPhoneBar);
		
		txtPhone2 = new JTextField();
		txtPhone2.setBounds(199, 265, 40, 25);
		getContentPane().add(txtPhone2);
		txtPhone2.setColumns(10);
		txtPhone2.addKeyListener(new KeyAdapter(){
			public void keyTyped(KeyEvent e){
				JTextField src = (JTextField)e.getSource();
				if(src.getText().length()>=4) e.consume();
				char c = e.getKeyChar();
				  if (!Character.isDigit(c)) {
				   e.consume();
				   return;
				  }
			}
		});
		
		JLabel lblNewLabel_2 = new JLabel("-");
		lblNewLabel_2.setBounds(242, 268, 6, 15);
		getContentPane().add(lblNewLabel_2);
		
		txtPhone3 = new JTextField();
		txtPhone3.setBounds(253, 265, 40, 25);
		getContentPane().add(txtPhone3);
		txtPhone3.setColumns(10);
		txtPhone3.addKeyListener(new KeyAdapter(){
			public void keyTyped(KeyEvent e){
				JTextField src = (JTextField)e.getSource();
				if(src.getText().length()>=4) e.consume(); // 길이를 4자리로 제한
				char c = e.getKeyChar(); 
				  if (!Character.isDigit(c)) { // 숫자만 입력받게 함
				   e.consume();
				   return;
				  }
			}
		});
		
		JTextArea txtForm = new JTextArea();
		txtForm.setText("정보통신망법 규정에 따라 NS Company에 회원가입 신청하시는 분께 수집하는 개인정보의 항목, 개인정보의 수집 및 이용목적, 개인정보의 보유 및 이용기간을 안내 드리오니 자세히 읽은 후 동의하여 주시기 바랍니다. \n1. 수집하는 개인정보 이용자가 캘린더와 같이 개인화 혹은 회원제 서비스를 이용하기 위해 회원가입을 할 경우, NS Company는 서비스 이용을 위해 필요한 최소한의 개인정보를 수집합니다. \n2. 수집한 개인정보의 이용 NS Company는 회원관리, 서비스 개발・ 제공 및 향상, 안전한 인터넷 이용환경 구축 등 아래의 목적으로만 개인정보를 이용합니다. \n- 회원 가입 의사의 확인, 연령 확인 및 법정 대리인 동의 진행, 이용자 및 법정대리인의 본인 확인, 이용자 식별, 회원탈퇴 의사의 확인 등 회원관리를 위하여 개인정보를 이용합니다. \n- 보안, 프라이버시, 안전 측면에서 이용자가  안심하고 이용할 수 있는 서비스 이용환경  구축을 위해 개인정보를 이용합니다. \n3. 개인정보의 파기 \n회사는 원칙적으로 이용자의 개인정보를  회원 탈퇴 시 지체없이 파기하고 있습니다. 단, 이용자에게 개인정보 보관기간에 대해  별도의 동의를 얻은 경우, 또는 법령에서  일정 기간 정보보관 의무를 부과하는 경우 에는 해당 기간 동안 개인정보를 안전하게  보관합니다. \n로그인 기록: 3개월 ");
		txtForm.setLineWrap(true);
		txtForm.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(txtForm);
		scrollPane.setBounds(46, 350, 247, 82);

		getContentPane().add(scrollPane);
		
		JLabel lblForm = new JLabel("개인정보 수집 및 이용에 대한 안내 (필수)");
		lblForm.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		lblForm.setBounds(46, 326, 256, 15);
		getContentPane().add(lblForm);
		
		chkAgree = new JCheckBox("위 약관에 동의합니다.");
		chkAgree.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		chkAgree.setBackground(Color.WHITE);
		chkAgree.setBounds(46, 438, 166, 23);
		getContentPane().add(chkAgree);
		
		JButton btnJoin = new JButton("회원가입");
		btnJoin.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		btnJoin.setForeground(Color.WHITE);
		btnJoin.setBackground(SystemColor.textHighlight);
		btnJoin.setBounds(46, 481, 247, 55);
		btnJoin.setBorder(null);
		btnJoin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = txtName.getText();
				String id = txtID.getText();
				String pw = pwfPW.getText();
				String birth = txtBirth.getText() + "-" + pwfBirth.getText();
				String phone = txtPhone1.getText() + "-" + txtPhone2.getText() + "-" + txtPhone3.getText();
				boolean isChecked = chkAgree.isSelected();
				
				if(name.equals("")) { // 이름이 비었을 경우
					JOptionPane.showMessageDialog(null, "이름을 입력하세요"); // 메시지창 띄우기
					txtName.requestFocus(); // 이름에 포커스 가게하기
					return;
				}
				else if(id.equals("")) {
					JOptionPane.showMessageDialog(null, "아이디를 입력하세요");
					txtID.requestFocus();
					return;
				}
				else if(pw.equals("")) {
					JOptionPane.showMessageDialog(null, "비밀번호를 입력하세요");
					pwfPW.requestFocus();
					return;
				}
				else if(birth.equals("-")) {
					JOptionPane.showMessageDialog(null, "주민번호를 입력하세요");
					txtBirth.requestFocus();
					return;
				}
				else if(phone.equals("--")) {
					JOptionPane.showMessageDialog(null, "핸드폰을 입력하세요");
					txtPhone1.requestFocus();
					return;
				}
				else if(!isChecked) {
					JOptionPane.showMessageDialog(null, "약관에 동의해주세요");
					return;
				}
				
				MemberVO member = new MemberVO();
				member.setMem_name(name);
				member.setMem_id(id);
				member.setMem_pw(pw);
				member.setMem_birth(birth);
				member.setMem_phone(phone);
				
				memberService.join(member);
				JOptionPane.showMessageDialog(null, "회원가입에 성공했습니다.");
				dispose();
				
				loginView.setVisible(true);
				loginView.setting(id);
			}
		});
		getContentPane().add(btnJoin);
		
		txtBirth = new JTextField();
		txtBirth.addKeyListener(new KeyAdapter(){
			public void keyTyped(KeyEvent e){
				JTextField src = (JTextField)e.getSource();
				if(src.getText().length()>=6) e.consume();
				char c = e.getKeyChar();
				  if (!Character.isDigit(c)) {
				   e.consume();
				   return;
				  }
			}
		});
		txtBirth.setBounds(150, 215, 59, 25);
		getContentPane().add(txtBirth);
		txtBirth.setColumns(10);
		
		url = "http://nscompany.speedgabia.com/images/left-arrow.png";
		JButton btnBack = new JButton("");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loginView.setVisible(true);
				dispose();
			}
		});
		btnBack.setBounds(22, 12, 32, 33);
		btnBack.setIcon(new ImageIcon(ImageUtil.getImage(url)));
		btnBack.setOpaque(true);
		
		getContentPane().add(btnBack);
		
		JLabel label = new JLabel("회원가입");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 17));
		label.setBounds(135, 18, 81, 19);
		getContentPane().add(label);
		
		getContentPane().revalidate();
		getContentPane().repaint();
	}
	
	private void init() {
		setTitle("일정 관리 | 가입");
		
		// 화면 크기 구하기
		Toolkit tk = getToolkit();
		Dimension dim = tk.getScreenSize();
		setSize(358, 610); // 전체화면
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
