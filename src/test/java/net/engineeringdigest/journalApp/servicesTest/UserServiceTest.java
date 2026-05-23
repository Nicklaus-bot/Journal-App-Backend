package net.engineeringdigest.journalApp.servicesTest;

import net.engineeringdigest.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Disabled
    @Test
    public void testAdd(){
        assertEquals(4 , 2 + 2);
    }

    @ParameterizedTest
    @ValueSource(strings ={
            "Sid",
            "Ram",
            "Sahej",
            "Anya"
    })
    public void findByUsernameTest(String name){
        assertNotNull(userRepository.findByUsername(name));
    }

    @ParameterizedTest
    @CsvSource({
            "5, 1 , 4",
            "3 , 1 , 1",
            "2 , 2 , 2",
            "10 , 5 , 5"
    })
    public void test(int exp , int a , int b){
        assertEquals(exp , a , b);
    }

}
