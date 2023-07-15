package eu.panic.chatservice.template.repository.implement;

import eu.panic.chatservice.generatedClasses.tables.MessagesTable;
import eu.panic.chatservice.template.entity.Message;
import eu.panic.chatservice.template.repository.MessageRepository;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MessageRepositoryImpl implements MessageRepository {
    public MessageRepositoryImpl(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    private final DSLContext dslContext;
    @Override
    public void save(Message message) {
        dslContext.insertInto(MessagesTable.MESSAGES_TABLE)
                .set(MessagesTable.MESSAGES_TABLE.TYPE, message.getType().toString())
                .set(MessagesTable.MESSAGES_TABLE.USERNAME, message.getUsername())
                .set(MessagesTable.MESSAGES_TABLE.MESSAGE, message.getMessage())
                .set(MessagesTable.MESSAGES_TABLE.TIMESTAMP, message.getTimestamp())
                .execute();
    }

    @Override
    public List<Message> findLastFiftyMessages() {
        return dslContext.selectFrom(MessagesTable.MESSAGES_TABLE)
                .orderBy(MessagesTable.MESSAGES_TABLE.TIMESTAMP.desc())
                .limit(50)
                .fetchInto(Message.class);
    }
}