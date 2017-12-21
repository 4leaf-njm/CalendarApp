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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;

import com.calendar.adapter.MouseAdapter;
import com.calendar.adapter.WindowAdapter;
import com.calendar.domain.ScheduleVO;
import com.calendar.service.MemberService;
import com.calendar.service.impl.MemberServiceImpl;
import com.calendar.util.ImageUtil;

public class ScheduleBasicView extends JFrame{
	
	private MemberService memberSerivce = MemberServiceImpl.getInstance();
	private MainView mainView;
	private String loginUser;
	private String selDate;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private Calendar calendar = Calendar.getInstance();
	private JLabel lblDate;
	private JPanel listPanel;
	
	private String url;
	
	public void setMainView(MainView mainView) {
		this.mainView = mainView;
	}
	
	public void setLoginUser(String loginUser) {
		this.loginUser = loginUser;
	}
	
	public void setSelDate(String selDate) {
		this.selDate = selDate;
		
		try {
			calendar.setTime(sdf.parse(selDate));
			String date = new SimpleDateFormat("MM월 dd일").format(calendar.getTime());
			lblDate.setText(date);
			
			addScheduleList(selDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public ScheduleBasicView() {
		init();
		
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		
		url = "http://nscompany.speedgabia.com/images/calendar.png";
		setIconImage(ImageUtil.getImage(url));
		
		lblDate = new JLabel("12월 11일");
		lblDate.setFont(new Font("굴림", Font.BOLD, 16));
		lblDate.setBounds(205, 17, 95, 15);
		getContentPane().add(lblDate);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 47, 484, 6);
		getContentPane().add(separator);
		
		url = "http://nscompany.speedgabia.com/images/plus.png";
		JButton btnRegister = new JButton("");
		btnRegister.setIcon(new ImageIcon(ImageUtil.getImage(url)));
		btnRegister.setBounds(437, 10, 30, 30);
		btnRegister.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ScheduleRegisterView registerView = new ScheduleRegisterView();
				registerView.setScheduleBasicView(ScheduleBasicView.this);
				registerView.setMainView(mainView);
				registerView.setLoginUser(loginUser);
				registerView.setSelDate(selDate);
				setVisible(false);
			}
		});
		getContentPane().add(btnRegister);
		
		listPanel = new JPanel();
		listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
		listPanel.setBackground(Color.WHITE);
		listPanel.setBorder(null);
		
		JScrollPane scrollPane = new JScrollPane(listPanel);
		scrollPane.setBounds(14, 65, 453, 333);
		scrollPane.setBorder(null);
		
		getContentPane().add(scrollPane);
	}
	
	private void init() {
		setTitle("일정 관리 | 목록");
		
		// 화면 크기 구하기
		Toolkit tk = getToolkit();
		Dimension dim = tk.getScreenSize();
		setSize(500, 457); // 전체화면
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
	
	private void addScheduleList(String date) {
		List<ScheduleVO> scheduleList = memberSerivce.getScheduleListByDate(loginUser, selDate);
		
		if(scheduleList.isEmpty()) {
			JLabel lblEmpty = new JLabel();
			lblEmpty.setText("* 일정이 없습니다.");
			lblEmpty.setForeground(Color.RED);
			lblEmpty.setFont(new Font("나눔바른고딕", Font.PLAIN, 16));
			lblEmpty.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 0));
			listPanel.add(lblEmpty);
		}
		else{
			for(ScheduleVO schedule : scheduleList) {
				JPanel panel = new JPanel();
				panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
				panel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
				panel.setBackground(Color.WHITE);
				panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				panel.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						setVisible(false);
						ScheduleDetailView detailView = new ScheduleDetailView();
						detailView.setScheduleBasicView(ScheduleBasicView.this);
						detailView.setMainView(mainView);
						detailView.setSchedule(schedule);
					}
				});
				listPanel.add(panel);
				
				JLabel lblTitle = new JLabel(schedule.getSchedule_title());
				lblTitle.setFont(new Font("굴림", Font.BOLD, 13));
				panel.add(lblTitle);
				panel.add(Box.createVerticalStrut(10));
				
				String time = schedule.getSchedule_start() + " - " + schedule.getSchedule_end();
				JLabel lblTime = new JLabel(time);
				lblTime.setFont(new Font("Gulim", Font.PLAIN, 12));
				panel.add(lblTime);
				
				panel.add(Box.createVerticalStrut(16));
				JSeparator separator_1 = new JSeparator();
				separator_1.setMaximumSize(new Dimension(1000, 13));
				listPanel.add(separator_1);
			}
		}
	}
	
	public void update() {
		listPanel.removeAll();
		addScheduleList(selDate);
	}
}
