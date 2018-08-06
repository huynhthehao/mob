package vn.homecredit.hcvn.data.model.api.contract;

public final class ContractStatus {
    private ContractStatus(){}

    public final static String Approved = "S";
    public final static String Active = "A";
    public final static String PaidOff = "L";
    public final static String Cancelled = "T";
    public final static String Finished = "K";
    public final static String WrittenOff = "H";
    public final static String Rejected = "D";
}