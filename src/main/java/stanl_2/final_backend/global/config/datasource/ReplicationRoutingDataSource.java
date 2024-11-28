package stanl_2.final_backend.global.config.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import static org.springframework.transaction.support.TransactionSynchronizationManager.isCurrentTransactionReadOnly;

public class ReplicationRoutingDataSource extends AbstractRoutingDataSource {

    public Object determineCurrentLookupKey() {
        String dataSourceName = isCurrentTransactionReadOnly() ? "reader" : "writer";
        System.out.println(">>>>>> current data source : {}"+ dataSourceName);
        return dataSourceName;

    }
}
