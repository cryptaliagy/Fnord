package slng.fnord;

import org.junit.Test;
import com.google.common.truth.Truth.assertThat;

public class AccountTest {
    @Test
    public void adminAccountTest(){
        // values should be verified by now, so only valid strings will be used
        Accounts accounts = new Accounts;
        accounts.addAdminEmail("glitt073@uottawa.ca");
        accounts.addAdminPassword("1337Potato");
        accounts.addAdminUsername("Graham");
    }

    @Test
    public void spAccountTest(){
        Accounts accounts = new Accounts;
        accounts.addSPEmail("glitt073@uottawa.ca");
        accounts.addSPPassword("1337Potato");
        accounts.addSPUsernames("Graham");
    }

    @Test
    public void homeOwnerAccountTest(){
        Accounts accounts = new Accounts;
        accounts.addHomeOwnerEmail("glitt073@uottawa.ca");
        accounts.addHomeOwnerPassword("1337Potato");
        accounts.addHomeOwnerUsernames("Graham");
    }
}
