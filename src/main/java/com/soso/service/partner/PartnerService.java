package com.soso.service.partner;

import com.soso.models.Partner;
import com.soso.service.BaseRestClient;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Garik Kalashyan on 4/23/2017.
 */
public class PartnerService extends BaseRestClient {

    public PartnerService(@NotNull Integer serviceId) {
        super(serviceId);
    }


}
