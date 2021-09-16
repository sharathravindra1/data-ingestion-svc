
package com.personal.dataingestionsvc.entity;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


@Data
@Entity
@Table(name = "patent_details", schema = "sys")
public class PatentDetails {
    @Id
    @Column(name = "application_number")
    private String patentApplicationNumber;
    @Column(name = "workflow_status")
    private String workflowStatus;
    @Column(name = "HK_CREATED")
    private Date createdDate;
    @Column(name ="HK_UPDATED")
    private Date updatedDate;
    @Column(name = "invention_subject_matter_category")
    private String inventionSubjectMatterCategory;
    @Column(name = "filing_date")
    private String filingDate;
    @Column(name = "main_CPC_symbol_text")
    private String mainCPCSymbolText;
    @Column(name = "further_CPC_symbol_array_text")
    private String furtherCPCSymbolArrayText;
    @Column(name = "inventor_name_array_text")
    private String inventorNameArrayText;
    @Column(name = "abstract_text")
    private String abstractText;
    @Column(name = "assignee_entity_name")
    private String assigneeEntityName;
    @Column(name = "assignee_postal_address_text")
    private String assigneePostalAddressText;
    @Column(name = "invention_title")
    private String inventionTitle;
    @Column(name = "file_location_URI")
    private String filelocationURI;
    @Column(name = "archive_URI")
    private String archiveURI;
    @Column(name = "claim_text")
    private String claimText;
    @Column(name = "description_text")
    private String descriptionText;
    @Column(name = "grant_document_identifier")
    private String grantDocumentIdentifier;
    @Column(name = "grant_date")
    private String grantDate;
    @Column(name = "patent_number")
    private String patentNumber;
    @Column(name  = "raw_text")
    private String raw_text;

}

