package com.mbm.poijiutils;

import java.util.List;
import java.util.Map;

import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelUnknownCells;

import lombok.Data;
import lombok.ToString;
@Data
@ToString
public class BookingPoiji {

	@ExcelCellName("First Name")
	private String firstname;

	@ExcelCellName("Last Name")
	private String lastname;

	@ExcelCellName("Total Price")
	private int totalprice;

	@ExcelCellName("Deposit Paid")
	private boolean depositpaid;

	@ExcelCellName("Check-in Date")
	private String checkin; // Store as String, format later

	@ExcelCellName("Check-out Date")
	private String checkout;

	@ExcelCellName("Additional Needs")
	private List<String> additionalneeds;
	
	@ExcelUnknownCells
	private Map<String, String> extracells;
	
	
	/*

	// Getters and Setters (Required for Poiji)
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public int getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(int totalprice) {
		this.totalprice = totalprice;
	}

	public boolean isDepositpaid() {
		return depositpaid;
	}

	public void setDepositpaid(boolean depositpaid) {
		this.depositpaid = depositpaid;
	}

	public String getCheckin() {
		return checkin;
	}

	public void setCheckin(String checkin) {
		this.checkin = checkin;
	}

	public String getCheckout() {
		return checkout;
	}

	public void setCheckout(String checkout) {
		this.checkout = checkout;
	}

	public String getAdditionalneeds() {
		return additionalneeds;
	}

	public void setAdditionalneeds(String additionalneeds) {
		this.additionalneeds = additionalneeds;
	}
	
	*/
/*
	@Override
	public String toString() {
		return "Booking{" + "firstname='" + firstname + '\'' + ", lastname='" + lastname + '\'' + ", totalprice="
				+ totalprice + ", depositpaid=" + depositpaid + ", checkin='" + checkin + '\'' + ", checkout='"
				+ checkout + '\'' + ", additionalneeds='" + additionalneeds + '\'' + '}';
	} */
}
