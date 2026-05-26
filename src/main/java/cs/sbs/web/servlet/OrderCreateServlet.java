package cs.sbs.web.servlet;

import cs.sbs.web.model.Order;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderCreateServlet extends HttpServlet {
    public static final List<Order> ORDER_LIST = new ArrayList<>();
    private static final AtomicInteger ORDER_ID_GENERATOR = new AtomicInteger(1001);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = response.getWriter();
        request.setCharacterEncoding("UTF-8");

        String customer = request.getParameter("customer");
        String food = request.getParameter("food");
        String quantityStr = request.getParameter("quantity");

        if (customer == null || customer.trim().isEmpty() ||
                food == null || food.trim().isEmpty() ||
                quantityStr == null || quantityStr.trim().isEmpty()) {
            out.println("Error: All fields are required.");
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityStr.trim());
            if (quantity <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            out.println("Error: quantity must be a valid number");
            return;
        }

        int newOrderId = ORDER_ID_GENERATOR.getAndIncrement();
        Order newOrder = new Order(newOrderId, customer.trim(), food.trim(), quantity);
        ORDER_LIST.add(newOrder);

        out.println("Order Created: " + newOrderId);
    }
}