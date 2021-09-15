package com.example.dataingestionsvc.controller;

import com.example.dataingestionsvc.service.IngestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ingest")
@Slf4j
public class IngestionController {

    @Autowired
    IngestionService ingestionService;

    @PostMapping("/queue")
    public void post(@RequestBody String applicationNumber){
            log.info("Posting {} to queue", applicationNumber);
            ingestionService.sendMsgToSqs(applicationNumber);
    }

}
