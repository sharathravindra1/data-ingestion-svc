package com.personal.dataingestionsvc.controller;

import com.personal.dataingestionsvc.service.IngestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ingest")
@Slf4j
public class IngestionController {

    @Autowired
    IngestionService ingestionService;

    @PostMapping("/queue")
    public ResponseEntity post(@RequestBody String applicationNumbers) {
            log.info("Posting {} to queue", applicationNumbers);
            try{
                ingestionService.sendMsgToSqs(applicationNumbers);
                return ResponseEntity.ok(" Request is being processed");
            }

            catch (Exception e){

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
            }
    }

}
