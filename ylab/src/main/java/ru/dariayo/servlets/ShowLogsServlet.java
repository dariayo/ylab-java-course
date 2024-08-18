package ru.dariayo.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.dariayo.log.AuditLog;
import ru.dariayo.log.AuditLogRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/api/showLogs")
public class ShowLogsServlet extends HttpServlet {
    private final AuditLogRepository auditLogRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ShowLogsServlet() {
        // Конструктор без параметров для соответствия спецификации сервлетов
        this.auditLogRepository = new AuditLogRepository(); // Замените это на реальную инициализацию
    }

    public ShowLogsServlet(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            String filterParam = req.getParameter("filter"); // Параметр фильтрации

            if (filterParam == null || filterParam.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                objectMapper.writeValue(resp.getWriter(), new ApiResponse("Filter parameter is required."));
                return;
            }

            // Фильтрация журналов
            List<AuditLog> filteredLogs = auditLogRepository.filterLogs(null, LocalDateTime.now(), null, filterParam);

            if (filteredLogs.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                objectMapper.writeValue(resp.getWriter(), new ApiResponse("No logs found."));
            } else {
                resp.setStatus(HttpServletResponse.SC_OK);
                objectMapper.writeValue(resp.getWriter(), filteredLogs);
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            objectMapper.writeValue(resp.getWriter(), new ApiResponse("An error occurred: " + e.getMessage()));
        }
    }

    // Класс для форматирования JSON-ответов
    private static class ApiResponse {
        private final String message;

        public ApiResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
