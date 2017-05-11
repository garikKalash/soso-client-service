package com.soso.service.authentication;

        import com.soso.service.BaseRestClient;
        import com.soso.service.JsonConverter;
        import org.springframework.stereotype.Repository;

/**
 * Created by Garik Kalashyan on 3/8/2017.
 */

public class AuthenticationTokenService extends BaseRestClient {

    public AuthenticationTokenService(Integer serviceId) {
        super(serviceId);
    }

    public boolean isValidToken(Integer selfId, Integer itemId, String token) {
        return JsonConverter.isValidTokenStatusFromJSONString(
                getRestTemplate().getForObject(getDestinationService().getUrl() + "authenticateService/isValidToken/" + selfId + "/" + itemId + "/" + token,
                        String.class)
        );
    }

    public String createToken(Integer serviceId, Integer clientId, String phoneNumber) {
        String result = getRestTemplate().getForObject(getDestinationService().getUrl() + "authenticateService/getToken/" + serviceId + "/" + clientId + "/" + phoneNumber, String.class);
        return JsonConverter.getCreatedTokenKeyFromJSONString(result);
    }

    public void removeToken(Integer selfId, Integer itemId, String token) {
        getRestTemplate().delete(getDestinationService().getUrl() + "authenticateService/deleteToken/" + selfId + "/" + itemId + "/" + token);
    }

}
