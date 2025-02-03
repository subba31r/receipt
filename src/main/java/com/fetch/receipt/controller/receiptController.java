package com.fetch.receipt.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import com.fetch.receipt.model.Receipt;
import com.fetch.receipt.service.ReceiptService;

@RestController
@RequestMapping("/receipts")
public class receiptController {
    
    @Autowired 
    ReceiptService receiptService;

    Map<String, Receipt> data = new HashMap<>(); //for storing receipts data

    //REST API for receipt processing
    @PostMapping("/process")
    public ResponseEntity<Map<String, Object>> receiptProcess(@RequestBody Receipt receipt) {
        String uniqueId = receiptService.getUniqueId();
        while(data.containsKey(uniqueId)){
            uniqueId = receiptService.getUniqueId();
        }
        data.put(uniqueId, receipt);

        Map<String, Object> response = new HashMap<>();
        response.put("id",uniqueId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    //REST API for fetching the total no.of points a receipt has
    @GetMapping("{id}/points")
    public ResponseEntity<Map<String, Object>> receiptPoints(@PathVariable String id) {
        String points = receiptService.calculatePoints(data.get(id));

        Map<String, Object> response = new HashMap<>();
        response.put("points",points);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
