package controller.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public abstract class DateUtil {
    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

    /* Class for keeping information about the start date and the end date*/
    public static class Range{
        private Date startDate;
        private Date endDate;

        public Range() {
        }

        public Range(Date startDate, Date endDate) {
            this.startDate = startDate;
            this.endDate = endDate;
        }

        public Date getStartDate() {
            return startDate;
        }

        public void setStartDate(Date startDate) {
            this.startDate = startDate;
        }

        public Date getEndDate() {
            return endDate;
        }

        public void setEndDate(Date endDate) {
            this.endDate = endDate;
        }
    }

    /**
     *   This method is used for determine the values of periods .
     *   @return an hashMap of period, the first of which is the Integer key, the second the String determination
     */
    public static HashMap<Integer,String> timePeriod(){
        HashMap<Integer,String> periods = new HashMap<>();
        periods.put(0,"");
        periods.put(1,"LastQtr");
        periods.put(2,"Last Month");
        periods.put(3,"Last Calendar Year");
        periods.put(4,"Current Year to Date");
        periods.put(5,"Current Qtr to Date");
        periods.put(6,"Current Month to Date");

        return periods;
    }

    /**
     *   This method is used for determine the date range for the period .
     *
     *   @param s the Date that selected as starting
     *   @param e the Date that selected as ending
     *   @param period the param, that select a period of dates
     *   @return an array of two Date objects, the first of which is the start date, the second the end date
     */
    public static Range getDates(String s, String e, String period){

        switch (period){
            case "0" :
                return new Range(convertStringToDate(s),convertStringToDate(e));
            case "1" :
                return getPreviousQuarter(new Date());
            case "2" :
                return getPreviousMonth(new Date());
            case "3" :
                return getPreviousYear(new Date());
            case "4" :
                return getYearToDate(new Date());
            case "5" :
                return getQuarterToDate(new Date());
            case "6" :
                return getMonthToDate(new Date());

        }

        return null;
    }

    /**
     *   This method is used to determine the date range for the current quarter to date.
     *
     *   @param date the Date that the date range is to be based on
     *   @return an array of two Date objects, the first of which is the start date, the second the end date
     */
    public static Range getQuarterToDate(Date date){
        final GregorianCalendar current = makeCalendar(date);
        final Range pair = new Range();

        pair.setEndDate(current.getTime());

        // Get the dates for the start and the end of the quarter
        current.set(Calendar.MONTH, Quarter.valueOf(current.get(Calendar.MONTH)).startMonth());
        current.set(Calendar.DAY_OF_MONTH,1);

        pair.setStartDate(current.getTime());

        return pair;
    }

    /**
     *   This method is used to determine the date range for the start of the current year to the current day in that year.
     *
     *   @param date the Date that the date range is to be based on
     *   @return an array of two Date objects, the first of which is the start date, the second the end date
     */
    public static Range getYearToDate(final Date date){
        final GregorianCalendar c = makeCalendar(date);

        final Range range = new Range();

        range.setEndDate(c.getTime());

        c.set(Calendar.DAY_OF_YEAR,1);

        range.setStartDate(c.getTime());

        return(range);
    }

    /**
     *   This method is used to determine the date range for the start of the current month to the current day.
     *
     *   @param date the Date that the date range is to be based on
     *   @return an array of two Date objects, the first of which is the start date, the second the end date
     */
    public static Range getMonthToDate(final Date date){
        final GregorianCalendar c = makeCalendar(date);
        final Range range = new Range();

        range.setEndDate(c.getTime());
        c.set(Calendar.DAY_OF_MONTH,1);
        range.setStartDate(c.getTime());

        return(range);
    }

    /**
     *   This method is used to determine the date range for the previous year.
     *
     *   @param date Date that the date range is to be based on
     *   @return an array of two Date objects, the first of which is the start date, the second the end date \
     */
    public static Range getPreviousYear(final Date date){
        final GregorianCalendar c = makeCalendar(date);

        final Range range = new Range();

        c.roll(Calendar.YEAR,-1);

        c.set(Calendar.DAY_OF_YEAR, c.getActualMinimum(Calendar.DAY_OF_YEAR));
        range.setStartDate(c.getTime());

        c.set(Calendar.DAY_OF_YEAR, c.getActualMaximum(Calendar.DAY_OF_YEAR));
        range.setEndDate(c.getTime());

        return(range);
    }

    /**
     *   This method is used to determine the date range for the previous quarter.
     *
     *   @param date the Date that the date range is to be based on
     *   @return an array of two Date objects, the first of which is the start date, the second the end date
     */
    public static Range getPreviousQuarter(final Date date){
        final GregorianCalendar c = makeCalendar(date);
        final Range range = new Range();
        final Quarter prevQtr = Quarter.valueOf(c.get(Calendar.MONTH)).previous();

        // roll back a year if necessary
        if(prevQtr.equals(Quarter.FOURTH)){c.roll(Calendar.YEAR,-1);}

        // Get the dates for the start and the end of the quarter
        c.set(Calendar.MONTH,prevQtr.startMonth());
        c.set(Calendar.DAY_OF_MONTH,1);
        range.setStartDate(c.getTime());

        c.set(Calendar.MONTH,prevQtr.endMonth());
        c.set(Calendar.DAY_OF_MONTH,c.getActualMaximum(Calendar.DAY_OF_MONTH));
        range.setEndDate(c.getTime());

        return(range);
    }

    /**
     *   This method is used to determine the date range for the month previous to the current month.
     *
     *   @param date the Date that the date range is to be based on
     *   @return an array of two Date objects, the first of which is the start date, the second the end date
     */
    public static Range getPreviousMonth(final Date date){
        final GregorianCalendar c = makeCalendar(date);

        final Range range = new Range();

        c.roll(Calendar.MONTH,-1);
        c.set(Calendar.DAY_OF_MONTH,1);
        range.setStartDate(c.getTime());
        c.set(Calendar.DAY_OF_MONTH,c.getActualMaximum(Calendar.DAY_OF_MONTH));
        range.setEndDate(c.getTime());

        return(range);
    }

    /**
     *   This method is used for determine the date range for the month previous to the current month.
     *
     *   @param s the String that contains the date
     *   @return a converted Date object
     */
    public static Date convertStringToDate(String s){

        List<SimpleDateFormat> knownPatterns = new ArrayList<>();
        knownPatterns.add(new SimpleDateFormat("MMM d, yyyy", Locale.ENGLISH));
        knownPatterns.add(new SimpleDateFormat("MMM d,yyyy", Locale.ENGLISH));

        Date date = standardDate();

        for (SimpleDateFormat pattern : knownPatterns) {
            try {
                date = pattern.parse(s);

            } catch (ParseException pe) {
                logger.error("Error convert to date string " + s);
            }
        }

        if(s.isEmpty()) return null;

        return date;
    }

    /**
     *  This method is used for create the necessary calendar object from the given date.
     *
     *  @param date the date to be set as current
     *  @return a calendar representing the given date
     */
    private static GregorianCalendar makeCalendar(Date date){
        final GregorianCalendar c = (GregorianCalendar)GregorianCalendar.getInstance();
        if(date != null){
            c.setTime(date);
        }
        return(c);
    }

    /**
     *   This method is used for create a standardDate.
     *
     *   @return a new Date object, which equals Jan 1, 1970
     */
    public static Date standardDate(){
        return new Date(-10000);
    }



}
