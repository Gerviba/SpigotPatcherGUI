package org.spigotmc.patcher;

import java.util.ArrayList;
import java.util.List;

/**
 * Language Helper Class
 * @author Gerviba
 */
public class LanguageHelper {

	//Installed Languages:
	public static List<LanguageHelper> Langs = new ArrayList<LanguageHelper>();
	//Selected Language:
	private static LanguageHelper instance;
	
	//Auto-Init: (in first use)
	static {
		Langs.add(instance = new LanguageHelper());
		Langs.add(new LanguageHelper("hu",
				"Magyar",
				"Tallózás",
				"Csomagold",
				"Csomagolás",
				"Megnyit: http://spigotmc.org/",
				"Hiba bejelentése",
				"Letöltés",
				"Az eredeti jar fájl: (original jar)",
				"A .bps fájl:",
				"Készítendő fájl:",
				"Ellenőrizze az elkészült jart",
				"Checksum: ",
				"Letöltés",
				"Letöltések",
				"Jelenlegi verzió:",
				"Legújabb verzió:",
				"Nyelv:",
				"Írd be az elkészült fájl checksumját",
				"Infó",
				"A fájl nem megfelelő, vagy bezártad a párbeszédablakot!",
				"Kérlek válassz ki egy sort a \"Letöltések\" panelről.",
				"Egy hiba történt!\nHiba: ",
				"Új verzió elérhető!\nTöltsd le a weblapról!",
				"Be kell állítsad mind a három fájlt!",
				"Nem lehet elkészíteni a fájlt! Kérlek ellenőrizd!",
				"A fájl sikeresen létrejött!",
				"Megnyitás sikertelen!",
				"Letöltés sikertelen!",
				"Különböző"));
		//TODO: Insert another languages here...
		
		//Detect computer's language:
		if(!System.getProperty("language", "_").equals("en")) {
			for(LanguageHelper lh : Langs)
				if(System.getProperty("user.language").equals(lh.LANG_ID)) {
					instance = lh;
					System.out.println("Computer language is detected! ("+lh.THIS_LANG+")");
					break;
				}
			for(LanguageHelper lh : Langs)
				if(System.getProperty("language", "en").equalsIgnoreCase(lh.LANG_ID)) {
					instance = lh;
					System.out.println("Language set (using JVM arguments)! ("+lh.THIS_LANG+")");
					break;
				}
		} else {
			System.out.println("Language set (using JVM arguments)! (English)");
		}
	}

	//
	public String LANG_ID = "en";
	public String THIS_LANG = "English";

	public String BUTTON_BROWSE = "Browse";
	public String BUTTON_PATCH = "Patch it";
	public String BUTTON_PATCHING = "Patching...";
	public String BUTTON_OPEN_SITE = "Open: http://spigotmc.org/";
	public String BUTTON_REPORT = "Report a BUG";
	public String BUTTON_DOWNLOAD = "Download selected";
	
	public String LABEL_ORIGINAL_FILE = "The original jar file:";
	public String LABEL_BPS_FILE = "The .bps file:";
	public String LABEL_RESULT_FILE = "The output file:";
	public String LABEL_CHECK_CHECKSUM = "Check the resultant jar";
	public String LABEL_CHECKSUM = "Checksum: ";
	public String LABEL_DOWNLOAD = "Download";
	public String LABEL_DOWNLOADS = "Downloads";
	public String LABEL_VERSION = "Current version:";
	public String LABEL_NEWEST_VERSION = "Newest version:";
	public String LABEL_LANGUAGE = "Language:";
	public String LABEL_ENTER_HASH = "Enter the resultant jar's checksum";
	public String LABEL_INFO = "Info";
	
	public String INFO_INVALID_FILE = "The file is incorrect or the dialog closed!";
	public String INFO_SELECT_AN_ITEM = "Please, select a line from the 'Downloads' panel.";
	public String INFO_ERROR = "An error occurred!\nError: ";
	public String INFO_NEW_VERSION = "New version is available!\nYou can download it from the website!";
	public String INFO_INCORRECT_INPUT = "You must set the three files!";
	public String INFO_CANNOT_CREATE = "Could not create specified output file.\nPlease ensure that it is in a valid directory which can be written to.";
	public String INFO_SUCCESS = "The file was successfully created!";
	
	public String TITLE_OPEN_FAILED = "Failed to open!";
	public String TITLE_DOWNLOAD_FAILED = "Failed to download!";
	public String DIFFERENT = "DIFFERENT";
	
	/**
	 * Default Language constructor
	 */
	private LanguageHelper() {
		System.out.println("An empty language initialized! (with default values)");
	}
	
	/**
	 * Custom Language constructor
	 * @param LANG_ID
	 * @param THIS_LANG
	 * @param BUTTON_BROWSE
	 * @param BUTTON_PATCH
	 * @param BUTTON_PATCHING
	 * @param BUTTON_OPEN_SITE
	 * @param BUTTON_REPORT
	 * @param BUTTON_DOWNLOAD
	 * @param LABEL_ORIGINAL_FILE
	 * @param LABEL_BPS_FILE
	 * @param LABEL_RESULT_FILE
	 * @param LABEL_CHECK_CHECKSUM
	 * @param LABEL_CHECKSUM
	 * @param LABEL_DOWNLOAD
	 * @param LABEL_DOWNLOADS
	 * @param LABEL_VERSION
	 * @param LABEL_NEWEST_VERSION
	 * @param LABEL_LANGUAGE
	 * @param LABEL_ENTER_HASH
	 * @param LABEL_INFO
	 * @param INFO_INVALID_FILE
	 * @param INFO_SELECT_AN_ITEM
	 * @param INFO_ERROR
	 * @param INFO_NEW_VERSION
	 * @param INFO_INCORRECT_INPUT
	 * @param INFO_CANNOT_CREATE
	 * @param INFO_SUCCESS
	 * @param TITLE_OPEN_FAILED
	 * @param TITLE_DOWNLOAD_FAILED
	 * @param DIFFERENT
	 */
	private LanguageHelper(String LANG_ID, String THIS_LANG, String BUTTON_BROWSE, String BUTTON_PATCH, String BUTTON_PATCHING, String BUTTON_OPEN_SITE,
			 String BUTTON_REPORT, String BUTTON_DOWNLOAD, String LABEL_ORIGINAL_FILE, String LABEL_BPS_FILE, String LABEL_RESULT_FILE, 
			 String LABEL_CHECK_CHECKSUM, String LABEL_CHECKSUM, String LABEL_DOWNLOAD, String LABEL_DOWNLOADS, String LABEL_VERSION, 
			 String LABEL_NEWEST_VERSION, String LABEL_LANGUAGE, String LABEL_ENTER_HASH, String LABEL_INFO, String INFO_INVALID_FILE, String INFO_SELECT_AN_ITEM, 
			 String INFO_ERROR, String INFO_NEW_VERSION, String INFO_INCORRECT_INPUT, String INFO_CANNOT_CREATE, String INFO_SUCCESS, String TITLE_OPEN_FAILED, 
			 String TITLE_DOWNLOAD_FAILED, String DIFFERENT) {
		this.LANG_ID = LANG_ID;
		this.THIS_LANG = THIS_LANG;
		
		this.BUTTON_BROWSE = BUTTON_BROWSE;
		this.BUTTON_PATCH = BUTTON_PATCH;
		this.BUTTON_PATCHING = BUTTON_PATCHING;
		this.BUTTON_OPEN_SITE = BUTTON_OPEN_SITE;
		this.BUTTON_REPORT = BUTTON_REPORT;
		this.BUTTON_DOWNLOAD = BUTTON_DOWNLOAD;
		
		this.LABEL_ORIGINAL_FILE = LABEL_ORIGINAL_FILE;
		this.LABEL_BPS_FILE = LABEL_BPS_FILE;
		this.LABEL_RESULT_FILE = LABEL_RESULT_FILE;
		this.LABEL_CHECK_CHECKSUM = LABEL_CHECK_CHECKSUM;
		this.LABEL_CHECKSUM = LABEL_CHECKSUM;
		this.LABEL_DOWNLOAD = LABEL_DOWNLOAD;
		this.LABEL_DOWNLOADS = LABEL_DOWNLOADS;
		this.LABEL_VERSION = LABEL_VERSION;
		this.LABEL_NEWEST_VERSION = LABEL_NEWEST_VERSION;
		this.LABEL_LANGUAGE = LABEL_LANGUAGE;
		this.LABEL_ENTER_HASH = LABEL_ENTER_HASH;
		this.LABEL_INFO = LABEL_INFO;
		
		this.INFO_INVALID_FILE = INFO_INVALID_FILE;
		this.INFO_SELECT_AN_ITEM = INFO_SELECT_AN_ITEM;
		this.INFO_ERROR = INFO_ERROR;
		this.INFO_NEW_VERSION = INFO_NEW_VERSION;
		this.INFO_INCORRECT_INPUT = INFO_INCORRECT_INPUT;
		this.INFO_CANNOT_CREATE = INFO_CANNOT_CREATE;
		this.INFO_SUCCESS = INFO_SUCCESS;
		
		this.TITLE_OPEN_FAILED = TITLE_OPEN_FAILED;
		this.TITLE_DOWNLOAD_FAILED = TITLE_DOWNLOAD_FAILED;
		this.DIFFERENT = DIFFERENT;
		System.out.println("Language '"+THIS_LANG+"' initialized!");
	}
	
	/**
	 * Language Getter
	 * @return The selected or auto-selected language
	 */
	public static LanguageHelper getLang() {
		return instance;
	}

}
