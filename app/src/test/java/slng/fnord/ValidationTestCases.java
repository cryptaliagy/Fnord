package slng.fnord;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class ValidationTestCases {
    @Test
    public void emailValidatorTest(){
        assertThat(Common.validateEmail("username@domain.com")).isTrue();
        assertThat(Common.validateEmail("user name@domain.com")).isFalse();
        assertThat(Common.validateEmail("")).isFalse(); // empty case
        //assertThat(Common.validateEmail(null)).isFalse(); // null case (unlikely to actually occur)
    }

    @Test
    public void passwordValidatorTest(){
        assertThat(Common.validatePassword("p4$$w0rd")).isTrue();
        assertThat(Common.validatePassword("s3cur3 p4$$w0rd")).isFalse();
        assertThat(Common.validatePassword("")).isFalse(); // empty case
        //assertThat(Common.validatePassword(null)).isFalse(); // null case
    }

    @Test
    public void usernameValidatorTest(){
        assertThat(Common.validateUser("ngnius")).isTrue();
        assertThat(Common.validateUser("ngnius ness")).isFalse();
        assertThat(Common.validateUser("")).isFalse(); // empty case
        //assertThat(Common.validateUser(null)).isFalse(); // null case (unlikely to actually occur)
    }

    @Test
    public void serviceValidatorTest(){
        assertThat(Common.validateService("Lawn cutting")).isTrue();
        assertThat(Common.validateService(" Putting spaces at the start like a jerk")).isFalse();
        assertThat(Common.validateService("")).isFalse(); // empty case
        //assertThat(Common.validateService(null)).isFalse(); // null case (unlikely to actually occur)

    }

    @Test
    public void priceValidatorTest(){
        assertThat(Common.validatePrice("12.34")).isTrue();
        assertThat(Common.validatePrice("98.999")).isFalse();
        assertThat(Common.validatePrice("Fourty two dollars and thirty cents")).isFalse();
        assertThat(Common.validatePrice("")).isFalse(); // empty case
        //assertThat(Common.validatePrice(null)).isFalse(); // null case (unlikely to actually occur)
    }

    @Test
    public void phoneValidatorTest(){
        assertThat(Common.validatePhoneNumber("6132225030")).isTrue();
        assertThat(Common.validatePhoneNumber("613 111-1111")).isTrue();
        assertThat(Common.validatePhoneNumber("555-1234")).isTrue();
        assertThat(Common.validatePhoneNumber("lol wut")).isFalse();
        assertThat(Common.validatePhoneNumber("613 555 42423")).isFalse();
        assertThat(Common.validatePhoneNumber("0118 999 881 999 119 7253")).isFalse(); // https://www.youtube.com/watch?v=ab8GtuPdrUQ
        assertThat(Common.validatePhoneNumber("")).isFalse(); // empty case
        //assertThat(Common.validatePhoneNumber(null)).isFalse(); // null case (unlikely to actually occur)
    }

    @Test
    public void timeValidatorTest(){
        assertThat(Common.validateTime("8h00")).isTrue();
        assertThat(Common.validateTime("12:00")).isTrue();
        assertThat(Common.validateTime("222:2222")).isFalse();
        assertThat(Common.validateTime("")).isFalse(); // empty case
    }

    @Test
    public void companyValidatorTest(){
        assertThat(Common.validateCompany("Tesla Motors")).isTrue();
        assertThat(Common.validateCompany("Cool Company Club")).isTrue();
        assertThat(Common.validateCompany(" InGenius")).isFalse();
        assertThat(Common.validateCompany("")).isFalse(); // empty case
        assertThat(Common.validateCompany(" ")).isFalse(); // space case
    }

    @Test
    public void addressValidatorTest(){
        assertThat(Common.validateAddress("123 University Drive")).isTrue();
        assertThat(Common.validateAddress("1 Victory Road")).isTrue();
        assertThat(Common.validateAddress("123NoSpaceStreet")).isFalse();
        assertThat(Common.validateAddress("This is not an address")).isFalse();
        assertThat(Common.validateAddress("")).isFalse(); // empty case
        assertThat(Common.validateAddress(" ")).isFalse(); // space case
    }

}
