package com.swp.jewelrystore.converter;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

@Component
public class DateTimeConverter {

    public String convertToDateTimeResponse(Date date) {
        StringTokenizer token = new StringTokenizer(date.toString(), " ");
        String dateResponse = token.nextToken();
        String timeResponse = token.nextToken();
        String[] splitDateResponse = dateResponse.split("-");
        String[] splitTimeResponse = timeResponse.split(":");
        return splitDateResponse[2] + "/" + splitDateResponse[1] + "/" + splitDateResponse[0] + " " +
                splitTimeResponse[0] + ":" + splitTimeResponse[1] + ":" + splitTimeResponse[2];
    }


    public static Date convertToDateTimeDTO(String date) {
        // String date truyền vào theo dạng dd/MM/yyyy HH:mm
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            // Parse the input string to a Date object using the input format
            Date parsedDate = inputFormat.parse(date + ":00");
            // Format the parsed Date object to the desired output format
            String formattedDate = outputFormat.format(parsedDate);
            // Parse the formatted date string back to a Date object
            Date dateDTO = outputFormat.parse(formattedDate);
            return dateDTO;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String convertToDateCompareToDB(String date) {
        StringTokenizer token = new StringTokenizer(date, "/");
        String day = token.nextToken();
        String month = token.nextToken();
        String year = token.nextToken();
        return year + "-" + month + "-" + day;
    }
}
