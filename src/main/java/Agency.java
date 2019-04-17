public class Agency implements Comparable<Agency> {
    private Address address;
    private Integer agency_code;
    private String correspondet_id;
    private String description;
    private String disabled;
    private Double distance;
    private String id;
    private String payment_method_id;
    private String phone;
    private String site_id;
    private String terminal;

    public enum Criterio {
        ADDRESS_LINE,
        AGENCY_CODE,
        DISTANCE;
    }

    public static Criterio criterio;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Integer getAgency_code() {
        return agency_code;
    }

    public void setAgency_code(Integer agency_code) {
        this.agency_code = agency_code;
    }

    public String getCorrespondet_id() {
        return correspondet_id;
    }

    public void setCorrespondet_id(String correspondet_id) {
        this.correspondet_id = correspondet_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisabled() {
        return disabled;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPayment_method_id() {
        return payment_method_id;
    }

    public void setPayment_method_id(String payment_method_id) {
        this.payment_method_id = payment_method_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSite_id() {
        return site_id;
    }

    public void setSite_id(String site_id) {
        this.site_id = site_id;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    @Override
    public int compareTo(Agency o) {
        switch (criterio) {
            case ADDRESS_LINE:
                return this.address.getAddress_line().compareTo(o.address.getAddress_line());
            case AGENCY_CODE:
                return this.agency_code.compareTo(o.agency_code);
            case DISTANCE:
                return this.distance.compareTo(o.distance);
            default:
                return 0;
        }
    }
}
