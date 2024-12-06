package org.openmrs.module.apitesting.web.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.openmrs.annotation.Authorized;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.openmrs.module.webservices.rest.web.v1_0.controller.BaseRestController;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.response.ResponseException;
import org.openmrs.module.apitesting.utils.ApiTestingUtils;
import org.openmrs.module.apitesting.utils.SimpleObject;
import org.codehaus.jackson.map.ObjectMapper;
import java.io.StringWriter;
import java.io.BufferedReader;

@Controller
@RequestMapping(value = "/rest/" + RestConstants.VERSION_1 + "/apitesting/eidlabware")
public class EIDLabwareRestController extends BaseRestController {
	
	/**
	 * Send EID Lab Data // Local server URL: http://127.0.0.1:9342/apitesting/eidlabware/eidpush
	 * 
	 * @param request
	 * @return response proxy
	 */
	@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.OPTIONS })
	// @Authorized
	@RequestMapping(method = RequestMethod.POST, value = "/eidpush", produces = "application/json")
	@ResponseBody
	public Object eidPush(HttpServletRequest request) {
		System.out.println("API TESTING MODULE: EID Lab MOCK: Push Samples");
		String successret = "{\n" + //
		        "    \"status\": \"OK\",\n" + //
		        "    \"message\": \"Test data saved successfully.\"\n" + //
		        "}";
		
		String failureret = "{\n"
		        + //
		        "    \"status\": \"ERROR\",\n"
		        + //
		        "    \"message\": \"Error saving data.\",\n"
		        + //
		        "    \"errors\": [\n"
		        + //
		        "        {\n"
		        + //
		        "            \"0\": \"23000\",\n"
		        + //
		        "            \"SQLSTATE\": \"23000\",\n"
		        + //
		        "            \"1\": 2627,\n"
		        + //
		        "            \"code\": 2627,\n"
		        + //
		        "            \"2\": \"[Microsoft][ODBC Driver 17 for SQL Server][SQL Server]Violation of PRIMARY KEY constraint 'PK_C_NHRL_EID_RL'. Cannot insert duplicate key in object 'dbo.C_NHRL_EID_RL'. The duplicate key value is (841954).\",\n"
		        + //
		        "            \"message\": \"[Microsoft][ODBC Driver 17 for SQL Server][SQL Server]Violation of PRIMARY KEY constraint 'PK_C_NHRL_EID_RL'. Cannot insert duplicate key in object 'dbo.C_NHRL_EID_RL'. The duplicate key value is (841954).\"\n"
		        + //
		        "        },\n"
		        + //
		        "        {\n"
		        + //
		        "            \"0\": \"01000\",\n"
		        + //
		        "            \"SQLSTATE\": \"01000\",\n"
		        + //
		        "            \"1\": 3621,\n"
		        + //
		        "            \"code\": 3621,\n"
		        + //
		        "            \"2\": \"[Microsoft][ODBC Driver 17 for SQL Server][SQL Server]The statement has been terminated.\",\n"
		        + //
		        "            \"message\": \"[Microsoft][ODBC Driver 17 for SQL Server][SQL Server]The statement has been terminated.\"\n"
		        + //
		        "        }\n" + //
		        "    ]\n" + //
		        "}";
		
		StringBuilder stringBuilder = new StringBuilder();
		try {
			BufferedReader reader = request.getReader();
			String line;
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line);
			}
		}
		catch (Exception et) {}
		String payload = stringBuilder.toString();
		
		System.out.println("API TESTING MODULE: EID Lab MOCK: Push Samples: Payload: " + payload);
		
		int rad = ApiTestingUtils.getRandomZeroOrOne();
		
		if (rad == 0) {
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(successret);
		} else {
			return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(failureret);
		}
	}
	
	/**
	 * Get EID Lab results // Example URL:
	 * http://covid2.nphl.go.ke/api/eid-exchange?mfl_code=5287&order_no
	 * =842308%2C841682%2C842533%2C840744 // Local server URL:
	 * http://127.0.0.1:9342/apitesting/eidlabware
	 * /eidpull?mfl_code=5287&order_no=842308%2C841682%2C842533%2C840744
	 * 
	 * @param mfl_code (the facility code)
	 * @param order_no (the list of order numbers)
	 * @throws ResponseException
	 */
	@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.OPTIONS})
	// @Authorized
    @RequestMapping(value = "/eidpull", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResponseEntity get(@RequestParam(value = "mfl_code") String mflCode, @RequestParam(value = "order_no") String orderNo, HttpServletRequest request, HttpServletResponse response) throws ResponseException {
        System.out.println("API TESTING MODULE: EID Lab MOCK: Pull Results - MFL Code: " + mflCode + " || Order Nos. : " + orderNo);

		String errorMessage = "{\n" + //
						"    \"status\": \"ERROR\",\n" + //
						"    \"message\": \"No test data found for ORDER NO: 842308,841682,842533,840744 and MFL_CODE: 0\",\n" + //
						"    \"errors\": \"404: Not found\"\n" + //
						"}";
        
		List<String> orders = ApiTestingUtils.splitString(orderNo, ",");

		String responsePayload = "";
		List<SimpleObject> responseArray = new LinkedList<>();
		int sweeper = 0;
		String negativeResult = "Negative";
		String positiveResult = "Positive";

		for(String order : orders) {
			String result = negativeResult;
			if(sweeper == 0) {
				result = negativeResult;
				sweeper = 1;
			} else if(sweeper == 1) {
				result = positiveResult;
				sweeper = 0;
			}

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
            Date currentDate = new Date();
            String formattedDate = dateFormat.format(currentDate);

			SimpleObject dateTested = SimpleObject.create("timezone_type", 3,
            "timezone", "Africa/Nairobi",
			"date", formattedDate
			);

			SimpleObject samplePayload = SimpleObject.create("order_number", ApiTestingUtils.convertStringToInt(order),
			"date_received", dateTested,
			"date_tested", dateTested,
			"sample_status", "Complete",
        	"lab_no", "2126520",
        	"result", result);

			responseArray.add(samplePayload);
		}

		ObjectMapper mapper = new ObjectMapper();
		StringWriter sw = new StringWriter();
		try {
			mapper.writeValue(sw, responseArray);
			responsePayload = sw.toString();
		} catch(Exception er) {
			System.err.println("API TESTING MODULE: EID Lab MOCK: Pull Results: Error. Failed to build payload");
			er.printStackTrace();
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorMessage);
		}

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(responsePayload);
    }
}
