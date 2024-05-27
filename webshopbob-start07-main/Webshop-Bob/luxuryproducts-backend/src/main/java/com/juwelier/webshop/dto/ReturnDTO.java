package com.juwelier.webshop.dto;

import java.util.UUID;

public class ReturnDTO {

//    public long orderItemId;
    public long productId;

    public UUID orderId;
     public String reason;

     public String explanation;



    public ReturnDTO(long productId, UUID orderId, String reason, String explanation) {
        this.productId = productId;
        this.orderId = orderId;
        this.reason = reason;
        this.explanation = explanation;
    }

    public ReturnDTO() {
    }
}
