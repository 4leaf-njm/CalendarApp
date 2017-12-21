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
import java.text.SimpleDateFormat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;

import com.calendar.adapter.MouseAdapter;
import com.calendar.adapter.WindowAdapter;
import com.calendar.domain.ScheduleVO;
import com.calendar.service.MemberService;
import com.calendar.service.impl.MemberServiceImpl;
import com.calendar.util.ImageUtil;

public class ScheduleDetailView extends JFrame{
	
	private MemberService memberService;
	private ScheduleVO schedule;
	private MainView mainView;
	private ScheduleBasicView basicView;
	
	private String url;
	
	public void setSchedule(ScheduleVO schedule) {
		this.schedule = schedule;
		showDetailSchedule(this.schedule);
	}
	
	public void setMainView(MainView mainView) {
		this.mainView = mainView;
	}
	
	public void setScheduleBasicView(ScheduleBasicView basicView) {
		this.basicView = basicView;
	}
	
	public ScheduleDetailView() {
		init();
		
		memberService = MemberServiceImpl.getInstance();
		
		getContentPane().setLayout(null);
		getContentPane().setBackground(Color.WHITE);
	
		url = "http://nscompany.speedgabia.com/images/calendar.png";
		setIconImage(ImageUtil.getImage(url));
		
		JLabel lblMain = new JLabel("일정 세부사항");
		lblMain.setFont(new Font("맑은 고딕", Font.BOLD, 17));
		lblMain.setBounds(178, 18, 130, 19);
		getContentPane().add(lblMain);
		
		url = "http://nscompany.speedgabia.com/images/left-arrow.png";
		JButton btnBack = new JButton("");
		btnBack.setBounds(12, 13, 30, 30);
		btnBack.setOpaque(true);
		btnBack.setIcon(new ImageIcon(ImageUtil.getImage(url)));
		btnBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				basicView.setVisible(true);
			}
		});
		getContentPane().add(btnBack);
		
		url = "http://nscompany.speedgabia.com/images/recycling-bin.png";
		JButton btnRemove = new JButton("");
		btnRemove.setBounds(442, 10, 30, 33);
		btnRemove.setIcon(new ImageIcon(ImageUtil.getImage(url)));
		btnRemove.setOpaque(true);
		btnRemove.setBorder(null);
		btnRemove.setBackground(new Color(240, 240, 240));
		btnRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "이 일정을 삭제하시겠습니까 ?", "메시지", JOptionPane.YES_NO_OPTION);
				if(result == JOptionPane.YES_OPTION) {
					memberService.removeSchedule(schedule.getSchedule_no());
					JOptionPane.showMessageDialog(null, "삭제가 완료되었습니다.");
					
					dispose();
					basicView.update();
					mainView.update();
				}
			}
		});
		getContentPane().add(btnRemove);
		
		url = "http://nscompany.speedgabia.com/images/pencil-on-a-notes-paper.png";
		JButton btnEdit = new JButton("");
		btnEdit.setBounds(400, 13, 30, 30);
		btnEdit.setOpaque(true);
		btnEdit.setIcon(new ImageIcon(ImageUtil.getImage(url)));
		btnEdit.setBorder(null);
		btnEdit.setBackground(new Color(240, 240, 240));
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ScheduleModifyView modifyView = new ScheduleModifyView();
				modifyView.setScheduleDetailView(ScheduleDetailView.this);
				modifyView.setMainView(mainView);
				modifyView.setSchedule(schedule);
				
				setVisible(false);
			}
		});
		getContentPane().add(btnEdit);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 53, 484, 2);
		separator.setForeground(new Color(160, 160, 160));
		separator.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(separator);
		
		JLabel lblTitle = new JLabel("제목");
		lblTitle.setBounds(36, 76, 41, 15);
		lblTitle.setFont(new Font("굴림", Font.BOLD, 14));
		getContentPane().add(lblTitle);
		
		JLabel lblDate = new JLabel("일자");
		lblDate.setFont(new Font("굴림", Font.BOLD, 15));
		lblDate.setBounds(36, 117, 41, 15);
		getContentPane().add(lblDate);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(33, 101, 422, 2);
		getContentPane().add(separator_1);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(33, 142, 422, 2);
		getContentPane().add(separator_2);
		
		JLabel lblMemo = new JLabel("메모");
		lblMemo.setFont(new Font("굴림", Font.BOLD, 15));
		lblMemo.setBounds(36, 200, 41, 15);
		getContentPane().add(lblMemo);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(33, 184, 422, 2);
		getContentPane().add(separator_3);
		
		JLabel lblTime = new JLabel("시간");
		lblTime.setFont(new Font("굴림", Font.BOLD, 15));
		lblTime.setBounds(36, 157, 41, 15);
		getContentPane().add(lblTime);
	}
	
	private void init() {
		setTitle("일정 관리 | 상세정보");
		
		// 화면 크기 구하기
		Toolkit tk = getToolkit();
		Dimension dim = tk.getScreenSize();
		setSize(500, 451); // 전체화면
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
	
	private void showDetailSchedule(ScheduleVO schedule) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		JLabel lblTitle2 = new JLabel(schedule.getSchedule_title());
		lblTitle2.setBounds(91, 69, 323, 26);
		lblTitle2.setFont(new Font("나눔바른고딕", Font.PLAIN, 15));
		getContentPane().add(lblTitle2);
		
		JLabel lblDate2 = new JLabel(sdf.format(schedule.getSchedule_date()));
		lblDate2.setBounds(91, 110, 364, 26);
		lblDate2.setFont(new Font("나눔바른고딕", Font.PLAIN, 15));
		getContentPane().add(lblDate2);
		
		JLabel lblTime2 = new JLabel(schedule.getSchedule_start() + " - " + schedule.getSchedule_end());
		lblTime2.setBounds(91, 151, 364, 26);
		lblTime2.setFont(new Font("나눔바른고딕", Font.PLAIN, 15));
		getContentPane().add(lblTime2);
		
		JTextArea txtMemo2 = new JTextArea(schedule.getSchedule_content());
		txtMemo2.setBounds(91, 197, 358, 185);
		txtMemo2.setFont(new Font("나눔바른고딕", Font.PLAIN, 15));
		txtMemo2.setLineWrap(true);
		getContentPane().add(txtMemo2);
		
		JLabel lblImport = new JLabel("");
		lblImport.setBounds(415, 67, 40, 27);
		if(schedule.getSchedule_import_yn() == 'Y'){
			url = "http://nscompany.speedgabia.com/images/star_on.png";
			lblImport.setIcon(new ImageIcon(ImageUtil.getImage(url)));
		}
		else{
			url = "http://nscompany.speedgabia.com/images/star_off.png";
			lblImport.setIcon(new ImageIcon(ImageUtil.getImage(url)));
		}
		getContentPane().add(lblImport);
	}
	
	public void update() {
		ScheduleVO sc = memberService.getScheduleByNo(schedule.getSchedule_no());
		showDetailSchedule(sc);
	}
}
