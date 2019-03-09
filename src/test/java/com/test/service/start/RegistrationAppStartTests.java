package com.test.service.start;

import com.test.service.exclusion.ExclusionService;
import com.test.service.user.request.UserRegistrationRequest;
import com.test.service.user.response.UserRegistrationResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RegistrationAppStartTests {

	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders httpHeaders = new HttpHeaders();

	@MockBean
	private ExclusionService exclusionService;

	private UserRegistrationRequest userRegistrationRequest;

	@Before
	public void before() {
		userRegistrationRequest = new UserRegistrationRequest();
		userRegistrationRequest.setDateOfBirth("1985-06-07");
		userRegistrationRequest.setPassword("pass1Word");
		userRegistrationRequest.setSsn("anilSSN");
		userRegistrationRequest.setUserName("bganilkumar4");
	}

	@Test
	public void testSuccessfulRegistration() {
		HttpEntity<UserRegistrationRequest> userRegistrationRequestHttpEntity = new HttpEntity<>(userRegistrationRequest, httpHeaders);
		Mockito.when(exclusionService.validate(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
		ResponseEntity<UserRegistrationResponse> responseEntity = restTemplate.exchange("http://localhost:"+port+"/user/registration/register",
				HttpMethod.POST, userRegistrationRequestHttpEntity, UserRegistrationResponse.class);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		UserRegistrationResponse response = responseEntity.getBody();
		assertEquals("User:bganilkumar4 has been successfully registered.", response.getMessage());
	}

	@Test
	public void contextLoads() {
	}

}
