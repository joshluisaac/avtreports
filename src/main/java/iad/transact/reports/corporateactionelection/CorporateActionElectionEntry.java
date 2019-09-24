package iad.transact.reports.corporateactionelection;

import java.math.BigDecimal;

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

  private CorporateActionElectionEntry() {}

  private CorporateActionElectionEntry(Builder builder) {
    this.adviserName = builder.adviserName;
    this.investorName = builder.investorName;
    this.invHeadRef = builder.invHeadRef;
    this.wrapperName = builder.wrapperName;
    this.decisionType = builder.decisionType;
    this.decisionTypeDescription = builder.decisionTypeDescription;
    this.decisionPersonFirmLinkRef = builder.decisionPersonFirmLinkRef;
    this.mifidIdentityDisplayName = builder.mifidIdentityDisplayName;
    this.decisionFirmIdRef = builder.decisionFirmIdRef;
    this.electedProduct = builder.electedProduct;
    this.electedProdRef = builder.electedProdRef;
    this.isin = builder.isin;
    this.origUnits = builder.origUnits;
    this.newUnits = builder.newUnits;
    this.electedPercentage = builder.electedPercentage;
    this.action = builder.action;
    this.errorMsg = builder.errorMsg;
  }

  public static final class Builder {
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

    public Builder setAdviserName(String adviserName) {
      this.adviserName = adviserName;
      return this;
    }

    public Builder setInvestorName(String investorName) {
      this.investorName = investorName;
      return this;
    }

    public Builder setInvHeadRef(String invHeadRef) {
      this.invHeadRef = invHeadRef;
      return this;
    }

    public Builder setWrapperName(String wrapperName) {
      this.wrapperName = wrapperName;
      return this;
    }

    public Builder setDecisionType(String decisionType) {
      this.decisionType = decisionType;
      return this;
    }

    public Builder setDecisionTypeDescription(String decisionTypeDescription) {
      this.decisionTypeDescription = decisionTypeDescription;
      return this;
    }

    public Builder setDecisionPersonFirmLinkRef(String decisionPersonFirmLinkRef) {
      this.decisionPersonFirmLinkRef = decisionPersonFirmLinkRef;
      return this;
    }

    public Builder setMifidIdentityDisplayName(String mifidIdentityDisplayName) {
      this.mifidIdentityDisplayName = mifidIdentityDisplayName;
      return this;
    }

    public Builder setDecisionFirmIdRef(String decisionFirmIdRef) {
      this.decisionFirmIdRef = decisionFirmIdRef;
      return this;
    }

    public Builder setElectedProduct(String electedProduct) {
      this.electedProduct = electedProduct;
      return this;
    }

    public Builder setElectedProdRef(String electedProdRef) {
      this.electedProdRef = electedProdRef;
      return this;
    }

    public Builder setIsin(String isin) {
      this.isin = isin;
      return this;
    }

    public Builder setOrigUnits(BigDecimal origUnits) {
      this.origUnits = origUnits;
      return this;
    }

    public Builder setNewUnits(BigDecimal newUnits) {
      this.newUnits = newUnits;
      return this;
    }

    public Builder setElectedPercentage(BigDecimal electedPercentage) {
      this.electedPercentage = electedPercentage;
      return this;
    }

    public Builder setAction(String action) {
      this.action = action;
      return this;
    }

    public Builder setErrorMsg(String errorMsg) {
      this.errorMsg = errorMsg;
      return this;
    }

    public CorporateActionElectionEntry build() {
      return new CorporateActionElectionEntry(this);
    }
  }

  public String getAdviserName() {
    return adviserName;
  }

  public String getInvestorName() {
    return investorName;
  }

  public String getInvHeadRef() {
    return invHeadRef;
  }

  public String getWrapperName() {
    return wrapperName;
  }

  public String getDecisionType() {
    return decisionType;
  }

  public String getDecisionTypeDescription() {
    return decisionTypeDescription;
  }

  public String getDecisionPersonFirmLinkRef() {
    return decisionPersonFirmLinkRef;
  }

  public String getMifidIdentityDisplayName() {
    return mifidIdentityDisplayName;
  }

  public String getDecisionFirmIdRef() {
    return decisionFirmIdRef;
  }

  public String getElectedProduct() {
    return electedProduct;
  }

  public String getElectedProdRef() {
    return electedProdRef;
  }

  public String getIsin() {
    return isin;
  }

  public BigDecimal getOrigUnits() {
    return origUnits;
  }

  public BigDecimal getNewUnits() {
    return newUnits;
  }

  public BigDecimal getElectedPercentage() {
    return electedPercentage;
  }

  public String getAction() {
    return action;
  }

  public String getErrorMsg() {
    return errorMsg;
  }
}
