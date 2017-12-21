package com.calendar.view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import com.calendar.adapter.MouseAdapter;
import com.calendar.domain.MemberVO;
import com.calendar.domain.ScheduleVO;
import com.calendar.service.MemberService;
import com.calendar.service.impl.MemberServiceImpl;
import com.calendar.util.ImageUtil;

public class MainView extends JFrame {
	
	private MemberService memberService;
	private List<ScheduleVO> scheduleList;
	private MemberVO member;
	private String loginUser; // 접속한 사용자 아이디
	
	private String[] weeks = {"일", "월", "화", "수", "목", "금", "토"};
	private SimpleDateFormat sdf;
	private Calendar calendar;
	private int curYear, curMonth, curDay;
	private int selMonth;
	private int count = 0; 
	
	private JPanel mainPanel, dayPanel;
	private JLabel lblDate, lblWelcome;
	
	private String url = null;
	
	private boolean isAllList = true;
	
	public void setLoginUser(String loginUser) {
		this.loginUser = loginUser;
		member = memberService.getMemberById(loginUser);
		lblWelcome.setText(member.getMem_name() + " 님 환영합니다.");
		update();
	}
	
	public MainView() {
		init();
		calInit();
		
		url = "http://nscompany.speedgabia.com/images/calendar.png";
		setIconImage(ImageUtil.getImage(url));
		
		memberService = MemberServiceImpl.getInstance();
		
		Container c = getContentPane();
		c.setLayout(new BorderLayout(10, 10));
		
		/* 상단 레이아웃 */
		JPanel topPanel = new JPanel();
		c.add(topPanel, BorderLayout.NORTH);
		topPanel.setLayout(new BorderLayout());
		
		JPanel datePanel = new JPanel();
		datePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
		topPanel.add(datePanel, BorderLayout.CENTER);
		
		JButton btnBeforeMonth = new JButton("◀");
		btnBeforeMonth.setActionCommand("before");
		btnBeforeMonth.addActionListener(new MonthChangeActionListener());
		datePanel.add(btnBeforeMonth);
		
		lblDate = new JLabel(curYear + "년 " + curMonth + "월");
		lblDate.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 20));
		datePanel.add(lblDate);
		
		JButton btnAfterMonth = new JButton("▶");
		btnAfterMonth.setActionCommand("after");
		btnAfterMonth.addActionListener(new MonthChangeActionListener());
		datePanel.add(btnAfterMonth);
		
		/* 달력 레이아웃 */
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout(0, 20));
		mainPanel.setSize(new Dimension(300,500));
		c.add(mainPanel, BorderLayout.CENTER);
		
		createCalendar(curMonth, isAllList);
		
		/* 오른쪽 레이아웃 */
		JPanel rightOnPanel = new JPanel();
		rightOnPanel.setLayout(new BorderLayout());
		rightOnPanel.setPreferredSize(new Dimension(450, 1000));
		rightOnPanel.setBackground(new Color(249, 249, 249));
		rightOnPanel.setBorder(new LineBorder(new Color(189, 189, 189)));
		
		JPanel rightOffPanel = new JPanel();
		rightOffPanel.setLayout(new BorderLayout());
		rightOffPanel.setPreferredSize(new Dimension(60, 1000));
		rightOffPanel.setBackground(new Color(249, 249, 249));
		rightOffPanel.setBorder(new LineBorder(new Color(189, 189, 189)));
		
		JButton btnSubMenuOn = new JButton("◀");
		btnSubMenuOn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				c.remove(rightOffPanel);
				c.add(rightOnPanel, BorderLayout.EAST);
				c.revalidate();
				c.repaint();
			}
		});
		rightOffPanel.add(btnSubMenuOn, BorderLayout.CENTER);
		
		lblWelcome = new JLabel();
		lblWelcome.setForeground(new Color(93, 93, 93));
		lblWelcome.setOpaque(true);
		lblWelcome.setBackground(Color.WHITE);
		lblWelcome.setFont(new Font("나눔바른고딕", Font.BOLD, 19));
		lblWelcome.setPreferredSize(new Dimension(450, 60));
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		rightOnPanel.add(lblWelcome, BorderLayout.NORTH);
		
		JLabel lblOnOff = new JLabel("▶");
		lblOnOff.setBackground(new Color(249, 249, 249));
		lblOnOff.setBorder(new LineBorder(new Color(189, 189, 189)));
		lblOnOff.setPreferredSize(new Dimension(40, 1000));
		lblOnOff.setHorizontalAlignment(SwingConstants.CENTER);
		lblOnOff.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblOnOff.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				// 서브메뉴 닫기
				c.remove(rightOnPanel); 
				c.add(rightOffPanel, BorderLayout.EAST);
				c.revalidate(); // 화면 업데이트
				c.repaint();
			}
		});
		rightOnPanel.add(lblOnOff, BorderLayout.WEST);
		
		JPanel menuPanel = new JPanel();
		menuPanel.setMaximumSize(new Dimension(500, 500));
		menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
		menuPanel.setBackground(new Color(249, 249, 249));
		menuPanel.add(Box.createVerticalStrut(100));
		
		JButton btnRegister = new JButton("일정 추가");
		btnRegister.setMaximumSize(new Dimension(405, 50));
		btnRegister.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ScheduleRegisterView registerView = new ScheduleRegisterView();
				registerView.setMainView(MainView.this);
				registerView.setLoginUser(loginUser);
			}
		});
		
		menuPanel.add(btnRegister);
		menuPanel.add(Box.createVerticalStrut(10));
		
		JButton btnLogout = new JButton("로그아웃");
		btnLogout.setMaximumSize(new Dimension(405, 50));
		btnLogout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "로그아웃 하시겠습니까 ?", "메시지", JOptionPane.YES_NO_OPTION);
				if(result == JOptionPane.OK_OPTION) {
					dispose();
					new LoginView();
				}
			}
		});
		menuPanel.add(btnLogout);
		menuPanel.add(Box.createVerticalStrut(130));
		
		JRadioButton radioAllList = new JRadioButton("전체 일정보기");
		radioAllList.setOpaque(false);
		radioAllList.setHorizontalAlignment(SwingConstants.CENTER);
		radioAllList.setFont(new Font("나눔바른고딕", Font.PLAIN, 16));
		radioAllList.setMaximumSize(new Dimension(250, 50));
		radioAllList.setSelected(true);
		radioAllList.setActionCommand("allList");
		radioAllList.addActionListener(new RadioChangeActionListener());
		
		JRadioButton radioOnlyImport = new JRadioButton("중요 일정보기");
		radioOnlyImport.setOpaque(false);
		radioOnlyImport.setFont(new Font("나눔바른고딕", Font.PLAIN, 16));
		radioOnlyImport.setHorizontalAlignment(SwingConstants.CENTER);
		radioOnlyImport.setMaximumSize(new Dimension(250, 50));
		radioOnlyImport.setActionCommand("onlyImport");
		radioOnlyImport.addActionListener(new RadioChangeActionListener());
		
		ButtonGroup group = new ButtonGroup();
		group.add(radioAllList);
		group.add(radioOnlyImport);
		
		JPanel radioPanel = new JPanel();
		radioPanel.setMaximumSize(new Dimension(250, 100));
		radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.Y_AXIS));
		radioPanel.setBackground(new Color(249, 249, 249));
		radioPanel.setBorder(BorderFactory.createLineBorder(new Color(166, 166, 166), 1, true));

		radioPanel.add(radioAllList);
		radioPanel.add(radioOnlyImport);
		
		Box box = Box.createHorizontalBox();
		box.setAlignmentX(0);
		box.setMaximumSize(new Dimension(405, 200));
		box.add(Box.createHorizontalStrut(80));
		box.add(radioPanel);
		menuPanel.add(box);
		rightOnPanel.add(menuPanel, BorderLayout.CENTER);
		c.add(rightOnPanel, BorderLayout.EAST);
	}
	
	/* 프레임 기본 설정 */
	private void init() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("일정 관리 | 메인");
		
		// 화면 크기 구하기
		Toolkit tk = getToolkit();
		Dimension dimension = tk.getScreenSize();
		setSize(dimension.width+20, dimension.height-45); // 전체화면
		setLocation(-10, 0);
		
		setVisible(true);
	}
	
	/* 날짜 관련 기본 설정 */
	private void calInit() {
		sdf = new SimpleDateFormat ("yyyy-MM-dd");
		
		calendar = Calendar.getInstance();
		curYear = calendar.get(Calendar.YEAR);
		curMonth = calendar.get(Calendar.MONTH) + 1;
		curDay = calendar.get(Calendar.DATE);
		
		selMonth = curMonth;
		calendar.set(Calendar.DATE, 1);
	}
	
	/* 요일 생성 */
	private void createWeek() {
		JPanel weekPanel = new JPanel();
		weekPanel.setLayout(new BoxLayout(weekPanel, BoxLayout.X_AXIS));
		
		weekPanel.add(Box.createHorizontalStrut(2));
		for(int i=0; i<weeks.length; i++) {
			JLabel lblWeek = new JLabel(weeks[i]);
			lblWeek.setHorizontalAlignment(SwingConstants.CENTER);
			lblWeek.setOpaque(true);
			lblWeek.setBackground(new Color(217, 229, 255));
			lblWeek.setBorder(new LineBorder(Color.BLACK));
			lblWeek.setMaximumSize(new Dimension(320, 320));
			lblWeek.setPreferredSize(new Dimension(0, 45));
			if(i == 0) lblWeek.setForeground(Color.RED);
			if(i == weeks.length-1) lblWeek.setForeground(Color.BLUE);
			
			weekPanel.add(lblWeek);
			if(i != weeks.length-1)
				weekPanel.add(Box.createHorizontalStrut(10));
		}
		mainPanel.add(weekPanel, BorderLayout.NORTH);
	}
	
	/* 달력 생성 */
	private void createCalendar(int month, boolean isAllList) {
		mainPanel.removeAll();
		createWeek();
		count = 0;
		
		dayPanel = new JPanel();
		dayPanel.setLayout(new GridLayout(0, 7, 10, 10));
		calendar.set(Calendar.DATE, 1);
		int startWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1; // 시작 요일
		int endDay = calendar.getActualMaximum(Calendar.DATE); // 마지막 일

		Calendar prevCal = Calendar.getInstance();
		prevCal.set(Calendar.MONTH, month-2);
		int prevEndDay = prevCal.getActualMaximum(Calendar.DATE); // 이전달 마지막일
		int prevDate = prevEndDay - (startWeek - 1); // 이전달 시작일
		int nextDate = 1; // 다음달 시작일
		
		scheduleList = memberService.getScheduleList(loginUser);
		for(int i=1; i<=42-startWeek; i++) {
			Calendar current = Calendar.getInstance();
			if(i==1) {
				for(int j=1; j<=startWeek; j++){
					current.set(curYear, month-2, prevDate++);
					settingDate(current, false);
				}
			}
			if(i<=endDay){
				current.set(curYear, month-1, i);
				settingDate(current, true);
			}
			else {
				current.set(curYear-1, month, nextDate++);
				settingDate(current, false);
			}
		}
		mainPanel.add(dayPanel, BorderLayout.CENTER);
		
		lblDate.setText(curYear + "년 " + selMonth + "월");
	}
	
	/* 해당 일자의 내용 세팅 */
	private void settingDate(Calendar current, boolean enabled) {
		count ++;
		
		JPanel memoPanel = new JPanel();
		memoPanel.setLayout(new BoxLayout(memoPanel, BoxLayout.Y_AXIS));
		memoPanel.setBackground(new Color(247, 250, 255));
		memoPanel.setBorder(new LineBorder(new Color(200, 200, 200)));
		memoPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		if(!enabled) memoPanel.setBackground(new Color(240, 240, 240));
		
		JLabel lblDay = new JLabel(current.get(Calendar.DATE) + "");
		lblDay.setMaximumSize(new Dimension(35, 35));
		lblDay.setHorizontalAlignment(SwingConstants.CENTER);
		lblDay.setFont(new Font("나눔바른고딕", Font.PLAIN, 14));
		
		if(count % 7 == 1) lblDay.setForeground(Color.RED);
		if(count % 7 == 0) lblDay.setForeground(Color.BLUE);
		
		memoPanel.add(lblDay);
		memoPanel.add(Box.createVerticalStrut(2));
		
		Iterator<ScheduleVO> itr = scheduleList.iterator();
		while(itr.hasNext()) {
			ScheduleVO schedule = itr.next();
			Timestamp schedule_date = schedule.getSchedule_date();
			String date = sdf.format(schedule_date);
			String current_date = sdf.format(current.getTime());
			
			if(!isAllList) {
				char import_yn = schedule.getSchedule_import_yn();
				if(import_yn != 'Y') continue;
			}
			
			if(date.equals(current_date)){
				Box box = Box.createHorizontalBox();
				box.setAlignmentX(0);
				box.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
				
				if(schedule.getSchedule_update_yn() == 'Y')
					url = "http://nscompany.speedgabia.com/images/pin_blue.png";
				else
					url = "http://nscompany.speedgabia.com/images/pin.png";
				JLabel lblIcon = new JLabel();
				lblIcon.setIcon(new ImageIcon(ImageUtil.getImage(url)));
				lblIcon.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
				
				JLabel lblTitle = new JLabel();
				lblTitle.setText(schedule.getSchedule_title());
				lblTitle.setFont(new Font("맑은고딕", Font.BOLD, 13));
				
				box.add(lblIcon);
				box.add(lblTitle);
				
				if(schedule.getSchedule_import_yn() == 'Y') {
					url = "http://nscompany.speedgabia.com/images/star_small_on.png";
					
					JLabel lblImportIcon = new JLabel();
					lblImportIcon.setIcon(new ImageIcon(ImageUtil.getImage(url)));
					lblImportIcon.setBorder(BorderFactory.createEmptyBorder(0, 7, 0, 0));
					box.add(lblImportIcon);
				}
				
				memoPanel.add(box);
				memoPanel.add(Box.createVerticalStrut(2));
			}
		}
		memoPanel.setToolTipText(sdf.format(current.getTime()));
		memoPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JPanel panel = (JPanel) e.getSource();
				ScheduleBasicView basicView = new ScheduleBasicView();
				basicView.setMainView(MainView.this);
				basicView.setLoginUser(loginUser);
				basicView.setSelDate(panel.getToolTipText());
			}
		});
		dayPanel.add(memoPanel);
	}
	
	public void update() {
		createCalendar(selMonth, isAllList); // 달력 생성
		mainPanel.invalidate(); // 달력 화면 업데이트
		mainPanel.revalidate();
		mainPanel.repaint();
	}
	
	class MonthChangeActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("before")) // 이전 버튼을 눌렀을 경우 
				selMonth -= 1; // month 를 -1 
			else if(e.getActionCommand().equals("after")) // 이후 버튼을 눌렀을 경우
				selMonth += 1; // month 를 +1
			
			// 1 ~ 12 사이가 되게 변경
			if(selMonth > 12) 
				selMonth = selMonth - 12;
			else if(selMonth < 1) 
				selMonth = selMonth + 12;
			
			calendar.set(Calendar.MONTH, selMonth-1);
			update(); // 달력 화면 업데이트
		}
	}
	
	class RadioChangeActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("allList")) 
				isAllList = true;
			else if(e.getActionCommand().equals("onlyImport"))
				isAllList = false;
			
			update();
		}
	}
}
