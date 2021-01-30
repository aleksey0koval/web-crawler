package web.crawler.servlet;

import web.crawler.model.ResultDto;
import web.crawler.model.SearchDto;
import web.crawler.service.SearchProcessor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Stack;

@WebServlet(name = "search", urlPatterns = "/search")
public class SearchServlet extends HttpServlet {
    private final SearchParamsBinder searchParamsBinder = new SearchParamsBinder();
    private final SearchProcessor searchProcessor = new SearchProcessor();

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Validate request params (validation)

        //Parse request params to search model (binding)
        SearchDto searchDto = searchParamsBinder.bind(req);

        //Call search service and pass search model, returns result model (processing)
        ResultDto resultDto = searchProcessor.search(searchDto);

        //Add result model to request scope and present result model on view (result.jsp)
        req.setAttribute("terms", searchDto.getTerms());
        req.setAttribute("result", resultDto);
        req.getRequestDispatcher("/result.jsp").forward(req, resp);
    }
}
