package iad.transact.reports.corporateactionelection;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CorporateActionElectionEntry {
  private String adviserName;
  private String investorName;
  private String invHeadRef;
  private String wrapperName;
  private String decisionType;
  private String decisionTypeDescription;
  private String decisionPersonFirmLinkRef;
  private String mifidIdentityDisplayName;
  private String decisionFirmIdRef;
  private String electedProduct;
  private String electedProdRef;
  private String isin;
  private BigDecimal origUnits;
  private BigDecimal newUnits;
  private BigDecimal electedPercentage;
  private String action;
  private String errorMsg;


}
