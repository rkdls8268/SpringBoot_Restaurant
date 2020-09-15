package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.ReservationService;
import kr.co.fastcampus.eatgo.domain.Reservation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension .class)
@WebMvcTest(ReservationController.class)
public class ReservationControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ReservationService reservationService;

    @Test
    public void create() throws Exception {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjE2LCJuYW1lIjoidGVzdGVyIn0.T45eNQRk5f6ymbaMZBiym-QgsdizureCm7-rliY6Y2s";

        Reservation mockReservation = Reservation.builder().id(10L).build();
        given(reservationService.addReservation(any(), any(), any(), any(), any(), anyInt()))
                .willReturn(mockReservation);

        mvc.perform(MockMvcRequestBuilders.post("/restaurants/1004/reservations")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"date\":\"2020-12-15\", \"time\":\"20:00\", " +
                        "\"partySize\":20}"))
                .andExpect(status().isCreated());
        Long userId = 16L;
        String name = "tester";
        String date = "2020-12-15";
        String time = "20:00";
        Integer partySize = 20;

        verify(reservationService).addReservation(eq(1004L), eq(userId), eq(name), eq(date), eq(time), eq(partySize));
    }
}