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

}
