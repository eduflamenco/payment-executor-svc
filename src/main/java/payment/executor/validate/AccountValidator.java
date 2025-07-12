package payment.executor.validate;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import payment.executor.contracts.validate.request.PaymentTransaction;
import payment.executor.entities.Account;
import payment.executor.entities.PendingFunds;
import payment.executor.entities.Transaction;
import payment.executor.repositories.AccountRepository;
import payment.executor.repositories.PendingFundRepository;
import payment.executor.repositories.TransactionRepository;

import java.util.UUID;

@Component
public class AccountValidator extends Validator {

    private AccountRepository accountRepository;
    private PendingFundRepository pendingFundRepository;
    private TransactionRepository  transactionRepository;

    public AccountValidator(AccountRepository accountRepository, PendingFundRepository pendingFundRepository, TransactionRepository  transactionRepository) {
        this.accountRepository = accountRepository;
        this.pendingFundRepository = pendingFundRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    @Transactional
    public ValidatorResult validate(PaymentTransaction payReq) {
        Transaction transaction = null;
        ValidatorResult result = null;
        try {
            Account account = accountRepository.findByAccountNumberForUpdate(payReq.creditCardDetails().cardNumber());
            Account accountReceiver = accountRepository.findByAccountNumberForUpdate(payReq.receiverAccountId());
            if (account == null || accountReceiver == null) {
                return new  ValidatorResult(false, "Invalid Account");
            }
            if (account.getBalance().compareTo(payReq.paymentAmount()) < 0){
                return new  ValidatorResult(false, "InsufficientFundsException");
            }

            if (!account.getClient().getUsername().equals(payReq.customerId())){
                return new  ValidatorResult(false, "Client Id validation error");
            }

            transaction = transactionRepository.save(createTransactionRecord(account, accountReceiver, payReq));
            PendingFunds pendingFunds = pendingFundRepository.save(createPendingFundsRecord(account, payReq.paymentAmount(), transaction.getId()));
            account.setBalance(account.getBalance() - payReq.paymentAmount());
            accountRepository.save(account);
            result = checkNext(payReq);
            result.setTransactionId(transaction.getId().toString());
            result.setAmount(payReq.paymentAmount());
            result.setBillNumber("FAC" + pendingFunds.getId());
        }catch (Exception e){
            System.out.println("Exception occurred" + e.getMessage());
            return new  ValidatorResult(false, "Exception occurred" + e.getMessage());
        }

        return result;
    }

    private Transaction createTransactionRecord(Account account, Account accountReceiver, PaymentTransaction payReq){
        Transaction transaction = new Transaction();
        transaction.setAmount(payReq.paymentAmount());
        transaction.setClient(account.getClient());
        transaction.setFromAccount(account);
        transaction.setStatus("pending");
        transaction.setReceiverAccount(accountReceiver);
        return  transaction;
    }

    private PendingFunds createPendingFundsRecord(Account account, Double amount, UUID tranId){
        PendingFunds pendingFunds = new PendingFunds();
        pendingFunds.setAccount(account);
        pendingFunds.setAmount(amount);
        pendingFunds.setTransactionId(tranId);
        return  pendingFunds;
    }

}
