package cs.sbs.web.servlet;

import cs.sbs.web.model.MenuItem;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class MenuListServlet extends HttpServlet {
    private static final List<MenuItem> MENU = new ArrayList<>();

    static {
        MENU.add(new MenuItem("Fried Rice", 8));
        MENU.add(new MenuItem("Fried Noodles", 9));
        MENU.add(new MenuItem("Burger", 10));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("Menu List:");

        // 直接从请求URL判断是否包含name参数
        String queryString = request.getQueryString();
        boolean hasNameParam = queryString != null && queryString.contains("name");

        if (!hasNameParam) {
            // 情况1：没有name参数，返回全部菜单
            for (int i = 0; i < MENU.size(); i++) {
                MenuItem item = MENU.get(i);
                out.printf("%d. %s - $%.0f%n", (i + 1), item.getName(), item.getPrice());
            }
        } else {
            // 情况2：有name参数，进行过滤
            String nameParam = request.getParameter("name");
            String keyword = (nameParam == null ? "" : nameParam).trim().toLowerCase();

            List<MenuItem> filtered = new ArrayList<>();
            for (MenuItem item : MENU) {
                if (item.getName().toLowerCase().contains(keyword)) {
                    filtered.add(item);
                }
            }
            // 输出过滤后的结果
            for (int i = 0; i < filtered.size(); i++) {
                MenuItem item = filtered.get(i);
                out.printf("%d. %s - $%.0f%n", (i + 1), item.getName(), item.getPrice());
            }
        }
    }
}