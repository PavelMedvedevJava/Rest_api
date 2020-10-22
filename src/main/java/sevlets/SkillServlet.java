package sevlets;

import com.fasterxml.jackson.databind.ObjectMapper;

import company.model.Skill;
import company.repo.hibernate.SkillRepositoryImplHibernate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "SkillServlet", urlPatterns = {"/api/v1/Skills"})
public class SkillServlet extends HttpServlet {
    private SkillRepositoryImplHibernate skillRepositoryImplHibernate = new SkillRepositoryImplHibernate();
    private Skill skill;
    private List<Skill> skillList;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String skillJson = request.getParameter("data");

        skill = objectMapper.readValue(skillJson, Skill.class);
        skillRepositoryImplHibernate.create(skill);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        skillList = (List<Skill>) skillRepositoryImplHibernate.getAll();
        if (request.getParameter("data")==null) {
            ObjectMapper objectMapper = new ObjectMapper();
            String skillsJson = objectMapper.writeValueAsString(skillList);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(skillsJson);

        } else {
            long id = Long.parseLong(request.getParameter("param"));
            skill = skillRepositoryImplHibernate.read(id).get();
            ObjectMapper objectMapper = new ObjectMapper();
            String developerJson = objectMapper.writeValueAsString(skillList);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(developerJson);
        }

    }
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        skillRepositoryImplHibernate.delete(id);

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String stringGson=req.getParameter("data");

        skill = objectMapper.readValue(stringGson, Skill.class);
        skillRepositoryImplHibernate.create(skill);
    }

}