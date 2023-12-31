package eu.panic.chatservice.template.service.implement;

import eu.panic.chatservice.template.dto.UserDto;
import eu.panic.chatservice.template.entity.Message;
import eu.panic.chatservice.template.entity.Replenishment;
import eu.panic.chatservice.template.enums.MessageType;
import eu.panic.chatservice.template.exception.InvalidCredentialsException;
import eu.panic.chatservice.template.payload.ChatSendMessageRequest;
import eu.panic.chatservice.template.payload.MessageMessage;
import eu.panic.chatservice.template.repository.MessageRepository;
import eu.panic.chatservice.template.repository.ReplenishmentRepository;
import eu.panic.chatservice.template.repository.UserRepository;
import eu.panic.chatservice.template.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ChatServiceImpl implements ChatService {
    public ChatServiceImpl(RestTemplate restTemplate, SimpMessagingTemplate simpMessagingTemplate, MessageRepository messageRepository, UserRepository userRepository, ReplenishmentRepository replenishmentRepository) {
        this.restTemplate = restTemplate;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.replenishmentRepository = replenishmentRepository;
    }

    private final RestTemplate restTemplate;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ReplenishmentRepository replenishmentRepository;
    private static final String JWT_URL = "http://localhost:8080/api/auth/getInfoByJwt";
    @Override
    public void sendMessage(String jwtToken, ChatSendMessageRequest chatSendMessageRequest) {
        log.info("Starting method sendMessage on service {} method: sendMessage", ChatServiceImpl.class);

        log.info("Receiving entity user by JWT token on service {} method: sendMessage", ChatServiceImpl.class);

        ResponseEntity<UserDto> userDtoResponseEntity =
                restTemplate.postForEntity(JWT_URL + "?jwtToken=" + jwtToken, null, UserDto.class);

        if (userDtoResponseEntity.getStatusCode().isError()){
            log.warn("Incorrect JWT token on service {} method: sendMessage", ChatServiceImpl.class);
            throw new InvalidCredentialsException("Incorrect JWT token");
        }

        if (chatSendMessageRequest.getMessage().length() > 60){
            log.warn("The maximum number of characters in a message is 60 on service {} method: sendMessage", ChatServiceImpl.class);
            throw new InvalidCredentialsException("The maximum number of characters in a message is 60");
        }

        UserDto userDto = userDtoResponseEntity.getBody();

        log.info("Finding last entity replenishment by Username on service {} method: sendMessage", ChatServiceImpl.class);

        Replenishment replenishment = replenishmentRepository.findTopByUsernameOrderByTimestampDesc(userDto.getUsername());

        if (replenishment == null || (System.currentTimeMillis() - replenishment.getTimestamp() * 1000) > 14 * 24 * 60 * 60 * 1000){
            log.warn("You must have made one deposit in the last 2 weeks in order to post in the chat room on service {} method: sendMessage", ChatServiceImpl.class);
            throw new InvalidCredentialsException("You must have made one deposit in the last 2 weeks in order to post in the chat room");
        }

        if (!userDto.getIsAccountNonLocked()){
            log.warn("You have been temporarily blocked. For all questions contact support on service on service {}" +
                    "method: sendMessage", ChatServiceImpl.class);
            throw new InvalidCredentialsException("You have been temporarily blocked. For all questions contact support");
        }

        Message message = new Message();

        message.setType(MessageType.MESSAGE);
        message.setUsername(userDto.getUsername());
        message.setMessage(chatSendMessageRequest.getMessage());
        message.setTimestamp(System.currentTimeMillis());

        log.info("Saving an entity message on service {} method: sendMessage", ChatServiceImpl.class);

        messageRepository.save(message);

        MessageMessage messageMessage = new MessageMessage();

        messageMessage.setType(message.getType());
        messageMessage.setUser(userRepository.findByUsername(message.getUsername()));

        messageMessage.setMessage(message.getMessage());
        messageMessage.setTimestamp(message.getTimestamp());

        simpMessagingTemplate.convertAndSend("/chat/topic", messageMessage);
    }

    @Override
    public List<MessageMessage> getAllMessages() {
        log.info("Starting method getAllMessages on service {} method: getAllMessages", ChatServiceImpl.class);

        log.info("Finding last fifty entities message on service {} method: getAllMessages", ChatServiceImpl.class);

        List<Message> messageList = messageRepository.findTop50ByOrderByTimestampDesc();

        List<MessageMessage> messageMessageList = new ArrayList<>();

        for (Message message : messageList){
            MessageMessage messageMessage = new MessageMessage();

            messageMessage.setId(message.getId());
            messageMessage.setType(message.getType());
            messageMessage.setUser(userRepository.findByUsername(message.getUsername()));
            messageMessage.setMessage(message.getMessage());
            messageMessage.setTimestamp(message.getTimestamp());

            messageMessageList.add(messageMessage);
        }

        return messageMessageList;
    }
}
