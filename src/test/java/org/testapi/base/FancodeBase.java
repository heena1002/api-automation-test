package org.testapi.base;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testapi.util.TestUtils;

import java.util.*;

import static org.testapi.base.CommonRestAssuredMethods.*;

public class FancodeBase {
    static Response response;
    static final String todosEndpoint = TestUtils.getProperty("test.api.path.todos");
    static final String usersEndpoint = TestUtils.getProperty("test.api.path.users");
        public Response getApiResponse(String api){
            if(api.equals("todos"))
                response = fetchResponse("get", todosEndpoint);
            else if(api.equals("users"))
                response = fetchResponse("get", usersEndpoint);

            return response;
    }

    protected List<Integer> fetchFancodeUsers(Response res){
         List<Integer> fancodeUsers = new ArrayList<>();
        JsonPath jsonPath = new JsonPath(res.asString());

        int users = jsonPath.getInt("res.size()");
        for(int i =0;i<users;i++) {
            double lat = jsonPath.getDouble("address[" + i + "].geo.lat");
            double lng = jsonPath.getDouble("address[" + i + "].geo.lng");
            if ((lat <=5  && lat >= -40)) {
                if ((lng >= 5 && lng <= 100)) {

                    fancodeUsers.add(jsonPath.getInt("id[" + i + "]"));
                }
            }
        }
        return fancodeUsers;
    }

    protected Map<Integer,String> getCompletedTaskCount(Response res){
            Map<Integer,String> mp = new HashMap<>();
            int count0=0, count1=0;
        int userId;
        int oldUserId = 0;
        JsonPath jsonPath = new JsonPath(res.asString());
        int users = jsonPath.getInt("res.size()");
        for(int i = 0;i<users;i++){
            userId =  jsonPath.get("userId["+i+"]");

            if(oldUserId != userId) {
                count0=0;
                count1=0;
            }

            boolean completed = jsonPath.get("completed["+i+"]");
            if(!completed) count0++;
            else count1++;
            mp.put(userId, count0 +" "+ count1);
             oldUserId = userId;
        }
            return mp;
    }

}












