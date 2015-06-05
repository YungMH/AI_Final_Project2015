package tw.com.riversoft.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateUtil {

  public static final String PATTERN_YYYY_MM = "yyyy/MM";
  public static final String PATTERN_YYYY_MM_DD = "yyyy/MM/dd";
  public static final String PATTERN_YYYY_MM_DD_HH_MM_SS_DASH = "yyyy-MM-dd HH:mm:ss";
  public static final String PATTERN_YYYY_MM_DD_HH_MM_SS_SLASH = "yyyy/MM/dd HH:mm:ss";
  public static final String PATTERN_YYYYMMDD = "yyyyMMdd";

  public static class Quarter {
  	
  	public static final Quarter FIRST = new Quarter(DateUtil.getDate(1970, 1, 1), DateUtil.getDate(1970, 3, 31), 1);
  	public static final Quarter SECOND = new Quarter(DateUtil.getDate(1970, 4, 1), DateUtil.getDate(1970, 6, 30), 2);
  	public static final Quarter THIRD = new Quarter(DateUtil.getDate(1970, 7, 1), DateUtil.getDate(1970, 9, 30), 3);
  	public static final Quarter FOURTH = new Quarter(DateUtil.getDate(1970, 10, 1), DateUtil.getDate(1970, 12, 31), 4);
  	
  	private Calendar startDate;
  	private Calendar endDate;
  	private int quarter;
  	
  	private Quarter(Calendar startDate, Calendar endDate, int quarter) {
  		
  		int year = DateUtil.now().get(Calendar.YEAR);
  		this.startDate = startDate;
  		this.startDate.set(Calendar.YEAR, year);
  		this.endDate = endDate;
  		this.endDate.set(Calendar.YEAR, year);
  		this.quarter = quarter;
  	}
  	
  	public void setYear(int year) {
  		
  		getStartDate().set(Calendar.YEAR, year);
  		getEndDate().set(Calendar.YEAR, year);
  	}
  	
  	public int getQuarter() {
  		return this.quarter;
  	}
  	
		public Calendar getStartDate() {
			return startDate;
		}
		
		public Calendar getEndDate() {
			return endDate;
		}
  	
  }
  
  public static Quarter transferFromQuarter(int year, int quarter) {
  
  	Quarter q = null;
  	if (quarter == 1)
  		q = Quarter.FIRST;
  	else if (quarter == 2)
  		q = Quarter.SECOND;
  	else if (quarter == 3)
  		q = Quarter.THIRD;
  	else if (quarter == 4)
  		q = Quarter.FOURTH;
  	else
  		throw new RuntimeException("Quarter translation failed.");
  	
  	q.setYear(year);
  	return q;
  }
  
  public static int transferToQuarter(Date date) {
  	
  	Calendar c = GregorianCalendar.getInstance();
  	c.setTime(date);
  	int month = c.get(Calendar.MONTH) + 1;
  	if (month >= 1 && month <= 3)
  		return 1;
  	else if (month >= 4 && month <= 6)
  		return 2;
  	else if (month >= 7 && month <= 9)
  		return 3;
  	else if (month >= 10 && month <= 12)
  		return 4;
  	else
  		throw new RuntimeException("Can not transfer date to quarter");
  }
  
  /**
   * 把日期加上 23:59:59:999的時間，以利做資料庫的日期截止查詢條件
   */
  public static Date makeEndDateCriteria(Date date) {
    
    Calendar end = GregorianCalendar.getInstance();
    end.setTime(date);
    end.set(Calendar.HOUR, 23);
    end.set(Calendar.MINUTE, 59);
    end.set(Calendar.SECOND, 59);
    end.set(Calendar.MILLISECOND, 999);
    return end.getTime();
  }
  
  public static String getChineseDateFormat(Date date) {

    Calendar c = GregorianCalendar.getInstance();
    c.setTime(date);
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH) + 1;
    int day = c.get(Calendar.DATE);

    StringBuffer formated = new StringBuffer();
    formated.append(" ").append(year).append("年").append(month).append("月").append(day).append("日");
    return formated.toString();
  }

  public static Date getFirstDayOfCurrentMonth() {

    Calendar now = now();
    now.set(Calendar.DATE, 1);
    return now.getTime();
  }

  public static String dateFormater(Date date, String pattern) {
    return dateFormater(date, pattern, null);
  }
  
  public static String dateFormater(Date date, String pattern, Locale locale) {

    String formattedDate = "";
    if (date != null) {
      Calendar calendar = GregorianCalendar.getInstance();
      calendar.setTime(date);
      formattedDate = calendarFormater(calendar, pattern, locale);
    }
    return formattedDate;
  }
  
  public static String calendarFormater(Calendar date, String pattern) {
    return calendarFormater(date, pattern, null);
  }
  
  public static String calendarFormater(Calendar date, String pattern, Locale locale) {

    String formattedDate = "";
    if(locale == null) locale = Locale.US;
    if (date != null) {
      SimpleDateFormat formatter = new SimpleDateFormat(pattern, locale);
      formattedDate = formatter.format(date.getTime());
    }
    return formattedDate;
  }

  /**
   * @return today with 00:00:00
   */
  public static Calendar today() {
    
    Calendar now = now();
    now.set(Calendar.HOUR, 0);
    now.set(Calendar.MINUTE, 0);
    now.set(Calendar.SECOND, 0);
    now.set(Calendar.MILLISECOND, 0);
    now.set(Calendar.AM_PM, Calendar.AM);
    return now;
  }
  
  public static Calendar now() {
    return GregorianCalendar.getInstance();
  }

  public static Calendar getDate(int year, int month, int day) {

    month = month - 1; // month is 0 based
    Calendar date = new GregorianCalendar(year, month, day);
    date.set(Calendar.HOUR, 0);
    date.set(Calendar.MINUTE, 0);
    date.set(Calendar.SECOND, 0);
    date.set(Calendar.MILLISECOND, 0);
    date.set(Calendar.AM_PM, Calendar.AM);
    return date;
  }

  public static String getChineseDate() {

    StringBuffer date = new StringBuffer();
    Calendar calendar = GregorianCalendar.getInstance();
    String y = StringUtil.makeUpZero(String.valueOf(calendar.get(Calendar.YEAR) - 1911), 3);
    String m = StringUtil.makeUpZero(String.valueOf(calendar.get(Calendar.MONTH) + 1), 2);
    String d = StringUtil.makeUpZero(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)), 2);
    date.append(y).append(m).append(d);
    return date.toString();
  }

  public static String getChineseDateWithoutDay() {

    String dateString = getChineseDate();
    return dateString.substring(0, dateString.length() - 2);
  }

  /**
   * If date is null, use current time instead automatically
   */
  public static Date addDate(Date date, int day) {

    Calendar calendar = GregorianCalendar.getInstance();
    if (date != null)
      calendar.setTime(date);
    calendar.add(Calendar.DATE, day);
    return calendar.getTime();
  }

  public static int calculateAge(Date birthDay) {

    Calendar birth_c = Calendar.getInstance();
    birth_c.setTime(birthDay);

    Date today = new Date(System.currentTimeMillis());
    Calendar c2 = Calendar.getInstance();
    c2.setTime(today);

    long remaindar = c2.getTimeInMillis() - birth_c.getTimeInMillis();

    long aYear = (long) 1000 * (long) 60 * (long) 60 * (long) 24 * (long) 365;

    int age = (int) (remaindar / aYear);
    return age;
  }

  public static Date parseDate(String rawDate, String pattern) throws ParseException {
  	
  	Date date = null;
  	SimpleDateFormat dateFormatter = new SimpleDateFormat();
  	dateFormatter.applyPattern(pattern);
  	date = dateFormatter.parse(rawDate);
		
		return date;
  }
  
  public static int getLastDateOfMonth(int year, int month) {
    
    Calendar c = getDate(year, month, 1);
    c.add(Calendar.MONTH, 1);
    c.add(Calendar.DATE, -1);
    return c.get(Calendar.DATE);
  }
  
  /**
   * 計算兩日期間差幾天
   * @param date1 第一個日期
   * @param date2 第二個日期
   * @return 相差天數
   */
  public static Long getDateDiff(Date date1, Date date2) {

    long millisPerHour = 86400000L;
    
    Calendar c = Calendar.getInstance();
    c.setTime(date1);
    c.set(Calendar.HOUR, 0);
    c.set(Calendar.MINUTE, 0);
    c.set(Calendar.SECOND, 0);
    c.set(Calendar.MILLISECOND, 0);
    Long date1Millis = c.getTimeInMillis();
    c.setTime(date2);
    c.set(Calendar.HOUR, 0);
    c.set(Calendar.MINUTE, 0);
    c.set(Calendar.SECOND, 0);
    c.set(Calendar.MILLISECOND, 0);
    Long date2Millis = c.getTimeInMillis();
    return (date2Millis - date1Millis) / millisPerHour;
  }
}
