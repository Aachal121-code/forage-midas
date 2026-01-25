package com.jpmc.midascore;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.TransactionRepository;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionListener {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @KafkaListener(
            topics = "${general.kafka-topic}",
            groupId = "midas-core-group"
    )
    public void listen(Transaction transaction) {
        System.out.println("Received transaction: " + transaction);

        // Lookup sender and recipient
        UserRecord sender = userRepository.findById(transaction.getSenderId());
        UserRecord recipient = userRepository.findById(transaction.getRecipientId());

        if (sender == null || recipient == null) {
            // Invalid IDs, ignore transaction
            return;
        }

        if (sender.getBalance() < transaction.getAmount()) {
            // Not enough balance, ignore transaction
            return;
        }

        // Deduct from sender, add to recipient
        sender.setBalance(sender.getBalance() - transaction.getAmount());
        recipient.setBalance(recipient.getBalance() + transaction.getAmount());

        // Save updated users
        userRepository.save(sender);
        userRepository.save(recipient);

        // Persist transaction
        TransactionRecord record = new TransactionRecord(sender, recipient, transaction.getAmount());
        transactionRepository.save(record);

        // Optional: print Waldorf's balance
        if (sender.getName().equalsIgnoreCase("waldorf") || recipient.getName().equalsIgnoreCase("waldorf")) {
            UserRecord waldorf = userRepository.findByName("waldorf");
            System.out.println("Waldorf's balance: " + waldorf.getBalance());
        }
    }
}
