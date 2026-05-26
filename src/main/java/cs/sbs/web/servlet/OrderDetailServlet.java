package cs.sbs.web.servlet;

import cs.sbs.web.model.Order;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class OrderDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            out.println("Error: Invalid order ID.");
            return;
        }

        String orderIdStr = pathInfo.substring(1);
        int orderId;
        try {
            orderId = Integer.parseInt(orderIdStr);
        } catch (NumberFormatException e) {
            out.println("Error: Invalid order ID format.");
            return;
        }

        Order foundOrder = null;
        for (Order order : OrderCreateServlet.ORDER_LIST) {
            if (order.getOrderId() == orderId) {
                foundOrder = order;
                break;
            }
        }

        if (foundOrder == null) {
            out.println("Error: Order not found.");
            return;
        }

        out.println("Order Detail");
        out.println("Order ID: " + foundOrder.getOrderId());
        out.println("Customer: " + foundOrder.getCustomer());
        out.println("Food: " + foundOrder.getFood());
        out.println("Quantity: " + foundOrder.getQuantity());
    }
}