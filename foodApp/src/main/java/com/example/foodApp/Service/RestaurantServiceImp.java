package com.example.foodApp.Service;

import com.example.foodApp.Request.CreateRestaurantResquest;
import com.example.foodApp.dto.RestaurantDto;
import com.example.foodApp.model.Address;
import com.example.foodApp.model.Restaurant;
import com.example.foodApp.model.User;
import com.example.foodApp.reponsitory.AddressResponsitory;
import com.example.foodApp.reponsitory.RestaurantReponsitory;
import com.example.foodApp.reponsitory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImp implements RestaurantService {
    @Autowired
    private RestaurantReponsitory restaurantReponsitory;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressResponsitory addressResponsitory;

    @Override
    public Restaurant createRestauranṭ(CreateRestaurantResquest req, User user) {
        Address address = addressResponsitory.save(req.getAddress());
        Restaurant restaurant = new Restaurant();
        restaurant.setAddress(address);
        restaurant.setName(req.getName());
        restaurant.setDescription(req.getDescription());
        restaurant.setCuisineType(req.getCuisineType());
        restaurant.setImages(req.getImages());
        restaurant.setOpeningHours(req.getOpeningHours());
        restaurant.setContactInformation(req.getContactInformation());
        restaurant.setOwner(user);
        restaurant.setRegistrationDate(LocalDateTime.now());
        return restaurantReponsitory.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long retaurantId, CreateRestaurantResquest updateRestaurant) throws Exception {
        Restaurant restaurant= findRestaurantById(retaurantId);
    if(restaurant.getCuisineType()!=null){
        restaurant.setCuisineType(updateRestaurant.getCuisineType());
    }
    if (restaurant.getDescription()!=null){
        restaurant.setDescription(updateRestaurant.getDescription());
    }
        if (restaurant.getName()!=null){
            restaurant.setName(updateRestaurant.getName());
        }
        return restaurantReponsitory.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long restaurantId) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);
        restaurantReponsitory.delete(restaurant);
    }

    @Override
    public Page<Restaurant> getAllRestaurant(PageRequest pageRequest) {
        Page<Restaurant> restaurantPage;
        restaurantPage= restaurantReponsitory.findAll(pageRequest);
        return restaurantPage;
    }

    @Override
    public Page<Restaurant> searchRestaurant(PageRequest pageRequest,String keyword) {
        Page<Restaurant> restaurantPage;
        restaurantPage=restaurantReponsitory.findBySearchQuery(pageRequest,keyword);
        return restaurantPage;
    }

    @Override
    public Restaurant findRestaurantById(Long id) throws Exception {
        Optional<Restaurant> optional = restaurantReponsitory.findById(id);
        if(optional.isEmpty()){
            throw new Exception("Restaurant not found with id");
        }
        return optional.get();
    }

    @Override
    public Restaurant getRestaurantByUserId(Long userId) throws Exception {
        Restaurant restaurant=restaurantReponsitory.findByOwnerId(userId);
        if(restaurant == null){
            throw new Exception("Restaurant not found with owner id");
        }
        return restaurant;
    }

    @Override
    public RestaurantDto addFavorites(Long restaurantId, User user) throws Exception {
        Restaurant restaurant= findRestaurantById(restaurantId);

        RestaurantDto dto = new RestaurantDto();
        dto.setDescription(restaurant.getDescription());
        dto.setImages(restaurant.getImages());
        dto.setName(restaurant.getName());
        dto.setOpen(restaurant.isOpen());
        dto.setId(restaurantId);
        boolean isFavorited = false;
        List<RestaurantDto> favorites = user.getFavourites();
        for (RestaurantDto favorite : favorites) {
            if (favorite.getId().equals(restaurantId)) {
                isFavorited = true;
                break;
            }
        }

        if (isFavorited) {
            favorites.removeIf(f -> f.getId().equals(restaurantId));
        }else {
            favorites.add(dto);
        }

        userRepository.save(user);
        return dto;
    }

    @Override
    public Restaurant updateRestaurantStatus(Long id) throws Exception {
        Restaurant restaurant= findRestaurantById(id);
        restaurant.setOpen(!restaurant.isOpen());
        return restaurantReponsitory.save(restaurant);
    }
}
