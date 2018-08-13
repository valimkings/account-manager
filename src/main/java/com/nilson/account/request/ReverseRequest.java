package com.nilson.account.request;

import javax.validation.constraints.NotNull;

public class ReverseRequest {

    @NotNull
    private Long transferId;

    public Long getTransferId() {
        return transferId;
    }

}
