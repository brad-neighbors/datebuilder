package com.bradneighbors.builders;

import org.apache.commons.lang3.builder.Builder;
import org.joda.time.DateTimeZone;
import org.joda.time.MutableDateTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A convenient utility to build dates easily.
 * Uses the joda-time library to manipulate dates.
 *
 * <p>All dates will be in UTC unless specified with inTimeZone()</p>
 *
 * <p>
 * Examples: <code>import static DateBuilder.*;</code>
 * <ul>
 * <li><code>Date oneYearAgoAtMidnight = now().inYear(2009).atMidnightExactly().build();</code></li>
 * <li><code>Date yesterday = yesterday().build();</code></li>
 * <li><code>Date tomorrow = tomorrow().build();</code></li>
 * <li><code>Date birthday = MM_dd_yyyy("08_29_1974").build();</code></li>
 * <li><code>Date birthday = now().inYear(1974).inMonth(8).onDay(29).build();</code></li>
 * </ul>
 * </p>
 */
public class DateBuilder implements Builder<Date> {

    private MutableDateTime dateTime = new MutableDateTime();

    /**
     * Gets a builder starting with current time.
     *
     * @return The builder.
     */
    public static DateBuilder now() {
        return new DateBuilder(System.currentTimeMillis());
    }

    /**
     * Gets a builder starting with the current date at midnight exactly.
     *
     * @return The builder.
     */
    public static DateBuilder today() {
        return new DateBuilder(System.currentTimeMillis()).atMidnightExactly();
    }

    /**
     * Gets a builder starting with yesterday's date at current time.
     *
     * @return The builder.
     */
    public static DateBuilder yesterday() {
        return new DateBuilder(System.currentTimeMillis()).subtractDays(1);
    }

    /**
     * Gets a builder starting with tomorrow's date at the current time.
     *
     * @return The builder.
     */
    public static DateBuilder tomorrow() {
        return new DateBuilder(System.currentTimeMillis()).addDays(1);
    }

    /**
     * Gets a builder with starting date at the specified date at midnight.
     * <p>
     * <code>Date august29_1974 = DateBuilder.aDate_MM_dd_YYYY("08_29_1974').build();</code>
     * </p>
     *
     * @param date the date string of format MM_DD_YYYY
     * @return The builder.
     * @throws java.lang.IllegalArgumentException When the supplied date string can't be converted to a date.
     */
    public static DateBuilder MM_dd_yyyy(String date) {
        DateFormat df = new SimpleDateFormat("MM_dd_yyyy");
        try {
            return new DateBuilder(df.parse(date).getTime()).atMidnightExactly();
        } catch (ParseException e) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * @param timeInMillis the current time in milliseconds
     */
    private DateBuilder(long timeInMillis) {
        dateTime = new MutableDateTime(timeInMillis, DateTimeZone.UTC);
    }

    /**
     * Instructs the builder to build the date with seconds / milliseconds = 0.
     *
     * @return The builder.
     */
    public DateBuilder atMidnightExactly() {
        dateTime.setHourOfDay(0);
        dateTime.setMillisOfDay(0);
        return this;
    }

    /**
     * Instructs the builder to add days to the date.
     *
     * @param numDays the number of days to add
     * @return The builder.
     */
    public DateBuilder addDays(int numDays) {
        dateTime.addDays(numDays);
        return this;
    }

    /**
     * Instructs the builder to add years to the date.
     *
     * @param numYears the number of years to add
     * @return The builder.
     */
    public DateBuilder addYears(int numYears) {
        dateTime.addYears(numYears);
        return this;
    }

    /**
     * Instructs the builder to subtract days from the date.
     *
     * @param numDays the number of days to subtract
     * @return The builder.
     */
    public DateBuilder subtractDays(int numDays) {
        dateTime.addDays(-numDays);
        return this;
    }

    /**
     * Instructs the builder to build the date in the specified year.
     *
     * @param year the year in which the date should occur
     * @return The builder.
     */
    public DateBuilder inYear(int year) {
        dateTime.setYear(year);
        return this;
    }

    /**
     * Instructs the builder to build the date in the specified month, one-based. (1=JAN, 2=FEB) etc.
     *
     * @param month the month, one-based
     * @return The builder.
     */
    public DateBuilder inMonth(int month) {
        dateTime.setMonthOfYear(month);
        return this;
    }

    /**
     * Instructs the builder to build the date on the specifed day of the month.
     *
     * @param day the day of the month
     * @return The builder.
     */
    public DateBuilder onDay(int day) {
        dateTime.setDayOfMonth(day);
        return this;
    }

    /**
     * Adds a number of hours to the date.
     *
     * @param hours the number of hours to add
     * @return The builder.
     */
    public DateBuilder addHours(int hours) {
        dateTime.addHours(hours);
        return this;
    }

    /**
     * Subtracts the specified number of the minutes to the date to be built.
     *
     * @param minutes num of minutes to subtract
     * @return The builder.
     */
    public DateBuilder subtractMinutes(int minutes) {
        dateTime.addMinutes(-minutes);
        return this;
    }

    public Date build() {
        return dateTime.toDate();
    }
}