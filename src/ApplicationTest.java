import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationTest {
    Application app=new Application();

    public ApplicationTest(){
        app.loadRecords();
    }

    @Test
    void executeSQL01() {
        assertEquals(1000000 ,app.executeSQL01());
    }

    @Test
    void executeSQL02() {
        assertEquals(11115 ,app.executeSQL02());
    }

    @Test
    void executeSQL03() {
        assertEquals(2686 ,app.executeSQL03());
    }

    @Test
    void executeSQL04() {
        assertEquals(1343 ,app.executeSQL04());
    }

    @Test
    void executeSQL05() {
        assertEquals(434650,app.executeSQL05());
    }

    @Test
    void executeSQL06() {
        assertEquals(2726  ,app.executeSQL06());
    }

    @Test
    void executeSQL07() {
        //127555
        //309059
        //263230
        List<Long> expectedList = new ArrayList<>(Arrays.asList(127555L,309059L,263230L));
        assertEquals(expectedList ,app.executeSQL07());
    }

    @Test
    void executeSQL08() {
        //468257
        //154270
        List<Long> expectedList = new ArrayList<>(Arrays.asList(468257L,154270L));
        assertEquals(expectedList ,app.executeSQL08());
    }

    @Test
    void executeSQL09() {
        //false 919981
        //true 80019
        Map<Boolean, Long> expectedMap = Map.of(
                false, 919981L,
                true, 80019L
        );
        assertEquals(expectedMap ,app.executeSQL09());
    }

    @Test
    void executeSQL10() {
        /*S 26551
        M 26337
        L 26309*/
        Map<String, Long> expectedMap = Map.of(
                "S", 26551L,
                "M", 26337L,
                "L", 26309L
        );
        assertEquals(expectedMap ,app.executeSQL10());
    }

    @Test
    void executeSQL11() {
        //C 98830
        //A 99281
        Map<String, Long> expectedMap = Map.of(
                "C", 98830L,
                "A", 99281L
        );
        assertEquals(expectedMap ,app.executeSQL11());
    }

    @Test
    void executeSQL12() {
        /*
        I 3
        J 3
        G 3
        H 1
        D 3
        B 1
        C 2
        F 2
        A 1
         */
        Map<String, Long> expectedMap = Map.of(
                "I", 3L,
                "J", 3L,
                "G", 3L,
                "H", 1L,
                "D", 3L,
                "B", 1L,
                "C", 2L,
                "F", 2L,
                "A", 1L
        );
        assertEquals(expectedMap ,app.executeSQL12());
    }

    @Test
    void executeSQL13() {
        //false 56055016
        //true 558159
        Map<Boolean, Long> expectedMap = Map.of(
                false, 56055016L,
                true, 558159L
        );
        assertEquals(expectedMap ,app.executeSQL13());
    }

    @Test
    void executeSQL14() {
        //D 3095
        //C 3185
        Map<String, Long> expectedMap = Map.of(
                "D", 3095L,
                "C", 3185L
        );
        assertEquals(expectedMap ,app.executeSQL14());
    }
}