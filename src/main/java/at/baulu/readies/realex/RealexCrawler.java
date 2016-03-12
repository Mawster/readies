package at.baulu.readies.realex;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Mario on 11.03.2016.
 */
@Service
public class RealexCrawler {
    private static final Log LOG = LogFactory.getLog(RealexCrawler.class);
    @Value("${readies.realex.user-secret}")
    private String realexUser;
    @Value("${readies.realex.password-secret}")
    private String realexPassword;
    private List<String> subAccounts;
    private Iterator<String> subAccountIterator;

    public RealexCrawler() {
        LOG.debug("Initialize RealexCrawler!");
        this.checkRealexSetting();
        this.login();
        this.initializeCustomers();
        this.subAccountIterator = subAccounts.iterator();
    }

    private void checkRealexSetting() {
        if (StringUtils.hasText(this.realexUser) || StringUtils.hasText(this.realexPassword)) {
            throw new IllegalStateException("Realex user and password has to be set!");
        }
    }

    private void login() {
        /*
        Normally we would crawl the admin page from realex to login - its not possible to create
        subaccounts on our own so we don't dont create the subaccounts automatically - we just got customer1 to
        customer10 and we will use it for this showcase
         */
    }

    private void initializeCustomers() {
        this.subAccounts = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            this.subAccounts.add("customer" + i);
        }
    }

    public String getNextSubAccount() {
        if (!this.subAccountIterator.hasNext()) {
            this.subAccountIterator = subAccounts.iterator();
        }
        return this.subAccountIterator.next();
    }
}
