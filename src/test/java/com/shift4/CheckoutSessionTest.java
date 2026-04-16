package com.shift4;

import com.shift4.enums.CheckoutSessionAction;
import com.shift4.enums.CustomFieldPlacement;
import com.shift4.enums.Interval;
import com.shift4.request.AmountRequest;
import com.shift4.request.CheckoutSessionCustomFieldRequest;
import com.shift4.request.CheckoutSessionRequest;
import com.shift4.request.CheckoutSessionStaticFieldRequest;
import com.shift4.request.LineItemRequest;
import com.shift4.request.PlanRequest;
import com.shift4.request.ProductRequest;
import com.shift4.request.TaxRequest;
import com.shift4.response.CheckoutSession;
import com.shift4.response.Customer;
import com.shift4.response.Plan;
import com.shift4.response.Product;
import com.shift4.response.Tax;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.shift4.testdata.Cards.successCard;
import static com.shift4.testdata.CheckoutSessions.checkoutSession;
import static com.shift4.testdata.CheckoutSessions.customField;
import static com.shift4.testdata.CheckoutSessions.lineItem;
import static com.shift4.testdata.CheckoutSessions.simpleProduct;
import static com.shift4.testdata.Customers.customer;
import static org.assertj.core.api.Assertions.assertThat;

class CheckoutSessionTest extends AbstractShift4GatewayTest {

    @Test
    void shouldCreateCheckoutSessionWithInlineProduct() {
        // given
        ProductRequest product = new ProductRequest()
                .name("Test Product")
                .amount(2999)
                .currency("USD");

        CheckoutSessionRequest request = new CheckoutSessionRequest()
                .lineItems(Collections.singletonList(new LineItemRequest(product)));

        // when
        CheckoutSession session = gateway.createCheckoutSession(request);

        // then
        assertThat(session.getId()).isNotNull();
        assertThat(session.getLineItems()).hasSize(1);
        assertThat(session.getLineItems().get(0).getProduct().getName())
                .isEqualTo("Test Product");
    }

    @Test
    void shouldReturnClientSecretWhenCreatingCheckoutSession() {
        // given
        ProductRequest product = new ProductRequest()
                .name("Test Product")
                .amount(1999)
                .currency("USD");

        CheckoutSessionRequest request = new CheckoutSessionRequest()
                .lineItems(Collections.singletonList(new LineItemRequest(product)));

        // when
        CheckoutSession session = gateway.createCheckoutSession(request);

        // then
        assertThat(session.getId()).isNotNull();
        assertThat(session.getClientSecret()).isNotNull();
    }

    @Test
    void shouldCreateCheckoutSessionWithProductId() {
        // given
        Product createdProduct = gateway.createProduct(
                new ProductRequest("Test Product", "USD")
                        .amount(new AmountRequest(2999))
        );

        ProductRequest productRef = new ProductRequest(createdProduct.getId());
        CheckoutSessionRequest request = new CheckoutSessionRequest()
                .lineItems(Collections.singletonList(new LineItemRequest(productRef)));

        // when
        CheckoutSession session = gateway.createCheckoutSession(request);

        // then
        assertThat(session.getId()).isNotNull();
        assertThat(session.getLineItems()).hasSize(1);
        assertThat(session.getLineItems().get(0).getProduct().getId())
                .isEqualTo(createdProduct.getId());
    }

    @Test
    void shouldCreateCheckoutSessionWithCustomFields() {
        // given
        CheckoutSessionCustomFieldRequest companyField = new CheckoutSessionCustomFieldRequest()
                .key("company")
                .label("Company Name")
                .optional(false)
                .placement(CustomFieldPlacement.STANDARD);

        CheckoutSessionRequest request = checkoutSession()
                .customFields(Collections.singletonList(companyField));

        // when
        CheckoutSession session = gateway.createCheckoutSession(request);

        // then
        assertThat(session.getId()).isNotNull();
        assertThat(session.getCustomFields()).hasSize(1);
        assertThat(session.getCustomFields().get(0).getKey()).isEqualTo("company");
        assertThat(session.getCustomFields().get(0).getLabel()).isEqualTo("Company Name");
    }

    @Test
    void shouldCreateCheckoutSessionWithStaticFields() {
        // given
        CheckoutSessionStaticFieldRequest orderIdField = new CheckoutSessionStaticFieldRequest()
                .key("order_id")
                .value("ORD-12345");
        CheckoutSessionStaticFieldRequest trackingField = new CheckoutSessionStaticFieldRequest()
                .key("tracking_number")
                .value("TRK-98765");

        CheckoutSessionRequest request = checkoutSession()
                .staticFields(Arrays.asList(orderIdField, trackingField));

        // when
        CheckoutSession session = gateway.createCheckoutSession(request);

        // then
        assertThat(session.getId()).isNotNull();
        assertThat(session.getStaticFields()).hasSize(2);
        assertThat(session.getStaticFields().get(0).getKey()).isEqualTo("order_id");
        assertThat(session.getStaticFields().get(0).getValue()).isEqualTo("ORD-12345");
        assertThat(session.getStaticFields().get(1).getKey()).isEqualTo("tracking_number");
        assertThat(session.getStaticFields().get(1).getValue()).isEqualTo("TRK-98765");
    }

    @Test
    void shouldCreateCheckoutSessionWithStaticAndCustomFields() {
        // given
        CheckoutSessionStaticFieldRequest orderIdField = new CheckoutSessionStaticFieldRequest()
                .key("order_id")
                .value("ORD-12345");
        CheckoutSessionCustomFieldRequest companyField = new CheckoutSessionCustomFieldRequest()
                .key("company")
                .label("Company Name")
                .optional(false);

        CheckoutSessionRequest request = checkoutSession()
                .staticFields(Collections.singletonList(orderIdField))
                .customFields(Collections.singletonList(companyField));

        // when
        CheckoutSession session = gateway.createCheckoutSession(request);

        // then
        assertThat(session.getId()).isNotNull();
        assertThat(session.getStaticFields()).hasSize(1);
        assertThat(session.getStaticFields().get(0).getKey()).isEqualTo("order_id");
        assertThat(session.getStaticFields().get(0).getValue()).isEqualTo("ORD-12345");
        assertThat(session.getCustomFields()).hasSize(1);
        assertThat(session.getCustomFields().get(0).getKey()).isEqualTo("company");
        assertThat(session.getCustomFields().get(0).getLabel()).isEqualTo("Company Name");
    }

    @Test
    void shouldCreateCheckoutSessionWithStaticFieldUsingBuilder() {
        // given
        CheckoutSessionStaticFieldRequest referenceField = new CheckoutSessionStaticFieldRequest()
                .key("reference_code")
                .value("REF-ABC123");

        CheckoutSessionRequest request = checkoutSession()
                .staticFields(Collections.singletonList(referenceField));

        // when
        CheckoutSession session = gateway.createCheckoutSession(request);

        // then
        assertThat(session.getId()).isNotNull();
        assertThat(session.getStaticFields()).hasSize(1);
        assertThat(session.getStaticFields().get(0).getKey()).isEqualTo("reference_code");
        assertThat(session.getStaticFields().get(0).getValue()).isEqualTo("REF-ABC123");
    }

    @Test
    void shouldCreateCheckoutSessionWithMetadata() {
        // given
        Map<String, String> metadata = new HashMap<>();
        metadata.put("order_id", "12345");
        metadata.put("customer_tier", "premium");

        CheckoutSessionRequest request = checkoutSession()
                .metadata(metadata);

        // when
        CheckoutSession session = gateway.createCheckoutSession(request);

        // then
        assertThat(session.getId()).isNotNull();
        assertThat(session.getMetadata()).containsEntry("order_id", "12345");
        assertThat(session.getMetadata()).containsEntry("customer_tier", "premium");
    }

    @Test
    void shouldCreateCheckoutSessionWithMultipleLineItems() {
        // given
        ProductRequest product1 = simpleProduct().name("Product 1").amount(1000);
        ProductRequest product2 = simpleProduct().name("Product 2").amount(2000);

        List<LineItemRequest> lineItems = Arrays.asList(
                lineItem(product1),
                new LineItemRequest(product2, 2)
        );

        CheckoutSessionRequest request = new CheckoutSessionRequest()
                .lineItems(lineItems);

        // when
        CheckoutSession session = gateway.createCheckoutSession(request);

        // then
        assertThat(session.getId()).isNotNull();
        assertThat(session.getLineItems()).hasSize(2);
        assertThat(session.getLineItems().get(0).getProduct().getName()).isEqualTo("Product 1");
        assertThat(session.getLineItems().get(1).getProduct().getName()).isEqualTo("Product 2");
        assertThat(session.getLineItems().get(1).getQuantity()).isEqualTo(2);
    }

    @Test
    void shouldCreateCheckoutSessionWithCustomer() {
        // given
        Customer customer = gateway.createCustomer(customer(successCard()));

        CheckoutSessionRequest request = checkoutSession()
                .customer(customer);

        // when
        CheckoutSession session = gateway.createCheckoutSession(request);

        // then
        assertThat(session.getId()).isNotNull();
        assertThat(session.getCustomer()).startsWith("chcust_");
    }

    @Test
    void shouldCreateCheckoutSessionWithLocaleAndCapture() {
        // given
        CheckoutSessionRequest request = checkoutSession()
                .locale("en")
                .capture(true)
                .collectBillingAddress(true)
                .collectShippingAddress(false);

        // when
        CheckoutSession session = gateway.createCheckoutSession(request);

        // then
        assertThat(session.getId()).isNotNull();
        assertThat(session.getLocale()).isEqualTo("en");
        assertThat(session.getCapture()).isTrue();
        assertThat(session.getCollectBillingAddress()).isTrue();
        assertThat(session.getCollectShippingAddress()).isFalse();
    }

    @Test
    void shouldCreateCheckoutSessionWithDonation() {
        // given
        AmountRequest donationAmount = new AmountRequest()
                .options(Arrays.asList(500, 1000, 2000))
                .custom(100, 5000);

        ProductRequest product = new ProductRequest()
                .name("Donation")
                .amount(donationAmount)
                .currency("USD");

        CheckoutSessionRequest request = new CheckoutSessionRequest()
                .lineItems(Collections.singletonList(new LineItemRequest(product)));

        // when
        CheckoutSession session = gateway.createCheckoutSession(request);

        // then
        assertThat(session.getId()).isNotNull();
        assertThat(session.getLineItems()).hasSize(1);
        assertThat(session.getLineItems().get(0).getProduct().getName()).isEqualTo("Donation");
    }

    @Test
    void shouldCreateCheckoutSessionWithComplexScenario() {
        // given
        ProductRequest product = new ProductRequest()
                .name("Premium Subscription")
                .amount(2999)
                .currency("USD")
                .description("Monthly premium plan");

        CheckoutSessionCustomFieldRequest companyField = customField();

        Map<String, String> metadata = new HashMap<>();
        metadata.put("subscription_tier", "premium");

        CheckoutSessionRequest request = new CheckoutSessionRequest()
                .lineItems(Collections.singletonList(lineItem(product)))
                .collectBillingAddress(true)
                .locale("en")
                .capture(true)
                .customFields(Collections.singletonList(companyField))
                .metadata(metadata);

        // when
        CheckoutSession session = gateway.createCheckoutSession(request);

        // then
        assertThat(session.getId()).isNotNull();
        assertThat(session.getLineItems()).hasSize(1);
        assertThat(session.getCollectBillingAddress()).isTrue();
        assertThat(session.getLocale()).isEqualTo("en");
        assertThat(session.getCapture()).isTrue();
        assertThat(session.getCustomFields()).hasSize(1);
        assertThat(session.getMetadata()).containsEntry("subscription_tier", "premium");
    }

    @Test
    void shouldCreateCheckoutSessionWithSubscriptionPlan() {
        // given - Create a plan first
        Plan plan = gateway.createPlan(
                new PlanRequest(2999, "USD", Interval.MONTH, "Monthly Subscription")
        );

        ProductRequest planProduct = new ProductRequest(plan.getId());
        CheckoutSessionRequest request = new CheckoutSessionRequest()
                .lineItems(Collections.singletonList(new LineItemRequest(planProduct)));

        // when
        CheckoutSession session = gateway.createCheckoutSession(request);

        // then
        assertThat(session.getId()).isNotNull();
        assertThat(session.getLineItems()).hasSize(1);
        assertThat(session.getLineItems().get(0).getProduct().getId())
                .isEqualTo(plan.getId());
    }

    @Test
    void shouldCreateCheckoutSessionWithDonationOptionsOnly() {
        // given
        AmountRequest donationAmount = new AmountRequest()
                .options(Arrays.asList(500, 1000, 2000, 5000));

        ProductRequest product = new ProductRequest()
                .name("Charity Donation")
                .amount(donationAmount)
                .currency("USD");

        CheckoutSessionRequest request = new CheckoutSessionRequest()
                .lineItems(Collections.singletonList(new LineItemRequest(product)));

        // when
        CheckoutSession session = gateway.createCheckoutSession(request);

        // then
        assertThat(session.getId()).isNotNull();
        assertThat(session.getLineItems()).hasSize(1);
        assertThat(session.getLineItems().get(0).getProduct().getName())
                .isEqualTo("Charity Donation");
    }

    @Test
    void shouldCreateCheckoutSessionWithDonationCustomRangeOnly() {
        // given
        AmountRequest donationAmount = new AmountRequest()
                .custom(100, 10000);

        ProductRequest product = new ProductRequest()
                .name("Custom Donation")
                .amount(donationAmount)
                .currency("USD");

        CheckoutSessionRequest request = new CheckoutSessionRequest()
                .lineItems(Collections.singletonList(new LineItemRequest(product)));

        // when
        CheckoutSession session = gateway.createCheckoutSession(request);

        // then
        assertThat(session.getId()).isNotNull();
        assertThat(session.getLineItems()).hasSize(1);
        assertThat(session.getLineItems().get(0).getProduct().getName())
                .isEqualTo("Custom Donation");
    }

    @Test
    void shouldCreateCheckoutSessionWithAllowSavedCards() {
        // given
        Customer customer = gateway.createCustomer(customer(successCard()));

        CheckoutSessionRequest request = checkoutSession()
                .customer(customer)
                .allowSavedCards(true);

        // when
        CheckoutSession session = gateway.createCheckoutSession(request);

        // then
        assertThat(session.getId()).isNotNull();
        assertThat(session.getAllowSavedCards()).isTrue();
    }

    @Test
    void shouldCreateCheckoutSessionWithCardVerificationAction() {
        // given
        Customer customer = gateway.createCustomer(customer(successCard()));

        CheckoutSessionRequest request = new CheckoutSessionRequest()
                .customer(customer)
                .action(CheckoutSessionAction.CARD_VERIFICATION)
                .capture(false)
                .currency("USD");

        // when
        CheckoutSession session = gateway.createCheckoutSession(request);

        // then
        assertThat(session.getId()).isNotNull();
        assertThat(session.getAction()).isEqualTo(CheckoutSessionAction.CARD_VERIFICATION);
    }

    @Test
    void shouldCreateCheckoutSessionWithRedirectUrl() {
        // given
        CheckoutSessionRequest request = checkoutSession()
                .redirectUrl("https://example.com/success");

        // when
        CheckoutSession session = gateway.createCheckoutSession(request);

        // then
        assertThat(session.getId()).isNotNull();
        assertThat(session.getRedirectUrl()).isEqualTo("https://example.com/success");
        assertThat(session.getUrl()).isNotNull();
    }

    @Test
    void shouldCreateCheckoutSessionWithVendorReference() {
        // given
        CheckoutSessionRequest request = checkoutSession()
                .vendorReference("INV-2024-001");

        // when
        CheckoutSession session = gateway.createCheckoutSession(request);

        // then
        assertThat(session.getId()).isNotNull();
        assertThat(session.getVendorReference()).isEqualTo("INV-2024-001");
    }

    @Test
    void shouldCreateCheckoutSessionWithInlineTax() {
        // given
        TaxRequest inlineTax = new TaxRequest()
                .name("VAT")
                .value(new BigDecimal("23.00"));

        ProductRequest product = new ProductRequest()
                .name("Taxed Product")
                .amount(1000)
                .currency("USD")
                .taxes(Collections.singletonList(inlineTax));

        CheckoutSessionRequest request = new CheckoutSessionRequest()
                .lineItems(Collections.singletonList(new LineItemRequest(product)));

        // when
        CheckoutSession session = gateway.createCheckoutSession(request);

        // then
        assertThat(session.getId()).isNotNull();
        assertThat(session.getLineItems()).hasSize(1);
        assertThat(session.getLineItems().get(0).getProduct().getName())
                .isEqualTo("Taxed Product");
    }

    @Test
    void shouldCreateCheckoutSessionWithTaxById() {
        // given
        Tax createdTax = gateway.createTax(
                new TaxRequest().name("Sales Tax").value(new BigDecimal("8.00"))
        );

        TaxRequest taxById = new TaxRequest(createdTax.getId());

        ProductRequest product = new ProductRequest()
                .name("Product With Existing Tax")
                .amount(2000)
                .currency("USD")
                .taxes(Collections.singletonList(taxById));

        CheckoutSessionRequest request = new CheckoutSessionRequest()
                .lineItems(Collections.singletonList(new LineItemRequest(product)));

        // when
        CheckoutSession session = gateway.createCheckoutSession(request);

        // then
        assertThat(session.getId()).isNotNull();
        assertThat(session.getLineItems()).hasSize(1);
    }

}
