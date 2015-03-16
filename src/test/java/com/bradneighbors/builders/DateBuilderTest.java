package com.bradneighbors.builders;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static com.bradneighbors.builders.DateBuilder.MM_dd_yyyy;
import static com.bradneighbors.builders.DateBuilder.now;
import static com.bradneighbors.builders.DateBuilder.today;
import static com.bradneighbors.builders.DateBuilder.tomorrow;
import static com.bradneighbors.builders.DateBuilder.yesterday;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DateBuilderTest {

    private static String systemTimeZone;

    @BeforeClass
    public static void setTimeZoneToUtc() {
        systemTimeZone = System.getProperty("user.timezone");
        System.setProperty("user.timezone", "UTC");
    }

    @AfterClass
    public static void restoreSystemTimeZone() {
        System.setProperty("user.timezone", systemTimeZone);
    }

    @Test
    public void buildsSpecifiedDateToFormat() {
        Date august29_1974 = MM_dd_yyyy("08_29_1974").build();
        assertEquals(146966400000L, august29_1974.getTime());
    }

    @Test
    public void addsADayToDateBeingBuilt() {
        Date date = MM_dd_yyyy("12_01_2012").addDays(2).build();
        assertEquals(1354492800000L, date.getTime());
    }

    @Test
    public void addsADayToDateBeingBuilt_DaysCanBeNeg() {
        Date date = MM_dd_yyyy("12_01_2012").addDays(-2).build();
        assertEquals(1354147200000L, date.getTime());
    }

    @Test
    public void buildsDatesAtMidnightExactly() {
        Date date = now().atMidnightExactly().build();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        assertEquals(0, cal.get(Calendar.MILLISECOND));
        assertEquals(0, cal.get(Calendar.MINUTE));
        assertEquals(0, cal.get(Calendar.HOUR));
    }

    @Test
    public void subtractsDays() {
        Date date = MM_dd_yyyy("12_01_2012").subtractDays(1).build();
        assertEquals(1354233600000L, date.getTime());
    }

    @Test
    public void buildsDateInSpecifiedYear() {
        Date date = now().inYear(2000).build();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        assertEquals(2000, cal.get(Calendar.YEAR));
    }

    @Test
    public void buildsDateInSpecifiedMonth() {
        Date date = today().inMonth(1).build();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        assertEquals(Calendar.JANUARY, cal.get(Calendar.MONTH));
    }

    @Test
    public void buildsDateOnSpecifiedDayOfMonth() {
        Date date = now().onDay(1).build();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        assertEquals(1, cal.get(Calendar.DAY_OF_MONTH));
    }

    @Test(expected = IllegalArgumentException.class)
    public void mustSpecifyPropertyFormattedStringForMM_dd_yyyy() {
        MM_dd_yyyy("crap_01_02015_foo").build();
    }

    @Test
    public void buildsYesterday() {
        Date yesterday = yesterday().atMidnightExactly().build();
        Date today = today().atMidnightExactly().build();
        assertTrue(yesterday.before(today));
    }

    @Test
    public void buildsTomorrow() {
        Date tomorrow = tomorrow().atMidnightExactly().build();
        Date today = today().atMidnightExactly().build();
        assertTrue(tomorrow.after(today));
    }

    @Test
    public void canAddYears() {
        Date today = today().build();
        Date today10YearsFromNow = today().addYears(10).build();

        Calendar todayCal = Calendar.getInstance();
        todayCal.setTime(today);

        Calendar today10YearsFromNowCal = Calendar.getInstance();
        today10YearsFromNowCal.setTime(today10YearsFromNow);

        assertEquals(10, today10YearsFromNowCal.get(Calendar.YEAR) - todayCal.get(Calendar.YEAR));
    }

    @Test
    public void canAddHours() {
        Date date = MM_dd_yyyy("12_01_2012").addHours(1).build();
        assertEquals(1354323600000L, date.getTime());
    }

    @Test
    public void canSubtractMinutes() {
        Date date = MM_dd_yyyy("12_01_2012").subtractMinutes(1).build();
        assertEquals(1354319940000L, date.getTime());
    }
}