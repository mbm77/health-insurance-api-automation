package com.mbm.endpointpojos;

import lombok.Data;

@Data // Lombok: generates getters, setters, toString, equals, hashCode
public class Config {
    private String baseUrl;
    private Endpoints endpoints;

    @Data
    public static class Endpoints {
        private String getBookingIds;
        private String createBooking;
        private String getBooking;
        private String getAccessToken;
        private String updateBooking;
        private String partialUpdateBooking;
        private String deleteBooking;
    }
}

