package org.primefaces.extensions.integrationtests.datepicker;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.primefaces.extensions.integrationtests.BaseIT;

@DisplayName("DatePicker Basic Display and Usage")
@Tag("DatePicker Tests")
public class DatePicker001IT extends BaseIT {
    private static final String datePickerInputID = "$('[id$=datepicker1_input]')";

    @DisplayName("Test Input")
    public void performTest() {
        focus(datePickerInputID);
        setValue(datePickerInputID, "02/19/1978");
        blur(datePickerInputID);

        Assertions.assertTrue(doesExist("$(\"[id$=datepicker1_panel] .ui-datepicker-month:contains('February')\")"));
        Assertions.assertTrue(doesExist("$(\"[id$=datepicker1_panel] .ui-datepicker-year:contains('1978')\")"));

        clickOnItem("$('[id$=button]')");

        String valueAfterClick = getValue(datePickerInputID);
        Assertions.assertEquals("02/19/1978", valueAfterClick);
    }

    public String getLocation() {
        return "datepicker/datePicker001.xhtml";
    }


}
