package slng.fnord;

import org.junit.Test;
import com.google.common.truth.Truth.assertThat;

public class ValidationTestCases {
    @Test
    public void emailValidator_register(){
        assertThat(RegisterActivity.validateEmail("username@domain.com")).isTrue();
        assertThat(RegisterActivity.validateEmail("user name@domain.com")).isFalse();
        assertThat(RegisterActivity.validateEmail("")).isFalse(); // empty case
        assertThat(RegisterActivity.validateEmail(null)).isFalse(); // null case (unlikely to actually occur)
    }

    @Test
    public void passwordValidator_register(){
        assertThat(RegisterActivity.validatePassword("p4$$w0rd")).isTrue();
        assertThat(RegisterActivity.validatePassword("s3cur3 p4$$w0rd")).isFalse();
        assertThat(RegisterActivity.validatePassword("")).isFalse(); // empty case
        assertThat(RegisterActivity.validatePassword(null)).isFalse(); // null case
    }

    @Test
    public void usernameValidator_register(){
        assertThat(RegisterActivity.validateUser("ngnius")).isTrue();
        assertThat(RegisterActivity.validateUser("ngnius ness")).isFalse();
        assertThat(RegisterActivity.validateUser("")).isFalse(); // empty case
        assertThat(RegisterActivity.validateUser(null)).isFalse(); // null case (unlikely to actually occur)
    }

}
