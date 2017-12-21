package com.calendar.adapter;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/* WindowListener를 구현한 WindowAdapter 클래스 */
public class WindowAdapter implements WindowListener{

	@Override
	public void windowOpened(WindowEvent e) {}

	@Override
	public void windowClosing(WindowEvent e) {}

	@Override
	public void windowClosed(WindowEvent e) {}

	@Override
	public void windowIconified(WindowEvent e) {}

	@Override
	public void windowDeiconified(WindowEvent e) {}

	
	@Override
	public void windowActivated(WindowEvent e) {}

	@Override
	public void windowDeactivated(WindowEvent e) {}
}
