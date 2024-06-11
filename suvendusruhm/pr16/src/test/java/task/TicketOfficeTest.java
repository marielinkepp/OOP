package task;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class TicketOfficeTest {

  // TODO add your tests here

    @Test
    public void mockSize() {
        // java.util.List is an interface with more than 10 methods
        List<Object> list = mock(List.class);
        // when size() is called on the mock object, then it should return 0
        when(list.size()).thenReturn(0);
        assertEquals(0, list.size());
    }

    public static void throwsExceptionWhenNoSeatsAvailable(TicketDatabase db, TicketOffice office) {


        // prepare the mock (when+thenReturn)
        // call office.buyTicket
        // assert/verify conditions


        // when size() is called on the mock object, then it should return 0
        //when(list.size()).thenReturn(0);
       // assertEquals(0, list.size());

        String showString = "show1";
        //when(db.getFreeSeats(showString)).thenReturn(List.of());

        //System.out.println(db.getFreeSeats(showString));
        //System.out.println(this.mockSize());

       //assertThrows(IllegalStateException.class, () -> {office.buyTicket("show1");});

        // prepare the mock (when+thenReturn)
        // call office.buyTicket
        // assert/verify conditions
    }

    public static void returnsAvailableSeatOnPurchase(TicketDatabase db, TicketOffice office) {


        // prepare the mock (when+thenReturn)
        // call office.buyTicket
        // assert/verify conditions
    }

    public static void callsReserveSeatWithCorrectArguments(TicketDatabase db, TicketOffice office) {
        String show = "show1";
        assertEquals(db.getFreeSeats(show).get(0), office.buyTicket(show));
    }

    public static void main(String[] args) {

        TicketDatabase db = mock(TicketDatabase.class);
        TicketOffice office = new TicketOffice(db);

        throwsExceptionWhenNoSeatsAvailable(db, office);
        //returnsAvailableSeatOnPurchase(db, office);
       // callsReserveSeatWithCorrectArguments(db, office);
    }
}
