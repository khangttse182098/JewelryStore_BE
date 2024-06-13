    package com.swp.jewelrystore.websocket;

    import com.fasterxml.jackson.core.JsonProcessingException;
    import com.fasterxml.jackson.databind.ObjectMapper;
    import com.swp.jewelrystore.model.response.InvoiceResponseDTO;
    import com.swp.jewelrystore.service.IOrderService;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.web.socket.TextMessage;
    import org.springframework.web.socket.WebSocketMessage;
    import org.springframework.web.socket.WebSocketSession;
    import org.springframework.web.socket.handler.TextWebSocketHandler;

    import java.io.IOException;
    import java.util.List;

    @Slf4j
    public class DataHandler extends TextWebSocketHandler {
        @Autowired
        private IOrderService orderService;
        private final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws IOException {
            // Assuming you have a method to fetch the list of InvoiceResponseDTO objects
            List<InvoiceResponseDTO> invoiceList = orderService.getPaidAndReceivedPurchaseOrder();
            ;
            // Convert list to JSON
            String jsonInvoiceList;
            try {
                jsonInvoiceList = objectMapper.writeValueAsString(invoiceList);
            } catch (JsonProcessingException e) {
                log.error("Error serializing invoice list to JSON", e);
                return;
            }

            // Send JSON invoice list as a WebSocket message
            session.sendMessage(new TextMessage(jsonInvoiceList));
        }
    }
