import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.dariayo.model.Person;
import ru.dariayo.repositories.PersonCollection;
import ru.dariayo.servlets.RegisterServlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RegisterServletTest {

    @Mock
    private PersonCollection personCollection;

    @InjectMocks
    private RegisterServlet registerServlet;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSuccessfulRegistration() throws Exception {
        // Prepare request and response mocks
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(outputStream);

        when(request.getParameter("username")).thenReturn("testUser");
        when(request.getParameter("password")).thenReturn("123");
        when(request.getParameter("role")).thenReturn("admin");
        when(request.getParameter("contacts")).thenReturn("test@example.com");
        when(response.getWriter()).thenReturn(writer);

        // Execute servlet
        registerServlet.doPost(request, response);

        // Verify response
        writer.flush();
        String responseContent = outputStream.toString();
        assertEquals("\"User registered successfully.\"", responseContent.trim());
        verify(personCollection).addPerson(any(Person.class));
    }

    @Test
    public void testMissingParameters() throws Exception {
        // Prepare request and response mocks
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(outputStream);

        when(request.getParameter("username")).thenReturn("testUser");
        when(request.getParameter("password")).thenReturn("");
        when(request.getParameter("role")).thenReturn("admin");
        when(request.getParameter("contacts")).thenReturn("test@example.com");
        when(response.getWriter()).thenReturn(writer);

        // Execute servlet
        registerServlet.doPost(request, response);

        // Verify response
        writer.flush();
        String responseContent = outputStream.toString();
        assertEquals("\"All fields are required.\"", responseContent.trim());
        verify(personCollection, never()).addPerson(any(Person.class));
    }

    @Test
    public void testInvalidPasswordForAdmin() throws Exception {
        // Prepare request and response mocks
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(outputStream);

        when(request.getParameter("username")).thenReturn("testUser");
        when(request.getParameter("password")).thenReturn("wrongPassword");
        when(request.getParameter("role")).thenReturn("admin");
        when(request.getParameter("contacts")).thenReturn("test@example.com");
        when(response.getWriter()).thenReturn(writer);

        // Execute servlet
        registerServlet.doPost(request, response);

        // Verify response
        writer.flush();
        String responseContent = outputStream.toString();
        assertEquals("\"Invalid password for the given role.\"", responseContent.trim());
        verify(personCollection, never()).addPerson(any(Person.class));
    }
}
