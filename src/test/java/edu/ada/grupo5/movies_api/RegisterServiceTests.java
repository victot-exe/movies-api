
package edu.ada.grupo5.movies_api;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ada.grupo5.movies_api.dto.RegisterDTO;
import edu.ada.grupo5.movies_api.dto.ResponseDTO;
import edu.ada.grupo5.movies_api.model.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class RegisterServiceTests {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	private RegisterDTO registerDTO = new RegisterDTO("victor.fagundes586@gmail.com", "flkjdas1235", UserRole.ADMIN);

	@Test
	public void register_should_return_200_when_register() throws Exception {
		ResponseDTO responseDTO = ResponseDTO.builder().build();
		mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(registerDTO)))
						.andExpect(status().isOk());
	}
}