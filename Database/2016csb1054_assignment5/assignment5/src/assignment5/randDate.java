/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment5;

/**
 *
 * @author rishabh
 */
// A class to generate Random Date in java.

//  Import Statement
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class randDate {

	private static String utilDate = null;
	
	// Method to generate the random Date.
    public String getDate(int start , int end) {

		GregorianCalendar gc = new GregorianCalendar();
		int year = randBetween(start, end);
		gc.set(gc.YEAR, year);
		int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));
		gc.set(gc.DAY_OF_YEAR, dayOfYear);
		String date = gc.get(gc.YEAR) + "/" + (gc.get(gc.MONTH) + 1) + "/" + gc.get(gc.DAY_OF_MONTH);
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
			utilDate = formatter.format(formatter.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return utilDate;

    }
    public static int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }
}