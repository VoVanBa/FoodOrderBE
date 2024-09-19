package com.example.foodApp.Service;

import com.example.foodApp.Request.AddCartItemRequest;
import com.example.foodApp.model.Cart;
import com.example.foodApp.model.CartItem;
import com.example.foodApp.model.Food;
import com.example.foodApp.model.User;
import com.example.foodApp.reponsitory.CartItemRepository;
import com.example.foodApp.reponsitory.CartRepository;
import com.example.foodApp.reponsitory.FoodResponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class CartServiceImp implements CartService{
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private FoodService foodService;
    @Autowired
    private CouponService couponService;
    @Override
    public CartItem addItemToCart(AddCartItemRequest req, String jwt) throws Exception {
        User user =userService.findUserByJwtToken(jwt);
        Food food= foodService.findFoodById(req.getFoodId());
        Cart cart= cartRepository.findByCustomerId(user.getId());

        for (CartItem cartItem :cart.getItems()){
            if(cartItem.getFood().equals(food)){
                int newQuantity=cartItem.getQuantity()+ req.getQuantity();
                return updateCartItemQuantity(cartItem.getId(),newQuantity);
            }
        }

        CartItem newCartItem = new CartItem();
        newCartItem.setFood(food);
        newCartItem.setQuantity(req.getQuantity());
        newCartItem.setCart(cart);
        newCartItem.setIngredients(req.getIngredient());
        newCartItem.setTotalPrice(req.getQuantity()*food.getPrice());

        CartItem saveCartItem=cartItemRepository.save(newCartItem);
        cart.getItems().add(saveCartItem);
        return saveCartItem;
    }

    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {
        Optional<CartItem> cartItemExits=cartItemRepository.findById(cartItemId);
        if(cartItemExits.isEmpty()){
            throw new Exception("cart item not found");
        }
        CartItem item=cartItemExits.get();
        item.setQuantity(quantity);
        item.setTotalPrice(item.getFood().getPrice()*quantity);
        return cartItemRepository.save(item);
    }

    @Override
    public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception {
        User user =userService.findUserByJwtToken(jwt);
        Cart cart= cartRepository.findByCustomerId(user.getId());

        Optional<CartItem> cartItemExits=cartItemRepository.findById(cartItemId);
        if(cartItemExits.isEmpty()){
            throw new Exception("cart item not found");
        }
        CartItem item=cartItemExits.get();
        cart.getItems().remove(item);
        cartRepository.save(cart);
        return cart;
    }

    @Override
    public Long caculaterCartTotals(Cart cart) {
        Long total=0L;
        for (CartItem cartItem:cart.getItems()) {
            total+=cartItem.getFood().getPrice()*cartItem.getQuantity();
        }

        return total;
    }

    @Override
    public Cart findCartById(Long id) throws Exception {
        Cart existCart=cartRepository.findById(id)
                .orElseThrow(()->new Exception("cart not found"));
        return existCart;
    }

    @Override
    public Cart findCartByUserId(Long userId) throws Exception {
        //User user= userService.findUserByJwtToken(jwt);
        Cart cart =cartRepository.findByCustomerId(userId);
        cart.setTotal(caculaterCartTotals(cart));
        return cart;
    }

    @Override
    public Cart cleanCart(Long userId) throws Exception {
      //  User user= userService.findUserByJwtToken(jwt);
        Cart cart= findCartByUserId(userId);
        cart.getItems().clear();
        return cartRepository.save(cart);
    }
}
