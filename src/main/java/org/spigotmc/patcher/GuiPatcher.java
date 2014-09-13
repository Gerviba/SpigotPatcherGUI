package org.spigotmc.patcher;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;

import javax.swing.AbstractListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.LineBorder;

import net.md_5.jbeat.Patcher;

import com.google.common.hash.Hashing;
import com.google.common.io.Files;

/**
 * GuiPatcher Class
 * @author Gerviba
 */
public class GuiPatcher extends JFrame {

	//Default URL-s:
	private static final String DOWNLOAD_URL = "https://raw.githubusercontent.com/Gerviba/SpigotPatcherGUI/master/DOWNLOADS.txt";
	private static final String VERSION_URL = "https://raw.githubusercontent.com/Gerviba/SpigotPatcherGUI/master/VERSION.txt";
	private static final String SPIGOT_URL = "http://spigotmc.org/";
	private static final String BUG_REPORT = "http://bug.url.org/";
	
	//Downloadable File's map:
	private static final HashMap<String, String> DownloadableItems = new HashMap<String, String>();
	//Files:
	private static File original = null, bps = null, result = null;
	//Version String:
	private static String newest_version = "N/A";
	
	JFileChooser fc;
	
	private static final long serialVersionUID = 1525215042060278658L;
	private JPanel contentPane;
	private JTextField OriginalBrowseText;
	private JTextField BpsBrowseText;
	private JTextField ResultBrowseText;
	private JTextField HashText;

	/**
	 * Init the application
	 */
	protected static void init() {
		try {
			
			BufferedReader bufferedreader = null;
			try {
		        bufferedreader = new BufferedReader(new InputStreamReader(new URL(DOWNLOAD_URL).openStream()));
		        String output;
		        
		        while((output = bufferedreader.readLine()) != null) {
		        	if(output.split("[;]").length == 2)
		        		DownloadableItems.put(output.split("[;]")[0], output.split("[;]")[1]);
		        }
		        
		        bufferedreader.close();
		        bufferedreader = new BufferedReader(new InputStreamReader(new URL(VERSION_URL).openStream()));
		        
		        if((output = bufferedreader.readLine()) != null) {
		        	newest_version = "v"+output;
		        	if(!newest_version.equals("v1.0.6"))
		        		JOptionPane.showMessageDialog(null, LanguageHelper.getLang().INFO_NEW_VERSION, "Spigot Patcher", JOptionPane.WARNING_MESSAGE);
		        }
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				if(bufferedreader != null) bufferedreader.close();
			}
			
			GuiPatcher frame = new GuiPatcher();
			frame.setVisible(true);
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Failed to launch!\nError:\n"+e.getMessage(), "Spigot Patcher", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
	}

	/**
	 * Setting up the screen
	 */
	@SuppressWarnings("serial")
	private GuiPatcher() {
		
		//Setup Look'n'Feel
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(GuiPatcher.class.getResource("/resources/spigot_icon.png")));
		setTitle("Spigot Patcher v1.0.6");
		setType(Type.POPUP);
		setResizable(false);
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            UIManager.put("nimbusBase", new Color(0xEEEEEE));
		            UIManager.put("nimbusBlueGrey", new Color(0xDDDDDD));
		            UIManager.put("control", new Color(0xDDDDDD));
		            UIManager.put("nimbusSelection", new Color(0xDDDDDD));
		            UIManager.put("nimbusSelectionBackground", new Color(0xff9c2b));
		            UIManager.put("nimbusOrange", new Color(0xff9c2b));
		            UIManager.put("nimbusFocus", new Color(0xe58f2d));
		            System.out.println("Look'n'Feel set!");
		            fc = new JFileChooser();
		            break;
		        }
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Setup the GUI
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds((int) ((Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 600) / 2), (int) ((Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 438) / 2), 600, 438);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setForeground(Color.BLACK);
		contentPane.setBorder(UIManager.getBorder("TextField.border"));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel VersionTitle = new JLabel(LanguageHelper.getLang().LABEL_VERSION);
		VersionTitle.setHorizontalAlignment(SwingConstants.LEFT);
		VersionTitle.setBounds(437, 11, 89, 14);
		contentPane.add(VersionTitle);
		
		JLabel VersionLabel = new JLabel("v1.0.6");
		VersionLabel.setForeground(Color.DARK_GRAY);
		VersionLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		VersionLabel.setBounds(493, 11, 89, 14);
		contentPane.add(VersionLabel);
		
		JLabel NewestVersionTitle = new JLabel(LanguageHelper.getLang().LABEL_NEWEST_VERSION);
		NewestVersionTitle.setHorizontalAlignment(SwingConstants.LEFT);
		NewestVersionTitle.setBounds(437, 27, 89, 14);
		contentPane.add(NewestVersionTitle);
				
		JLabel NewestVersionLable = new JLabel(newest_version);
		NewestVersionLable.setForeground(Color.DARK_GRAY);
		NewestVersionLable.setHorizontalAlignment(SwingConstants.RIGHT);
		NewestVersionLable.setBounds(493, 27, 89, 14);
		contentPane.add(NewestVersionLable);
		
		JLabel LanguageTitle = new JLabel(LanguageHelper.getLang().LABEL_LANGUAGE);
		LanguageTitle.setBounds(437, 59, 89, 14);
		contentPane.add(LanguageTitle);
		
		JLabel LanguageLabel = new JLabel(LanguageHelper.getLang().THIS_LANG);
		LanguageLabel.setForeground(Color.DARK_GRAY);
		LanguageLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		LanguageLabel.setBounds(493, 59, 89, 14);
		contentPane.add(LanguageLabel);
		
		JLabel OriginalTitle = new JLabel(LanguageHelper.getLang().LABEL_ORIGINAL_FILE);
		OriginalTitle.setBounds(10, 111, 288, 14);
		contentPane.add(OriginalTitle);
		
		final JLabel OriginalChecksum = new JLabel(LanguageHelper.getLang().LABEL_CHECKSUM+(original != null ? hash(original) : ""));
		OriginalChecksum.setForeground(Color.DARK_GRAY);
		OriginalChecksum.setBounds(20, 155, 370, 14);
		contentPane.add(OriginalChecksum);
		
		OriginalBrowseText = new JTextField();
		OriginalBrowseText.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
		        if(fc.showOpenDialog(GuiPatcher.this) == JFileChooser.APPROVE_OPTION && fc.getSelectedFile() != null && fc.getSelectedFile().exists() && fc.getSelectedFile().canRead()) {
		            original = fc.getSelectedFile();
		            OriginalBrowseText.setText(fc.getSelectedFile().getAbsolutePath());
		            OriginalChecksum.setText(LanguageHelper.getLang().LABEL_CHECKSUM+(original != null ? hash(original) : ""));
		        } else {
		        	JOptionPane.showMessageDialog(null, LanguageHelper.getLang().INFO_INVALID_FILE, LanguageHelper.getLang().TITLE_OPEN_FAILED, JOptionPane.ERROR_MESSAGE);
		        }
			}
		});
		OriginalBrowseText.setText("C:/");
		OriginalBrowseText.setEditable(false);
		OriginalBrowseText.setForeground(Color.LIGHT_GRAY);
		OriginalBrowseText.setBounds(10, 125, 288, 26);
		contentPane.add(OriginalBrowseText);
		OriginalBrowseText.setColumns(10);
		
		JButton OriginalBrowseButton = new JButton(LanguageHelper.getLang().BUTTON_BROWSE);
		OriginalBrowseButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
		        if(fc.showOpenDialog(GuiPatcher.this) == JFileChooser.APPROVE_OPTION && fc.getSelectedFile() != null && fc.getSelectedFile().exists() && fc.getSelectedFile().canRead()) {
		            original = fc.getSelectedFile();
		            OriginalBrowseText.setText(fc.getSelectedFile().getAbsolutePath());
		            OriginalChecksum.setText(LanguageHelper.getLang().LABEL_CHECKSUM+(original != null ? hash(original) : ""));
		        } else {
		        	JOptionPane.showMessageDialog(null, LanguageHelper.getLang().INFO_INVALID_FILE, LanguageHelper.getLang().TITLE_OPEN_FAILED, JOptionPane.ERROR_MESSAGE);
		        }
			}
		});
		OriginalBrowseButton.setBounds(308, 127, 82, 23);
		OriginalBrowseButton.setBackground(new Color(0xCCCCCC));
		contentPane.add(OriginalBrowseButton);
		
		JLabel BpsTitle = new JLabel(LanguageHelper.getLang().LABEL_BPS_FILE);
		BpsTitle.setBounds(10, 180, 288, 14);
		contentPane.add(BpsTitle);
		
		final JLabel BpsChecksum = new JLabel(LanguageHelper.getLang().LABEL_CHECKSUM+(bps != null ? hash(bps) : ""));
		BpsChecksum.setForeground(Color.DARK_GRAY);
		BpsChecksum.setBounds(20, 224, 370, 14);
		contentPane.add(BpsChecksum);
		
		BpsBrowseText = new JTextField();
		BpsBrowseText.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(fc.showOpenDialog(GuiPatcher.this) == JFileChooser.APPROVE_OPTION && fc.getSelectedFile() != null && fc.getSelectedFile().exists() && fc.getSelectedFile().canRead()) {
		            bps = fc.getSelectedFile();
		            BpsBrowseText.setText(fc.getSelectedFile().getAbsolutePath());
		            BpsChecksum.setText(LanguageHelper.getLang().LABEL_CHECKSUM+(bps != null ? hash(bps) : ""));
		        } else {
		        	JOptionPane.showMessageDialog(null, LanguageHelper.getLang().INFO_INVALID_FILE, LanguageHelper.getLang().TITLE_OPEN_FAILED, JOptionPane.ERROR_MESSAGE);
		        }
			}
		});
		BpsBrowseText.setText("C:/");
		BpsBrowseText.setEditable(false);
		BpsBrowseText.setForeground(Color.LIGHT_GRAY);
		BpsBrowseText.setColumns(10);
		BpsBrowseText.setBounds(10, 194, 288, 26);
		contentPane.add(BpsBrowseText);
		
		JButton BpsBrowseButton = new JButton(LanguageHelper.getLang().BUTTON_BROWSE);
		BpsBrowseButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(fc.showOpenDialog(GuiPatcher.this) == JFileChooser.APPROVE_OPTION && fc.getSelectedFile() != null && fc.getSelectedFile().exists() && fc.getSelectedFile().canRead()) {
		            bps = fc.getSelectedFile();
		            BpsBrowseText.setText(fc.getSelectedFile().getAbsolutePath());
		            BpsChecksum.setText(LanguageHelper.getLang().LABEL_CHECKSUM+(bps != null ? hash(bps) : ""));
		        } else {
		        	JOptionPane.showMessageDialog(null, LanguageHelper.getLang().INFO_INVALID_FILE, LanguageHelper.getLang().TITLE_OPEN_FAILED, JOptionPane.ERROR_MESSAGE);
		        }
			}
		});
		BpsBrowseButton.setBounds(308, 196, 82, 23);
		BpsBrowseButton.setBackground(new Color(0xCCCCCC));
		contentPane.add(BpsBrowseButton);
		
		final JLabel ResultTitle = new JLabel(LanguageHelper.getLang().LABEL_RESULT_FILE);
		ResultTitle.setBounds(10, 249, 288, 14);
		contentPane.add(ResultTitle);
		
		ResultBrowseText = new JTextField();
		ResultBrowseText.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(fc.showSaveDialog(GuiPatcher.this) == JFileChooser.APPROVE_OPTION) {
		            result = fc.getSelectedFile();
		            ResultTitle.setText(fc.getSelectedFile().getAbsolutePath());
		        }
			}
		});
		ResultBrowseText.setText("C:/Spigot/Spigot.jar");
		ResultBrowseText.setEditable(false);
		ResultBrowseText.setForeground(Color.LIGHT_GRAY);
		ResultBrowseText.setColumns(10);
		ResultBrowseText.setBounds(10, 263, 288, 26);
		contentPane.add(ResultBrowseText);
		
		JButton ResultBrowseButton = new JButton(LanguageHelper.getLang().BUTTON_BROWSE);
		ResultBrowseButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(fc.showSaveDialog(GuiPatcher.this) == JFileChooser.APPROVE_OPTION) {
		            result = fc.getSelectedFile();
		            ResultTitle.setText(fc.getSelectedFile().getAbsolutePath());
		        }
			}
		});
		ResultBrowseButton.setBounds(308, 265, 82, 23);
		ResultBrowseButton.setBackground(new Color(0xCCCCCC));
		contentPane.add(ResultBrowseButton);
		
		final JCheckBox CheckingEnabled = new JCheckBox(LanguageHelper.getLang().LABEL_CHECK_CHECKSUM);
		CheckingEnabled.setBackground(Color.WHITE);
		CheckingEnabled.setBounds(10, 296, 292, 23);
		contentPane.add(CheckingEnabled);
		
		HashText = new JTextField();
		HashText.setForeground(Color.GRAY);
		HashText.setText(LanguageHelper.getLang().LABEL_ENTER_HASH);
		HashText.setColumns(10);
		HashText.setBounds(10, 314, 288, 26);
		contentPane.add(HashText);
		
		JButton OpenWebsite = new JButton(LanguageHelper.getLang().BUTTON_OPEN_SITE);
		OpenWebsite.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					if(Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(java.awt.Desktop.Action.BROWSE))
						Desktop.getDesktop().browse(new URL(SPIGOT_URL).toURI());
				} catch(Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		OpenWebsite.setBackground(new Color(0xBBBBBB));
		OpenWebsite.setBounds(170, 351, 220, 23);
		contentPane.add(OpenWebsite);
		
		JButton ReportBUG = new JButton(LanguageHelper.getLang().BUTTON_REPORT);
		ReportBUG.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					if(Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(java.awt.Desktop.Action.BROWSE))
						Desktop.getDesktop().browse(new URL(BUG_REPORT).toURI());
				} catch(Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		ReportBUG.setBackground(new Color(0xBBBBBB));
		ReportBUG.setBounds(170, 377, 129, 23);
		contentPane.add(ReportBUG);
		
		JButton InfoButton = new JButton(LanguageHelper.getLang().LABEL_INFO);
		InfoButton.setBackground(new Color(0xBBBBBB));
		InfoButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				JOptionPane.showMessageDialog(null, "The Spigot Patcher GUI is created by: Gerviba\n\nThe Spigot Patcher is created by the Spigot Dev Team.\nCheck out their site: http://spigotmc.org/", "Spigot Patcher", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		InfoButton.setBounds(301, 377, 89, 23);
		contentPane.add(InfoButton);

		final JButton PATCH = new JButton(LanguageHelper.getLang().BUTTON_PATCH);
		PATCH.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				PATCH.setText(LanguageHelper.getLang().BUTTON_PATCHING);
				PATCH.setEnabled(false);
				
				if(original == null || bps == null || result == null) {
					JOptionPane.showMessageDialog(null, LanguageHelper.getLang().INFO_INCORRECT_INPUT, "Spigot Patcher", JOptionPane.ERROR_MESSAGE);
					PATCH.setEnabled(true);
					PATCH.setText(LanguageHelper.getLang().BUTTON_PATCH);
					return;
				}
				
		        try {
		        	if(!result.createNewFile()) {
			        	JOptionPane.showMessageDialog(null, LanguageHelper.getLang().INFO_CANNOT_CREATE, "Spigot Patcher", JOptionPane.ERROR_MESSAGE);
						PATCH.setEnabled(true);
						PATCH.setText(LanguageHelper.getLang().BUTTON_PATCH);
						return;
			        }
		        	
		            new Patcher(bps, original, result).patch();
		            System.out.println("Success!");
		            
		            if(CheckingEnabled.isSelected()) {
		            	JOptionPane.showMessageDialog(null, LanguageHelper.getLang().INFO_SUCCESS+"\r"+hash(result)+" <=["+(hash(result).equalsIgnoreCase(HashText.getText()) ? "OK" : LanguageHelper.getLang().DIFFERENT)+"]=> "+HashText.getText(), "Spigot Patcher", JOptionPane.ERROR_MESSAGE);
		            } else {
		            	JOptionPane.showMessageDialog(null, LanguageHelper.getLang().INFO_SUCCESS, "Spigot Patcher", JOptionPane.ERROR_MESSAGE);
		            }
		            	
		        } catch ( Exception ex ) {
		        	JOptionPane.showMessageDialog(null, LanguageHelper.getLang().INFO_ERROR+ex.getMessage(), "Spigot Patcher", JOptionPane.ERROR_MESSAGE);
		            ex.printStackTrace();
		            result.delete();
		            return;
		        }
				
				PATCH.setEnabled(true);
				PATCH.setText(LanguageHelper.getLang().BUTTON_PATCH);
			}
		});
		PATCH.setBackground(new Color(0xd88934));
		PATCH.setBounds(10, 351, 150, 49);
		contentPane.add(PATCH);
		
		JLabel LOGO = new JLabel("");
		LOGO.setBounds(0, 0, 600, 105);
		contentPane.add(LOGO);
		LOGO.setIcon(new ImageIcon(GuiPatcher.class.getResource("/resources/spigot_upperbar.png")));
		
		JPanel DownloadPanel = new JPanel();
		DownloadPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		DownloadPanel.setBounds(402, 103, 193, 308);
		contentPane.add(DownloadPanel);
		DownloadPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel NorthPanel = new JPanel();
		DownloadPanel.add(NorthPanel, BorderLayout.NORTH);
		
		JLabel DownloadLabel = new JLabel(LanguageHelper.getLang().LABEL_DOWNLOADS);
		NorthPanel.add(DownloadLabel);
		
		JPanel ContentPanel = new JPanel();
		DownloadPanel.add(ContentPanel, BorderLayout.CENTER);
		ContentPanel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane Scrolling = new JScrollPane();
		ContentPanel.add(Scrolling, BorderLayout.CENTER);
		
		final JList<String> DownloadList = new JList<String>();
		DownloadList.setModel(new AbstractListModel<String>() {
			String[] values = (String[]) DownloadableItems.keySet().toArray(new String[DownloadableItems.keySet().size()]);
			public int getSize() {
				return values.length;
			}
			public String getElementAt(int index) {
				return values[index];
			}
		});
		Scrolling.setViewportView(DownloadList);
		
		JButton DownloadButton = new JButton(LanguageHelper.getLang().LABEL_DOWNLOAD);
		DownloadButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(DownloadList.getSelectedValue() == null || DownloadableItems.get(DownloadList.getSelectedValue()) == null) {
					JOptionPane.showMessageDialog(null, LanguageHelper.getLang().INFO_SELECT_AN_ITEM, LanguageHelper.getLang().TITLE_DOWNLOAD_FAILED, JOptionPane.ERROR_MESSAGE);
					return;
				}
				BufferedInputStream in = null;
				ByteArrayOutputStream out = null;
				try {
					in = new BufferedInputStream(new URL(DownloadableItems.get(DownloadList.getSelectedValue())).openStream());
			        if (fc.showSaveDialog(GuiPatcher.this) == JFileChooser.APPROVE_OPTION) {
			             out = new ByteArrayOutputStream();
				   		 byte[] buf = new byte[1024];
				   		 int n = 0;
				   		 while(-1!=(n=in.read(buf)))
				   		    out.write(buf, 0, n);
				   		 byte[] response = out.toByteArray();
				    
				   		 FileOutputStream fos = new FileOutputStream(fc.getSelectedFile().getAbsolutePath());
				   		 fos.write(response);
				   		 fos.close();
				   		 System.out.println("Download success!");
			        }
				} catch(Exception e) {
					JOptionPane.showMessageDialog(null, LanguageHelper.getLang().INFO_ERROR+e.getMessage(), LanguageHelper.getLang().TITLE_DOWNLOAD_FAILED, JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				} finally {
		            if (in != null) {
		                try { in.close(); } catch (IOException e) {}
		            }
		            if (out != null) {
		            	try { out.close(); } catch (IOException e) {}
		            }
		        }
			}
		});
		
		ContentPanel.setBackground(new Color(0xBBBBBB));
		ContentPanel.add(DownloadButton, BorderLayout.SOUTH);
		System.out.println("Rendering GUI is completed!");
	}
	
	/**
	 * Hash the file
	 * @param file
	 * @return The md5 hash of the file
	 */
	private static String hash(File file) {
		try {
			return Files.hash( file, Hashing.md5() ).toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
}
