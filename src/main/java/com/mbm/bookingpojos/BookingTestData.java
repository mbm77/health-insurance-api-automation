package com.mbm.bookingpojos;

import lombok.ToString;

@ToString
public class BookingTestData {
    private CreateBooking createBooking;
    private BasePojo basePojo;

    public BookingTestData(CreateBooking createBooking, BasePojo basePojo) {
        this.createBooking = createBooking;
        this.basePojo = basePojo;
    }

    public CreateBooking getCreateBooking() {
        return createBooking;
    }

    public BasePojo getBasePojo() {
        return basePojo;
    }
}

