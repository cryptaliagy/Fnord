package slng.fnord;

import org.junit.Test;
import static com.google.common.truth.Truth.assertThat;

public class AccountTest {
    @Test
    public void adminAccountTest(){
        // values should be verified by now, so only valid strings will be used
        Accounts accounts = new Accounts();
        accounts.makeAccount("glitt073@uottawa.ca", "Graham", "1337Potato", UserTypes.ADMIN);
        User user = accounts.getUser("glitt073@uottawa.ca");
        assertThat(user!=null).isTrue();
        assertThat(user.getType()==UserTypes.ADMIN).isTrue();
    }

    @Test
    public void spAccountTest(){
        // values should be verified by now, so only valid strings will be used
        Accounts accounts = new Accounts();
        accounts.makeAccount("glitt073@uottawa.ca", "Graham", "1337Potato", UserTypes.SERVICEPROVIDER);
        User user = accounts.getUser("glitt073@uottawa.ca");
        assertThat(user!=null).isTrue();
        assertThat(user.getType()==UserTypes.SERVICEPROVIDER).isTrue();
    }

    @Test
    public void homeOwnerAccountTest(){
        // values should be verified by now, so only valid strings will be used
        Accounts accounts = new Accounts();
        accounts.makeAccount("glitt073@uottawa.ca", "Graham", "1337Potato", UserTypes.HOMEOWNER);
        User user = accounts.getUser("glitt073@uottawa.ca");
        assertThat(user!=null).isTrue();
        assertThat(user.getType()==UserTypes.HOMEOWNER).isTrue();
    }
}
