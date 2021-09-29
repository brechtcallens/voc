import org.junit.Test;
import org.python.types.Bool;
import org.python.types.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.python.types.Int.getInt;

public class ListProfilingTest {

    @Test
    public void workloadTest(){
        List list1 = new List();
        List list2 = new List();


        int size = 170000;
        for (int i = 0; i < size; i++) {
            list1.append(getInt(i));
        }

        for (int i = 0; i < size; i++) {
            list2.insert(getInt(0), getInt(i));
        }

        list2.reverse();

        assertEquals(Bool.TRUE, list1.__eq__(list2));

        list1.reverse();
        list1.sort(null, null);

        assertEquals(Bool.TRUE, list1.__eq__(list2));


        List list3 = (List) list1.__add__(list2);
        list3.sort(null, null);

        assertEquals(getInt(2), list3.count(getInt((size-1))));
        assertEquals(Bool.TRUE, list3.__contains__(getInt(0)));
        assertEquals(Bool.TRUE, list3.__contains__(getInt(size-1)));

        for (int i = 0; i < size; i++){
            list1.pop(getInt(0));
        }

        for (int i = size-1; i >= 0; i--){
            list2.pop(getInt(i));
        }
    }
}
