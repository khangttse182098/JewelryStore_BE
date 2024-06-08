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

    public Date convertToDateTimeDTO(String date) {
        StringTokenizer token = new StringTokenizer(date, " ");
        String timeResponse = token.nextToken();
        String dateResponse = token.nextToken();
        String[] splitDateResponse = dateResponse.split("/");
        String[] splitTimeResponse = timeResponse.split(":");
        String inputDate = splitDateResponse[2] + "/" + splitDateResponse[1] + "/" + splitDateResponse[0] + " " +
                splitTimeResponse[0] + ":" + splitTimeResponse[1] + ":" + splitTimeResponse[2];
        System.out.println(inputDate);
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        try {
            // Parse the input string to a Date object using the input format
            Date dateDTO = outputFormat.parse(inputDate);
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
