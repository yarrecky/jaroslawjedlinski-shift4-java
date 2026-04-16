package com.shift4;

import com.shift4.connection.Connection;
import com.shift4.connection.HttpClientConnection;
import com.shift4.enums.FileUploadPurpose;
import com.shift4.exception.SignException;
import com.shift4.request.*;
import com.shift4.response.*;
import com.shift4.util.ObjectSerializer;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.codec.binary.Base64.encodeBase64String;
import static org.apache.commons.codec.binary.Hex.encodeHexString;

public class Shift4Gateway implements Closeable {

    public static final String DEFAULT_ENDPOINT = "https://api.shift4.com";
    public static final String UPLOADS_ENDPOINT = "https://uploads.api.shift4.com";

    private static final String CHARGES_PATH = "/charges";
    private static final String TOKENS_PATH = "/tokens";
    private static final String CUSTOMERS_PATH = "/customers";
    private static final String CARDS_PATH = "/customers/%s/cards";
    private static final String PAYMENT_METHOD_PATH = "/payment-methods";
    private static final String PLANS_PATH = "/plans";
    private static final String SUBSCRIPTIONS_PATH = "/subscriptions";
    private static final String EVENTS_PATH = "/events";
    private static final String BLACKLIST_RULE_PATH = "/blacklist";
    private static final String CREDIT_PATH = "/credits";
    private static final String FILES_PATH = "/files";
    private static final String DISPUTES_PATH = "/disputes";
    private static final String FRAUD_WARNING_PATH = "/fraud-warnings";
    private static final String REFUND_PATH = "/refunds";
    private static final String CHECKOUT_SESSIONS_PATH = "/checkout-sessions";
    private static final String PRODUCTS_PATH = "/products";
    private static final String TAXES_PATH = "/taxes";

    private final ObjectSerializer objectSerializer = ObjectSerializer.INSTANCE;
    private final ConnectionClient gatewayClient;
    private final ConnectionClient gatewayUploadsClient;

    public Shift4Gateway() {
        this(null);
    }

    public Shift4Gateway(String secretKey) {
        this(secretKey, new HttpClientConnection());
    }

    public Shift4Gateway(String secretKey, String merchantId) {
        this(secretKey, merchantId, new HttpClientConnection());
    }

    public Shift4Gateway(String secretKey, Connection connection) {
        this.gatewayClient = new ConnectionClient(connection, secretKey, DEFAULT_ENDPOINT);
        this.gatewayUploadsClient = new ConnectionClient(connection, secretKey, UPLOADS_ENDPOINT);
    }

    public Shift4Gateway(String secretKey, String merchantId, Connection connection) {
        this.gatewayClient = new ConnectionClient(connection, secretKey, merchantId, DEFAULT_ENDPOINT);
        this.gatewayUploadsClient = new ConnectionClient(connection, secretKey, merchantId, UPLOADS_ENDPOINT);
    }

    public Charge createCharge(ChargeRequest request) {
        return post(CHARGES_PATH, request, Charge.class);
    }

    public Charge createCharge(ChargeRequest request, RequestOptions requestOptions) {
        return post(CHARGES_PATH, request, requestOptions, Charge.class);
    }

    public Charge captureCharge(CaptureRequest request) {
        return post(CHARGES_PATH + "/" + request.getChargeId() + "/capture", request, Charge.class);
    }

    public Charge captureCharge(CaptureRequest request, RequestOptions requestOptions) {
        return post(CHARGES_PATH + "/" + request.getChargeId() + "/capture", request, requestOptions, Charge.class);
    }

    public Charge retrieveCharge(String chargeId) {
        return get(CHARGES_PATH + "/" + chargeId, Charge.class);
    }

    public Charge updateCharge(ChargeUpdateRequest request) {
        return post(CHARGES_PATH + "/" + request.getChargeId(), request, Charge.class);
    }

    public Charge updateCharge(ChargeUpdateRequest request, RequestOptions requestOptions) {
        return post(CHARGES_PATH + "/" + request.getChargeId(), request, requestOptions, Charge.class);
    }

    public ListResponse<Charge> listCharges() {
        return list(CHARGES_PATH, Charge.class);
    }

    public ListResponse<Charge> listCharges(ChargeListRequest request) {
        return list(CHARGES_PATH, request, Charge.class);
    }

    public Customer createCustomer(CustomerRequest request) {
        return post(CUSTOMERS_PATH, request, Customer.class);
    }

    public Customer createCustomer(CustomerRequest request, RequestOptions requestOptions) {
        return post(CUSTOMERS_PATH, request, requestOptions, Customer.class);
    }

    public Customer retrieveCustomer(String customerId) {
        return get(CUSTOMERS_PATH + "/" + customerId, Customer.class);
    }

    public Customer updateCustomer(CustomerUpdateRequest request) {
        return post(CUSTOMERS_PATH + "/" + request.getCustomerId(), request, Customer.class);
    }

    public Customer updateCustomer(CustomerUpdateRequest request, RequestOptions requestOptions) {
        return post(CUSTOMERS_PATH + "/" + request.getCustomerId(), request, requestOptions, Customer.class);
    }

    public DeleteResponse deleteCustomer(String customerId) {
        return delete(CUSTOMERS_PATH + "/" + customerId, DeleteResponse.class);
    }

    public ListResponse<Customer> listCustomers() {
        return list(CUSTOMERS_PATH, Customer.class);
    }

    public ListResponse<Customer> listCustomers(CustomerListRequest request) {
        return list(CUSTOMERS_PATH, request, Customer.class);
    }

    public Card createCard(CardRequest request) {
        return post(format(CARDS_PATH, request.getCustomerId()), request, Card.class);
    }

    public Card createCard(CardRequest request, RequestOptions requestOptions) {
        return post(format(CARDS_PATH, request.getCustomerId()), request, requestOptions, Card.class);
    }

    public Card retrieveCard(String customerId, String cardId) {
        return get(format(CARDS_PATH, customerId) + "/" + cardId, Card.class);
    }

    public Card updateCard(CardUpdateRequest card) {
        return post(format(CARDS_PATH, card.getCustomerId()) + "/" + card.getCardId(), card, Card.class);
    }

    public Card updateCard(CardUpdateRequest card, RequestOptions requestOptions) {
        return post(format(CARDS_PATH, card.getCustomerId()) + "/" + card.getCardId(), card, requestOptions, Card.class);
    }

    public DeleteResponse deleteCard(String customerId, String cardId) {
        return delete(format(CARDS_PATH, customerId) + "/" + cardId, DeleteResponse.class);
    }

    public ListResponse<Card> listCards(String customerId) {
        return listCards(new CardListRequest().customerId(customerId));
    }

    public ListResponse<Card> listCards(CardListRequest listCards) {
        return list(format(CARDS_PATH, listCards.getCustomerId()), listCards, Card.class);
    }

    public PaymentMethod createPaymentMethod(PaymentMethodRequest request) {
        return post(PAYMENT_METHOD_PATH, request, PaymentMethod.class);
    }

    public PaymentMethod createPaymentMethod(PaymentMethodRequest request, RequestOptions requestOptions) {
        return post(PAYMENT_METHOD_PATH, request, requestOptions, PaymentMethod.class);
    }

    public PaymentMethod retrievePaymentMethod(String paymentMethodId) {
        return get(PAYMENT_METHOD_PATH + "/" + paymentMethodId, PaymentMethod.class);
    }

    public ListResponse<PaymentMethod> listPaymentMethods(PaymentMethodListRequest listRequest) {
        return list(PAYMENT_METHOD_PATH, listRequest, PaymentMethod.class);
    }

    public DeleteResponse deletePaymentMethod(String paymentMethodId) {
        return delete(PAYMENT_METHOD_PATH + "/" + paymentMethodId, DeleteResponse.class);
    }

    public Subscription createSubscription(SubscriptionRequest request) {
        return post(SUBSCRIPTIONS_PATH, request, Subscription.class);
    }

    public Subscription createSubscription(SubscriptionRequest request, RequestOptions requestOptions) {
        return post(SUBSCRIPTIONS_PATH, request, requestOptions, Subscription.class);
    }

    public Subscription retrieveSubscription(String subscriptionId) {
        return get(SUBSCRIPTIONS_PATH + "/" + subscriptionId, Subscription.class);
    }

    public Subscription updateSubscription(SubscriptionUpdateRequest request) {
        return post(SUBSCRIPTIONS_PATH + "/" + request.getSubscriptionId(), request, Subscription.class);
    }

    public Subscription updateSubscription(SubscriptionUpdateRequest request, RequestOptions requestOptions) {
        return post(SUBSCRIPTIONS_PATH + "/" + request.getSubscriptionId(), request, requestOptions, Subscription.class);
    }

    public Subscription cancelSubscription(SubscriptionCancelRequest request) {
        return delete(SUBSCRIPTIONS_PATH + "/" + request.getSubscriptionId(), request, Subscription.class);
    }

    public ListResponse<Subscription> listSubscriptions(String customerId) {
        return listSubscriptions(new SubscriptionListRequest().customerId(customerId));
    }

    public ListResponse<Subscription> listSubscriptions(SubscriptionListRequest request) {
        return list(SUBSCRIPTIONS_PATH, request, Subscription.class);
    }

    public Plan createPlan(PlanRequest request) {
        return post(PLANS_PATH, request, Plan.class);
    }

    public Plan createPlan(PlanRequest request, RequestOptions requestOptions) {
        return post(PLANS_PATH, request, requestOptions, Plan.class);
    }

    public Plan retrievePlan(String planId) {
        return get(PLANS_PATH + "/" + planId, Plan.class);
    }

    public Plan updatePlan(PlanUpdateRequest request) {
        return post(PLANS_PATH + "/" + request.getPlanId(), request, Plan.class);
    }

    public Plan updatePlan(PlanUpdateRequest request, RequestOptions requestOptions) {
        return post(PLANS_PATH + "/" + request.getPlanId(), request, requestOptions, Plan.class);
    }

    public DeleteResponse deletePlan(String planId) {
        return delete(PLANS_PATH + "/" + planId, DeleteResponse.class);
    }

    public ListResponse<Plan> listPlans() {
        return list(PLANS_PATH, Plan.class);
    }

    public ListResponse<Plan> listPlans(PlanListRequest request) {
        return list(PLANS_PATH, request, Plan.class);
    }

    public Event retrieveEvent(String eventId) {
        return get(EVENTS_PATH + "/" + eventId, Event.class);
    }

    public ListResponse<Event> listEvents() {
        return list(EVENTS_PATH, Event.class);
    }

    public ListResponse<Event> listEvents(EventListRequest listEvents) {
        return list(EVENTS_PATH, listEvents, Event.class);
    }

    public Token createToken(TokenRequest request) {
        return post(TOKENS_PATH, request, Token.class);
    }

    public Token createToken(TokenRequest request, RequestOptions requestOptions) {
        return post(TOKENS_PATH, request, requestOptions, Token.class);
    }

    public Token retrieveToken(String tokenId) {
        return get(TOKENS_PATH + "/" + tokenId, Token.class);
    }

    public BlacklistRule createBlacklistRule(BlacklistRuleRequest request) {
        return post(BLACKLIST_RULE_PATH, request, BlacklistRule.class);
    }

    public BlacklistRule createBlacklistRule(BlacklistRuleRequest request, RequestOptions requestOptions) {
        return post(BLACKLIST_RULE_PATH, request, requestOptions, BlacklistRule.class);
    }

    public BlacklistRule retrieveBlacklistRule(String blacklistRuleId) {
        return get(BLACKLIST_RULE_PATH + "/" + blacklistRuleId, BlacklistRule.class);
    }

    public DeleteResponse deleteBlacklistRule(String blacklistRuleId) {
        return delete(BLACKLIST_RULE_PATH + "/" + blacklistRuleId, DeleteResponse.class);
    }

    public ListResponse<BlacklistRule> listBlacklistRules() {
        return list(BLACKLIST_RULE_PATH, BlacklistRule.class);
    }

    public ListResponse<BlacklistRule> listBlacklistRules(BlacklistRuleListRequest request) {
        return list(BLACKLIST_RULE_PATH, request, BlacklistRule.class);
    }

    public Credit createCredit(CreditRequest request) {
        return post(CREDIT_PATH, request, Credit.class);
    }

    public Credit createCredit(CreditRequest request, RequestOptions requestOptions) {
        return post(CREDIT_PATH, request, requestOptions, Credit.class);
    }

    public Credit retrieveCredit(String creditId) {
        return get(CREDIT_PATH + "/" + creditId, Credit.class);
    }

    public Credit updateCredit(CreditUpdateRequest credit) {
        return post(CREDIT_PATH + "/" + credit.getCreditId(), credit, Credit.class);
    }

    public Credit updateCredit(CreditUpdateRequest credit, RequestOptions requestOptions) {
        return post(CREDIT_PATH + "/" + credit.getCreditId(), credit, requestOptions, Credit.class);
    }

    public ListResponse<Credit> listCredits() {
        return list(CREDIT_PATH, Credit.class);
    }

    public ListResponse<Credit> listCredits(CreditListRequest request) {
        return list(CREDIT_PATH, request, Credit.class);
    }

    public String signCheckoutRequest(CheckoutRequest checkoutRequest) {
        String data = objectSerializer.serialize(checkoutRequest);

        try {
            String algorithm = "HmacSHA256";

            Mac hmac = Mac.getInstance(algorithm);
            hmac.init(new SecretKeySpec(gatewayClient.getSecretKey().getBytes(UTF_8), algorithm));
            String signature = encodeHexString(hmac.doFinal(data.getBytes(UTF_8)));

            return encodeBase64String((signature + "|" + data).getBytes(UTF_8));
        } catch (Exception ex) {
            throw new SignException(ex);
        }
    }

    public FileUpload createFileUpload(File file, FileUploadPurpose purpose) {
        Map<String, File> files = new HashMap<>();
        files.put("file", file);

        Map<String, String> form = new HashMap<>();
        form.put("purpose", purpose.getValue());

        return uploadsMultipart(FILES_PATH, files, form, FileUpload.class);
    }

    public FileUpload retrieveFileUpload(String id) {
        return uploadsGet(FILES_PATH + "/" + id, FileUpload.class);
    }

    public ListResponse<FileUpload> listFileUploads() {
        return uploadslist(FILES_PATH, null, FileUpload.class);
    }

    public ListResponse<FileUpload> listFileUploads(FileUploadListRequest request) {
        return uploadslist(FILES_PATH, request, FileUpload.class);
    }

    public Dispute retrieveDispute(String id) {
        return get(DISPUTES_PATH + "/" + id, Dispute.class);
    }

    public Dispute updateDispute(DisputeUpdateRequest request) {
        return post(DISPUTES_PATH + "/" + request.getDisputeId(), request, Dispute.class);
    }

    public Dispute updateDispute(DisputeUpdateRequest request, RequestOptions requestOptions) {
        return post(DISPUTES_PATH + "/" + request.getDisputeId(), request, requestOptions, Dispute.class);
    }

    public Dispute closeDispute(String id) {
        return post(DISPUTES_PATH + "/" + id + "/close", null, Dispute.class);
    }

    public ListResponse<Dispute> listDisputes() {
        return list(DISPUTES_PATH, null, Dispute.class);
    }

    public ListResponse<Dispute> listDisputes(DisputeListRequest request) {
        return list(DISPUTES_PATH, request, Dispute.class);
    }

    public ListResponse<FraudWarning> listFraudWarnings() {
        return list(FRAUD_WARNING_PATH, FraudWarning.class);
    }

    public ListResponse<FraudWarning> listFraudWarnings(FraudWarningListRequest request) {
        return list(FRAUD_WARNING_PATH, request, FraudWarning.class);
    }

    public FraudWarning retrieveFraudWarning(String id) {
        return get(FRAUD_WARNING_PATH + "/" + id, FraudWarning.class);
    }

    public Refund retrieveRefund(String refundId) {
        return get(REFUND_PATH + "/" + refundId, Refund.class);
    }

    public Refund createRefund(RefundRequest request) {
        return post(REFUND_PATH, request, Refund.class);
    }

    public Refund createRefund(RefundRequest request, RequestOptions requestOptions) {
        return post(REFUND_PATH, request, requestOptions, Refund.class);
    }

    public Refund updateRefund(RefundUpdateRequest request) {
        return post(REFUND_PATH + "/" + request.getRefundId(), request, Refund.class);
    }

    public Refund updateRefund(RefundUpdateRequest request, RequestOptions requestOptions) {
        return post(REFUND_PATH + "/" + request.getRefundId(), request, requestOptions, Refund.class);
    }

    public ListResponse<Refund> listRefunds(String chargeId) {
        RefundListRequest request = new RefundListRequest();
        request.chargeId(chargeId);
        return listRefunds(request);
    }

    public ListResponse<Refund> listRefunds(RefundListRequest request) {
        return list(REFUND_PATH, request, Refund.class);
    }

    public Payout retrievePayout(String id) {
        return get("/payouts/" + id, Payout.class);
    }

    public Payout createPayout() {
        return post("/payouts", null, Payout.class);
    }

    public ListResponse<Payout> listPayouts() {
        return list("/payouts", Payout.class);
    }

    public ListResponse<Payout> listPayouts(PayoutListRequest request) {
        return list("/payouts", request, Payout.class);
    }

    public ListResponse<PayoutTransaction> listPayoutTransactions(String payoutId) {
        PayoutTransactionListRequest request = new PayoutTransactionListRequest();
        request.payout(payoutId);
        return list("/payout-transactions", request, PayoutTransaction.class);
    }

    public ListResponse<PayoutTransaction> listPayoutTransactions(PayoutTransactionListRequest request) {
        return list("/payout-transactions", request, PayoutTransaction.class);
    }

    public CheckoutSession createCheckoutSession(CheckoutSessionRequest request) {
        return post(CHECKOUT_SESSIONS_PATH, request, CheckoutSession.class);
    }

    public CheckoutSession createCheckoutSession(CheckoutSessionRequest request, RequestOptions requestOptions) {
        return post(CHECKOUT_SESSIONS_PATH, request, requestOptions, CheckoutSession.class);
    }

    public Product createProduct(ProductRequest request) {
        return post(PRODUCTS_PATH, request, Product.class);
    }

    public Product retrieveProduct(String productId) {
        return get(PRODUCTS_PATH + "/" + productId, Product.class);
    }

    public Product updateProduct(ProductUpdateRequest request) {
        return post(PRODUCTS_PATH + "/" + request.getProductId(), request, Product.class);
    }

    public DeleteResponse deleteProduct(String productId) {
        return delete(PRODUCTS_PATH + "/" + productId, DeleteResponse.class);
    }

    public ListResponse<Product> listProducts() {
        return list(PRODUCTS_PATH, Product.class);
    }

    public ListResponse<Product> listProducts(ProductListRequest request) {
        return list(PRODUCTS_PATH, request, Product.class);
    }

    public Tax createTax(TaxRequest request) {
        return post(TAXES_PATH, request, Tax.class);
    }

    public Tax retrieveTax(String taxId) {
        return get(TAXES_PATH + "/" + taxId, Tax.class);
    }

    public Tax updateTax(TaxUpdateRequest request) {
        return post(TAXES_PATH + "/" + request.getTaxId(), request, Tax.class);
    }

    public ListResponse<Tax> listTaxes() {
        return list(TAXES_PATH, Tax.class);
    }

    public ListResponse<Tax> listTaxes(TaxListRequest request) {
        return list(TAXES_PATH, request, Tax.class);
    }

    @Override
    public void close() throws IOException {
        gatewayClient.close();
        gatewayUploadsClient.close();
    }

    protected <T> T get(String path, Class<T> responseClass) {
        return gatewayClient.get(path, responseClass);
    }

    protected <T> T get(String path, Class<T> responseClass, Expand expand) {
        return gatewayClient.get(path, responseClass, expand);
    }
    protected <T> T get(String path, Class<T> responseClass, RequestOptions options, Expand expand) {
        return gatewayClient.get(path, responseClass, expand, options);
    }

    protected <T> T uploadsGet(String path, Class<T> responseClass) {
        return gatewayUploadsClient.get(path, responseClass);
    }

    protected <T> T post(String path, Object request, Class<T> responseClass) {
        return gatewayClient.post(path, request, responseClass);
    }

    protected <T> T post(String path, Object request, RequestOptions options, Class<T> responseClass) {
        return gatewayClient.post(path, request, options, responseClass);
    }

    protected <T> T uploadsMultipart(String path, Map<String, File> files, Map<String, String> form, Class<T> responseClass) {
        return gatewayUploadsClient.multipart(path, files, form, responseClass);
    }

    protected <T> ListResponse<T> list(String path, Class<T> elementClass) {
        return gatewayClient.list(path, elementClass);
    }

    protected <T> ListResponse<T> list(String path, Object request, Class<T> elementClass) {
        return gatewayClient.list(path, request, elementClass);
    }

    protected <T> ListResponse<T> list(String path, Object request, RequestOptions options, Class<T> elementClass) {
        return gatewayClient.list(path, request, options, elementClass);
    }

    protected <T> ListResponse<T> uploadslist(String path, Object request, Class<T> elementClass) {
        return gatewayUploadsClient.list(path, request, elementClass);
    }

    protected <T> T delete(String path, Class<T> responseClass) {
        return gatewayClient.delete(path, responseClass);
    }

    protected <T> T delete(String path, Object request, Class<T> responseClass) {
        return gatewayClient.delete(path, request, responseClass);
    }

    public void setSecretKey(String secretKey) {
        this.gatewayClient.setSecretKey(secretKey);
        this.gatewayUploadsClient.setSecretKey(secretKey);
    }

    public void setConnection(Connection connection) {
        this.gatewayClient.setConnection(connection);
        this.gatewayUploadsClient.setConnection(connection);
    }

    public void setEndpoint(String endpoint) {
        this.gatewayClient.setEndpoint(endpoint);
    }

    public void setUploadsEndpoint(String uploadsEndpoint) {
        this.gatewayUploadsClient.setEndpoint(uploadsEndpoint);
    }

    public void setMerchantId(String merchantId) {
        this.gatewayClient.setMerchantId(merchantId);
        this.gatewayUploadsClient.setMerchantId(merchantId);
    }

    public void setHeadersFactory(Shift4GatewayHeadersFactory headersFactory) {
        this.gatewayClient.setHeadersFactory(headersFactory);
        this.gatewayUploadsClient.setHeadersFactory(headersFactory);
    }
}
