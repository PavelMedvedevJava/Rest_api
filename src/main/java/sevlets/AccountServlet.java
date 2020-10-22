package sevlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import company.model.Account;
import company.model.Skill;
import company.repo.hibernate.AccountRepositoryImplHibernate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AccountServlet", urlPatterns = {"/api/v1/accounts"})
public class AccountServlet extends HttpServlet {
    private Account account;
    private List<Account> accountList;
    private AccountRepositoryImplHibernate accountRepositoryImplHibernate=new AccountRepositoryImplHibernate();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String accountJson = request.getParameter("data");

        account = objectMapper.readValue(accountJson, Account.class);
        accountRepositoryImplHibernate.create(account);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        accountList = (List<Account>) accountRepositoryImplHibernate.getAll();
        if (request.getParameter("data")==null) {
            ObjectMapper objectMapper = new ObjectMapper();
            String accounJson = objectMapper.writeValueAsString(accountList);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(accounJson);

        } else {
            long id = Long.parseLong(request.getParameter("param"));
            account = accountRepositoryImplHibernate.read(id).get();
            ObjectMapper objectMapper = new ObjectMapper();
            String accountJson = objectMapper.writeValueAsString(accountList);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(accountJson);
        }

    }
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        accountRepositoryImplHibernate.delete(id);

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String stringGson=req.getParameter("data");

        account = objectMapper.readValue(stringGson, Account.class);
        accountRepositoryImplHibernate.create(account);
    }
}