package payment.executor.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import payment.executor.contracts.confirmation.TransactionMessage;
import payment.executor.entities.*;

import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

@Component
public class ExecuteTransactions {

    private final ClientRepository clientRepository;
    private final TransactionRepository transactionRepository;
    private final PendingFundRepository pendingFundRepository;
    private final EntryRepository entryRepository;
    private final AccountRepository accountRepository;

    public ExecuteTransactions(ClientRepository clientRepository, TransactionRepository transactionRepository, PendingFundRepository pendingFundRepository, EntryRepository entryRepository, AccountRepository accountRepository) {
        this.clientRepository = clientRepository;
        this.transactionRepository = transactionRepository;
        this.pendingFundRepository = pendingFundRepository;
        this.entryRepository = entryRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public String execute(TransactionMessage transactionMessage) {
        UUID transactionId = UUID.fromString(transactionMessage.transactionId());
        Transaction transaction = null;
        try {
            Optional<Transaction> trx = transactionRepository.findById(transactionId);
            if(!trx.isPresent()) {
                throw new SQLException("No result for transacction");
            }
            transaction = trx.get();
            Account fromAccount = accountRepository.findByAccountNumber(transaction.getFromAccount().getAccountNumber());
            entryRepository.save(CreateEntry(transaction, fromAccount, TransactionType.DEBITO));

            Account receiverAccount = accountRepository.findByAccountNumberForUpdate(transaction.getReceiverAccount().getAccountNumber());
            entryRepository.save(CreateEntry(transaction, receiverAccount, TransactionType.CREDITO));
            receiverAccount.setBalance(receiverAccount.getBalance() + transaction.getAmount());
            accountRepository.save(receiverAccount);

            UpdateTransactionStatus(transaction);
            transactionRepository.save(transaction);

            PendingFunds pendingFunds = pendingFundRepository.findByTransactionId(transactionId);
            pendingFunds.setPending(false);
            pendingFundRepository.save(pendingFunds);
        }catch (Exception e){
            e.printStackTrace();
            return "Something went wrong";
        }
        return "Transaction executed successfully";
    }

    private Entry CreateEntry(Transaction transaction, Account account, Enum type) {
        Entry entry = new Entry();
        entry.setAccount(account);
        entry.setType(type.toString());
        entry.setAmount(type == TransactionType.DEBITO ? transaction.getAmount() * -1 : transaction.getAmount());
        entry.setTransactionId(transaction.getId());
        return entry;
    }

    private void UpdateTransactionStatus(Transaction transaction){
        transaction.setStatus("COMPLETE");
    }
}
