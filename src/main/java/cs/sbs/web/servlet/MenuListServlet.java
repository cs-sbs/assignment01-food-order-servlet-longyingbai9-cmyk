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

        String nameParam = request.getParameter("name");

        // 修改这里：空字符串也应该被当作有效搜索，但返回空结果
        if (nameParam == null || nameParam.trim().isEmpty()) {
            // 没有name参数：返回空结果
        } else {
            // 有name参数（包括空字符串）：进行搜索过滤
            String keyword = nameParam.trim().toLowerCase();
            List<MenuItem> filtered = new ArrayList<>();

            if (!keyword.isEmpty()) {
                for (MenuItem item : MENU) {
                    if (item.getName().toLowerCase().contains(keyword)) {
                        filtered.add(item);
                    }
                }
            }
          if (!filtered.isEmpty()){
              // 输出过滤结果
              for (int i = 0; i < filtered.size(); i++) {
                  MenuItem item = filtered.get(i);
                  out.printf("%d. %s - $%.0f%n", (i + 1), item.getName(), item.getPrice());
              }
          }else {
              out.println("No matching items found.");
          }





        }
    }
}