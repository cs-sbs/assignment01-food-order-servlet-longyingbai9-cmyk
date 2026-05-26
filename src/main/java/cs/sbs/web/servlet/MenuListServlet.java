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
import java.util.stream.Collectors;

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

        String nameParam = request.getParameter("name");
        List<MenuItem> filteredMenu;

        if (nameParam == null) {
            // 情况1：没有name参数 → 返回全部菜单
            filteredMenu = MENU;
        } else if (nameParam.trim().isEmpty()) {
            // 情况2：name参数是空字符串 → 返回空列表
            filteredMenu = new ArrayList<>();
        } else {
            // 情况3：有有效关键词 → 按名称过滤
            String keyword = nameParam.toLowerCase();
            filteredMenu = MENU.stream()
                    .filter(item -> item.getName().toLowerCase().contains(keyword))
                    .collect(Collectors.toList());
        }

        out.println("Menu List:");
        for (int i = 0; i < filteredMenu.size(); i++) {
            MenuItem item = filteredMenu.get(i);
            out.printf("%d. %s - $%.0f%n", (i + 1), item.getName(), item.getPrice());
        }
    }
}