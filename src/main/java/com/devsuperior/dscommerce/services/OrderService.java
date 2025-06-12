package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.dto.OrderDTO;
import com.devsuperior.dscommerce.dto.OrderItemDTO;
import com.devsuperior.dscommerce.entities.Order;
import com.devsuperior.dscommerce.entities.OrderItem;
import com.devsuperior.dscommerce.entities.OrderStatus;
import com.devsuperior.dscommerce.entities.Product;
import com.devsuperior.dscommerce.repositories.OrderItemRepository;
import com.devsuperior.dscommerce.repositories.OrderRepository;
import com.devsuperior.dscommerce.repositories.ProductRepository;
import com.devsuperior.dscommerce.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Transactional(readOnly = true)
    public OrderDTO findById(Long id) {
        Order o = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado"));
        authService.validateSelfOrAdmin(o.getClient().getId());
        return new OrderDTO(o);
    }
    @Transactional
    public OrderDTO insert(OrderDTO dto) {
        Order o = new Order();
        o.setMoment(Instant.now());
        o.setStatus(OrderStatus.WAITING_PAYMENT);
        o.setClient(userService.authenticated());

        for (OrderItemDTO itemDTO : dto.getItems()) {
            Product p = productRepository.getReferenceById(itemDTO.getProductId());
            OrderItem item = new OrderItem(o, p, itemDTO.getQuantity(), p.getPrice());
            o.getItems().add(item);
        }

        repository.save(o);
        orderItemRepository.saveAll(o.getItems());

        return new OrderDTO(o);
    }


}
