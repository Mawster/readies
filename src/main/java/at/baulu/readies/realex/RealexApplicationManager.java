package at.baulu.readies.realex;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Mario on 11.03.2016.
 */
@Service
public class RealexApplicationManager {
    private static final Log LOG = LogFactory.getLog(RealexApplicationManager.class);
    private RealexCrawler realexCrawler;

    @Autowired
    public RealexApplicationManager(RealexCrawler realexCrawler) {
        LOG.debug("Initializing ReadiesRealexClient");
        this.realexCrawler = realexCrawler;
    }
}
