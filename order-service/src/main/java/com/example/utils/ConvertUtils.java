package com.example.utils;

import com.example.domain.ItemLine;
import com.example.domain.OrderDto;
import com.example.order.domain.Order;
import com.example.order.domain.OrderDetails;
import com.example.order.web.CreateOrderRequest;

import java.util.Optional;

/**
 * Helper class to convert model objects to data transfer objects
 */
public class ConvertUtils {

    /**
     * Conver {@code CreateOrderRequest} to {@code OrderDto}
     * @param createOrderRequest
     * @return
     */
    public static OrderDto convert(CreateOrderRequest createOrderRequest) {
        OrderDto orderDto = new OrderDto(createOrderRequest.getCustomerId());
        if (createOrderRequest.getItems() != null) {
            for (ItemLine item : createOrderRequest.getItems()) {
                orderDto.addItem(item);
            }
        }
        return orderDto;
    }

    /**
     * Convert {@code ItemLine} to {@code OrderDetails}
     * @param itemLine
     * @return
     */
    public static OrderDetails toOderDetails(ItemLine itemLine) {
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setAmount(itemLine.getAmount());
        orderDetails.setPrice(itemLine.getPrice());
        orderDetails.setProductId(itemLine.getProductId());
        return orderDetails;
    }

    /**
     * Convert {@code OrderDetails} to {@code ItemLine}
     * @param orderDetails
     * @return
     */
    public static ItemLine toItemLine(OrderDetails orderDetails) {
        ItemLine itemLine = new ItemLine();
        itemLine.setAmount(orderDetails.getAmount());
        itemLine.setPrice(orderDetails.getPrice());
        itemLine.setProductId(orderDetails.getProductId());
        return itemLine;
    }

    /**
     * Convert {@code Order} to {@code OrderDto}
     * @param order
     * @return
     */
    public static Optional<OrderDto> toOrderDto(Order order) {
        OrderDto dto = new OrderDto();
        dto.setOrderId(order.getId());
        dto.setCustomerId(order.getCustomerId());
        dto.setState(order.getState().name());
        Optional.ofNullable(order.getRejectionReason()).ifPresent(reason -> dto.setRejectReason(reason.name()));
        order.getOrderDetails().stream().map(ConvertUtils::toItemLine).forEach(dto::addItem);
        return Optional.of(dto);
    }
}
