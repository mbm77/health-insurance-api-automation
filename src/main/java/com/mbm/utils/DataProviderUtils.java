package com.mbm.utils;

import static com.mbm.utils.HandlingNullValuesInBuilderPattern.handleNoData;
import static com.mbm.utils.HandlingNullValuesInBuilderPattern.handleNoDataBoolean;
import static com.mbm.utils.HandlingNullValuesInBuilderPattern.handleNoDataInteger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.testng.annotations.DataProvider;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbm.constants.FrameworkConstants;
import com.mbm.dto.booking.Booking;
import com.mbm.dto.booking.BookingDates;
import com.mbm.dto.booking.CreateBooking;
import com.mbm.poijiutils.BookingExcel;
import com.poiji.bind.Poiji;;

public class DataProviderUtils {
	@DataProvider(name = "BookingData", parallel = true)
	public Iterator<Booking> getBookingData() {
		String filePath = FrameworkConstants.getExcelPath();
		List<Map<String, String>> excelData = ExcelUtils.getDataFromExcel(filePath, "Sheet3");

		List<Booking> bookingData = new ArrayList<>();
		for (Map<String, String> data : excelData) {

			if (!Boolean.parseBoolean(data.get("enabled"))) {
				continue;
			}
			Booking booking = Booking.builder().firstname(data.get("firstname")).lastname(data.get("lastname"))
					.totalprice(Integer.parseInt(data.get("totalprice")))
					.depositpaid(Boolean.parseBoolean(data.get("depositpaid").toLowerCase()))
					.bookingdates(
							BookingDates.builder().checkin(data.get("checkin")).checkout(data.get("checkout")).build())
					.additionalneeds(data.get("additionalneeds")).build();
			bookingData.add(booking);
		}
		return bookingData.iterator();
	}

	@DataProvider(name = "loginData")
	public Object[] getLoginData() {
		List<Map<String, String>> loginData = ExcelUtils.getDataFromExcel(FrameworkConstants.getExcelPath(),
				FrameworkConstants.getLoginDataSheet());
		return loginData.toArray();

	}

	@DataProvider(name = "customerProfileData")
	public Object[] getCustomerData() {
		List<Map<String, String>> customerData = ExcelUtils.getDataFromExcel(FrameworkConstants.getExcelPath(),
				FrameworkConstants.getCustomerProfileData());
		return customerData.toArray();
	}

	@DataProvider(name = "BookingDataByPoiji", parallel = false)
	public static Iterator<Booking> getBookingDataUsingPoiji() {
		// PoijiOptions option =
		// PoijiOptions.PoijiOptionsBuilder.settings().addListDelimiter(";").build();
		File file = new File(System.getProperty("user.dir") + "/src/test/resources/test-data/BookingData.xlsx");

		// Convert Excel data to List of Booking objects
		List<BookingExcel> bookingExcelList = Poiji.fromExcel(file, BookingExcel.class);
		// System.out.println(bookingExcelList.size());System.exit(0);//4

		// Use Streams to print data by calling toString() method on each BookingExcel
		// object
		// bookingExcelList.stream().forEach(booking ->
		// System.out.println(booking.toString()));

		// Convert BookingExcel to Booking POJOs and return the list
		List<Booking> bookingList = bookingExcelList.stream().filter(BookingExcel -> BookingExcel.getEnabled() == true)
				.map(BookingExcel::toBooking).collect(Collectors.toList());
		// System.out.println("Size "+bookingList.size()+'
		// '+bookingList);System.exit(0);
		return bookingList.iterator();
	}

	@DataProvider(name = "BookingDataScenarioExcel")
	public Iterator<CreateBooking> getBookingDataFromExcel() {
		String filePath = System.getProperty("user.dir")
				+ "/src/test/resources/test-data/CreateBookingDataScenarios.xlsx";
		List<Map<String, String>> excelData = ExcelUtils.getDataFromExcel(filePath, "Sheet2");
		// List<CreateBooking> bookingList =
		// BookingConverter.convertToCreateBookingList(excelData);
		List<CreateBooking> testDataList = new ArrayList<>();
		CreateBooking createBooking = null;
		excelData = excelData.stream().filter(mapData -> mapData.get("Enabled").equalsIgnoreCase("yes"))
				.collect(Collectors.toList());
		for (Map<String, String> data : excelData) {

			createBooking = getCustomizedBookingData1(data);

			/*
			 * BasePojo basePojo = new BasePojo(data.get("ScenarioID"),
			 * data.get("ScenarioDesc"), Integer.parseInt(data.get("ExpectedStatusCode")),
			 * data.get("ExpectedErrorMessage"));
			 * 
			 * testData = new BookingTestData(createBooking, basePojo);
			 * //System.out.println("✅ Debug (Loop): " + testData); // Debug Line
			 * 
			 */
			testDataList.add(createBooking);
		}

		return testDataList.iterator();

	}

	private CreateBooking getCustomizedBookingData(Map<String, String> data) {
		CreateBooking createBooking = CreateBooking.builder().firstname(handleNoData(data.get("firstname")))
				.lastname(handleNoData(data.get("lastname"))).totalprice(handleNoDataInteger(data.get("totalprice")))
				.depositpaid(handleNoDataBoolean(data.get("depositpaid")))
				.bookingdates((handleNoData(data.get("checkin")) != null && handleNoData(data.get("checkout")) != null)
						? new BookingDates(handleNoData(data.get("checkin")), handleNoData(data.get("checkout")))
						: null)
				.additionalneeds(handleNoData(data.get("additionalneeds"))).build();

		return createBooking;
	}

	private CreateBooking getCustomizedBookingData1(Map<String, String> data) {
		CreateBooking createBooking = new CreateBooking();
		createBooking.setScenarioId(data.get("ScenarioID"));
		createBooking.setScenarioDesc(data.get("ScenarioDesc"));
		createBooking.setExpectedStatusCode(Integer.parseInt(data.get("ExpectedStatusCode")));
		createBooking.setExpectedErrorMessage(data.get("ExpectedErrorMessage"));

		if (!data.get("ExpectedErrorMessage").equals("NO_DATA"))
			createBooking.setExpectedErrorMessage(data.get("ExpectedErrorMessage"));
		if (!data.get("firstname").equalsIgnoreCase("NO_DATA"))
			createBooking.setFirstname((data.get("firstname")));
		if (!data.get("lastname").equalsIgnoreCase("NO_DATA"))
			createBooking.setLastname((data.get("lastname")));
		if (!data.get("totalprice").equalsIgnoreCase("NO_DATA"))
			createBooking.setTotalprice(Integer.parseInt(data.get("totalprice")));
		if (!data.get("depositpaid").equalsIgnoreCase("NO_DATA"))
			createBooking.setDepositpaid(Boolean.parseBoolean(data.get("depositpaid")));
		if (!data.get("checkin").equalsIgnoreCase("NO_DATA") && !data.get("checkout").equalsIgnoreCase("NO_DATA"))
			createBooking.setBookingdates(new BookingDates(data.get("checkin"), data.get("checkout")));
		if (!data.get("additionalneeds").equalsIgnoreCase("NO_DATA"))
			createBooking.setAdditionalneeds(data.get("additionalneeds"));

		return createBooking;

	}

	@DataProvider(name = "bookingDataFromJsonFile")
	public Object[][] bookingDataFromJsonFile() {
		String filePath = FrameworkConstants.getJsonBookingData();
		File file = new File(filePath);

		try {
			if (!file.exists()) {
				throw new FileNotFoundException("Test data file not found at: " + file.getAbsolutePath());
			}

			String jsonData = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
			ObjectMapper mapper = new ObjectMapper();
			List<Map<String, Object>> dataList = mapper.readValue(jsonData,
					new TypeReference<List<Map<String, Object>>>() {
					});

			Object[][] data = new Object[dataList.size()][1];
			for (int i = 0; i < dataList.size(); i++) {
				data[i][0] = dataList.get(i);
			}
			return data;

		} catch (IOException e) {
			// Log with your reporting tool to make it visible in the report
			System.err.println("❌ Error reading booking data file: " + e.getMessage());
			e.printStackTrace();

			// Fail the test early by returning empty data or throwing a RuntimeException
			throw new RuntimeException("Failed to load booking test data from: " + filePath, e);
		}
	}

}
