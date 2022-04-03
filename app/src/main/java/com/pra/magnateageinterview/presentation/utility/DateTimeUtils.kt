package com.pra.magnateageinterview.presentation.utility

import android.content.Context
import android.widget.Toast
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher

object DateTimeUtils {

    fun getCurrentDateTime(): String {
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date = Date()
        return dateFormat.format(date)
        //System.out.println(dateFormat.format(date))
    }

    fun isTimeBetweenTwoTime(startTime: String, endTime: String, currentTime: String): Boolean {
        val formatter = SimpleDateFormat("HH:mm:ss")
        val date_from: Date? = formatter.parse(startTime)
        val date_to: Date? = formatter.parse(endTime)
        val dateNow: Date? = formatter.parse(currentTime)

        if (date_to?.after(date_from)!!) {
            println("proper way to explain")
            return false
        } else {
            return date_from?.equals(dateNow)!! ||
                    date_to?.equals(dateNow)!! ||
                    date_from.before(dateNow) && date_to.after(dateNow)
        }
    }


    fun isBetweenLiedTwoTime(context: Context,startTime: String?, endTime: String?, currentTime: String): Boolean {

        val reg = "^([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$"

        if (startTime?.matches(reg.toRegex())!! && endTime?.matches(reg.toRegex())!!
            && currentTime.matches(reg.toRegex())
        ) {
            var valid = false

            // Start Time
            var startTime = SimpleDateFormat("HH:mm:ss").parse(startTime)
            val startCalendar = Calendar.getInstance()
            startCalendar.time = startTime!!

            // Current Time
            var currentTime = SimpleDateFormat("HH:mm:ss").parse(currentTime)
            val currentCalendar = Calendar.getInstance()
            currentCalendar.time = currentTime!!

            // End Time
            var endTime = SimpleDateFormat("HH:mm:ss").parse(endTime)
            val endCalendar = Calendar.getInstance()
            endCalendar.time = endTime

            //
            if (currentTime <= endTime) {
                currentCalendar.add(Calendar.DATE, 1)
                currentTime = currentCalendar.time
            }

            if (startTime < endTime) {
                startCalendar.add(Calendar.DATE, 1);
                startTime = startCalendar.time;

            }

            if (currentTime.before(startTime)) {
           //     Toast.makeText(context," Time is Lesser ",Toast.LENGTH_SHORT).show()
           //     System.out.println(" Time is Lesser ");
                valid = false;
            } else {
                if (currentTime.after(endTime)) {
                    endCalendar.add(Calendar.DATE, 1);
                    endTime = endCalendar.time;
                }

                System.out.println("Comparing , Start Time /n " + startTime);
                System.out.println("Comparing , End Time /n " + endTime);
                System.out.println("Comparing , Current Time /n " + currentTime);

                if (currentTime.equals(endTime) ||
                    currentTime.before(endTime)
                ) {
                    System.out.println("RESULT, Time lies b/w");
                    valid = true;
                } else {
                    valid = false;
                    Toast.makeText(context," RESULT, Time does not lies b/w ",Toast.LENGTH_SHORT).show()
                    System.out.println("RESULT, Time does not lies b/w");
                }

            }
            return valid
        } else {
            println("====> Not a valid time, expecting HH:MM:SS format")
            Toast.makeText(context," Not a valid time, expecting HH:MM:SS format ",Toast.LENGTH_SHORT).show()
            return false
        }
    }

    fun isNotNullAndNotEmpty(s: String?): Boolean {
        return if (s != null && !s.isEmpty()) {
            true
        } else {
            false
        }
    }

    fun isDateBetweenTwoDate(startDate: String?, endDate: String?, currentDate: String): Boolean {
        val formatter = SimpleDateFormat("yyyy-MM-dd")

        if (startDate != null && endDate != null) {
            val date_from: Date? = formatter.parse(startDate)
            val date_to: Date? = formatter.parse(endDate)
            val dateNow: Date? = formatter.parse(currentDate)

            return date_from?.equals(dateNow)!! ||
                    date_to?.equals(dateNow)!! ||
                    date_from.before(dateNow) && date_to.after(dateNow)

        } else if (startDate != null) {
            val date_from: Date? = formatter.parse(startDate)
            val dateNow: Date? = formatter.parse(currentDate)

            return date_from?.equals(dateNow)!! || date_from.before(dateNow)
        }
        return false
    }

}