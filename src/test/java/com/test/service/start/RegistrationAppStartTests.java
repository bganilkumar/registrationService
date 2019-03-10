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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RegistrationAppStartTests {

	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders httpHeaders = new HttpHeaders();

	@MockBean
	private ExclusionService exclusionService;

	private UserRegistrationRequest userRegistrationRequest;

	private HttpEntity<UserRegistrationRequest> userRegistrationRequestHttpEntity;

	@Before
	public void before() {
		userRegistrationRequest = new UserRegistrationRequest();
		userRegistrationRequest.setDateOfBirth("1985-06-07");
		userRegistrationRequest.setPassword("pass1Word");
		userRegistrationRequest.setSsn("anilSSN");
		userRegistrationRequest.setUserName("bganilkumar4");
		userRegistrationRequestHttpEntity = new HttpEntity<>(userRegistrationRequest, httpHeaders);
	}

	@Test
	public void testSuccessfulRegistrationResponse() {
		when(exclusionService.validate(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
		ResponseEntity<UserRegistrationResponse> responseEntity = exchange(userRegistrationRequestHttpEntity);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		UserRegistrationResponse response = responseEntity.getBody();
		assertEquals("User:"+userRegistrationRequest.getUserName()+" has been successfully registered.", response.getMessage());
	}

	@Test
	public void testBlackListedRegistrationResponse() {
		when(exclusionService.validate(Mockito.anyString(), Mockito.anyString())).thenReturn(false);
		ResponseEntity<UserRegistrationResponse> responseEntity = exchange(userRegistrationRequestHttpEntity);
		assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
		UserRegistrationResponse response = responseEntity.getBody();
		assertEquals("User:" + userRegistrationRequest.getUserName() + " has been blacklisted. Can't proceed with the registration.", response.getMessage());
	}

	@Test
	public void testUserAlreadyRegisteredRegistrationResponse() {
        userRegistrationRequest.setUserName("bganilkumar5");
		when(exclusionService.validate(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
		ResponseEntity<UserRegistrationResponse> responseEntity = exchange(userRegistrationRequestHttpEntity);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		responseEntity = exchange(userRegistrationRequestHttpEntity);
		assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
		UserRegistrationResponse response = responseEntity.getBody();
		assertEquals("User:" + userRegistrationRequest.getUserName()+ " has been already registered.", response.getMessage());
	}

	@Test
	public void testInvalidUserInputRegistrationResponse() {
		userRegistrationRequest.setUserName("bganilkumar&&8");
		userRegistrationRequest.setPassword("Pas");
		userRegistrationRequest.setDateOfBirth("2018");
		when(exclusionService.validate(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
		ResponseEntity<UserRegistrationResponse> responseEntity = exchange(userRegistrationRequestHttpEntity);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		UserRegistrationResponse response = responseEntity.getBody();
		assertEquals(3, response.getErrors().size());
		assertEquals("User Registration failed. Please provide valid input.", response.getMessage());
		assertEquals(HttpStatus.BAD_REQUEST, response.getHttpStatus());
	}

	private ResponseEntity<UserRegistrationResponse> exchange(HttpEntity<UserRegistrationRequest> userRegistrationRequestHttpEntity) {
		return restTemplate.exchange("http://localhost:"+port+"/user/registration/register",
				HttpMethod.POST, userRegistrationRequestHttpEntity, UserRegistrationResponse.class);
	}


}
