package com.cruty.parse60.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.TextArea;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.cruty.parse60.common.ConfigInfo;

public class MainFrame extends JFrame {
	private JTabbedPane tabPane;
	private JPanel normalPacket;
	private JPanel normalPacket2;
	private JPanel normalPacket3;	
	private JTabbedPane tabPane2;
	private JTabbedPane tabPane3;
	private JTextField content;
	private JTextField length;
	private TextArea parseResult;
	private ConfigInfo cfg = ConfigInfo.getInstance();
	
	public MainFrame() {
		super("CUPS报文解释工具");
		Container container = this.getContentPane();
		/**初始化窗口相关参数*/
		{
			//设置关闭窗口时，自动关闭进程
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			//获取系统分辨率
			double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
			double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
			//设置大小
			this.setSize((int)width/2,(int)height/2);
			//设置显示位置
			this.setLocation((int)width/4,(int)height/4);
			//禁用最大化按钮
			//this.setResizable(false);
		}
		
		/**初始化相关组件*/
		{
			tabPane = new JTabbedPane();
			tabPane2 = new JTabbedPane();
			container.add(tabPane, BorderLayout.CENTER);
			normalPacket = new JPanel();
			normalPacket2 = new JPanel();
			normalPacket3 = new JPanel();
			tabPane.addTab("F60报文解析", null, normalPacket, null);
			tabPane.addTab("F48用法解析", null, normalPacket3, null);
			tabPane.addTab("关于", null,normalPacket2,null);
			JPanel tabnp = new JPanel();
			tabnp.setLayout(null);
			normalPacket.add(tabnp);
			tabnp.setPreferredSize(new java.awt.Dimension(900, 800));
			tabnp.setVerifyInputWhenFocusTarget(false);
			tabnp.setRequestFocusEnabled(false);
			tabnp.setFocusable(false);
			tabnp.setFocusTraversalKeysEnabled(false);
			tabnp.setOpaque(false);

			JLabel serverIpLabel = new JLabel("报文内容：");
			serverIpLabel.setBounds(10, 17, 94, 15);
			tabnp.add(serverIpLabel);
			content = new JTextField("", 60);
			content.setBounds(73, 12, 160, 25);
			content.getDocument().addDocumentListener(new DocumentListener() {
				public void removeUpdate(DocumentEvent e) {
					contentAction();
				}

				public void insertUpdate(DocumentEvent e) {
					contentAction();
				}

				public void changedUpdate(DocumentEvent e) {
					contentAction();
				}
			});
			tabnp.add(content);
			
			JLabel serverPortLabel = new JLabel("报文长度：");
			serverPortLabel.setBounds(280, 17, 104, 15);
			tabnp.add(serverPortLabel);
			length = new JTextField("", 5);
			length.setBounds(350, 12, 50, 25);
			length.getDocument().addDocumentListener(
					new DocumentListener() {
						public void removeUpdate(DocumentEvent e) {
						}

						public void insertUpdate(DocumentEvent e) {
						}

						public void changedUpdate(DocumentEvent e) {
						}
					});
			tabnp.add(length);
			
			
			JLabel parseResultLabel = new JLabel("解析結果:");
			parseResultLabel.setBounds(10, 73, 55, 52);
			tabnp.add(parseResultLabel);
			
			parseResult = new TextArea(null, 720,381,
					TextArea.SCROLLBARS_VERTICAL_ONLY);
			parseResult.setBounds(73, 65, 720, 381);
//			parseResult.setDropTarget(new DropTarget(this,
//					DnDConstants.ACTION_REFERENCE, new DropTargetAdapter() {
//						public void drop(DropTargetDropEvent event) {
//							
//						}
//					}, true));
			tabnp.add(parseResult);
		}
		
	}
	public void normalPacket3(){
		JLabel serverIpLabel = new JLabel("报文内容：");

	}
	public void contentAction(){
		
		String input = content.getText().trim();
		String regex = "[0-9]*";
		if(input.matches(regex)&&input.length()>30){
			this.length.setText(String.valueOf(input.length()-3));
			String message = input.substring(3);
			this.parseResult.setText(parse60(message));
		}
		if(!input.matches(regex)){
			this.parseResult.setText("输入的60域报文格式不正确，请重新输入！");
		}
		
	}

	private String parse60(String message) {
		StringBuffer sb = new StringBuffer();
		String[] begin = cfg.getValue("60.1_location").split("\\|");
		String beginKey = "60.1";
		int beginIndex = 0;
		int endIndex = 0;
		while(true){
			
			endIndex+=Integer.parseInt(begin[0]);
			sb.append(beginKey+"(").append(cfg.getValue(beginKey+"_name")).append(")=").append(message.substring(beginIndex, endIndex)).append("("+cfg.getValue(beginKey+"."+message.substring(beginIndex, endIndex)+"_name")+")").append("\n");
			if(begin[1].equals("end")){
				break;
			}
			beginKey = begin[1];
			beginIndex+=Integer.parseInt(begin[0]);
			begin= cfg.getValue(beginKey+"_location").split("\\|");
			sb.append("\n");
		}
		
		return sb.toString();
	}
	
}
