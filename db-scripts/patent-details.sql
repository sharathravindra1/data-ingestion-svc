docker run -d -p 1521:1521 --name oracle store/oracle/database-enterprise:12.2.0.1-slim
----- Create Patent Details
DROP TABLE patent_details;

CREATE TABLE patent_details
(
    HK_CREATED                        DATE NOT NULL,
    HK_UPDATED                        DATE NOT NULL,
    application_number                varchar2(300) NOT NULL,
    workflow_status                   varchar2(20),
    invention_subject_matter_category varchar2(300),
    filing_date                       varchar2(30),
    main_CPC_symbol_text              varchar2(300),
    further_CPC_symbol_array_text      clob,
    inventor_name_array_text           clob,
    abstract_text                     clob,
    assignee_entity_name              varchar2(100),
    assignee_postal_address_text      varchar2(300),
    invention_title                   varchar2(300),
    file_location_URI                 varchar2(300),
    archive_URI                       varchar2(300),
    claim_text                        clob,
    description_text                  clob,
    grant_document_identifier         varchar2(300),
    grant_date                        varchar2(300),
    patent_number                     varchar2(30),
    raw_text                          clob

)
INITRANS 20;

CREATE UNIQUE INDEX patent_details_PK ON patent_details(application_number)
INITRANS 20;

ALTER TABLE patent_details ADD PRIMARY KEY (application_number)
    USING INDEX patent_details_PK
ENABLE VALIDATE;

CREATE INDEX patent_details_pat_num
ON patent_details(patent_number);

--CREATE SEQUENCE patent_details_seq MINVALUE 100000000 INCREMENT BY 10 START WITH 100000005 CACHE 100;
GRANT SELECT, INSERT, UPDATE ON sys.patent_details to sys;
GRANT SELECT, INSERT, UPDATE ON sys.patent_details to system;
GRANT SELECT, INSERT, UPDATE ON patent_details to sys;
GRANT SELECT, INSERT, UPDATE ON patent_details to system;

commit;
