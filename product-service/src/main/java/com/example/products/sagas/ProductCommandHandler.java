package com.example.products.sagas;

import com.example.domain.ItemLine;
import com.example.domain.OrderDto;
import com.example.exceptions.OutOfStockException;
import com.example.products.domain.*;
import com.example.commands.ReserveProductCommand;
import com.example.replies.ProductOutOfStock;
import com.example.replies.ProductReserved;
import io.eventuate.tram.commands.consumer.CommandHandlers;
import io.eventuate.tram.commands.consumer.CommandMessage;
import io.eventuate.tram.messaging.common.Message;
import io.eventuate.tram.sagas.participant.SagaCommandHandlersBuilder;

import java.util.List;

import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withFailure;
import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withSuccess;

public class ProductCommandHandler {

    private ProductService productService;

    public ProductCommandHandler(ProductService productService) {
        this.productService = productService;
    }

    public CommandHandlers commandHandlerDefinitions() {
        return SagaCommandHandlersBuilder
                .fromChannel("productService")
                .onMessage(ReserveProductCommand.class, this::reserveProduct)
                .build();
    }

    public Message reserveProduct(CommandMessage<ReserveProductCommand> cm) {
        ReserveProductCommand cmd = cm.getCommand();
        OrderDto orderDto = cmd.getOrderDto();
        List<ItemLine> itemLines = orderDto.getItemLines();
        try {
            productService.updateProducts(itemLines, cmd.getOrderId());
            return withSuccess(new ProductReserved());
        } catch (OutOfStockException e) {
            return withFailure(new ProductOutOfStock());
        }
    }

}
