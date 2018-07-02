package PS9_Helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.rational.test.ft.ObjectNotFoundException;
import com.rational.test.ft.object.interfaces.BrowserTestObject;
import com.rational.test.ft.object.interfaces.DomainTestObject;
import com.rational.test.ft.object.interfaces.FrameTestObject;
import com.rational.test.ft.object.interfaces.GuiTestObject;
import com.rational.test.ft.object.interfaces.IWindow;
import com.rational.test.ft.object.interfaces.RootTestObject;
import com.rational.test.ft.object.interfaces.SelectGuiSubitemTestObject;
import com.rational.test.ft.object.interfaces.StatelessGuiSubitemTestObject;
import com.rational.test.ft.object.interfaces.TestObject;
import com.rational.test.ft.object.interfaces.TextGuiTestObject;
import com.rational.test.ft.object.interfaces.ToggleGUITestObject;
import com.rational.test.ft.script.RationalTestScript;
import com.rational.test.ft.value.RegularExpression;

/**
 * Description : Super class for script helper
 * 
 * @author desaid
 * @since March 19, 2009
 */
public abstract class PS9_Helper extends RationalTestScript {
	//TODO Insert shared METHODS here
	// Mapped Drive
	public static final String mappedDrive = "\\\\file01\\Groups\\Automation\\DOE_Ed Automation\\XL_Files\\";
	public static final String mappedControl = "\\\\file01\\Groups\\Automation\\Control.xls";
	public static final String PS9mappedDrive = "\\\\file01\\Groups\\Automation\\PS9Upgrade Automation\\XL_Files\\";
	public static final String tempDir = "D:\\RationalTemp\\";
	
	// Environment to run ALL Scripts
	public final String environment = getValueFromControl("SERVER");
	
	public static BrowserTestObject myBrowser = (BrowserTestObject)returnTO(".class", "Html.HtmlBrowser");
	BrowserTestObject btoX;
	
	// Create Global Sequal Variables
	public static String sQuery = "";
	public static String fileName = "";
	public static String fName = "";
	public static String lName = "";
	
	// Create a Global Placeholder to store the Job Id created in Galaxy
	public static String position_title = "";
	public static String jobReqNo = "";
	public static String applcntID;
	public static String empID = "";
	public static String nID = "";
	public static String randSSN = null;
	
	// Create Global Placeholders to store the Date - Time Stamp in NYCAPS
	public static String startFromDate;
	public static String startFromTime;
	public static String endToDate;
	public static String endToTime;
	
	// Declare Variables for Reading a Result Set
	public static Connection c;
	public static Statement stmnt;
	public static ResultSet rs;
	public static ResultSetMetaData data;
	public static int colCount;
	public static int rowCount = 0;
	
	public static int iter = 0;
	public static int line = 1;
	public static String remark = "";
	
	// Create Global variables to save XL column names and it's corrosponding values
	public static Vector colNames = new Vector();
	public static Vector colValues = new Vector();
	public static Vector galaxyValues = new Vector();
	/**
	 * This Vector is used by the following functions: clickRandomExclude and
	 * buildVectorOfLowestChildren clickRandomEI and
	 * buildVectorOfLowestChildrenEI It is a dynamic vector to add the lowest
	 * level children of a table
	 * 
	 * @author Saurabh Malhotra
	 */
	public Vector lowestChildren = new Vector();
	public boolean secondLvl = false;
	
	/**
	 * This function generates SSN Randomly 
	 * @author Saurabh Malhotra
	 */
	public void randomSSN() {
		GregorianCalendar gc1 = new GregorianCalendar();
		String sSSN = String.valueOf(gc1.getTimeInMillis());
				
		int randNum = (int) ((Math.random() * 599) + 100);
		
		String sSSN1 = Integer.toString(randNum);
		String sSSN2 = sSSN.substring(11);
		String sSSN3 = sSSN.substring(9);
		
		randSSN = sSSN1 + sSSN2 + sSSN3;
		log("Randomly Created SSN: " + randSSN);
	}
	
	/**
	 * This function records the Start Time and puts them into Global Variables.
	 * @return void 
	 * @author Shruti Grover
	 */
	public void recordStartDateTime() {
		startFromDate = getTodaysDate("MM/dd/yyyy");
		startFromTime = getTodaysDate("hh:mm:ssaaa");
		System.out.println("Start Date:: " + startFromDate);
		System.out.println("Start Time:: " + startFromTime);
	}

	/**
	 * This function records the End Time and puts them into Global Variables.
	 * @return void 
	 * @author Shruti Grover
	 */
	public void recordEndDateTime() {
		endToDate = getTodaysDate("MM/dd/yyyy");
		endToTime = getTodaysDate("hh:mm:ssaaa");
		System.out.println("End Date:: " + endToDate);
		System.out.println("End Time:: " + endToTime);
	}
	
	/**
	 * @param query
	 * @param desiredColumns
	 * @param desiredRow
	 * @return All column values from a desired row
	 * @author Dan Chirillo
	 * @Updated - Dipen Desai
	 * @since 09/24/08 01:30 PM
	 */
	public String[] ExecuteQuery(String query, int[] desiredColumns, int desiredRow) {
		// Query on same XL File to get UserID, PW and URLS for Environment
		fileName = mappedDrive + "Control.xls";
		sQuery = "Select * from [Login$] where Key = '" + environment + "'";
		read_XL(fileName, sQuery);
		
		String driver = "oracle.jdbc.driver.OracleDriver";
		String oracle_sid = colValues.elementAt(0).toString();
		String id = colValues.elementAt(4).toString();
		String password = colValues.elementAt(5).toString();
		String oracle_host = colValues.elementAt(6).toString();
		String port = colValues.elementAt(7).toString();
		
		String[] returnedData= new String[desiredColumns.length];
		
		try {
			Class.forName(driver);
		} catch (Exception c) {
			System.out.println(c.toString());
			throw new RuntimeException("Could not load JDBC driver class:" + c.toString());
		}
		String url = "jdbc:oracle:thin:@" + oracle_host + ":" + port + ":" + oracle_sid;
		
		try {
			Connection connection = DriverManager.getConnection(url, id, password);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			
			System.out.println("Desired Row: " + desiredRow);
			for (int row = 0; row < desiredRow; row++) {
				resultSet.next();
			}
			
			ResultSetMetaData data = resultSet.getMetaData();
			int colCount = data.getColumnCount();
			log("Column count in Result Set: " + colCount);
			
			for (int i=0; i < desiredColumns.length; i++) {
				returnedData[i] = resultSet.getString(desiredColumns[i]);
				System.out.println(i + ": " + returnedData[i]);
			}
			
			resultSet.close();
			statement.close();
			connection.close();
		} catch (Exception sql) {
			System.out.println(sql.toString());
//			sql.printStackTrace();
		}
		return returnedData;
	}

	/**
	 * @param query
	 * @param desiredColumns
	 * @return - All row values in the desired column
	 * @author Dan Chirillo
	 * @Updated - Dipen Desai
	 * @since 10/02/08 10:30 AM
	 */
	public String[] ExecuteQuery(String query, int desiredColumns) {
		fileName = mappedDrive + "Control.xls";
		sQuery = "Select * from [Login$] where Key = '" + environment + "'";
		read_XL(fileName, sQuery);
		
		String driver = "oracle.jdbc.driver.OracleDriver";
		String oracle_sid = colValues.elementAt(0).toString();
		String id = colValues.elementAt(4).toString();
		String password = colValues.elementAt(5).toString();
		String oracle_host = colValues.elementAt(6).toString();
		String port = colValues.elementAt(7).toString();
		
		String[] returnedData;
		
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException c) {
			throw new RuntimeException("Could not load JDBC driver class:" + c.toString());
		}
		String url = "jdbc:oracle:thin:@" + oracle_host + ":" + port + ":" + oracle_sid;

		try {
			Connection connection = DriverManager.getConnection(url, id, password);
			Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultSet = statement.executeQuery(query);
						
			int rowCount = 0;
			while (resultSet.next()) {
				rowCount++;
			}
			
			System.out.println("size of result set: " + rowCount);
			returnedData = new String[rowCount];
			
			resultSet.first();
			int x = 0;
			do {
				returnedData[x] = resultSet.getString(desiredColumns);
				x++;
			} while (resultSet.next());
			
			resultSet.close();
			statement.close();
			connection.close();
			
			return returnedData;
		} catch (Exception sql) {
			logError("Exception executing SQL query: " + sql.toString());
			return null;
		}
	}
	
	/**
	 * @param query
	 * @return - All Column Values
	 * @author Dan Chirillo
	 * @Updated - Dipen Desai
	 * @since 10/02/08 10:30 AM
	 */
	public String[] ExecuteQuery(String query) {
		// Query on same XL File to get UserID, PW and URLS for Environment
		fileName = mappedDrive + "Control.xls";
		sQuery = "Select * from [Login$] where Key = '" + environment + "'";
		read_XL(fileName, sQuery);
		
		//String driver = "oracle.jdbc.driver.OracleDriver";
		String driver = "oracle.jdbc.OracleDriver";
		String oracle_sid = colValues.elementAt(0).toString();
		String id = colValues.elementAt(4).toString();
		String password = colValues.elementAt(5).toString();
		String oracle_host = colValues.elementAt(6).toString();
		String port = colValues.elementAt(7).toString();
		
		String[] returnedData = new String[query.substring(0, query.indexOf("FROM")).split(",").length];
		
		try {
			Class.forName(driver);
		} catch (Exception c) {
			System.out.println(c.toString());
			throw new RuntimeException("Could not load JDBC driver class:" + c.toString());
		}
		String url = "jdbc:oracle:thin:@" + oracle_host + ":" + port + ":" + oracle_sid;
		
		try {
			Connection connection = DriverManager.getConnection(url, id, password);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			
			for (int row = 0; row < 1; row++) {
				resultSet.next();
			}
			
			ResultSetMetaData data = resultSet.getMetaData();
			int colCount = data.getColumnCount();
			log("Column count in Result Set: " + colCount);
			
			for (int i=1; i <= colCount; i++) {
				returnedData[i-1] = resultSet.getString(i);
				System.out.println("Returned Data: " + i + ": " + returnedData[i-1]);
			}
			
			resultSet.close();
			statement.close();
			connection.close();
		} catch (Exception sql) {
			System.out.println(sql.toString() + " --------------------- DIPEN");
			returnedData = null;
		}
		return returnedData;
	}
	
	
	/**
	 * Pass in an SQL Query to run.  This function returns the 1st cell (1,1) out of the result set.
	 * @param query
	 * @return
	 * @author Dan Chirillo
	 */
	public String getEmpID(String query) {
		return getEmpID(query, 1);
	}
	
	/**
	 * Pass in an SQL Query to run.  This function returns the cell (desiredRow,1) out of the result set.
	 * @param query
	 * @return
	 * @author Dan Chirillo
	 */
	public String getEmpID(String query, int desiredRow) {
		int[] columns = {1};
		String[] data = ExecuteQuery(query, columns, desiredRow);
		return data[0];
	}
	
	/**
	 * Pass in an SQL Query to run.  This function returns the cell (desiredRow, DesiredCol) out of the result set.
	 * @param query
	 * @return
	 * @author Dan Chirillo
	 */
	public String getEmpID(String query, int desiredCol, int desiredRow) {
		int[] columns = {desiredCol};

		String[] data = ExecuteQuery(query, columns, desiredRow);
		return data[0];
	}
	
	/**
	 * This function checks to see if a directory exists.
	 * If it does not exist this function tries to create it.
	 * 
	 * @param destFolder - location where the file needs to be copied
	 * @author Saurabh Malhotra
	 */
	public void checkTempDir(String destFolder) {
		try {
			File f = new File(destFolder);
			if (!f.exists()) {
				f.mkdir();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This function will take a path for example:
	 * "S:\\somedirectory\\myExcel.xls" and copy the given file to the
	 * destination folder that is specified, for example: 
	 * "D:\\temp\\"
	 * 
	 * @param source - Path to the file that needs to be copied
	 * @param destFolder - location where the file needs to be copied
	 * @author Saurabh Malhotra
	 * @return
	 */
	public String copyToTemp(String source, String destFolder) {
		return copyToTemp(source, destFolder, false);
	}
	
	/**
	 * This function will take a path for example:
	 * "S:\\somedirectory\\myExcel.xls" and copy the given file to the
	 * destination folder that is specified, for example: 
	 * "D:\\temp\\"
	 * 
	 * @param source - Path to the file that needs to be copied
	 * @param destFolder - location where the file needs to be copied
	 * @param delIfExists - pass true if you want to overwrite the file if it
	 * 						exists in the dest folder, false otherwise.
	 * @author Saurabh Malhotra
	 * @return
	 */
	public String copyToTemp(String source, String testFolder, boolean delIfExists) {
		String fileName;
		String temp = testFolder; //"D:\\temp\\";
		String toReturn = "";
		checkTempDir(temp);

		if (source.indexOf("\\") >= 0) {
			fileName = returnLastToken(source, "\\");
		} else {
			fileName = returnLastToken(source, "//");
		}

		try {
			File f = new File(temp + fileName);
			if (f.exists() && !delIfExists){
				//file exists already and Del if Exists is false.  Therefore do nothing.
				toReturn = temp + fileName;
			} else {	//All other scenarios overwrite the file or create a new file
				FileChannel srcChannel = new FileInputStream(source).getChannel();
				FileChannel dstChannel = new FileOutputStream(temp + fileName).getChannel();
				dstChannel.transferFrom(srcChannel, 0, srcChannel.size());
				srcChannel.close();
				dstChannel.close();
				toReturn = temp + fileName;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return toReturn;
	}
	
	/**
	 * This function will take a path for example
	 * "S:\\somedirectory\\myExcel.xls" and try to find the given file (myExcel.xls) in a
	 * destination directory, for example: "D:\\temp\\"
	 * If the file exists it will try to delete the file from the temp directory.
	 * 
	 * @param destFolder - location where the file needs to be deleted
	 * @param source - Path to the file + Name of File that needs to be deleted
	 * 
	 * @author Saurabh Malhotra
	 * @author Dipen Desai (Modified on 03/02/2009)
	 */
	public void delFromTemp(String source, String destFolder) {
		String dFile;
		if (source.indexOf("\\") >= 0)
			dFile = returnLastToken(source, "\\");
		else
			dFile = returnLastToken(source, "//");
		try {
			File f = new File(destFolder + dFile);
			if (f.exists())
				f.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This function returns a word from the sentence by mentioning its position
	 * If wordpos is less than zero than string is read backwards.
	 * 
	 * @param - Sentence and wordposition
	 * @return - String in the given position
	 * @author Abirami Bandaru
	 */
	public String getToken(String sentence, int wordpos) {
		StringTokenizer st = new StringTokenizer(sentence);
		int i = 0;
		String tmp = null;
		
		if((wordpos > 0) && ((st.countTokens() - wordpos) >= 0)) {
			while (st.hasMoreTokens()) {
				i++;
				tmp = st.nextToken();
				if (i == wordpos)
					break;
			}
			return tmp;
		} else if((wordpos < 0) && ((st.countTokens() + wordpos) >= 0)) {
			i = st.countTokens();
			while (st.hasMoreTokens()) {
				tmp = st.nextToken();
				if((i + wordpos) == 0) {
					break;
				}
				--i;
			}
			return tmp;
		} else {
			System.out.println("No Token selected OR Token number is out of bounds.");
			return null;
		}
	}
	
	/**
	 * This function takes a String and parses it based on the token provided.
	 * It returns the last token.
	 * 
	 * @param myStr - String to parse
	 * @param Token - Token used for StringTokenizer
	 * @return - the last token in myStr
	 * @author Saurabh Malhotra
	 */
	public String returnLastToken(String myStr, String Token) {
		StringTokenizer st = new StringTokenizer(myStr, Token);
		String toReturn = "";
		while (st.hasMoreTokens()) {
			toReturn = st.nextToken();
		}
		return toReturn;
	}

	/**
	 * This function takes a text file's path as a string and returns the contents of that file as a String 
	 * @param filePath - path to the text file to be copied to a string
	 * @return - complete contents of the file as a String value
	 * @author - Saurabh Malhotra
	 */
	public String copyFileToString(String filePath) {
		String content = "";
		try{
			//read from filePath file and copy to a string
			FileInputStream fis = new FileInputStream(filePath);
			int x = fis.available();
			byte b[]= new byte[x];
			fis.read(b);
			fis.close();
			content = new String(b);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		//return contents of the file
		return content;
	}
	
	/**
	 * Get and close all open html objects.  Including dialog boxes.
	 * @author Saurabh Malhotra
	 */
	public void closeAllHtmlObjects() {
		checkDialogBox();
		
		int htmlDomCount = 0;
		int htmlTopObjects = 0;
		do{
			try{
				//get all testable domains
				DomainTestObject[] doms = getDomains();
				htmlDomCount = 0;
				htmlTopObjects = 0;
				for (int n = 0; n < doms.length; n++){
					System.out.println(n + ": " + doms[n].getName().toString());
					if (doms[n].getName().toString().equals("Html")){
						//keep count of how many are html domains
						htmlDomCount++;
					}
				}
		
				//get all html domains
				DomainTestObject[] htmlDoms = new DomainTestObject[htmlDomCount];
				htmlDomCount = 0; 
				for (int n = 0; n < doms.length; n++){
					System.out.println(n + ": " + doms[n].getName().toString());
					if (doms[n].getName().toString().equals("Html")){
						htmlDoms[htmlDomCount] = doms[n];
						htmlDomCount++;
					}
				}
				
				//loop through html domains
				for (int n = 0; n < htmlDoms.length; n++){
					//get and close all testable objects in Html domain
					TestObject[] TOs = htmlDoms[n].getTopObjects();
					
					for (int x = 0; x < TOs.length; x++){
						System.out.println(n + "." + x + ": " + TOs[x].toString());
						FrameTestObject fTO = new FrameTestObject(TOs[x]);
						fTO.close();
						fTO.unregister();
						htmlTopObjects++;
					}
					unregister(TOs);
				}
				unregisterAll();
			}
			catch (Exception e){
				
			}
		}while (htmlTopObjects > 0);
	}
	
	
	/**
	 * This function closes any open IE browser windows
	 * @author Saurabh Malhotra
	 */
	public void closeBrowserAny() {
		IWindow[] wins = getTopWindows();
		for (int n = 0; n < wins.length; ++n) {
			if (wins[n].getWindowClassName().equals("IEFrame")) {
				wins[n].close();
			}
		}
	}
	
	/**
	 * This function returns date in any format mentioned in parameter 2
	 * @param Date, input format, output format
	 * @return Date in the format mentioned in parameter 3
	 * @author Abirami Bandaru
	 */
	public String ChangeMyFormat(String dt, String in, String out) {
		SimpleDateFormat input = new SimpleDateFormat(in);
		SimpleDateFormat sd = new SimpleDateFormat(out);
		
		try {
			Date inputDt = input.parse(dt);
			String reqDt = sd.format(inputDt);
			return reqDt;
		} catch (Exception e) {
			System.out.println(e);	
		}
		return "";
	}
	
	/**
	 * This function takes a string parameter that is a date in the format:
	 * MM/dd/yyyy and adds noOfDays days to the date and returns back the future
	 * date in the format: MM/dd/yyyy
	 * 
	 * @author Saurabh Malhotra
	 */
	public String addDaysFromDate(String aDate, int noOfDays) {
		try {
			String start = aDate;
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			Date d = sdf.parse(start);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(d);
			calendar.add(Calendar.DATE, noOfDays);
			Date end = calendar.getTime();
			return sdf.format(end);
		} catch (Exception e) {
			System.out.println("Error Parsing");
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * This function returns todays date as a String in the format MM/dd/yyyy
	 * 
	 * @return String TodaysDate
	 * @author Abirami Bandaru
	 */
	public String getTodaysDate() {
		return getTodaysDate("MM/dd/yyyy");
	}
	
		
	/**
	 * This function returns todays date in the format provided. 
	 * @param myFormat - format to be used when return date as a String (eg. "MM/dd/yyyy")
	 * @return
	 * @author Abirami Bandaru
	 */
	public String getTodaysDate(String myFormat) {
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
		String today = sdf.format(now);
		return today;
	}
	
	/**
	 * This function takes a top level TestObject myTO, and two strings that are used to 
	 * find a TextGuiTestObject inside the top level TestObject.  This TextGuiTestObject 
	 * contains a date string which we compare to todays date to see if they match.
	 * Results are logged to the Test Log 
	 * @param myTO - Top level test object
	 * @param recProperty - property used to find the text gui test object
	 * @param propValue - value for the property provided in recProperty
	 */
	public void compareDates(TestObject myTO, String recProperty, String propValue) {
		String sDate = getTodaysDate();
		compareDates(myTO, recProperty, propValue, sDate);
	}
	
	/**
	 * This function takes two string parameter that are dates in the format:
	 * MM/dd/yyyy.  It compare two dates and give the following results
	 * If the two dates are equal, it returns "Equal"
	 * If first date > second date, it returns "Greater"
	 * If first date < second date, it returns "Lesser"
	 * @param firstDate
	 * @param secondDate
	 * @return
	 * @author Abirami Bandaru
	 */
	public String compareDates(String firstDate, String secondDate) {
		try {          
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");                                
			Date firstD = sdf.parse(firstDate);
			Date secondD = sdf.parse(secondDate);
			int compare = firstD.compareTo(secondD);
			if (compare == 0)
				return "Equal";
			else if (compare > 0)
				return "Greater";
			else
				return "Lesser";
		} catch(Exception e) {
		     System.out.println("Error Parsing");
		      e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * This function takes a top level TestObject myTO, and three strings.  Two of them are used to 
	 * find a TextGuiTestObject inside the top level TestObject.  This TextGuiTestObject 
	 * contains a date string which we compare to date string to see if they match.
	 * Results are logged to the Test Log 
	 * @param myTO - Top level test object
	 * @param recProperty - property used to find the text gui test object
	 * @param propValue - value for the property provided in recProperty
	 * @param date - String of date that was entered
	 * @author Abirami Bandaru
	 */
	public String compareDates(TestObject myTO, String recProperty, String propValue, String date) {
		String status = "";
		try {
			TestObject[] TOs = myTO.find(atDescendant(recProperty, propValue));
			if (TOs.length > 0){
				TextGuiTestObject TO = (TextGuiTestObject) TOs[0];
				TO.waitForExistence(300, 0.10);
				if (TO.exists()) {
					String firstDate = TO.getText();
					System.out.println("firstDate: " + firstDate);
					
					SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
					Date ExcelDate = sdf.parse(date);
					Date firstDt = sdf.parse(firstDate);
					
					//Convert Excel date to MM/dd/yyyy Format
					SimpleDateFormat sd = new SimpleDateFormat("MM/dd/yyyy");
					String eDate = sd.format(ExcelDate);
					
					System.out.println("ExcelDate: " + ExcelDate);
					System.out.println("date: " + date);
					System.out.println("firstDt: " + firstDt);
										
					int compare = firstDt.compareTo(ExcelDate);
					
					System.out.println("Compare Result: " + compare);
					
					if ( compare == 0 ) {
						logTestResult("<font color=\"DeepPink\">Latest Date Matches the given date", true, "<font color=\"BlueViolet\"><b>Expected Date: " + eDate + "</b>\n<b>" + "Actual Date: " + firstDate + "</b></font>");
						status = "Equal";
					}
					else if (compare > 0){
						LogScreenShot("<font color=\"DeepPink\">Latest Date is greater than the given date </font>");
						status = "Greater";
					}
					else if ( compare < 0){
						LogScreenShot("<font color=\"DeepPink\">Latest Date is less than the given date </font>");
						status = "Lesser";
					}
				}
				TO.unregister();
			}
			unregister(TOs);
		}
		catch (Exception e) {
			System.out.println("The TextGuiTestObject could not be found");
			logError("<font color=\"DeepBlue\">" + "CompareDates FAILED " + e.toString() + "</font>", getRootTestObject().getScreenSnapshot());
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * String To Int function, pass in a String and it will return the int it
	 * represents.
	 * 
	 * @param str
	 * @return
	 * @author Saurabh Malhotra
	 */
	public int stringToInt(String str) {
		int myInt = 0;

		// remove $ sign if it exists
		int indexDol = str.indexOf("$");
		if (indexDol >= 0) {
			str = str.substring(indexDol + 1, str.length());
		}

		// remove . if it exists
		int indexPer = str.indexOf(".");
		if (indexPer >= 0) {
			str = str.substring(0, indexPer);
		}
		System.out.println("Str in Fn:" + str);
		// convert to int
		try {
			myInt = Integer.parseInt(str);
		} catch (NumberFormatException e) {
			System.out.println(e.toString());
		}
		return myInt;
	}

	/**
	 * This function takes two string parameter that are dates in the format:
	 * MM/dd/yyyy. It finds the middle date between the two dates and returns it
	 * in the same format as a String. If the two dates are equal, it returns an
	 * empty string "".
	 * 
	 * @param firstDate
	 * @param secondDate
	 * @return
	 * @author Saurabh Malhotra
	 */
	public String findMiddleDate(String firstDate, String secondDate) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			Date firstD = sdf.parse(firstDate);
			Date secondD = sdf.parse(secondDate);
			Calendar calendar = Calendar.getInstance();
			int compare = firstD.compareTo(secondD);
			if (compare == 0) {
				return "";
			} else if (compare < 0) {
				//firstD < secondD
				long ms = secondD.getTime() - firstD.getTime();
				long days = ms / (24 * 60 * 60 * 1000);
				int intDays = (int) (days / 2);
				calendar.setTime(firstD);
				calendar.add(Calendar.DATE, intDays);
				Date end = calendar.getTime();
				return sdf.format(end);
			} else {
				//secondD < firstD
				long ms = firstD.getTime() - secondD.getTime();
				long days = ms / (24 * 60 * 60 * 1000);
				int intDays = (int) (days / 2);
				calendar.setTime(secondD);
				calendar.add(Calendar.DATE, intDays);
				Date end = calendar.getTime();
				return sdf.format(end);
			}
		} catch (Exception e) {
			System.out.println("Error Parsing");
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * Pass in a table TestObject and a link String. This method will attempt to
	 * click on the link if it exists in the table. If it doesn't exist this
	 * method will return a -1. This method will click on the 1st instance of
	 * that link. If it exists more than once, the rest of instances will be
	 * ignored.
	 * 
	 * @param myTable - table that contains the link
	 * @param link - the link that you wish to click
	 * @return 1 if click was successful. -1 if couldn't find the link in myTable
	 * @author Saurabh Malhotra
	 */
	public int click1stSpecific(TestObject myTable, String link) {
		RegularExpression rowRE = new RegularExpression("PSSRCHRESULTS.*ROW",false);
		try {
			TestObject[] TOs = myTable.find(atDescendant(".className", rowRE,".text", link));
			int numOfObjects = TOs.length;
			if (numOfObjects >= 1) {
				GuiTestObject GUITO = new GuiTestObject(TOs[0]);
				GUITO.click();
				GUITO.unregister();
				unregister(TOs);
				return 1;
			}
			unregister(TOs);
			return -1;
		} catch (Exception e) {
			System.out.println("Unable to find and click on: " + link + "\n" + e.toString());
			return -1;
		}
	}
	
	/**
	 * This function takes a table as an input and randomly clicks on one of the
	 * valid objects in the table.
	 * 
	 * @param myTable
	 * @author Saurabh Malhotra
	 */
	public void clickRandom(TestObject myTable) {
		TestObject[] TOs;
		RegularExpression rowRE = new RegularExpression("PSSRCHRESULTS.*ROW", false);
		TOs = myTable.find(atDescendant(".className", rowRE));
		int numOfObjects = TOs.length;
		int randNum = (int) (Math.random() * numOfObjects);
		GuiTestObject GUITO = new GuiTestObject(TOs[randNum]);
		GUITO.click();

		unregister(TOs);
		GUITO.unregister();
	}
	
	/**
	 * This Function clicks a Random link in a table and allows to exclude and
	 * include values. A bit on the complicated side, but it does the job.
	 * 
	 * @param myTable - TestObject (table) that contains links to be clicked
	 * @param excludeVal[] - String Array - The 1st value in array should be the starting
	 *            text of the row(s) to exclude - Can contain as many values
	 *            after that. All values after the 1st should contain text that
	 *            is part of description to be avoided.
	 * @param include - String - Value that must be in the text we are to click.
	 * @author Saurabh Malhotra
	 */
	public void clickRandomEI(TestObject myTable, String[] excludeVal, String include) {
		lowestChildren.clear();
		buildVectorOfLowestChildrenEI(myTable, excludeVal, include);
		int count = lowestChildren.size();
		System.out.println("Lowest Children Count: " + count);
		TestObject[] lowChilds = new TestObject[count];
		lowestChildren.copyInto(lowChilds);
		int randNum = (int) (Math.random() * count);
		//System.out.println("Random Number: " + randNum);
		GuiTestObject x = new GuiTestObject(lowChilds[randNum]);
		x.click(atPoint(1, 1));
		x.unregister();
		unregister(lowChilds);
		lowestChildren.clear(); //clear the public vector
	}
	
	/**
	 * This Function clicks a Random link in a table and allows to exclude and include values.  A bit on the complicated side, but it does the job.
	 * @param myTable - TestObject (table) that contains links to be clicked
	 * @param excludeVal[] - String Array - The 1st value in array should be the starting text of the row(s) to exclude
	 * 					   - Can contain as many values after that.  All values after the 1st should contain text that is part of description to be avoided. 
	 * @param include - String - Value that must be in the text we are to click.
	 * @author Saurabh Malhotra
	 */
	public void clickRandomEI(TestObject myBrowser, String recProperty, String propValue, String[] excludeVal, String include) {
		TestObject[] TableObjs = myBrowser.find(atDescendant(recProperty, propValue));
		System.out.println(propValue);
		
		if (TableObjs.length >= 1 ) {
			StatelessGuiSubitemTestObject myTable = (StatelessGuiSubitemTestObject) TableObjs[0];
			clickRandomEI(myTable, excludeVal, include);
		}
	}
	
	/**
	 * This function takes a table and a string to include. Only the valid links
	 * in the table that contain the string are used and one of them is randomly
	 * clicked. The string does not have to match the entire .text property but
	 * be part of it. The search is not case sensitive.
	 * 
	 * @param myTable - Table of type Rational TestObject
	 * @param strToInclude - String
	 * @author Saurabh Malhotra
	 */
	public void clickRandomInclude(TestObject myTable, String strToInclude) {
		TestObject[] TOs;
		RegularExpression rowRE = new RegularExpression("PSSRCHRESULTS.*ROW", false);
		RegularExpression includeRE = new RegularExpression(".*" + strToInclude + ".*", false);
		TOs = myTable.find(atDescendant(".className", rowRE, ".text", includeRE));
		int numOfObjects = TOs.length;
		if (numOfObjects >= 1) {
			int randNum = (int) (Math.random() * numOfObjects);
			GuiTestObject GUITO = new GuiTestObject(TOs[randNum]);
			GUITO.click();
			GUITO.unregister();
		}
		unregister(TOs);
	}

	/**
	 * Pass a Table Test object into this function to have it click on a random
	 * item inside the table. This function will exclude the test objects with
	 * the same href property as the one with the value passed in as the 2nd
	 * parameter.
	 * 
	 * @param myTable - table that contains link to be clicked
	 * @param excludeVal - value to exclude when clicking
	 * @author Saurabh Malhotra
	 */
	public void clickRandomExclude(TestObject myTable, String Exclude) {
		// vector used to hold final list of clickable objects
		Vector clickableTOs = new Vector();

		// int used to hold final count of clickable objects
		int count;

		// array used to hold final list of clickable objects
		TestObject[] finalTOs;

		// find all clickable links in the table
		RegularExpression rowRE = new RegularExpression("PSSRCHRESULTS.*ROW", false);
		TestObject[] TOs = myTable.find(atDescendant(".className", rowRE));
		int numOfObjects = TOs.length;

		// find all the objects that should be excluded
		TestObject[] excludeTOs = myTable.find(atDescendant(".text", Exclude));

		//if the exclude value exists in the table do the following to remove it from clickable objects
		if (excludeTOs.length >= 1) {
			String hrefToExclude = excludeTOs[0].getProperty(".href").toString();
			//loop through all the objects and remove the ones that match the exclude value
			for (int x = 0; x < numOfObjects; x++) {
				if (TOs[x].getProperty(".href").toString().equals(hrefToExclude)) {
					//if test object's href matches an href on the exclude list - Do nothing
				} else {
					clickableTOs.add(TOs[x]); //if not on the exclude list, add to vector of cliackable Test Objects
				}
			}

			//create array of clickable Test Objects that are not on our exclude list
			count = clickableTOs.size();
			finalTOs = new TestObject[count];
			clickableTOs.copyInto(finalTOs);
		}
		//the exclude value does not exist, so we will use the entire table to find random value to click
		else {
			count = numOfObjects;
			finalTOs = TOs;
		}

		//find a random number
		int randNum = (int) (Math.random() * (count - 1));
		GuiTestObject GUITO = new GuiTestObject(finalTOs[randNum]);

		//click on a random object based on random number
		GUITO.click();

		//unregister all Test Objects
		unregister(TOs);
		unregister(finalTOs);
		GUITO.unregister();
	}

	/**
	 * Pass in a table and a String array to this function. The function will
	 * click on a random object while avoiding objects based on the String
	 * array.
	 * 
	 * @param myTable - TestObject table containing the links to click
	 * @param arrayToExclude - String[] array containing the exact .text property of links to be avoided
	 */
	public void clickRandomExcludeArray(TestObject myTable, String[] arrayToExclude) {
		Vector hrefsToExclude = new Vector(); //vector to hold hrefs of things to exclude
		Vector clickableTOs = new Vector(); //vector to hold clickable test objects
		int finalCount;
		TestObject[] finalTOs;

		//Build an array of hrefs that should be excluded
		for (int x = 0; x < arrayToExclude.length; x++) {
			try {
				TestObject[] excludeTOs = myTable.find(atDescendant(".text", arrayToExclude[x]));
				if (excludeTOs.length >= 0) {
					String href = excludeTOs[0].getProperty(".href").toString();
					hrefsToExclude.add(href);
				} else {
					System.out.println("Unable to find " + arrayToExclude[x]);
				}
				unregister(excludeTOs);
			} catch (Exception e) {
				System.out.println("Exception occured when trying to find "	+ arrayToExclude[x] + " " + e.toString());
			}
		}

		//create string array to hold hrefs of things to exclude
		int count = hrefsToExclude.size();
		String[] strHrefsToExclude = new String[count];
		hrefsToExclude.copyInto(strHrefsToExclude);

		//find all valid TestObjects
		TestObject[] TOs;
		RegularExpression rowRE = new RegularExpression("PSSRCHRESULTS.*ROW", false);
		TOs = myTable.find(atDescendant(".className", rowRE));
		int numOfObjects = TOs.length;
		int numOfHrefs = strHrefsToExclude.length;

		//if there are hrefs to exclude loop through and remove them
		if (numOfHrefs > 0) {
			//loop through valid TestObjects and remove the ones on our exclude list based on href property
			for (int x = 0; x < numOfObjects; x++) {
				for (int y = 0; y < numOfHrefs; y++) {
					if (TOs[x].getProperty(".href").toString().equals(
							strHrefsToExclude[y])) {
						//if test object's href matches an href on the exclude list Do nothing
					} else {
						clickableTOs.add(TOs[x]); //if not on the exclude list, add to vector of cliackable Test Objects
					}
				}
			}

			//create array of clickable Test Objects that are not on our exclude list
			finalCount = clickableTOs.size();
			finalTOs = new TestObject[finalCount];
			clickableTOs.copyInto(finalTOs);
		} else {
			finalCount = numOfObjects;
			finalTOs = TOs;
		}

		//find a random number
		int randNum = (int) (Math.random() * (finalCount - 1));
		GuiTestObject GUITO = new GuiTestObject(finalTOs[randNum]);

		//click on a random object based on random number
		GUITO.click();

		//unregister all Test Objects
		unregister(TOs);
		unregister(finalTOs);
		GUITO.unregister();
	}

	/**
	 * Recursive Function used by clickRandomEI to traverse through the table
	 * object and find all the clickable links
	 * 
	 * @param myTable - TestObject (table) that contains links to be clicked
	 * @param excludeVal[] - String Array - The 1st value in array should be the starting
	 *            text of the row(s) to exclude - Can contain as many values
	 *            after that. All values after the 1st should contain text that
	 *            is to be avoided.
	 * @param include - String - Value that must be in the text we are to click.
	 * @author Saurabh Malhotra
	 * @return
	 */
	public int buildVectorOfLowestChildrenEI(TestObject myTable, String[] excludeVal, String include) {
		TestObject[] tablesObjs = myTable.getChildren();
		int numOfcurChildren = tablesObjs.length;

		if (numOfcurChildren == 0) {
			String myTableTxt = myTable.getProperty(".text").toString();
			int okToClick = 0;
			
			for (int num = 1; num < excludeVal.length; num++) {
				if (myTableTxt.indexOf(excludeVal[num]) != -1) {
					okToClick++;
				}
			}
			if (okToClick == 0) {
				lowestChildren.addElement(myTable);
			}
		} else {
			for (int count = 0; count < numOfcurChildren; count++) {
				String myText = tablesObjs[count].getProperty(".text").toString();
				if ((!myText.startsWith(excludeVal[0])) && (myText.indexOf(include) != -1)) {
					buildVectorOfLowestChildrenEI(tablesObjs[count], excludeVal, include);
				}
			}
		}
		return -1;
	}
	
	/**
	 * Message box displayed at run time.
	 * @param message - the string you wish to display
	 * @author Saurabh Malhotra
	 */
	public void myMsgBox(String message) {
		JFrame frame = new JFrame();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		JOptionPane.showMessageDialog(frame, message);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.dispose();
	}

	/**
	 * Input box displayed at run time.
	 * 
	 * @param question - The question you wish to ask.
	 * @return string containing the answer.
	 * @author Saurabh Malhotra
	 */
	public String myInputBox(String question) {
		JFrame frame = new JFrame();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		String s = JOptionPane.showInputDialog(frame, question, "Please input answer here.");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.dispose();
		return s;
	}
	
	/**
	 * A list box displays all available environments to execute a script.
	 * 
	 * @param void
	 * @return string containing the enrironment.
	 * @author Dipen Desai
	 */
	public String envListBox() {
		try {
			Object[] list = {"HCMSNY6"};
			
			JFrame frame = new JFrame();
			
			Object selVal = JOptionPane.showInputDialog(null, "", "Please Specify Environment", JOptionPane.QUESTION_MESSAGE, null, list, list[0]);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.dispose();
			
			return selVal.toString();
		} catch (Exception exc) {
			log("Environment is not specified. Default 'HCMSNY6' is selected.");
			
			return "HCMSNY6";
		}
	}
	
	/* This function will get the Value for the Key that is passed to the function.
	 * @param key - The Key for which the Value will be retrieved from the Control File. 
	 * @author Dipen Desai
	 */
//	public String getValueFromControl(String x) {
//		// Query on Control XL File to get UserID, PW and URS for Server Name = sSrvr
//		fileName = mappedDrive + "Control.xls";
//		sQuery = "Select * from [Control$] where Key = '" + x + "'";
//		read_XL(fileName, sQuery);
//		
//		return colValues.elementAt(1).toString();
//	}
	
	public String getValueFromControl(String key) {
		String value = "";
		try {
			// Enable JDBC ODBC Driver to access excel
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			
			// Excel File to be queried
			String FN = mappedDrive + "Control.xls";
			
			//Copying file to local temp location (To allow concurent users)
			FN = copyToTemp(FN, "D:\\RationalTemp\\", false); 
			System.out.println("FN: " + FN);
			
			// Set Connection to Excel File
			Connection c = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xls)};DBQ=" + FN + "; READONLY=true");
			// Crt Object to be used to query Excel File
			Statement stmnt = c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			// Select statement to query Excel worksheet and get data
			sQuery = "Select * from [Control$] where Key = '" + key + "'";
			// Get a ResultSet by executing the Select Statement
			ResultSet rs = stmnt.executeQuery(sQuery);
			rs.next();

			value = rs.getString("Value");
			rs.close();
			stmnt.close();
			c.close();
			
			System.out.println("Key:: " + key);
			System.out.println("Value:: " + value);
		}
		catch (Exception e) {
			System.out.println("Exception in getValueFromControl() :: " + e.toString());
		}
		return value;
	}
	
	/**
	 * This function returns a string between start and end string
	 * 
	 * @param sentence - Sentence that needs to be parsed
	 * @param startStr,endStr - String between startStr and endStr will be returned
	 * @return String between start and end string
	 * @author Abirami Bandaru
	 */
	public String parseString(String sentence, String startStr, String endStr) {
		int startPos = sentence.indexOf(startStr) + startStr.length();
		System.out.println("starting position:"+startPos);
		int endPos = sentence.indexOf(endStr);
		System.out.println("ending position:"+endPos);
		if (endPos == 0 || endPos < startPos)
			return null;
		String retStr = sentence.substring(startPos, endPos).trim();
		return retStr;
	}

	/**
	 * This function returns a string by omitting the last word of the given string
	 * 
	 * @param sentence - Sentence in which last word needs to be omitted
	 * @return String by excluding the last word
	 * @author Abirami Bandaru
	 */
	public static String getLastButOne(String sentence) {
		if (sentence == null)
			return null;
		
		int pos = sentence.lastIndexOf(" ");
		String myStr = sentence.substring(0, pos);
		System.out.println("String after removing last word: " + myStr);
		return myStr;
	}

	/**
	 * This function encrypt the password
	 * 
	 * @author Saurabh Malhotra
	 */
	public String encrypt(String str) {
		String s = "";
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c >= 'a' && c <= 'm')
				c += 13;
			else if (c >= 'n' && c <= 'z')
				c -= 13;
			else if (c >= 'A' && c <= 'M')
				c += 13;
			else if (c >= 'N' && c <= 'Z')
				c -= 13;
			else if (c >= '0' && c <= '4')
				c += 5;
			else if (c >= '5' && c <= '9')
				c -= 5;
			s = s + c;
		}
		return s;
	}	

	/**
	 * This function takes a table and returns the number of valid clickable
	 * links. Returns -1 if something goes wrong.
	 * 
	 * @param myTable
	 * @author Saurabh Malhotra
	 */
	public int getNumOfLinks(TestObject myTable) {
		try {
			TestObject[] TOs;
			RegularExpression rowRE = new RegularExpression("PSSRCHRESULTS.*ROW", false);
			TOs = myTable.find(atDescendant(".className", rowRE));
			int numOfObjects = TOs.length;
			unregister(TOs);
			return numOfObjects;
		} catch (Exception e) {
			return -1;
		}
	}

	/** This function finds a test object and clicks on it.
	 * @param myTO - Higher level object where object to be clicked will be found.
	 * 				Could be browser, HTML page, HTML table etc.
	 * @param recProperty - property used for recognition.  For example ".text", ".id", ".name"
	 * @param propValue - value for the recProperty used for recognition
	 * @author Saurabh Malhotra
	 */
	public boolean click(TestObject myBrowser, String recProperty, String propValue) {
		boolean status = true;
		System.out.println("Start CLICK: " + recProperty + " : " + propValue);
		try {
			TestObject[] TOs = myBrowser.find(atDescendant(recProperty, propValue));
			int numOfObjects = TOs.length;
			
			if (numOfObjects >= 1) {
				for(int i=0; i<numOfObjects; i++) {
					GuiTestObject myGTO = (GuiTestObject) TOs[i];
					myGTO.waitForExistence(600, 0.10);
					try {
						if (myGTO.exists()) {
							myGTO.click();
							browserWait(myBrowser);
							System.out.println("CLICKED: " + recProperty + " : " + propValue + "\n\n");
							i = numOfObjects;
						}
						myGTO.unregister();
					} catch (Exception nm) {
						System.out.println("\t\t\tOBJECT EXCEPTION " + i + " OF " + numOfObjects);
					}
				}
				unregister(TOs);
			} else {
				throw new ObjectNotFoundException();
			}
		} catch (Exception e) {
			LogScreenShot("CLICK -- EXCEPTION OBJECT NOT FOUND " + recProperty + " : " + propValue);
			status = false;
		}
		return status;
	}
	
	public boolean click(String recProperty, String propValue) {
		return click(myBrowser, recProperty, propValue);
	}
	
	/**
	 * @param recProperty
	 * @param propValue
	 * @param numOfSecs
	 * @return
	 */
	public int waitForExistenceAndClick(String recProperty, String propValue, int numOfSecs) {
		System.out.println("Waiting " + numOfSecs + " seconds for " + propValue);
		
		GuiTestObject myGTO = (GuiTestObject)returnTO(recProperty, propValue);
		GregorianCalendar sTime = new GregorianCalendar();
		GregorianCalendar eTime = new GregorianCalendar();
		eTime.add(Calendar.SECOND, numOfSecs);
		
		Date curTime = sTime.getTime();
		Date endTime = eTime.getTime();
		System.out.println("Cur Time: " + curTime.toString());
		System.out.println("End Time: " + endTime.toString());
		
		while (curTime.compareTo(endTime) < 0) {
			if (myGTO == null) {
				sleep(1);
				curTime = new Date();
				myGTO = (GuiTestObject)returnTO(recProperty, propValue);
			} else {
				myGTO.click();
				System.out.println("Found and clicked: " + propValue);
				myGTO.unregister();
				return 0;
			}
		}
		System.out.println("Unable to find object: " + propValue);
		return -1;
	}
	 
	 /**
		 * This function will help the script to wait for a particular time for the object to appear in the screen
		 * and click on it when it appears
		 * @param myTO
		 * @param recProperty
		 * @param propValue
		 * *@author Ajit. K. Abraham
		 */
	public boolean waitForExistenceAndClick(String recProperty, String propValue) {
		boolean Status = true;
		
		try{
			sleep(2);
			TestObject[] TOs = myBrowser.find(atDescendant(recProperty, propValue));
			int numOfObjects = TOs.length;
		
			if (numOfObjects > 0) {
				GuiTestObject myGTO = (GuiTestObject) TOs[0];
				myGTO.waitForExistence(20, 0.10);
				if (myGTO.exists()) {
					myGTO.click();
					System.out.println("Clicking: " + propValue);
					browserWait(myBrowser);
					myGTO.unregister();
				}
			} else {
				Status = false;
			}
			unregister(TOs);
		} catch (Exception e) {
			LogScreenShot("WAIT & CLICK -- OBJECT NOT FOUND " + recProperty + " : " + propValue);
			Status = false;
		}
		return Status;
	}
	
	/**
	 * This function finds a test object and hovers over on it.
	 * @param myTO - Higher level object where object to be hove over will be found.
	 * 				Could be browser, HTML page, HTML table etc.
	 * @param recProperty - property used for recognition.  For example ".text", ".id", ".name"
	 * @param propValue - value for the recProperty used for recognition
	 * @author Saurabh Malhotra
	 */
	public boolean hover(TestObject myBrowser, String recProperty, String propValue) {
		boolean status = true;
		try {
			TestObject[] TOs = myBrowser.find(atDescendant(recProperty, propValue));

			if (TOs.length > 0) {
				GuiTestObject myGTO = (GuiTestObject) TOs[0];
				if (myGTO.exists()){
					myGTO.hover();
					System.out.println("HOVERED: " + recProperty + " : " + propValue);
				}
				myGTO.unregister();
				unregister(TOs);
			} else {
				throw new ObjectNotFoundException();
			}
		} catch (Exception e) {
			LogScreenShot("HOVER -- EXCEPTION OBJECT NOT FOUND " + recProperty + " : " + propValue);
			status = false;
		}
		return status;
	}
	
	public boolean hover(String recProperty, String propValue) {
		return hover(myBrowser, recProperty, propValue);
	}
	
	/**
	 * This function finds a dropdown object and selects the selection.
	 * @param myBrowser - Higher level object where object to be clicked will be found.
	 * 				Could be browser, HTML page, HTML table etc.
	 * @param recProperty - property used for recognition.  For example ".text", ".id", ".name"
	 * @param propValue - value for the recProperty used for recognition
	 * @param selection - value to be selected
	 * @author Saurabh Malhotra
	 */
	public boolean select(TestObject TO, String recProperty, String propValue, String selection) {
		boolean status = true;
		try {
			TestObject[] TOs = TO.find(atDescendant(recProperty, propValue));

			if (TOs.length > 0) {
				SelectGuiSubitemTestObject mySTO = (SelectGuiSubitemTestObject) TOs[0];
				if (mySTO.exists()) {
					mySTO.select(selection);
					myBrowser.inputKeys("{TAB}");
					browserWait(myBrowser);
					System.out.println("SELECTED: " + selection + " FROM " + recProperty + " : " + propValue);
				}
				mySTO.unregister();
				unregister(TOs);
			} else {
				throw new ObjectNotFoundException();
			}
		} catch (Exception e) {
			LogScreenShot("SELECT -- EXCEPTION OBJECT NOT FOUND " + recProperty + " : " + propValue);
			status = false;
		}
		return status;
	}
	
	public boolean select(String recProperty, String propValue, String selection) {
		return select(myBrowser, recProperty, propValue, selection);
	}
	
	/**
	 * This function finds a Input Box object and sets the text.
	 * @param myTO - Higher level object where object to be clicked will be found.
	 * 				Could be browser, HTML page, HTML table etc.
	 * @param recProperty - property used for recognition.  For example ".text", ".id", ".name"
	 * @param propValue - value for the recProperty used for recognition
	 * @param text - value to be inputed.
	 * @author Saurabh Malhotra
	 */
	public boolean setText(TestObject TO, String recProperty, String propValue, String text) {
		boolean status = true;
		try {
			TestObject[] TOs = TO.find(atDescendant(recProperty, propValue));

			if (TOs.length > 0) {
				TextGuiTestObject myGTO = (TextGuiTestObject) TOs[0];
				if (myGTO.exists()) {
					myGTO.setText(text);
					myBrowser.inputKeys("{TAB}");
					browserWait(myBrowser);
					System.out.println("SET TEXT: " + text + " IN " + recProperty + " : " + propValue);
				}
				myGTO.unregister();
				unregister(TOs);
			} else {
				throw new ObjectNotFoundException();
			}
		} catch (Exception e) {
			LogScreenShot("SET TEXT -- EXCEPTION OBJECT NOT FOUND " + recProperty + " : " + propValue);
			status = false;
		}
		return status;
	}
	
	public boolean setText(String recProperty, String propValue, String text) {
		return setText(myBrowser, recProperty, propValue, text);
	}

	/**
	 * @param myBrowser - Higher level object where object to be checked will be found
	 * @param recProperty - Object Recognition Property
	 * @param propValue - Object Recognition Property Value
	 * @param chk - if TRUE - check the check box (if already checked do NOTHING.)
	 * 				if FALSE - uncheck the check box (if already unchecked do NOTHING.)
	 * 
	 * @return boolean
	 * @author Dipen Desai
	 * @since 02/17/2009
	 */
	public boolean checkBox(TestObject myBrowser, String recProperty, String propValue, boolean chk) {
		boolean status = true;
		try {
			TestObject[] TOs = myBrowser.find(atDescendant(recProperty, propValue));
			
			if (TOs.length > 0) {
				GuiTestObject myGTO = (GuiTestObject) TOs[0];
				String checkedProp = TOs[0].getProperty(".checked").toString();
				
				if (myGTO.exists() && ((chk && checkedProp.equalsIgnoreCase("FALSE")) || (!chk && checkedProp.equalsIgnoreCase("TRUE")))) {
					myGTO.click();
					browserWait(myBrowser);
					System.out.println("STATE OF CHECK BOX CHANGED: Before - " + !chk + "\t\tAfter - " + chk);
				} else {
					System.out.println("State of Check Box matches required State: NO ACTION TAKEN");
				}
				myGTO.unregister();
				unregister(TOs);
			} else {
				throw new ObjectNotFoundException();
			}
		} catch (Exception e) {
			LogScreenShot("CHECK BOX -- EXCEPTION OBJECT NOT FOUND " + recProperty + " : " + propValue);
			status = false;
		}
		return status;
	}
	
	public boolean checkBox(String recProperty, String propValue, boolean chk) {
		return checkBox(myBrowser, recProperty, propValue, chk);
	}
	
	/**
	 * This function finds a Radio Option object and selects the specified option item.
	 * @param myTO - Higher level object where object to be clicked will be found.
	 * 				 Could be browser, HTML page, HTML table etc.
	 * @param recProperty - property used for recognition.  For example ".name"
	 * @param propValue - value for the recProperty used for recognition
	 * @param text - radio button's option value that will be selected.
	 * @author Shruti Grover
	 */
	public boolean selectRadio(TestObject myBrowser, String recProperty, String propValue, String text) {
		boolean status = true;
		System.out.println("Setting Text: " + text + "  In: " + propValue);
		
		try {
			TestObject[] TOs = myBrowser.find(atDescendant(recProperty, propValue, ".value", text));
			
			if (TOs.length > 0) {
				ToggleGUITestObject TOb = (ToggleGUITestObject) TOs[0];
				TOb.waitForExistence(2500, 0.10);
				if (TOb.exists())
					TOb.click();
				TOb.unregister();
			} else {
				throw new ObjectNotFoundException();
			}
			unregister(TOs);
		} catch (Exception e) {
			LogScreenShot("SELECT RADIO -- EXCEPTION OBJECT NOT FOUND " + recProperty + " : " + propValue);
			status = false;
		}
		return status;
	}
	
	public boolean selectRadio(String recProperty, String propValue, String text) {
		return selectRadio(recProperty, propValue, text);
	}
	
	/**
	 * This function click on the Input 'OK' button (warning messages) appear in the screen.
	 * @author Abirami Bandaru
	 */
	public void checkInputButton() {
		RootTestObject rTO = getRootTestObject();
		TestObject[] TOs = rTO.find(atDescendant(".class", "Html.INPUT.button", ".title", "Ok (Enter)"));
  		int numOfObjects = TOs.length;

  		while (numOfObjects >= 1) {
  			GuiTestObject GUITO = new GuiTestObject(TOs[0]);
  			GUITO.waitForExistence(100,0.25);
			GUITO.click();
			LogScreenShot("<font color=\"DeepPink\">" + "OK ScreenShot" + "</b></font>");
			sleep(7);
			TOs = rTO.find(atDescendant(".class", "Html.INPUT.button", ".title", "Ok (Enter)"));
	  		numOfObjects = TOs.length;
		}
	}
	
	/**
	 * This function will check to see if the browser's state is in the ready
	 * state of 4. If it is not, it will keep on waiting. If the browser never
	 * goes to ready state of 4, this function will keep on waiting.
	 * 
	 * @param myBrowser - Pass in a browser test object.
	 */
	public void browserWait(TestObject myBrowser) {
		System.out.println("Browser Wait: Current State - " + myBrowser.getProperty(".readyState").toString());
		while (!myBrowser.getProperty(".readyState").toString().equalsIgnoreCase("4")) {
			sleep(0.25);
		}
		System.out.println("Browser Wait: After State - " + myBrowser.getProperty(".readyState").toString());
	}
	
	/**
	 * This function will check to see if the browser's state is in the ready
	 * state of 4. If it is not, it will wait till it gets a ready state of 4
	 * or total no of seconds, which ever is earlier.
	 * 
	 * @param myBrowser - Pass in a browser test object.
	 * @param seconds - Max wait time in seconds.
	 */
	public void browserWait(TestObject myBrowser, int seconds) {
		GregorianCalendar stTime = new GregorianCalendar();
		GregorianCalendar enTime = new GregorianCalendar();
		
		enTime.add(Calendar.SECOND, seconds);
		
		Date curTime = stTime.getTime();
		Date endTime = enTime.getTime();
		
		while ((myBrowser.getProperty(".readyState").toString().equals("4")) && (curTime.compareTo(endTime) < 0)){
			sleep(0.25);
			curTime = new Date();
		}
	}
	
	/**
	 * This function will check to see if the Html Dialog's state is in the ready
	 * state of 2. If it is not, it will keep on waiting. If the browser never
	 * goes to ready state of 4, this function will keep on waiting.
	 * 
	 * @param myBrowser - Pass in a browser test object.
	 */
	public void dialogWait(TestObject myDialog) {
		while (myDialog.getProperty(".readyState").toString() != "2") {
			sleep(0.25);
		}
	}

	/**
	 * This function takes TestObject and a string parameter: This Function will
	 * Return the Table with '.classIndex' property in the Object Recognition
	 * Properties. Use this for Returning a Child Table under a Parent Table or
	 * Document. eg: ReturnTableObject(gto111b, txt);
	 * 
	 * @author Ajit K Abraham
	 */
	public TestObject ReturnTableObject(TestObject myTable, String Class) {
		TestObject[] TOs = myTable.find(atDescendant(".classIndex", Class));
		GuiTestObject TO = (GuiTestObject) TOs[0];
		return (TO);
	}

	/**
	 * @param myTO
	 * @param ObjType
	 * @param recProperty
	 * @param propValue
	 * @param sVPName
	 * @param sLogName
	 */
	public void myVpDynamic(TestObject myTO, String ObjType, String recProperty, String propValue, String sVPName, String sLogName) {
		TestObject[] TOs = myTO.find(atDescendant(recProperty, propValue));
		if (TOs.length > 0) {
			if ((ObjType.equalsIgnoreCase("HtmlLink")) || (ObjType.equalsIgnoreCase("PushButton"))){
				try {
					GuiTestObject TO = (GuiTestObject) TOs[0];
					TO.waitForExistence(300, 0.10);
					if (TO.exists()) {
						vpDynamic(sVPName, TO).performTest(1, 120);
						System.out.println("This IS the GUITESTOBJ" + sVPName);
						logTestResult("<font color=\"DeepPink\">" + sLogName, true,	"</b></font>");
					}
				} catch (Exception e) {
					logError("<font color=\"DeepBlue\">" + sVPName + " FAILED " + e.toString() + "</font>", getRootTestObject().getScreenSnapshot());
				}
			}
			else if ((ObjType.equalsIgnoreCase("CheckBox")) || (ObjType.equalsIgnoreCase("RadioButton"))){
				try {
					ToggleGUITestObject TO = (ToggleGUITestObject) TOs[0];
					TO.waitForExistence(300, 0.10);
					if (TO.exists()) {
						vpDynamic(sVPName, TO).performTest(1, 120);
						System.out.println("This IS the ToggleGUITestObject" + sVPName);
						logTestResult("<font color=\"DeepPink\">" + sLogName, true, "</b></font>");
					}
				} catch (Exception e) {
					logError("<font color=\"DeepBlue\">" + sVPName + " FAILED " + e.toString() + "</font>", getRootTestObject().getScreenSnapshot());
				}
			}
			else if (ObjType.equalsIgnoreCase("EditBox")){
				try {
					TextGuiTestObject TO = (TextGuiTestObject) TOs[0];
					TO.waitForExistence(300, 0.10);

					if (TO.exists()) {
						vpDynamic(sVPName, TO).performTest(1, 120);
						System.out.println("This IS the TextGuiTestObject" + sVPName);
						logTestResult("<font color=\"DeepPink\">" + sLogName, true, "</b></font>");
					}
				} catch (Exception e) {
					logError("<font color=\"DeepBlue\">" + sVPName + " FAILED " + e.toString() + "</font>", getRootTestObject().getScreenSnapshot());
				}
			}
			else if (ObjType.equalsIgnoreCase("ListBox")){
				try {
					SelectGuiSubitemTestObject TO = (SelectGuiSubitemTestObject) TOs[0];
					TO.waitForExistence(300, 0.10);

					if (TO.exists()) {
						vpDynamic(sVPName, TO).performTest(1, 120);
						System.out.println("This IS the SelectGuiSubitemTestObject"	+ sVPName);
						logTestResult("<font color=\"DeepPink\">" + sLogName, true,	"</b></font>");
					}
				} catch (Exception e) {
					logError("<font color=\"DeepBlue\">" + sVPName + " FAILED "	+ e.toString() + "</font>", getRootTestObject().getScreenSnapshot());
				}
			}
		}
		else 
			logError("<font color=\"DeepBlue\">" + sVPName + " Failed: Object not found" + "</font>", getRootTestObject().getScreenSnapshot());
	}
	
	/**
	 * This function takes a TestObject and 4 String parameters: This Function
	 * will check the given Date with Date in the Date Field. If the given Date is prior to
	 * the Date in the Date Field, the function will click on the Next button.
	 * eg: checkDatesclickNext(myBrowser, recProperty, propValue, nValue, sVPName);
	 * myBrowser, recProperty, propValue - table id, nValue - button id
	 * @author Ajit K Abraham
	 */
	public boolean checkDatesclickNext(TestObject myTO, String recProperty, String propValue, String date, String next) {
		boolean status = true;
		
		try {
			Date dtTmp ;
			Date dtTmp1;
			TestObject[] TOs = myTO.find(atDescendant(recProperty, propValue));
			if (TOs.length > 0){
				TextGuiTestObject TO = (TextGuiTestObject) TOs[0];
				//Checks for the Existence of Object
				TO.waitForExistence(300, 0.10);
				if (TO.exists()) {
					String firstDate = TO.getText();
					dtTmp = new SimpleDateFormat("MM/dd/yyyy").parse(firstDate);
					dtTmp1 = new SimpleDateFormat("MM/dd/yyyy").parse(date);
					System.out.println("The First Date is :" + dtTmp );
					System.out.println("The Given Date is :" + dtTmp1);
					
					while (dtTmp.compareTo(dtTmp1)> 0)
					{
						TestObject[] TOn = myTO.find(atDescendant(recProperty, next));
						
						if (TOn.length > 0){
							GuiTestObject TOt = (GuiTestObject) TOn[0];
							TOt.waitForExistence(300,0.10);
							TOt.click();
							sleep(5);
							
							TestObject[] TO2 = myTO.find(atDescendant(recProperty, propValue));
							if (TO2.length > 0){
								TextGuiTestObject TOa = (TextGuiTestObject) TO2[0];
								//Checks for the Existence of Object
								TOa.waitForExistence(300, 0.10);
								if (TOa.exists()) {
									String firstDate1 = TOa.getText();
									Date dtTmp2 = new SimpleDateFormat("MM/dd/yyyy").parse(firstDate1);				
									
									dtTmp = dtTmp2;
									System.out.println(dtTmp);
								}
								TOa.unregister();
							}
							else
								System.out.println("Unable to Find the Object Second Time");
							unregister(TO2);
							TOt.unregister();
						}
						unregister(TOn);
					}
					TO.unregister();
				}
				unregister(TOs);
			}
		}
		catch (Exception e) {
			System.out.println("The TextGuiTestObject could not be found");
			logError("<font color=\"DeepBlue\">" + "checkDatesclickNext  FAILED " + e.toString() + "</font>", getRootTestObject().getScreenSnapshot());
		}
		
		return status;
	}
	
	/**
	 * This function takes a TestObject and 3 String parameters: 
	 * This Function get the current value of the Drop down list box 
	 * @param myTO - Top level test object
	 * @param recProperty - Property type of the drop down list box
	 * @param propValue - Property value of the recProperty
	 * @author Ajit K Abraham
	 */
	public String GetFldValueDdownLBox(TestObject myTO, String recProperty, String propValue) {
		String ListBoxValue = "";
		
		TestObject[] TOs = myTO.find(atDescendant(recProperty, propValue));
		if (TOs.length > 0){
			SelectGuiSubitemTestObject TO = (SelectGuiSubitemTestObject) TOs[0];
			TO.waitForExistence(10, 0.10);
			if (TO.exists()){ 
				ListBoxValue = TO.getSelectedText();
			}
			TO.unregister();
		}	
		unregister(TOs);
		return ListBoxValue;
	}

	/**
	 * This function takes a TestObject and 4 String parameters
	 * This Function tokenize the values in gvnValue and compare with value in drop down list box and returns a boolean value
	 * based on the comparison
	 * Returns true if matches, otherwise returns false
	 * @param myTO - Top level test object
	 * @param recProperty - Property type of the drop down list box
	 * @param propValue - Property value of the recProperty
	 * @param gvnValue - List of values to compare with value in drop down list box
	 * @param Jobindicator - If the gvnValue is empty, compare the value of Jobindicator with jobindicator field value from the application
	 * 
	 * e.g.Compare the leave status with given value if it is there, otherwise compare the jobindicator and returns a flag
	 * @author Abirami Bandaru
	 */	
	public boolean CompareListBoxValue(TestObject myTO, String recProperty, String propValue, String gvnValue, String Jobindicator){
		//Get the count of different list box values to compare
		StringTokenizer st = new StringTokenizer(gvnValue, ",");
		int noOfToken = st.countTokens();
		
		String [] gvnFldValues = new String[noOfToken];
		//Get the Given List Box values to compare
		int i=0;
		
		while (st.hasMoreTokens()) {
			gvnFldValues[i] = st.nextToken();
			i++;
		}
		
		//If Leave Status is not specified in excel, check for Job Indicator='Primary Job'
		if (gvnFldValues.length < 1) {
			gvnFldValues = new String[1];
			gvnFldValues[0] = Jobindicator;
		    gvnValue = Jobindicator;
		    propValue = "JOB_JOB_INDICATOR$0";
		    noOfToken = 1;
		}
			
		//Get the current List box value from Application
		String ListBoxValue = GetFldValueDdownLBox(myTO,recProperty,propValue);
		boolean Flag = false;
		for ( i=0; i < noOfToken; i++) {
			if (ListBoxValue.startsWith(gvnFldValues[i]))
				Flag = true;
		}
		return Flag;
	}
	
	/**
	 * This function finds a Input Box object and gets the text.
	 * @param myTO - Higher level object where object to be clicked will be found.
	 * 				Could be browser, HTML page, HTML table etc.
	 * @param recProperty - property used for recognition.  For example ".text", ".id", ".name"
	 * @param propValue - value for the recProperty used for recognition
	 * @author Abirami Bandaru
	 */
	public String getTextData(TestObject myTO, String recProperty, String propValue) {
		String text = "";
		
		try {
			TestObject[] TOs = myTO.find(atDescendant(recProperty, propValue));

			int numOfObjects = TOs.length;
			if (numOfObjects >= 1) {
				TextGuiTestObject myGTO = (TextGuiTestObject) TOs[0];
				myGTO.waitForExistence(2500, 0.10);
				if (myGTO.exists())
					text = myGTO.getText();
					System.out.println("inside gettext:"+text);
				myGTO.unregister();
			} else {
				System.out.println("Unable to find GUI TestObject: " + recProperty + " : " + propValue);
				logError("Unable to find GUI TestObject: " + recProperty + " : " + propValue, getRootTestObject().getScreenSnapshot());
			}
			unregister(TOs);
		} catch (Exception e) {
			System.out.println("Unable to set Text GUI TestObject" + e.toString());
		}
		return text;
	}
	
	/**
	 * This function finds a GuiTest Object like PushButton with property = ".value" or HTML Link with property = ".text" and gets the text.
	 * @param myTO - Higher level object where object to be clicked will be found.
	 * 				Could be browser, HTML page, HTML table etc.
	 * @param recProperty - property used for recognition.  For example ".text", ".value", ".name"
	 * @param propValue - value for the recProperty used for recognition
	 * @author Ajit K Abraham
	 */
	public String getTextOfGuiTestObject(TestObject myTO, String recProperty,String propValue) {
		String text = "";
		try {
			TestObject[] TOs = myTO.find(atDescendant(".name", propValue));

			int numOfObjects = TOs.length;
			if (numOfObjects >= 1) {
				GuiTestObject myGTO = (GuiTestObject) TOs[0];
				myGTO.waitForExistence(120, 0.10);
				if (myGTO.exists())
					text = myGTO.getProperty(recProperty).toString();
				myGTO.unregister();
			} else {
				System.out.println("Unable to find GUI TestObject: " + propValue);
				logError("Unable to find GUI TestObject: " + propValue, getRootTestObject().getScreenSnapshot());
			}
			unregister(TOs);

		} catch (Exception e) {
			System.out.println("Unable to Find GUI TestObject" + e.toString());
		}
		return text;
	}
	
	/**
	 * This function will return ".text" value of a specific cell defined by (columnIndex, rowIndex) 
	 * of the Table.
	 * 
	 * @param propValue - ".className" of the Table
	 * @param rowIndex - Row Index
	 * @param colIndex - Column Index
	 * @return String = ".text" property of the cell
	 * @author Dipen Desai
	 * @since 04/14/2009
	 */
	public String getCellTextProp(String propValue, int rowIndex, int colIndex) {
		String rTxt = null;
		
		try {
			TestObject[] nT = myBrowser.find(atDescendant(".className", propValue));
			
			if(nT.length > 0) {
				StatelessGuiSubitemTestObject table = (StatelessGuiSubitemTestObject) nT[0];
				GuiTestObject myCell = (GuiTestObject) table.getSubitem(atCell(atRow(atIndex(rowIndex)), atColumn(atIndex(colIndex))));
				rTxt = myCell.getProperty(".text").toString().trim();
				
				myCell.unregister();
				table.unregister();
				unregister(nT);
			} else {
				log("Table is NOT found in NYCAPS");
			}
		} catch (Exception ex) {
			log("Exception Occurred while getting Cell Text Property of the Table " + propValue);
			ex.printStackTrace();
		}
		return rTxt;
	}
	
	/**
	 * This function will return ".text" value of a specific cell defined by (columnName, rowIndex) 
	 * of the Table.
	 * 
	 * @param propValue - ".className" of the Table
	 * @param rowIndex - Row Index
	 * @param colName - Column name
	 * @return String = ".text" property of the cell
	 * @author Dipen Desai
	 * @since 04/14/2009
	 */
	public String getCellTextProp(TestObject TO, String propValue, int rowIndex, String colName) {
		String rTxt = null;
		
		try {
			TestObject[] nT = TO.find(atDescendant(".className", propValue));
			
			if(nT.length > 0) {
				StatelessGuiSubitemTestObject table = (StatelessGuiSubitemTestObject) nT[0];
				GuiTestObject myCell = (GuiTestObject) table.getSubitem(atCell(atRow(atIndex(rowIndex)), atColumn(colName)));
				rTxt = myCell.getProperty(".text").toString().trim();
				
				myCell.unregister();
				table.unregister();
				unregister(nT);
			} else {
				log("Table is NOT found in NYCAPS");
			}
		} catch (Exception ex) {
			log("Exception Occurred while getting Cell Text Property of the Table " + propValue);
			ex.printStackTrace();
		}
		return rTxt;
	}
	
	// Userful with Default Browser Window
	public String getCellTextProp(String propValue, int rowIndex, String colName) {
		return getCellTextProp(myBrowser, propValue, rowIndex, colName);
	}

	/**
	 * This function takes 2 String parameters: This Function will return an instance of the EditBox object.
	 * @author Ajit K Abraham
	 */
	public TextGuiTestObject getTextGuiObject(TestObject n, String propValue) {
		TestObject[] TOn1 = myBrowser.find(atDescendant(".name", propValue));
		int noofObjects = TOn1.length;
		System.out.println(noofObjects);
		TextGuiTestObject myGTO2 = (TextGuiTestObject) TOn1[0];
		myGTO2.waitForExistence(60,0.10);
		return myGTO2;
	}

		
	/**
	 * This function takes a String parameter: This Function will get the text value of a table.
	 * @author Ajit K Abraham
	 */
	public String getTextofTable(TestObject myBrowser, String propValue){
		TestObject[] TOnew = null;
		StatelessGuiSubitemTestObject myGTO = null;
		
		try {
			browserWait(myBrowser);
			TOnew = myBrowser.find(atDescendant(".class", "Html.TABLE",".className" ,propValue));
			myGTO = (StatelessGuiSubitemTestObject) TOnew[0];
			myGTO.waitForExistence(60,0.10);
			
			// Assigning '.text' property of the table to String
			String sentence = myGTO.getProperty(".text").toString();
			unregister(TOnew);
			myGTO.unregister();
			return sentence;
		} catch (Exception getTextE) {
			System.out.println("Exception Occurred while getting Text from TABLE: " + propValue);
			getTextE.printStackTrace();
			unregister(TOnew);
			myGTO.unregister();
			return null;
		}
	}
		
	/**
	 * This function takes a TestObject and 4 String parameters
	 * This Function tokenize the gvnValue which is to be excluded in the random selection
	 * @param myTO - Top level test object
	 * @param recProperty - Property type of the drop down list box
	 * @param propValue - Property value of the recProperty
	 * @param gvnValue - List of values to be excluded in the random selection
	 * @param crText - Current value from the application 
	 * @author Abirami Bandaru
	 */	
	public String[] stringTokenizeGvnValue(TestObject myTO, String recProperty, String propValue, String gvnValue, String crText) {
		//Get the count of different given values to be excluded
		StringTokenizer st = new StringTokenizer(gvnValue, ",");
		int noOfToken = st.countTokens();
		
		String [] gvnFldValues = new String[noOfToken+1];
		
		//Get the Given excluded values
		int i = 0;
		while (st.hasMoreTokens()) {
			gvnFldValues[i] = st.nextToken();
			System.out.println("Given values are: " + gvnFldValues[i]);
			System.out.println("Value of i: " + i);
			i++;
		}
		System.out.println("crtext value: " + crText);
		gvnFldValues[i] = crText;
		System.out.println("application value is: " + gvnFldValues[i]);
		
		return gvnFldValues;
	}
	
	/**
	 * This function takes a TestObject and 4 String parameters
	 * This function goes to next empl_rcd and compare the dropdown list box value with the given value
	 * 
	 * @param myTO - Top level test object
	 * @param recProperty - Property type of the drop down list box
	 * @param propValue - Property value of the recProperty
	 * @param gvnValue - List of values to compare with value in drop down list box
	 * @param Jobindicator - If the gvnValue is empty, compare the value of Jobindicator with jobindicator field value from the application
	 * @param next - property value of 'Next in List' push button
	 *  
	 * e.g.If the flag is false(no values are matching in first empl_rcd), go to next empl_rcd if 'Next in List' button is enabled and compare the value
	 * in 'CompareListBoxValue' function. Do it in a loop till 'Next in List' button is disabled
	 * @author Abirami Bandaru
	 */	
	public boolean CompareLBoxvaluesinAllEmplrcd(TestObject myTO, String recProperty, String propValue, String gvnValue, String Jobindicator,String next) {
		boolean status = true;
		boolean flag = CompareListBoxValue(myTO,recProperty,propValue,gvnValue,Jobindicator);
		
		GregorianCalendar sTime = new GregorianCalendar();
		GregorianCalendar eTime = new GregorianCalendar();
		eTime.add(Calendar.SECOND, 10);
		
		Date curTime = sTime.getTime();
		Date endTime = eTime.getTime();
		System.out.println("Cur Time: " + curTime.toString());
		System.out.println("End Time: " + endTime.toString());
		
		System.out.println("Flag is "+flag);
		
		while (flag == false && (curTime.compareTo(endTime)) < 0 ) {
			sleep(1);
			curTime = new Date();
			
			//Check the 'Next in List' push button is enabled and click on it and verify the field value with the given value
			//until it hits the last empl_rcd
			TestObject[] TOn = myTO.find(atDescendant(recProperty, next));
			
			if (TOn.length > 0){
				GuiTestObject TOt = (GuiTestObject) TOn[0];
				TOt.waitForExistence(300,0.10);
				String enableProp = TOt.getProperty(".disabled").toString();
				if (enableProp == "false") {
					TOt.click();
					sleep(7);
					flag = CompareListBoxValue(myTO,recProperty,propValue,gvnValue,Jobindicator);
					System.out.println("II - Flag is "+flag);
				} else {
					logError("<font color=\"DeepPink\">Invalid Leave Status/Job Indicator for termination, Select different Employee ID",getRootTestObject().getScreenSnapshot());
				    flag = true;
				    status = false;
				}
				TOt.unregister();
			}
			unregister(TOn);
		}
		return status;
	}
	
	/**
	 * This Function will log a message in Playback log as well as Console.
	 * 
	 * @author Dipen Desai
	 */
	public void log(String x) {
		System.out.println(x);
		logInfo("<font color=#" + Integer.toHexString((int) (Math.random() * 16777215)) + ">" + x + "</b></font>");
	}
	
	/**
	 * This function takes a String parameter: This Function will Log a screen shot
	 * in the PlayBack Log of the Script . eg: LogResult(sLog);
	 * 
	 * @author Ajit K Abraham
	 * @author desaid - Modified 02/26/2009
	 */
	public void LogScreenShot(String message) {
		sleep(3);
		logInfo("<font color=#" + Integer.toHexString((int) (Math.random() * 16777215)) + ">" + message + "</b></font>", getRootTestObject().getScreenSnapshot());
		System.out.println(message);
	}

	/**
	 * This function takes a TestObject and 2 String parameters: This Function
	 * will Refresh the Run Report Table till the Run Status shows Success. eg:
	 * RefreshTableTillSuccess(gto111b, txt, nValue1);
	 * myBrowser, recProperty, propValue - table id, nValue - button id
	 * @author Ajit K Abraham
	 */
	public boolean RefreshTableTillSuccess(TestObject myTO, String recProperty, String propValue, String nValue1) {
		boolean status = true;
		String sStatus = "";
		int count = 0;
		
		System.out.println("Table: " + propValue);
		System.out.println("Refresh Button: " + nValue1);
		
		try {
			do {
				count++;
				TestObject[] TableObjs = myTO.find(atDescendant(recProperty, propValue));
				
				if (TableObjs.length >= 1 ) {
					StatelessGuiSubitemTestObject myTable = (StatelessGuiSubitemTestObject) TableObjs[0];
					TestObject[] TOrefresh = myTO.find(atDescendant(recProperty, nValue1));
					
					if (TOrefresh.length >= 1) {
						GuiTestObject TOr = (GuiTestObject) TOrefresh[0];
						count++;
		
						GuiTestObject myCell = (GuiTestObject) myTable.getSubitem(atCell(atRow(2), atColumn(7)));
						sStatus = myCell.getProperty(".text").toString().trim();
						
						TOr.click(); browserWait(myBrowser);
						
						System.out.println("Process - Run Status is " + sStatus);
						sleep(6);
					} else {
						System.out.println("Refresh Button not found: " + nValue1);
						status = false;
					}
				} else {
					System.out.println("Table not found: " + propValue);
					status = false;
				}
			} while ((sStatus.compareTo("Success") != 0) && (sStatus.compareTo("No Success") != 0) && (sStatus.compareTo("Error") != 0) && (count <= 30));
		} catch (Exception e) {
			System.out.println("Exception occured in NYCAPSHelper.RefreshTableTillSuccess() : " + e.toString());
		}
		return status;
	}
	
	/**
	 * This function takes 2 String parameters: This Function will return the boolean value depending upon the existence of the GuiTestObject object.
	 * @author Ajit K Abraham
	 */
	public boolean existGuiTestObject(TestObject n, String propValue) {
		boolean exist = true;
		browserWait(myBrowser);
		TestObject[] TOn1 = myBrowser.find(atDescendant(".name", propValue));
		GuiTestObject myGTO2 = (GuiTestObject) TOn1[0];
		myGTO2.waitForExistence(60,0.10);
		if(myGTO2.exists()){
				exist = true;
			}
		else
			exist = false;
		return exist;
	}

	/**
	 * This function takes a TestObject and a String parameter: This Function
	 * will close a new Browser or a second browser. eg:
	 * newBrowserClose(gto111b, txt);
	 * 
	 * @author Ajit K Abraham
	 */

	public void newBrowserClose(TestObject TestObj, String txt) {
		BrowserTestObject btoX;
		try {
			TestObject[] TOs = TestObj.find(atDescendant(".class", txt));
			System.out.println(txt);
			GuiTestObject TO = (GuiTestObject) TOs[0];

			TO.waitForExistence(100, 0.10);

			if (TO.exists()) {
				TestObject TOn = TO.getTopParent();
				btoX = (BrowserTestObject) TOn;
				btoX.maximize();
				sleep(3);
				btoX.close();
				System.out.println("The NewBrowserClose IS CLOSED");
			}

		} catch (Exception e333444) {
			System.out.println("The NewBrowserClose Failed");
		}
	}
	
	/**
	 * This function takes a TestObject and 2 String parameters: This Function
	 * will Check the Property of a ToggleGUI TestObject such as
	 * CheckBox/RadioButton. eg: CheckTogglePropertyState(gto111b, txt, nValue1);
	 * 
	 * @author Ajit K Abraham
	 */
	public String CheckTogglePropertyState(TestObject myObj, String txt, String nValue) {
		String result = "false";

		TestObject[] TOs = myObj.find(atDescendant(".id", txt));
		System.out.println(txt);

		ToggleGUITestObject TO = (ToggleGUITestObject) TOs[0];
		//Checks for the Existence of Object
		TO.waitForExistence(300, 0.10);
		if (TO.exists()) {
			result = TO.getProperty(nValue).toString().trim();
			System.out.println("THE " + nValue + " PROPERTY OF THIS OBJECT IS " + result);
		} else {
			System.out.println("THE CHECKTOGGLEPROPERTY FUNCTION FAILED");
		}
		
		TO.unregister();
		unregister(TOs);
		
		return result;
	}

	/**
	 * This function takes a TestObject and 2 String parameters: This Function
	 * will Check the Property of a GUI TestObject such as EditBox. eg:
	 * CheckGUIPropertyState(gto111b, txt, nValue1);
	 * 
	 * @author Ajit K Abraham
	 */
	public String CheckGUIPropertyState(TestObject myObj, String txt, String nValue) {
		String result = "false";

		TestObject[] TOs = myObj.find(atDescendant(".id", txt));
		System.out.println(txt);

		GuiTestObject TO = (GuiTestObject) TOs[0];
		//Checks for the Existence of Object
		TO.waitForExistence(300, 0.10);
		if (TO.exists()) {
			result = TO.getProperty(nValue).toString().trim();
			System.out.println("THE " + nValue + " PROPERTY OF THIS OBJECT IS "	+ result);
		} else {
			System.out.println("THE CHECKGUIPROPERTY FUNCTION FAILED");
		}
		return result;
	}

	/**
	 * This function takes a TestObject and 4 String parameters: This Function
	 * will extract the ProcessInstanceID from the table. eg: processID =
	 * getProcessInstanceID(gto111b,txt,nValue1,sVPName,sLog );
	 * 
	 * @author Ajit K Abraham
	 */
	public String getProcessInstanceID(TestObject myTable, String txt, String nValue, String nValue1, String nValue2) {
		TestObject[] TOs = myTable.find(atDescendant(".class", txt,	".className", nValue));
		System.out.println(txt + nValue);

		StatelessGuiSubitemTestObject TO = (StatelessGuiSubitemTestObject) TOs[0];
		TO.waitForExistence(100,0.10);
		
		String processInstance = "";
		String process = TO.getProperty(".text").toString();
		System.out.println("emplid is:"+nValue1);
		System.out.println("property is:"+nValue2);
		System.out.println("The Data From the Table is :" + process);
		processInstance = parseString(process, nValue1, nValue2);

		System.out.println("The Value of the ID is " + processInstance);

		return processInstance;
	}
	
	/**
	*This Function will return a log display depending on the boolean value.
	*This will check for the Object id: in the newly opened browser and check for the string
	*to find if its present there.
	*/
	public String getSpecifiedSetString(TestObject myTable, String txt, String nValue, String nValue1, String nValue2) {
		sleep(5);
		
		try {
			TestObject[] TOs = myTable.find(atDescendant(".text", nValue));
			System.out.println(nValue);
			GuiTestObject TO = (GuiTestObject) TOs[0];

			sleep(5);
			TO.waitForExistence(600, 0.10);

			if (TO.exists()) {
				TestObject to99 = TO.getTopParent();
				btoX = (BrowserTestObject)to99;
				System.out.println(btoX.getProperties().toString());
	    
				browserWait(btoX);
				btoX.maximize();
			}
		} catch (Exception e333444) {
			System.out.println("The NewBrowser Failed");
		}
		
		TestObject[] TOs = btoX.find(atDescendant(".class", txt));
		System.out.println(txt + nValue);

		StatelessGuiSubitemTestObject TO = (StatelessGuiSubitemTestObject) TOs[0];
		sleep(5);
		TO.waitForExistence(1000,0.10);
		
		String processInstance = "";
		String process = TO.getProperty(".text").toString();
		
//		String lastWord = getLastWord(process);
		String lastWord = getToken(process, -1);
		System.out.println("emplid is:"+nValue1);
		System.out.println("property is:"+nValue2);
		System.out.println("The Data From the Table is :" + process);
		
		processInstance = nValue1 + " " + parseString(process, nValue1, lastWord);
		
		System.out.println("The Value of the processInstance is " + processInstance);
				
		String setOfString = nValue1 + " " + parseString(processInstance, nValue1, nValue2);
		if (setOfString == null) {
			System.out.println("The ID is Invalid For This Employee"); 
		}
		System.out.println("The Value of the ID is " + setOfString);
		return setOfString;
	}

	public boolean verifyEditBoxValue(TestObject myBrowser, String recProperty, String propValue, String nValue) {
		boolean value = true;
		
		TestObject[] TOs = myBrowser.find(atDescendant(".id", propValue));
		System.out.println(propValue);
		TextGuiTestObject TO = (TextGuiTestObject) TOs[0];
		
		//Checks for the Existence of Object
		TO.waitForExistence(300, 0.10);
		if (TO.exists()) {
			String result = TO.getText();
			System.out.println("THE " + propValue + " TEXT INSIDE THIS FIELD IS "	+ result);
			
			System.out.println("THE ENTERED EXCEL VALUE IS " + nValue);
			
			if (value = (result.compareTo(nValue)) == 0) {
				System.out.println("THE ENTERED VALUE IS MATCHING!!!!");
				LogScreenShot("<font color=\"DeepPink\">THE ENTERED VALUE " +nValue+ " IS MATCHING!!!!</font>");
			} else {
				System.out.println("THE ENTERED VALUE DOESNT MATCH");
				LogScreenShot("<font color=\"DeepPink\">The Value in The TextBox Is Not Matching with the Value Entered From Excel; Please ReCheck</font>");
				value = false;
			}
		}
		return value;
	}
	
	public boolean verifyListBoxValue(TestObject myBrowser, String recProperty, String propValue, String nValue) {
		boolean value = true;
		
		TestObject[] TOs = myBrowser.find(atDescendant(".id", propValue));
		System.out.println(propValue);
		SelectGuiSubitemTestObject TO = (SelectGuiSubitemTestObject) TOs[0];
	
		//Checks for the Existence of Object
		TO.waitForExistence(300, 0.10);
		if (TO.exists()) {
			String result = TO.getSelectedText();
			System.out.println("THE " + propValue + " TEXT INSIDE THIS FIELD IS "	+ result);
			
			System.out.println("THE ENTERED EXCEL VALUE IS "	+ nValue);
			
			if (value = (result.compareTo(nValue)) == 0) {
				System.out.println("THE ENTERED VALUE IS MATCHING!!!!");
				LogScreenShot("<font color=\"DeepPink\">THE ENTERED VALUE IS MATCHING!!!!</font>");
			} else {
				System.out.println("THE ENTERED VALUE DOESNT MATCH");
				LogScreenShot("<font color=\"DeepPink\">The Value in The TextBox Is Not Matching with the Value Entered From Excel; Please ReCheck</font>");
				value = false;
			}
		}
		return value;
	}
	
	/**
	 * This function returns the typical property of the table based on the
	 * recognition property and the property value provided.  If the .text property 
	 * is not found null will be returned.
	 * 
	 * e.g. getpropertyTable(myBrowser, ".name", "EFIELD_FLD$0", ".defaultValue) - will return '.defaultValue' property
	 * of the object which has .name = EFIELD_FLD$0.
	 * 
	 * @param prop - property value which will be converted to string and returned
	 * @param recProperty - recognition property to be used (eg. ".class")
	 * @param propValue - property value for the recognition property (eg. "HTML.HTMLBrowser")
	 * @return String
	 * @author Dipen Desai
	 */
	public String getPropertyTable(TestObject myBrowser, String recProperty, String propValue, String prop) {
		TestObject[] TOs = myBrowser.find(atDescendant(recProperty, propValue));
		System.out.println(propValue);
		
		GuiTestObject gTO = (GuiTestObject) TOs[0];
		return gTO.getProperty(prop).toString();
	}
	
	/**
	 * This function returns the .text property of the object based on the
	 * recognition property and the property value provided.  If the .text property 
	 * is not found null will be returned.
	 * 
	 * @param recProperty - recognition property to be used (eg. ".class")
	 * @param propValue - property value for the recognition property (eg. "HTML.HTMLBrowser")
	 * @return
	 * @author Abirami Bandaru
	 */
	public String getPropertyObject(TestObject myBrowser, String recProperty, String propValue) {
		TestObject[] TOs = myBrowser.find(atDescendant(recProperty, propValue));
		System.out.println(propValue);
		GuiTestObject TO = (GuiTestObject) TOs[0];
		String text = null;
		text = TO.getProperty(".text").toString();
		return text;
	}
	
	/**
	 * This Function will click 'OK' on all secuirty dialogs.
	 * 
	 * @author Ajit Abraham
	 */
	public void checkDialogBox() {
		RootTestObject rTO = getRootTestObject();
		TestObject[] TOs = rTO.find(atDescendant(".class", "Html.DialogButton", ".text", "OK"));
  		int numOfObjects = TOs.length;

  		while (numOfObjects >= 1) {
  			GuiTestObject GUITO = new GuiTestObject(TOs[0]);
  			GUITO.waitForExistence(30,0.25);
			GUITO.click();
			TOs = rTO.find(atDescendant(".class", "Html.DialogButton", ".text", "OK"));
	  		numOfObjects = TOs.length;
	 		sleep(3);
		}
  		System.out.println("\n\n\n\nDone with Clickgin 'OK' on Popup Message\n\n\n\n");
	}
	
	/**
	 * This Function will click 'YES' on all secuirty dialogs.
	 * 
	 * @return void
	 * @author Dipen Desai
	 * Created On: 7/2/2008
	 */
	public void checkSecurityBox() {
		RootTestObject rTO = getRootTestObject();
		TestObject[] TOs = rTO.find(atDescendant(".class", "Html.DialogButton", ".text", "&Yes"));
  		int numOfObjects = TOs.length;
  		
  		while (numOfObjects >= 1) {
  			GuiTestObject GUITO = new GuiTestObject(TOs[0]);
  			GUITO.waitForExistence(30,0.25);
			GUITO.click();
			TOs = rTO.find(atDescendant(".class", "Html.DialogButton", ".text", "&Yes"));
	  		numOfObjects = TOs.length;
	 		sleep(3);
		}
	}
	
	/**
	 * This Function will click 'Cancel' button for dialogs boxes.
	 * 
	 * @return void
	 * @author Dipen Desai
	 * Created On: 7/2/2008
	 */
	public void checkCancelBox() {
		RootTestObject rTO = getRootTestObject();
		TestObject[] TOs = rTO.find(atDescendant(".class", "Html.DialogButton", ".text", "Cancel"));
  		int numOfObjects = TOs.length;

  		while (numOfObjects >= 1) {
  			GuiTestObject GUITO = new GuiTestObject(TOs[0]);
  			GUITO.waitForExistence(30,0.25);
			GUITO.click();
			TOs = rTO.find(atDescendant(".class", "Html.DialogButton", ".text", "Cancel"));
	  		numOfObjects = TOs.length;
	 		sleep(3);
		}
	}
	
	/**
	 * This function will click all 'OK' button appears on screen in a loop.
	 * 
	 * @return boolean
	 * @author Dipen Desai
	 * @since 04/13/2009
	 */
	public boolean clickAllOK() {
		boolean x = true;
		do {
			x = click(".name", "#ICOK");
		} while(x);
		return x;
	}
	
	
	/**
	 * This function add the run control ID if it is not available. 
	 * 
	 * @param recProperty - recognition property to be used (eg. ".class")
	 * @param propValue - property value for the recognition property (eg. "HTML.HTMLBrowser")
	 * @return
	 * @author Abirami Bandaru
	 */
	public void createRunControlID(TestObject myBrowser, String recProperty, String propValue) {
		String runControlID = "RS";
		
		setText(recProperty, propValue, runControlID);
		//Click on Search
		TestObject[] TOs = myBrowser.find(atDescendant(".id", "#ICSearch"));
		GuiTestObject TO = (GuiTestObject) TOs[0];
		TO.click();
		sleep(2);

		browserWait(myBrowser);
		//Get the text of the form
		TestObject[] TOf = myBrowser.find(atDescendant(".name", "win0"));
		GuiTestObject FO = (GuiTestObject) TOf[0];
		String txt = FO.getProperty(".text").toString();
		
		//Add a Run Control ID if it is not available
		int fnd =  txt.indexOf("No matching values were found");

		if (fnd != -1) {
			//Click on Add New Value Tab
			TestObject[] TOt = myBrowser.find(atDescendant(".name", "tab1"));
			GuiTestObject tab = (GuiTestObject) TOt[0];
			tab.click();
			sleep(2);
			
			//Click Add Button
			TestObject[] TOadd = myBrowser.find(atDescendant(".id", "#ICSearch"));
			GuiTestObject addBt = (GuiTestObject) TOadd[0];
			addBt.click();
		}
	}
	
	/**
	 * This function returns the 1st instance of a test object based on the 
	 * recognition property and the property value provided.  If the Test object 
	 * is not found null will be returned.
	 * 
	 * @param recProperty - recognition property to be used (eg. ".class")
	 * @param propValue - property value for the recognition property (eg. "HTML.HTMLBrowser")
	 * @return
	 * @author Saurabh Malhotra
	 */
	public static TestObject returnTO(String recProperty, String propValue) {
		
		RootTestObject rTO = getRootTestObject();
		TestObject[] TOs = rTO.find(atDescendant(recProperty, propValue));
		TestObject returnTO = null;
		int numOfObjects = TOs.length;
		if (numOfObjects >= 1) {
			returnTO = TOs[0];
		}
		
		return returnTO;
	}
	
	
	
	//This Function is to Find a Specific String from the Set of String Values starting from last word 
	// and eliminating the spaces and words to reach the specific string.
	public String FindAString(TestObject myBrowser,String nValue, String txt, int count, String position, String message, String tex) {
	 	int positionNo = tex.length();
			
	 	System.out.println("The Length of the String is : " + positionNo);
	 	
		int posit = stringToInt(position);
		int positnew = (positionNo - posit);
		
		System.out.println("The Position of the New String is : " + positnew);
		System.out.println("The whole String is "+ tex);
		
		sleep(1);		
		int pos = tex.lastIndexOf(",");
		String myStr = tex.substring(0,pos);
		System.out.println("This is the last But One of this String : " + myStr);
		int pos1 = myStr.lastIndexOf(" ");
		String myStr1 = myStr.substring(0,pos1);
		System.out.println("This is the Second last But One of this String : " + myStr1);
		
		sleep(1);
		int pos2 = myStr1.lastIndexOf(" ");
		String myStr2 = myStr1.substring(0,pos2);
		System.out.println("This is the Third last But One of this String : " + myStr2);
		
		String EmpID = getToken(myStr2, -1);

		if(EmpID.compareTo(txt) != 0) {
			logError("<font color=\"DeepPink\">The Employee ID's are not Matching</b></font>" + txt + " " + EmpID);
		}
	
	  	if (! "".equals(myStr2))
			logTestResult("<font color=\"DeepPink\">The Data Displayed is ", true, "<font color=\"BlueViolet\">Employee ID" + count +": <B>" + EmpID + "</B></font>\n");
		else 
			logError("<font color=\"DeepPink\">The Process has not been successful</font>", getRootTestObject().getScreenSnapshot());
	  			
		logInfo("<font color=\"DeepPink\">" + message + "</b></font>" + EmpID);
		return EmpID;
	}
	
	/**
	 * This will extract the specific ID from the Table and give as the output in the LogFile.
	 * @param myBrowser
	 * @param nValue
	 * @param txt
	 * @param count
	 * @param position
	 * @return
	 */
	 public String ExtractTableValue(TestObject myBrowser,String nValue, String txt, int count, String position) {
	 	sleep(5);
	 	TestObject[] TOs = myBrowser.find(atDescendant(".class", nValue, ".className", txt));
	  	String tex = TOs[0].getProperty(".text").toString();
	  	System.out.println("The whole String is "+ tex);
	  	int i = stringToInt(position);
	  	
	  	String emplid = getToken(tex,i);
	  	System.out.println("Extracted Value Is " + emplid);
	  	
	  	if (! "".equals(emplid))
	  		logTestResult("<font color=\"DeepPink\">The Extracted Value ", true,  + count +": <B>" + emplid + "</B></font>\n");
		else 
			logError("Emp Id Not Found ", getRootTestObject().getScreenSnapshot());

	  	return emplid;
	}
	 
	/**
	 * This will extract the specific ID from the Table and give as the output in the LogFile.
	 * @param myBrowser
	 * @param nValue
	 * @param txt
	 * @param count
	 * @param position
	 * @return
	 */
	 public String ExtractEmplid(TestObject myBrowser,String nValue, String txt, int count, String position) {
	 	sleep(4);
	 	System.out.println("**3**");
	 	String emplid = "";
	 	TestObject[] TOs = myBrowser.find(atDescendant(".class", nValue, ".className", txt));
	 	System.out.println("This is the Length of TO " + TOs.length);
	 	if(TOs.length >= 1){
		  	String tex = TOs[0].getProperty(".text").toString();
		  	System.out.println("The whole String is "+ tex);
		  	int i = stringToInt(position);
		  	
		  	emplid = getToken(tex,i);
		  	System.out.println("Extracted Value Is " + emplid);
		  	logTestResult("<font color=\"DeepPink\">The Extracted Value ", true,  + count +": <B>" + emplid + "</B></font>\n");
	  	}

		else 
			logError("Emp Id Not Found ", getRootTestObject().getScreenSnapshot());
	
	  	return emplid;
	}
	 
	/**
	 * @param gtoX
	 * @param dWaitTime
	 * @return
	 */
	public GuiTestObject WaitForObject(GuiTestObject gtoX[], double dWaitTime) {
		GuiTestObject gtoReturn = null;
		boolean bContinue = true;
		int iForCntr;
		long lTimeDiff;
		long lNowTime;
		
		GregorianCalendar gc1 = new GregorianCalendar();
		long lStrtTime = gc1.getTimeInMillis();
    
		while (bContinue == true) {
			for (iForCntr = 0; iForCntr < gtoX.length; iForCntr++) {
				 try {
				 	gtoX[iForCntr].waitForExistence(0.250,0.250);
				 	if ((gtoX[iForCntr].exists()) == true) {
				 		gtoReturn = gtoX[iForCntr];
				 		iForCntr = gtoX.length + 99;
				 		bContinue = false;
			  	 
				 		System.out.println(gtoReturn.toString());
				 	} else
				 		gtoReturn = null;
				 }
				 catch (Exception EF) { }
			}
			
			gc1 = new GregorianCalendar();
			lNowTime = gc1.getTimeInMillis();
			lTimeDiff = lNowTime - lStrtTime;
			
			if ((lTimeDiff > (dWaitTime * 1000)) && (dWaitTime > 0))
				bContinue = false;
		}
		return gtoReturn;
	}

	public class Encrypt {
		private String password = null;
		public String getPassword() {
			return password;
		}

		public String new2Password(String passwd) {
			try {
				MessageDigest md = MessageDigest.getInstance("SHA-1");
				String clearPassword = passwd;
				md.update(clearPassword.getBytes());
				byte[] digestedPassword = md.digest();
				return new String(digestedPassword);
			} catch (java.security.NoSuchAlgorithmException e) {
				System.out.println("Rats, MD5 doesn't exist");
				System.out.println(e.toString());
				return null;
			}

		}
		
		public void setPassword(String passwd) {
			try {
				MessageDigest sha = MessageDigest.getInstance("MD5");
				byte[] tmp = passwd.getBytes();
				sha.update(tmp);
			} catch (java.security.NoSuchAlgorithmException e) {
				System.out.println("Rats, MD5 doesn't exist");
				System.out.println(e.toString());
			}
		}
	}
	
    /**
	 * This function formats double to double with rounding off to 2 decimals 
	 * @author Abirami Bandaru
	 */
    public static double DoubleTo2Dec(double dl_Value) {
        double dl_fmtValue=0; 
        BigDecimal bd = new BigDecimal(dl_Value); 

        try {
	        dl_fmtValue=(bd.setScale(2, BigDecimal.ROUND_HALF_UP)).doubleValue();
	
	        } catch(ArithmeticException e) {
	            System.out.println("\n1"+e.getMessage());
	        } catch(IllegalArgumentException e) {
	            System.out.println("\n2"+e.getMessage());
	        }
        return dl_fmtValue;
    }

    /**
	 * This function repeatedly click refresh button until process became success or failure 
	 * @author Abirami Bandaru
	 */
    public void findProcessStatus(GuiTestObject button, StatelessGuiSubitemTestObject table) {
		//Return the process Status
		String monitorSt= "";
		try {
			do {
				button.click();
				GuiTestObject myCell = (GuiTestObject)table.getSubitem(atCell(atRow(2),atColumn(7)));
				monitorSt = myCell.getProperty(".text").toString();
				sleep(15);
			} while (!((monitorSt.equals("Success"))||(monitorSt.equals("Error"))));
		}
		catch (Exception e) { }
    }
	
	public int checkProcessFlag(TestObject myTable) {
	  	try {
	  		TestObject [] TOs = myTable.find(atList(atProperty(".text", "12/14/2006"),atDescendant(".text", "Not Proc"))) ;
	  		int numOfObjects = TOs.length;
	  		System.out.println("numOfObjects"+numOfObjects);
	  		if (numOfObjects == 1) {
		  		unregister(TOs);
				return 1;
	  		}
	  		unregister(TOs);
	  		return -1;
	 	} catch (Exception e) {
	  		System.out.println("Unable to find the Process Flag: " + "\n" + e.toString());
	  		return -1;
	  	}
	}
	
	/**
	 * Pass in a table TestObject. This method will check the PayrunID is unconfirmed
	 * @param myTable - table that contains the link
	 * @return 1 if click was successful.  -1 if couldn't find the link in myTable
	 * @author Abirami Bandaru
	 */
	public int verifyUnconfirm(TestObject myTable) {
		RegularExpression rowRE = new RegularExpression("PSSRCHRESULTSODDROW", false) ;
		try {
			TestObject[] TOs = myTable.find(atDescendant(".className", rowRE, ".text", "N"));
			int numOfObjects = TOs.length;
			if (numOfObjects == 3) {
		  		unregister(TOs);
			}
			unregister(TOs);
			return 1;
		} catch (Exception e) {
			System.out.println("Unable to find the row");
			return -1;
		}
	}
	
	/**
	 * Pass in a top level browser and a current selected value of the list box.  This method will attempt to click on the first list box 
	 * value randomly in the list box.  If it doesn't exist this method will return a false.  
	 * @param myTO - Browser that contains the list box
	 * @param recProperty - property used for recognition.  For example ".text", ".id", ".name"
	 * @param propValue - value for the recProperty used for recognition
	 * @param crValue - Current selected value from the list box
	 * @return true if click was successful.  false if couldn't find the list box values in myTO
	 * @author Abirami Bandaru
	 */
	public boolean selectRandomListboxValue(TestObject myTO,String recProperty, String propValue,String crValue) {
			boolean status = true;
			
			System.out.println("recproperty:"+recProperty);
			System.out.println("propValue:"+propValue);
			System.out.println("cr value"+crValue);
				
			try{
				TestObject[] TableObjs = myTO.find(atDescendant(recProperty,propValue));
				
				System.out.println("after testobj");
				
				//Get the Drop down list box values from the application
				if (TableObjs.length >= 1 ) {
					SelectGuiSubitemTestObject myTable = (SelectGuiSubitemTestObject)TableObjs[0];
					String appDpDownValues = myTable.getProperty(".text").toString();
					System.out.println("app values:"+appDpDownValues);
					
					StringTokenizer st = new StringTokenizer(appDpDownValues, " ");
					int noOfToken = st.countTokens();
					
					String [] appFldValues = new String[(noOfToken-1)];
					
					int i=0;
					while (st.hasMoreTokens()) {
						String temp = st.nextToken();
						if (temp.compareTo(crValue) != 0) {
							System.out.println("i value is:"+i);
							appFldValues[i] = temp;
							System.out.println("token in array:"+appFldValues[i]);
							i++;
						}
					}
					
					--noOfToken;
					Random generator = new Random();
					int randomSelection = generator.nextInt(noOfToken);
					System.out.println("random:"+randomSelection);
					
					//Select random ListBox Value 
					myTable.click();
					myTable.click();
					sleep(3);
					myTable.select(randomSelection);
					sleep(2);
				} else {
					System.out.println("Unable to find GUI TestObject: " + recProperty + " : " + propValue);
					logError("Unable to find GUI TestObject: " + recProperty + " : " + propValue, getRootTestObject().getScreenSnapshot());
					status = false;
				}
				unregister(TableObjs);
			} catch (Exception e) {
				System.out.println("Unable to select GUI TestObject" + e.toString());
				status = false;
			}
			return status;
		}
	
    /**
	 * This function will increase comp rate by $3000
	 * @author Abirami Bandaru
	 */
    public String changeCompensation(String crComprate) {
    	//Remove the decimal as well as the decimal numbers
		String strWithoutDecimals = crComprate.substring(0, (crComprate.length()-7)); 		
		
		//Remove the comma
		String strWithoutComma = strWithoutDecimals.replaceAll("," , ""); 

		//Convert String to Double
		double curComp = Double.valueOf(strWithoutComma.trim()).doubleValue();
							
		//Convert back to string after adding 3000
		String newComp = Double.toString(curComp + 3000);
		
		return(newComp);
    }
    
    /**
	 * This function takes a String parameter: This Function will get the text value of a EditBox and returns the value.
	 * @author Ajit K Abraham
	 */
	public String getTextofEditBox(TestObject myBrowser, String propValue) {
		browserWait(myBrowser);
		TestObject[] TOn = myBrowser.find(atDescendant(".name", propValue));
		TextGuiTestObject myGTO1 = (TextGuiTestObject) TOn[0];
		String sentence = myGTO1.getText().toString();
		System.out.println("This is the EditBox text Value:" + sentence);
		return sentence;
	}
	
	/**
	 * This function takes a String parameter: This Function will get the selected text value of a ListBox and returns the value.
	 * @author Ajit K Abraham
	 */
	public String getselectedTextofListBox(TestObject myBrowser, String propValue) {
		browserWait(myBrowser);
		String ListBoxValue = "";
		TestObject[] TOs = myBrowser.find(atDescendant(".name", propValue));
		if (TOs.length > 0){
			SelectGuiSubitemTestObject TO = (SelectGuiSubitemTestObject) TOs[0];
			TO.waitForExistence(10, 0.10);
			if (TO.exists()){ 
				ListBoxValue = TO.getSelectedText();
			}
			TO.unregister();
		}	
		unregister(TOs);
		return ListBoxValue;
	}
	
	/**
	 * This function takes 2 String parameters: This Function will return an instance of the ListBox or Dropdown object.
	 * @author Ajit K Abraham
	 */
	public SelectGuiSubitemTestObject getSelectGuiSubitemTestObject(TestObject n, String propValue) {
		TestObject[] TOn1 = myBrowser.find(atDescendant(".name", propValue));
		SelectGuiSubitemTestObject myGTO2 = (SelectGuiSubitemTestObject) TOn1[0];
		myGTO2.waitForExistence(60,0.10);
		return myGTO2;
	}
	
	/**
	 * This function takes 2 String parameters: This Function will return an instance of the Table or StatelessGuiSubitemTestObject object.
	 * @author Ajit K Abraham
	 */
	public StatelessGuiSubitemTestObject getStatelessGuiSubitemTestObject(TestObject n, String propValue) {
		TestObject[] TOn1  = myBrowser.find(atDescendant(".class", "Html.TABLE",".className",propValue));
		StatelessGuiSubitemTestObject myGTO2 = (StatelessGuiSubitemTestObject) TOn1[0];
		return myGTO2;
	}
	
	/**
	 * Pass in a High level TestObject and a link String. This method will attempt to click on the link if the table
	 * and link exists.If table or link don't exist this method will return a -1. This method will click on the 1st 
	 * instance of that link. If it exists more than once, the rest of instances will be ignored.
	 * 
	 * @param myBrowser - Higher level object where object to be clicked will be found.Could be browser, HTML page,
	 *                    HTML table etc.
	 * @param link - the link that you wish to click on the table
	 * @return 1 if click was successful. -1 if couldn't find the link/table 
	 * @author Abirami Bandaru
	 */
	public int click1stSpecificLink(TestObject myBrowser, String link) {
		TestObject[] TOs  = myBrowser.find(atDescendant(".class", "Html.TABLE",".className","PSSRCHRESULTSTITLE"));
		int numOfObjects = TOs.length;
		
		try{
			if (numOfObjects >=1){
				TestObject myTa = TOs[0];
				myTa.waitForExistence(60,0.10);
				
				RegularExpression rowRE = new RegularExpression("PSSRCHRESULTS.*ROW", false) ;
		  		TestObject[] TO1 = myTa.find(atDescendant(".className", rowRE, ".text", link));
		  		int numOfObj = TO1.length;
		  		if (numOfObj >= 1){
			  		GuiTestObject GUITO = new GuiTestObject(TO1[0]);
					GUITO.click();
					GUITO.unregister();
					unregister(TOs);
					return 1;
		  		}
		  		unregister(TO1);
		  		unregister(TOs);
		  		return -1;
			}
				System.out.println("table not available");
				return -1;
		}
	  	catch (Exception e){
	  		System.out.println("Unable to find Table and click on: " + link + "\n" + e.toString());
	  		return -1;
	  	}
	}
	
	/**
	 * This function takes a TestObject and 4 String parameters: This Function
	 * will check the given Date with Date in the Date Field. If the given Date is prior to
	 * the Date in the Date Field, the function will click on the Next button.
	 * eg: checkDatesclickNext(myBrowser, recProperty, propValue, nValue, sVPName);
	 * myBrowser, recProperty, propValue - table id, nValue - button id
	 * @author Ajit. K. Abraham
	 */
	public void IncreaseCompRate(TestObject myTO, String recProperty, String propValue, String Value) {
		try{
			TestObject[] TOs = myTO.find(atDescendant(recProperty, propValue));
			if (TOs.length > 0){
				TextGuiTestObject TO = (TextGuiTestObject) TOs[0];
				//Checks for the Existence of Object
				TO.waitForExistence(300, 0.10);
				if (TO.exists()) {
					String CompRte = TO.getText();
					
					//remove the comma
					String strWithoutComma = CompRte.replaceAll("," , ""); 
		
					//Convert String to Double
					double curComp = Double.valueOf(strWithoutComma.trim()).doubleValue();
					String ValueWithoutComma = Value.replaceAll("," , ""); 
					double intvalue = Double.valueOf(ValueWithoutComma.trim()).doubleValue();					
					//Convert back to string after adding 5000
					String newComp = Double.toString(curComp + intvalue);
					
					//input back into compensation field
					TO.setText(newComp);
						
					System.out.println(newComp);
					
					TO.setText(newComp);
					
					String CompRtenew = TO.getText();
					
					System.out.println("This is the Value Extracted from the CompRate after incrementing: " + CompRtenew);
				}
				TO.unregister();
			}
			unregister(TOs);
		}
		catch (Exception e) {
			System.out.println("The TextGuiTestObject could not be found");
			logError("<font color=\"DeepBlue\">" + "IncreaseCompRate  FAILED " + e.toString() + "</font>", getRootTestObject().getScreenSnapshot());}
	}

	/**
	*This function will wait for a dialogue box where the text to be clicked inside the dialogue box has to be 
	*passed through the parameterization in Excel.
	
	*@author Ajit. K. Abraham
	**/
	public void WaitNClickDialogue(TestObject myTO,String recProperty, String propValue, String textValue) {
		try{
			sleep(3);
			RootTestObject rTO = getRootTestObject();
			TestObject[] TOs = rTO.find(atDescendant(recProperty, propValue,".text", textValue));
			int numOfObjects = TOs.length;
		
			if (numOfObjects >= 1) {
				System.out.println("find the object");
				GuiTestObject myGTO = (GuiTestObject) TOs[0];
				myGTO.waitForExistence(1000, 0.10);
				if (myGTO.exists()){
					myGTO.click();
					}
				}
			unregister(TOs);
		}
		catch (Exception e){
			System.out.println("Unable to perform the WaitforExistenceAndClick GUI TestObject: " + propValue);
			System.out.println(e.toString());
		}
	}
	
	//Click on the EventID
	//author Ajit.K.Abraham.
	public String pickEventID(TestObject myTable, String recProperty,String propValue) {
		 String crEvent = "";
		 TestObject[] TOs = myTable.find(atDescendant(recProperty, propValue));
		 int numOfObjects = TOs.length;
		 if (numOfObjects >= 1){
	  		GuiTestObject GUITO = new GuiTestObject(TOs[0]);
	  		crEvent = GUITO.getProperty(".text").toString();
	  		click1stSpecific(GUITO,getToken(crEvent, -1));
	  		}
		return crEvent;
	}

	public void UpdateBenefAddNewRow (TestObject myTable, String recProperty,String propValue, String newValue) {
		TestObject[] TOs = myTable.find(atDescendant(recProperty, propValue));
		int numOfObjects = TOs.length;
		if (numOfObjects >= 1){
			TextGuiTestObject GUITO = new TextGuiTestObject(TOs[0]);
			String process = GUITO.getProperty(".text").toString();
			System.out.println("The Data in the Field is : " + process);
			if(process.compareTo("") != 0) {
				System.out.println("There is Data in the Field");
				TestObject[] TOn = myTable.find(atDescendant(recProperty, newValue));
				if (numOfObjects >= 1){
					GuiTestObject GUITOn = new GuiTestObject(TOn[0]);
					GUITOn.click();
					}
				}
				else
					System.out.println("There is no Existing Data in the Field");
		}
	}

	/** DOE FUNCTION
	 * This Function Will Check if the Particular text passing through the Excel Spreadsheet is present in that String or not
	 * @param myTO - Higher level object where object to be clicked will be found.
	 * 				Could be browser, HTML page, HTML table etc.
	 * @param nValue - value to be checked
	 * @param propValue1 - property used for recognition.  For example ".class"
	 * @param propValue2 - property used for recognition.  For example ".className"
	 * @return status as a boolean value
	 * @author Ajit Abraham
	 */
	public boolean CheckPresenceOfAString (TestObject myTO, String nValue, String propValue, String sLog) {
		boolean result = true;
		try {
			browserWait(myTO);
			int status;
			TestObject[] TOs = myTO.find(atDescendant(".class", "Html.TABLE", ".className", propValue));
	
			StatelessGuiSubitemTestObject TO = (StatelessGuiSubitemTestObject) TOs[0];
			TO.waitForExistence(100,0.10);
			
			String process = TO.getProperty(".text").toString();
			System.out.println("The Data From the Table is :" + process);
			status = process.indexOf(nValue);
			System.out.println("This is the value of Status" + status);
			
			if (status < 0) { 
				logTestResult("<font color=\"DeepPink\">There is no such text in this Table </font>"+ nValue,true);
				result = false;
			} else { 
				logTestResult("<font color=\"DeepPink\">The given text "+ nValue + " is present in this Table", true);
				result = true;
			}
		} catch (Exception e) {
			System.out.println("The String Or Table is Not Present");
			result = false;
		}
		return(result);
	}
	
	/**
	 * This Function Will Check if the Particular text passing through the Excel Spreadsheet is present in that String or not
	 * @param myTO - Higher level object where object to be clicked will be found.
	 * 				Could be browser, HTML page, HTML table etc.
	 * @param nValue - value to be checked
	 * @param propValue1 - property used for recognition.  For example ".class"
	 * @param propValue2 - property used for recognition.  For example ".className"
	 * @return status as a boolean value
	 * @author Abirami Bandaru, Ajit Abraham
	 */
	public boolean CheckPresenceOfAString (TestObject myTO, String nValue, String propValue1, String propValue2 , String sLog) {
		int status;
		boolean result;
		TestObject[] TOs = myTO.find(atDescendant(".class", propValue1, ".className", propValue2));

		StatelessGuiSubitemTestObject TO = (StatelessGuiSubitemTestObject) TOs[0];
		TO.waitForExistence(100,0.10);
		
		String process = TO.getProperty(".text").toString();
		System.out.println("The Data From the Table is :" + process);
		status = process.indexOf(nValue);
		System.out.println("This is the value of Status" + status );
		
		if (status < 0) {
			logInfo("<font color=\"DeepRed\">" + sLog +".  THERE IS NO SUCH TEXT " + nValue + " IN THE STRING </b></font>");
			result = false;
		} else {
			logInfo("<font color=\"DeepBlue\"> FUNCTION FAILED !!! THE GIVEN TEXT " + nValue + " IS FOUND INSIDE THE STRING </b></font>");
			result = true;
		}
		return (result);
	}

	/**
	 * This function takes 2 String parameters: This Function will return an instance of the GuiTestObject object.
	 * @author Ajit K Abraham
	 */
	public boolean getGuiTestObj(TestObject n, String propValue){
		boolean status = true;
		browserWait(myBrowser);
		TestObject[] TOn1 = myBrowser.find(atDescendant(".name", propValue));
		GuiTestObject myGTO2 = (GuiTestObject) TOn1[0];
		myGTO2.waitForExistence(60,0.10);
		if(myGTO2.exists()){
			status = true;
		} else
			status = false;
		return status;
	}

	/** DOE FUNCTION
	* This Function Will Check if the Particular text passing through the Excel Spreadsheet is present in that String or not and Approve
	* the Worklist item.
	* @param myBrowser - Higher level object where object to be clicked will be found.
	* 				Could be browser, HTML page, HTML table etc.
	* @param nValue - value to be checked
	* @param propValue1 - property used for recognition.  For example ".class"
	* @param propValue2 - property used for recognition.  For example ".className"
	* @param sVPName(String) - Passes .name property of right arrow to search on other pages
	* @return status as a boolean value
	* @author Ajit Abraham
	*/
	public void ApproveWorkList (TestObject myBrowser, String sObjType, String recProperty, String propValue, String sVPName, String sLog, String newvrbl) {
		browserWait(myBrowser);
		boolean Status = true;
		boolean presence = false;
		
		int counter = 0;
		
		while(Status && !presence) {
			counter++;
			System.out.println("Counter: " + counter);
			
			LogScreenShot("This is the WorkList " + counter + " Screenshot");
			
			presence = CheckPresenceOfAString(myBrowser, newvrbl, propValue, sLog);
			
			if (presence == true) {
				String[] excludeVal = {sObjType};
				clickRandomEI(myBrowser, recProperty, propValue, excludeVal, newvrbl); browserWait(myBrowser); sleep(2);
			} else {
				LogScreenShot("This is the WorkList " + counter + " Screenshot");
				try {
					Status = getGuiTestObj(myBrowser, sVPName);
				} catch (Exception e) {
					System.out.println("There is No RIGHT BUTTON");
					Status = false;
				}
			}
			
			if(Status == true) {
//				clickGuiTestObject(myBrowser, ".name", sVPName);
				click(".name", sVPName);
			} else if(Status == false) {
				System.out.println("There is no more Right Button");
			}
		}
	}

	/** DOE FUNCTION
	* This Function Will Check if the Particular text passing through the Excel Spreadsheet is present in that String or not.
	* @param myTO - Higher level object where object to be clicked will be found.
	* 				Could be browser, HTML page, HTML table etc.
	* @param nValue - value to be checked
	* @param propValue1 - property used for recognition.  For example ".class"
	* @param propValue2 - property used for recognition.  For example ".className"
	* @return status as a boolean value
	* @author Ajit Abraham
	*/
	public void AbsenceOfStringInWorkList (TestObject myTO, String propValue, String sVPName, String sLog, String newvrbl ) {
		browserWait(myTO);
		boolean Status = true;
		boolean presence = false;
		int counter = 0;
		
		while(Status && !presence) {
			counter++;
			LogScreenShot("This is the WorkList " + counter + " Screenshot");
			presence = CheckPresenceOfAString(myTO,newvrbl, propValue, sLog);
			System.out.println("This is the Presence: " + presence);
			if (presence == false){
				logTestResult("<font color=\"DeepPink\">" + sLog + "</font>"+ newvrbl,true);
				try {
					Status = getGuiTestObj(myTO, sVPName);
				} catch (Exception e) {
					
					System.out.println("There is no RIGHT BUTTON");
					Status = false;
				}
			}
			if(Status == true) {
				click(".name", sVPName);
			}
			if (presence == true) {
				System.out.println("The Text is Still Present In The WorkList!!!");
				logError("The Text is Still Present In The WorkList!!!");
			}
			browserWait(myTO);
		}
	}

	/**
	 * This Function clicks a Random link in a table and allows to exclude and include values.  A bit on the complicated side, but it does the job.
	 * @param myTable - TestObject (table) that contains links to be clicked
	 * @param excludeVal[] - String Array - The 1st value in array should be the starting text of the row(s) to exclude
	 * 					   - Can contain as many values after that.  All values after the 1st should contain text that is part of description to be avoided. 
	 * @param include - String - Value that must be in the text we are to click.
	 * @author Saurabh Malhotra
	 */
	public void clickSpecificLink(TestObject myBrowser,String recProperty, String propValue, String[] excludeVal, String include) {
		
		TestObject[] TableObjs = myBrowser.find(atDescendant(recProperty, propValue));
		System.out.println(propValue);
		
		if (TableObjs.length >= 1 ) {
			StatelessGuiSubitemTestObject myTable = (StatelessGuiSubitemTestObject) TableObjs[0];
		
		lowestChildren.clear();
		buildVectorOfLowestChildrenEI(myTable, excludeVal, include);
		int count = lowestChildren.size();
		System.out.println("Lowest Children Count: " + count);
		TestObject[] lowChilds = new TestObject[count];
	    lowestChildren.copyInto(lowChilds);
	    int randNum = (int)(Math.random()*count);
	    
	    GuiTestObject x = new GuiTestObject(lowChilds[randNum]);
		x.click(atPoint(1,1));
		x.unregister();
		unregister(lowChilds);
		lowestChildren.clear();	//clear the public vector
		}
	}

	/**
	 * This Function compares the dropdown list box values with the given values
	 * @param myTo - TestObject that contains list box
	 * @param excludeVal[] - String Array - The 1st value in array should be the starting text of the row(s) to exclude
	 * 					   - Can contain as many values after that.  All values after the 1st should contain text that is part of description to be avoided. 
	 * @param include - String - Value that must be in the text we are to click.
	 * @author Abirami Bandaru
	 */
	public void verifyDrpdownValues(TestObject myTO, String recProperty, String propValue,String gvnValue) {
		TestObject[] TableObjs = myTO.find(atDescendant(recProperty, propValue));
		
		//Get the Drop down list box values from the application
		if (TableObjs.length >= 1 ) {
			SelectGuiSubitemTestObject myTable = (SelectGuiSubitemTestObject)TableObjs[0];
			String appDpDownValues = myTable.getProperty(".text").toString();
			System.out.println("List box values from application:"+appDpDownValues);
			
			StringTokenizer st = new StringTokenizer(appDpDownValues, " ");
			int noOfToken = st.countTokens();
			
			String [] appFldValues = new String[noOfToken];
			
			int i=0;
			while (st.hasMoreTokens()) {
				appFldValues[i] = st.nextToken();
				System.out.println("List box values from application:"+appFldValues[i]);
				i++;
			}
			
			//Get the count of different list box values to compare
			st = new StringTokenizer(gvnValue, ",");
			noOfToken = st.countTokens();
			String [] gvnFldValues = new String[noOfToken];
			//Get the Given List Box values to compare
			
			i = 0;
			while (st.hasMoreTokens()) {
				gvnFldValues[i] = st.nextToken();
				System.out.println("List box values from excel:"+gvnFldValues[i]);
				i++;
			}
			if (!Arrays.equals(appFldValues,gvnFldValues))
				logTestResult("<font color=\"DeepRed\">Dropdownlist box values comparison", false, "<font color=\"Red\">Dropdownlist box values do not match with given values"+"</font>");
		}
	}

	public void incrementValue(TestObject myTO, String recProperty, String propValue, String incrValue) {
		TestObject[] TOs = myTO.find(atDescendant(recProperty, propValue));
		if (TOs.length > 0) {
			TextGuiTestObject TO = (TextGuiTestObject) TOs[0];
			//Checks for the Existence of Object
			TO.waitForExistence(300, 0.10);
			
			if (TO.exists()) {
				//Get Current value
				String curValue = TO.getText();
				//Remove the comma
				String strWithoutComma = curValue.replaceAll("," , ""); 
				//Convert String to Double
				double curVal = Double.valueOf(strWithoutComma.trim()).doubleValue();
				double incrVlue = Double.valueOf(incrValue).doubleValue();
				//Convert back to string after adding "Value" parameter
				String newValue = Double.toString(curVal + incrVlue);
				//Input back into field
				TO.setText(newValue);
			}
			unregister(TOs);
		}
	}

	public boolean clickSubItemListBoxValue(TestObject myTO, String recProperty, String propValue) {
		boolean status = true;
		try {
			String value = "";
			TestObject[] TableObjs = myTO.find(atDescendant(recProperty, propValue));
			
			//Get the Drop down list box values from the application
			if (TableObjs.length >= 1 ) {
				SelectGuiSubitemTestObject myTable = (SelectGuiSubitemTestObject)TableObjs[0];
				String appDpDownValues = myTable.getProperty(".text").toString();
				System.out.println("List box values from application:"+appDpDownValues);
				
				StringTokenizer st = new StringTokenizer(appDpDownValues, " ");
				int noOfToken = st.countTokens();
				
				String [] appFldValues = new String[noOfToken];
				
				int i=0;
				
				appFldValues[i] = st.nextToken();
				value = appFldValues[0];
				System.out.println("Click this ListBox Value :" + value);
				
				//Click the first ListBox Value 	
				select(recProperty, propValue, value);
			} else {
				System.out.println("Unable to find GUI TestObject: " + recProperty + " : " + propValue);
				logError("Unable to find GUI TestObject: " + recProperty + " : " + propValue, getRootTestObject().getScreenSnapshot());
				//stop();
				status = false;
			}
			unregister(TableObjs);
		} catch (Exception e) {
			System.out.println("Unable to select GUI TestObject" + e.toString());
			status = false;
		}
		return status;
	}

	public void compareResult(TestObject myTO, String recProperty, String propValue, String VPName,String text) {
		int iTab = 9;
		char del = (char)iTab;
		
		String newText = getTextData(myTO, recProperty, propValue);
		System.out.println("THIS IS THE OLD TEXT: " + text);
		System.out.println("THIS IS THE NEW TEXT: " + newText);
		
		if(text.compareTo(newText)!=0) {
			System.out.println("THE OLD " +VPName+ "IS " + text + "AND IT IS CHANGED TO NEW"+ VPName + del+ newText + "; THE VERIFICATION IS PASSED");
			logTestResult("<font color=\"DeepPink\">The "  + del + VPName + del + "change verification is Passed",true,"<font color=\"BlueViolet\"><b>Old"+ del + VPName+":"+ text + "</b>\n<b>" + "New"+ del + VPName+":" + newText + "</b></font>");
		} else {
			System.out.println("THE OLD " +VPName+ "IS " + text + " AND IT IS CHANGED TO NEW"+ VPName + del+ newText + "; THE VERIFICATION IS FAILED");
			logTestResult("<font color=\"DeepPink\">The " + del + VPName + del + "change verification is Passed",false,"<font color=\"BlueViolet\"><b>Old"+ del + VPName+":"+ text + "</b>\n<b>" + "New"+ del + VPName+":" + newText + "</b></font>");
		}
	}

	/**
	 * This function is for the purpose of checking the right Toggle Box for the DOE PAR scripts.
	 * The function will compare with the dynamic value stored while runtime with the value in the specific column in different rows in the table.
	 * @author Ajit . K. Abraham
	 */
	public boolean checkRightToggle(TestObject myTO, String recProperty, String propValue, String sObjType, String reqID, String nValue1, String sVPname, String sLog){
		boolean status = true;
		GuiTestObject myCell;
		String chkbox = "";
		int row = 0;
		int sObj = Integer.parseInt(sObjType);
		int sColumn = Integer.parseInt(sVPname);
		System.out.println("This is the integer value: " + sObj);
		TestObject[] TOs = myTO.find(atDescendant(recProperty, propValue));
		StatelessGuiSubitemTestObject TOt = (StatelessGuiSubitemTestObject) TOs[0];
		
		try {
			for (int r=1; r<sObj; r++) {
				System.out.println("Row: " + r);
				myCell = (GuiTestObject)TOt.getSubitem(atCell(atRow(r), atColumn(sColumn)));
				String sMyCell = myCell.getProperty(".text").toString().trim();
				System.out.println("This is the value for Row: "+ r + " is: " + sMyCell);
				System.out.println(" Row:" + r + "Column" + sVPname + ": " + sMyCell);//myCell.getProperty(".text").toString());
				if(reqID.compareTo(sMyCell) == 0) {
					System.out.println(sLog);
					row = r-2;
					chkbox = nValue1 + row;
					System.out.println("This is checkbox : "+ chkbox);
					status = false;
				} else
					System.out.println("THERE IS NO SPECIFIC value : " + reqID + " PRESENT IN THE ROW NUMBER: " + r);
			}
			TestObject[] TO1s = TOt.find(atDescendant(".name", chkbox));
			ToggleGUITestObject TOb = (ToggleGUITestObject) TO1s[0];
			TOb.click();
		} catch (Exception e) {
			System.out.println("The maximum rows has reached");
			status = false;
		}
		return status;	
	}
	
	//Fetch and return the excel line count of the passing String.
	//Ajit Abraham.
	public int read_Excel(String Action) {
		Connection c = null;
		Statement stmnt = null;
		ResultSet rs;
		int excelLineCnt = 1;
		String sQuery;
		String sAction;
		int newlinecnt = 0;
		
		try {
			// Enable JDBC ODBC Driver to access excel
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			
			// Copy the Excel file to local directory if it is not available
//			FN = myHelper.copyToTemp(FN, tempDirectory, false);
//			System.out.println(FN);
			String xlPath = "\\\\psfile01\\sharedfiles\\FuncTstScripts\\NYCAPS_RFT\\Rollout_XL\\xlfiles\\CityPAR_Workflow_JobReq.xls";
			String scriptName = "Condition_ExcelTrial";
			
			// Open connection to the xl script file
			c = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xls)};DBQ=" + xlPath + "; READONLY=true");
			// Crt Statement Object to be used to query Excel File
			stmnt = c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			// Select statement to query Excel worksheet get all rows and columns from relevant Script's worksheet
			sQuery = "Select * from [" + scriptName + "$]";
			// Get a ResultSet by executing the Select Statement
			rs = stmnt.executeQuery(sQuery);
			
			while (rs.next()) {
				boolean status = true;

				if (status) {
					try {
						sAction = rs.getString("Action").trim();
					} catch (Exception Egs101) {
						sAction = "";
					}
					excelLineCnt++;
					if (sAction.compareToIgnoreCase(Action) == 0) {
						System.out.println("This is the Excel Line for " + Action + " is " + excelLineCnt);
						newlinecnt = excelLineCnt;
						status = false;
					} else
						status = true;
				}
			}
				
			rs.close();
			stmnt.close();
			c.close();
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
			System.out.println(e.toString());
		}
		return newlinecnt;
	}
	
	/**
	 * This function queries on any XL file located on String fileName and run a query (sQuery).
	 * Boolean write decides over writing on existing file.
	 * write = true -- delete existing file from temp folder and copy a new file
	 * write = false -- keep file in temp folder
	 * 
	 * Column Names are stored to global String array (colNames).
	 * Column Values are stored to global String array (colValues).
	 * 
	 * @author desaid
	 */
	public void read_XL(String fileName, String sQuery, boolean write) {
		String tempDirectory = "D:\\RationalTemp\\";
		Connection c = null;
		Statement stmnt = null;
		ResultSet rs;
		
		// Clear Vector - Remove all previously stored values
		colNames.clear();
		colValues.clear();
		
		// Print File Name and Query
		log("XL File: " + fileName);
		log("Query: " + sQuery);
		
		try {
			// Enable JDBC ODBC Driver to access excel
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			
			//Copy the Excel file to local directory if it is not available
			fileName = copyToTemp(fileName, tempDirectory, write);
			System.out.println("Temp File: " + fileName);
			
			// Set Connection to Excel File
			c = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xls)};DBQ=" + fileName + "; READONLY=true");
			
			// Crt Statement Object to be used to query Excel File
			stmnt = c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			// Get a ResultSet by executing the Select Statement
			rs = stmnt.executeQuery(sQuery);
			rs.next();
			
			ResultSetMetaData data = rs.getMetaData();
			int colCount = data.getColumnCount();
			log("Column count in Result Set: " + colCount);
			
			for (int i=1; i <= colCount; i++) {
				colNames.addElement(data.getColumnName(i));
				colValues.addElement(rs.getString(colNames.elementAt(i-1).toString()));
			}
			
			log("Column Names: " + colNames);
			log("Column Values: " + colValues);
			
			rs.close();
			stmnt.close();
			c.close();
		} catch (Exception e) {
			log("Exception: " + e.getMessage());
		}
	}
	
	/**
	 * When write parameter is not specified then use "false" and call read_XL function
	 */
	public void read_XL(String fileName, String sQuery) {
		read_XL(fileName, sQuery, false);
	}
	
	/**
	 * This function executes 'INSERT' / 'UPDATE' query (sQuery) on any XL file located on String fileName.
	 * 
	 * @param fileName - Name of XL File.
	 * 		e.g. - fileName = "\\\\psfile01\\sharedfiles\\FuncTstScripts\\NYCAPS_RFT\\XL_Files\\ESS_HealthBenefits\\WBC_Termination.xls";
	 * @param sQuery - SQL Query that will write in given XL File. 
	 *		e.g. - sQuery = "INSERT INTO [Results_" + environment + "$] (remark, empID, effectDate) VALUES ('"
							+ remark + "', '" + empID + "', '" + eventDt + "')";
							
	 * @return boolean - if 'FALSE' query executed successfully
	 * 				   - if 'TRUE' query executed UNSUCCESSFULLY
	 * 
	 * NOTE: To execute 'SELECT' statement please use function 'read_XL'
	 * 
	 * @author Dipen Desai
	 * @since 09/11/2008
	 */
	public boolean write_To_XL(String fileName, String sQuery) {
		try {
			Connection c = null;
			Statement stmnt = null;
			
			// Print Information (Environment, Iteration, File Name, Query)
			log("XL File: " + fileName);
			log("Query: " + sQuery);
			
			// Enable JDBC ODBC Driver to access excel
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			
			// Set Connection to Excel File
			c = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xls)};DBQ=" + fileName + "; READONLY=true");
			
			// Crt Statement Object to be used to query Excel File
			stmnt = c.createStatement();
			
			// Get a ResultSet by executing the Select Statement
			boolean write = stmnt.execute(sQuery);
			System.out.println("BOOLEAN: " + write + "\n");
			
			stmnt.close();
			c.close();
			
			return write;
		} catch (Exception e) {
			log("Write_2_XL Exception: " + e);
			e.printStackTrace();
			return true;
		}
	}
	
	/**
	 * This function logs into NYCAPS system.
	 * 
	 * @param environment -
	 *            decides environment for login Role of an actor login into
	 *            NYCAPS is decided by the string For e.g. "ORG" - denotes
	 *            ORIGINATOR "APR1" - denotes Approvar 1 "" - Viewr/User/XML
	 *            Validator etc.
	 * @author Dipen Desai
	 */
	public void nycaps_Login(String x) {
		closeAllHtmlObjects(); sleep(10);
		
		if(x.length() < 6) {
			x = environment + x;
		}
		
		// Query on same XL File to get UserID, PW and URS for Server Name = sSrvr
		fileName = mappedDrive + "Control.xls";
		sQuery = "Select * from [Login$] where Key = '" + x + "'";
		read_XL(fileName, sQuery);
		
		// Get User ID, PW and URL to Global Variables for Login
		try {
			startBrowser(colValues.elementAt(3).toString()); sleep(10);
			// Click 'YES' if Security Box appears
			checkSecurityBox();
			
			myBrowser = (BrowserTestObject)returnTO(".class", "Html.HtmlBrowser");
			myBrowser.maximize();
			browserWait(myBrowser); sleep(5);
			
			waitForExistenceAndClick(".name", "overridelink");
			waitForExistenceAndClick(".text", "Sign in to PeopleSoft");
			
			// Set UserID and PWD and  Sign In
			setText(".name", "userid", colValues.elementAt(1).toString());
			setText(".name", "pwd", colValues.elementAt(2).toString());
			
			click(".name", "Submit"); sleep(2);
			hover(".text", "Home");
		} catch (Exception e) {
			System.out.println("Login Exception: " + e.getMessage());
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}
	
	/**
	 * This function logs out of NYCAPS system and closes the browser.
	 * @author Dipen Desai
	 */
	public void nycaps_Logoff() {
		myBrowser = (BrowserTestObject)returnTO(".class", "Html.HtmlBrowser");
		click(".text", "Sign out");
		browserWait(myBrowser);
		
		checkCancelBox();
		browserWait(myBrowser);
		sleep(3);
		
		myBrowser.close();
		myBrowser.unregister();
	}
	
	/**
	 * This function Logs out of NYCAPS system and then
	 * Re-Logs in as a different user with a different role.  
	 * Role of the user is defined by 'writetoRolloutControl' function and
	 * it's parameter defined in script by user.
	 * 
	 * @param environment - decides environment for login
	 * Role of an actor login into NYCAPS is decided by the string
	 * 			For e.g.	"ORG" - denotes ORIGINATOR
	 * 						"APR1" - denotes Approvar 1
	 * 						"" - Viewr/User/ XML Validator etc.		
	 * @author Dipen Desai
	 */
	public void nycaps_ReLogin(String x) {
		log("Starting nycaps_ReLogin");
		click(".text", "Sign out"); sleep(2);
		
		checkCancelBox();
		browserWait(myBrowser);
		hover(".name", "userid");
		sleep(3);
		
		if(x.length() < 6) {
			x = environment + x;
		}
		
		// Query on same XL File to get UserID, PW and URS for Server Name = sSrvr
		fileName = mappedDrive + "Control.xls";
		sQuery = "Select * from [Login$] where Key = '" + x + "'";
		read_XL(fileName, sQuery);
		
		// Use Global Variables (User ID and PW) for Login
		try {
			// Set UserID and PWD and click Sign In
			setText(".name", "userid", colValues.elementAt(1).toString());
			setText(".name", "pwd", colValues.elementAt(2).toString());
			
			click(".name", "Submit"); sleep(2);
			hover(".text", "Home");
		} catch (Exception e) {
			System.out.println("Login Exception: " + e.getMessage());
			System.out.println(e.toString());
		}
	}
	/**
	 * This function reads text of pop-up dialog box and Return String.. 
	 * 
	 * @return String
	 * @author Dipen Desai
	 * @since 05/06/2010
	 */
	public String readDialog() {
		String ret = "";
		
		try {
			RootTestObject rTO = getRootTestObject();
			TestObject[] TOs = rTO.find(atDescendant(".class", "Html.DialogStatic", ".classIndex", "1"));
			int numOfObjects = TOs.length;
			System.out.println("This is the number of Objects for dialog box: " + numOfObjects);
			
			if (numOfObjects >= 1) {
				GuiTestObject myGTO = (GuiTestObject) TOs[0];
				myGTO.waitForExistence(120, 0.10);
				ret = myGTO.getProperty(".text").toString().trim();
				System.out.println("Text found in the dialog box:: " + ret);
				
				LogScreenShot("Pop-Up Message Verification");
				WaitNClickDialogue(myBrowser, ".class", "Html.DialogButton", "OK");
			} else {
				System.out.println("There is no Dialog Box");
			}
		} catch (RuntimeException e) {
			System.out.println("READ DIALOG BOX --> EXCEPTION: " + e.toString());
		}
		
		return ret;
	}
	
	/**
	 * This function reads text of pop-up dialog box and compares it with expectedValue string as
	 * a Manual Verification Point.  If dialog box is not available then VP will fail. 
	 * 
	 * @param name - Name of the Manual Verification Point (MUST BE LESS THAN 30 CHARS; NO WHITE SPACES)
	 * @param expectedValue - Expected Value of the Manual Verification Point
	 * 
	 * @return boolean
	 * @author Dipen Desai
	 * @since 2/17/2009
	 */
	public boolean readDialog_VP(String vp_Name, String expectedValue) {
		boolean Status = true;
		try {
			String text = "";
			
			RootTestObject rTO = getRootTestObject();
			TestObject[] TOs = rTO.find(atDescendant(".class", "Html.DialogStatic", ".classIndex", "1"));
			int numOfObjects = TOs.length;
			System.out.println("This is the number of Objects for dialog box: " + numOfObjects);
			
			if (numOfObjects >= 1) {
				GuiTestObject myGTO = (GuiTestObject) TOs[0];
				myGTO.waitForExistence(120, 0.10);
				text = myGTO.getProperty(".text").toString().trim();
				System.out.println("Text found in the dialog box:: " + text);
				
				vpManual(vp_Name, text, expectedValue).compareAndLog();
				LogScreenShot("Pop-Up Message Verification");
				
				WaitNClickDialogue(myBrowser, ".class", "Html.DialogButton", "OK");
			} else {
				System.out.println("There is no Dialog Box");
				vpManual("PopUp_Message_VP", text, expectedValue).compareAndLog();
				Status = false;
			}
		} catch (RuntimeException e) {
			Status = false;
			System.out.println("Exception has occurred while reading a Dialog Box");
			e.printStackTrace();
		}
		return Status;
	}
	
	/**
	 * This function reads text of pop-up dialog box and compares it with expectedValue string as
	 * a Manual Verification Point.  If dialog box is not available then VP will fail. 
	 * 
	 * @param name - Name of the Manual Verification Point (MUST BE LESS THAN 30 CHARS; NO WHITE SPACES)
	 * @param expectedValue - Expected Value of the Manual Verification Point
	 * 
	 * @return boolean
	 * @author Dipen Desai
	 * @since 2/17/2009
	 */
	public boolean readDialog_Compare(String expectedValue) {
		boolean Status = true;
		try {
			String text = "";
			
			RootTestObject rTO = getRootTestObject();
			TestObject[] TOs = rTO.find(atDescendant(".class", "Html.DialogStatic", ".classIndex", "1"));
			int numOfObjects = TOs.length;
			System.out.println("This is the number of Objects for dialog box: " + numOfObjects);
			
			if (numOfObjects >= 1) {
				GuiTestObject myGTO = (GuiTestObject) TOs[0];
				myGTO.waitForExistence(120, 0.10);
				text = myGTO.getProperty(".text").toString().trim();
				System.out.println("Text found in the dialog box:: " + text);
				
				if(!text.equals(expectedValue)) {
					Status = false;
				}
				LogScreenShot("Pop-Up Message Verification");
				
				WaitNClickDialogue(myBrowser, ".class", "Html.DialogButton", "OK");
			} else {
				log("NO DIALOG BOX available to Compte Text.");
				Status = false;
			}
		} catch (RuntimeException e) {
			Status = false;
			System.out.println("Exception has occurred while reading a Dialog Box");
			e.printStackTrace();
		}
		return Status;
	}
	
	public boolean sendEmail_RFT(String[] to, String subj, String content) {
		boolean status = true;
		
		// Add Handlers for Main MIME Types
		MailcapCommandMap mc = (MailcapCommandMap)CommandMap.getDefaultCommandMap();
		mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
		mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
		mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
		mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
		mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
		CommandMap.setDefaultCommandMap(mc);
        
        // Create properties, get Session
        Properties props = new Properties();

        // Specify HOST (Host = MAIL02.fisalan.nycnet)
        props.put("mail.smtp.host", "MAIL02.fisalan.nycnet");
        
//        // To see what is going on behind the scene. (UNCOMMENT IT FOR DEBUG.)
//        props.put("mail.debug", "true");
        
        // Define the Session
        Session session = Session.getInstance(props);
        
        try {
            // Create a New Message on Current Session
            Message msg = new MimeMessage(session);

            // Set Sent From Address
            String user = System.getProperty ("user.name");
    		user = user.substring(user.length() - 1, user.length()) + user.substring(0, user.length() - 1);
            msg.setFrom(new InternetAddress(user + "@fisa.nyc.gov"));
            
            // Set Sent To Addresses
            InternetAddress[] toAdd = new InternetAddress[to.length];
            for(int cTo=0; cTo<to.length; cTo++) {
            	toAdd[cTo] = new InternetAddress(to[cTo]);
            }
            msg.setRecipients(Message.RecipientType.TO, toAdd);
            
//            // Set Sent CC Addresses (DEFAULT - City Automation Testing Team)
//            InternetAddress[] automationTeam = {new InternetAddress("ddesai@fisa.nyc.gov"), new InternetAddress("aabraham@fisa.nyc.gov"), new InternetAddress("sgrover@fisa.nyc.gov"), new InternetAddress("malluri@fisa.nyc.gov"), new InternetAddress("magrawal@fisa.nyc.gov")};
//            msg.setRecipients(Message.RecipientType.CC, automationTeam);
//            
//            // Set Sent BCC Addresses (DEFAULT - City Automation Testing Team)
//            InternetAddress[] automationTeam = {new InternetAddress("ddesai@fisa.nyc.gov"), new InternetAddress("aabraham@fisa.nyc.gov"), new InternetAddress("sgrover@fisa.nyc.gov"), new InternetAddress("malluri@fisa.nyc.gov"), new InternetAddress("magrawal@fisa.nyc.gov")};
//            msg.setRecipients(Message.RecipientType.BCC, automationTeam);
            
            // Set Subject
            msg.setSubject(subj);
            
            // Set Sent Date
            msg.setSentDate(new java.util.Date());

            // Set Message Content
            msg.setContent(content, "text/html");
            
            // Send the message
            Transport.send(msg);
            log("Email Sent - " + msg.getSubject());
        } catch (MessagingException mex) {
        	status = false;
        	log("Email has NOT been sent.");
            mex.printStackTrace();
        }
        return status;
	}
	
	public ResultSet readResultSet(String fileName, String sQuery) {
		String tempDirectory = "D:\\RationalTemp\\";
		
		// Print File Name and Query
		log("XL File: " + fileName);
		log("Query: " + sQuery);
		
		try {
			// Enable JDBC ODBC Driver to access excel
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			
			// Copy the Excel file to local directory if it is not available
			fileName = copyToTemp(fileName, tempDirectory, true);
			System.out.println("Temp File: " + fileName);
			
			// Set Connection to Excel File
			c = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xls)};DBQ=" + fileName + "; READONLY=true");
			
			// Crt Statement Object to be used to query Excel File
			stmnt = c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			// Get a ResultSet by executing the Select Statement
			rs = stmnt.executeQuery(sQuery);
			
			// Get Column Count
			data = rs.getMetaData();
			colCount = data.getColumnCount();
			
			// Get Row Count
			rs.last();
			rowCount = rs.getRow();
			
			// Go to the Desired Row
			if(rowCount >= line && line > 0){
				rs.absolute(line);
			} else {
				rs.first();
			}
			
			log("COLUMN COUNT: " + colCount + " --- ROW COUNT: " + rowCount + " --- START LINE NO: " + line);
			return rs;
		} catch (Exception e) {
			log("Exception: " + e.getMessage());
			rs = null;
			return rs;
		}
	}
	
	/**
	 * This function generates RANDOM character String.
	 * 
	 * @param sOrig - Input String
	 * @return String
	 */
	public String MakeAllAplha(String sOrig) {
		String sRtn;
		sRtn = sOrig;
		String sAlpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		int iLen = sOrig.length();
		int iA;
		int iX;
		String sA2;
		sRtn = sOrig;

		for (iX = 0; iX < iLen; iX++) {
			String sA = sOrig.substring(iX, (iX + 1));
			sA2 = sA;

			try {
				try {
					iA = Integer.parseInt(sA);
				} catch (Exception EXAA) {
					iA = -1;
				}
				if (iA == 0) {
					sA2 = "Z";
				}
				if (iA >= 1) {
					iA = iA - 1;
					sA2 = sAlpha.substring(iA, (iA + 1));
				}
				sOrig = sOrig.replaceFirst(sA, sA2);
			} catch (Exception EXA) {
			}
		}
		sOrig = sOrig.replaceAll(" ", "");
		sRtn = sOrig;
		return sRtn;
	}
}