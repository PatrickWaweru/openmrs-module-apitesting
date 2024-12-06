[![Build Status](https://travis-ci.org/openmrs/openmrs-module-apitesting.svg?branch=master)](https://travis-ci.org/openmrs/openmrs-module-apitesting)

# openmrs-module-apitesting

Description
-----------
To test various APIs locally.

The project WIKI page:
https://wiki.openmrs.org/display/projects/OpenMRS+apitesting+module

Development
-----------
$ mvn clean install -DskipTests=true

Use
---
1. EID Labware LabManifest Testing
    a. configure the global properties
        - Push URL (labware_eid_server_url): http://127.0.0.1:9677/openmrs/ws/rest/v1/apitesting/eidlabware/eidpush (Adjust depending on your local server)
        - Pull URL (labware_eid_server_result_url): http://127.0.0.1:9677/openmrs/ws/rest/v1/apitesting/eidlabware/eidpull (Adjust depending on your local server)
    b. no need to set a token: (labware_eid_server_api_token)
    c. To begin testing HIE patients (children under 2 yrs of age):
        - Use the SQL: SELECT person_id, uuid
            FROM person
            WHERE birthdate >= CURDATE() - INTERVAL 2 YEAR;
        - Make sure the patient has a HIE number
        - Create a "HIV DNA POLYMERASE CHAIN REACTION, QUALITATIVE" test order
        - insert the order into an EID manifest
2. VL Labware LabManifest Testing -- TODO
3. FLU Labware LabManifest Testing -- TODO
4. VL CHAI LabManifest Testing -- TODO
5. VL EDARP LabManifest Testing -- TODO

Installation 
------------
If you want to upload this module into OpenMRS instance then please follow
1. Build the module to produce the .omod file.
2. Use the OpenMRS Administration > Manage Modules screen to upload and install the .omod file or just throw the omod into the modules directory and restart the tomcat server
