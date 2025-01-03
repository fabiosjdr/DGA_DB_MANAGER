package br.com.nextgen.DGA_DB_MANAGER.repositories.report_activity;

import java.math.BigInteger;

public interface ReportActivity {

    String     getTitle();
    BigInteger getIdActivityDetail();
    BigInteger getIdActivity();
    BigInteger getIdActivityStage();
    String     getStageName();
    BigInteger getUserId();
    String     getUserName();
    Long       getTime();
}