package duke.nl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class RedirectServlet extends HttpServlet {

    private final ImageRepository repository;

    public RedirectServlet(ImageRepository repository) {
        this.repository = repository;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter pw = resp.getWriter();
        Image image = repository.findOne(Long.parseLong(req.getPathInfo().substring(1)));
        resp.sendRedirect(image.getUrl());
        pw.close();

    }


}
