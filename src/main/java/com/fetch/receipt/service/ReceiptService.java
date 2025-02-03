package com.fetch.receipt.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fetch.receipt.model.Item;
import com.fetch.receipt.model.Receipt;

@Service
public class ReceiptService {
    
    String regex = "[^a-zA-Z0-9]";

    //Used for generating uniqueId
    public String getUniqueId(){
        return UUID.randomUUID().toString();
    }

    //function for calculating the total number of points a receipt has
    public String calculatePoints(Receipt reciept){
        //counting alphanumeric in retailerName
        int retailerNamePoints = reciept.getRetailer().replaceAll(regex, "").length();

        //Checking if total is round dollar and multiple of 0.25
        int totalPoints = 0;
        String[] total = reciept.getTotal().split("\\.");
        if(total[1].equals("00")){
            totalPoints += 50;
        }
        if(Double.parseDouble(reciept.getTotal())%0.25 == 0){
            totalPoints += 25;
        }

        //no.of items in the receipt and trimmed length of shortdescription
        List<Item> items = reciept.getItems();
        int itemsLenPoints = 5 * (items.size()/2);
        int trimmedLenPoints = 0;
        for(int i=0;i<items.size();i++){
            int shortDescriptionLen = items.get(i).getShortDescription().trim().length();
            if(shortDescriptionLen % 3 == 0){
                trimmedLenPoints += (int) Math.ceil(Double.parseDouble(items.get(i).getPrice())*0.2);
            }
        }

        //Day is odd or not
        int date = Integer.parseInt(reciept.getPurchaseDate().split("-")[2]);
        int datePoints = date%2 == 0 ? 0 : 6;

        //purchase time between 2pm and 4pm
        int purchaseTime = Integer.parseInt(reciept.getPurchaseTime().split(":")[0]);
        int purchasePoints = purchaseTime >= 14 && purchaseTime<16 ? 10 :0 ;

        //final calculated points are returned
        int finalPoints = retailerNamePoints+totalPoints+itemsLenPoints+trimmedLenPoints+datePoints+purchasePoints;
        
        return String.valueOf(finalPoints);
    }

    
}
