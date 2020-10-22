package sevlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import company.model.Developer;
import company.repo.hibernate.DeveloperRepositoryImplHibernate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "Developers",urlPatterns = {"/api/v1/developers"})
public class DeveloperServlet extends HttpServlet {
    private Developer developer;
    private List<Developer> developerList;

    DeveloperRepositoryImplHibernate developerRepositoryImplHibernate = new DeveloperRepositoryImplHibernate();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String stringGson=request.getParameter("data");

        developer = objectMapper.readValue(stringGson, Developer.class);
        developerRepositoryImplHibernate.update(developer);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        developerList= (List<Developer>) developerRepositoryImplHibernate.getAll();
        if (request.getParameter("data")==null) {
            ObjectMapper objectMapper = new ObjectMapper();
            String developersJson = objectMapper.writeValueAsString(developerList);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(developersJson);

        } else {
            long id = Long.parseLong(request.getParameter("param"));
            developer = developerRepositoryImplHibernate.read(id).get();
            ObjectMapper objectMapper = new ObjectMapper();
            String developerJson = objectMapper.writeValueAsString(developerList);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(developerJson);
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        developerRepositoryImplHibernate.delete(id);

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String stringGson=req.getParameter("data");

        developer = objectMapper.readValue(stringGson, Developer.class);
        developerRepositoryImplHibernate.create(developer);
    }
}