package com.example.foodApp.Service;

import com.example.foodApp.Request.OrderRequest;
import com.example.foodApp.model.*;
import com.example.foodApp.reponsitory.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImp implements OrderService{
    @Autowired
    private OrderRespository orderRespository;
    @Autowired
    private OrderItemRespository orderItemRespository;
    @Autowired
    private AddressResponsitory addressResponsitory;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private CartService cartService;
    @Override
    public Order createOrder(OrderRequest order, User user) throws Exception {
        Address shippAddress = order.getDeliveryAddress();
        // Kiểm tra và thêm địa chỉ giao hàng nếu chưa tồn tại
        Address addressExits=addressResponsitory.findAddressById(order.getDeliveryAddress().getId());
        if (addressExits==null) {
            Address savedAddress = addressResponsitory.save(shippAddress);
            user.getAddresses().add(savedAddress);
            userRepository.save(user); // Cập nhật lại user sau khi thêm địa chỉ mới
        }



        // Lấy thông tin nhà hàng từ id
        Restaurant restaurant = restaurantService.findRestaurantById(order.getRestaurantId());

        // Tạo mới đơn hàng
        Order createOrder = new Order();
        createOrder.setRestaurant(restaurant);
        createOrder.setCustomer(user);
        createOrder.setCreateAt(new Date());
        createOrder.setOrderStatus("PENDING");
        createOrder.setDeliveryAddress(shippAddress);

        // Lấy giỏ hàng của người dùng
        Cart cart = cartService.findCartByUserId(user.getId());
        List<Orderitem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cart.getItems()) {
            Orderitem orderItem = new Orderitem();
            orderItem.setFood(cartItem.getFood());
            orderItem.setIngredients(cartItem.getIngredients());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(cartItem.getTotalPrice());

            // Lưu từng OrderItem vào cơ sở dữ liệu
            Orderitem savedOrderItem = orderItemRespository.save(orderItem);
            orderItems.add(savedOrderItem);
        }

        // Tính tổng giá trị của giỏ hàng
        Long totalPrice = cartService.caculaterCartTotals(cart);
        Long totalDiscount = order.getTotal();

        // So sánh tổng giá từ giỏ hàng và từ đơn đặt hàng
        if (totalPrice.equals(totalDiscount)) {
            createOrder.setTotalPrice(totalPrice);
        } else {
            createOrder.setTotalPrice(totalDiscount);
        }

        // Gán danh sách OrderItems vào đơn hàng
        createOrder.setItems(orderItems);

        // Lưu đơn hàng và cập nhật danh sách đơn hàng của nhà hàng
        Order savedOrder = orderRespository.save(createOrder);
        restaurant.getOrders().add(savedOrder);

        return savedOrder;
    }

    @Override
    public Order updateOrder(Long orderId, String orderStatus) throws Exception {
        Order order= findOrderById(orderId);
        if(orderStatus.equals("OUT_FOR_DELIVREY")
                || orderStatus.equals("DELIVERY")
                || orderStatus.equals("COMPLETED")
                || orderStatus.equals("PENDING"))
        {
            order.setOrderStatus(orderStatus);
            return orderRespository.save(order);

        }
        throw new Exception("please select a volid order status");

    }

    @Override
    public void calcelOrder(Long orderId) throws Exception {
        Order order=findOrderById(orderId);
        orderRespository.deleteById(orderId);
    }

    @Override
    public List<Order> getUsersOrder(Long userId) {

        return orderRespository.findByCustomerId(userId);
    }

    @Override
    public List<Order> getRestaurantsOrder(Long restaurantId, String orderStatus) {
        List<Order> orders= orderRespository.findByRestaurantId(restaurantId);
        if(orderStatus!=null){
            orders=orders.stream().filter(order->order.getOrderStatus().equals(orderStatus)).collect(Collectors.toList());
        }
        return orders;
    }

    @Override
    public Order findOrderById(Long orderId) throws Exception {
        Order order= orderRespository.findById(orderId)
                .orElseThrow(()->new Exception("not found order"));
        return order;
    }
}
