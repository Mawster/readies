package at.baulu.readies.realex;

import at.baulu.readies.rest.transaction.Transaction;
import com.realexpayments.remote.sdk.RealexClient;
import com.realexpayments.remote.sdk.domain.Card;
import com.realexpayments.remote.sdk.domain.Cvn;
import com.realexpayments.remote.sdk.domain.payment.AutoSettle;
import com.realexpayments.remote.sdk.domain.payment.PaymentRequest;
import com.realexpayments.remote.sdk.domain.payment.PaymentResponse;
import com.realexpayments.remote.sdk.http.HttpConfiguration;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by Mario on 12.03.2016.
 */
@Service
public class ReadiesRealexClient {
    private static final Log LOG = LogFactory.getLog(ReadiesRealexClient.class);
    private final HttpConfiguration realexFakeEndpoint;
    @Value("${readies.realex.user-secret}")
    private String realexUser;
    @Value("${readies.realex.password-secret}")
    private String realexPassword;

    public ReadiesRealexClient() {
        LOG.debug("Initializing ReadiesRealexClient");
        realexFakeEndpoint = new HttpConfiguration();
        realexFakeEndpoint.setEndpoint("https://epage.sandbox.payandshop.com/epage-remote.cgi");
    }

    public PaymentResponse executePayment(Transaction transaction) {
        LOG.info(String.format("Execute a new payment: %s", ToStringBuilder.reflectionToString(transaction, ToStringStyle.JSON_STYLE)));

        Card card = new Card()
                .addExpiryDate("0119")
                .addNumber("4263970000005262")
                .addType(Card.CardType.VISA)
                .addCardHolderName("Joe Smith")
                .addCvn("123")
                .addCvnPresenceIndicator(Cvn.PresenceIndicator.CVN_PRESENT);
        LOG.info(String.format("Using card: %s", ToStringBuilder.reflectionToString(card, ToStringStyle
                .JSON_STYLE)));

        PaymentRequest request = new PaymentRequest()
                .addAccount(transaction.getCreditor().getSubAccount())
                .addMerchantId(realexUser)
                .addType(PaymentRequest.PaymentType.AUTH)
                .addAmount((long) transaction.getAmount())
                .addCurrency("GBP")
                .addCard(card)
                .addAutoSettle(new AutoSettle().addFlag(AutoSettle.AutoSettleFlag.TRUE));
        LOG.info(String.format("PaymentRequest created: %s", ToStringBuilder.reflectionToString(request, ToStringStyle
                .JSON_STYLE)));

        RealexClient client = new RealexClient(realexPassword, this.realexFakeEndpoint);
        PaymentResponse response = client.send(request);
        LOG.info(String.format("Response for paymentrequest: %s", ToStringBuilder.reflectionToString(response, ToStringStyle
                .JSON_STYLE)));
        return response;
    }
}
